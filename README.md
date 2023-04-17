# 웹서버 1
> ✏️HTTP 학습하여 웹 서버 구현   
> ✏️클라이언트에서 전달받은 데이터를 학습하고 
> ✏️자바의 멀티스레드 프로그래밍 학습   
> ✏️MIME 타입에 대해 이해하고 이를 적용
> ✏️HTTP redirection 기능을 적용

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

### 4단계 

* 3단계 피드백 반영
  * `Content-Type` enum으로 변경 후 accept와 확장자를 
* post 메소드 학습
  * form 데이터를 어떻게 전달하는지 
  * 사용되는 중요한 헤더가 뭔지 확인
* post로 변경 시 request 어떻게 오는지 확인

### 5단계

* 4단계 피드백 반영 
  * url 확장자를 찾는 책임을 외부에 넘기는 방법 생각해보기
  * put, delete 요청 처리 
* 쿠키와 세션 학습
  * http 강의 섹션7 보기
  * 쿠키, 세션 파트 책 보기 (+웹서버는 쿠키와 세션을 어떻게 관리하는지 위주로 학습)
* [회원가입] 시 User 객체는 DataBase 클래스 Map<SID, User>users 에 저장 
* [로그인] 클릭 시 로그인 기능 구현 
  * 성공 : index.html로 이동 및 쿠키 설정 (SID=세션 ID)
    * 세션 ID는 적당한 크기의 무작위 숫자 + 문자열 사용 
  * 요청 헤더에 쿠키가 전달되는지 확인 

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
