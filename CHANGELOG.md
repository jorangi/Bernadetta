# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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
