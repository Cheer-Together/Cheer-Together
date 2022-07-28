<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="community-main">
      <div style="display:flex; height:36px">
        <div style="height:36px; width:36px; background-color:aquamarine;"></div>
        <div style="margin-left: 10px;">
          {{articleid}}
        </div>
      </div>
    </div>
    <ArticleSides/>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleSides from "../components/ArticleSides.vue"
const articleid = this.$route.params.articleid
</script>

<style>
.community-main {
  width: 790px;
  margin-top: 140px;
  margin-left: 300px;
}
@media (max-width: 1580px) {
.community-main {
  margin-top: 120px;
  margin-left: 200px;
  }
}
</style>