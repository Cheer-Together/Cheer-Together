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
    <div div class="favoriteTeam-section-islogin" @click="accountStore.loginDialogToggle()" v-else>

      <div v-for="team in accountStore.profile.favoriteTeamList" :key="team.apiId" class="main-card">
        <img :src="team.logo" alt="">
        <div>
          {{team.hanName}}
        </div>
      </div>
      

    </div>
  </div>
</template>

<script setup>
import { useAccountStore } from '@/store'
const accountStore = useAccountStore()

if (accountStore.isLogin) {
  accountStore.userProfile(accountStore.profileId)
}
</script>

<style>
.favoriteTeam {
  margin-bottom: 40px;
}
.favoriteTeam-title {
  font-size: 24px;
  padding: 5.5px 0;
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
  max-width: 400px;
  width: 100%;
  margin: 10px;
  background-color: var(--sub-color);
  font-family: var(--bold-font);
  font-size: 20px;
  border-radius: 5px;
}
.main-card img {
  width: 50%;
  max-width: 120px;
}
.main-card div { 
  width: 100%;
}
.favoriteTeam-section-title {
  padding-top: 78px ;
  font-size: 20px;
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