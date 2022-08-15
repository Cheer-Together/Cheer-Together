<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="onair-page">
      <!-- 헤더 -->
      <div class="onair-header">
        <div>
          <h1>실시간 응원방 - {{ $route.params.leaguename }}</h1>
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
import { useOnAirStore, useAccountStore } from "@/store"
import { useGamesStore } from  '@/store/modules/game.js'

 
const onAirStore = useOnAirStore()
const accountStore = useAccountStore()
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

let items = useGamesStore().liveGames




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

