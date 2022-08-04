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
            <div style="display: flex; flex-direction: column; align-items: center;">
              <div style="width:550px; height:100px; margin-top: 50px">
                <div style="height:80px; width:80px; background-color: aquamarine; margin: auto;">
                </div>
              </div>
              <div style="height:20px; font-size:18px; margin-top: 10px;">
                <p>로그인 후 이용하실 수 있습니다.</p>
              </div>
              <div style="margin-top:20px">
                <input v-model="loginId" type="text" placeholder=" 아이디" style="width:340px; height:50px; border-radius:1px; border: 1px solid #bcbcbc;">
              </div>
              <div style="margin-top:20px">
                <input v-model="loginPassword" type="password" placeholder=" 비밀번호" style="width:340px; height:50px; border-radius:1px; border: 1px solid #bcbcbc;">
              </div>
              <v-btn
                style="margin-top:22px; color:white;"
                color="#2E6AFD"
                width="340px"
                @click.prevent="loginButton()"
              >
                로그인
              </v-btn>
              <div style="margin-top: 70px;">
                <a class="word-link">아이디 찾기</a><a> | </a>
                <a class="word-link" @click.prevent="passwordModalBtn()">비밀번호 찾기</a><a> | </a>
                <a class="word-link" @click.prevent="toSignupBtn()">회원가입</a>
              </div>
              <v-btn
                style="margin-top:22px;  color:white;"
                color="#1EC800"
                width="340px"
                @click.prevent="social='네이버', dialog2=true"
              >
              네이버로 로그인
              </v-btn>
              <v-btn
                style="margin-top:22px;"
                color="#FEE500"
                width="340px"
                @click.prevent="social='카카오', dialog2=true"
              >
              카카오로 로그인
              </v-btn>
            </div>
            
          </v-card-text>

          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="accountStore.loginDialogToggle()"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!-- 모달창2 -->
      <v-dialog
        v-model="dialog2"
      >
        <v-card>
          <v-card-title>
            소셜 로그인 페이지
          </v-card-title>
          <v-card-text>
            <p>
              {{social}}로 로그인 페이지
            </p>
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="dialog2 = false"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog
        v-model="dialog3"
      >
        <v-card>
          <v-card-title>
            <span>에러 표시용 모달</span>
          </v-card-title>
          <v-card-text>
            아이디와 비밀번호를 입력해주세요
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
            <div style="display: flex; flex-direction: column; align-items: center;">
              <div style="width:340px; height:30px; font-size:24px; margin-top: 10px; text-align:left">
                <p>비밀번호를 재설정합니다.</p>
              </div>
              <div style="width:340px; height:20px; font-size:18px; margin-top: 40px;">
                <a>가입하신 계정의 이메일을 입력해 주세요</a>
              </div>
              <div style="margin-top:20px">
                <input v-model="findPasswordEmail" type="text" placeholder=" 이메일" style="width:340px; height:50px; border-radius:1px; border: 1px solid #bcbcbc;">
              </div>
              <v-btn
                style="margin-top:20px; color:white;"
                color="#2E6AFD"
                width="340px"
                @click.prevent="findPasswordBtn()"
              >
                다음
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
import { ref } from 'vue'
import { useAccountStore } from "@/store"
import router from '@/router/index.js';
const accountStore = useAccountStore()
const dialog2 = ref(false) // 아이디, 비밀번호 잘못 입력시
const dialog3 = ref(false) // 추후에 소셜 로그인을 위한 모달
const findPasswordModal = ref(false)
const findPasswordEmail = ref('')
const loginId = ref('')
const loginPassword = ref('')
const social = ref('')
function loginButton() {
  if (this.loginId&&this.loginPassword) {
    const user = {email:this.loginId, password:this.loginPassword}
    accountStore.loginAccount(user)
    accountStore.loginDialogToggle()
    this.loginId = ''
    this.loginPassword = ''
  } else {
    this.dialog3 = true
  }
}
function passwordModalBtn() {
  this.findPasswordModal = true
}
function findPasswordBtn() {
  accountStore.findPassword(this.findPasswordEmail)
  this.findPasswordEmail = ''
}
function toSignupBtn() {
  accountStore.loginDialogToggle()
  this.loginId = ''
  this.loginPassword = ''
  router.push({name: 'Signup'})
}
</script>

<style>
.word-link:hover {
  color: var(--main-color);
  cursor:pointer
}
</style>