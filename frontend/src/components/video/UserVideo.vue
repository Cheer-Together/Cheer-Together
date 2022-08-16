<template>
  <div v-if="streamManager" class="cam-range">
    <ov-video :stream-manager="streamManager" class="cam" />
    <div class="cam-user-name" >
      <!-- 이름 클릭하면 방장일 경우 강퇴 조치 -->
      {{ clientData }} <span id="emitIcon"  @click="onEmit(clientData)">⛔</span>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";
import Swal from "sweetalert2";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
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
        title: '정말 퇴장시키시겠습니까?',
        text: '닉네임 : ' + clientData,
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
            clientData + '님을 \n 내보냈습니다!',
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