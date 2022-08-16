<template>
  <div class="favoriteTeam">
    <div class="favoriteTeam-title">
      좋아하는 팀
    </div>
    <!-- 로그인이 안되어 있을 때 -->
    <div class="favoriteTeam-section" @click="accountStore.loginDialogToggle()" v-if="!accountStore.isLogin">
      <div class="favoriteTeam-section-title">
        로그인 후 이용해 주세요!
      </div>
    </div>

    <!-- 로그인이 되어 있을 때 -->
    <div div class="favoriteTeam-section-islogin" @click="accountStore.loginDialogToggle()" v-if="accountStore.isLogin && accountStore.profile.favoriteTeamList.length !== 0">

      <div v-for="team in accountStore.profile.favoriteTeamList" :key="team.apiId" class="main-card" :style="{ backgroundColor: bgcolor[team.leagueName]}">
        <img :src="team.logo" alt=""  class="main-card-logo">
        <div>
          <div class="main-card-teamname">
            {{ team.hanName }}
          </div>
          <div class="main-card-leaguename">
            {{ team.leagueName }}
          </div>
        </div>
      </div>
    </div>

    <!-- 로그인 했지만 좋아하는 팀이 없을 때 -->
    <div class="favoriteTeam-section" @click="router.push({name: 'MypageEdit' , params: { userid: accountStore.profileId } })" v-if="accountStore.isLogin && accountStore.profile.favoriteTeamList.length === 0">
      <div class="favoriteTeam-section-title">
        좋아하는 팀을 추가해 보세요!
      </div>
    </div>


  </div>
</template>

<script setup>
import router from '@/router'
import { useAccountStore } from '@/store'
const accountStore = useAccountStore()

if (accountStore.isLogin) {
  accountStore.userProfile(accountStore.profileId)
}

const bgcolor = {
  "Premier League" : '#3F1052',
  "La Liga" : '#003472',
  "Serie A" : '#0F3059',
  "Bundesliga" : '#B12829',
  "Ligue 1" : '#90A5B6',
  "K League 1" : '#53BA47',
}
</script>

<style>
.favoriteTeam {
  margin-bottom: 40px;
}
.favoriteTeam-title {
  font-size: 24px;
  padding: 5.5px 0;
  font-family: var(--bold-font);
}
.favoriteTeam-section {
  margin-top: 11px;
  width: 1580px;
  height: 190px;
  text-align: center;
  background-color: var(--sub-color);
  border-radius: 4px;
}
.favoriteTeam-section:hover, .favoriteTeam-section:hover .favoriteTeam-section-title{
  cursor: pointer;
  font-size: 24px;
}
.favoriteTeam-section-islogin {
  margin-top: 11px;
  width: 1580px;
  height: 190px;
  text-align: center;
  border-radius: 4px;
  display: flex;
}
.main-card {
  border: 1px solid black ;
  width: 295px;
  margin: 10px;
  background-color: var(--sub-color);
  font-family: var(--bold-font);
  font-size: 20px;
  border-radius: 5px;
  display: flex;
  color: #ecf0f5;
  padding: 30px 10px 0 10px;
}
.main-card-logo {
  width: 80px;
  height: 80px;
}
.main-card div { 
  width: 100%;
}
.favoriteTeam-section-title {
  padding-top: 78px ;
  font-size: 20px;
}
.main-card-teamname {
  font-size: 18px;
  margin: 10px 0;
}

.main-card-leaguename {
  font-size: 16px;
}
@media (max-width: 1580px) {
.favoriteTeam {
  margin-bottom: 16px;
}
.favoriteTeam-title {
  font-size: 15px;
}
.favoriteTeam-section {
  margin-top: 8px;
  width: 1300px;
  height: 170px;
}
.favoriteTeam-section-title {
  padding-top: 78px ;
}
}
</style>