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
    <div style="width:130px">
      <p>{{article.memberResponse.nickname}}</p>
    </div>
    <div style="width:80px">
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
  createdDate.value = createdTime.getFullYear() +'/'+ createdTime.getMonth() +'/'+ createdTime.getDate()
}
function toArticleDetail(id) {
  router.push({name: 'ArticleDetail', params: { articleid: id }})
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