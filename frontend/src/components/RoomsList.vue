<template>
  <div class="rooms-list-page">
    <div class="rooms-list-section">
      <div class="rooms-list-items" v-for="thumbnail in thumbnails" :key="thumbnail.id">
        {{ thumbnail.name }}
      </div>
    </div>
  </div>
</template>

<script setup>
const thumbnails = [
  {id: '1', name : 'thumbnail-1'},
  {id: '2', name : 'thumbnail-2'},
  {id: '3', name : 'thumbnail-3'},
  {id: '4', name : 'thumbnail-4'},
  {id: '5', name : 'thumbnail-5'},
  {id: '6', name : 'thumbnail-6'},
  {id: '7', name : 'thumbnail-7'},
  {id: '8', name : 'thumbnail-8'},
  {id: '9', name : 'thumbnail-9'},
  {id: '10', name : 'thumbnail-10'},
  {id: '11', name : 'thumbnail-11'},
  {id: '12', name : 'thumbnail-12'},
  {id: '13', name : 'thumbnail-13'},
  {id: '14', name : 'thumbnail-14'},
  {id: '15', name : 'thumbnail-15'},
  {id: '16', name : 'thumbnail-16'},
  {id: '17', name : 'thumbnail-17'},
  {id: '18', name : 'thumbnail-18'},
  {id: '19', name : 'thumbnail-19'},
  {id: '20', name : 'thumbnail-20'},
  
]
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
  height: 205px;
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
    height: 160px;
    background-color: aqua;
    margin-top: 12px;
    margin-right: 25px;
  }
}
</style>