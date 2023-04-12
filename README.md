# 웹서버 1
> ✏️HTTP 학습하여 웹 서버 구현   
> ✏️자바의 멀티스레드 프로그래밍 학습   
> ✏️MIME 타입에 대해 이해하고 이를 적용

## [매일 TODO 기록](https://github.com/jaea-kim/be-java-was/wiki/TODO)

## 📝 학습
* HTTP 강의 듣기
* GET 프로토콜 정리


## 📌미션 계획
### 1단계
* http request header 출력 
* http://localhost:8080/index.html 접속 시 index.html 파일 읽어 응답
  * request line에서 path 분리하기
  * 분리된 path에 해당하는 파일 읽어 응답하기 -> `java files readallbytes` 
* 기존코드 개선

### 2단계
* GET으로 회원가입 기능 구현
* HTML와 URL을 비교해 사용자가 입력한 값 파싱하기 
* 단위테스트 

### 3단계
* HTTP Response + MIME 타입 학습
* Stylesheet 파일을 지원하도록 구현 -> 응답 헤더의 Content-Type 이용
  * 요청 헤더의 Accept를 활용 

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
