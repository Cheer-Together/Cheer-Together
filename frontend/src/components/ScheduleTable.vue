<template>
  <div class="schedule-list-items" v-for="match in scheduleStore.schedules" :key="match.date">
    <div class="schedule-date">
      <p>{{ match.date }}</p>
    </div>
    <div class="schedule-time">
      <p>{{ match.time }}</p>
    </div>
    <div class="schedule-stadium">
      <p>{{ match.stadium }}</p>
    </div>
    <div class="schedule-matches">
      <div v-if="!!match.time">
        <p>{{ match.team1 }}</p>
        <img :src="match.team1_logo" class="team-logo">
        <p>VS</p> 
        <img :src="match.team2_logo" class="team-logo">
        <p>{{ match.team2 }}</p>
      </div>
      <div v-if="!match.time" class="no-match">경기가 없습니다.</div>
    </div>
  </div>
</template>

<script setup>
import { useScheduleStore } from  '../store/index.js'
const scheduleStore = useScheduleStore();
</script>

<style>

</style>