<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <ArticleForm v-if="communityStore.toggle"/>
    <ArticleList v-else/>
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
communityStore.communityMounted()
</script>

<style>
.maincolor {
  color: var(--main-color);
}
</style>