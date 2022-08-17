<template>
  <div class="community-side">
    <div class="community-news-top">
      <p>오늘의 뉴스</p>
    </div>
    <div v-for="item in news" :key="item.id" class="community-news">
      <div class="maincolor" style="width:24px; text-align: center;">
        <p>{{item.id+1}}</p>
      </div>
      <div style="display: inline-block;">
        <p @click=articleBtn(item.link) class="article-list">{{item.title}}</p>
      </div>
    </div>

    <div class="community-matches-top">
      <p style="font-size: 20px; font-family: 'MICEGothic Bold';">오늘의 경기</p>
      <p class="word-link" @click="onairStore.moveOnairPage" style="font-size: 14px;">자세히 보기</p>
    </div>
    <div v-if="(todayMatches.length==0)" class="community-matches-nothing">
      <a style="margin-top:45px">오늘 경기가 없습니다.</a>
    </div>
    <div v-for="match in todayMatches" :key="match.id" class="community-matches">
      <div class="community-matches-teamname" style="justify-content: flex-end;">
        <a>
          {{match.teams[0]}}
        </a>
      </div>
      <div style="width:40px; height:40px;">
        <img :src="match.logos[0]" style="height:40px; width:40px;">
      </div>
      <div style="width:50px; height:60px;">
        <div class="community-matches-time">
          {{match.time}}
        </div>
        <div class="community-matches-versus">
          <p>vs</p>
        </div>
      </div>
      <div style="width:40px; height:40px;">
        <img :src="match.logos[1]" style="height:40px; width:40px;">
      </div>
      <div class="community-matches-teamname">
        <a>
          {{match.teams[1]}}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useOnAirStore } from '@/store'
import axios from 'axios'
import { ref } from "vue"
const onairStore = useOnAirStore()
const news = ref([])
const todayMatches = ref({})
let loadnews = []


const now = new Date()
let yyyy = now.getFullYear()
let mm = now.getMonth()+1
if (mm < 10) {mm = '0' + mm}
let dd = now.getDate()
if (dd < 10) {dd = '0' + dd}
const DD = dd.toLocaleString()
const yyyymmdd = yyyy+mm+DD
let matches = []
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/games/date',
  method: 'GET',
  params: {
    date : yyyymmdd
  }
})
.then(res => {
  let matchHour = 0
  let matchMinute = 0
  let matchDate = new Date()
  res.data.forEach((match) => {
    matchDate = new Date(match.kickoff)
    matchHour = matchDate.getHours()
    matchMinute = matchDate.getMinutes()
    if (matchMinute < 10) {matchMinute = '0' + matchMinute}
    matches.push({
      id: match.id,
      teams:[match.home.hanName, match.away.hanName],
      logos:[match.home.logo, match.away.logo],
      time: matchHour + ':' + matchMinute,
    }) 
  })
  todayMatches.value = matches.slice(0, 4)
})
.catch(err => {
  console.log(err)
})

function articleBtn(link) {
  window.open(link)
}
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/news',
  method: 'GET',
  params: {
    subject : '프리미어리그 세리에 라리가'
  }
})
.then(res => {
  let id = 0
  res.data.forEach((e) => {
    let title = e.title
    title = title.replaceAll('&apos;', "'")
    title = title.replaceAll('&quot;', '"')
    loadnews.push({link: e.link, title: title, id: id++})
  })
  news.value = loadnews
})
  .catch(err => {
    console.log(err)
})
</script>

<style>
.community-side {
  width: 350px;
  margin-left: 80px;
}
.article-list { 
  text-overflow: ellipsis; 
  white-space: nowrap; 
  overflow: hidden; 
  width: 320px;
}
.article-list:hover {
  color: var(--main-color);
  cursor:pointer
}
.community-news-top {
  height:30px; 
  border-bottom: 1px solid #bcbcbc; 
  font-size: 20px;
  font-family: 'MICEGothic Bold';
}
.community-matches-top {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin:60px 0 20px;
  height:30px;
  border-bottom: 1px solid #bcbcbc;
}
.community-news {
  display:flex; 
  height:30px; 
  align-items: center; 
  font-size: 14px;
}
.community-matches {
  display:flex; 
  width:350px; 
  height:60px;
}
.community-matches-nothing {
  display:flex; 
  flex-direction: column;
  font-size: 18px;
  width:350px; 
  height:120px;
  background-color: var( --sub-color );
  align-items: center;
}
.community-matches-teamname {
  display:flex; 
  width:110px; 
  height:40px; 
  font-size: 11px; 
  align-items: center; 
}
.community-matches-time {
  display:flex;
  justify-content: center;
  height:10px;
  font-size: 10px;
}
.community-matches-versus {
  display:flex;
  justify-content: center;
  height:50px;
}
.word-link:hover {
  color: var(--main-color);
  cursor: pointer
}
</style>