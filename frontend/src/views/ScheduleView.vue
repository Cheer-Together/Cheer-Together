<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="schedule-page">
      <div class="schedule-page-leagues">
        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === '프리미어리그'}">프리미어리그</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === '라리가'}">라리가</p>
        </div>
        
        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === '세리에 A'}">세리에 A</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === '분데스리가'}">분데스리가</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === '리그 1'}">리그 1</p>
        </div>

        <div class="schedule-page-league-items" @click="scheduleStore.clickLeague">
          <p :class="{'league-active' : route.params.leaguename === 'K리그'}">K리그</p>
        </div>
      </div>

      <ScheduleSelectMonth :key="$route.fullPath"/>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ScheduleSelectMonth from "../components/ScheduleSelectMonth.vue"
import { useScheduleStore } from  '../store/index.js'
import { useRoute } from 'vue-router'
const scheduleStore = useScheduleStore();
const route = useRoute()

</script>

<style>
.schedule-page{
  margin-top: 180px;
  margin-left: 470px;
  margin-bottom: 100px;
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
.schedule-page-league-items:hover {
  opacity: 0.5;
}

.league-active {
  color: var(--main-color)
}

@media (max-width: 1580px) {
  .schedule-page {
    margin-top: 100px;
    margin-left: 330px;
  }
}

</style>