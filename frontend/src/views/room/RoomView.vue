<template>
  <div style="display:flex;">
    <!-- 세션 정보가 잘못 되었을 때 -->
    <div id="noSession" v-if="!mySessionId">세션 정보가 잘못 되었습니다.</div>
    <!-- 세션 정보가 잘 되었을 때 -->
    <div id="session" v-if="mySessionId">
      <!-- 헤더 -->
      <div class="match-screen-header">
        <div style="display:flex;">
          <div class="match-screen-title">
            {{ sessionInfo.name }}
          </div>
          <div class="match-screen-icon">
            <v-icon  size="40" @click="leaveSession">
              mdi-account-multiple
            </v-icon>
            6 
          </div>
        </div>

        <div class="match-screen-layout" @click="clickLayoutButton">
          레이아웃
        </div>
      </div>
      
      <!-- 스크린 -->
        <div style="margin: 0 auto;" :style="{ height : roomStore.screenHeight, width : roomStore.screenWidth,}">
          <GameVideo :stream_url="this.stream_urls" id="video-section" :style="{ height : roomStore.screenHeight, width : roomStore.screenWidth,}" />

        </div>


      <!-- 비디오 컴포넌트 사이즈 조절 필요 
            영상 시청하기 위해서는 CORS 설정 필요 -->


      <!-- 캠 레이아웃 -->
      <div id="video-container" v-if="roomStore.isClickLayout">
          <user-video
            :stream-manager="publisher"
            @click="updateMainVideoStreamManager(publisher)"
          />
          <user-video
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            @click="updateMainVideoStreamManager(sub)"
          />
        </div>

      <!-- 푸터 -->
      <div class="match-screen-footer">

        <!-- 나가기 -->
        <div class="button-range" style="width: 150px;" @click="leaveSession">
          <div class="icon-range">
            <v-icon size="40">
              mdi-exit-run
            </v-icon>
          </div>
          <div class="button-range-title">
            <span style="color: red;">나가기</span>
          </div>
        </div>

        <!-- 채팅 -->
        <!-- 채팅창이 켜져있을 때 -->
        <div class="button-range" style="width: 170px;" @click="chattingOnOff" v-if="roomStore.isClickChatting === 'a'">
          <div class="icon-range">
            <v-icon size="40">
              mdi-chat-processing-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            채팅 <span style="color: red;">끄기</span>
          </div>
        </div>

        <!-- 채팅창이 꺼져있을 때 -->
        <div class="button-range" style="width: 170px;" @click="chattingOnOff" v-if="roomStore.isClickChatting === '' || roomStore.isClickChatting === 'b'">
          <div class="icon-range">
            <v-icon size="40">
              mdi-chat-processing
            </v-icon>
          </div>
          <div class="button-range-title">
            채팅 보기
          </div>
        </div>

        <!-- 비디오 -->
        <!-- 비디오가 켜져있을 때 -->
        <div class="button-range" @click="toggleCam" v-if="!cam">
          <div class="icon-range">
            <v-icon size="40">
              mdi-video-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            비디오 <span style="color: red;">끄기</span>
          </div>
        </div>

        <!-- 비디오가 꺼져있을 때 -->
        <div class="button-range" @click="toggleCam" v-else>
          <div class="icon-range">
            <v-icon size="40" color="red">
              mdi-video-off-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            비디오 켜기
          </div>
        </div>

        <!-- 마이크 -->
        <!-- 마이크가 켜져있을 때 -->
        <div class="button-range" @click="toggleMic" v-if="!mic">
          <div class="icon-range">
            <v-icon size="40">
              mdi-microphone
            </v-icon>
          </div>
          <div class="button-range-title">
            마이크 <span style="color: red;">끄기</span>
          </div>
        </div>

        <!-- 마이크가 꺼져있을 때 -->
        <div class="button-range" @click="toggleMic" v-else>
          <div class="icon-range">
            <v-icon size="40" color="red">
              mdi-microphone-off
            </v-icon>
          </div>
          <div class="button-range-title">
            마이크 켜기
          </div>
        </div>

        <!-- 응원가 -->
        <!-- 응원가가 켜져있을 때 -->
        <div class="button-range" @click="toggleBullhorn" v-if="!bullhorn">
          <div class="icon-range">
            <v-icon size="40">
              mdi-bullhorn-outline
            </v-icon>
          </div>
          <div class="button-range-title">
            응원가 <span style="color: red;">끄기</span>
          </div>
        </div>

        <!-- 응원가가 꺼져있을 때 -->
        <div class="button-range" @click="toggleBullhorn" v-else>
          <div class="icon-range">
            <v-icon size="40">
              mdi-bullhorn
            </v-icon>
          </div>
          <div class="button-range-title">
            응원가 켜기
          </div>
        </div>
      </div>
    </div>

    <!-- 채팅 -->
    <div v-if="roomStore.isClickChatting == 'a'" class="match-screen-chatting-area" >
      <!-- 채팅 헤더 -->
      <div class="match-screen-chatting-header">
        <v-icon class="match-screen-chatting-header-setting-button" @click="roomStore.isClickSettingButton = !roomStore.isClickSettingButton">
          mdi-cog
        </v-icon>

        <!-- 드롭 다운 -->
        <div v-if="roomStore.isClickSettingButton" class="setting-dropdown">
          <div @click="clickBillboard">
            전광판
          </div>
          <div @click="clickGameInfo">
            경기 정보
          </div>
        </div>

        <!-- 전광판 -->
        <div v-if="roomStore.isClickBillboard">
          전광판
        </div>
        <!-- 경기 정보 -->
        <div v-if="roomStore.isClickGameInfo" style="position: relative;">
          <div class="room-game-info-header" @click="roomStore.isClickGameInfo = false">
            <v-icon>
              mdi-close
            </v-icon>
          </div>
          <div class="room-game-info" id="room-game-info">
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

      <!-- 채팅 내용 창 -->
      <div class="match-screen-chatting-section" id="match-screen-chatting-section">
        <div v-show="isOpenedChattingWindow" class="chatting-window">
          <div id="chatting-content" style="max-width:222px;"></div>
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

import { useAccountStore, useRoomStore,  } from "@/store/index.js";

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

      mySessionId: undefined,
      myUserName: undefined,
      sessionInfo: undefined,
      roomStore: useRoomStore(),
      isOpenedChattingWindow: true,
      message: "",
      //테스트용 스트리밍 URL.
      //서버에서 스트리밍을 시작해야 보임
      //추후 스트리밍 서버 설정 방법 고려 후 변경 예정
      stream_urls: "https://i7b204.p.ssafy.io/tmp/hls/test/index.m3u8",

      mic: false,
      cam: false,
      bullhorn: false,
    };
  },
  mounted() {
    this.mySessionId = this.$route.params.session;

    this.inMount();
    console.log(this.sessionInfo)

    // 사용한 피니아 변수 초기화
    this.roomStore.isClickChatting = ''
    this.roomStore.isClickLayout = false
    this.roomStore.screenWidth = '1400px'
    this.roomStore.screenHeight = '800px'
    this.roomStore.isClickSettingButton = false
    this.roomStore.isClickBillboard = false
    this.roomStore.isClickGameInfo = false
  },

  methods: {
    async inMount(){
      this.mySessionId = this.$route.params.session;
      this.myUserName = useAccountStore().profile.nickname;
      await useRoomStore().getInfo(this.mySessionId)
      .then(() => this.sessionInfo = useRoomStore().roomInfo);
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

        for(const badWord in BAD_WORDS_LIST) {
          if(message.includes(badWord)) {
            message = '[삭제된 메세지]';
            break; 
          }
        }

        document.getElementById("chatting-content").innerHTML += `<div>${userName}:</div>`;
        document.getElementById("chatting-content").innerHTML += `<div style:'word-break:break-all; max-width: 222px;'>${message}<div>`;
        
        var objDiv = document.getElementById("match-screen-chatting-section");
        objDiv.scrollTop = objDiv.scrollHeight;

      });

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
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
      this.$router.push({ name: "MainPage" });
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
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
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
      this.roomStore.isClickGameInfo = false
      if (this.roomStore.isClickChatting == '') {
        this.roomStore.isClickChatting = 'a'
      }
      else if (this.roomStore.isClickChatting == 'a'){
        this.roomStore.isClickChatting = 'b'
      }
      else {
        this.roomStore.isClickChatting = 'a'
      }
    },

    sendChat() {
      if (this.message && this.message != "") {
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
    
    toggleMic(){
      this.publisher.publishAudio(this.mic);   // true to unmute the audio track, false to mute it
      this.mic = !this.mic;
    },
    toggleCam(){
      this.publisher.publishVideo(this.cam);   // true to enable the video track, false to disable it
      this.cam = !this.cam;
    },
    toggleBullhorn(){
      this.bullhorn = !this.bullhorn;
    },
    clickBillboard() {
      this.roomStore.isClickSettingButton = false
      this.roomStore.isClickBillboard = true
    },
    clickGameInfo() {
      this.roomStore.isClickSettingButton = false
      this.roomStore.isClickGameInfo = true
      // this.roomStore.getGameInfo(this.roomStore.playTeams.apiId)
      this.cam = !this.cam;

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

/* 레이아웃 버튼 */
.match-screen-layout {
  margin: 10px 30px 0 0;
  width: 100px;
  height: 30px;
  background-color: #ecf0f5;
  text-align: center;
  border-radius: 3px;
  border: 1px solid #54575b;
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
  width: 98%;
  height: 180px;
  margin-right: 60px;
  display: flex;

}


/* 하단 버튼  */
.match-screen-footer {
  margin: 50px 0 0 0;
  display: flex;
  flex-direction: row-reverse;
  height: 50px;
}
.match-screen-footer > div {
  margin: 0 30px 0 0 ;
}
.button-range {
  width: 190px;
  height: 70px;
  background-color: #cfd3d8;
  display: flex;
  font-family: var(--bold-font);
  font-size: 20px;
  border-radius: 90px;

}
.button-range:hover {
  cursor: pointer;
}
.icon-range {
  margin: 9px 0 0 9px;
  padding: 4px;
  background-color: darkgray;
  width: 50px;
  height: 50px;
  border-radius: 50px;
}
.button-range-title {
  padding: 20px 0 0 10px;
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
  height: 780px;
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
  width: 200px;
  height: 70px;
  border-radius: 20px;
  background-color: var(--sub-color);
  display: flex;
}
.chatting-input {
  max-width: 150px;
  min-width: 150px; 
  max-height: 50px;
  min-height: 50px;
  margin: auto 0;
  overflow: auto;
  background: none;
  padding: 5px;
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

/* 드롭다운 영역 */

.setting-dropdown {
  position: absolute;
  right: 10px;
  top: 30px;
  width: 150px;
  height: 50px;
  border: 1px solid grey;
  display: flex;
  border-radius: 15px;
}
.setting-dropdown div {
  width: 74px;
  padding-top: 10px ;
  text-align: center;
}
.setting-dropdown div:hover {
  cursor: pointer;
}
.setting-dropdown div:first-child {
  border-right: 1px solid grey;
}

/* 드롭다운 경기 정보 */

.room-game-info {
  position: absolute;
  min-width: 300px;
  height: 500px;
  border: 1px solid grey;
  overflow-y: auto;
  margin-right: 50px;
  border-radius: 10px;
  background-color: #ffffff;
  left: -60px;
  padding-top: 10px;

}
.room-game-info-header {
  position: absolute;
  width: 279px;
  height: 40px;
  background-color: #ffffff;
  top: 1px;
  right: 20px;
  z-index: 10;
  padding-left: 260px;

}
.room-game-info-header:hover {
  cursor: pointer;
}
.room-game-info::-webkit-scrollbar {
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
  font-size: 18px;
}
.room-game-info-title:first-child {
  margin-top: 50px;
}
.room-game-info-section {
  margin: 5px 0 0 30px;
  font-family: var(--bold-font);
  font-size: 18px;
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
</style>
