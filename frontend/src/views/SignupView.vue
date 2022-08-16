<template>
  <NavBar/>  
  <div style="display:flex;">
    <SideBar/>
    <div class="signup">
      <!-- 이미지 ㅜㅜ -->
      <!-- <div class="signup-image" v-if="!accountStore.myImage">
        <label for="input-file" style="padding:66px 30px 66px 35px;">
          <v-icon class="sideBar-item-icon">
            mdi-image-search
          </v-icon>
          이미지 올리기
        </label>
      </div> -->

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
          <v-btn style="color:white;" class="signup-range-button" @click.prevent="accountStore.checkEmail(credentialsSignup.email)">
            중복 확인
          </v-btn>
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
          <v-btn v-if="!accountStore.isPushEmail" class="signup-range-button" @click.prevent="pushEmail(credentialsSignup.email)" style="background-color:brown; color:white;">
            보내기 
          <v-icon>
            mdi-send
          </v-icon>
          </v-btn>

          <v-btn style="color:white;" v-else class="signup-range-button" @click.prevent="accountStore.emailAuthCodeCheck(userInputEmailAuthCode)">
            확인
          </v-btn>
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
          <input type="text" class="signup-range-input" maxlength="10" v-model="credentialsSignup.nickname">
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
          <textarea cols="21" rows="5" maxlength="180" class="signup-range-input-introduce" v-model="credentialsSignup.myInfo"></textarea>
        </div>
      </div>
      <!-- 좋아하는 리그 -->
      <div class="signup-range" style="height:230px;">
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
          <div v-if="accountStore.profile.favoriteLeagueList.length !== 0" class="signup-favorite-league" >
            <div class="signup-favorite-league-item" style="margin-left:0px;">
              <img :src="accountStore.profile.favoriteLeagueList[0].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[0].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length >= 2" >
              <img :src="accountStore.profile.favoriteLeagueList[1].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[1].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteLeagueList.length == 3" >
              <img :src="accountStore.profile.favoriteLeagueList[2].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteLeagueList[2].hanName }}
              </div>
            </div>
          </div>        
        </div>
      </div>

      <!-- 좋아하는 팀 -->
      <div class="signup-range" style="height:230px;">
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
          <div v-if="accountStore.profile.favoriteTeamList.length !== 0" class="signup-favorite-league">
            <div class="signup-favorite-league-item" style="margin-left:0px;">
              <img :src="accountStore.profile.favoriteTeamList[0].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[0].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteTeamList.length >= 2" >
              <img :src="accountStore.profile.favoriteTeamList[1].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[1].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteTeamList.length >= 3" >
              <img :src="accountStore.profile.favoriteTeamList[2].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[2].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteTeamList.length >= 4" >
              <img :src="accountStore.profile.favoriteTeamList[3].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[3].hanName }}
              </div>
            </div>

            <div class="signup-favorite-league-item" v-if="accountStore.profile.favoriteTeamList.length == 5" >
              <img :src="accountStore.profile.favoriteTeamList[4].logo" class="signup-favorite-league-image" style="width:100px; height:100px; object-fit: contain;">
              <div class="signup-favorite-league-item-title" style="font-size:16px; width:100px;">
                {{ accountStore.profile.favoriteTeamList[4].hanName }}
              </div>
            </div>
          </div>  
        </div>
      </div>

      <!-- 회원 가입!! -->
      <div class="signup-range-bottom" style="margin-top: 120px;">
        <v-btn class="signup-submit-button"  @click="router.push({ name:'Mypage', params: { userid: accountStore.profileId } })" style="height:50px; font-size:21px;">
          이전
        </v-btn>
        <v-btn class="signup-submit-button next" @click.prevent="changeSignUp(credentialsSignup)" style="height:50px; font-size:21px; color:white;">
          다음
        </v-btn>
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
accountStore.profile.favoriteLeagueList = []
accountStore.profile.favoriteTeamList = []

let userInputEmailAuthCode = ''
let credentialsSignup = {
  email: "",
  favoriteLeagueList: [ 
  ],
  favoriteTeamList: [
  ],
  myInfo: "",
  nickname: "",
  password: '',
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
      title: '회원가입 실패! 😢',
      text: '이메일 중복을 확인하세요.',
      confirmButtonText: '확인'
    })
  }
  else {
    Swal.fire({
      icon: 'success',
      title: '인증메일 발송! 📨',
      text: '이메일에 인증번호를 발송했습니다.',
      confirmButtonText: '확인'
    })
    accountStore.isPushEmail = true
    accountStore.emailAuth(email)
  }
}

const changeSignUp = (credentialsSignup) => {
  if (!accountStore.emailDoubleChecked) {
    Swal.fire({
      icon: 'error',
      title: '회원가입 실패! 😢',
      text: '이메일 중복을 확인하세요.',
      confirmButtonText: '확인'
    })
  }
  else if (!accountStore.emailAuthCodeChecked) {
    Swal.fire({
      icon: 'error',
      title: '회원가입 실패! 😢',
      text: '인증번호를 입력하세요.',
      confirmButtonText: '확인'
    })
  }
  else if (accountStore.isAllowPassword) {
    Swal.fire({
      icon: 'error',
      title: '회원가입 실패! 😢',
      text: '비밀번호를 입력하세요.',
      confirmButtonText: '확인'
    })
  }
  else if (accountStore.passwordAccordance != accountStore.passwordAccordance2) {
    Swal.fire({
      icon: 'error',
      title: '회원가입 실패! 😢',
      text: '비밀번호 확인이 일치하지 않습니다.',
      confirmButtonText: '확인'
    })
  }
  else if ( accountStore.isShowPasswordError === true ) {
    Swal.fire({
      icon: 'error',
      title: '회원가입 실패! 😢',
      text: '올바르지 않은 비밀번호입니다. (영어, 숫자, 특수문자 조합 8~20자)',
      confirmButtonText: '확인'
    })
  } 
  else {
    // 사용한 피니아 변수들 감기
    credentialsSignup.password = accountStore.passwordAccordance

    if (accountStore.profile.favoriteLeagueList.length == 3) {
      credentialsSignup.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId, accountStore.profile.favoriteLeagueList[1].apiId, accountStore.profile.favoriteLeagueList[2].apiId]
    }
    else if (accountStore.profile.favoriteLeagueList.length == 2) {
      credentialsSignup.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId, accountStore.profile.favoriteLeagueList[1].apiId ]
    }
    else if (accountStore.profile.favoriteLeagueList.length == 1) {
      credentialsSignup.favoriteLeagueList = [accountStore.profile.favoriteLeagueList[0].apiId]
    }

    if (accountStore.profile.favoriteTeamList.length == 5) {
      credentialsSignup.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId, accountStore.profile.favoriteTeamList[3].apiId, accountStore.profile.favoriteTeamList[4].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 4) {
      credentialsSignup.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId, accountStore.profile.favoriteTeamList[3].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 3) {
      credentialsSignup.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId, accountStore.profile.favoriteTeamList[2].apiId]
    }
    else if (accountStore.profile.favoriteTeamList.length == 2) {
      credentialsSignup.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId, accountStore.profile.favoriteTeamList[1].apiId ]
    }
    else if (accountStore.profile.favoriteTeamList.length == 1) {
      credentialsSignup.favoriteTeamList = [accountStore.profile.favoriteTeamList[0].apiId]
    }
    // 사용한 피니아 변수들 초기화
    accountStore.passwordAccordance = ''
    accountStore.passwordAccordance2 = ''
    accountStore.profile.favoriteLeagueList = []
    accountStore.profile.favoriteTeamList = []

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
  padding: 10px;
  resize: none;
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
  width: 100px;
  height: 120px;
}
.signup-favorite-league-image {
  width: 100px;
  height: 100px;
}
.signup-favorite-league-item-title {
  width: 100px;
  height: 20px;
  text-align: center;
  font-size: 16px;
  font-family: var(--bold-font);
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