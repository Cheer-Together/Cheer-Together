# 같이 집관 ⚽️

## 프로젝트 개요

실시간으로 사람들과 같이 스포츠 경기를 응원할 수 있는 화상 서비스
<br><br>

## 프로젝트 이름 및 설명

같이집관, 직접 관람(직관)이 아닌 집에서 관람(”집관”)한다는 특징을 이름에 담았다. 또한 여러 사람들과 다같이 응원한다는 의미에서 “같이”를 넣었다.
<br><br>

## 프로젝트 정보

스포츠 경기를 현장에서 직접 관람을 하면, 사람들과 같은 팀을 응원하면서 소속감/유대감을 느낄 수 있어 재밌게 응원할 수 있다. 그러나 최근 코로나의 여파 또는 티켓을 구하는 데에 실패하거나 시간/거리 상의 이유로 직관을 하기가 어려운 경우가 많다. 이러한 이유로 ‘같이 집관’을 서비스하게 되었다.
‘같이 집관’은 직관을 가지 못하더라도 화상에서 실시간으로 사람들과 경기 화면을 보면서 응원할 수 있다. 또한 현장감과 소속감을 느낄 수 있도록 응원가, 전광판, 실시간 경기 정보, 승부예측 시스템 등도 구현되어 있다.
<br><br>

## 프로젝트 개발 환경 및 사용 기술 스택

### FE

- HTML, CSS
- JavaScript
- Vue 3.2.37
- Pinia 2.0.17
- Vuetify 3.0.0.Beta,
- Node.js 16.16.0

### BE

- Java 8
- Spring Boot 2.7.2
- Gradle 7.5
- JPA
- MySQL 8.0.29
- Swagger
- WebRTC , Kurento/Openvidu

### CI/CD

- AWS EC2
- Ubuntu 20.0.4
- Docker 20.10.17
- Jenkins 2.319.3
- Nginx 1.18.0

### 버전/이슈 관리

- Jira
- GitLab / Sourcetree

### TOOL

- Postman
- Figma

### 협업

- Cisco Webex Meetings
- Mattermost
- Notion

### IDE

- Visual Studio Code
- IntelliJ
<br><br>

## 프로젝트 기능 및 예시화면

1. 회원 관리
    - 회원가입
        ![_EC_95_84_EC_9D_B4_EB_94_94__EC_A4_91_EB_B3_B5](/uploads/61e7d8f4557781b35356e5a4d1901352/_EC_95_84_EC_9D_B4_EB_94_94__EC_A4_91_EB_B3_B5.gif)
        ![이메일_인증](/uploads/89186558b9561cbd58c5a50842619605/이메일_인증.gif)
        ![좋아하는](/uploads/eea3021fd0e4195aa2317aa6fd09b617/좋아하는.gif)

    
    - 로그인
        
        ![_EB_A1_9C_EA_B7_B8_EC_9D_B8](/uploads/97498d26d989f798d825247ca3cd4612/_EB_A1_9C_EA_B7_B8_EC_9D_B8.gif)

        
    - 소셜로그인
    - 회원정보 수정
    - 회원탈퇴
    - 마이페이지
        
        ![_EC_83_81_EB_8C_80_EB_B0_A9__EB_A7_88_EC_9D_B4_ED_8E_98_EC_9D_B4_EC_A7_80](/uploads/57bf0ef8b2dd9019b9208118c48e7aeb/_EC_83_81_EB_8C_80_EB_B0_A9__EB_A7_88_EC_9D_B4_ED_8E_98_EC_9D_B4_EC_A7_80.gif)
        
2. 응원방
    - 응원방 목록 리그별로 제공
    - 실시간 인기 응원방
    - 응원방 검색
    - 방 생성(공개/비공개)
    - 전광판
    - 실시간 경기 정보
    - 승부 예측(포인트 베팅)
    - 응원가 재생
    - 채팅
    - 악성 유저 관리(강퇴 및 채팅 비속어 필터링)
  
3. 경기 일정 및 결과
    - 리그별/월별 경기 일정 제공
    - 오늘의 경기 목록 제공
    - 종료된 경기에 대한 경기 결과 제공
       
        ![_EA_B2_BD_EA_B8_B0__EC_9D_BC_EC_A0_95](/uploads/028824819cdca1e3c38432283765b75c/_EA_B2_BD_EA_B8_B0__EC_9D_BC_EC_A0_95.gif)
        
4. 커뮤니티
    - 게시판 기능(CRUD)
        
        ![_EC_BB_A4_EB_AE_A4_EB_8B_88_ED_8B_B0__EA_B8_80_EC_9E_91_EC_84_B1](/uploads/7e607b42ddec8c863f7f4be6845f5ae4/_EC_BB_A4_EB_AE_A4_EB_8B_88_ED_8B_B0__EA_B8_80_EC_9E_91_EC_84_B1.gif)
        
    - 좋아요 기능
        ![_EA_B2_8C_EC_8B_9C_EB_AC_BC__EC_A7_84_EC_9E_85__EC_A2_8B_EC_95_84_EC_9A_94](/uploads/9785b5d88d68aedb6bc4cd188313042b/_EA_B2_8C_EC_8B_9C_EB_AC_BC__EC_A7_84_EC_9E_85__EC_A2_8B_EC_95_84_EC_9A_94.gif)
    - 최신 스포츠 뉴스 제공
    - 게시글 정렬 및 검색
    - 포인트 랭킹
