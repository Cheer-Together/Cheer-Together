<template>
  <div v-if="!toggle" class="community-main">
    <div style="display:flex; justify-content: space-between; height:60px">
      <div style="width:200px;">
        <v-select
        :items="items"
        label="리그 분류"
        density="compact"
        solo
        ></v-select>
      </div>
      <div>
        <v-btn @click="communityStore.communityToggle()" style="height:40px; margin-left:20px">글 작성하기</v-btn>
      </div>
    </div>
    <div style="display:flex; height:38px; margin:0; text-align: center; align-items: center; border-bottom: 1px solid #bcbcbc;">
      <div v-for="header in headers" :key="header.name" :style="header.style">
        {{header.name}}
      </div>
    </div>

    <div v-for="article in communityStore.articles" :key="article.article_id" style="display:flex; height:36px; margin:0; text-align: center; align-items: center;">
      <div style="width:100px">
        <p>{{article.category}}</p>
      </div>
      <div class="article-list" style="width:380px">
        <p>{{article.title}}</p>
      </div>
      <div style="width:100px">
        <p>{{article.updated_date}}</p>
      </div>
      <div style="width:130px">
        <p>{{article.author}}</p>
      </div>
      <div style="width:80px">
        <p>{{article.recommended}}</p>
      </div>
    </div>

    <div style="display:flex; text-align: center; align-items: center; width:600px; height:36px; margin: 20px auto;">
      <input type="text" size="34px" maxlength="30" placeholder=" 검색할 내용을 입력하세요." style="width:480px; height:34px; border-radius:5px; border: 1px solid #bcbcbc;">
      <v-btn style="height:34px; margin-left:20px">검색</v-btn>
    </div>
    <div style="width:280px; height:32px; margin: 10px auto;">
      <p>페이지네이션</p>
    </div>
  </div>
</template>

<script setup>
import { useCommunityStore } from "@/store"

const communityStore = useCommunityStore()

const items = [
  {title: '전체보기'},
  {title: '프리미어리그'},
  {title: '라리가'},
  {title: '세리에A'},
  {title: '분데스리가'},
  {title: '리그 1'},
  {title: 'K리그'},
]
const headers = [
  {
    name: '리그',
    style: {width: '100px'},
  },
  {
    name: '제목',
    style: {width:'380px'},
  },
  {
    name: '등록일',
    style: {width:'100px'},
  },
  {
    name: '작성자',
    style: {width:'130px'},
  },
  {
    name: '추천수',
    style: {width:'80px'},
  },
]
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