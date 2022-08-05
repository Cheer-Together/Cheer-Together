<template>
  <div class="favoriteTeam">
    <div class="favoriteTeam-title">
      좋아하는 팀
    </div>
    <div class="favoriteTeam-section" @click="accountStore.loginDialogToggle()">
      <div class="favoriteTeam-section-title">
        로그인 후 이용해 주세요!
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAccountStore } from '@/store'
const accountStore = useAccountStore()
</script>

<style>
.favoriteTeam {
  margin-bottom: 40px;
}
.favoriteTeam-title {
  font-size: 24px;
  padding: 5.5px 0;
}
.favoriteTeam-section {
  margin-top: 11px;
  width: 1580px;
  height: 190px;
  text-align: center;
  background-color: var(--sub-color);
  border-radius: 4px;
}
.favoriteTeam-section:hover, .favoriteTeam-section:hover .favoriteTeam-section-title{
  cursor: pointer;
  font-size: 24px;
}

.favoriteTeam-section-title {
  padding-top: 78px ;
  font-size: 20px;
}

@media (max-width: 1580px) {
.favoriteTeam {
  margin-bottom: 16px;
}
.favoriteTeam-title {
  font-size: 15px;
}
.favoriteTeam-section {
  margin-top: 8px;
  width: 1300px;
  height: 170px;
}
.favoriteTeam-section-title {
  padding-top: 78px ;
}
}
</style>