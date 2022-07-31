<template>
  <div class="navBar">
    <img :src="navBarLogo" class="navBar-logo">
    <div class="navBar-items">
      <div class="navBar-item">
        로그인
      </div>
      <div class="navBar-item" @click="this.$router.push({ name:'Signup', })">
        회원가입
      </div>
      <div>
      </div>
    </div>
  </div>
</template>

<script setup>
const navBarLogo = require('../assets/image/로고.png');
</script>

<style>
.navBar {
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid var(--main-color);
  position: fixed;
  background-color: #ffffff;
  top: 0;
  z-index: 1;
}
.navBar-logo {
  margin-left: 30px;
  width: 80px;
}
.navBar-items {
  display: flex;
  margin-right: 100px;
}
.navBar-item {
  width: 100px;
  height: 18px;
  text-align: center;
  margin: auto 0;
}
.navBar-item:hover {
  color: var(--main-color);
  cursor: pointer;
}

@media (max-width: 1580px) {
.navBar {
  height: 60px;
}
.navBar-logo {
  margin-left: 20px;
  width: 60px;
}
.navBar-items {
  display: flex;
  margin-right: 30px;
}
.navBar-item {
  font-size: 12px;
  width: 80px;
  height: 18px;
}
}
</style>