# Backend (Spring Boot)

루트 `AGENTS.md`와 `docs/architecture.md`의 규칙이 우선 적용된다. 이 파일은 backend 전용 내용만 다룬다.

## 빌드 / 실행

- 빌드 도구: Gradle (wrapper 사용, `build.gradle`)
- 테스트: `gradlew.bat test` (Windows) / `./gradlew test` (Linux, macOS)
- 빌드: `gradlew.bat build`
- 실행: `gradlew.bat bootRun`
- Java / Spring Boot 버전은 `build.gradle`을 기준으로 한다. 임의로 올리지 말 것.
- 의존성 추가/변경이 필요하면 `build.gradle`을 수정하기 전에 먼저 알릴 것.

## 패키지 구조

기본 패키지: `com.bernadetta.backend` (경로: `src/main/java/com/bernadetta/backend/`)

```
com.bernadetta.backend
├── config            # 설정 클래스 (SecurityConfig 등)
├── controller        # HTTP 진입점
├── service           # 유스케이스 (예정)
├── domain            # 도메인 모델, 인터페이스 (예정)
└── infrastructure    # 외부 연동 구현체: DB, ai-server 클라이언트 (예정)
```

- 현재 존재하는 패키지는 `config`, `controller`뿐이다. 나머지는 구현하면서 추가한다.
- 의존 방향: `controller -> service -> domain <- infrastructure`. 역방향 의존 금지.
- domain은 Spring, DB, 외부 라이브러리 구현 세부에 의존하지 않는다.
- WebSocket 핸들러 / `MessageHandler`를 둘 패키지는 아직 미정이다. 만들기 전에 위치를 먼저 물어볼 것.

## 코딩 규칙

- 구성 요소 간 의존은 생성자 주입을 사용한다. 필드 주입(`@Autowired` 필드) 금지.
- 외부 연동(DB, ai-server 호출)은 인터페이스를 먼저 정의하고 infrastructure에서 구현한다.
- 메시지/타입별 분기는 `MessageHandler` 같은 인터페이스 + 구현체로 처리한다. if/switch 체인 금지.
- 설정값(URL, 타임아웃, 모델명 등)은 `application.properties`와 `@ConfigurationProperties`로 분리한다. 코드에 하드코딩 금지.
- 컨트롤러/핸들러에는 로직을 두지 않고 service로 위임한다.

## 수정 금지 / 주의

- 아래 폴더는 생성물이므로 직접 수정하지 말 것: `build/`, `bin/`, `.gradle/`
- `SecurityConfig`(인증/보안 설정) 변경은 영향이 크므로 변경 전에 계획을 먼저 보여줄 것.
- `TestController`는 임시 코드일 수 있다. 삭제/수정하기 전에 확인할 것.

## 테스트

- 새 기능에는 테스트를 함께 작성한다. 인터페이스 기반 구조를 활용해 외부 연동은 대역(fake/mock)으로 대체한다.
- 변경 후 `test`를 실행하고 통과 여부를 보고한다.
