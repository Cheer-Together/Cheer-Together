<template>
  <RouterView/>
  <LoginView/>
</template>

<script setup>
import LoginView from "./views/LoginView.vue"
import { useLeagueStore } from "@/store"

const leagueStore = useLeagueStore()
leagueStore.leaguesAll() 
</script>

<style>
@font-face {
    font-family: 'MICEGothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic Bold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
}
@font-face {
    font-family: 'MICEGothic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic.woff2') format('woff2');
    font-weight: 400;
    font-style: normal;
}
:root {
  --main-color: #2E6AFD;
  --sub-color: #F2F8FF;
  /* --main-color: rgb(78, 167, 235);
  --sub-color: rgb(177, 202, 255); */
  --active-color: #604a3e;
  --bold-font: 'MICEGothic Bold';
  
}
label:hover {
  cursor: pointer;
}
body {
  font-family: 'MICEGothic';
  margin: 0;
  color: #3b4151;
  /* color: black; */
}
</style>
