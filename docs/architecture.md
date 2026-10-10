# Architecture

## 1. 개요

AI 채팅 서비스. 사용자는 frontend에서 대화하고, backend가 세션/인증/이력을 관리하며,
실제 LLM 호출과 AI 로직은 ai-server가 담당한다.

## 2. 서비스 구성과 책임

| 서비스    | 기술                           | 책임                                       | 하지 않는 것                 |
| --------- | ------------------------------ | ------------------------------------------ | ---------------------------- |
| frontend  | Vite, TypeScript, Tailwind CSS | UI, 스트리밍 응답 표시                     | 비즈니스 로직, LLM 호출      |
| backend   | Spring Boot                    | 인증, 세션, 대화 이력 저장, ai-server 중계 | LLM 직접 호출, 프롬프트 구성 |
| ai-server | Python                         | 프롬프트 구성, LLM 호출, AI 로직           | 사용자/인증/DB 관리          |

서비스 간 경계를 넘는 구현(예: backend에서 LLM 직접 호출)이 필요해 보이면 구현하지 말고 먼저 제안한다.

## 3. 통신 흐름

```
frontend --(WebSocket)--> backend --(미정)--> ai-server --> LLM Provider
```

- frontend ↔ backend: WebSocket (순수 WebSocket, STOMP 미사용)
- backend ↔ ai-server: 미정 (7번 참고)

## 4. 핵심 추상화 (인터페이스)

| 인터페이스       | 소속      | 역할                         | 구현체             |
| ---------------- | --------- | ---------------------------- | ------------------ |
| `MessageHandler` | backend   | WebSocket 메시지 타입별 처리 | 메시지 타입별 구현 |
| `ChatRepository` | backend   | 대화 이력 저장 추상화        | 미정               |
| `LlmProvider`    | ai-server | LLM 호출 추상화              | OpenAI, Gemini 등  |

- 새 메시지 타입 / 새 LLM 프로바이더는 **구현체만 추가**한다. 기존 코드 수정은 최소화한다.
- 상위 계층은 인터페이스에만 의존하고, 구체 클래스를 직접 생성/참조하지 않는다.
- 인터페이스 정의 위치: (패키지 구조 확정 후 경로 기입)

## 5. 모듈 구조와 의존 방향

backend: `controller -> service -> domain <- infrastructure`

- domain은 어떤 계층도 참조하지 않는다.
- 외부 연동(DB, ai-server 클라이언트)은 infrastructure에서 domain/service가 정의한 인터페이스를 구현한다.
- WebSocket 핸들러는 메시지를 파싱해 `MessageHandler`로 위임만 하고, 로직을 직접 갖지 않는다.

ai-server, frontend 내부 구조는 아직 미정 (7번 참고).

## 6. 확정된 설계 결정

- frontend ↔ backend 실시간 통신은 WebSocket을 사용한다.
  (이유: 응답 스트리밍 + 생성 중단/상태 이벤트 등 양방향 메시지 필요)
- 순수 WebSocket(`WebSocketHandler`)을 사용하고 STOMP는 쓰지 않는다.
  (이유: 1:1 AI 대화 구조라 구독/방 개념이 불필요)
- 메시지는 `{type, payload}` 형식의 JSON이며, type별 `MessageHandler`로 라우팅한다.
- 타입/종류별 분기는 Strategy 등 다형성으로 처리한다. if/switch 체인으로 늘리지 않는다.
- 설정값, 모델명, 프롬프트, URL은 하드코딩하지 않고 설정/상수로 분리한다.

## 7. 아직 안 정한 것

**AI가 임의로 결정하지 말고, 필요하면 먼저 물어볼 것.**

- backend ↔ ai-server 통신 방식 (gRPC 스트리밍 / HTTP+SSE 등)
- 인증 방식
- WebSocket 재연결 / 하트비트 정책
- `ChatRepository` 구현체 (저장소 종류)
- ai-server 인터페이스 방식 (`ABC` / `Protocol`)
- ai-server, frontend 내부 모듈 구조

## 8. WebSocket 메시지 (초안, 확정 전)

공통 형식: `{ "type": "<타입>", "payload": { ... } }`

| type          | 방향             | 설명                |
| ------------- | ---------------- | ------------------- |
| `chat.send`   | client -> server | 사용자 메시지 전송  |
| `chat.chunk`  | server -> client | 응답 스트리밍 조각  |
| `chat.done`   | server -> client | 응답 완료           |
| `chat.cancel` | client -> server | 응답 생성 중단 요청 |
| `chat.error`  | server -> client | 오류 통지           |

타입을 추가/변경할 때는 이 표와 `MessageHandler` 구현체를 함께 수정한다.
