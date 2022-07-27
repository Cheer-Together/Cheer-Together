<template>
  <div class="FavoriteTeam">
    <div class="FavoriteTeam-title">
      좋아하는 팀
    </div>
    <div class="FavoriteTeam-section">
      <div class="FavoriteTeam-section-title">
        로그인 후 이용해 주세요!
      </div>
    </div>
  </div>
</template>

<script setup>

</script>

<style>
.FavoriteTeam {
  margin-bottom: 21px;
}
.FavoriteTeam-title {
  font-size: 18px;
  padding: 5.5px 0;
}
.FavoriteTeam-section {
  margin-top: 11px;
  width: 1580px;
  height: 170px;
  text-align: center;
  background-color: var(--sub-color);
}
.FavoriteTeam-section:hover {
  cursor: pointer;
}
.FavoriteTeam-section-title {
  padding-top: 78px ;
}

@media (max-width: 1580px) {
.FavoriteTeam {
  margin-bottom: 16px;
}
.FavoriteTeam-title {
  font-size: 15px;
}
.FavoriteTeam-section {
  margin-top: 8px;
  width: 1300px;
  height: 170px;
}
.FavoriteTeam-section-title {
  padding-top: 78px ;
}
}
</style>