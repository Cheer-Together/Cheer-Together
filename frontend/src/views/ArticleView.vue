<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <ArticleForm v-if="communityStore.toggle"/>
    <ArticleList v-if="!communityStore.toggle"/>
    <ArticleSides/>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleForm from "../components/ArticleForm.vue"
import ArticleList from "../components/ArticleList.vue"
import { useCommunityStore } from "@/store"
import ArticleSides from "../components/ArticleSides.vue"

const communityStore = useCommunityStore()
</script>

<style>
.community-side {
  width: 350px;
  margin-top: 140px;
  margin-left: 80px;
}
@media (max-width: 1580px) {
.community-side {
  margin-top: 120px;
  }
}
.article-list { 
  text-overflow: ellipsis; 
  white-space: nowrap; 
  overflow: hidden; 
  width: 320px;
}
.article-list:hover {
  color: var(--main-color);
  cursor:pointer
}
.community-sides-top {
  height:30px; 
  border-bottom: 1px solid #bcbcbc; 
  font-size: 20px;
}
.community-news {
  display:flex; 
  height:30px; 
  align-items: center; 
  font-size: 14px;
}
.community-matches {
  display:flex; 
  width:350px; 
  height:60px;
}
.community-matches-teamname {
  display:flex; 
  width:110px; 
  height:40px; 
  font-size: 11px; 
  align-items: center; 
}
.maincolor {
  color: var(--main-color);
}
</style>