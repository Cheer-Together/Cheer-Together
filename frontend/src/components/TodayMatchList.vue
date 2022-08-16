<template>
  <div class="todayMatchList">
    <div class="todayMatchList-title-button">
      <div class="todayMatchList-title">경기 일정</div>
      <div v-if="isActiveCarousel">
        <button @click="moveCarousel(1)"><v-icon> mdi-checkbox-blank-circle</v-icon></button>
        <button @click="moveCarousel(2)"><v-icon> mdi-checkbox-blank-circle-outline</v-icon></button>
      </div>

      <div v-if="!isActiveCarousel">
        <button @click="moveCarousel(1)"><v-icon> mdi-checkbox-blank-circle-outline</v-icon></button>
        <button @click="moveCarousel(2)"><v-icon> mdi-checkbox-blank-circle</v-icon></button>
      </div>
    </div>
    <div class="todayMatchList-section">
      <!-- 리그 목록-->
      <div class="todayMatchList-section-item" v-for="(league, i) in leagueStore.leaguesNoTeam" :key="league.apiId" :style="{ transform: moveCarouselValue }">
        <!-- 리그 이름 + 더보기 있는 영역 -->
        <div class="todayMatchList-item-header">
          <div class="todayMatchList-section-title">
            {{ league.hanName }}
          </div>
          <div class="todayMatchList-section-etc">
            더보기
            <v-icon style="width: 10px; font-size: 20px"> mdi-chevron-right </v-icon>
          </div>
        </div>

        <!-- 경기 일정 영역 -->
        <div class="todayMatchList-item-section">
          <!-- 상단 하늘색 영역 -->
          <div class="todayMatchList-item-section-header">
            <div>
              <v-icon @click="gameStore.clickPreDate(i)"> mdi-chevron-left </v-icon>
              {{ gameStore.month[i] }}.{{ gameStore.date[i] }} <span style="font-size: 15px">({{ gameStore.day[i] }})</span>
              <v-icon @click="gameStore.clickNextDate(i)"> mdi-chevron-right </v-icon>
            </div>
          </div>

          <!-- 내부 경기 상세 내용 -->
          <div v-for="match in gameStore.todayGames[i]" :key="match.id">
            <div v-if="match != {}" class="todayMatchList-item-match">
              <div class="todayMatchList-item-match-team1">
                <div style="margin-right: 5px">
                  {{ match.home.hanName }}
                </div>
                <img :src="match.home.logo" width="25" />
              </div>

              <div class="todayMatchList-item-match-versus">
                <div>vs</div>

                <div style="color: grey">
                  {{ match.kickoff.substring(11, 13) }}
                  :
                  {{ match.kickoff.substring(14, 16) }}
                </div>
              </div>

              <div class="todayMatchList-item-match-team2">
                <img :src="match.away.logo" width="25" />
                <div style="margin-left: 5px">
                  {{ match.away.hanName }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useGameStore, useLeagueStore } from "@/store";

const gameStore = useGameStore();
const leagueStore = useLeagueStore();
for(let i = 0 ; i < 6 ; i++) {
  gameStore.month[i] = gameStore.today[i].getMonth() + 1;
  gameStore.date[i] = gameStore.today[i].getDate();
  gameStore.day[i] = gameStore.dayName[gameStore.today[i].getDay()];
}
// let month = today.getMonth() + 1;  // 월
// let date = today.getDate();  // 날짜
// let day = today.getDay();  // 요일


// let preDate = new Date(today);

// if (0 < month < 10) {
//   month = '0' + month
// }
// if (0 < date < 10) {
//   date = '0' + date
// }

// 캐러셀 함수 및 변수
let isActiveCarousel = ref(true);
let moveCarouselValue = ref("translateX(0)");
const moveCarousel = (dir) => {
  if (dir === 1) {
    moveCarouselValue.value = "translateX(0)";
    isActiveCarousel.value = true;
  } else {
    moveCarouselValue.value = "translateX(-1599px)";
    isActiveCarousel.value = false;
  }
};



//이동 함수
</script>

<style>
.todayMatchList {
  margin-bottom: 50px;
}
.todayMatchList-title-button {
  display: flex;
  justify-content: space-between;
}
.todayMatchList-title-button div {
  margin-right: 10px;
}
.todayMatchList-title {
  font-size: 24px;
  padding: 5.5px 0;
  font-family: var(--bold-font);
}
.todayMatchList-section {
  margin-top: 11px;
  width: 1580px;
  height: 400px;
  display: flex;
  font-size: 15px;
  flex-wrap: nowrap;
  overflow: hidden;
}
.todayMatchList-section-item {
  width: 513px;
  height: 388px;
  margin-right: 20px;
  transition-duration: 0.3s;
}
.todayMatchList-item-header {
  display: flex;
  justify-content: space-between;
}
.todayMatchList-section-title {
  height: 30px;
  margin: 10px 0;
  font-size: 20px;
}
.todayMatchList-section-etc {
  height: 30px;
  margin: 10px 0;
  font-size: 16px;
  padding: 4px 2px 0 0;
}
.todayMatchList-item-section {
  width: 513px;
  height: 350px;
  border: 1px solid var(--card-color);
  border-radius: 5px;
  overflow: hidden;
}
.todayMatchList-item-section-header {
  width: 511px;
  border-bottom: 1px solid var(--card-color);
  background-color: var(--sub-color);
  height: 60px;
  text-align: center;
  font-size: 20px;
}
.todayMatchList-item-section-header > div {
  padding-top: 13.25px;
}
.todayMatchList-item-match {
  margin: 10px 0;
  display: flex;
  align-items: center;
  text-align: center;
}

.todayMatchList-item-match-team1 {
  display: flex;
  width: 200px;
  height: 25px;
  justify-content: flex-end;
  align-items: center;
}
.todayMatchList-item-match-versus {
  width: 113px;
}
.todayMatchList-item-match-team2 {
  display: flex;
  width: 200px;
  height: 25px;
  justify-content: flex-start;
  align-items: center;
}
.todayMatchList-item-match-none {
  text-align: center;
  margin-top: 10px;
}
@media (max-width: 1580px) {
  .todayMatchList {
    margin-bottom: 16px;
  }
  .todayMatchList-title {
    font-size: 15px;
  }
  .todayMatchList-section {
    margin-top: 8px;
    width: 1300px;
    font-size: 15px;
  }
  .todayMatchList-item-section {
    width: 420px;
  }
  .todayMatchList-item-section-header {
    width: 418px;
  }
  .todayMatchList-item-match {
    font-size: 12px;
  }
}
</style>
