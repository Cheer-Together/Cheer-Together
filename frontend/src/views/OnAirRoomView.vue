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
          <div class="search-box">
            <div class="select-league">
              <select name="option" @change="changeCategory()">
                <option value="name">방이름</option>
                <option value="managerId">방장아이디</option>
              </select>
            </div>
            <input type="text" v-model="searchData.text" @keyup.enter="onAirStore.searchRooms(searchData)">
            <button @click.prevent="onAirStore.searchRooms(searchData)"><v-icon>mdi-magnify</v-icon></button>
          </div>
          <!-- 경기 목록 별로 정렬 -->
          <div class="select-live-match">
            <select name="liveMatch" @change="changeMatch()">
              <option value="none" class="select-none"> ======= 선택 =======</option>
              <option :value="match.id" v-for="match in items" :key="match.id">{{ match.title }}</option>
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

const fetchLeagueLogo = (leaguename) => {
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
}

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

.onair-header-make-room{
  width: 100px;
  height: 60px;
  border-radius: 10px;
  margin: 0 20px;
  text-align: center;
  background-color: #BDF9CD;
}

.search-box{
  display: flex;
  border-radius: 5px;
  border: 2px solid black;
}

.search-box select {
  margin : 3px;
}

.select-none {
  text-align: center;
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

