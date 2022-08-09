<template>
  <div class="community-main">
    <div class="write-top">
      <div style="width: 180px;">
        <v-select
        v-model="category"
        :items="items"
        item-title="title"
        item-value="apiId"
        label="리그 분류"
        density="compact"
        solo
        ></v-select>
      </div>
      <div>
        <input v-model="title" type="text" placeholder=" 제목을 입력하세요." class="write-title">
      </div>
    </div>

    <div class="write-author">
      <div style="width:34px; height:34px; background-color: aquamarine; margin:0 10px 0 10px;">
      </div>
      <a>{{accountStore.token}} | {{ now }}</a>
    </div>
    <div style="width:790px; margin-bottom: 15px;">
      <textarea v-model="content" placeholder="내용을 입력하세요. 우측 하단을 드래그하여 높이를 변경할 수 있습니다." class="write-content"></textarea>
    </div>
    <div class="write-bottom">
      <v-btn @click="communityStore.communityToggle()" class="write-button">취소하기</v-btn>
      <v-btn @click="completeButton()" class="write-button">작성완료</v-btn>
    </div>
  </div>
</template>

<script setup>
import router from "@/router"
import { useAccountStore, useCommunityStore } from "@/store"
import axios from "axios"
import { ref } from 'vue'
const category = ref('분류 없음')
const title = ref('')
const content = ref('')
const communityStore = useCommunityStore()
const accountStore = useAccountStore()
const items = [
  {title: '분류 없음', apiId: false},
  {title: '프리미어리그', apiId: 39},
  {title: '라리가', apiId: 140},
  {title: '세리에A', apiId: 135},
  {title: '분데스리가', apiId: 78},
  {title: '리그 1', apiId: 61},
  {title: 'K리그', apiId: 292},
]
const time = new Date();
const now = time.toLocaleString();
function completeButton() {
  axios({
    url: "https://i7b204.p.ssafy.io/cheertogether/articles",
    method: 'POST',
    data: {
      content: content.value,
      leagueApiId: category.value,
      title: title.value,
    },
    params: {
      jwtToken: sessionStorage.getItem('token')
    }
  })
  .then(res => {
    console.log(res.data)
    router.push({name:'Article'})
  })
  .catch(err => {
    console.log(err)
  })
  // const contents = {
  //   category,
  //   title,
  //   content,
  //   updated_date: `${time.getHours()}:${time.getMinutes()}`,
  //   author: '작성자 이름'
  // }
  // communityStore.writeArticle(contents)
  communityStore.communityToggle()
}
</script>

<style>
.write-top {
  display: flex;
  justify-content: space-between;
  width:790px;
  height:40px;
  margin-bottom: 15px;
}
.write-author {
  display: flex;
  align-items: center;
  width:790px;
  height:36px;
  margin-bottom: 15px;
  font-size: 13px
}
.write-bottom {
  display: flex; 
  justify-content: flex-end;
}
.write-button {
  height:34px; 
  margin:0 10px 0 10px;
}
.write-title {
  width:580px;
  height:40px;
  border-radius:5px;
  border: 1px solid #bcbcbc;
}
.write-content {
  width:790px;
  height:300px;
  border-radius:5px;
  border: 1px solid #bcbcbc;
}
</style>