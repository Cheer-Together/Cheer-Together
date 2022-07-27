<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="MainPage">
      <FavoriteTeam/>
      <PopularMatch/>
      <TodayMatchList/>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteTeam from "../components/FavoriteTeam.vue"
import PopularMatch from "../components/PopularMatch.vue"
import TodayMatchList from "../components/TodayMatchList.vue"
</script>

<style>
.MainPage {
  margin: 130px 0 0 275px;
}

</style>