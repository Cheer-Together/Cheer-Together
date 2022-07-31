<template>
  <NavBar/>  
    <div style="display:flex;">
    <SideBar/>
    <div class="signup">
      <div class="signup-image">
        
      </div>
      <!-- 이메일 -->
      <div class="signup-range">
        <div class="signup-range-title">
          이메일
        </div>
        <div style="position: relative;">
          <input type="email" class="signup-range-input">
          <button class="signup-range-button">
            중복 확인
          </button>
        </div>
      </div>
      <!-- 이메일 인증번호 -->
      <div class="signup-range">
        <div class="signup-range-title">
          이메일 인증번호
        </div>
        <div style="position: relative;">
          <input type="text" class="signup-range-input">
          <button class="signup-range-button">
            확인
          </button>
        </div>
      </div>
      <!-- 닉네임 -->
      <div class="signup-range">
        <div class="signup-range-title">
          닉네임
        </div>
        <div style="position: relative;">
          <input type="text" class="signup-range-input">
        </div>
      </div>
      <!-- 비밀번호 -->
      <div class="signup-range">
        <div class="signup-range-title">
          비밀번호
        </div>
        <div style="position: relative;">
          <input type="password" class="signup-range-input">
        </div>
      </div>
      <!-- 비밀번호 확인 -->
      <div class="signup-range">
        <div class="signup-range-title">
          비밀번호 확인
        </div>
        <div style="position: relative;">
          <input type="password" class="signup-range-input">
        </div>
      </div>
      <!-- 본인 소개 -->
      <div class="signup-range signup-range-introduce" >
        <div class="signup-range-title">
          본인 소개
        </div>
        <div style="position: relative;">
          <textarea cols="21" rows="5" class="signup-range-input-introduce"></textarea>
        </div>
      </div>
      <!-- 좋아하는 리그 -->
      <div class="signup-range" style="height:182px;">
        <div class="signup-range-title">
          좋아하는 리그
           <!-- <button style="border:1px solid black;" @click="signupStore.toggleFavoriteLeague()">click me</button> -->

          <v-dialog
            v-model="signupStore.favoriteLeague"
          >
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteLeagueModal/>
          </v-dialog>
          <div v-if="signupStore.selectLeague.length !== 0" class="signup-favorite-league">
            <div class="signup-favorite-league-item">
              <img :src="signupStore.selectLeague[0].img" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ signupStore.selectLeague[0].title }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="signupStore.selectLeague.length >= 2" >
              <img :src="signupStore.selectLeague[1].img" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ signupStore.selectLeague[1].title }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="signupStore.selectLeague.length == 3" >
              <img :src="signupStore.selectLeague[2].img" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ signupStore.selectLeague[2].title }}
              </div>
            </div>
          </div>        
        </div>
      </div>

      <!-- 좋아하는 팀 -->
      <div class="signup-range" style="height:182px;">
        <div class="signup-range-title">
          좋아하는 팀    
          <v-dialog
            v-model="signupStore.favoriteTeam"
          >
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteTeamModal/>
          </v-dialog>
       
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteLeagueModal from "../components/FavoriteLeagueModal.vue"
import FavoriteTeamModal from "../components/FavoriteTeamModal.vue"
import { useSignupStore } from "@/store"

const signupStore = useSignupStore()
</script>
<style>
.signup {
  margin: 130px 0 0 210px;
  width: 100%;
}
.signup-image {
  width: 200px;
  height: 200px;
  border-radius: 200px;
  border: 1px solid #D9D9D9;
  margin: 0 auto 35px;
}
.signup-range {
  width: 420px;
  height: 83px;
  font-size: 21px;
  margin: 0 auto 20px;
}
.signup-range-input {
  width: 420px;
  height: 50px;
  border: 1px solid #D9D9D9;
  white-space: nowrap;
  padding-left: 10px;
  padding: 0 10px;
}
.signup-range-button {
  position: absolute;
  width: 100px;
  height: 40px;
  top: 5px;
  right: 5px;
  background-color: #0066A2;
  color: white;
  border-radius: 5px;
  font-size: 13px;
}
.signup-range-input-introduce{
  width: 420px;
  height: 200px;
  border: 1px solid #D9D9D9;
  white-space: pre-wrap;
  padding: 0 10px;

}
.signup-range-introduce{
  height: 233px; 
}
.signup-favorite-league {
  width: 420px;
  height: 150px;
  display: flex;
}
.signup-favorite-league-item {
  margin: 15px 20px;
}
.signup-favorite-league-image {
  width: 70px;
  height: 70px;
}
.signup-favorite-league-item-title {
  width: 70px;
  height: 12px;
  text-align: center;
  font-size: 12px;
}
@media (max-width: 1580px) {
.signup {
  margin: 90px 0 0 200px;
}
}
</style>