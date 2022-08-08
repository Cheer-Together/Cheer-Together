<template>
  <NavBar/>  
  <div style="display:flex;">
    <SideBar/>
    <div class="mypagedeit">
      <div class="mypagedeit-image" v-if="!accountStore.profile.profileImage">
        <label for="input-file" style="padding:66px 30px 66px 35px;">
          <v-icon class="sideBar-item-icon">
            mdi-image-search
          </v-icon>
          이미지 올리기
        </label>

      </div>

      <div v-if="accountStore.profile.profileImage" class="mypagedeit-image" :style="{'background-image': `url(${accountStore.profile.profileImage})`}">
        <label for="input-file" style="padding:66px 150px 66px 35px;">
          &nbsp;
        </label>
      </div>


      <input type="file" @change="onInputImage" id="input-file" style="display:none;" accept='image/jpeg,image/gif,image/png'>

      <!-- 이메일 -->
      <div class="mypagedeit-range" v-if="!accountStore.emailDoubleChecked">
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
          <input type="text" class="mypagedeit-range-input" v-model="accountStore.profile.nickname" :placeholder="accountStore.profile.nickname" maxlength="40">
        </div>
      </div>

      <!-- 비밀번호 -->
      <div class="mypagedeit-range">
        <div class="mypagedeit-range-title">
          비밀번호 변경
        </div>
        <div style="position: relative;">
          <input type="password" class="mypagedeit-range-input" maxlength="20" v-model="accountStore.passwordAccordance" @blur="accountStore.checkPassword(accountStore.passwordAccordance)">
        </div>
      </div>

      <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === true">
        올바르지 않은 비밀번호입니다. 영어, 숫자, 특수문자 조합 8- 20자
      </div>

      <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === false" style="color: var(--main-color)">
        올바른 비밀번호입니다.
      </div>
      <!-- 비밀번호 확인 -->
      <div class="mypagedeit-range">
        <div class="mypagedeit-range-title">
          비밀번호 확인
        </div>
        <div style="position: relative;">
          <input type="password" class="mypagedeit-range-input" maxlength="20" v-model="accountStore.passwordAccordance2">
        </div>
      </div>

      <div v-if="accountStore.passwordAccordance2 != '' && accountStore.passwordAccordance != accountStore.passwordAccordance2" class="err-password-accordance">
        비밀번호가 일치하지 않습니다.
      </div>
      
      <!-- 본인 소개 -->
      <div class="mypagedeit-range mypagedeit-range-introduce" >
        <div class="mypagedeit-range-title">
          본인 소개
        </div>
        <div style="position: relative;">
          <textarea cols="21" rows="5" class="mypagedeit-range-input-introduce" v-model="accountStore.profile.myInfo"></textarea>
        </div>
      </div>
      <!-- 좋아하는 리그 -->
      <div class="mypagedeit-range" style="height:182px;">
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
            <div class="mypagedeit-favorite-league-item">
              <img :src="accountStore.profile.favoriteLeagueList[0].logo" class="mypagedeit-favorite-league-image">
              <div class="mypagedeit-favorite-league-item-title">
                {{ accountStore.profile.favoriteLeagueList[0].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length >= 2" >
              <img :src="accountStore.profile.favoriteLeagueList[1].logo" class="mypagedeit-favorite-league-image">
              <div class="mypagedeit-favorite-league-item-title">
                {{ accountStore.profile.favoriteLeagueList[1].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length == 3" >
              <img :src="accountStore.profile.favoriteLeagueList[2].logo" class="mypagedeit-favorite-league-image">
              <div class="mypagedeit-favorite-league-item-title">
                {{ accountStore.profile.favoriteLeagueList[2].hanName }}
              </div>
            </div>
          </div>        
        </div>
      </div>

      <!-- 좋아하는 팀 -->
      <div class="mypagedeit-range" style="height:182px;">
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
            <div class="mypagedeit-favorite-team-item">
              <img :src="accountStore.profile.favoriteTeamList[0].logo" class="mypagedeit-favorite-team-image">
              <div class="mypagedeit-favorite-team-item-title">
                {{ accountStore.profile.favoriteTeamList[0].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 2" >
              <img :src="accountStore.profile.favoriteTeamList[1].logo" class="mypagedeit-favorite-team-image">
              <div class="mypagedeit-favorite-team-item-title">
                {{ accountStore.profile.favoriteTeamList[1].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 3" >
              <img :src="accountStore.profile.favoriteTeamList[2].logo" class="mypagedeit-favorite-team-image">
              <div class="mypagedeit-favorite-team-item-title">
                {{ accountStore.profile.favoriteTeamList[2].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length >= 4" >
              <img :src="accountStore.profile.favoriteTeamList[3].logo" class="mypagedeit-favorite-team-image">
              <div class="mypagedeit-favorite-team-item-title">
                {{ accountStore.profile.favoriteTeamList[3].hanName }}
              </div>
            </div>

            <div class="mypagedeit-favorite-team-item" v-if="accountStore.profile.favoriteTeamList.length == 5" >
              <img :src="accountStore.profile.favoriteTeamList[4].logo" class="mypagedeit-favorite-team-image">
              <div class="mypagedeit-favorite-team-item-title">
                {{ accountStore.profile.favoriteTeamList[4].hanName }}
              </div>
            </div>
          </div>  
        </div>
      </div>

      <!-- 회원 가입!! -->
      <div class="mypagedeit-range-bottom">
        <button class="mypagedeit-submit-button"  @click="router.push({name:'MainPage'})">
          이전
        </button>
        <button class="mypagedeit-submit-button next" @click="changeUserInfo()">
          저장
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import FavoriteLeagueModal from "../components/FavoriteLeagueModal.vue"
import FavoriteTeamModal from "../components/FavoriteTeamModal.vue"
import { useAccountStore } from "@/store"
import { useLeagueStore } from "@/store"
import router from '@/router'
// import Swal from 'sweetalert2'

const leagueStore = useLeagueStore()
const accountStore = useAccountStore()

accountStore.userProfile(35)

// 회원가입 시 변수 초기화 영역
accountStore.passwordAccordance = '';
accountStore.passwordAccordance2 = '';
accountStore.isShowPasswordError = '';
const onInputImage = (e) => {
  console.log(e.target.files[0])
  let url = URL.createObjectURL(e.target.files[0])
  accountStore.profile.profileImage = url
}

const changeUserInfo = () => {
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
    // 회원 정보 수정
    accountStore.editUserProfile(34)
  
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
  font-size: 12px;
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
  height: 12px;
  text-align: center;
  font-size: 12px;
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
@media (max-width: 1580px) {
.mypagedeit {
  margin: 90px 0 0 200px;
}
}
</style>