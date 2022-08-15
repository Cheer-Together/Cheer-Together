<template>
  <RouterView />
  <LoginView />
</template>

<script setup>
import LoginView from "./views/LoginView.vue";
import {
  useLeagueStore,
  useGameStore,
  useAccountStore,
  useRoomStore,
} from "@/store";
import { useGamesStore } from "@/store/modules/game";
import jwt_decode from "jwt-decode";
import { ref } from "vue";

const accountStore = useAccountStore();
const leagueStore = useLeagueStore();
const gameStore = useGameStore();
const roomStore = useRoomStore();
const gamesStore = useGamesStore();

// 로그인 유저인지 확인
const toke = ref(sessionStorage.getItem("token") ?? "");
const decoded = ref("");
if (toke.value) {
  decoded.value = jwt_decode(toke.value);
}

// 로그인 유저라면 id값 가져와서 프로필에 저장
if (accountStore.isLogin) {
  console.log(decoded.value.sub);
  accountStore.profileId = decoded.value.sub;
  accountStore.userProfile(decoded.value.sub);
}
accountStore.getPointRanking();

// 리그데이터 불러오기
leagueStore.leaguesAll();
leagueStore.getLeaguesNoTeam();

// 경기 일정 데이터 불러오기 
gameStore.generateTodayGames()

// 응원방 데이터 불러오기
roomStore.getRooms();

gamesStore.getAllLiveGames();
</script>

<style>
@font-face {
  font-family: "MICEGothic Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic Bold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}
@font-face {
  font-family: "MICEGothic";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic.woff2")
    format("woff2");
  font-weight: 400;
  font-style: normal;
}
:root {
  --main-color: #2e6afd;
  --sub-color: #f2f8ff;
  /* --main-color: rgb(78, 167, 235);
  --sub-color: rgb(177, 202, 255); */
  --active-color: #2ecc71;
  /* 네브 바 */
  --navbar-back-color: #ffffff;
  --navbar-border-color: black;
  /* 사이드 바 */
  --sidebar-back-color: #ffffff;
  --sidebar-border-color: black;
  --bold-font: "MICEGothic Bold";
  /* 카드 */
  --card-color: black;
}
label:hover {
  cursor: pointer;
}
body {
  font-family: "MICEGothic";
  margin: 0;
  color: #3b4151;
  /* color: black; */
}
</style>
