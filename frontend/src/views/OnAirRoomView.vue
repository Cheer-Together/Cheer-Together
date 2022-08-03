<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="onair-page">
      <!-- 헤더 -->
      <div class="onair-header">
        <div>
          <h1>프리미어리그</h1>
        </div>
        <div class="onair-header-boxes">
          <div>
            <div>팀명</div>
          </div>

          <div>
            <div>정렬</div>
          </div>

          <button>방만들기</button>
        </div>
      </div>

      <!-- 응원방 목록 -->
      <!-- 1번째 줄 -->
      <v-container
        class="bg-surface-variant mb-6"
      >
        <v-row
          align="start"
          
          style="height: 170px;"
          class="rooms-list"
        >
          <v-col
            v-for="thumbnail in thumbnails.slice(0, 5)"
            :key="thumbnail.id"
          >
            <!-- <v-sheet class="pa-2 ma-2">
              .align-start
            </v-sheet> -->
            <div class="thumbnail">
              {{ thumbnail.name }}
            </div>
          </v-col>
        </v-row>

        <!-- 2번째 줄 -->
        <v-row
          align="start"
          
          style="height: 170px;"
          class="rooms-list"
        >
          <v-col
            v-for="thumbnail in thumbnails.slice(5, 10)"
            :key="thumbnail.id"
          >
            <!-- <v-sheet class="pa-2 ma-2">
              .align-start
            </v-sheet> -->
            <div class="thumbnail">
              {{ thumbnail.name }}
            </div>
          </v-col>
        </v-row>

        <!-- 3번째 줄 -->
        <v-row
          align="start"
          
          style="height: 170px;"
          class="rooms-list"
        >
          <v-col
            v-for="thumbnail in thumbnails.slice(10, 15)"
            :key="thumbnail.id"
          >
            <!-- <v-sheet class="pa-2 ma-2">
              .align-start
            </v-sheet> -->
            <div class="thumbnail">
              {{ thumbnail.name }}
            </div>
          </v-col>
        </v-row>
      </v-container>
      
      <!-- 검색 도구 -->
      <div class="search-bar">
        <input type="text" class="search-bar-input" placeholder=" 검색어를 입력하세요">
        <button>검색</button>
      </div>

      <!-- 페이지네이션 -->
      <v-pagination
        v-model="page"
        :length="5"
      ></v-pagination>

    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
let page = 1
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
.onair-page {
  margin: 100px 0 0 250px;
}

.onair-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 1600px;
  
}

.onair-header-boxes {
  display: flex;
  align-items: center;
  width: 300px;
  justify-content: space-between;
}

/* .onair-header-team{
  width: 50px;
  height: 40px;
  border: 1px solid black;
  border-radius: 3px;
  margin: 0 20px;
  text-align: center;
} */

/* .onair-header-team-text{
  margin-top: 8px;
} */

.onair-header-boxes button{
  width: 100px;
  height: 60px;
  border-radius: 10px;
  margin: 0 20px;
  text-align: center;
  background-color: #BDF9CD;
}
.v-row {
  margin-top: 30px;
}

.thumbnail {
  width: 300px;
  height: 170px;
  background-color: aquamarine;
}

.search-bar {
  margin-top: 35px;
  margin-left: 600px;
  width: 600px;
  height: 60px;
}

.search-bar-input {
  width: 450px;
  border: 1px solid black;
  border-radius: 3px;
}

.search-bar button {
  background-color: #d9d9d9;
}
</style>

