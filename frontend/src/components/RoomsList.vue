<template>
  <div class="rooms-list-page">
    <div class="rooms-list-section">

      <div v-if="isNoRoom" class="room-list-nothing">
        <div class="room-list-nothing-content">
          <p style="margin-top:40px">검색 조건에 해당하는 방이 없습니다</p>
          <p style="margin-top:30px">방을 개설하시면 같이 집관 하실 수 있습니다</p>
        </div>
      </div>

      <div class="rooms-list-section-item" v-for="room in onairStore.currentRooms" :key="room.id" @click="onairStore.enterRoom(room.roomId)">
        <!-- 여기가 썸네일 부분입니다. -->
        <div class="rooms-list-section-thumbnail" :style="{ backgroundImage: 'url(' + popularThumbnail[0] + ')' }">
          <!-- 홈 vs 어웨이  -->
          <!-- 홈 -->
          <div class="rooms-list-section-logo">
            <img :src="room.gameInfo?.home.logo" alt="" width="50" height="50">
            <div>
              {{ room.gameInfo?.home.hanName }} 
            </div>
          </div>

          <!-- VS -->
          <div class="rooms-list-section-logo-vs">
            VS
          </div>
          <!-- 킥오프 -->
          <div>
            
          </div>
          <!-- 어웨이 -->
          <div class="rooms-list-section-logo">
            <img :src="room.gameInfo?.away.logo" alt="" width="50" height="50">
            <div>
              {{ room.gameInfo?.away.hanName }}
            </div>
          </div>
          <!-- 경기장 이름 -->
          <div class="rooms-list-section-stadium">
            {{ room.gameInfo?.stadium }}
          </div>
        </div>
        <div class="rooms-list-section-item-title">
          {{ room.name }} 
          <v-icon style="color:#222222;">
            mdi-account
          </v-icon>
          {{ room.headCount }}
          <span v-if="room.status === 'PRIVATE'" class="private-room"><v-icon>mdi-lock</v-icon></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useOnAirStore } from '../store/index.js'
import { useRoute } from "vue-router"
import { ref } from "vue";

const route = useRoute()
const onairStore = useOnAirStore();

const isNoRoom = ref(false)
if (onairStore.currentRooms.length == 0) {
  isNoRoom.value = true
}

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

if (route.params.leaguename === '모든 응원방 목록') {
  onairStore.moveOnairPage
}
else {
  onairStore.moveLeagueRooms(route.params.leaguename)
}

</script>

<style>
.rooms-list-page {
  margin-top: 20px;
}
.rooms-list-section {
  width: 1600px;
  display: flex;
  flex-wrap: wrap;
  margin-top: 30px;
  margin-bottom: 100px;
}

.room-item-title {
  position: relative;
  top: 195px;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
}
.private-room {
  text-align: end;
}
.room-list-nothing {
  width: 100%;
  height: 200px;
  background-color: var(--sub-color);
}
.room-list-nothing-content{
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  font-size: 24px;
}
.room-list-nothing-content{
    font-size: 20px;
  }

/* 여기부터 썸네일 css */
.rooms-list-section-item {
  width: 375px;
  height: 270px;
  margin: 0 20px 35px 0 ;
  border-radius: 10px;
  overflow: hidden;
  text-align: center;
}
.rooms-list-section-item:hover {
  opacity: 0.5;
  cursor: pointer;
}
.rooms-list-section-thumbnail {
  width: 375px;
  height: 230px;
  position: relative;
  padding: 0 auto;
  border: 1px solid #ecf0f5;
  background-size: cover;
}
.rooms-list-section-logo {
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
.rooms-list-section-logo:first-child {
  background-color:rgba(121, 4, 4, 0.8);
  left: 87px;

}
.rooms-list-section-logo-vs {
  position: absolute;
  width: 25px;
  top: 55px;
  right: 181px;
  font-size: 28px;
  font-family: var(--bold-font);
  z-index: 2;
  color: #ffffff;
}
.rooms-list-section-stadium {
  position: absolute;
  width: 200px;
  top: 135px;
  right: 87px;
  font-size: 16px;
  font-family: var(--bold-font);
  color: #ffffff;
}
.rooms-list-section-item-title {
  font-size: 24px;
  /* font-weight: bold; */
  width: 375px;
  height: 24px;
  padding: 2px 0 0 10px;
  text-align: start;
}
@media (max-width: 1580px) {
  .rooms-list-page {
    margin-top: 0;
  }
  .rooms-list-section {
    width: 1300px;
  }
  .room-list-nothing-content{
    font-size: 20px;
  }
  .rooms-list-items {
    width: 300px;
    height: 182px;
    background-color: aqua;
    margin-top: 12px;
    margin-right: 25px;
  }
  .room-item-title {
    position: relative;
    top: 155px;
    text-align: center;
    font-weight: bold;
    font-size: 16px;
  }
  .private-room {
    position: relative;
    left: 273px;
    bottom: 22px;
  }
}
</style>