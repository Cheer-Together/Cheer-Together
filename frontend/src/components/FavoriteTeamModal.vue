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
      <div v-for="selectFavoriteTeams in signupStore.selectFavoriteTeams" :key="selectFavoriteTeams.name" class="favorite-team-modal-select-button" @click="changeLeague(selectFavoriteTeams.name)">
        {{ selectFavoriteTeams.name }}
      </div>
    </div>
    <!-- 리그 영역 -->
    <div v-for="mymyteam in signupStore.selectFavoriteTeams" :key="mymyteam.name">
      <div class="favorite-team-modal-teams-section-area" v-if="mymyteam.name == signupStore.selectFavoriteTeam">
        <div class="favorite-team-modal-teams-section">
          <div v-for="myTeams in signupStore.premierLeague" :key="myTeams.name" class="favorite-team-modal-team" @click="addSelectTeam(myTeams)"> 
            <img :src="myTeams.img" width="80" height="80">
            {{ myTeams.name }}
          </div>

        </div>
        <div v-if="signupStore.selectTeam.length !== 0" style="height: 200px;">

        </div>
      </div>
    </div>

    <!-- 푸터 영역 -->
    <div v-if="signupStore.selectTeam.length !== 0" class="favorite-team-modal-footer">
      <div class="favorite-team-modal-footer-item">
        <img :src="signupStore.selectTeam[0].img" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ signupStore.selectTeam[0].name }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="signupStore.selectTeam.length >= 2" >
        <img :src="signupStore.selectTeam[1].img" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ signupStore.selectTeam[1].name }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="signupStore.selectTeam.length == 3" >
        <img :src="signupStore.selectTeam[2].img" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ signupStore.selectTeam[2].name }}
        </div>
      </div>

      <button class="save-button" @click="signupStore.favoriteTeam = false">저장</button>
    </div>
  </v-card>
</template>

<script setup>
import { useSignupStore } from "@/store"

const signupStore = useSignupStore()

const addSelectTeam = (myTeam) => {
  if (signupStore.selectTeam.length < 3){
    if (signupStore.selectTeam.includes(myTeam)) {
      const smallNumberIndex = signupStore.selectTeam.indexOf(myTeam);
      signupStore.selectTeam.splice(smallNumberIndex, 1)
    }
    else {
      signupStore.selectTeam.push(myTeam);
    }
  }
  else if (signupStore.selectTeam.length === 3) {
    if (signupStore.selectTeam.includes(myTeam)) {
      const smallNumberIndex = signupStore.selectTeam.indexOf(myTeam);
      signupStore.selectTeam.splice(smallNumberIndex, 1)
    }
  }
}

const changeLeague = (name) => {
  signupStore.selectFavoriteTeam = name
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
  border: 1px solid var(--main-color);
  border-radius: 5px;
  font-family: 'MICEGothic Bold';
  color: var(--main-color);
  background-color: var(--sub-color);
}
.favorite-team-modal-teams-section {
  width: 600px;
  min-height: 600px;
  display: flex;
  flex-wrap: wrap;
  padding: 0px 50px;
  word-break: keep-all;
  
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
  margin: 10px 20px 50px 20px;
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
</style>