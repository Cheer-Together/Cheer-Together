<template>
  <div class="sideBar">
    <!-- 홈 -->
    <div class="sideBar-item" @click="router.push({name:'MainPage'})">
      <v-icon class="sideBar-item-icon">
        mdi-home
      </v-icon>
      <div class="sideBar-item-title">
        홈
      </div>
    </div>
    <!-- 실시간 집관 -->
    <div class="sideBar-item" @click="router.push({name: 'Onair' , params: {leaguename: 'Premier-league'} })">
      <v-icon class="sideBar-item-icon">
        mdi-video-outline
      </v-icon>
      <div class="sideBar-item-title" >
        실시간 집관
      </div>
    </div>
    <!-- 프리미어리그 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      프리미어리그
    </div>
    <!-- 라리가 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      라리가
    </div>
    <!-- 세리에 A -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      세리에 A
    </div>
    <!-- 분데스리가 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      분데스리가
    </div>
    <!-- 리그 1 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      리그 1
    </div>
    <!-- K리그 1 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      K리그 1
    </div>
    <!-- 경기 일정 -->
    <div class="sideBar-item" @click="router.push({name:'Schedule'})">
      <v-icon class="sideBar-item-icon">
        mdi-calendar-month-outline
      </v-icon>
      <div class="sideBar-item-title">
        경기 일정
      </div>
    </div>
    <!-- 커뮤니티 -->
    <div class="sideBar-item" @click="router.push({name:'Article'})">
      <v-icon class="sideBar-item-icon">
        mdi-account-group
      </v-icon>
      <div class="sideBar-item-title">
        커뮤니티
      </div>
    </div>
  </div>
</template>

<script setup>
import { useOnAirStore } from '../store/index.js'
import router from '@/router/index.js'
const onairStore = useOnAirStore();

</script>

<style>
@keyframes hoverSidebarItem {
  0% {
    overflow: hidden;
    background-color: white;
  }
  100% {
    overflow: hidden;
    padding-right: 0px;
  }
}

.sideBar {
  min-width: 210px;
  height:100%;
  border-right: 1px solid rgb(225, 252, 255);
  position: fixed;
  top: 100px;
  background-color: rgb(225, 252, 255);
}
.sideBar-item {
  font-size: 15px;
  width: 160px;
  margin: 15px 12px 0px 15px;
  padding: 10px 0px 10px 10px;
  border-radius: 3px;
  font-family: var(--bold-font);
}
.sideBar-item:hover {
  background-color: var(--sub-color);
  cursor:pointer;
  animation-name: hoverSidebarItem;
  animation-duration: 0.5s;
  white-space:nowrap;
  color: var(--main-color);
  box-shadow: 0.5px 0.5px 0.5px;
  /* text-shadow: -0.5px 0px var(--active-color), 0.5px 0.5px var(--active-color), 0.5px 0.5px var(--active-color), 0px -0.5px var(--active-color); */
}
.sideBar-item-icon {
  width: 22px;
  height: 22px;
  font-size: 22px;
  margin-right: 7px;
}
.sideBar-item-title{
  display: inline;
  width: 138px;
  height: 38px;
  font-size: 18px;
  align-items: center;
}
.sideBar-subtitle {
  font-size: 16px;
  margin: 10px 60px 0 60px;
}
.sideBar-subtitle:hover {
  color: var(--main-color);
  cursor:pointer;
}
.sideBar-subtitle-active {
  color: var(--main-color);
}

@media (max-width: 1580px) {
.sideBar {
  min-width: 150px;
  top: 60px;
}
.sideBar-item {
  font-size: 12px;
  margin: 15px 0px 0px 14px;
  padding: 5px 0px;
}
.sideBar-item-icon {
  width: 15px;
  height: 15px;
  font-size: 15px;
  margin-right: 5px;
}
.sideBar-item-title{
  display: inline;
  width: 100px;
  height: 38px;
  font-size: 12px;
  align-items: center;
}
.sideBar-subtitle {
  font-size: 6px;
  margin: 10px 20px 0 40px;
}

}
</style>