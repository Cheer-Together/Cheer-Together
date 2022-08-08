<template>
  <NavBar/>  
  <div style="display:flex;">
    <SideBar/>
    <div class="signup">
      {{route.query.code}}
      <div class="signup-image" v-if="!accountStore.myImage">
        <label for="input-file" style="padding:66px 30px 66px 35px;">
          <v-icon class="sideBar-item-icon">
            mdi-image-search
          </v-icon>
          이미지 올리기
        </label>
      </div>
      <div v-if="accountStore.myImage" class="signup-image" :style="{'background-image': `url(${accountStore.myImage})`}">
        <label for="input-file" style="padding:66px 150px 66px 35px;">
          &nbsp;
        </label>
      </div>


      <input type="file" @change="onInputImage" id="input-file" style="display:none;" accept='image/jpeg,image/gif,image/png'>

      <!-- 이메일 -->
      <div class="signup-range" v-if="!accountStore.emailDoubleChecked">
        <div class="signup-range-title">
          이메일
        </div>
        <div style="position: relative;">
          <input type="email" class="signup-range-input" v-model="credentialsSignup.email" maxlength="40">
          <button class="signup-range-button" @click.prevent="accountStore.checkEmail(credentialsSignup.email)">
            중복 확인
          </button>
        </div>
      </div>

      <!-- 이메일 유효성 검사 + 가입하지 않은 이메일  -->
      <div class="signup-range" v-if="accountStore.emailDoubleChecked">
        <div class="signup-range-title">
          이메일
        </div>
        <div style="position: relative;">
          <div type="email" class="signup-range-input checked-input" style="padding-top:10px">
            {{ credentialsSignup.email }}
            <button class="signup-range-button checked">
              <v-icon class="sideBar-item-icon">
                mdi-check-bold
              </v-icon>
            </button>
          </div>
        </div>
      </div>

      <div class="err-password-accordance" v-if="accountStore.emailDoubleChecked" style="color: var(--main-color)">
        사용 가능한 이메일입니다.
      </div>
      <!-- 이메일 인증번호 -->
      <div class="signup-range" v-if="!accountStore.emailAuthCodeChecked">
        <div class="signup-range-title">
          이메일 인증번호
        </div>
        <div style="position: relative;">
          <input type="text" class="signup-range-input" maxlength="10" v-model="userInputEmailAuthCode">
          <button v-if="!accountStore.isPushEmail" class="signup-range-button" @click.prevent="pushEmail(credentialsSignup.email)" style="background-color:brown;">
            보내기 
          <v-icon>
            mdi-send
          </v-icon>
          </button>

          <button v-else class="signup-range-button" @click.prevent="accountStore.emailAuthCodeCheck(userInputEmailAuthCode)">
            확인
          </button>
        </div>
      </div>

      <!-- 이메일 인증번호 + 가입하지 않은 이메일  -->
      <div class="signup-range" v-if="accountStore.emailAuthCodeChecked">
        <div class="signup-range-title">
          이메일 인증번호
        </div>
        <div style="position: relative;">
          <div type="text" class="signup-range-input checked-input" style="padding-top:10px">
            {{userInputEmailAuthCode }}
            <button class="signup-range-button checked">
              <v-icon class="sideBar-item-icon">
                mdi-check-bold
              </v-icon>
            </button>
          </div>
        </div>
      </div>

      <div class="err-password-accordance" v-if="accountStore.emailAuthCodeChecked" style="color: var(--main-color)">
        인증되었습니다.
      </div>
      <!-- 닉네임 -->
      <div class="signup-range">
        <div class="signup-range-title">
          닉네임
        </div>
        <div style="position: relative;">
          <input type="text" class="signup-range-input" maxlength="40" v-model="credentialsSignup.nickname">
        </div>
      </div>

      <!-- 비밀번호 -->
      <div class="signup-range">
        <div class="signup-range-title">
          비밀번호
        </div>
        <div style="position: relative;">
          <input type="password" class="signup-range-input" maxlength="20" v-model="accountStore.passwordAccordance" @blur="accountStore.checkPassword(accountStore.passwordAccordance)">
        </div>
      </div>

      <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === true">
        올바르지 않은 비밀번호입니다. 영어, 숫자, 특수문자 조합 8- 20자
      </div>

      <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === false" style="color: var(--main-color)">
        올바른 비밀번호입니다.
      </div>
      <!-- 비밀번호 확인 -->
      <div class="signup-range">
        <div class="signup-range-title">
          비밀번호 확인
        </div>
        <div style="position: relative;">
          <input type="password" class="signup-range-input" maxlength="20" v-model="accountStore.passwordAccordance2">
        </div>
      </div>

      <div v-if="accountStore.passwordAccordance2 != '' && accountStore.passwordAccordance != accountStore.passwordAccordance2" class="err-password-accordance">
        비밀번호가 일치하지 않습니다.
      </div>
      
      <!-- 본인 소개 -->
      <div class="signup-range signup-range-introduce" >
        <div class="signup-range-title">
          본인 소개
        </div>
        <div style="position: relative;">
          <textarea cols="21" rows="5" class="signup-range-input-introduce" v-model="credentialsSignup.myInfo"></textarea>
        </div>
      </div>
      <!-- 좋아하는 리그 -->
      <div class="signup-range" style="height:182px;">
        <div class="signup-range-title">
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
          <div v-if="leagueStore.selectLeague.length !== 0" class="signup-favorite-league">
            <div class="signup-favorite-league-item">
              <img :src="leagueStore.selectLeague[0].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectLeague[0].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="leagueStore.selectLeague.length >= 2" >
              <img :src="leagueStore.selectLeague[1].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectLeague[1].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="leagueStore.selectLeague.length == 3" >
              <img :src="leagueStore.selectLeague[2].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectLeague[2].hanName }}
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
          <div v-if="leagueStore.selectTeam.length !== 0" class="signup-favorite-league">
            <div class="signup-favorite-league-item">
              <img :src="leagueStore.selectTeam[0].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectTeam[0].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="leagueStore.selectTeam.length >= 2" >
              <img :src="leagueStore.selectTeam[1].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectTeam[1].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="leagueStore.selectTeam.length == 3" >
              <img :src="leagueStore.selectTeam[2].logo" class="signup-favorite-league-image">
              <div class="signup-favorite-league-item-title">
                {{ leagueStore.selectTeam[2].hanName }}
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
        <button class="signup-submit-button next" @click.prevent="changeSignUp(credentialsSignup)">
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
import axios from 'axios'
import { onMounted } from "vue"
const route = useRoute()
onMounted(()=>{
  axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/oauth2/kakao',
  method: 'POST',
  data: {
    code: route.query.code,
  }
  }).then(res => {
      console.log(res.data)
  }).catch(err => {
      console.log(err)
  })
})

// route.query.code 이거를 /oauth2/kakao에다가

const leagueStore = useLeagueStore()
const accountStore = useAccountStore()

// 회원가입 시 변수 초기화 영역
accountStore.emailDoubleChecked = false ;
accountStore.emailAuthCode = 'AAAAAAAAAAA';
accountStore.emailAuthCodeChecked = false;
accountStore.passwordAccordance = '';
accountStore.passwordAccordance2 = '';
accountStore.isPushEmail = false;
accountStore.isShowPasswordError = '';
accountStore.myImage  = ''
leagueStore.selectLeague = []
leagueStore.selectTeam = []

let userInputEmailAuthCode = ''
let credentialsSignup = {
  email: "",
  favoriteLeagueList: [ 
  ],
  myInfo: "",
  nickname: "",
  password: '',
  profileImage: '',
  role: 'user'
}

const onInputImage = (e) => {
  console.log(e.target.files[0])
  let url = URL.createObjectURL(e.target.files[0])
  accountStore.myImage = url
}

const pushEmail = (email) => {
  if (!accountStore.emailDoubleChecked){
    Swal.fire({
      icon: 'error',
      title: '이메일 중복을 확인 하세요!',
    })
  }
  else {
    accountStore.isPushEmail = true
    accountStore.emailAuth(email)
  }
}

const changeSignUp = (credentialsSignup) => {
  if (!accountStore.emailDoubleChecked) {
    Swal.fire({
      icon: 'error',
      title: '이메일 중복을 확인 하세요!',
    })
  }
  else if (!accountStore.emailAuthCodeChecked) {
    Swal.fire({
      icon: 'error',
      title: '인증번호를 입력하세요!',
    })
  }
  else if (accountStore.isAllowPassword) {
    Swal.fire({
      icon: 'error',
      title: '비밀번호를 입력하세요!',
    })
  }
  else if (accountStore.passwordAccordance != accountStore.passwordAccordance2) {
    Swal.fire({
      icon: 'error',
      title: '비밀번호가 일치하지 않습니다!',
    })
  }
  else {
    // 사용한 피니아 변수들 감기
    credentialsSignup.password = accountStore.passwordAccordance
    credentialsSignup.profileImage = accountStore.myImage

    if (leagueStore.selectLeague.length == 3) {
      credentialsSignup.favoriteLeagueList = [leagueStore.selectLeague[0].apiId, leagueStore.selectLeague[1].apiId, leagueStore.selectLeague[2].apiId]
    }
    else if (leagueStore.selectLeague.length == 2) {
      credentialsSignup.favoriteLeagueList = [leagueStore.selectLeague[0].apiId, leagueStore.selectLeague[1].apiId ]
    }
    else if (leagueStore.selectLeague.length == 1) {
      credentialsSignup.favoriteLeagueList = [leagueStore.selectLeague[0].apiId]
    }

    // 사용한 피니아 변수들 초기화
    accountStore.passwordAccordance = ''
    accountStore.passwordAccordance2 = ''
    accountStore.myImage = ''
    leagueStore.selectLeague = []
    leagueStore.selectTeam = []

    // 회원가입 진행
    accountStore.signUp(credentialsSignup)
  }
}
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