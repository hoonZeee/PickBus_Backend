# PickBus Backend

## 개발 환경

* Java 17
* Architecture: Layered 

## 개인정보 관리
* PasswordEncoder를 이용한 비밀번호 Hash 처리

## 빌드 및 실행

```bash
./gradlew clean bootJar
docker-compose up -d --build
```

## 환경 변수 관리

* `.env` 파일 사용

## 인증 방식

* JWT 기반 인증

    * Access Token: SecurityContextHolder에 저장
    * Refresh Token: Redis 저장소 관리

## 예외 처리

* Global Exception Handler 적용

## API 관리

* Swagger를 통한 API 문서화

## 스케줄러

* 1일, 15일 정류장 데이터 갱신

## 보안

* CSRF 방어
* Brute Force 공격 방어 로직 적용

## 어드민 관련

* WebConfig 설정
* React Admin Frontend + JWT 인증
* Authentication: `hasRole('ADMIN')` 기반 권한 부여

## 프론트엔드 연동
* iOS (Swift) 클라이언트에서 JWT 기반 인증 연동
