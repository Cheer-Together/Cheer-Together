<template>
  <div>
    <div class="schedule-list-items" v-for="day in 31" :key="day">
      <div class="schedule-date">
        <p>{{ $route.params.month }}월 {{ day }}일</p>
      </div>
      <div class="schedule-time">
        <div v-if="uniqGameDays.includes(`${day}`)">
          <div v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id">
            {{ game.kickoff.substring(11, 16) }}
          </div>
        </div>
      </div>
      <div class="schedule-stadium" >
        <div v-if="uniqGameDays.includes(`${day}`)">
          <p v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id">
            {{ game.stadium }}
          </p>
        </div>
      </div>
      <div class="schedule-matches">
        <div v-if="uniqGameDays.includes(`${day}`)">
          <div v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id" class="schedule-matches-versus">
            <p>{{ game.home.hanName }}</p>
            <img :src="game.home.logo" class="team-logo">
            <p>VS</p> 
            <img :src="game.away.logo" class="team-logo">
            <p>{{ game.away.hanName }}</p>
          </div>
        </div>
        <div v-if="!uniqGameDays.includes(`${day}`)">
          <p class="no-match">경기가 없습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useScheduleStore } from  '../store/index.js'
// import { onUpdated } from 'vue'
const scheduleStore = useScheduleStore();
const games = scheduleStore.gamesMonth

let dupGameDays = []
for(let game of games){
  if(game.kickoff.substring(8, 10).startsWith('0')){
    dupGameDays.push(game.kickoff.substring(9, 10))
  } else {
    dupGameDays.push(game.kickoff.substring(8, 10))
  }
}
let set = new Set(dupGameDays)
let uniqGameDays = [...set]





</script>

<style>
.schedule-list-items {
  display: flex;
  margin-top: 5px;
  border-bottom: 1px solid #d1cfcf;
}
</style>