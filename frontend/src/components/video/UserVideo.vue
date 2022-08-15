<template>
  <div v-if="streamManager" class="cam-range">
    <ov-video :stream-manager="streamManager" class="cam" />
    <div class="cam-user-name" @click="onEmit()">
      <!-- 이름 클릭하면 방장일 경우 강퇴 조치 -->
      {{ clientData }}
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

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
    onEmit() {
      this.$emit("forceOut", this.clientData);
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

.cam-range:hover > .cam-user-name {
  display: block;
}

.cam-user-name {
  font-size: 20px;
  font-family: var(--bold-font);
  /* position: absolute;
  top: 0;
  right: 10px; */
  display: none;
  text-align: center;
}
</style>