<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="schedule-page">
      <div class="schedule-page-leagues">
        <div class="schedule-page-league-items">
          <p class="league-active">프리미어리그</p>
        </div>

        <div class="schedule-page-league-items">
          <p>라리가</p>
        </div>
        
        <div class="schedule-page-league-items">
          <p>세리에 A</p>
        </div>

        <div class="schedule-page-league-items">
          <p>분데스리가</p>
        </div>

        <div class="schedule-page-league-items">
          <p>리그 1</p>
        </div>

        <div class="schedule-page-league-items">
          <p>K리그</p>
        </div>
      </div>

      <div class="schedule-page-select">
        <h1 class="schedule-page-season-select">
          22/23 시즌
        </h1>
        <div class="schedule-page-month-select">
          <div class="schedule-page-month-item">
            <p class="item-active">22년 8월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>9월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>10월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>11월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>12월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>23년 1월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>2월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>3월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>4월</p>
          </div>
          <div class="schedule-page-month-item">
            <p>5월</p>
          </div>
        </div>
      </div>

      <div class="schedule-list">
        <div class="schedule-list-title">
          <div class="schedule-date">
            <p>날짜</p>
          </div>
          <div class="schedule-time">
            <p>시간</p>
          </div>
          <div class="schedule-stadium">
            <p>장소</p>
          </div>
          <div class="schedule-matches">
            <p>경기</p>
          </div>
        </div>

        <div class="schedule-list-items">
          <div class="schedule-date">
            <p>8월 1일</p>
          </div>
          <div class="schedule-time">
            <p>00:30</p>
          </div>
          <div class="schedule-stadium">
            <p>Selhurst Park</p>
          </div>
          <div class="schedule-matches">
            <p>팰리스 VS 아스널</p>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
</script>

<style>
.schedule-page{
  margin-top: 50px;
  margin-left: 65px;
}

.schedule-page-leagues {
  display: flex;
  width: 1000px;
  height: 50px;
  background-color: var(--sub-color);
  
}

.schedule-page-league-items{
  margin-left: 65px;
  margin-top: 8px;
  font-size: 25px;
}

.schedule-page-season-select {
  margin-top: 40px;
  margin-left: 400px;
}

.schedule-page-month-select {
  display: flex;
  margin-left: 100px;
}

.schedule-page-month-item {
  margin: 5px 40px 40px 5px;
}

.schedule-page-month-item .item-active {
  color: #2E6AFD
}

.league-active {
  color: #2E6AFD
}

.schedule-list-title {
  display: flex;
  border-bottom: 1px solid #bcbcbc
}


.schedule-list-items {
  display: flex;
  margin-top: 5px;
}

.schedule-date {
  width: 140px;
  text-align: center;
}

.schedule-time {
  width: 80px;
  text-align: center;
}

.schedule-stadium {
  width: 230px;
  text-align: center;
}

.schedule-matches {
  width: 700px;
  text-align: center;
}

</style>