<template>
  <div>
    <v-row
      justify="center"
    >
      <!-- 여기부터 모달창 -->
      <v-dialog
        v-model="accountStore.loginDialog"
      >
        <v-card>
          <v-card-text>
            <div class="login-dialog">
              <div class="login-dialog-logo">
                <img :src="loginLogo" class="login-logo">
              </div>
              <div class="login-dialog-info">
                <p>{{accountStore.loginDialogMsg}}</p>
              </div>
              <div style="margin-top:20px">
                <input v-model="loginId" type="text" placeholder="아이디" class="login-dialog-textinput">
              </div>
              <div style="margin-top:20px">
                <input v-model="loginPassword" @keyup.enter="loginButton()" type="password" placeholder="비밀번호" class="login-dialog-textinput">
              </div>
              <div style="margin-top: 20px;">
                <a class="word-link" @click="passwordModalBtn()">비밀번호 찾기</a><a> | </a>
                <a class="word-link" @click="toSignupBtn()">회원가입</a>
              </div>
              <v-btn
                v-if="isValidId"
                style="margin-top:22px; color:white;"
                color="#2E6AFD"
                width="340px"
                @click.prevent="loginButton()"
              >
                로그인
              </v-btn>
              <v-btn
                v-else
                style="margin-top:22px;"
                width="340px"
                disabled
              >
                아이디가 유효하지 않습니다.
              </v-btn>
              <v-btn
                style="margin-top:22px; margin-bottom: 40px"
                color="#FEE500"
                width="340px"
                @click.prevent="accountStore.kakaoLogin()"
              >
              카카오로 로그인
              </v-btn>
            </div>   
          </v-card-text>
        </v-card>
      </v-dialog>
      <!-- 모달창2 -->
      <v-dialog
        v-model="dialog3"
      >
        <v-card>
          <v-card-text>
            {{errorText}}
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="dialog3 = false"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <!-- 비밀번호 찾기 모달 -->
      <v-dialog
        v-model="findPasswordModal"
      >
        <v-card>
          <v-card-text>
            <div class="login-dialog">
              <div class="password-dialog-top">
                <p>비밀번호를 재설정합니다.</p>
              </div>
              <div class="password-dialog-info">
                <a>가입하신 계정의 이메일을 입력해 주세요</a>
              </div>
              <div style="margin-top:20px">
                <input v-model="findPasswordEmail" type="text" placeholder=" 이메일" class="password-dialog-textinput">
              </div>
              <v-btn v-if="isVaildEmail"
                style="margin-top:20px; color:white;"
                color="#2E6AFD"
                width="340px"
                @click.prevent="findPasswordBtn()"
              >
                다음
              </v-btn>
              <v-btn v-else
                style="margin-top:20px;"
                width="340px"
                disabled
              >
                유효한 이메일을 입력하세요
              </v-btn>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="findPasswordModal = false"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script setup>
import { ref,watchEffect } from 'vue'
import { useAccountStore } from "@/store"
import router from '@/router/index.js'
import axios from 'axios'
const accountStore = useAccountStore()
const dialog3 = ref(false) // 에러 발생시 모달
const findPasswordModal = ref(false)
const findPasswordEmail = ref('')
const loginId = ref('')
const loginPassword = ref('')
const loginLogo = require('../assets/image/로고.png')
const isVaildEmail = ref(false)
const isValidId = ref(false)
const errorText = ref('')
function loginButton() {
  if (loginId.value&&loginPassword.value) {
    const user = {email:loginId.value, password:loginPassword.value}
    accountStore.loginAccount(user)
    accountStore.loginDialogToggle()
    loginId.value = ''
    loginPassword.value = ''
  } else {
    errorText.value = '아이디와 비밀번호 형식이 올바르지 않습니다.'
    dialog3.value = true
  }
}
function passwordModalBtn() {
  findPasswordModal.value = true
}
function findPasswordBtn() {
  findPasswordModal.value = false
  axios({
    url: 'https://i7b204.p.ssafy.io/cheertogether/members/find/password',
    method: "GET",
    params: {
      email: findPasswordEmail.value,
    },
  })
  .then((res) => {
    console.log(res.data)
    dialog3.value = true
    errorText.value = '해당 이메일로 임시 비밀번호를 전송했습니다.'
  })
  .catch((err) => {
    console.log(err)
    dialog3.value = true
    errorText.value = '올바른 이메일인지 확인해주세요.'
  })
  findPasswordEmail.value = ''
}
watchEffect(() => {
    const emailRule = /^([0-9a-zA-Z_.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/
    isVaildEmail.value = emailRule.test(findPasswordEmail.value)
    isValidId.value = emailRule.test(loginId.value)
})
function toSignupBtn() {
  accountStore.loginDialogToggle()
  loginId.value = ''
  loginPassword.value = ''
  router.push({name: 'Signup'})
}
</script>

<style>
.login-dialog {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.login-dialog-logo {
  display: flex;
  justify-content: center;
  width:450px;
  height:140px;
  margin-top: 10px;
}
.login-dialog-info {
  height:20px;
  font-size:18px;
  margin-top: 10px;
  font-family: 'MICEGothic Bold';
}
.login-dialog-textinput {
  width:340px;
  height:50px;
  border-radius:1px;
  border: 1px solid #bcbcbc;
  padding-left: 10px;
}
.password-dialog-top {
  width:340px;
  height:30px;
  font-size:24px;
  margin-top: 10px;
  text-align:left
}
.password-dialog-info {
  width:340px;
  height:20px;
  font-size:18px;
  margin-top: 40px;
}
.password-dialog-textinput {
  width:340px;
  height:50px;
  border-radius:1px;
  border: 1px solid #bcbcbc;
}
.word-link:hover {
  color: var(--main-color);
  cursor: pointer
}
.login-logo {
  width: 140px;
  height: 140px;
}
</style>