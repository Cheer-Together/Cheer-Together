<template>
  <div>
    <div class="schedule-list-items" v-for="day in 31" :key="day">
      <div class="schedule-date">
        <p>{{ $route.params.month }}월 {{ day }}일</p>
      </div>
      <div class="schedule-time">
        <div v-if="uniqGameDays.includes(`${day}`)">
          <div v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id" class="schedule-time-items">
            {{ game.kickoff.substring(11, 16) }}
          </div>
        </div>
      </div>
      <div class="schedule-stadium" >
        <div v-if="uniqGameDays.includes(`${day}`)">
          <div v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id" class="schedule-stadium-items">
            <p>{{ game.stadium }}</p>
          </div>
        </div>
      </div>
      <div class="schedule-matches">
        <div v-if="uniqGameDays.includes(`${day}`)">
          <div v-for="game in games.filter((ele) => Number(ele.kickoff.substring(8, 10)).toString() === `${day}`)" :key="game.id" class="schedule-matches-items">
            <div class="versus">VS</div> 
            <div class="team-left">
              <p class="left-score" v-if="game.status === 'FT'">{{ game.homeScore }}</p>
              <img :src="game.home.logo" class="team-logo">
              <p>{{ game.home.hanName }}</p>
            </div>
            <div class="team-right">
              <p class="right-score" v-if="game.status === 'FT'">{{ game.awayScore }}</p>
              <img :src="game.away.logo" class="team-logo">
              <p>{{ game.away.hanName }}</p>
            </div>
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
.schedule-date {
  width: 140px;
  text-align: center;
  margin: auto;
}

.schedule-time {
  width: 80px;
  height: auto;
  text-align: center;
  margin: auto;
}

.schedule-time-items {
  height: 35px;
  line-height: 35px;
}

.schedule-stadium {
  width: 260px;
  text-align: left;
  margin: auto;
}

.schedule-stadium-items {
  font-size: 10px;
  height: 35px;
  line-height: 35px;
}

.schedule-matches {
  width: 700px;
  text-align: center;
  margin: auto;
}

.schedule-matches-items{
  display: flex;
  width: 550px;
  height: 35px;
  align-items: center;
}

.team-left {
  display: flex;
  flex-direction: row-reverse;
  position: relative;
  left: 30px;
  width: 210px;
  height: 35px;
  line-height: 35px;
  text-align: right;
}

.team-right {
  display: flex;
  position: relative;
  left: 100px;
  width: 210px;
  height: 35px;
  line-height: 35px;
  text-align: left;
}

.versus {
  position: relative;
  left: 285px;
}

.left-score {
  margin-left: 10px;
  font-weight: bold;
}

.right-score {
  margin-right: 10px;
  font-weight: bold;

}
.schedule-matches .team-logo {
  width: 35px;
  height: 35px;
}

.schedule-matches .no-match {
  color: #a7a4a4
}
</style>