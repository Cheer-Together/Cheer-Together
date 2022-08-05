<template>
  <v-card class="favorite-team-modal">
    <!-- 헤더 영역 -->
    <div class="favorite-team-modal-header">
      <!-- 메인 타이틀 -->
      <div class="favorite-team-modal-header-title">
        좋아하는 팀
      </div>
      <!-- 서브 타이틀 -->
      <div class="favorite-team-modal-header-subtitle">
        좋아하는 팀을 정해보세요!
      </div>
    </div>
    <!-- 리그 선택 영역 -->
    <div class="favorite-team-modal-select-buttons">
      <div v-for="bigleague in leagueStore.leagues" :key="bigleague.apiId" class="favorite-team-modal-select-button" @click="changeLeague(bigleague.hanName)">
        <p v-if="leagueStore.selectFavoriteTeam == bigleague.hanName" class="active-button"> {{ bigleague.hanName }} </p >
        <p v-else class="passive-button"> {{ bigleague.hanName }} </p >
        
       
      </div>
    </div>
    <!-- 리그 영역 -->
    <div v-for="leagueSelected in leagueStore.leagues" :key="leagueSelected.apiId">
      <div class="favorite-team-modal-teams-section-area" v-if="leagueSelected.hanName == leagueStore.selectFavoriteTeam">
        <div class="favorite-team-modal-teams-section">
          <div v-for="LeagueSelectedTeam in leagueSelected.teamList" :key="LeagueSelectedTeam.id" class="favorite-team-modal-team" @click="addSelectTeam(LeagueSelectedTeam)">
            <img :src="LeagueSelectedTeam.logo" width="80" height="80">
            <div>{{ LeagueSelectedTeam.hanName }}</div>
          </div>

        </div>
        <div v-if="leagueStore.selectTeam.length !== 0" style="height: 200px;">

        </div>
      </div>
    </div>

    <!-- 푸터 영역 -->
    <div v-if="leagueStore.selectTeam.length !== 0" class="favorite-team-modal-footer">
      <div class="favorite-team-modal-footer-item" @click="spliceTeam(leagueStore.selectTeam[0])">
        <img :src="leagueStore.selectTeam[0].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ leagueStore.selectTeam[0].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="leagueStore.selectTeam.length >= 2" @click="spliceTeam(leagueStore.selectTeam[1])">
        <img :src="leagueStore.selectTeam[1].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ leagueStore.selectTeam[1].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="leagueStore.selectTeam.length == 3" @click="spliceTeam(leagueStore.selectTeam[2])">
        <img :src="leagueStore.selectTeam[2].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ leagueStore.selectTeam[2].hanName }}
        </div>
      </div>

      <button class="save-button" @click="leagueStore.favoriteTeam = false">저장</button>
    </div>
  </v-card>
</template>

<script setup>
import { useLeagueStore } from "@/store"

const leagueStore = useLeagueStore()



const addSelectTeam = (myTeam) => {
  if (leagueStore.selectTeam.length < 3){
    if (leagueStore.selectTeam.includes(myTeam)) {
      const smallNumberIndex = leagueStore.selectTeam.indexOf(myTeam);
      leagueStore.selectTeam.splice(smallNumberIndex, 1)
    }
    else {
      leagueStore.selectTeam.push(myTeam);
    }
  }
  else if (leagueStore.selectTeam.length === 3) {
    if (leagueStore.selectTeam.includes(myTeam)) {
      const smallNumberIndex = leagueStore.selectTeam.indexOf(myTeam);
      leagueStore.selectTeam.splice(smallNumberIndex, 1)
    }
  }
}

const spliceTeam = (myTeam) => {
      if (leagueStore.selectTeam.includes(myTeam)) {
      const smallNumberIndex = leagueStore.selectTeam.indexOf(myTeam);
      leagueStore.selectTeam.splice(smallNumberIndex, 1)
    }
}

const changeLeague = (name) => {
  leagueStore.selectFavoriteTeam = name
}
</script>

<style>
.favorite-team-modal {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}
.favorite-team-modal::-webkit-scrollbar {
    display: none; 
}
.favorite-team-modal-header {
  width: 600px;
  height: 150px;
  border-bottom: 1px solid black;
}
.favorite-team-modal-header-title {
  height: 50px;
  font-family: 'MICEGothic Bold';
  font-size: 40px;
  text-align: center;
  margin: 27px auto;
}
.favorite-team-modal-header-subtitle {
  font-size: 20px;
  text-align: center;
  margin: 4px auto 22px;
  color: var(--main-color);
  
}
.favorite-team-modal-select-buttons {
  width: 600px;
  height: 100px;
  display: flex;
  padding: 0px 75px;
  flex-wrap: wrap;
  border-bottom: 1px solid black;
}
.favorite-team-modal-select-button {
  width: 100px;
  height: 30px;
  font-size: 18px;
  margin: 10px 25px;
  text-align: center;
  font-family: 'MICEGothic Bold';

}
.favorite-team-modal-teams-section {
  width: 600px;
  min-height: 600px;
  display: flex;
  flex-wrap: wrap;
  padding: 0px 50px;
  font-size: 14px;
}
.favorite-team-modal-teams-section-area {
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  
}
.favorite-team-modal-teams-section-area::-webkit-scrollbar {
    display: none; 
}
.favorite-team-modal-team {
  width: 80px;
  height: 100px;
  margin: 10px;
  text-align: center;
}
.favorite-team-modal-footer {
  border-top: 1px solid black;
  position: fixed;
  display: flex;
  width: 600px;
  bottom: 0;
  background-color: white;
}
.favorite-team-modal-footer-item {
  margin: 10px 20px 30px 20px;
}
.favorite-team-modal-footer-image {
  width: 70px;
  height: 70px;
}
.favorite-team-modal-footer-item-title {
  width: 70px;
  height: 12px;
  text-align: center;
  font-size: 12px;
}
.save-button {
  position: absolute;
  width: 100px;
  height: 40px;
  bottom: 40px;
  color: white;
  background-color: #0066A2;
  border-radius: 5px;
  right: 15px;
}
.active-button {
  background-color: var(--main-color);
  color: white;
  border-radius: 5px;
}
.passive-button {
  background-color: var(--sub-color);
  border: 1px solid var(--main-color);
  color: var(--main-color);
  border-radius: 5px;

}
</style>