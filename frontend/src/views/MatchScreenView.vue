<template>
  <NavBar/>
  <div style="display:flex; margin-top: 80px;">
    <!-- 스크린 -->
    <div class="match-screen">
      <!-- 헤더 -->
      <div class="match-screen-header">
        <div style="display:flex;">
          <div class="match-screen-title">
            일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십
          </div>
          <div class="match-screen-icon">
            <v-icon  size="40">
              mdi-account-multiple
            </v-icon>
            6

          </div>

        
        </div>

        <div class="match-screen-layout">
          레이아웃
        </div>
      </div>
      <!-- 스크린 -->
      <div class="match-screen-section">

      </div>
      <!-- 푸터 -->
      <div class="match-screen-footer">
        <!-- 채팅 -->
        <v-icon size="40" @click="chattingOnOff" class="match-screen-footer-icon">
          mdi-chat-processing-outline
        </v-icon>
        <!-- 비디오 -->
        <v-icon size="40" class="match-screen-footer-icon">
          mdi-video-outline
        </v-icon>
        <!-- 마이크 -->
        <v-icon size="40" class="match-screen-footer-icon">
          mdi-microphone
        </v-icon>
        <!-- 응원가 -->
        <v-icon size="40" class="match-screen-footer-icon">
          mdi-bullhorn
        </v-icon>
      </div>
    </div>

    <!-- 채팅 -->
    <div v-if="matchScreenStore.isClickChatting == 'a'" class="match-screen-chatting-area" >
      <div class="match-screen-chatting-header">

      </div>

      <div class="match-screen-chatting-section">

      </div>
      <div class="match-screen-chatting-footer">

      </div>
    </div>
    <!-- 얘는 그냥 사라질 때 자연스러운 효과를 위한 영역 신경 안써도 됩니다. -->
    <div v-if="matchScreenStore.isClickChatting == 'b'" class="match-screen-chatting-area2" >

    </div>
  </div> 
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import { useMatchScreenStore } from "@/store"

const matchScreenStore = useMatchScreenStore()

const chattingOnOff = () => {
  if (matchScreenStore.isClickChatting == '') {
    matchScreenStore.isClickChatting = 'a'
  }
  else if (matchScreenStore.isClickChatting == 'a'){
    matchScreenStore.isClickChatting = 'b'
  }
  else {
    matchScreenStore.isClickChatting = 'a'
  }
}
</script>

<style>
@keyframes chattingOver {
  0% {
    width: 0px;
  }
  100% {
    width: 295px;
  }
}
@keyframes chattingLeave {
  0% {
    width: 295px;
  }
  100% {
    width: 0px;
  }
}
.match-screen {
  width: 100%;
  height: 850px;
  margin-left: 30px;
}
.match-screen-header {
  font-family: 'MICEGothic Bold';
  font-size: 20px;
  width: 100%;
  height: 50px;
  margin: 15px 0px;
  padding: 0 0 0 30px;
  display: flex;
  justify-content: space-between;
}
.match-screen-title {
  width: 740px;
  height: 50px;
  padding: 10px 0 10px 10px;
  background-color: #ecf0f5;
  border-radius: 3px;
}
.match-screen-icon {
  padding-top: 8px;
  margin-left: 10px;
  font-size: 25px;
}
.match-screen-layout {
  margin: 10px 30px 0 0;
  width: 100px;
  height: 30px;
  background-color: #ecf0f5;
  text-align: center;
  border-radius: 3px;
  border: 1px solid #54575b;
}
.match-screen-section {
  width: 100%;
  height: calc(100% - 130px);
  background-image: url('@/assets/image/손흥민.jpg');
  background-size:auto;
  background-position: center;
  background-repeat: no-repeat;
}
.match-screen-footer {
  display: flex;
  flex-direction: row-reverse;
  height: 50px;
}
.match-screen-footer-icon {
  padding: 7px 0 0 0;
  margin-right: 10px;
}
.match-screen-footer-icon:first-child {
  margin-right: 30px;
}
.match-screen-chatting-area {
  width: 295px;
  height: 800px;
  margin: 26px 30px 0 0;
  animation-name: chattingOver;
  animation-duration: 0.3s;
  animation-timing-function: ease-in;
  border: 1px solid #cfd2d6;
  border-radius: 3px;
}
.match-screen-chatting-area2 {
  margin-right: 30px;
  animation-name: chattingLeave;
  animation-duration: 0.3s;
  animation-timing-function: ease-out;
}
</style>