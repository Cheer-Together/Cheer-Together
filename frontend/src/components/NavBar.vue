<template>
  <div class="navBar">
    <div>
      <img :src="navBarLogo" class="navBar-logo" @click="router.push({ name:'MainPage', })">
      <img :src="navBarNameLogo" class="navBar-namelogo" @click="router.push({ name:'MainPage', })">
    </div>
    <div class="navBar-items">
      <div v-if="accountStore.isLogin" class="navBar-item-login">
        <img :src="user.profileImage" class="navBar-profile-image">
        <div class="navBar-profile-nickname">
          <span style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">{{ user.nickname}}</span>
          
          <v-icon class="navBar-profile-icon" @click="navbarStore.isClickProfile = !navbarStore.isClickProfile">
            mdi-chevron-down
          </v-icon>
        </div>
      </div>
      <div v-if="navbarStore.isClickProfile" class="navBar-profile-dropdown">
        <div @click="router.push({ name:'Mypage', params: { userid: 30 } })">
          <v-icon>
            mdi-account-circle
          </v-icon>
          내정보
        </div>
        <div @click="myLogout">
          <v-icon>
            mdi-logout
          </v-icon>
          로그아웃
        </div>
      </div>
      <div v-if="!accountStore.isLogin" @click="accountStore.loginDialogToggle()" class="navBar-item-notlogin">
        로그인
      </div>
      <div v-if="!accountStore.isLogin" class="navBar-item-notlogin" @click="router.push({ name:'Signup', })">
        회원가입
      </div>
      
      <div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAccountStore } from '@/store'
import { useNavbarStore } from '@/store'
import router from '@/router'

const navbarStore = useNavbarStore()
const accountStore = useAccountStore()
const navBarLogo = require('../assets/image/로고.png');
const navBarNameLogo = require('../assets/image/같이집관이름로고.png');

navbarStore.isClickProfile = false

const user = {
  email: "tjdjer5@tjdejr.com",
  nickname: "tjdejr",
  profileImage: require('../assets/image/로고.png'),
  role: "user",
  myInfo: "tjdejr",
  favoriteLeagueList: [
    {
      id: 4,
      league: {
        id: 1,
        teamList: [
          {
            "id": 1,
            "name": "Arsenal",
            "logo": "https://media.api-sports.io/football/teams/42.png",
            "code": "ARS",
            "apiId": 42,
            "hanName": "아스널"
          },
          {
            "id": 2,
            "name": "Aston Villa",
            "logo": "https://media.api-sports.io/football/teams/66.png",
            "code": "AST",
            "apiId": 66,
            "hanName": "아스톤 빌라"
          },
          {
            "id": 3,
            "name": "Bournemouth",
            "logo": "https://media.api-sports.io/football/teams/35.png",
            "code": "BOU",
            "apiId": 35,
            "hanName": "본머스"
          },
          {
            "id": 4,
            "name": "Brentford",
            "logo": "https://media.api-sports.io/football/teams/55.png",
            "code": "BRE",
            "apiId": 55,
            "hanName": "브렌트포드"
          },
          {
            "id": 5,
            "name": "Brighton",
            "logo": "https://media.api-sports.io/football/teams/51.png",
            "code": "BRI",
            "apiId": 51,
            "hanName": "브라이턴 & 호브 알비온"
          },
          {
            "id": 6,
            "name": "Chelsea",
            "logo": "https://media.api-sports.io/football/teams/49.png",
            "code": "CHE",
            "apiId": 49,
            "hanName": "첼시"
          },
          {
            "id": 7,
            "name": "Crystal Palace",
            "logo": "https://media.api-sports.io/football/teams/52.png",
            "code": "CRY",
            "apiId": 52,
            "hanName": "크리스탈 팰리스"
          },
          {
            "id": 8,
            "name": "Everton",
            "logo": "https://media.api-sports.io/football/teams/45.png",
            "code": "EVE",
            "apiId": 45,
            "hanName": "에버튼"
          },
          {
            "id": 9,
            "name": "Fulham",
            "logo": "https://media.api-sports.io/football/teams/36.png",
            "code": "FUL",
            "apiId": 36,
            "hanName": "풀럼"
          },
          {
            "id": 10,
            "name": "Leeds",
            "logo": "https://media.api-sports.io/football/teams/63.png",
            "code": "LEE",
            "apiId": 63,
            "hanName": "리즈 유나이티드"
          },
          {
            "id": 11,
            "name": "Leicester",
            "logo": "https://media.api-sports.io/football/teams/46.png",
            "code": "LEI",
            "apiId": 46,
            "hanName": "레스터 시티"
          },
          {
            "id": 12,
            "name": "Liverpool",
            "logo": "https://media.api-sports.io/football/teams/40.png",
            "code": "LIV",
            "apiId": 40,
            "hanName": "리버풀"
          },
          {
            "id": 13,
            "name": "Manchester City",
            "logo": "https://media.api-sports.io/football/teams/50.png",
            "code": "MAC",
            "apiId": 50,
            "hanName": "맨체스터 시티"
          },
          {
            "id": 14,
            "name": "Manchester United",
            "logo": "https://media.api-sports.io/football/teams/33.png",
            "code": "MUN",
            "apiId": 33,
            "hanName": "맨체스터 유나이티드"
          },
          {
            "id": 15,
            "name": "Newcastle",
            "logo": "https://media.api-sports.io/football/teams/34.png",
            "code": "NEW",
            "apiId": 34,
            "hanName": "뉴캐슬 유나이티드"
          },
          {
            "id": 16,
            "name": "Nottingham Forest",
            "logo": "https://media.api-sports.io/football/teams/65.png",
            "code": "NOT",
            "apiId": 65,
            "hanName": "노팅엄 포레스트"
          },
          {
            "id": 17,
            "name": "Southampton",
            "logo": "https://media.api-sports.io/football/teams/41.png",
            "code": "SOU",
            "apiId": 41,
            "hanName": "사우스햄튼"
          },
          {
            "id": 18,
            "name": "Tottenham",
            "logo": "https://media.api-sports.io/football/teams/47.png",
            "code": "TOT",
            "apiId": 47,
            "hanName": "토트넘 홋스퍼"
          },
          {
            "id": 19,
            "name": "West Ham",
            "logo": "https://media.api-sports.io/football/teams/48.png",
            "code": "WES",
            "apiId": 48,
            "hanName": "웨스트햄 유나이티드"
          },
          {
            "id": 20,
            "name": "Wolves",
            "logo": "https://media.api-sports.io/football/teams/39.png",
            "code": "WOL",
            "apiId": 39,
            "hanName": "울버햄튼원더러스"
          }
        ],
        name: "Premier League",
        logo: "https://media.api-sports.io/football/leagues/39.png",
        apiId: 39,
        hanName: "프리미어리그"
      }
    },

  ]
}
const myLogout = () => {
  navbarStore.isClickProfile = false
  accountStore.logoutAccount()
}
</script>

<style>
.navBar {
  width: 100%;
  height: 100px;
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid var(--navbar-border-color);
  position: fixed;
  background-color: var(--navbar-back-color);
  top: 0;
  z-index: 1;
}
.navBar-logo {
  margin-left: 10px;
  width: 98px;
}
.navBar-namelogo {
  width: 98px;

}
.navBar-logo:hover {
  cursor: pointer;
}
.navBar-profile-image {
  width: 40px;
  border: 1px solid var( --main-color);
  border-radius: 50px;
}
.navBar-profile-nickname {
  width: 90px;
  text-align: start;
  margin-left: 10px;
  font-size: 20px;
}
.navBar-profile-icon:hover {
  cursor: pointer;
}
.navBar-items {
  display: flex;
  margin-right: 40px;
  position: relative;
}
.navBar-item {
  width: 100px;
  height: 18px;
  text-align: center;
}
.navBar-item-login {
  display: flex;
  width: 150px;
  height: 100px;
  align-items: center;
  font-size: 20px;
}
.navBar-item-notlogin{
  width: 100px;
  height: 18px;
  text-align: center;
  margin: auto 0;
}
.navBar-item:hover, .navBar-item-notlogin:hover {
  color: var(--main-color);
  cursor: pointer;
}
.navBar-profile-dropdown {
  width: 300px;
  position: absolute;
  right: 30px;
  top: 80px;
  border: 1px solid #ecf0f5;
  color: #5f60639b;
  background-color: #ffffff;
  border-radius: 10px;
  z-index: 2;
  display: flex;
}
.navBar-profile-dropdown div {
  width: 150px;
  height: 60px;
  font-size: 18px;
  text-align: center;
  padding-top: 18px;
}
.navBar-profile-dropdown div:first-child {
  border-right: 1px solid #ecf0f5;
}
.navBar-profile-dropdown div:hover {
  cursor: pointer;
}

@media (max-width: 1580px) {
.navBar {
  height: 60px;
}
.navBar-logo {
  margin-left: 20px;
  width: 60px;
}
.navBar-items {
  display: flex;
  margin-right: 30px;
}
.navBar-item {
  font-size: 12px;
  width: 80px;
  height: 18px;
}
.navBar-item-login{
  height: 60px;
}
}
</style>