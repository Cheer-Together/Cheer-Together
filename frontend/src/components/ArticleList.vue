<template>
  <div class="community-main">
    <div class="community-list-top">
      <div style="width:200px;">
        <v-select
        :items="items"
        label="리그 분류"
        density="compact"
        solo
        ></v-select>
      </div>
      <div>
        <v-btn @click="writeButton()" style="height:40px; margin-left:20px">글 작성하기</v-btn>
      </div>
    </div>
    <div class="community-list-header">
      <div v-for="header in headers" :key="header.name" :style="header.style">
        {{header.name}}
      </div>
    </div>

    <div v-for="article in pagedArticles" :key="article.article_id" class="community-list-articles">
      <div style="width:100px">
        <p>{{article.category}}</p>
      </div>
      <div @click="toArticleDetail(article.article_id)" class="article-list" style="width:380px">
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

    <div class="community-list-search">
      <input type="text" placeholder=" 검색할 내용을 입력하세요." class="community-list-searchbar">
      <v-btn style="height:34px; margin-left:20px">검색</v-btn>
    </div>
    <div>
      <v-pagination
        v-model="page"
        :length="pageLength"
        rounded="circle"
      ></v-pagination>
    </div>
    {{decoded}}
  </div>
</template>

<script setup>
import { useAccountStore, useCommunityStore } from "@/store"
import { ref, watchEffect } from "vue"
import router from "@/router"
import jwt_decode from "jwt-decode"
const communityStore = useCommunityStore()
const accountStore = useAccountStore()
const { articles } = communityStore
const toke = ref(sessionStorage.getItem('token')??'')
const decoded = ref('')
if (toke.value) {
  decoded.value = jwt_decode(toke.value)
}

// 페이지네이션
const page = ref(1)
const pageLength = ref(parseInt((articles.length-1)/15)+1)
const pagedArticles = ref(articles.filter(article => article.article_id < 15))
watchEffect(() => {
    pagedArticles.value = articles.filter(article => {if (article.article_id >= 15*(page.value-1)) {if (article.article_id < 15*page.value) {return true}}})
})
function writeButton() {
  if (accountStore.isLogin) {
    communityStore.communityToggle()
  } else {
    accountStore.loginDialogToggle()
  }
}

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
function toArticleDetail(id) {
  router.push({name: 'ArticleDetail', params: { articleid: id }})
}
</script>

<style>
.community-main {
  width: 790px;
  margin-top: 140px;
  margin-left: 300px;
}
.community-list-top {
  display:flex;
  justify-content: space-between;
  height:60px;
}
.community-list-header {
  display:flex;
  height:38px;
  margin:0;
  text-align: center;
  align-items: center;
  border-bottom: 1px solid #bcbcbc;
}
.community-list-articles {
  display:flex;
  height:36px;
  margin:0;
  text-align: center;
  align-items: center;
}
.community-list-search {
  display:flex;
  text-align: center;
  align-items: center;
  width:600px;
  height:36px;
  margin: 20px auto;
}
.community-list-searchbar {
  width:480px;
  height:34px;
  border-radius:5px;
  border: 1px solid #bcbcbc;
}
@media (max-width: 1580px) {
  .community-main {
    margin-top: 120px;
    margin-left: 200px;
  }
}
</style>