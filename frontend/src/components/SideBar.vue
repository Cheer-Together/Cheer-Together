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
    <div class="sideBar-item" @click="onairStore.moveOnairPage">
      <v-icon class="sideBar-item-icon">
        mdi-video-outline
      </v-icon>
      <div class="sideBar-item-title" >
        실시간 집관
      </div>
    </div>
    <!-- 프리미어리그 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === '프리미어리그'}">프리미어리그</p>
    </div>
    <!-- 라리가 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === '라리가'}">라리가</p>
    </div>
    <!-- 세리에 A -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === '세리에 A'}">세리에 A</p>
    </div>
    <!-- 분데스리가 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === '분데스리가'}">분데스리가</p>
    </div>
    <!-- 리그 1 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === '리그 1'}">리그 1</p>
    </div>
    <!-- K리그 1 -->
    <div class="sideBar-subtitle" @click="onairStore.moveLeagueRooms">
      <p :class="{'sideBar-subtitle-active' : $route.params.leaguename === 'K리그 1'}">K리그 1</p>
    </div>
    <!-- 경기 일정 -->
    <div class="sideBar-item" @click="scheduleStore.moveSchedulePage('2022-08')">
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
import { useScheduleStore } from '../store/index.js'
import router from '@/router/index.js'
const onairStore = useOnAirStore()
const scheduleStore = useScheduleStore()

</script>

<style>
.sideBar {
  min-width: 210px;
  height:100%;
  border-right: 1px solid var(--sidebar-border-color);
  position: fixed;
  top: 100px;
  background-color: var(--navbar-back-color);
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
.sideBar-item,
.sideBar-item::after {
  -webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
  -o-transition: all 0.3s;
	transition: all 0.3s;
}

.sideBar-item{
  font-size: 15px;
  width: 160px;
  margin: 15px 12px 0px 15px;
  padding: 10px 0px 10px 10px;
  border-radius: 3px;
  font-family: var(--bold-font);
  background: none;
  border-radius: 5px;
  display: block;
  position: relative;
  text-transform: uppercase;
}

.sideBar-item::before,
.sideBar-item::after {
  background: var(--active-color);
  content: '';
  position: absolute;
  z-index: -1;
}
.sideBar-item:hover {
  color: #ffffff;
  cursor: default;
}
.sideBar-item {
  overflow: hidden;
}
.sideBar-item::after {
  /*background-color: #f00;*/
  height: 100%;
  left: -35%;
  top: 0;
  transform: skew(50deg);
  transition-duration: 0.6s;
  transform-origin: top left;
  width: 0;
}

.sideBar-item:hover:after {
  height: 100%;
  width: 135%;
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