# Personnel Management(인사 관리 관리자 사이트)

<br>

## Overivew

- 인사담당자가 되었다고 생각하고 업무에 필요로 할 것 같은 일부 기능들을 구현하였다
- 기본적으로 직원들의 정보들에 대해서 관리할 수 있고 직원들의 출근 상태를 한눈에 쉽게 알아볼 수 있도록 표현하였다
- 각 직원들에 대한 급여관리와 진행중인 또는 진행되었던 업무들에 대해서 볼 수 있고 각 업무에 대해 직원들에 점수 또는 코멘트를 달아 성과관리를 할 수 있다
- 직원들에 대해서 인사발령을 할 수 있으며 해당 날짜가 되면 자동으로 인사 이동이 이루어지며 현재까지 이루어진 인사현황을 확인할 수 있다

<br>

## Usage
### React

- #### package.json 수정 (프로젝트 내의 fronted 폴더 내에 존재)

```
"proxy": http://<해당주소>:8080/
ex) "proxy": http://localhost:8080/
```

- #### 종속성 라이브러리 설치 및 프론트단 서버 실행

```
1. npm install
2. npm start
```

### Spring Boot

- #### application.yaml 수정 (/src/main/resources 폴더 내에 존재)

```
spring:
  datasource:
    url: <해당 DB 주소>
    username: <DB ID>
    password: <DB PW>
    driver-class-name: <해당 DB 드라이버>
    ex) driver-class-name: org.h2.driver (H2)
        driver-class-name: com.mysql.cj.jdbc.Driver (MYSQL)
```

- #### Build And Run

```
1. ./gradlew build -x test
2. cp GeoLite2-City.mmdb build/libs
3. java -jar -Djava.net.perferIPv4Stack=true build/libs/<생성된 jar 파일>
4. localhost:8080/init 접속  // 더미데이터 생성
```

<br>

## Preview

### 전체적인 뷰

![모든뷰](https://user-images.githubusercontent.com/50051656/112812628-4f139e00-90b8-11eb-942f-e5df03ff870b.gif)

### 근태

![근태](https://user-images.githubusercontent.com/50051656/112816236-25f50c80-90bc-11eb-8d2b-b0d31adff29a.gif)

### 직원

![직원](https://user-images.githubusercontent.com/50051656/112812794-79fdf200-90b8-11eb-9fc3-f8964fb0d45d.gif)


### 인사현황

![인사현황](https://user-images.githubusercontent.com/50051656/112812637-50dd6180-90b8-11eb-8fbb-24aafd53e6d4.gif)

<br>

## 기능 목록

### 로그인

|No|기능|설명|
|---|---|:---:|
|1|로그인|로그인 시 jwt 토큰 발급 및 jwt토큰을 이용하여 로그인 시도|
|2|로그아웃|-|

### 근태관리
|No|기능|설명|
|---|---|---|
|1|조회|출근, 퇴근, 결근, 지각 등 상태별 조회, 날짜 또는 사원 이름을 통한 조회|
|2|최신화|매일 자정에 휴가, 병가 등 근태 기록 동기화|
|3|출퇴근|출근 또는 퇴근시 시간과 해당 직원 상태 변경|

### 급여관리
|No|기능|설명|
|---|---|:---:|
|1|조회|사원 이름을 통한 조회|
|2|급여 수정|-|

### 업무관리
|No|기능|설명|
|---|---|:---:|
|1|조회|업무명, 직원이름, 부서명을 통한 조회|
|2|업무 추가|-|
|3|업무 수정|-|

### 성과관리
|No|기능|설명|
|---|---|---|
|1|조회|업무명, 직원이름, 부서명을 통한 조회|
|2|성과 수정|각 업무에 대해서 성과점수와 코멘트를 매김|

### 직원관리
|No|기능|설명|
|---|---|:---:|
|1|조회|직원이름, 부서명을 통한 조회|
|2|직원 추가|-|
|3|직원 수정|-|

### 인사현황관리

|No|기능|설명|
|---|---|:---:|
|1|조회|직원이름, 직급, 부서명을 통한 조회|
|2|인사 발령|원하는 날짜에 원하는 부서와 직급으로 인사 발령 등록|
|3|최신화|매일 자정 인사 발령이 필요한 직원들 인사 이동|

<br>

## Develop Enviroment

- Java jdk 11
- Spring Boot 2.4.1
- Gradle
- H2 Database
- npm 6.14.5
<br>

## Technical Stack

- Spring Boot
- JPA
- Spring Security
- JWT
<br>

## Contributors

- ### Back-End
  - 하주현
    - 로그인
    - 근태
    - 직원
    - 인사현황
  - 송예은
    - 급여
    - 업무
    - 성과
    - 프로필

- ### Front-End
  - 김다윤
  - 보은
