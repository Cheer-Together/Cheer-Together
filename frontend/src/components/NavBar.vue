<template>
  <div class="NavBar">
    <img :src="navBarLogo" width="80" class="NavBar-logo">
    <div class="NavBar-items">
      <div class="NavBar-item">
        로그인
      </div>
      <div class="NavBar-item">
        회원가입
      </div>
      <div>
        {{ token }}
      </div>
      <!-- <v-icon>
        mdi-account
      </v-icon> -->
    </div>
  </div>
</template>

<script setup>
const navBarLogo = require('../assets/image/임시로고.png');
</script>

<style>
.NavBar {
  height: 80px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid var(--main-color);
}
.NavBar-logo {
  margin-left: 30px;
}
.NavBar-items {
  display: flex;
  margin-right: 100px;
}
.NavBar-item {
  width: 100px;
  height: 18px;
  text-align: center;
  margin: auto 0;
}
</style>