<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="mypage">
      <!-- 헤더 -->
      <div class="mypage-header">
        <!-- 이미지 닉네임 소개 -->
        <div style="display:flex;">
          <!-- 프로필 이미지 -->
          <div class="mypage-header-image">
            <img :src="accountStore.otherProfile.profileImage" alt="" width="200" height="200">
          </div>
          <!-- 닉네임 소개 -->
          <div>
            <!-- 닉네임 -->
            <div class="mypage-header-nickname">
              {{ accountStore.otherProfile.nickname }}
            </div>
            <!-- 소개 -->
            <div class="mypage-header-info">
              {{ accountStore.otherProfile.myInfo }}
            </div>
          </div>
        </div>
        <!-- 회원정보수정 -->
        <div style="display:flex;" v-if="accountStore.otherProfile.email === accountStore.profile.email">
          <v-btn  class="mypage-header-editinfo" @click="router.push({name: 'MypageEdit' , params: { userid: accountStore.profileId } })">
            <v-icon>
              mdi-pencil-box
            </v-icon>
            회원정보수정
          </v-btn>

          <v-btn style="color:red; width:140px;" class="mypage-header-editinfo" @click="withdrawal">
            <v-icon >
              mdi-delete-forever
            </v-icon>
            회원 탈퇴
          </v-btn>
        </div>        
      </div>
      <!-- 본문 -->
      <div class="mypage-section">
        <!-- 좋아하는 리그 -->
        <div class="mypage-section-favorite-league">
          <div class="mypage-section-favorite-league-title">
            {{ accountStore.otherProfile.nickname }}님이 좋아하는 리그
          </div>
          <div style="display:flex;">
            <div class="mypage-favorite-league" v-for="favoriteLeague in accountStore.otherProfile.favoriteLeagueList" :key="favoriteLeague.apiId">
              <img :src="favoriteLeague.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.hanName }}
              </div>
          </div>
          </div>
        </div>

        <!-- 좋아하는 팀 -->
        <div class="mypage-section-favorite-league">
          <div class="mypage-section-favorite-league-title">
            {{ accountStore.otherProfile.nickname }}님이 좋아하는 팀
          </div>
          <div style="display:flex;">

            <div class="mypage-favorite-league" v-for="favoriteTeam in accountStore.otherProfile.favoriteTeamList" :key="favoriteTeam.iapiId">
              <img :src="favoriteTeam.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteTeam.hanName }}
              </div>
            </div>
           
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import router from '@/router'
import { useAccountStore } from "@/store"
import { useRoute } from "vue-router"

const route = useRoute()

const accountStore = useAccountStore()
accountStore.presentUserProfile(route.params.userid)

// 회원 탈퇴 함수
const withdrawal = () => {

  accountStore.withdrawal(accountStore.profileId)
}
</script>

<style>
.mypage {
  width: 100%;
  margin: 30px 0 0 210px;
}
.mypage-header {
  margin: 30px auto;
  width: 1300px;
  height: 350px;
  border-bottom: 1px solid #b6b9ba;
  padding: 75px 0 0 42.5px;
}
.mypage-header-image {
  width: 200px;
  height: 200px;
  border-radius: 150px;

  margin-right:50px;
  overflow: hidden;
}
.mypage-header-nickname {
  padding: 7px;
  width: 300px;
  height: 50px;
  font-size: 32px;
  font-family: var(--bold-font);
}
.mypage-header-info {
  margin-top: 30px;
  padding: 10px;
  width: 940px;
  height: 120px;
  font-size: 17px;
  background-color: var( --sub-color );
}
.mypage-header-editinfo {
  margin: 20px 40px 0 0;
  padding: 5px;
  width: 160px;
  height: 37px;
  background-color: blanchedalmond;
  color: red;
  border-radius: 30px;
  font-size: 18px;
  text-align: center;
  font-family: var(--bold-font);
}
.mypage-header-editinfo:first-child {
  margin-left: auto;
  color: black;
}
.mypage-header-editinfo:hover {
  cursor: pointer;
}
.mypage-section {
  margin: 0 auto;
  width: 1300px;
  height: 700px;
}
.mypage-section-favorite-league {
  padding: 10px;
  height: 300px;
}
.mypage-section-favorite-league-title {
  font-size: 25px;
  font-family: var(--bold-font);
}
.mypage-favorite-league {
  margin: 35px;
  width: 130px;
  height: 130px;
}
.mypage-favorite-league img {
  width: 130px;
  height: 130px;
  object-fit: contain;
}
.mypage-favorite-league-title {
  margin-top: 10px;
  font-size: 18px;
  width: 130px;
  text-align: center;
  font-family: var(--bold-font);
}
.mypage-section-favorite-team {
    padding: 10px;
  height: 300px;

}
</style>