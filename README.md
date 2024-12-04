## SK m&service 과제

### 개발 환경

**Front-End**  
- Thymeleaf (HTML, CSS, JavaScript, AJAX)  

**Back-End**  
- Java 17  
- Spring Boot 3.4.0  
- H2 Database  
- Spring Security  
- Spring Data JPA  
- Gradle  
- JWT  

**버전 관리**  
- **도구:** Git  
- **브랜치 전략:**  
  - 브랜치 네이밍: `feature/#N`  
  - 머지 방식: Squash and Merge  

---

## 구현 기능

### 1. 회원 관리  
- 회원가입  
- 로그인  
- 로그아웃  
- 마이페이지  

### 2. 게시판 기능  
- 게시판 목록 조회  
  - 역순 정렬  
  - 노출순 정렬  
  - 검색 조회 (작성자 ID, 제목 기준)  
  - 페이징 처리  
- 게시글 상세 조회 및 조회 수 증가  
- 게시글 작성 (파일 첨부 가능)  
- 게시글 수정 및 삭제 (작성자만 가능)  

---

### 아키텍처

**Controller**  - 클라이언트 요청 처리 및 응답 반환  
**Service**  - 비즈니스 로직 처리  
**DTO**  - 데이터 전송 객체로 데이터 계층 간 이동 관리  
**Repository**  - 데이터 접근 계층 (Persistence Layer)  
**Entity**  - 데이터베이스 매핑 객체  


## 주요 특징  
- Spring Security와 JWT를 활용한 인증 및 권한 관리  
- Spring Data JPA를 활용한 데이터베이스 연동  
- H2 Database를 사용한 빠른 개발 및 테스트 환경 구축  
- Git을 통한 체계적인 버전 관리 및 브랜치 전략 적용
- 인터페이스와 구현체를 분리하여 유지보수성과 확장성을 강화  

