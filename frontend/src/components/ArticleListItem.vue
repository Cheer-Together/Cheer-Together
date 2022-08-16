<template>
  <div class="community-list-articles">
    <div style="width:100px">
      <p>{{article.leagueResponseExceptTeamList.hanName}}</p>
    </div>
    <div @click="toArticleDetail(article.id)" class="article-list" style="width:380px">
      <p>{{article.title}}</p>
    </div>
    <div style="width:100px">
      <p>{{createdDate}}</p>
    </div>
    <div @click="toUserDetail(article.memberResponse.id)" class="article-list" style="width:130px">
      <p>{{article.memberResponse.nickname}}</p>
    </div>
    <div style="width:80px" class="article-likes">
      <p>{{article.likes}}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from 'vue'
import router from "@/router"
const createdDate = ref('')
const props = defineProps({
  article: Object
})

const createdTime = new Date(props.article.createDate)
const now = new Date()
if (createdTime.getDate()==now.getDate() && createdTime.getMonth()==now.getMonth() && createdTime.getFullYear()==now.getFullYear()) {
  let hh = createdTime.getHours()
  if (hh < 10) {hh = '0' + hh}
  let mmm = createdTime.getMinutes()
  if (mmm < 10) {mmm = '0' + mmm}
  createdDate.value = hh + ":" + mmm
} else {
  let mm = createdTime.getMonth()+1
  if (mm < 10) {mm = '0' + mm}
  let dd = createdTime.getDate()
  if (dd < 10) {dd = '0' + dd}
  createdDate.value = (createdTime.getFullYear()-2000) +'/'+ mm +'/'+ dd
}
function toArticleDetail(id) {
  router.push({name: 'ArticleDetail', params: { articleid: id }})
}
function toUserDetail(id) {
  router.push({name: 'Mypage', params: { userid: id }})
}
</script>

<style>
.community-list-articles {
  display:flex;
  height:36px;
  margin:0;
  text-align: center;
  align-items: center;
}
</style>