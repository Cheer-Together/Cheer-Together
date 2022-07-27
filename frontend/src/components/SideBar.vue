<template>
  <div class="SideBar">
    <!-- 홈 -->
    <div class="SideBar-item" @click="this.$router.push({name:'MainPage'})">
      <v-icon class="SideBar-item-icon">
        mdi-home
      </v-icon>
      <div class="SideBar-item-title">
        홈
      </div>
    </div>
    <!-- 실시간 집관 -->
    <div class="SideBar-item">
      <v-icon class="SideBar-item-icon">
        mdi-video-outline
      </v-icon>
      <div class="SideBar-item-title">
        실시간 집관
      </div>
    </div>
    <!-- 프리미어리그 -->
    <div class="SideBar-subtitle">
      프리미어리그
    </div>
    <!-- 라리가 -->
    <div class="SideBar-subtitle">
      라리가
    </div>
    <!-- 세리에 A -->
    <div class="SideBar-subtitle">
      세리에 A
    </div>
    <!-- 분데스리가 -->
    <div class="SideBar-subtitle">
      분데스리가
    </div>
    <!-- 리그 1 -->
    <div class="SideBar-subtitle">
      리그 1
    </div>
    <!-- K리그 1 -->
    <div class="SideBar-subtitle">
      K리그 1
    </div>
    <!-- 경기 일정 -->
    <div class="SideBar-item" @click="this.$router.push({name:'Schedule'})">
      <v-icon class="SideBar-item-icon">
        mdi-calendar-month-outline
      </v-icon>
      <div class="SideBar-item-title">
        경기 일정
      </div>
    </div>
    <!-- 커뮤니티 -->
    <div class="SideBar-item" @click="this.$router.push({name:'ArticleView'})">
      <v-icon class="SideBar-item-icon">
        mdi-account-group
      </v-icon>
      <div class="SideBar-item-title">
        커뮤니티
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style>
.SideBar {
  min-width: 210px;
  min-height: 889px;
  max-height: 3000px;
  border-right: 1px solid var(--main-color);
  position: fixed;
  top: 80px;
}
.SideBar-item {
  font-size: 15px;
  margin: 15px 12px 0px 28px;
  padding: 11px 0px;
}
.SideBar-item:hover {
  background-color: var(--sub-color);
  cursor:pointer;
}
.SideBar-item-icon {
  width: 22px;
  height: 22px;
  font-size: 22px;
  margin-right: 10px;
}
.SideBar-item-title{
  display: inline;
  width: 138px;
  height: 38px;
  font-size: 15px;
  align-items: center;
}
.SideBar-subtitle {
  font-size: 13px;
  margin: 10px 80px 0 60px;
}
.SideBar-subtitle:hover {
  color: var(--main-color);
  cursor:pointer;
}

@media (max-width: 1580px) {
.SideBar {
  min-width: 150px;
  top: 60px;
}
.SideBar-item {
  font-size: 12px;
  margin: 15px 0px 0px 14px;
  padding: 5px 0px;
}
.SideBar-item-icon {
  width: 15px;
  height: 15px;
  font-size: 15px;
  margin-right: 5px;
}
.SideBar-item-title{
  display: inline;
  width: 100px;
  height: 38px;
  font-size: 12px;
  align-items: center;
}
.SideBar-subtitle {
  font-size: 6px;
  margin: 10px 20px 0 40px;
}

}
</style>