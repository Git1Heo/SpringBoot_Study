# 스프링 부트 연습 (22.01.04)
1. 프로젝트 이름
      1. MemberProject
2. 기본패키지
      1. com.icia.member
3. dependency
      1. Spring web, lombok, Thymeleaf, starter-validation
4. 서버포트
      1. 8091
5. 기본기능
      1. 기본주소 요청하면 index.html 출력
      2. MainController에서 기본주소 요청 처리
6. index.html
      1. 회원가입 페이지(/member/save), 로그인페이지(/member/login) 요청 링크 있음.
7. MemberController
      1. 회원가입 페이지 요청이 오면 회원가입 페이지 출력
      2. 회원가입페이지 위치
  1. templates/member/save.html
      3. 로그인 페이지 요청이 오면 로그인 페이지 출력
  1. templates/member/login.html
8. save.html
      1. 회원가입 항목
  1. 이메일, 비밀번호, 이름
      2. 회원가입 요청을 하면 /member/save로 요청을 하며 회원가입 항목은 MemberSaveDTO에 담아서 컨트롤러에 전달된다.
      3. 컨트롤러에서 MemberSaveDTO에 제대로 담겨오는지 출력
------
- 컨트롤러 DTO 폴더 생성후 안에 MemberDTO,memberController 생성
- 템플릿에 member 폴더 생성후 안에 member관련 html 생성

- 타입리프 문법
  - html의 name과 id값 자동
  - th로 유효성검사

#01/05
- JPA활용한 DB
  - database, 유저 생성
  - yml에 db,jpa설정
  - 엔티티, 리포지토리, 서비스 생성
  - 리파지토리에서 jap임포트
  - save,findById
  
- 테스트코드
  - DB에 잘 들어가는지
  - DB에 들어간 값과 내가 넣은값과 값이 같은지

#01/06
- 로그인 처리(findById-PK가 아닌값-) 리파지토리에 추가
- 로그인 처리 테스트코드
- 회원가입 중복처리
- 전체목록조회(findAll)
- 전체목록조회 테스트코드

