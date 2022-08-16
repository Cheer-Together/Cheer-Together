<template>
  <div class="rooms-list-page">
    <div class="rooms-list-section">
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
import { useOnAirStore } from '../store/index.js'

const onairStore = useOnAirStore();
const rooms = onairStore.currentRooms

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