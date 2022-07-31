<template>
  <v-card class="favorite-team-modal">
    <!-- 헤더 영역 -->
    <div class="favorite-team-modal-header">
      <!-- 메인 타이틀 -->
      <div class="favorite-team-modal-header-title">
        좋아하는 팀
      </div>
      <!-- 서브 타이틀 -->
      <div class="favorite-team-modal-header-subtitle">
        좋아하는 팀을 정해보세요!
      </div>
    </div>
    <!-- 리그 선택 영역 -->
    <div class="favorite-team-modal-select-buttons">

    </div>
  </v-card>
</template>

<script setup>
// import { useSignupStore } from "@/store"

// const signupStore = useSignupStore()



</script>

<style>
.favorite-team-modal-header {
  width: 600px;
  height: 150px;
  border-bottom: 1px solid black;
}
.favorite-team-modal-header-title {
  height: 50px;
  font-family: 'MICEGothic Bold';
  font-size: 40px;
  text-align: center;
  margin: 27px auto;
}
.favorite-team-modal-header-subtitle {
  font-size: 20px;
  text-align: center;
  margin: 4px auto 22px;
  color: var(--main-color);
  
}
.favorite-team-modal-select-buttons {
  width: 600px;
  height: 100px;
}
</style>