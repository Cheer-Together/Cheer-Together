<template>
  <div v-if="loaded" style="display: flex; height: 40px; margin-bottom: 5px;">
    <div style="height: 40px; width:40px; margin-right: 6px; background-color:aquamarine;">
    </div>
    <div style="font-size:13px; margin-left: 2px;">
      <div style="color:#8D8D8D; display: flex;">
        {{replyNickName}} | {{replyCreatedDate}}
        <div v-if="loginUserId==props.reply.memberId" @click="deleteComment()" class="word-link" style="margin-left:5px">
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
  reply: Object
})
const replyNickName = ref('')
const replyCreatedDate = ref('')
const loginUserId = jwt_decode(sessionStorage.getItem('token')).sub
const loaded = ref(false)

const createdTime = new Date(props.reply.createDate)
let yy = createdTime.getFullYear()
let mm = createdTime.getMonth()
let dd = createdTime.getDate()
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
</script>

<style>

</style>