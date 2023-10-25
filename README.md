# meetwork server

![Meetwork Information 01](https://user-images.githubusercontent.com/65934968/186832772-868451b6-6d16-48d9-8dc1-25a2b204742d.png)
<img alt="background" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/97ab66f7-584c-4e59-a2f9-e06b73959741">
<img alt="service 소개" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/561a7dfe-b095-40f5-8117-293c966b339b">

<br>

![Frame (1)](https://github.com/dankook-univ/meetwork-server/assets/81179951/be4b81a1-914d-4dd4-8875-5a64117483e3)

## 서비스 소개 
행사 운영 및 관리를 돕는 서비스, Meetwork입니다. 

단기간 행사마다 <br>
공지 채널을 개설, 단톡방 개설, 퀴즈 플랫폼 이용 등등 <br>
여러 플랫폼을 이동하며 소통하는 상황을 한방에 해결하고자 만들었습니다.

<br>

## 기능
1. 소셜 로그인
2. 행사 개설 및 관리
    1. 멤버 초대 및 참가
    2. 멤버 권한 생성 및 부여
    3. 팀 만들기
    4. 행사 삭제
3. 공지 및 게시판 
4. 팀 & 개인 채팅
5. 퀴즈
    1. 퀴즈 생성
    2. 퀴즈 풀기
    3. 퀴즈 결과 공유하기
    4. 퀴즈 결과 보기 

<br>
<br>

## 기술스택

**`Backend`**: **Java, Spring Boot, JPA, QueryDSL**

**`Database`**: **PostgreSQL, Redis**

**`Infra`**: **Naver Cloud Platform**

**`CI/CD`**: **Github actions**

**`외부API`** : **Kakao Development(SSO), Slack, Gmail**

**`Etc`**: **Swagger**

<br>
<br>

## ERD
<img width="1170" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/960813d3-a7c7-4578-baa3-aaad2eba70df">

<br>
<br>

## 실행화면
### [실행 영상 in 대회](https://youtu.be/1KgCIGsMTS0)
※ 대회 현장 미션이 추가된 영상입니다. 

<br>

#### 행사 초대
<img src="https://github.com/dankook-univ/meetwork-server/assets/81179951/0ff4a444-16a9-4a3c-bf5f-c1f1915b7432" width="260"/>

<br>

#### 게시판
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/52d239cc-547c-4937-8276-7bb638de691a">
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/0d1de864-55c1-4f3c-970f-69a506ccb681">
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/1ad5cb67-7647-434b-aeda-0f230ff527ec">

<br>

#### 채팅
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/62c57399-5c3a-4069-9344-886e38624928">

<br>

#### 퀴즈
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/b7cc446f-0bb1-43af-9c68-eead07849b8a">
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/b2e13077-3537-47ef-b318-58233d6c70a4">
<img width="260" alt="image" src="https://github.com/dankook-univ/meetwork-server/assets/81179951/54f450d8-1ec2-4699-be72-00a72bfd403a">

<br>
<br>

## 시작하기

#### 1. spring profile 설정하기

```bash
--spring.profiles.active=local
```

#### 2. argument 설정하기

```bash
--storage.bucket=${bucket}
--storage.end-point=${end_point}
--storage.region=${region}
--storage.access-key=${access-key}
--storage.secret-key=${secret-key}
--slack.token=${slack_token}
--slack.channel-id=${slack_channel_id}
--spring.mail.username=${mail_username}
--spring.mail.password=${mail_password}
```
