<template>
  <div class="rooms-list-page">
    <div class="rooms-list-section">
      <div class="rooms-list-items" v-for="room in rooms" :key="room.id">
        {{ room.name }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { useOnAirStore } from '../store/index.js'

const onairStore = useOnAirStore();
const rooms = onairStore.allRooms

</script>

<style>
.rooms-list-page {
  margin-top: 20px;
}
.rooms-list-section {
  width: 1600px;
  display: flex;
  flex-wrap: wrap;
}
.rooms-list-items {
  width: 380px;
  height: 230px;
  background-color: aqua;
  margin-top: 20px;
  margin-right: 20px;
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
}
</style>