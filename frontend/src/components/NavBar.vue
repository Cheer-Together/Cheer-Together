<template>
  <div class="NavBar">
    <img :src="navBarLogo" class="NavBar-logo">
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
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid var(--main-color);
  position: fixed;
  background-color: #ffffff;
}
.NavBar-logo {
  margin-left: 30px;
  width: 80px;
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

@media (max-width: 1580px) {
.NavBar {
  height: 60px;
}
.NavBar-logo {
  margin-left: 20px;
  width: 60px;
}
.NavBar-items {
  display: flex;
  margin-right: 30px;
}
.NavBar-item {
  font-size: 12px;
  width: 80px;
  height: 18px;
}
}
</style>