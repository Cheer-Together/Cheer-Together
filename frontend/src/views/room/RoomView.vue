<template>
  <NavBar/>
  <div style="display:flex; margin-top: 100px;">
    <!-- 세션 정보가 잘못 되었을 때 -->
    <div id="noSession" v-if="!mySessionId">세션 정보가 잘못 되었습니다.</div>
    <!-- 세션 정보가 잘 되었을 때 -->
    <div id="session" v-if="mySessionId">
      <!-- 헤더 -->
      <div class="match-screen-header">
        <div style="display:flex;">
          <div class="match-screen-title">
            {{ mySessionId }}
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
      <div class="match-screen-section" :style="{ height : matchScreenStore.screenHeight }">

      </div>

      <!-- 캠 레이아웃 -->
      <div id="video-container" v-if="matchScreenStore.isClickLayout">
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

    <div v-show="isOpenedChattingWindow" class="chatting-window">
      <ul id="chatting-content">
      </ul>
      <div class="">
        <input type="text" v-model="message" @keyup.enter="sendChat()" />
        <button type="button" @click="sendChat()">입력</button>
      </div>
    </div>
  </div>
</template>
<script>
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/video/UserVideo.vue";
import axios from "axios";
import { useAccountStore, useMatchScreenStore } from "@/store/index.js";
import NavBar from "@/components/NavBar.vue"

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = process.env.VUE_APP_OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OPENVIDU_SERVER_SECRET;

export default {
  name: "Match-Room",

  components: {
    NavBar,
    UserVideo,
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

      matchScreenStore: useMatchScreenStore(),
      isOpenedChattingWindow: true,
      message: "",
    };
  },
  mounted() {
    this.mySessionId = this.$route.params.session;
    this.myUserName = useAccountStore().profile.nickname;
    this.joinSession();
  },

  methods: {
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
        let message = receive[1];
        document.getElementById("chatting-content").innerHTML += `<li>${userName}:</li>`;
        document.getElementById("chatting-content").innerHTML += `${message}`;
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
      this.matchScreenStore.isClickLayout = !this.matchScreenStore.isClickLayout

      if ( this.matchScreenStore.screenHeight === '705px') {
        this.matchScreenStore.screenHeight = '465px'
      }
      else {
        this.matchScreenStore.screenHeight = '705px'
      }
    },
    chattingOnOff() {
      if (this.matchScreenStore.isClickChatting == '') {
        this.matchScreenStore.isClickChatting = 'a'
      }
      else if (this.matchScreenStore.isClickChatting == 'a'){
        this.matchScreenStore.isClickChatting = 'b'
      }
      else {
        this.matchScreenStore.isClickChatting = 'a'
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
#session {
  width: 100%;
  height: 830px;
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
.match-screen-layout:hover {
  cursor: pointer;
}
.match-screen-section {
  /* max-height: calc(100% - 240px - 100px); */
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
#video-container {
  width: 100%;
  height: 240px;
  margin-right: 20px;
  display: flex;
  background-color: #54575b;
}

</style>