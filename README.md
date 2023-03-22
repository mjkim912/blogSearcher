# 블로그 검색 서비스


### 목차


---
### 1. 개발환경

- Spring Boot 2.7.9
- Java 11
- JPA
- H2
- Gradle
- Bootstrap 5
- Lombok


### 2. 개발 내용
- Api 를 이용한 블로그 검색
  - 키워드 검색
  - Sorting : 정확도, 최신순
  - 페이징 : 최대 50페이지, 한 페이지 당 레코드 수

- 인기 검색어 표시
  - 많이 검색한 순서
  - 최대 10개


### 3. API 명세
|Method|URI|설명|
|------|---|---|
|GET|localhost:8080/|블로그 검색 화면|
|POST|localhost:8080/searchKeyword|키워드 검색|
|GET|localhost:8080/getTopSearched|인기검색어 조회|
