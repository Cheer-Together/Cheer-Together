<template>
  <div class="community-main">
    <div class="write-top">
      <div style="width: 180px;">
        <v-select
        :items="categories"
        v-model="category"
        label="리그 분류"
        density="compact"
        solo
        ></v-select>
      </div>
      <div>
        <input v-model="title" type="text" placeholder=" 제목을 입력하세요." style="width:580px; height:40px; border-radius:5px; border: 1px solid #bcbcbc;">
      </div>
    </div>

    <div class="write-author">
      <div style="width:34px; height:34px; background-color: aquamarine; margin:0 10px 0 10px;">
      </div>

      <a>{{accountStore.token}} | {{ now }}</a>
    </div>
    <div style="width:790px; margin-bottom: 15px;">
      <textarea v-model="content" placeholder="내용을 입력하세요. 우측 하단을 드래그하여 높이를 변경할 수 있습니다." style="width:790px; height:300px; border-radius:5px; border: 1px solid #bcbcbc;"></textarea>
    </div>
    <div class="write-bottom">
      <v-btn @click="communityStore.communityToggle()" class="write-button">취소하기</v-btn>
      <v-btn @click="completeButton()" class="write-button">작성완료</v-btn>
    </div>
  </div>
</template>

<script setup>
import { useAccountStore, useCommunityStore } from "@/store"
import { ref } from 'vue'

const category = ref('분류 없음')
const title = ref('')
const content = ref('')
const communityStore = useCommunityStore()
const accountStore = useAccountStore()
const categories = [
  {title: '분류 없음'},
  {title: '프리미어리그'},
  {title: '라리가'},
  {title: '세리에A'},
  {title: '분데스리가'},
  {title: '리그 1'},
  {title: 'K리그'},
]
const time = new Date();
const now = time.toLocaleString();

// 일단 store에 저장 (나중에 DB에 저장할 예정)
function completeButton() {
  const contents = {
    category,
    title,
    content,
    updated_date: `${time.getHours()}:${time.getMinutes()}`,
    author: accountStore.token,
  }
  communityStore.writeArticle(contents)
  communityStore.communityToggle()
}
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
</style>