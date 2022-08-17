<template>
  <NavBar/>  
  <div style="display:flex;">
    <SideBar/>
    <div class="mypagedeit">
      <!-- 이메일 -->
      <div class="mypagedeit-range" v-if="!accountStore.emailDoubleChecked" style="margin-top:40px;">
        <div class="mypagedeit-range-title ">
          이메일
        </div>
        <div style="position: relative;">
          <input type="email" class="mypagedeit-range-input checked-input" :placeholder="accountStore.profile.email" maxlength="40" disabled>
        </div>
      </div>

      <!-- 닉네임 -->
      <div class="mypagedeit-range">
        <div class="mypagedeit-range-title">
          닉네임
        </div>
        <div style="position: relative;">
          <input type="text" class="mypagedeit-range-input" v-model="myNickname" :placeholder="myNickname" maxlength="10">
        </div>
      </div>

      <!-- 비밀번호 -->
      <div class="mypagedeit-range" v-if="!isSocialLogin">
        비밀 번호
        <v-dialog
          v-model="accountStore.isChangePasswordModal"
        >
          <template v-slot:activator="{ props }" >
            <v-btn style="font-family: var(--bold-font);" v-bind="props" class="change-password-button">
              비밀번호 변경
            </v-btn>
          </template>
          <!-- 모달 창 -->
          <ChangePasswordModal/>
        </v-dialog>
      </div>

      
      <!-- 본인 소개 -->
      <div class="mypagedeit-range mypagedeit-range-introduce" >
        <div class="mypagedeit-range-title">
          본인 소개
        </div>
        <div style="position: relative;">
          <textarea cols="21" rows="5" maxlength="180" class="mypagedeit-range-input-introduce" v-model="accountStore.profile.myInfo"></textarea>
        </div>
      </div>
      <!-- 좋아하는 리그 -->
      <div class="mypagedeit-range" style="height:230px;">
        <div class="mypagedeit-range-title">
          좋아하는 리그
          <v-dialog
            v-model="leagueStore.favoriteLeague"
          >
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteLeagueModal/>
          </v-dialog>
          <div v-if="accountStore.profile.favoriteLeagueList.length !== 0" class="mypagedeit-favorite-league">
            <div class="mypagedeit-favorite-league-item" style="margin-left:0px;">
              <img :src="accountStore.profile.favoriteLeagueList[0].logo" class="mypagedeit-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[0].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length >= 2" >
              <img :src="accountStore.profile.favoriteLeagueList[1].logo" class="mypagedeit-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[1].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length == 3" >
              <img :src="accountStore.profile.favoriteLeagueList[2].logo" class="mypagedeit-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[2].hanName }}
              </div>
            </div>
          </div>        
        </div>
      </div>

      <!-- 좋아하는 팀 -->
      <div class="mypagedeit-range" style="height:230px;">
        <div class="mypagedeit-range-title">
          좋아하는 팀    
          <v-dialog
            v-model="leagueStore.favoriteTeam"
          >
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteTeamModal/>
          </v-dialog>
          <div v-if="accountStore.profile.favoriteTeamList.length !== 0" class="mypagedeit-favorite-team">
            <div class="mypagedeit-favorite-team-item" style="margin-left:0px;">
              <img :src="accountStore.profile.favoriteTeamList[0].logo" class="mypagedeit-favorite-team-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-team-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[0].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 2" >
              <img :src="accountStore.profile.favoriteTeamList[1].logo" class="mypagedeit-favorite-team-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-team-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[1].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 3" >
              <img :src="accountStore.profile.favoriteTeamList[2].logo" class="mypagedeit-favorite-team-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-team-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[2].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 4" >
              <img :src="accountStore.profile.favoriteTeamList[3].logo" class="mypagedeit-favorite-team-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-team-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[3].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length == 5" >
              <img :src="accountStore.profile.favoriteTeamList[4].logo" class="mypagedeit-favorite-team-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="mypagedeit-favorite-team-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[4].hanName }}
              </div>
            </div>
          </div>  
        </div>
      </div>

      <!-- 회원 수정 -->
      <div class="mypagedeit-range-bottom">
        <v-btn class="mypagedeit-submit-button"  @click="router.go(-1)" style="height:50px; font-size:21px;">
          이전
        </v-btn>
        <v-btn class="mypagedeit-submit-button next" @click="changeUserInfo(myNickname)" style="height:50px; font-size:21px; color:white;">
          저장
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteLeagueModal from "../components/FavoriteLeagueModal.vue"
import ChangePasswordModal from "../components/ChangePasswordModal.vue"
import FavoriteTeamModal from "../components/FavoriteTeamModal.vue"
import { useAccountStore } from "@/store"
import { useLeagueStore } from "@/store"
import router from '@/router'
import { ref } from "vue";

const leagueStore = useLeagueStore()
const accountStore = useAccountStore()

accountStore.userProfile(accountStore.profileId)

let myNickname = ref(accountStore.profile.nickname)
const isSocialLogin = ref(sessionStorage.getItem("isSocialLogin") ?? false);

const changeUserInfo = (myNickname) => {
    // 사용한 피니아 변수들 감기

    if (accountStore.profile.favoriteLeagueList.length == 3) {
      accountStore.profile.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId, accountStore.profile.favoriteLeagueList[1].apiId, accountStore.profile.favoriteLeagueList[2].apiId]
    }
    else if (accountStore.profile.favoriteLeagueList.length == 2) {
      accountStore.profile.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId, accountStore.profile.favoriteLeagueList[1].apiId ]
    }
    else if (accountStore.profile.favoriteLeagueList.length == 1) {
      accountStore.profile.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId]
    }

    if (accountStore.profile.favoriteTeamList.length == 5) {
      accountStore.profile.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId, accountStore.profile.favoriteTeamList[3].apiId, accountStore.profile.favoriteTeamList[4].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 4) {
      accountStore.profile.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId, accountStore.profile.favoriteTeamList[3].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 3) {
      accountStore.profile.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 2) {
      accountStore.profile.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId ]
    }
    else if (accountStore.profile.favoriteTeamList.length == 1) {
      accountStore.profile.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId]
    }
    
    accountStore.profile.nickname = myNickname

    // 회원 정보 수정
    accountStore.editUserProfile(accountStore.profileId)
  
}

</script>
<style>
.mypagedeit {
  margin: 130px 0 0 210px;
  width: 100%;
}
.mypagedeit-image {
  width: 200px;
  height: 200px;
  border-radius: 200px;
  border: 1px solid #D9D9D9;
  margin: 0 auto 35px;
  padding-top: 85px;
  background-size: cover;
  background-repeat:no-repeat ;
  background-position: center;
}
.mypagedeit-image:hover {
  cursor: pointer;
}
.mypagedeit-range {
  width: 420px;
  height: 83px;
  font-size: 21px;
  margin: 0 auto 20px;
}
.mypagedeit-range-input {
  width: 420px;
  height: 50px;
  border: 1px solid #D9D9D9;
  white-space: nowrap;
  padding-left: 10px;
  padding-right: 120px;
  
}
.mypagedeit-range-button {
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
.checked-input {
  background-color: darkgrey;
  color: black;
  border: 1px solid black;
}
.checked {
  background-color: #1EC800;
  border: 1px solid black;
}
.err-password-accordance {
  text-align: center;
  color: red;
  margin-bottom: 10px;
}
.mypagedeit-range-input-introduce{
  width: 420px;
  height: 200px;
  border: 1px solid #D9D9D9;
  white-space: pre-wrap;
  padding: 0 10px;
  resize: none;
}
.mypagedeit-range-introduce{
  height: 233px; 
}
.mypagedeit-favorite-league {
  width: 420px;
  height: 150px;
  display: flex;
}
.mypagedeit-favorite-league-item {
  margin: 15px 20px;
}
.mypagedeit-favorite-league-image {
  width: 70px;
  height: 70px;
}
.mypagedeit-favorite-league-item-title {
  width: 70px;
  height: 12px;
  text-align: center;
  font-size: 16px;
  font-family: var(--bold-font);
}
.mypagedeit-favorite-team {
  width: 420px;
  height: 150px;
  display: flex;
}
.mypagedeit-favorite-team-item {
  margin: 15px 10px;
}
.mypagedeit-favorite-team-image {
  width: 70px;
  height: 70px;
}
.mypagedeit-favorite-team-item-title {
  width: 70px;
  height: 16px;
  text-align: center;
  font-size: 16px;
  font-family: var(--bold-font);
}
.mypagedeit-range-bottom {
  width: 420px;
  height: 83px;
  font-size: 21px;
  margin: 40px auto 20px;
  display: flex;
  justify-content: space-around;
}
.mypagedeit-submit-button {
  font-family: 'MICEGothic Bold';
  width: 200px;
  height: 50px;
  background-color: #D9D9D9;
  border-radius: 5px;
}
.next {
  background-color: var(--main-color);
  color: white;
}
.change-password-button {
  margin-top: 15px;
  margin-bottom: 20px;
  padding: 5px;
  width: 160px;
  height: 37px;
  background-color: blanchedalmond;
  color: black;
  border-radius: 30px;
  font-size: 18px;
  text-align: center;
}
.change-password-button:hover {
  cursor: pointer;
}
@media (max-width: 1580px) {
.mypagedeit {
  margin: 90px 0 0 200px;
}
}
</style>