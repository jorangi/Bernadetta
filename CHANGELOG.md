# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

---

## [0.1.0] - 2026-10-13

### Added
- **회원 인증 및 권한 (Auth Domain)**
  - JWT 기반 로그인 기능 구현 (`POST /api/auth/login`)
    - JJWT (0.12.6) 라이브러리 도입 및 토큰 생성/검증 유틸(`JwtTokenProvider`) 구현
    - 아이디 및 비밀번호 검증 후 `accessToken` 발급
  - 로그아웃 엔드포인트 인터페이스 추가 (`POST /api/auth/logout`)
- **회원 관리 기능 (User Domain - [#3](https://github.com/jorangi/Bernadetta/issues/3))**
  - 회원가입 구현 (`POST /api/users/signup`)
    - 사용자 정의 아이디(`userId`), 이메일, 전화번호 중복 검증
    - Spring Security `BCryptPasswordEncoder` 기반 비밀번호 단방향 암호화
    - 계정 상태(`UserStatus`: `ACTIVE`, `SUSPENDED`, `DELETED`) 및 권한(`Role`) 기본값 설정
  - 아이디 찾기 구현 (`POST /api/users/find-id`)
    - 등록된 이름과 이메일 일치 검증을 통한 아이디 및 가입일시 조회
  - 비밀번호 변경 및 재설정 구현
    - 로그인 상태 비밀번호 변경 (`PATCH /api/users/{id}/password`): 기존 비밀번호 검증, 이전 비밀번호 재사용 방지
    - 비밀번호 분실 시 재설정 (`POST /api/users/reset-password`): 본인 확인(아이디, 이메일 일치) 후 새 비밀번호로 초기화
  - 개인정보(프로필) 수정 구현 (`PUT /api/users/{id}`)
    - 닉네임 및 전화번호 수정 기능
    - 전화번호 변경 시 타 사용자 중복 검증
- **회원 정보 변경 이력 추적 (`UserHistory`)**
  - 닉네임, 전화번호, 비밀번호 변경 시 변경 전/후 값 및 변경 유형(`HistoryType`) 자동 기록 구조 구현

---

## [0.0.1] - 2026-10-10

### Added
- **프로젝트 초기화 (Project Initialization)**
  - Bernadetta (AI 캐릭터 챗 서비스) 프로젝트 기본 저장소 구성 및 README 작성
- **백엔드 기본 환경 구축 (Backend Foundation)**
  - Spring Boot 4.1.1 & Java 21 기반 백엔드 애플리케이션 초기화
  - Gradle 빌드 환경 및 핵심 의존성 구성:
    - `spring-boot-starter-webmvc`, `spring-boot-starter-websocket`
    - `spring-boot-starter-data-jpa`, `mysql-connector-j`
    - `spring-boot-starter-security`, `spring-boot-starter-validation`, `lombok`
  - MySQL 연동 및 JPA/Hibernate 기본 설정 (`application.properties`)
  - Spring Security 초기 보안 설정 (`SecurityConfig`) 구성 (개발 편의를 위한 전체 엔드포인트 허용)
  - 서버 상태 점검을 위한 헬스체크 테스트 API 추가 (`GET /api/health`)
  - 기본 애플리케이션 단위 테스트 환경 구성 (`BackendApplicationTests`)
