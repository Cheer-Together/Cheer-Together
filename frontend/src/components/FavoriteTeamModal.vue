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
      <v-btn v-for="bigleague in leagueStore.leagues" :key="bigleague.apiId" class="favorite-team-modal-select-button" @click="changeLeague(bigleague.hanName)">
        <p v-if="leagueStore.selectFavoriteTeam == bigleague.hanName" class="active-button"> {{ bigleague.hanName }} </p >
        <p v-else class="passive-button"> {{ bigleague.hanName }} </p >
        
       
      </v-btn>
    </div>
    <!-- 리그 영역 -->
    <div v-for="leagueSelected in leagueStore.leagues" :key="leagueSelected.apiId">
      <div class="favorite-team-modal-teams-section-area" v-if="leagueSelected.hanName == leagueStore.selectFavoriteTeam">
        <div class="favorite-team-modal-teams-section">
          <div v-for="LeagueSelectedTeam in leagueSelected.teamList" :key="LeagueSelectedTeam.id" class="favorite-team-modal-team" @click="addSelectTeam(LeagueSelectedTeam)">
            <img :src="LeagueSelectedTeam.logo" width="80" >
            <div style="font-family: var(--bold-font);">{{ LeagueSelectedTeam.hanName }}</div>
          </div>

        </div>
        <div v-if="accountStore.profile.favoriteTeamList.length !== 0" style="height: 130px;">

        </div>
      </div>
    </div>

    <!-- 푸터 영역 -->
    <div v-if="accountStore.profile.favoriteTeamList.length !== 0" class="favorite-team-modal-footer">
      <div class="favorite-team-modal-footer-item" @click="spliceTeam(accountStore.profile.favoriteTeamList[0])">
        <img :src="accountStore.profile.favoriteTeamList[0].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ accountStore.profile.favoriteTeamList[0].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="accountStore.profile.favoriteTeamList.length >= 2" @click="spliceTeam(accountStore.profile.favoriteTeamList[1])">
        <img :src="accountStore.profile.favoriteTeamList[1].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ accountStore.profile.favoriteTeamList[1].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="accountStore.profile.favoriteTeamList.length >= 3" @click="spliceTeam(accountStore.profile.favoriteTeamList[2])">
        <img :src="accountStore.profile.favoriteTeamList[2].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ accountStore.profile.favoriteTeamList[2].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="accountStore.profile.favoriteTeamList.length >= 4" @click="spliceTeam(accountStore.profile.favoriteTeamList[3])">
        <img :src="accountStore.profile.favoriteTeamList[3].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ accountStore.profile.favoriteTeamList[3].hanName }}
        </div>
      </div>

      <div class="favorite-team-modal-footer-item" v-if="accountStore.profile.favoriteTeamList.length == 5" @click="spliceTeam(accountStore.profile.favoriteTeamList[4])">
        <img :src="accountStore.profile.favoriteTeamList[4].logo" class="favorite-team-modal-footer-image">
        <div class="favorite-team-modal-footer-item-title">
          {{ accountStore.profile.favoriteTeamList[4].hanName }}
        </div>
      </div>

      <v-btn style="color:white;" class="save-button" @click="leagueStore.favoriteTeam = false">저장</v-btn>
    </div>
  </v-card>
</template>

<script setup>
import { useAccountStore } from "@/store"
import { useLeagueStore } from "@/store"

const accountStore = useAccountStore()
const leagueStore = useLeagueStore()



const addSelectTeam = (myTeam) => {
  if (accountStore.profile.favoriteTeamList.length < 5){
    let myChecked = true
    accountStore.profile.favoriteTeamList.filter((e)=> {
      if (e.apiId === myTeam.apiId) {
        myChecked = false
        const smallNumberIndex = accountStore.profile.favoriteTeamList.indexOf(e);
        accountStore.profile.favoriteTeamList.splice(smallNumberIndex, 1)
      }
    })
    if (myChecked) {
        accountStore.profile.favoriteTeamList.push(myTeam)  
      }
  }
  else{
    accountStore.profile.favoriteTeamList.filter((e)=> {
      if (e.apiId === myTeam.apiId) {
        const smallNumberIndex = accountStore.profile.favoriteTeamList.indexOf(e);
        accountStore.profile.favoriteTeamList.splice(smallNumberIndex, 1)
        return
      }
    })
  }
}

const spliceTeam = (myTeam) => {
      if (accountStore.profile.favoriteTeamList.includes(myTeam)) {
      const smallNumberIndex = accountStore.profile.favoriteTeamList.indexOf(myTeam);
      accountStore.profile.favoriteTeamList.splice(smallNumberIndex, 1)
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
  width: 700px;
  height: 150px;
  border-bottom: 1px solid black;
  z-index: 2;
  position: fixed;
  background-color: white;
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
  width: 700px;
  height: 120px;
  display: flex;
  padding: 0px 75px;
  flex-wrap: wrap;
  border-bottom: 1px solid black;
  background-color: white;
  position: fixed;
  top: 150px;
  z-index: 2;
}
.favorite-team-modal-select-button {
  width: 133px;
  height: 30px;
  font-size: 18px;
  margin: 10px 25px;
  text-align: center;
  font-family: 'MICEGothic Bold';

}
.favorite-team-modal-teams-section {
  width: 700px;
  min-height: 600px;
  max-height: 600px;
  display: flex;
  flex-wrap: wrap;
  padding: 0px 30px;
  font-size: 14px;
  margin-top: 290px;
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
  width: 100px;
  margin: 14px;
  text-align: center;
}
.favorite-team-modal-team:hover {
  transform: scale(1.1);
  cursor: pointer;
}
.favorite-team-modal-footer {
  border-top: 1px solid black;
  position: fixed;
  display: flex;
  width: 700px;
  bottom: 0px;
  background-color: white;
  height: 130px;
}
.favorite-team-modal-footer-item {
  margin: 17px 10px 30px 10px;
}
.favorite-team-modal-footer-image {
  width: 60px;
  margin-left: 15px;

}
.favorite-team-modal-footer-item-title {
  width: 90px;
  height: 12px;
  text-align: center;
  font-size: 14px;
  font-family: var(--bold-font);
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
  width: 133px;
  height: 35px;
  padding-top: 4px;
}
.passive-button {
  background-color: var(--sub-color);
  color: var(--main-color);
  border-radius: 5px;
  width: 133px;
  height: 35px;
  padding-top: 4px;
}
</style>