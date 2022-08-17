<template>
  <div style="display:flex;">
    <!-- 세션 정보가 잘못 되었을 때 -->
    <div id="noSession" v-if="!mySessionId">세션 정보가 잘못 되었습니다.</div>
    <!-- 세션 정보가 잘 되었을 때 -->
    <div id="session" v-if="mySessionId">
      <!-- 헤더 -->
      <div class="match-screen-header">
        <div style="display:flex;">
          <!-- 방 제목 -->
          <div class="match-screen-title">
            {{ sessionInfo?.name }}
          </div>
          <div class="match-screen-icon">
            <v-icon  size="40" @click="leaveSession">
              mdi-account-multiple
            </v-icon>
            {{subscribers.length + 1}}
          </div>
        </div>

        <!-- 버튼들 -->
        <div style="display:flex">

          <v-btn class="match-screen-layout" @click="clickBillboard" style="background-color: #5F45FF">
            전광판
          </v-btn>
          <v-btn class="match-screen-layout" @click="clickGamePrediction" style="background-color: #cb99c5">
            승부예측
          </v-btn>
          <v-btn class="match-screen-layout" @click="clickGameInfo" style="background-color: #f0d264">
            경기정보
          </v-btn>

          <!-- 레이아웃 -->
          <v-btn class="match-screen-layout" @click="clickLayoutButton">
            레이아웃
          </v-btn>
        </div>

        <!-- 승부 예측 -->
        <div class="game-prediction" v-if="roomStore.isClickPredictButton">
          <div v-if="!this.isValidateTime" style="font-size: 30px;">예측 종료</div>
          <!-- 승부 예측 시간 -->
          <div style="font-size: 16px;">
            {{ roomStore.predictMonth }}.{{ roomStore.predictDate }} {{ roomStore.predictDay }}
          </div>
          <!-- 승부 예측 제목 -->
          <div v-if="roomStore.playTeams.home">
            <span style="font-family: var(--bold-font); color: var(--main-color); font-size: 15px">{{ roomStore.predictTime }}</span>
            {{ roomStore.playTeams.home.hanName }} vs
            {{ roomStore.playTeams.away.hanName }}
            <span style="float:right">내 잔여 축구공 : {{ myPoint }}</span>
          </div>
          <div style="display:flex;">
            <!-- 홈 팀 -->
            <div class="game-prediction-team" v-bind:style=" isPredictedTeam == 1 ? 'background-color:#db9908' : 'background-color:#323236' ">
              <!-- 홈팀 이미지 -->
              <img :src="roomStore.playTeams.home.logo" class="game-prediction-image" style="margin-right:10px;">
              <div>
                <div>
                  {{ roomStore.playTeams.home.hanName }}
                </div>
                <div style="font-size:16px;">
                  {{ gamePredictionStore.team1_point}} 축구공 ({{ gamePredictionStore.team1_count}} 명)
                </div>
              </div>
            </div>

            <div class="game-prediction-vs">
              vs
            </div>
              <!-- 어웨이 팀 -->
            <div class="game-prediction-team" style="flex-direction: row-reverse"
              v-bind:style=" isPredictedTeam == 2 ? 'background-color:#db9908' : 'background-color:#323236' ">
              <!-- 어웨이팀 이미지 -->
              <img :src="roomStore.playTeams.away.logo" class="game-prediction-image" style="margin-left:10px;">
              <div>
                <div>
                  {{ roomStore.playTeams.away.hanName }}
                </div>
                <div style="font-size:16px;">
                  {{ gamePredictionStore.team2_point}} 축구공 ({{ gamePredictionStore.team2_count}} 명)
                </div>
              </div>
            </div>  
          </div>
          <div style="display:flex; margin-top:10px;" v-if="!this.isPredicted && this.isValidateTime">
            <!--  -->
            <div>
              <input class="predict-input" type="number" v-model="team1_pointToSend" min="0" :max="myPoint" @blur="setMaxPoint(1)">
              <button class="predict-button" @click="sendGamePrediction(1)">예측</button>
            </div>
            <div style="width:30px;">

            </div>
            <div>
              <input class="predict-input" type="number" v-model="team2_pointToSend" min="0" :max="myPoint" @blur="setMaxPoint(2)">
              <button class="predict-button" @click="sendGamePrediction(2)">예측</button>
            </div>
          </div>
          <div style="display:flex; margin-top:10px; color:orange;" v-if="this.isPredicted">
            <!--  -->
            <div v-if="this.isPredictedTeam == 1">
              예측한 팀 : {{roomStore.playTeams.home.hanName}} ({{gamePredictionStore.predictedPoint}}개)
            </div>
            <div v-if="this.isPredictedTeam == 2">
              예측한 팀 : {{roomStore.playTeams.away.hanName}} ({{gamePredictionStore.predictedPoint}}개)
            </div>
          </div>
        </div>

        <!-- 전광판 -->
        <div class="room-game-billboard" v-if="roomStore.isClickBillboard">
          <!-- 닫기 버튼 -->
          <div class="room-game-billboard-header">
            <v-icon id="close-button" @click="roomStore.isClickBillboard = false">
              mdi-close
            </v-icon>
          </div>

          <!-- 경기 정보 -->
          <div class="room-game-billboard-section">
            <!-- 홈 -->
            <div class="room-game-billboard-team" style="flex-direction: row-reverse;">
              <img :src="roomStore.playTeams.home.logo" class="room-game-billboard-team-logo">
              <div style="height: 50px; padding-top:10px; margin-right:5px;">
                {{ roomStore.playTeams.home.hanName }}
              </div>
            </div>
            <!-- 킥오프 + 경기장 -->
            <div class="room-game-billboard-team-vs">
              <div class="room-game-billboard-score">
                {{ roomStore.homeGoalPoint }}
              </div>

              <div class="room-game-billboard-time">
                {{ roomStore.predictTime }}
              </div>

              <div class="room-game-billboard-score">
                {{ roomStore.awayGoalPoint }}
              </div>
            </div>

            <!-- 어웨이 -->
            <div class="room-game-billboard-team">
              <img :src="roomStore.playTeams.away.logo" class="room-game-billboard-team-logo">
              <div style="height: 50px; padding-top:10px; margin-right:5px;">
                {{ roomStore.playTeams.away.hanName }}

              </div>
            </div>
          </div>
          <div>
            <div class="room-game-billboard-stadium">
              {{ roomStore.playTeams.stadium }}
            </div>
          </div>

          <!-- 골 넣은 사람들 -->
          <div class="room-game-billboard-footer">
            <div v-for="(goal, i) in roomStore.goal" :key="i" >
              <!-- 둘 다 있을 경우 -->
              <div v-if="goal.homeGoal && goal.awayGoal" style="width: 100%; display:flex;">
                <div class="room-game-billboard-footer-goal-player" style="flex-direction: row-reverse;">
                  <v-icon style="margin-left: 5px;">
                    mdi-soccer
                  </v-icon>
                  {{ goal.homeGoal}}
                </div>

                <div style="width: 6px text-align:center;">
                ㅣ
                </div>
                <div class="room-game-billboard-footer-goal-player">
                  <v-icon style="margin-right: 5px;">
                    mdi-soccer
                  </v-icon>
                  {{ goal.awayGoal}}
                </div>
              </div>

              <!-- 홈만 있을 경우 -->
              <div v-if="goal.homeGoal && !goal.awayGoal" style="width: 100%; display:flex;">
                <div class="room-game-billboard-footer-goal-player" style="flex-direction: row-reverse;">
                  <v-icon style="margin-left: 5px;">
                    mdi-soccer
                  </v-icon>
                  {{ goal.homeGoal}}
                </div>

                <div style="width: 6px text-align:center;">
                ㅣ
                </div>
                <div class="room-game-billboard-footer-goal-player">
                </div>
              </div>

              <!-- 어웨이만 있을 경우 -->
              <div v-if="!goal.homeGoal && goal.awayGoal" style="width: 100%; display:flex;">
                <div class="room-game-billboard-footer-goal-player" style="flex-direction: row-reverse;">

                </div>

                <div style="width: 6px text-align:center;">
                ㅣ
                </div>
                <div class="room-game-billboard-footer-goal-player">
                  <v-icon style="margin-right: 5px;">
                    mdi-soccer
                  </v-icon>
                  {{ goal.awayGoal}}
                </div>
              </div>
            </div>

          </div>
        </div>

        <!-- 경기 정보 -->
        <div v-if="roomStore.isClickGameInfo" class="room-game-info">
          <div class="room-game-info-header" @click="roomStore.isClickGameInfo = false">
            <v-icon>
              mdi-close
            </v-icon>
          </div>
          <div class="room-game-info-content" id="room-game-info">
            <!-- 후반전 -->
            <div class="room-game-info-title">
              후반전
            </div>
            <div class="room-game-info-item" v-for="(gameInfo, i) in roomStore.gameInfoHalf" :key="i">
              <!-- 시간 나타내는 영역 -->
              <div class="room-game-info-time-section">
                <div class="room-game-info-time">
                  {{ gameInfo.time.elapsed }}'
                </div>
              </div>

              <!-- 골 -->
              <div v-if="gameInfo.type === 'Goal'" class="room-game-info-section">
                <div>
                  <v-icon>
                    mdi-soccer
                  </v-icon>
                  <span style="color: var(--main-color)">Goal !!!</span>
                </div>

                <div id="goal-player">
                  골 :
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.player.name }}
                </div>

                <div id="assist-player" v-if="gameInfo.assist.name">
                  어시 : {{ gameInfo.assist.name }}
                </div>
              </div>

              <!-- 카드 -->
              <!-- 옐로 카드 -->
              <div v-if="gameInfo.type === 'Card' && gameInfo.detail === 'Yellow Card'" class="room-game-info-section">
                <div id="goal-player">
                  <div id="yellow-card" >
                  </div>

                  <img :src="gameInfo.team.logo" alt="" width="16" style="margin: 25px 0 0 25px">
                  <span style="margin-bottom: 10px">{{ gameInfo.player.name }}</span>
                  
                </div>
              </div>

              <!-- 레드 카드 -->
              <div v-if="gameInfo.type === 'Card' && gameInfo.detail === 'Red Card'" class="room-game-info-section">
                <div id="goal-player">
                  <div id="red-card" >
                  </div>

                  <img :src="gameInfo.team.logo" alt="" width="16" style="margin: 25px 0 0 25px">
                  <span style="margin-bottom: 10px">{{ gameInfo.player.name }}</span>
                  
                </div>
              </div>
              
              <!-- 교체 -->
              <div v-if="gameInfo.type === 'subst'" class="room-game-info-section">
                <div style="margin-top: 15px;">
                  <v-icon style="color: green">
                    mdi-arrow-right
                  </v-icon>
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.assist.name }}
                </div>
                <div>
                  <v-icon style="color: red">
                    mdi-arrow-left
                  </v-icon>
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.player.name }}
                </div>
              </div>

            </div>

            <!-- 전반전 -->
            <div class="room-game-info-title">
              전반전
            </div>
            <div class="room-game-info-item" v-for="(gameInfo, i) in roomStore.gameInfo" :key="i">
              <!-- 시간 나타내는 영역 -->
              <div class="room-game-info-time-section">
                <div class="room-game-info-time">
                  {{ gameInfo.time.elapsed }}'
                </div>
              </div>

              <!-- 골 -->
              <div v-if="gameInfo.type === 'Goal'" class="room-game-info-section">
                <div>
                  <v-icon>
                    mdi-soccer
                  </v-icon>
                  <span style="color: var(--main-color)">Goal !!!</span>
                </div>

                <div id="goal-player">
                  골 :
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.player.name }}
                </div>

                <div id="assist-player" v-if="gameInfo.assist.name">
                  어시 : {{ gameInfo.assist.name }}
                </div>
              </div>

              <!-- 카드 -->
              <!-- 옐로 카드 -->
              <div v-if="gameInfo.type === 'Card' && gameInfo.detail === 'Yellow Card'" class="room-game-info-section">
                <div id="goal-player">
                  <div id="yellow-card" >
                  </div>

                  <img :src="gameInfo.team.logo" alt="" width="16" style="margin: 25px 0 0 25px">
                  <span style="margin-bottom: 10px">{{ gameInfo.player.name }}</span>
                  
                </div>
              </div>

              <!-- 레드 카드 -->
              <div v-if="gameInfo.type === 'Card' && gameInfo.detail === 'Red Card'" class="room-game-info-section">
                <div id="goal-player">
                  <div id="red-card" >
                  </div>

                  <img :src="gameInfo.team.logo" alt="" width="16" style="margin: 25px 0 0 25px">
                  <span style="margin-bottom: 10px">{{ gameInfo.player.name }}</span>
                  
                </div>
              </div>
              
              <!-- 교체 -->
              <div v-if="gameInfo.type === 'subst'" class="room-game-info-section">
                <div>
                  <v-icon style="color: green">
                    mdi-arrow-right
                  </v-icon>
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.assist.name }}
                </div>
                <div>
                  <v-icon style="color: red">
                    mdi-arrow-left
                  </v-icon>
                  <img :src="gameInfo.team.logo" alt="" width="16">
                  {{ gameInfo.player.name }}
                </div>
              </div>

            </div>
            {{ roomStore.gameInfo.response }}
          </div>
        </div>
      </div>

      <!-- 스크린 -->
      <div style="margin: 0 auto;" :style="{ height : roomStore.screenHeight, width : roomStore.screenWidth,}">
      <!-- 비디오 컴포넌트 사이즈 조절 필요
            영상 시청하기 위해서는 CORS 설정 필요 -->
        <GameVideo :stream_url="this.stream_urls" id="video-section" :style="{ height : roomStore.screenHeight, width : roomStore.screenWidth,}" />
      </div>
      <div class="img-toggle">

      </div>

      <!-- 캠 레이아웃 -->
      <div id="video-container" v-if="roomStore.isClickLayout">
          <user-video
            :stream-manager="publisher"
            :isSessionManager="isSessionManager"
            @click="updateMainVideoStreamManager(publisher)"
            
          />
          <user-video
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            :isSessionManager="isSessionManager"
            @click="updateMainVideoStreamManager(sub)"
            @forceOut="forceOut"
          />
        </div>

      <!-- 푸터 -->
      <div class="match-screen-footer">

        <!-- 나가기 -->
        <v-btn class="button-range" style="width: 160px;" @click="clickLeaveSessionButton">
          <div class="icon-range">
            <v-icon size="40">
              mdi-exit-run
            </v-icon>
          </div>
          <div class="button-range-title">
            <span style="color: red;">나가기</span>
          </div>
        </v-btn>

        <!-- 채팅 -->
        <!-- 채팅창이 켜져있을 때 -->
        <v-btn class="button-range" style="width: 170px;" @click="chattingOnOff" v-if="roomStore.isClickChatting === 'a'">
          <div class="icon-range">
            <v-icon size="40">
              mdi-chat-processing-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            채팅 <span style="color: red;">끄기</span>
          </div>
        </v-btn>

        <!-- 채팅창이 꺼져있을 때 -->
        <v-btn class="button-range" style="width: 170px;" @click="chattingOnOff" v-if="roomStore.isClickChatting === '' || roomStore.isClickChatting === 'b'">
          <div class="icon-range">
            <v-icon size="40">
              mdi-chat-processing
            </v-icon>
          </div>
          <div class="button-range-title">
            채팅 보기
          </div>
        </v-btn>

        <!-- 비디오 -->
        <!-- 비디오가 켜져있을 때 -->
        <v-btn class="button-range" @click="toggleCam" v-if="!cam">
          <div class="icon-range">
            <v-icon size="40">
              mdi-video-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            비디오 <span style="color: red;">끄기</span>
          </div>
        </v-btn>

        <!-- 비디오가 꺼져있을 때 -->
        <v-btn class="button-range" @click="toggleCam" v-else>
          <div class="icon-range">
            <v-icon size="40" color="red">
              mdi-video-off-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            비디오 켜기
          </div>
        </v-btn>

        <!-- 마이크 -->
        <!-- 마이크가 켜져있을 때 -->
        <v-btn class="button-range" @click="toggleMic" v-if="!mic">
          <div class="icon-range">
            <v-icon size="40">
              mdi-microphone
            </v-icon>
          </div>
          <div class="button-range-title">
            마이크 <span style="color: red;">끄기</span>
          </div>
        </v-btn>

        <!-- 마이크가 꺼져있을 때 -->
        <v-btn class="button-range" @click="toggleMic" v-else>
          <div class="icon-range">
            <v-icon size="40" color="red">
              mdi-microphone-off
            </v-icon>
          </div>
          <div class="button-range-title">
            마이크 켜기
          </div>
        </v-btn>

        <!-- 응원가 -->
        <!-- 응원가가 켜져있을 때 -->
        <v-btn class="button-range" @click="toggleBullhorn" v-if="!bullhorn">
          <div class="icon-range">
            <v-icon size="40">
              mdi-bullhorn-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            응원가 <span style="color: red;">끄기</span>
          </div>
        </v-btn>

        <!-- 응원가가 꺼져있을 때 -->
        <v-btn class="button-range" @click="toggleBullhorn" v-else>
          <div class="icon-range">
            <v-icon size="40">
              mdi-bullhorn
            </v-icon>
          </div>
          <div class="button-range-title">
            응원가 켜기
          </div>
        </v-btn>

        <!--응원가 플레이어-->
        <div style="height:54px; display:flex; margin-top:10px">
          <audio ref="audio" controls>
            <source :src="roomStore.cheeringSong">
          </audio>
          <select v-show="roomStore.roomInfo.managerId==accountStore.profileId" v-model="onePick" @change="changeSong($event)" style="margin: 5px 0 0 10px;">
            <option v-for="(song, i) in roomStore.songList" :value="i" :key="song.name">
             <div clas="select-item">{{ song.name }}</div>
            </option>
          </select>
          <div v-show="roomStore.roomInfo.managerId!=accountStore.profileId" style="margin: 5px 0 0 10px;" class="nowPlaySong">
            📀{{ nowPlaySong }}
          </div>
        </div>
      </div>
    </div>

    <!-- 채팅 -->
    <div v-show="roomStore.isClickChatting == 'a'" class="match-screen-chatting-area" >
      <!-- 채팅 헤더 -->
      <div class="match-screen-chatting-header">
      </div>

      <!-- 채팅 내용 창 -->
      <div class="match-screen-chatting-section" id="match-screen-chatting-section">
        <div id="chattingWindow" class="chatting-window">
          <div id="chatting-content" v-html="chattingContent" style="max-width:222px;"></div>
        </div>
      </div>
      <!-- 채팅 입력하는 곳 -->
      <div class="match-screen-chatting-footer">
        <div class="chatting-input-wrap">
          <textarea v-model="message" @keyup.enter="sendChat()" class="chatting-input"/>
          <button type="button" @click="sendChat()" class="chatting-input-button">입력</button>
        </div>
      </div>
    </div>
    <!-- 얘는 그냥 사라질 때 자연스러운 효과를 위한 영역 신경 안써도 됩니다. -->
    <div v-if="roomStore.isClickChatting == 'b'" class="match-screen-chatting-area2" >
    </div>


  </div>
</template>
<script>
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/video/UserVideo.vue";
import GameVideo from "@/components/video/GameVideo.vue";
import axios from "axios";
import sanitizeHTML from "sanitize-html";
import _ from 'lodash'

import { useAccountStore, useRoomStore, useGamePredictionStore } from "@/store/index.js";
import { updateRoomHeadCount, deleteRoom } from '@/api/room.js';

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = process.env.VUE_APP_OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OPENVIDU_SERVER_SECRET;

const BAD_WORDS_LIST = process.env.VUE_APP_BAD_WORDS.split(",");

export default {
  name: "Match-Room",

  components: {
    UserVideo,
    GameVideo,
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      accountStore: useAccountStore(),

      mySessionId: undefined,
      myUserName: undefined,
      sessionInfo: undefined,
      isSessionManager: false,
      roomStore: useRoomStore(),
      isOpenedChattingWindow: true,
      message: "",
      //테스트용 스트리밍 URL.
      //서버에서 스트리밍을 시작해야 보임
      //추후 스트리밍 서버 설정 방법 고려 후 변경 예정
      stream_urls: "https://i7b204.p.ssafy.io/tmp/hls/test/index.m3u8",

      mic: false,
      cam: false,
      chattingContent: "",

      gamePredictionStore : useGamePredictionStore(),
      myPoint: 0,
      team1_pointToSend: 0,
      team2_pointToSend: 0,
      isPredicted: false,
      isValidateTime: false,
  
      bullhorn: false,
      onePick: "0",

      backImg : [
        "https://media.api-sports.io/football/venues/556.png",
        "https://media.api-sports.io/football/venues/550.png",
        "https://media.api-sports.io/football/venues/494.png",
        "https://media.api-sports.io/football/venues/519.png",
        "https://media.api-sports.io/football/venues/555.png",
        "https://media.api-sports.io/football/venues/525.png",
        "https://media.api-sports.io/football/venues/495.png",
        "https://media.api-sports.io/football/venues/566.png",
        "https://media.api-sports.io/football/venues/546.png",
      ],
      myImg : '',
      nowPlaySong : "재생중인 응원가가 없습니다.",
    };
  },
  mounted() {
    this.mySessionId = this.$route.params.session;

    this.inMount();

    // 사용한 피니아 변수 초기화
    this.roomStore.isClickChatting = ''
    this.roomStore.isClickLayout = false
    this.roomStore.screenWidth = '1400px'
    this.roomStore.screenHeight = '800px'
    this.roomStore.isClickSettingButton = false
    this.roomStore.isClickBillboard = false
    this.roomStore.isClickGameInfo = false
    this.roomStore.isClickPredictButton = false
    this.loading = setInterval(this.getGameInfo, 60000);

  },

  methods: {
    async inMount(){
      // this.myImg = this.backImg[_.sampleSize(_.range(0, 9), 1)];
      this.myImg = _.sampleSize(this.backImg);
      this.mySessionId = this.$route.params.session;
      this.myUserName = useAccountStore().profile.nickname;
      this.myPoint = useAccountStore().profile.point;

      let list = this.gamePredictionStore.isPredictedList;
      for(let predictedSession of list) {
        if(predictedSession == this.mySessionId) {
          this.isPredicted = true;
          break;
        }
      }

      await useRoomStore().getInfo(this.mySessionId)
      .then(() =>  {
        this.sessionInfo = useRoomStore().roomInfo;
        this.isSessionManager = (this.sessionInfo.managerId == useAccountStore().profileId);
      });

      this.joinSession();
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
        if(this.isSessionManager == true) {
          this.session
          .signal({
            data: (this.gamePredictionStore.team1_point) + "/" + (this.gamePredictionStore.team1_count)
              + "/" + (this.gamePredictionStore.team2_point) + "/" + (this.gamePredictionStore.team2_count)
              + "/" + (this.gamePredictionStore.team1_predict_list) + "/" + (this.gamePredictionStore.team2_predict_list),
            to: [],
            type: "game-prediction-broadcast",
          })
          .then(() => {
            console.log("Message successfully sent");
          })
          .catch((error) => {
            console.error(error);
          });
          this.updateRoomHeadCount(this.subscribers.length+1);
        }
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.session.on("signal:my-chat", (event) => {
        let receive = event.data.split("/");
        let userName = receive[0];
        let message = String(receive[1]);

        for(let badWord of BAD_WORDS_LIST) {
          if(message.includes(badWord)) {
            message = '[삭제된 메세지]';
            break; 
          }
        }

        message = sanitizeHTML(message);
        this.chattingContent += `<div style:'margin-top: 10px;'>${userName}:</div>`;
        this.chattingContent += `<div style:'word-break:break-all; max-width: 222px; margin-bottom: 10px'>${message}<div>`;

        // this.chattingWindow.scrollTo({ top: this.chattingWindow.scrollHeight, behavior: 'smooth' })
        let objDiv = document.getElementById("match-screen-chatting-section");
        objDiv.scrollTop = objDiv.scrollHeight;
      });

      this.session.on("signal:game-prediction", (event) => {
        let receive = event.data.split("/");
        this.gamePredictionStore.team1_point = parseInt(receive[0]);
        this.gamePredictionStore.team1_count = parseInt(receive[1]);
        this.gamePredictionStore.team2_point = parseInt(receive[2]);
        this.gamePredictionStore.team2_count = parseInt(receive[3]);
        this.gamePredictionStore.team1_predict_list = Array(receive[4]);
        this.gamePredictionStore.team2_predict_list = Array(receive[5]);
      });


      this.session.on("signal:game-prediction-broadcast", (event) => {
        let receive = event.data.split("/");
        this.gamePredictionStore.team1_point = parseInt(receive[0]);
        this.gamePredictionStore.team1_count = parseInt(receive[1]);
        this.gamePredictionStore.team2_point = parseInt(receive[2]);
        this.gamePredictionStore.team2_count = parseInt(receive[3]);
        this.gamePredictionStore.team1_predict_list = Array(receive[4]);
        this.gamePredictionStore.team2_predict_list = Array(receive[5]);
      });

      this.session.on("signal:game-prediction-result", (event) => {
        let receive = event.data.split("/");
        this.gamePredictionStore.team1_predict_list = Array(receive[0]);
        this.gamePredictionStore.team2_predict_list = Array(receive[1]);
      });

      this.session.on("signal:force-out", (event) => {
        if(event.data === this.myUserName){
          this.leaveSession();
        }
      });

      this.session.on("signal:cheering-song", (event) => {
        this.roomStore.cheeringSong = this.roomStore.songList[event.data].file;
        this.nowPlaySong = this.roomStore.songList[event.data].name;
        this.$refs.audio.pause();
        this.$refs.audio.load();
        this.$refs.audio.play();
      })

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---


      this.gamePredictionStore.team1_point = 0;
      this.gamePredictionStore.team1_count = 0;
      this.gamePredictionStore.team2_point = 0;
      this.gamePredictionStore.team2_count = 0;
      this.gamePredictionStore.team1_predict_list = [];
      this.gamePredictionStore.team2_predict_list = [];

      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
      this.$router.push({ name: "MainPage" });
    },

    clickLeaveSessionButton(){
          if(this.isSessionManager == true) {
            useGamePredictionStore().team1_predict_list = [];
            useGamePredictionStore().team2_predict_list = [];
            console.log("I'm SessionManager!!");
            this.subscribers.forEach(async e => {
              let userId = JSON.parse(e.stream.connection.data).clientData;
              console.log("foreach: "+userId);
              await this.session
                .signal({
                  data: userId,
                  to: [],
                  type: "force-out",
                })
                .then(() => {
                  console.log("force-out successfully requested");
                })
                .catch((error) => {
                  console.error(error);
                });
            });
            deleteRoom(this.sessionInfo.roomId,
              () => {
                console.log("Room Delete Complete");
              },
              (err) => {
                console.log(err);
            });
          } else {
            this.updateRoomHeadCount(this.subscribers.length+1);
          }

          this.leaveSession();
    },
    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server (POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    clickLayoutButton() {
      this.roomStore.isClickLayout = !this.roomStore.isClickLayout

      if ( this.roomStore.screenHeight === '800px') {
        this.roomStore.screenWidth = '1000px'
        this.roomStore.screenHeight = '610px'
      }
      else {
        this.roomStore.screenWidth = '1400px'
        this.roomStore.screenHeight = '800px'
      }
    },
    chattingOnOff() {
      this.roomStore.isClickSettingButton = false,
      this.roomStore.isClickBillboard = false,
      this.roomStore.isClickGameInfo = false,
      this.roomStore.isClickSetting = false,

      this.roomStore.isClickGameInfo = false
      if (this.roomStore.isClickChatting == '') {
        this.roomStore.isClickChatting = 'a'
        let objDiv = document.getElementById("match-screen-chatting-section");
        objDiv.scrollTop = objDiv.scrollHeight;
      }
      else if (this.roomStore.isClickChatting == 'a'){
        this.roomStore.isClickChatting = 'b'
      }
      else {
        this.roomStore.isClickChatting = 'a'
        let objDiv = document.getElementById("match-screen-chatting-section");
        objDiv.scrollTop = objDiv.scrollHeight;
      }
    },

    sendChat() {
      if (this.message && this.message.trim() != "") {
        this.session
          .signal({
            data: this.myUserName + "/" + this.message,
            to: [],
            type: "my-chat",
          })
          .then(() => {
            console.log("Message successfully sent");
          })
          .catch((error) => {
            console.error(error);
          });
      }
      this.message = "";
    },

    forceOut(clientData) {
      console.log(clientData + " " + this.isSessionManager);
      if(this.isSessionManager == false) return;
      this.session
        .signal({
          data: clientData,
          to: [],
          type: "force-out",
        })
        .then(() => {
          console.log("force-out successfully requested");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    clickGamePrediction() {
      this.roomStore.isClickPredictButton = !this.roomStore.isClickPredictButton;
      this.isValidateTime = this.validateTime();
      this.roomStore.isClickBillboard =false;
      this.roomStore.isClickGameInfo = false;
      console.log(useRoomStore().gamePredictionDeadline);
      console.log(this.validateTime());
      console.log(this.isValidateTime);
    },

    sendGamePrediction(team) {
      let pointToSend;
      if(team == 1) {
        pointToSend = this.team1_pointToSend;
      } else if(team == 2) {
        pointToSend = this.team2_pointToSend;
      }

      if (pointToSend && pointToSend > 0) {
        if(team == 1) {
          this.gamePredictionStore.team1_point += pointToSend;
          this.gamePredictionStore.team1_count++;
          this.gamePredictionStore.team1_predict_list.push(parseInt(this.accountStore.profileId));
          this.isPredictedTeam = 1;
        } else if(team == 2) {
          this.gamePredictionStore.team2_point += pointToSend;
          this.gamePredictionStore.team2_count++;
          this.gamePredictionStore.team2_predict_list.push(parseInt(this.accountStore.profileId));
          this.isPredictedTeam = 2;
        }

        this.session
          .signal({
            data: (this.gamePredictionStore.team1_point) + "/" + (this.gamePredictionStore.team1_count)
              + "/" + (this.gamePredictionStore.team2_point) + "/" + (this.gamePredictionStore.team2_count)
              + "/" + (this.gamePredictionStore.team1_predict_list) + "/" + (this.gamePredictionStore.team2_predict_list),
            to: [],
            type: "game-prediction",
          })
          .then(() => {
            console.log("Message successfully sent");
          })
          .catch((error) => {
            console.error(error);
          });
        this.roomStore.subtractPoint(this.accountStore.profileId, team, pointToSend);
        this.gamePredictionStore.predictedPoint = pointToSend;
        this.team1_pointToSend = 0;
        this.team2_pointToSend = 0;
        this.gamePredictionStore.isPredictedList.push(this.mySessionId);
        this.isPredicted = true;
        this.myPoint -= pointToSend;
      }
    },

    setMaxPoint() {
      if(this.team1_pointToSend > this.myPoint) {
        this.team1_pointToSend = this.myPoint;
      }
      if(this.team2_pointToSend > this.myPoint) {
        this.team2_pointToSend = this.myPoint;
      }
    },

    validateTime() {
      return (new Date() <= new Date(useRoomStore().gamePredictionDeadline));
    },
    
    toggleMic(){
      this.publisher.publishAudio(this.mic);   // true to unmute the audio track, false to mute it
      this.mic = !this.mic;
    },
    toggleCam(){
      this.publisher.publishVideo(this.cam);   // true to enable the video track, false to disable it
      this.cam = !this.cam;
    },
    toggleBullhorn(){
      this.$refs.audio.muted = !this.$refs.audio.muted;
      this.bullhorn = !this.bullhorn;
    },
    clickBillboard() {
      this.roomStore.isClickGameInfo = false,
      this.roomStore.isClickPredictButton = false,

      this.roomStore.isClickSettingButton = false
      this.roomStore.isClickBillboard = !this.roomStore.isClickBillboard
    },
    clickGameInfo() {
      this.roomStore.isClickBillboard = false,
      this.roomStore.isClickPredictButton = false,

      this.roomStore.isClickSettingButton = false
      this.roomStore.isClickGameInfo = !this.roomStore.isClickGameInfo

    },
    getGameInfo() {
      console.log("받아옴")
      this.isValidateTime = this.validateTime();
      this.roomStore.getGameInfo(this.roomStore.playTeams.apiId);
      this.roomStore.update(this.roomStore.playTeams.id, this.roomStore.playTeams.apiId);
      if(this.roomStore.playTeams.status == "FT") {
        console.log("!!!!!!!finish!!!!!!!!!!!!")
        if(this.isSessionManager == true) {
          this.session
            .signal({
              data: (this.gamePredictionStore.team1_predict_list) + "/" + (this.gamePredictionStore.team2_predict_list),
              to: [],
              type: "game-prediction-result",
            })
            .then(() => {
              console.log("Message successfully sent");
            })
            .catch((error) => {
              console.error(error);
            });
        }
        this.myPoint = this.gamePredictionStore.distributePoints() + this.myPoint;
        clearInterval(this.loading);
      }
    },
    updateRoomHeadCount(number){
      updateRoomHeadCount(this.sessionInfo.roomId, number,
        () => {
          console.log("HeadCount Successfully updated");
        },
        (err) => {
          console.log(err)
        }
      );
    },
    changeSong(event){
      this.roomStore.cheeringSong=this.roomStore.songList[event.target.value].file;
      this.$refs.audio.pause();
      this.$refs.audio.load();
      this.$refs.audio.play();
      this.session
        .signal({
          data: event.target.value,
          to: [],
          type: "cheering-song",
        })
        .then(() => {
          console.log("응원가 공유 성공");
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
};
</script>

<style scoped>
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
/* 가장 최상단 뷰 */
.room {
  display: flex;
  background-size: cover;
  height: 1080px;
  background-position: center;
  background-repeat: no-repeat;
}
.room::-webkit-scrollbar {
  width: 1px;
}
/* 메인 영역 */
/* 상단 제목 */
#session {
  width: 100%;
  height: 830px;
  margin: 50px 0 0 30px;
}
.match-screen-header {
  font-family: var(--bold-font);
  font-size: 20px;
  width: 100%;
  height: 50px;
  margin: 15px 0px;
  padding: 0 0 0 30px;
  display: flex;
  justify-content: space-between;
  position: relative;
}
.match-screen-title {
  max-width: 740px;
  height: 60px;
  padding: 10px 0 10px 10px;
  /* background-color: #ecf0f5; */
  border-radius: 3px;
  font-size: 28px;
}
.match-screen-icon {
  padding-top: 8px;
  margin-left: 10px;
  font-size: 25px;
}

/* 승부 예측 */
.game-prediction {
  width: 600px;
  height: 215px;
  background-color: #1a1a1c;
  z-index: 10;
  border-radius: 10px;
  padding: 10px;
  color: white;
  position: absolute;
  top: 55px;
  right: 30px;
  box-shadow: rgba(26,26,28, 0.25) 0px 54px 55px, rgba(26,26,28, 0.12) 0px -12px 30px, rgba(26,26,28, 0.12) 0px 4px 6px, rgba(26,26,28, 0.17) 0px 12px 13px, rgba(26,26,28, 0.09) 0px -3px 5px;
}
.game-prediction-team {
  width: 275px;
  background-color: #323236;
  border-radius: 10px;
  height: 70px;
  padding-top: 10px;
  display: flex;
}
.game-prediction-image {
  width: 50px;
  height: 50px;
  border-radius: 50px;
  border: 1px solid #ecf0f5;
  vertical-align: middle;
}
.game-prediction-vs {
  height: 50px;
  width: 30px;
  padding-top: 9px;
  text-align: center;
}
.predict-input {
  width: 100px;
  outline: 1px solid black;
  background-color: #ecf0f5;
  border-radius: 10px;
  padding: 1px 10px;
  text-align: right;
}
.predict-input::-webkit-outer-spin-button,
.predict-input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
}
.predict-button {
  width: 165px;
  margin-left: 10px;
  background-color: #323236;
  color: white; 
}

/* 레이아웃 버튼 */
.match-screen-layout {
  margin: 10px 30px 0 0;
  width: 100px;
  height: 40px;
  background-color: #6698cb;
  text-align: center;
  border-radius: 3px;
  border: 1px solid #54575b;
  color: white;
  font-size: 18px;
  padding: 6px;
}
.match-screen-layout:hover {
  cursor: pointer;
}
/* 스크린 영역*/
#video-section {
  width: 1400px;
  height: 800px;
  margin: 0 auto;
}

/* 하단 캠 */
#video-container {
  margin-top: 10px;
  /* background-color: #7F5283; */
  height: 190px;
  margin-right: 60px;
  display: flex;
  padding-top: 5px;

}


/* 하단 버튼  */
.match-screen-footer {
  margin: 30px 0 0 0;
  display: flex;
  flex-direction: row-reverse;
  height: 70px;
  padding: 20px 0 0 0;
}
.match-screen-footer > div {
  margin: 0 30px 0 0 ;
}
.button-range {
  width: 190px;
  background-image: linear-gradient(to right, #84fab0 0%, #8fd3f4 51%, #84fab0 100%);
  height: 70px;
  display: flex;
  font-family: var(--bold-font);
  font-size: 20px;
  border-radius: 90px;
  margin-right: 15px;
}
.button-range:hover {
  cursor: pointer;
}
.icon-range {
  /* margin: 9px 0 0 9px; */
  padding: 4px;
  background-image: linear-gradient(to right, #a1fdf1 0%, #c2e9fb 51%, #a1fdfd 100%);

  width: 50px;
  height: 50px;
  border-radius: 50px;
  margin: 0px;
}
.button-range-title {
  padding: 3px 0 0 10px;
}

/* 채팅 영역 */
.match-screen-chatting-area {
  width: 295px;
  height: 980px;
  margin: 76px 30px 0 0;
  animation-name: chattingOver;
  animation-duration: 0.3s;
  animation-timing-function: ease-in;
  border: 1px solid #cfd2d6;
  border-radius: 3px;
  background-color: rgba( 255, 255, 255, 0.8 );
  /* background-color: #FEFBF6; */
}
.match-screen-chatting-area2 {
  margin-right: 30px;
  animation-name: chattingLeave;
  animation-duration: 0.3s;
  animation-timing-function: ease-out;
}


/* 채팅 헤더 */
.match-screen-chatting-header {
  height: 100px;
  border-bottom: 1px solid #cfd2d6;
  position: relative;
}
.match-screen-chatting-header-setting-button {
  width: 20px;
  height: 20px;
  margin: 10px 10px auto 220px;
}

/* 채팅 내용 */
.match-screen-chatting-section {
  height: 790px;
  overflow-y: scroll;
  border-bottom: 1px solid #cfd2d6;
  padding: 10px 15px 10px 10px;
  word-wrap: break-word;
}
.match-screen-chatting-section::-webkit-scrollbar {
  width: 3px;
  background-color: var(--sub-color);
}

/* 채팅 입력 */
.match-screen-chatting-footer {
  padding: 10px;
  position: relative;
}
.chatting-input-wrap {
  margin: 0 auto;
  width: 220px;
  height: 70px;
  border-radius: 20px;
  background-color: var(--sub-color);
  border: 1px solid #A6D1E6;
  display: flex;
}
.chatting-input {
  max-width: 170px;
  min-width: 170px; 
  max-height: 50px;
  min-height: 50px;
  margin: auto 0;
  overflow: auto;
  background: none;
  padding: 5px 8px;
  resize: none;
}
.chatting-input:focus {
  outline: none; 
}
.chatting-input::-webkit-scrollbar {
  width: 1px;
}
.chatting-input-button {
  padding: 0 auto;
  width: 50px;
  color: var(--main-color);
}

/*  경기 정보 */
.room-game-info {
  position: absolute;
  top: 55px;
  right: 30px;
  box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;
  border-radius: 10px;
  border: 1px solid grey;
  width: 305px;
  overflow: hidden;
  z-index: 10;
}
.room-game-info-content {
  min-width: 300px;
  height: 500px;
  border: 1px solid grey;
  overflow-y: auto;
  margin: 0 auto;
  background-color: #ffffff;
}
.room-game-info-header {
  text-align: end;
  padding: 3px 3px 0 0;
  background-color: #ffffff;
}
.room-game-info-header:hover {
  cursor: pointer;
}
.room-game-info-content::-webkit-scrollbar {
  width: 1px;
}
.room-game-info-item {
  width: 277px;
  height: 80px;
  margin: 10px;
  display: flex;
  border: 1px solid #cfd2d6;
  border-radius: 15px;
}
.room-game-info-time-section {
  width: 40px;
  height: 100%;
  border-right: 1px solid #cfd2d6;
  position: relative;
}
.room-game-info-time {
  position: absolute;
  width: 35px;
  height: 35px;
  border: 1px solid #cfd2d6;
  border-radius: 35px;
  text-align: center;
  padding-top: 5px;
  top: 23px;
  left: 23px;
  background-color: #ffffff;
}
.room-game-info-title {
  margin: 10px;
  font-size: 20px;
}
.room-game-info-title:first-child {
  margin-top: 40px;
}
.room-game-info-section {
  margin: 5px 0 0 30px;
  font-family: var(--bold-font);
  font-size: 16px;
}
#goal-player {
  font-size: 16px;
  position: relative;
}
#assist-player {
  font-family: var(--bold-font);
  font-size: 10px;
}
#yellow-card {
  width: 20px;
  height: 30px;
  background: yellow;
  color: none;
  position: absolute;
  top: 15px;
}
#red-card {
  width: 20px;
  height: 30px;
  background: red;
  color: none;
  position: absolute;
  top: 15px;
}

/* 전광판 */
.room-game-billboard {
  width: 700px;
  background-color: #5F45FF;
  color: white;
  position: absolute;
  top: 55px;
  right: 30px;
  z-index: 10;
  right: 0;
  box-shadow: rgba(59,65,81, 0.25) 0px 54px 55px, rgba(59,65,81, 0.12) 0px -12px 30px, rgba(59,65,81, 0.12) 0px 4px 6px, rgba(59,65,81, 0.17) 0px 12px 13px, rgba(59,65,81, 0.09) 0px -3px 5px;
}
.room-game-billboard-header {
  width: 100%;
  height: 25px;
  text-align: end;
  background-color: #5F45FF;
  color: white;
}
#close-button:hover {
  cursor: pointer;
}
.room-game-billboard-section {
  width: 100%;
  height: 80px;
  padding: 10px;
  background-color: #5F45FF;
  display: flex;
}
.room-game-billboard-team{
  width: 300px;
  height: 40px;
  display: flex;
  font-size: 20px;
  vertical-align: middle;
  font-family: var(--bold-font);
  padding-top: 15px;
}
.room-game-billboard-team-logo {
  width: 50px;
  height: 50px;
  border-radius: 50px;
}
.room-game-billboard-team-vs {
  width: 180px;
  height: 70px;
  display: flex;
}
.room-game-billboard-score {
  width: 50px;
  padding-top: 15px;
  font-size: 40px;
  font-family: var(--bold-font);
  text-align: center;
}
.room-game-billboard-time {
  font-family: var(--bold-font);
  width: calc(100% - 100px);
  margin: 20px 0;
  text-align: center;
  color: white;
}
.room-game-billboard-stadium {
  text-align: center;
  width: 700px;
  margin: 0 auto;
  height: 30px;
  color: #ecf0f5;
  font-family: var(--bold-font);
}
.room-game-billboard-footer {
  margin: 15px 40px 0 40px ;
  padding: 10px 0 0 0;
  border-top: 1px solid #ffffff;
}
.room-game-billboard-footer-goal-player {
  width: calc(50% - 3px);
  height: 24px;
  display: flex;
}

/* 응원가 */
@keyframes marquee {
  from { transform: translateX(0); }
  to { transform: translateX(-50%); }
}
audio::-webkit-media-controls-panel {
  background-image: linear-gradient(to right, #fbc2eb 0%, #a6c1ee 51%, #fbc2eb 100%);
}
select {
  -moz-appearance: none;
  -webkit-appearance: none;
  appearance: none;

  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;

  color: #444;
  background-color: #fff;

  padding: .6em 1.4em .5em .8em;
  margin: 20px;

  border: 1px solid #aaa;
  border-radius: .5em;
  box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
  width: 260px;
}
select:hover {
  border-color: #888;
}
select:focus {
  border-color: #aaa;
  box-shadow: 0 0 1px 3px rgba(59, 153, 252, .7);
  box-shadow: 0 0 0 3px -moz-mac-focusring;
  color: #222;
  outline: none;
}
select:disabled {
  opacity: 0.5;
}
.select-item {
  width: 100%;
  height: 54px;
  position: absolute;
}
.select-item:hover {
  transform: translateX(-300px);
}
.nowPlaySong {
  border: 1px solid #aaa;
  border-radius: .5em;
  box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
  width: 260px;
  text-align: center;
  padding-top: 10px;
}

</style>

