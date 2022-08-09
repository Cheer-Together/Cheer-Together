<template>
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
</template>

<script setup>
import ScheduleTable from "./ScheduleTable.vue"
import { useScheduleStore } from  '../store/index.js'
const scheduleStore = useScheduleStore();
</script>

<style>
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
.schedule-list {
  width: 1000px;
}

.schedule-list-title {
  display: flex;
  border-bottom: 1px solid #bcbcbc
}
</style>