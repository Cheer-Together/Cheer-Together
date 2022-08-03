<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="mainPage">
      <FavoriteTeam/>
      <PopularMatch/>
      <TodayMatchList/>
      <MainNews/>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteTeam from "../components/FavoriteTeam.vue"
import PopularMatch from "../components/PopularMatch.vue"
import TodayMatchList from "../components/TodayMatchList.vue"
import MainNews from "../components/MainNews.vue"
</script>

<style>
.mainPage {
  margin: 130px 0 0 275px;
}
@media (max-width: 1580px) {
.mainPage {
  margin: 90px 0 0 200px;
}
}
</style>