<template>
  <div>
    <div class="schedule-list-items" v-for="day in days" :key="day">
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
              <p :class="{'winner' : game.homeScore > game.awayScore}">{{ game.home.hanName }}</p>
            </div>
            <div class="team-right">
              <p class="right-score" v-if="game.status === 'FT'">{{ game.awayScore }}</p>
              <img :src="game.away.logo" class="team-logo">
              <p :class="{'winner' : game.homeScore < game.awayScore}">{{ game.away.hanName }}</p>
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
import { useRoute } from  'vue-router'
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

const route = useRoute()
const monthOfThirtyOne = ['1', '3', '5', '7', '8', '10', '12']
const monthOfThirty = ['4', '6', '9', '11']
const February = '2'
let days = ''
const distinguishMonth = () => {
  if(monthOfThirtyOne.includes(route.params.month)){
    days = 31
  } else if (monthOfThirty.includes(route.params.month)){
    days = 30
  } else if (route.params.month === February){
    days = 28
  }
}
distinguishMonth()
</script>

<style>
.schedule-list-items {
  display: flex;
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
  height: 40px;
  line-height: 40px;
}

.schedule-stadium {
  width: 300px;
  text-align: left;
  margin: auto;
}

.schedule-stadium-items {
  font-size: 12px;
  height: 40px;
  line-height: 40px;
}

.schedule-matches {
  width: 680px;
  text-align: center;
  margin: auto;
}

.schedule-matches-items{
  display: flex;
  width: 550px;
  height: 40px;
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
  left: 80px;
  width: 210px;
  height: 35px;
  line-height: 35px;
  text-align: left;
}

.versus {
  position: relative;
  left: 275px;
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
  width: 25px;
  height: 25px;
  margin: 5px;
}

.schedule-matches .no-match {
  display: flex;
  color: #a7a4a4;
  height: 40px;
  justify-content: center;
  align-items: center;
}
.winner {
  font-weight: bold;
}
</style>