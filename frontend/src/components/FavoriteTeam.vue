<template>
  <div class="favoriteTeam">
    <div class="favoriteTeam-title">
      좋아하는 팀
    </div>
    <div class="favoriteTeam-section">
      <div class="favoriteTeam-section-title">
        로그인 후 이용해 주세요!
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style>
.favoriteTeam {
  margin-bottom: 21px;
}
.favoriteTeam-title {
  font-size: 18px;
  padding: 5.5px 0;
}
.favoriteTeam-section {
  margin-top: 11px;
  width: 1580px;
  height: 170px;
  text-align: center;
  background-color: var(--sub-color);
}
.favoriteTeam-section:hover {
  cursor: pointer;
}
.favoriteTeam-section-title {
  padding-top: 78px ;
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