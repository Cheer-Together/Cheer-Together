<template>
  <div v-if="streamManager" class="cam-range">
    <ov-video :stream-manager="streamManager" class="cam" />
    <div class="cam-user-name" >
      <!-- 이름 클릭하면 방장일 경우 강퇴 조치 -->
      {{ clientData }} <span id="emitIcon"  @click="onEmit(clientData)" v-if="isSessionManager && accountStore.profile.nickname !== clientData">⛔</span>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";
import Swal from "sweetalert2";
import { useAccountStore, } from "@/store/index.js";

export default {
  name: "UserVideo",
  data() {
    return {
      accountStore : useAccountStore(),
    }
  },
  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    isSessionManager : Boolean,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    onEmit(clientData) {
      Swal.fire({
        title: '강제 퇴장! 😡',
        text: clientData + '님을 퇴장시키겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네. 내보낼래요!',
        cancelButtonText: '아뇨. 괜찮아요.'
      }).then((result) => {
        if (result.isConfirmed) {
          this.$emit("forceOut", this.clientData);
          Swal.fire(
            clientData + '님을 \n 퇴장시켰습니다! 🤦',
            '',
            'success'
          )
        }
      })
      
    },
  },
};
</script>

<style scoped>
.cam-range {
  position: relative;
}
.cam {
  width: 240px;
  border-radius: 5px;
  margin: 0 10px;
}
#emitIcon:hover {
  cursor: pointer;
}
.cam-user-name {
  margin: 0 10px;
  width: 240px;
  font-size: 20px;
  font-family: var(--bold-font);
  /* position: absolute;
  top: 0;
  right: 10px; */
  text-align: center;
  background-color: #FEFBF6;
}
</style>