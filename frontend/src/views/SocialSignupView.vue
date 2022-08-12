<template>
  <NavBar/>  
  <div style="display:flex;">
    <SideBar/>
    <div v-if="loaded" class="signup">
      <div class="signup-range">
        <div class="signup-range-title">
          이메일
        </div>
        <div style="position: relative;">
          <input disabled type="email" class="signup-range-input" v-model="socialSignupEmail">
        </div>
      </div>

      <!-- 닉네임 -->
      <div class="signup-range">
        <div class="signup-range-title">
          닉네임
        </div>
        <div style="position: relative;">
          <input type="text" class="signup-range-input" maxlength="40" v-model="socialSignupNickname">
        </div>
      </div>
   
      <!-- 본인 소개 -->
      <div class="signup-range signup-range-introduce" >
        <div class="signup-range-title">
          본인 소개
        </div>
        <div style="position: relative;">
          <textarea cols="21" rows="5" class="signup-range-input-introduce" v-model="socialSignupMyInfo"></textarea>
        </div>
      </div>

      <!-- 좋아하는 리그 -->
      <div class="signup-range" style="height:182px;">
        <div class="signup-range-title">
          좋아하는 리그
          <v-dialog v-model="leagueStore.favoriteLeague">
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteLeagueModal/>
          </v-dialog>
          <div v-if="accountStore.profile.favoriteLeagueList.length !== 0" class="signup-favorite-league">
            <div v-for="favoriteLeagueList in accountStore.profile.favoriteLeagueList" :key="favoriteLeagueList.logo" class="signup-favorite-league-item">
              <img :src="favoriteLeagueList.logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ favoriteLeagueList.hanName }}
              </div>
            </div>
          </div>        
        </div>
      </div>

      <!-- 좋아하는 팀 -->
      <div class="signup-range" style="height:182px;">
        <div class="signup-range-title">
          좋아하는 팀    
          <v-dialog v-model="leagueStore.favoriteTeam">
            <template v-slot:activator="{ props }">
              <v-icon class="sideBar-item-icon" v-bind="props">
                mdi-plus-circle-outline
              </v-icon>
            </template>
            <!-- 모달 창 -->
            <FavoriteTeamModal/>
          </v-dialog>
          <div v-if="accountStore.profile.favoriteTeamList.length !== 0" class="signup-favorite-league">
            <div v-for="favoriteTeamList in accountStore.profile.favoriteTeamList" :key="favoriteTeamList.hanName" class="signup-favorite-league-item">
              <img :src="favoriteTeamList.logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ favoriteTeamList.hanName }}
              </div>
            </div>
          </div>  
        </div>
      </div>

      <!-- 회원 가입!! -->
      <div class="signup-range-bottom">
        <button class="signup-submit-button"  @click="router.push({name:'MainPage'})">
          이전
        </button>
        <button class="signup-submit-button next" @click="socialSignupBtn()">
          다음
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
import Swal from 'sweetalert2'
import router from '@/router'
import { useRoute } from "vue-router"
import axios from "axios"
import { ref } from "vue"

const route = useRoute()
const leagueStore = useLeagueStore()
const accountStore = useAccountStore()
const socialSignupEmail = ref('')
const socialSignupMyInfo = ref('')
const socialSignupNickname = ref('')
const loaded = ref(false)

// 회원가입 시 변수 초기화 영역
accountStore.myImage  = ''
accountStore.profile.favoriteLeagueList = []
accountStore.profile.favoriteTeamList = []

function socialSignupBtn() {
  let leagueList = []
  let teamList = []
  for (const league of accountStore.profile.favoriteLeagueList){
    leagueList.push(league.apiId)
  }
  for (const league of accountStore.profile.favoriteTeamList){
    teamList.push(league.apiId)
  }
  axios({
    url: "https://i7b204.p.ssafy.io/cheertogether/oauth2/kakao/join",
    method: 'POST',
    data: {
      email : socialSignupEmail.value,
      favoriteLeagueList : leagueList,
      favoriteTeamList: teamList,
      myInfo : socialSignupMyInfo.value,
      nickname : socialSignupNickname.value,
      role : 'USER'
    }
  })
  .then(res => {
    console.log('------------')
    console.log(res)
    console.log('------------')
    sessionStorage.setItem('token', res.data.token)
    sessionStorage.setItem('isSocialLogin', true)
    //accountStore.socialLoginComplete(res.data.token)
    Swal.fire({
      icon: 'success',
      title: '가입되었습니다!',
    })
    router.push({name:'MainPage'})
  })
  .catch(err => {
    console.log(err)
    Swal.fire({
      icon: 'error',
      title: '가입 실패.'
    })
  })
}
// 소셜 회원가입 or 로그인 분기
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/oauth2/kakao',
  method: 'POST',
  data: {code: route.query.code}
  }).then(res => {
    if (res.data.email) {
      if (!res.data.newMember) {
        sessionStorage.setItem('token', res.data.token)
        sessionStorage.setItem('isSocialLogin', true)
        accountStore.socialLoginComplete(res.data.token)
        Swal.fire({
          icon: 'success',
          title: '성공적으로 로그인 되었습니다.',
        })
        router.push({name:'MainPage'})
      } else {
        console.log(res)
        socialSignupEmail.value = res.data.email
      }
    } else {
      console.log('응답은 오는데 email이 없음')
      console.log(res.data)
      Swal.fire({
        icon: 'error',
        title: '이메일을 못받았음.'
      })
      router.push({name:'MainPage'})
    }
  }).catch(err => {
    console.log(err)
    Swal.fire({
      icon: 'error',
      title: 'Axios에러.'
    })
    router.push({name:'MainPage'})
}).then(loaded.value = true)
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
  padding-top: 85px;
  background-size: cover;
  background-repeat:no-repeat ;
  background-position: center;
}
.signup-image:hover {
  cursor: pointer;
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
  padding-right: 120px;
  
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
.signup-range-input-introduce {
  width: 420px;
  height: 200px;
  border: 1px solid #D9D9D9;
  white-space: pre-wrap;
  padding: 0 10px;

}
.signup-range-introduce {
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
.signup-range-bottom {
  width: 420px;
  height: 83px;
  font-size: 21px;
  margin: 40px auto 20px;
  display: flex;
  justify-content: space-around;
}
.signup-submit-button {
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
.signup {
  margin: 90px 0 0 200px;
}
}
</style>