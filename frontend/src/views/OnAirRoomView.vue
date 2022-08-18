<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="onair-page">
      <!-- 헤더 -->
      <div class="onair-header">
        <div class="onair-header-title-and-logo">
          <div class="onair-header-title">실시간 집관 - {{ $route.params.leaguename }} </div>
          <img :src="fetchLeagueLogo($route.params.leaguename)" class="onair-header-logo">
        </div>
        <div class="onair-after-search-text" v-if="onAirStore.isSearched === true">
          '{{ onAirStore.searchWord }}' 에 대한 검색 결과입니다.
        </div>
        <div class="onair-header-boxes">
          <!-- 검색 도구 -->
          <div class="onair-search-box">
            <div class="select-league">
              <select name="option" @change="changeCategory()">
                <option value="name">방 제목</option>
                <option value="managerId">방장아이디</option>
              </select>
              <span class="icoArrow"><v-icon>mdi-chevron-down</v-icon></span>
            </div>
            <button class="onair-search-btn" @click.prevent="onAirStore.searchRooms(searchData)"><v-icon color="black">mdi-magnify</v-icon></button>
            <input class="onair-search-input" type="text" v-model="searchData.text" @keyup.enter="onAirStore.searchRooms(searchData)">
          </div>
          <!-- 경기 목록 별로 정렬 -->
          <div class="select-live-match">
            <select name="liveMatch" @change="changeMatch()">
              <option value="none" class="select-none"> ========= 경기 선택 =========</option>
              <option :value="match.id" v-for="match in items" :key="match.id" class="select-option">{{ match.title }}</option>
            </select>
          </div>

          <button @click="onAirStore.makeRoomDialogToggle()" class="onair-header-make-room" v-if="accountStore.isLogin">방만들기</button>
        </div>
      </div>

      <!-- 응원방 목록 -->
      <RoomsList :key="$route.fullPath"/>
      

    </div>
  </div>
  <OnAirMakeModal/>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import RoomsList from "../components/RoomsList.vue"
import OnAirMakeModal from "../components/OnAirMakeModal.vue"
import { useOnAirStore, useAccountStore, useLeagueStore } from "@/store"
import { watch } from 'vue'
import { useRoute } from "vue-router"

 
const onAirStore = useOnAirStore()
const accountStore = useAccountStore()
const leagueStore = useLeagueStore() // eslint-disable-line no-unused-vars
const searchData = {
  text : '',
  category : 'name'
}
const liveMatchData = {
  id: ''
}
const changeCategory = () => {
  const selectMenu = document.querySelector('.select-league select')
  searchData.category = selectMenu.options[document.querySelector(".select-league select").selectedIndex].value
}

const changeMatch = () => {
  const selectMenu = document.querySelector('.select-live-match select')
  liveMatchData.id = selectMenu.options[document.querySelector(".select-live-match select").selectedIndex].value
  onAirStore.selectMatch(liveMatchData.id)
}

const leagues = ['프리미어리그', '라리가', '세리에 A', '분데스리가', '리그 1', 'K리그 1']
const fetchLeagueLogo = (leaguename) => {
  if(leagues.includes(leaguename)){
    for(let league of leagueStore.leagues){
      if(league.hanName === leaguename){
        return league.logo
      } else if (league.hanName === '세리에' && leaguename === '세리에 A'){
        return league.logo
      } else if (league.hanName === '리그앙' && leaguename === '리그 1'){
        return league.logo
      } else if (league.hanName === 'K리그' && leaguename === 'K리그 1'){
        return league.logo
      }
  }
} else { return require('../assets/image/로고.png'); }}

const route = useRoute()
let items = []
if (route.params.leaguename === '모든 응원방 목록'){
  items = JSON.parse(sessionStorage.getItem('AllLiveGames'))
} else if (route.params.leaguename === '프리미어리그'){
  items = JSON.parse(sessionStorage.getItem('liveGames39'))
} else if (route.params.leaguename === '라리가'){
  items = JSON.parse(sessionStorage.getItem('liveGames140'))
} else if (route.params.leaguename === '세리에 A'){
  items = JSON.parse(sessionStorage.getItem('liveGames135'))
} else if (route.params.leaguename === '분데스리가'){
  items = JSON.parse(sessionStorage.getItem('liveGames78'))
} else if (route.params.leaguename === '리그 1'){
  items = JSON.parse(sessionStorage.getItem('liveGames61'))
} else if (route.params.leaguename === 'K리그 1'){
  items = JSON.parse(sessionStorage.getItem('liveGames292'))
} 
watch(route, () => {
  onAirStore.isSearched = false
})



</script>

<style>
.onair-page {
  margin: 120px 0 0 250px;
}

.onair-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 1600px;
  margin-top: 40px;
}

.onair-header-title-and-logo{
  display: flex;
}
.onair-header-title {
  font-size: 32px;
  font-weight: bold;
  padding-top: 5px;
  margin-right: 10px;
}

.onair-header-logo {
  width: 60px;
  height: 60px;
  object-fit: contain;
}

.onair-header-boxes {
  display: flex;
  align-items: center;
  width: auto;
  justify-content: space-between;
  margin-right: 20px;
}

.onair-search-box {
  width: fit-content;
  height: fit-content;
  position: relative;
  margin: auto 20px;
  display: flex;
  border: 2px solid var(--main-color);
  border-radius: 4px;
  background-color: #f2f7ff;
}

.onair-search-input {
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
}

.onair-search-input:focus {
  width: 300px;
  border-radius: 0px;
  background-color: transparent;
  transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);
  margin-top: 2px;
}

.onair-search-btn {
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
  margin-top: 2px;
}

.onair-search-btn:focus ~ .onair-search-input {
  width: 300px;
  border-radius: 0px;
  background-color: transparent;
  transition: all 500ms cubic-bezier(0, 0.110, 0.35, 2);
}

.onair-header-make-room{
  font-size: 17px;
  width: 100px;
  height: 60px;
  border-radius: 10px;
  margin: 0 20px;
  text-align: center;
  background-color: #BDF9CD;
}
.onair-header-make-room:hover {
  opacity: 0.5;
}

.select-none {
  text-align: center;
}

.select-league {
  position: relative;
  width: 110px;
  height: 35px;
  border-right: 2px solid var(--main-color);
}

.select-league select {
  width: inherit;
  height: inherit;
  background: transparent;
  border: 3px;
  outline: 0 none;
  padding: 0 5px;
  position: relative;
  z-index: 3;
  margin-top: 2px;
}

.select-league option{
  background-color: #f2f7ff;
  color: black;
  padding: 3px 0;
  font-size: 16px;
}

.icoArrow {
  position: absolute; 
  top: 0; 
  right: 0; 
  z-index: 0; 
  width: 35px; 
  height: inherit;
  display: flex;
  justify-content: center;
  align-items: center;
}

.select-live-match {
  position: relative;
  width: auto;
  height: 39px;
  border: 2px solid var(--main-color);
  border-radius: 4px;
  padding: 0px;
  margin: auto 20px;
  background-color: #f2f7ff;

}

.select-live-match select {
  width: inherit;
  height: inherit;
  background: transparent;
  border: 3px transparent;
  outline: 0 none;
  padding: 0 5px;
  position: relative;
  z-index: 3;
  
}

.select-live-match option{
  background-color: #f2f7ff;
  color: black;
  padding: 3px 0;
  font-size: 16px;
}


@media (max-width: 1580px) {
  .onair-page {
    margin: 100px 0 0 200px;
  }
  .onair-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 1300px;
  }
  
}
</style>

