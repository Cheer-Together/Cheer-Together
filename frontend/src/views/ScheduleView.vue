<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="schedule-page">
      <div class="schedule-page-leagues">
        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p class="league-active">프리미어리그</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p>라리가</p>
        </div>
        
        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p>세리에 A</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p>분데스리가</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p>리그 1</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p>K리그</p>
        </div>
      </div>

      <div class="schedule-page-select">
        <h1 class="schedule-page-season-select">
          22/23 시즌
        </h1>
        <div class="schedule-page-month-select">
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p class="item-active">22년 8월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>9월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>10월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>11월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>12월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>23년 1월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>2월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>3월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>4월</p>
          </div>
          <div class="schedule-page-month-item" @click="scheduleStore.clickMonth">
            <p>5월</p>
          </div>
        </div>
      </div>

      <div class="schedule-list">
        <div class="schedule-list-title">
          <div class="schedule-date">
            <p>날짜</p>
          </div>
          <div class="schedule-time">
            <p>시간</p>
          </div>
          <div class="schedule-stadium">
            <p>장소</p>
          </div>
          <div class="schedule-matches">
            <p>경기</p>
          </div>
        </div>

        <ScheduleTable/>
        

      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ScheduleTable from "../components/ScheduleTable.vue"
import { useScheduleStore } from  '../store/index.js'
const scheduleStore = useScheduleStore();
</script>

<style>
.schedule-page{
  margin-top: 130px;
  margin-left: 470px;
}

.schedule-page-leagues {
  display: flex;
  width: 1000px;
  height: 50px;
  background-color: var(--sub-color);
  margin: 0 auto;
}

.schedule-page-league-items{
  width: calc(1000px / 6);
  margin-top: 8px;
  font-size: 25px;
  text-align: center;
  cursor: pointer;
}

.schedule-page-select {
  margin: 50px auto;
  width: 1000px;
}

.schedule-page-season-select {
  text-align: center;
}

.schedule-page-month-select {
  display: flex;
  width: 1000px;
  height: 70px;
  justify-content: space-evenly;
  margin: 10px 0;
  
}

.schedule-page-month-item {
  text-align: center;
  margin-top: 25px;
  cursor: pointer;
}

.schedule-page-month-item .item-active {
  color: var(--main-color)
}

.league-active {
  color: var(--main-color)
}

.schedule-list {
  width: 1000px;
}

.schedule-list-title {
  display: flex;
  border-bottom: 1px solid #bcbcbc
}

.schedule-list-items {
  display: flex;
  margin-top: 5px;
  border-bottom: 1px solid #d1cfcf;
}

.schedule-date {
  width: 140px;
  text-align: center;
  margin: auto;
}

.schedule-time {
  width: 80px;
  text-align: center;
  margin: auto;
}

.schedule-stadium {
  width: 230px;
  text-align: center;
  margin: auto;
}

.schedule-matches {
  width: 700px;
  text-align: center;
  margin: auto;
}

.schedule-matches div {
  display: flex;
  justify-content: center;
  align-items: center;
}

.schedule-matches .team-logo {
  width: 35px;
  height: 35px;
}

.schedule-matches .no-match {
  color: #a7a4a4
}

@media (max-width: 1580px) {
  .schedule-page {
    margin-top: 100px;
    margin-left: 330px;
  }
}

</style>