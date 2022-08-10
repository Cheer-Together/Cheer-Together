<template>
  <div style="display: flex; height: 40px; margin-bottom: 5px;">
    <div style="height: 40px; width:40px; margin-right: 6px; background-color:aquamarine;">
    </div>
    <div style="font-size:13px">
      <div style="color:#8D8D8D">
        {{replyNickName}} | {{replyCreatedDate}}
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
const props = defineProps({
  reply: Object
})
const replyNickName = ref('')
const replyCreatedDate = ref('')
const createdTime = new Date(props.reply.createDate)
replyCreatedDate.value = createdTime.getFullYear() +'/'+ createdTime.getMonth() +'/'+ createdTime.getDate() + ' ' + createdTime.getHours() + ":" + createdTime.getMinutes()
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/members/' + props.reply.memberId,
  method: 'GET', 
}).then(res => {
  console.log(res)
  replyNickName.value = res.data.nickname
}).catch(err => {
  console.log(err)
})
</script>

<style>

</style>