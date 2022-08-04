<template>
  <v-card class="favorite-league-modal">
    <!-- 헤더 영역 -->
    <div class="favorite-league-modal-title">
      좋아하는 리그
    </div>
    <button @click="leagueStore.favoriteLeague = false" style="position:absolute; top:0; right:0;">
      <v-icon v-bind="props = false">
        mdi-close-box
      </v-icon>
    </button>
    <div class="favorite-league-modal-subtitle">
      좋아하는 리그를 정해보세요!
    </div>
    <!-- 섹션 -->
    <div class="favorite-league-modal-section">
      <div v-for="bigLeague in leagueStore.leagues" :key="bigLeague.id" class="favorite-league-modal-section-item" @click="addSelectLeague(bigLeague)">
        <img :src="bigLeague.logo" >
        <div class="bigleague-title">
          {{ bigLeague.hanName }}
        </div>
      </div>

    </div>
    <!-- 푸터 -->
    <div v-if="leagueStore.selectLeague.length !== 0" class="favorite-league-modal-footer">
      <div class="favorite-league-modal-footer-item" @click="spliceLeague(leagueStore.selectLeague[0])">
        <img :src="leagueStore.selectLeague[0].logo" class="favorite-league-modal-footer-image">
        <div class="favorite-league-modal-footer-item-title">
          {{ leagueStore.selectLeague[0].hanName }}
        </div>
      </div>

      <div class="favorite-league-modal-footer-item" v-if="leagueStore.selectLeague.length >= 2" @click="spliceLeague(leagueStore.selectLeague[1])">
        <img :src="leagueStore.selectLeague[1].logo" class="favorite-league-modal-footer-image">
        <div class="favorite-league-modal-footer-item-title">
          {{ leagueStore.selectLeague[1].hanName }}
        </div>
      </div>

      <div class="favorite-league-modal-footer-item" v-if="leagueStore.selectLeague.length == 3" @click="spliceLeague(leagueStore.selectLeague[2])">
        <img :src="leagueStore.selectLeague[2].logo" class="favorite-league-modal-footer-image">
        <div class="favorite-league-modal-footer-item-title">
          {{ leagueStore.selectLeague[2].hanName }}
        </div>
      </div>

      <button class="save-button" @click="leagueStore.favoriteLeague = false">저장</button>
    </div>

  </v-card>
</template>

<script setup>
import { useLeagueStore } from "@/store"
const leagueStore = useLeagueStore()

leagueStore.selectFavoriteTeam = '프리미어리그'
const addSelectLeague = (bigLeague) => {
  if (leagueStore.selectLeague.length < 3){
    if (leagueStore.selectLeague.includes(bigLeague)) {
      const smallNumberIndex = leagueStore.selectLeague.indexOf(bigLeague);
      leagueStore.selectLeague.splice(smallNumberIndex, 1)
    }
    else {
      leagueStore.selectLeague.push(bigLeague);
    }
  }
  else if (leagueStore.selectLeague.length === 3) {
    if (leagueStore.selectLeague.includes(bigLeague)) {
      const smallNumberIndex = leagueStore.selectLeague.indexOf(bigLeague);
      leagueStore.selectLeague.splice(smallNumberIndex, 1)
    }
  }
}

const spliceLeague = (myLeague) => {
      if (leagueStore.selectLeague.includes(myLeague)) {
      const smallNumberIndex = leagueStore.selectLeague.indexOf(myLeague);
      leagueStore.selectLeague.splice(smallNumberIndex, 1)
    }
}
</script>

<style>
.favorite-league-modal {
  width: 600px;
  height: 720px;
  position: relative;
}
.favorite-league-modal-title {
  font-family: 'MICEGothic Bold';
  font-size: 40px;
  width: 250px;
  height: 50px;
  margin: 27px auto 0;
}
.favorite-league-modal-subtitle {
  font-size: 20px;
  width: 250px;
  height: 20px;
  margin: 31px auto 22px;
  color: var(--main-color);
}
.favorite-league-modal-section {
  width: 600px;
  height: 450px;
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  border-top: 1px solid black;
  padding-left: 25px;
}
.favorite-league-modal-section-item:hover {
  cursor: pointer;
  transform: scale(1.1);
}
.favorite-league-modal-section-item {
  margin: 50px 25px 0px 25px;
  width: 133px;
  height: 156px;
}
.favorite-league-modal-section-item img {
  width: 133px;
  height: 133px;
}
.bigleague-title {
  width: 133px;
  height: 17px;
  text-align: center;
  font-size: 15px;
}
.favorite-league-modal-footer {
  border-top: 1px solid black;
  display: flex;
  position: relative;
}
.favorite-league-modal-footer-item {
  margin: 15px 20px;
}
.favorite-league-modal-footer-image {
  width: 70px;
  height: 70px;
}
.favorite-league-modal-footer-item-title {
  width: 70px;
  height: 12px;
  text-align: center;
  font-size: 12px;
}
.save-button {
  position: absolute;
  width: 100px;
  height: 40px;
  bottom: 40px;
  color: white;
  background-color: #0066A2;
  border-radius: 5px;
  right: 15px;
}
@media (max-width: 1580px) {
  .favorite-league-modal {
    transform: scale(0.8);
    min-height: 720px;
  }
  .favorite-league-modal-section {
    min-height: 440px;
  }
}
</style>