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

- /getTopSearched
```
http://localhost:8080/getTopSearched
```
- Response
```
[
  {
    "id":1,
    "keyword":"정자역 맛집",
    "count":8,
    "lastest_srch_date":"2023-56-22 03:56:57"
   },
  {
    "id":2,
    "keyword":"감자빵",
    "count":2,
    "lastest_srch_date":"2023-57-22 03:57:03"
  }
]
```


[카카오 블로그 API](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)

[네이버 블로그 API](https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8-%EA%B2%80%EC%83%89-api-%EB%A0%88%ED%8D%BC%EB%9F%B0%EC%8A%A4)


