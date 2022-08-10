<template>
  <div class="schedule-list-items" v-for="day in 31" :key="day">
    <div class="schedule-date">
      <p>{{ $route.params.month }}월 {{ day }}일</p>
    </div>
    <div class="schedule-time">
      <div>
        <p>ddd</p>
      </div>
    </div>
    <div class="schedule-stadium">
      <p>싸피 경기장</p>
    </div>
    <div class="schedule-matches">
      <div>
        <p>1팀</p>
        <img src="" class="team-logo">
        <p>VS</p> 
        <img src="" class="team-logo">
        <p>2팀</p>
      </div>
      <!-- <div v-if="!match.time" class="no-match">경기가 없습니다.</div> -->
    </div>
  </div>
</template>

<script setup>
// import { useScheduleStore } from  '../store/index.js'
// const scheduleStore = useScheduleStore();
const dayList = ['6', '7', '8', '13', '14', '15', '16', '21', '20', '22', '23', '27', '28', '29']
console.log(dayList)
</script>

<style>
.schedule-list-items {
  display: flex;
  margin-top: 5px;
  border-bottom: 1px solid #d1cfcf;
}
</style>