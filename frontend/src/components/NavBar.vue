<template>
  <div class="navBar">
    <div>
      <img :src="navBarLogo" class="navBar-logo" @click="router.push({ name:'MainPage', })">
      <img :src="navBarNameLogo" class="navBar-namelogo" @click="router.push({ name:'MainPage', })">
    </div>   

    <div class="navBar-items">
      <!-- 검색 도구 -->
      <div class="nav-search-box">
        <button class="nav-search-btn" @click.prevent="onAirStore.searchRooms(searchData)"><v-icon color="black" dense>mdi-magnify</v-icon></button>
        <input class="nav-search-input" type="text" v-model="searchData.text" @keyup.enter="onAirStore.searchRooms(searchData)" placeholder="응원방 제목을 검색하세요">
      </div>
      <!-- 로그인 -->
      <div v-if="accountStore.isLogin" class="navBar-item-login">
        <img :src="accountStore.profile.profileImage" class="navBar-profile-image">
        <div class="navBar-profile-nickname">
          {{ accountStore.profile.nickname }}
        </div>
          <v-icon class="navBar-profile-icon" @click="navbarStore.isClickProfile = !navbarStore.isClickProfile">
            mdi-chevron-down
          </v-icon>
      </div>

      <!-- 로그인 드롭다운 -->
      <div v-if="navbarStore.isClickProfile" class="navBar-profile-dropdown">
        <div @click="router.push({ name:'Mypage', params: { userid: accountStore.profileId } })" class="navbar-profile-dropdown-btn">
          <v-icon>
            mdi-account-circle
          </v-icon>
          내정보
        </div>
        <div @click="myLogout" class="navbar-profile-dropdown-btn">
          <v-icon>
            mdi-logout
          </v-icon>
          로그아웃
        </div>
      </div>

      <!-- 비로그인 -->
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
import { useAccountStore, useNavbarStore, useOnAirStore } from '@/store'
import router from '@/router'

const navbarStore = useNavbarStore()
const accountStore = useAccountStore()
const navBarLogo = require('../assets/image/로고.png');
const navBarNameLogo = require('../assets/image/같이집관이름로고.png');

navbarStore.isClickProfile = false

const myLogout = () => {
  navbarStore.isClickProfile = false
  accountStore.logoutAccount();
}

const onAirStore = useOnAirStore()

const searchData = {
  text : '',
  category : 'name'
}

</script>

<style>
.navBar {
  width: 100%;
  height: 120px;
  display: flex;
  justify-content: space-between;
  border-bottom: 3px solid var(--navbar-border-color);
  position: fixed;
  background-color: var(--navbar-back-color);
  top: 0;
  z-index: 10000;
}
.navBar-logo {
  margin-left: 10px;
  width: 118px;
}
.navBar-namelogo {
  width: 118px;

}
.navBar-logo:hover {
  cursor: pointer;
}
.navBar-namelogo:hover {
  cursor: pointer;
}
.navBar-profile-image {
  width: 40px;
  height: 40px;
  border: 1px solid grey;
  border-radius: 50px;
}
.navBar-profile-nickname {
  max-width: 200px;
  text-align: start;
  margin-left: 7px;
  font-size: 20px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-family: var(--bold-font);
  padding-top: 4px;
}
.navBar-profile-icon{
  padding-top: 4px;
}
.navBar-profile-icon:hover {
  cursor: pointer;
}
.nav-search-box {
  width: fit-content;
  height: fit-content;
  position: relative;
  margin: 43.5px 30px 43.5px 0; 
}

/* .nav-search-input {
  height: 30px;
  width: 30px;
  border-style: none;
  padding: 0px;
  font-size: 18px;
  letter-spacing: 2px;
  outline: none;
  border-radius: 15px;
  transition: all .5s ease-in-out;
  background-color: transparent;
  padding-right: 30px;
  color:black;
} */

.nav-search-input::placeholder {
  color: gray;
  font-size: 18px;
  letter-spacing: 2px;
  font-weight: 100;
}

.nav-search-input {
  width: 300px;
  height: 30px;
  border-radius: 0px;
  background-color: transparent;
  border-bottom:1px solid var(--main-color);
  transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);
}

.nav-search-btn {
  width: 30px;
  height: 30px;
  border-style: none;
  font-size: 18px;
  font-weight: bold;
  outline: none;
  cursor: pointer;
  border-radius: 20px;
  position: absolute;
  right: 0px;
  color:#ffffff ;
  background-color:transparent;
  pointer-events: painted;  
}

.nav-search-btn:focus ~ .nav-search-input {
  width: 300px;
  border-radius: 0px;
  background-color: transparent;
  border-bottom:1px solid var(--main-color);
  transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);
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
  width: 260px;
  height: 120px;
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
  color: #4747489b;
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
  font-weight: bold;
}

@media (max-width: 1580px) {
.navBar {
  height: 60px;
}
.navBar-logo {
  margin-left: 20px;
  width: 58px;
}
.navBar-namelogo {
  width: 58px;

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
.navBar-profile-nickname {
  font-size: 15px;
}
}
</style>