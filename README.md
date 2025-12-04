# Backend - Mandi API Server

> Spring Boot 기반 트래킹 가이드 RESTful API

---

## 기술 스택

| Category | Technologies |
|----------|-------------|
| Framework | Spring Boot 3.3.1, Java 17 |
| Database | Spring Data JPA, H2, QueryDSL 5.0.0 |
| Cache | Redis |
| Security | Spring Security OAuth2, JWT 4.2.0 |
| API Docs | Swagger UI 2.6.0 |
| Storage | AWS S3 |
| Monitoring | Spring Actuator, OpenTelemetry |
| Build | Gradle, Docker Compose |

---

## 아키텍처

### 계층 구조
```
Controller (API) → Service (비즈니스 로직) → Repository (데이터 접근) → Entity (도메인 모델)
```

### 도메인 구성
- **user**: 회원, 인증
- **course**: 코스, 완료 기록
- **trekking**: 트래킹 세션
- **post**: 게시글
- **comment**: 댓글
- **badge**: 뱃지

---

## 주요 기능

### 인증/보안
- JWT 이중 토큰 (Access 30분, Refresh 2주)
- Google OAuth 2.0 소셜 로그인
- Redis 토큰 관리 및 블랙리스트
- Spring Security Filter Chain

### 코스 관리
- 코스 목록/상세 조회
- 난이도/거리/시간 필터링
- GPS 기반 주변 코스 추천
- 사용자 선호도 추천
- 완료 코스 기록

### 트래킹
- 세션 시작/종료
- GPS 100m 이내 검증 (하버사인 공식)
- 자동 완료 기록 저장

### 커뮤니티
- 게시글/댓글 CRUD
- 좋아요 기능
- AWS S3 이미지 업로드
- 코스 후기 및 별점

---

## API 문서

### Swagger UI
http://localhost:8080/swagger-ui.html

### 주요 엔드포인트

**Auth**
- `POST /api/google/login` - 로그인
- `POST /api/google/signup` - 회원가입
- `POST /api/logout` - 로그아웃
- `POST /api/reissue` - 토큰 갱신

**Profile**
- `POST /api/profile/check-nickname` - 닉네임 중복 확인
- `GET /api/profile/info` - 프로필 조회

**Course**
- `GET /api/courses` - 코스 목록
- `GET /api/courses/{id}` - 코스 상세
- `POST /api/courses/nearby` - 주변 코스 추천
- `GET /api/courses/preferred` - 선호도 추천
- `GET /api/courses/completed` - 완료 코스

**Trekking**
- `POST /api/trekking/{courseId}/start` - 시작
- `POST /api/trekking/{courseId}/finish` - 종료

**Post**
- `GET /api/post/category/{category}` - 카테고리별 목록
- `POST /api/post/create` - 작성
- `POST /api/post/{id}/like` - 좋아요

**Review**
- `GET /api/review` - 후기 목록
- `POST /api/review` - 후기 작성

**Comment**
- `POST /api/comment/{postId}` - 댓글 작성
- `POST /api/comment/{id}/like` - 좋아요

**Badge**
- `GET /api/badge` - 뱃지 목록

---

## 시작하기

### 실행
```bash
# Redis 시작 (Docker)
docker-compose --profile local up -d

# 애플리케이션 실행
./gradlew bootRun
```

서버: http://localhost:8080

### 환경 변수
```yaml
access-jwt-secret-key: ${JWT_ACCESS_SECRET}
refresh-jwt-secret-key: ${JWT_REFRESH_SECRET}

cloud.aws.s3.bucket: ${S3_BUCKET}
cloud.aws.credentials.access-key: ${AWS_ACCESS_KEY}
cloud.aws.credentials.secret-key: ${AWS_SECRET_KEY}
```

### H2 Console
- http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`

---

## 주요 설정

**Security**
- JWT: Access Token 30분, Refresh Token 2주
- BCrypt 비밀번호 암호화
- CORS 허용

**Database**
- 개발: H2 In-Memory
- 프로덕션: MySQL/PostgreSQL

**Redis**
- 토큰 저장소
- 블랙리스트 관리

**Monitoring**
- `/actuator/health` - 헬스 체크
- `/actuator/metrics` - 메트릭

---





