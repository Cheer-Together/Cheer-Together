<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="onair-page">
      <!-- 헤더 -->
      <div class="onair-header">
        <div>
          <h1>{{ $route.params.leaguename }}</h1>
        </div>
        <div class="onair-header-boxes">
          <!-- 검색 도구 -->
          <div class="search-box">
            <button class="btn-search"><v-icon>mdi-magnify</v-icon></button>
            <input type="text" class="input-search" placeholder="Type to Search...">
          </div>

          <div>
            <p>경기 목록</p>
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
const onAirStore = useOnAirStore()
const accountStore = useAccountStore()
</script>

<style>
.onair-page {
  margin: 100px 0 0 250px;
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
  width: fit-content;
  height: fit-content;
  position: relative;
}
/* .input-search{
  height: 50px;
  width: 50px;
  border-style: none;
  padding: 10px;
  font-size: 18px;
  letter-spacing: 2px;
  outline: none;
  border-radius: 25px;
  transition: all .5s ease-in-out;
  background-color: #22a6b3;
  padding-right: 40px;
  color:#fff;
} */
/* .input-search::placeholder{
  color:rgba(255,255,255,.5);
  font-size: 18px;
  letter-spacing: 2px;
  font-weight: 100;
} */

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

