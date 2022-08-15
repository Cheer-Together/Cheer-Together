<template>
  <div class="community-main">
    <div class="community-list-top">
      <div style="width:200px;">
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
        <v-btn @click="writeButton()" style="height:40px; margin-left:20px">글 작성하기</v-btn>
      </div>
    </div>
    <div class="community-list-header">
      <div v-for="header in headers" :key="header.name" :style="header.style">
        {{header.name}}
      </div>
    </div>
    <ArticleListItem v-for="article in pagedArticles" :key="article.id" :article="article"/>
    <div class="community-list-search">
      <input v-model="searchString" type="text" placeholder=" 검색할 내용을 입력하세요." class="community-list-searchbar">
      <v-btn @click="searchBtn" style="height:34px; margin-left:20px">검색</v-btn>
    </div>
    <div>
      <v-pagination
        v-model="page"
        :length="pageLength"
        rounded="circle"
      ></v-pagination>
    </div>
  </div>
</template>

<script setup>
import ArticleListItem from "./ArticleListItem.vue"
import { useAccountStore, useCommunityStore } from "@/store"
import { ref, watch } from "vue"
import axios from "axios";
const communityStore = useCommunityStore()
const accountStore = useAccountStore()
const page = ref(1)
const pageLength = ref(1)
const articles = ref({})
const pagedArticles = ref({})
const category = ref('')
const searchString = ref('')
function writeButton() {
  if (accountStore.isLogin) {
    communityStore.communityToggle()
  } else {
    accountStore.loginDialogToggle()
  }
}
function searchBtn() {
  axios({
    url: 'https://i7b204.p.ssafy.io/cheertogether/articles/search',
    method: 'GET',
    params: {
      keyword: searchString.value,
    }  
  }).then(res => {
    articles.value = res.data
    searchString.value = ''
    pagedArticles.value = articles.value.slice(0, 15)
    pageLength.value = parseInt((articles.value.length-1)/15)+1
  }).catch(err => {
    console.log(err)
  })
}
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/articles',
  method: 'GET', 
}).then(res => {
  articles.value = res.data
  pagedArticles.value = articles.value.slice(0, 15)
  pageLength.value = parseInt((articles.value.length-1)/15)+1
}).catch(err => {
  console.log(err)
})

//리그 분류
watch(category, () => {
  //category.value가 false(전체보기)인 경우, 모든 게시글을 가져옴
  if (category.value) {
    axios({
      url: 'https://i7b204.p.ssafy.io/cheertogether/articles/header/' + category.value,
      method: 'GET', 
    }).then(res => {
      articles.value = res.data
      page.value = 1
      pagedArticles.value = articles.value.slice(0, 15)
      pageLength.value = parseInt((articles.value.length-1)/15)+1
    }).catch(err => {
      console.log(err)
  })} else {
    axios({
      url: 'https://i7b204.p.ssafy.io/cheertogether/articles',
      method: 'GET', 
    }).then(res => {
      articles.value = res.data
      page.value = 1
      pagedArticles.value = articles.value.slice(0, 15)
      pageLength.value = parseInt((articles.value.length-1)/15)+1
    }).catch(err => {
      console.log(err)
    })
  }
})
watch(page, () => {
  pagedArticles.value = articles.value.slice(15*(page.value-1), 15*page.value)
})

const items = [
  {title: '전체보기', apiId: false},
  {title: '프리미어리그', apiId: 39},
  {title: '라리가', apiId: 140},
  {title: '세리에A', apiId: 135},
  {title: '분데스리가', apiId: 78},
  {title: '리그 1', apiId: 61},
  {title: 'K리그', apiId: 292},
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
</style>