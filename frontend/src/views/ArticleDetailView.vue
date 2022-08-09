<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="community-main">
      <div class="community-detail-top">
        <div style="height:36px; width:36px; margin-right:10px; background-color:aquamarine;">
        </div>
        <a>{{maincontent.leagueResponseExceptTeamList.hanName}}</a>
      </div>
      <div class="community-detail-header">
        <div style="margin-left: 10px; font-size:20px;">
          {{maincontent.title}}
        </div>
        <div style="font-size:13px;">
          <a class="word-link">신고하기</a><a> | </a>
          <a class="word-link" @click="toArticleListBtn()">글 목록</a>
        </div>
      </div>
      <div class="community-detail-content-top">
        <div style="height:36px; width:36px; margin:0 10px 0 10px; background-color:bisque;">
        </div>
        <a>{{maincontent.memberResponse.nickname}} | {{createdDate}}</a>
      </div>
      <div style="margin: 10px">
        {{maincontent.content}}
      </div>
      <div style="height:50px; margin-top: 30px;">
        <div style="height:50px; width:50px; margin:auto; background-color:aquamarine">
        </div>
      </div>
      <div class="community-detail-writecomment">
        <input type="text" v-model="commentText" maxlength="30" placeholder=" 여기에 댓글을 달아주세요." class="community-detail-commentcontent">
        <v-btn style="height:34px; margin-left:20px">댓글작성</v-btn>
      </div>
    </div>
    <ArticleSides/>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRoute } from 'vue-router'
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleSides from "../components/ArticleSides.vue"
import router from '@/router/index.js'
import axios from "axios"
const route = useRoute()
const articleid = route.params.articleid
const maincontent = ref({})
const createdDate = ref('')
const commentText = ref('')
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/articles/' + articleid,
  method: 'GET', 
}).then(res => {
  maincontent.value = res.data
  const createdTime = new Date(res.data.createDate)
  createdDate.value = createdTime.getFullYear() +'-'+ createdTime.getMonth() +'-'+ createdTime.getDate() + ' ' + createdTime.getHours() + ":" + createdTime.getMinutes()
}).catch(err => {
  console.log(err)
})

function toArticleListBtn() {
  commentText.value = ''
  router.push({name: 'Article'})
}
</script>

<style>
.community-detail-top {
  display:flex;
  align-items: center;
  height:36px;
}
.community-detail-header {
  display:flex;
  height:38px;
  margin:0;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #bcbcbc;
}
.community-detail-content-top {
  display:flex;
  align-items: center;
  height:36px;
  margin:10px 0 10px 0;
}
.community-detail-writecomment {
  display:flex; align-items: center; width:790px; height:36px; margin: 20px 10px 25px;
}
.community-detail-commentcontent {
  width:650px;
  height:34px;
  border-radius:5px;
  border: 1px solid #bcbcbc;
}
.word-link:hover {
  color: var(--main-color);
  cursor: pointer
}
</style>