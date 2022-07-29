<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="community-main">
      <div style="display:flex; align-items: center; height:36px">
        <div style="height:36px; width:36px; margin-right:10px; background-color:aquamarine;">
        </div>
        <a>{{maincontent.category}}</a>
      </div>
      <div style="display:flex; height:38px; margin:0; justify-content: space-between; align-items: center; border-bottom: 1px solid #bcbcbc;">
        <div style="margin-left: 10px; font-size:20px;">
          {{maincontent.title}}
        </div>
        <div style="font-size:13px;">
          신고하기 | 글 목록
        </div>
      </div>
      <div style="display:flex; align-items: center; height:36px; margin:10px 0 10px 0;">
        <div style="height:36px; width:36px; margin:0 10px 0 10px; background-color:bisque;">
        </div>
        <a>{{maincontent.author}} | {{maincontent.updated_date}}</a>
      </div>
      <div style="margin: 10px">
        {{maincontent.content}}
      </div>
      <div style="height:50px; margin-top: 30px;">
        <div style="height:50px; width:50px; margin:auto; background-color:aquamarine">
        </div>
      </div>
      <div style="display:flex; align-items: center; width:790px; height:36px; margin: 20px 10px 25px;">
        <input type="text" size="34px" maxlength="30" placeholder=" 여기에 댓글을 달아주세요." style="width:650px; height:34px; border-radius:5px; border: 1px solid #bcbcbc;">
        <v-btn style="height:34px; margin-left:20px">댓글작성</v-btn>
      </div>
    </div>
    <ArticleSides/>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleSides from "../components/ArticleSides.vue"
import { useRoute } from 'vue-router'
import { useCommunityStore } from "@/store"

const communityStore = useCommunityStore()
const route = useRoute()
const articleid = route.params.articleid

// DB를 사용할경우 절대로 쓰지 않을 방법
const maincontents = communityStore.articles.filter((article) => article.article_id == articleid)
const maincontent = maincontents[0]



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