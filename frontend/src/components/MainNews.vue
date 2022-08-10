<template>
  <div class="mainNews">
    <div class="mainNews-title">
      스포츠 뉴스
    </div>
    <div class="mainNews-section">
      <div class="mainNews-item" v-for="news in newsStore.news" :key="news.id">
        <img :src="news.img" alt="" class="mainNews-item-image">

        <div class="mainNews-item-section">
          <div class="mainNews-item-title">
            {{ news.title }}
          </div>
          <div class="mainNews-item-content">
            {{ news.content }}
          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { useNewsStore } from "@/store"

const newsStore = useNewsStore()
newsStore.getNews('축구')
</script>

<style>
.mainNews {
  margin-bottom: 100px;
}
.mainNews-title {
  font-size: 24px;
  padding: 5.5px 0;
}
.mainNews-section {
  margin-top: 11px;
  width: 1580px;
}
.mainNews-item{
  width: 1580px;
  height: 200px;
  margin-bottom: 30px;
  display: flex;
  border-bottom: 1px solid black;
}
.mainNews-item-image {
  width: 300px;
  height: 170px;
  margin-right: 78px;
}
.mainNews-item-title{
  width: 1200px;
  font-size: 20px;
  font-family: 'MICEGothic Bold';
  margin-bottom: 10px;
}
.mainNews-item-content {
  display: -webkit-box;
  width: 1200px;
  height: 135px;
  white-space: normal;
  font-size: 15px;
  -webkit-line-clamp: 6;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;	
  overflow: hidden; 
  word-break: break-all;
}

</style>