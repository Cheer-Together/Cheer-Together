<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="community">    
      <ArticleForm v-if="communityStore.toggle"/>
      <ArticleList v-else/>
      <ArticleSides/>
    </div>
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
if (!communityStore.isModify) {
  communityStore.communityMounted()
}
</script>

<style>
.community {
  display: flex;
  margin-top: 160px;
  margin-left: 210px;
  width:100%;
  justify-content: center;
  align-content: flex-start;
  min-width: 1400px;
}
@media (max-width: 1580px) {
  .community {
    margin-top: 120px;
    margin-left: 175px;
  }
}
.community-main {
  width: 790px;
}
.maincolor {
  color: var(--main-color);
}
</style>