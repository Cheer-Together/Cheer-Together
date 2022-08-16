<template>
  <div class="rooms-list-page">
    <div class="rooms-list-section">
      <div v-if="isNoRoom" class="room-list-nothing">
        <div class="room-list-nothing-content">
          <p style="margin-top:40px">검색 조건에 해당하는 방이 없습니다</p>
          <p style="margin-top:30px">방을 개설하시면 같이 집관 하실 수 있습니다</p>
        </div>
      </div>
      <div class="rooms-list-items" v-for="room in rooms" :key="room.id" @click="onairStore.enterRoom(room.roomId)">
        <div class="room-item-title">{{ room.name }}</div>
        <!-- <div>
          <img :src="room.home.logo" alt="">
          <img :src="room.away.logo" alt="">
        </div> -->
        <div v-if="room.status === 'PRIVATE'" class="private-room"><v-icon>mdi-lock</v-icon></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useOnAirStore } from '../store/index.js'

const onairStore = useOnAirStore();
const rooms = onairStore.currentRooms
const isNoRoom = ref(false)
if (rooms.length==0) {
  isNoRoom.value = true
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
.rooms-list-items {
  width: 380px;
  height: 230px;
  background-color: aqua;
  margin-top: 20px;
  margin-right: 20px;
  cursor: pointer;
  border-radius: 5px;
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
.room-item-title {
  position: relative;
  top: 195px;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
}
.private-room {
  position: relative;
  left: 350px;
  bottom: 24px;
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