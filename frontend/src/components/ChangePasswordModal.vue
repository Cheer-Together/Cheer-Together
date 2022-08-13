<template>
  <v-card class="change-password-modal">
    <div class="change-password-modal-header">
      <img :src="require('../assets/image/로고.png')" alt="" width="200">
      <div class="change-password-header-title">
        비밀번호 변경
      </div>
    </div>
    <div class="change-password">
      <div class="change-password-title">
        이전 비밀번호
      </div>
      <div style="position: relative;">
        <input type="password" class="change-password-input" maxlength="20" v-model="accountStore.passwordAccordance" >
      </div>
    </div>
    

    <!-- 새로운 비밀번호 -->
    <div class="change-password">
      <div class="change-password-title">
        새로운 비밀번호
      </div>
      <div style="position: relative;">
        <input type="password" class="change-password-input" maxlength="20" v-model="accountStore.passwordAccordance2" @blur="accountStore.checkPassword(accountStore.passwordAccordance2)">
      </div>
    </div>
    <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === true">
      올바르지 않은 비밀번호입니다. 영어, 숫자, 특수문자 조합 8- 20자
    </div>

    <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError === false" style="color: var(--main-color)">
      올바른 비밀번호입니다.
    </div>

    <div class="change-password">
      <div class="change-password-title">
        새로운 비밀번호 확인
      </div>
      <div style="position: relative;">
        <input type="password" class="change-password-input" maxlength="20" v-model="passwordAccordance3" @blur="checkPassword()" @keyup.enter="myChangePassword">
      </div>
    </div>

    <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError2 === true">
      비밀번호가 일치하지 않습니다.
    </div>

    <div class="err-password-accordance"  v-if="accountStore.isShowPasswordError2 === false" style="color: var(--main-color)">
      비밀번호가 일치합니다.
    </div>

    <button @click="myChangePassword" class="change-password-button">비밀번호 변경</button>
  </v-card>
</template>

<script setup>
import { useAccountStore } from "@/store"
import Swal from 'sweetalert2'

const accountStore = useAccountStore()


// 회원가입 시 변수 초기화 영역
accountStore.passwordAccordance = '';
accountStore.passwordAccordance2 = '';
accountStore.isShowPasswordError = '';

let passwordAccordance3 = ''
const checkPassword = () => {
  if (passwordAccordance3 === accountStore.passwordAccordance2) {
    accountStore.isShowPasswordError2 = false
  }
  else {  
   accountStore.isShowPasswordError2 = true
  }
}
const myChangePassword = () => {
  const userInfo = {
    userId: accountStore.profileId,
    password1: accountStore.passwordAccordance,
    password2: accountStore.passwordAccordance2,
  }
  accountStore.passwordAccordance = '';
  accountStore.passwordAccordance2 = '';
  console.log(accountStore.isShowPasswordError)
  if (accountStore.isShowPasswordError === true) {
    accountStore.isChangePasswordModal = false
    // accountStore.isChangePasswordModal = true
    Swal.fire({
      icon: 'error',
      title: '올바른 비밀번호가 아닙니다.',
    })
  }
  else {
    accountStore.changePassword(userInfo)
  }
    accountStore.isShowPasswordError = '';
}
</script>

<style scoped>
.change-password-modal {
  width: 600px;
  height: 900px;
}
.change-password-modal-header {
  width: 400px;
  margin: 0 auto;
}
 .change-password-modal-header > img {
  width: 200px;
  height: 200px;
  margin-left: 100px;
 }
.change-password-header-title {
  font-family: var(--bold-font);
  color: var(--main-color);
  font-size: 20px;
  width: 400px;
  text-align: center;
  padding-bottom: 30px;
  margin-bottom: 50px;
  border-bottom: 1px solid gray;
}
.change-password {
  width: 420px;
  height: 83px;
  font-size: 21px;
  margin: 0 auto 20px;
}
.change-password-input {
  width: 420px;
  height: 50px;
  border: 1px solid #D9D9D9;
  white-space: nowrap;
  padding-left: 10px;
  padding-right: 120px;
  
}
.change-password-button {
  position: fixed;
  bottom: 30px;
  left: calc(50% - 100px);
  width: 200px;
  height: 50px;
  margin: 0 auto;
  background-color: #0066A2;
  color: white;
  border-radius: 5px;
  font-size: 20px;
}
</style>