<template>
  <div v-if="loaded" style="display: flex; height: 40px; margin-bottom: 5px;">
    <div style="height: 40px; width:40px; margin-right: 6px;">
      <img :src="commentLogo" style="height:40px; width:40px;">
    </div>
    <div style="font-size:13px; margin-left: 2px;">
      <div style="color:#8D8D8D; display: flex;">
      <div class="word-link" @click="toUserDetail(reply.memberId)">
        {{replyNickName}}
      </div>
        <a style="margin-left: 5px;"> | {{replyCreatedDate}}</a>
        <div v-if="loginUserId==props.reply.memberId||userRole=='ADMIN'" @click="deleteComment()" class="word-link" style="margin-left:5px">
          <v-icon>
            mdi-delete
          </v-icon>
          <a>삭제하기</a>
        </div>
      </div>
      <div>
        {{props.reply.content}}
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { defineProps, ref } from 'vue'
import jwt_decode from "jwt-decode"
import router from '@/router';
const props = defineProps({
  reply: Object,
  userRole: String
})
const replyNickName = ref('')
const replyCreatedDate = ref('')
const loginUserId = jwt_decode(sessionStorage.getItem('token')).sub
const loaded = ref(false)
const commentLogo = ref(require("../assets/image/로고.png"))

const createdTime = new Date(props.reply.createDate)
let yy = createdTime.getFullYear()
let mm = createdTime.getMonth()+1
if (mm < 10) {mm = '0' + mm}
let dd = createdTime.getDate()
if (dd < 10) {dd = '0' + dd}
let hh = createdTime.getHours()
if (hh < 10) {hh = '0' + hh}
let mmm = createdTime.getMinutes()
if (mmm < 10) {mmm = '0' + mmm}
replyCreatedDate.value = yy +'/'+ mm +'/'+ dd + ' ' + hh + ":" + mmm
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/members/' + props.reply.memberId,
  method: 'GET', 
}).then(res => {
  replyNickName.value = res.data.nickname
  if (res.data.favoriteTeamList.length > 0) {
    commentLogo.value = res.data.favoriteTeamList[0].logo
  }
  loaded.value = true
}).catch(err => {
  console.log(err)
})

function deleteComment() {
  axios({
    url: 'https://i7b204.p.ssafy.io/cheertogether/articles/reply/' + props.reply.id,
    method: 'DELETE',
  }).then(res => {
    console.log(res)
    router.go()
  }).catch(err => {
    console.log(err)
  })
}

function toUserDetail(id) {
  router.push({name: 'Mypage', params: { userid: id }})
}
</script>

<style>

</style>