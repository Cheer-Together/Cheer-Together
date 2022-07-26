<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="MainPage">
      <FavoriteTeam/>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteTeam from "../components/FavoriteTeam.vue"
</script>

<style>
.MainPage {
  margin: 50px 0 0 65px;
}

</style>