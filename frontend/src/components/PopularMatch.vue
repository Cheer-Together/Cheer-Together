<template>
  <div class="popularMatch">
    <div class="popularMatch-title">
      실시간 인기 응원방 📣
    </div>
    <div class="popularMatch-section">
      <!-- 첫 번째 -->

      <div class="popularMatch-section-item" v-for="(room, i) in roomStore.popularRooms" :key="room.roomId">
        <!-- 여기가 썸네일 부분입니다. -->
        <div @click="onairStore.enterRoom(roomIds[i])" class="popularMatch-section-thumbnail" :style="{ backgroundImage: 'url(' + popularThumbnail[i] + ')' }">
          <!-- 홈 vs 어웨이  -->
          <!-- 홈 -->
          <div class="popularMatch-section-logo">
            <img :src="room.gameInfo?.home.logo" alt="" width="50" height="50">
            <div>
              {{ room.gameInfo?.home.hanName }}
            </div>
          </div>

          <!-- VS -->
          <div class="popularMatch-section-logo-vs">
            VS
          </div>
          <!-- 킥오프 -->
          <div>
            
          </div>
          <!-- 어웨이 -->
          <div class="popularMatch-section-logo">
            <img :src="room.gameInfo?.away.logo" alt="" width="50" height="50">
            <div>
              {{ room.gameInfo?.away.hanName }}
            </div>
          </div>
          <!-- 경기장 이름 -->
          <div class="popularMatch-section-stadium">
            {{ room.gameInfo?.stadium }}
          </div>
        </div>
        <div class="popularMatch-section-item-title">
          {{ room.name }} 
          <v-icon style="color:#222222;">
            mdi-account
          </v-icon>
          {{ room.headCount }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// import router from '@/router'
import { useOnAirStore, useRoomStore } from "@/store"
import axios from "axios"
import { ref } from "vue";
const onairStore = useOnAirStore()
const roomStore = useRoomStore()
const roomIds = ref([])
let roomIdList = []
axios({
  url: "https://i7b204.p.ssafy.io/cheertogether/rooms/popular",
  method: 'GET',
}).then(res =>{
  res.data.forEach((room) => {
    roomIdList.push(room.roomId)    
  })
  roomIds.value = roomIdList
}).catch(err => {
  console.log(err)
})

const popularThumbnail = [
  "https://media.api-sports.io/football/venues/556.png",
  "https://media.api-sports.io/football/venues/550.png",
  "https://media.api-sports.io/football/venues/494.png",
  "https://media.api-sports.io/football/venues/519.png",
  "https://media.api-sports.io/football/venues/555.png",
  "https://media.api-sports.io/football/venues/525.png",
  "https://media.api-sports.io/football/venues/495.png",
  "https://media.api-sports.io/football/venues/566.png",
  "https://media.api-sports.io/football/venues/546.png",
]

roomStore.getPopularRooms()
</script>

<style>
.popularMatch {
  margin-bottom: 40px;
}
.popularMatch-title {
  font-size: 30px;
  padding: 5.5px 0;
  font-family: var(--bold-font);
}
.popularMatch-section {
  margin-top: 11px;
  width: 1580px;
  min-height: 240px;
  max-height: 480px;
  text-align: center;
  display: flex;
  flex-wrap: wrap;
}
/* 여기부터 썸네일 css */
.popularMatch-section-item {
  width: 375px;
  height: 260px;
  margin: 0 20px 20px 0 ;
  border-radius: 10px;
  overflow: hidden;
}
.popularMatch-section-item:hover {
  cursor: pointer;
  opacity: 0.5;
}
.popularMatch-section-thumbnail {
  width: 375px;
  height: 230px;
  position: relative;
  padding: 0 auto;
  border: 1px solid #ecf0f5;
  background-size: cover;
}
.popularMatch-section-logo {
  width: 100px;
  height: 170px;
  background-color:rgba(4, 84, 121, 0.8);
  position: absolute;
  top: 20px;
  right: 88px;
  padding: 25px 0 0 0;
  color: white;
  font-size: 12px;
}
.popularMatch-section-logo:first-child {
  background-color:rgba(121, 4, 4, 0.8);
  left: 87px;

}
.popularMatch-section-logo-vs {
  position: absolute;
  width: 25px;
  top: 55px;
  right: 181px;
  font-size: 28px;
  font-family: var(--bold-font);
  z-index: 2;
  color: #ffffff;
}
.popularMatch-section-stadium {
  position: absolute;
  width: 200px;
  top: 135px;
  right: 87px;
  font-size: 16px;
  font-family: var(--bold-font);
  color: #ffffff;
}
.popularMatch-section-item-title {
  font-size: 16px;
  width: 375px;
  height: 24px;
  padding: 2px 0 0 10px;
  text-align: start;
}
@media (max-width: 1580px) {
.popularMatch {
  margin-bottom: 16px;
}
.popularMatch-title {
  font-size: 25px;
}
.popularMatch-section {
  margin-top: 8px;
  width: 1300px;
  height: 150px;
  overflow-x: hidden;
}
.popularMatch-section-title {
  padding-top: 78px ;
}
.popularMatch-section-item {
  width: 248px;
  height: 150px;
  margin-left: 15px;
}
.popularMatch-section-item-image{
  height: 130px;
}
.popularMatch-section-item-image img {
  width: 248px;
  height: 130px;
}
.popularMatch-section-item-title {
  font-size: 12px;
  width: 248px;
  height: 20px;
  background-color: #222222;
  color: #ffffff;
}
}
</style>