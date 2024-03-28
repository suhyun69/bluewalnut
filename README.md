# Bluewalnut 백엔드 개발자 채용 과제

## 개발환경 
> Java 17  
> SpringBoot 3.2.4  
> Gradle

## 실행방법
1. gradlew build
2. cd build/libs
3. java -jar api-0.0.1.SNAMPSHOT.jar

## Swagger URL
> http://localhost:8080/swagger-ui/index.html

## Demo Page URL
> http://localhost:8080/

## API
### User
+ POST /v1/user/card
  + 카드 등록
  + cardNo를 AES로 암호화하여 paymentService.registryCard로 전달한다.
  + paymentService.registryCard로부터 cardRefId를 전달받는다.
+ GET /v1/user/card
  + 내 카드 조회
  + CI를 기준으로 등록한 카드의 cardRefId를 전체 조회한다.
+ GET /v1/user/checkout
  + 내 결제건 조회
  + CI를 기준으로, 결제내역을 전체 조회한다.
### Payment
+ POST /v1/payment/approvalToken
  + Approval Token
  + 토큰을 전달하여 PG사에 결제를 요청한다 
  + pgService.approvalToken 호출하여 전달받은 결과를 바탕으로 결제건 상태를 업데이트한다.
+ GET /v1/payment/checkout
  + checkout status 조회
  + 결제 요청건의 현재 상태를 조회한다
+ POST /v1/payment/checkout
  + Pay by Card_Ref_ID
  + 카드 참조 ID와 결제금액을 전달받아 결제 요청건을 생성, 저장한다.
  + checkoutId 리턴.
### Token
+ GET /v1/token/issue
  + 1회용 토큰 발행
  + 전달받은 checkoutId를 기준으로 1회용 토큰을 생성하고 반환한다
+ GET /v1/token/verify
  + 토큰 유효성 체크
  + 토큰과 매핑된 결제건을 조회하여 유효성을 체크한다
      + 결제 요청건 상태
      + 결제 요청 만료일

## Scenario
1. POST /v1/user/card
   + 카드 등록
2. POST /v1/payment/checkout
   + 결제 요청건 생성
3. POST /v1/token/issue
   + 1회용 토큰 발행
4. POST /v1/payment/approval
   + PG사 결제 요청

## 고민했던 점
> "사용자의 CI는 이미 습득을 했으며 사용자 식별키로 CI를 사용한다"
>
JWT Token에 CI 정보를 넣어 API 호출 시 사용자를 식별하려고 했으나, 실제 구현하려면 회원가입 및 로그인 로직 구현이 필요한 반면 요구사항에는 해당 내용이 없어, 단순히 임의의 CI를 파라미터로 전달하여 사용자 식별키로 사용했다.
> "모든 거래는 rest API 기반으로 한다"
> 
"사용자", "결제 시스템", "토큰발급 시스템", "결제 승인사" 각각을 API로 구현하고, API들 사이의 REST 호출을 기반으로 로직을 구현해야 하는 것처럼 해석된다.  
1. 하나의 SpringBoot 프로젝트 내에서 별도로 동작하는 여러 API를 구현하기 어렵다
2. 각 시스템 간 통신을 완전하게 분리할 경우, API 간 통신을 제어할 로직이 추가로 필요하다

따라서 시스템을 각 Controller에 할당하고 관련 로직을 최대한 메소드로 분리하되, 꼭 필요한 경우에만 Service 간에 호출하도록 구현했다.   