# Bluewalnut 백엔드 엔지니어 채용 과제

## 개발환경 
> Java 17  
> SpringBoot 3.2.4  
> Gradle

## 실행방법
1. gradlew build
2. cd build/libs
3. java -jar v3-0.0.1.SNAMPSHOT.jar

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