<template>
  <video ref="video" controls></video>
</template>

<script>
import Hls from "hls.js";
//import { ref } from "vue";

export default {
  props: {
    stream_url: String,
  },
  mounted() {
    this.mountVideo();
  },
  methods: {
    mountVideo() {
      let hls = new Hls();
      let video = this.$refs["video"];
      hls.loadSource(this.stream_url);
      hls.attachMedia(video);
      hls.on(Hls.Events.MANIFEST_PARSED, () => {
        video.play();
      });
    },
  },
};
</script>

<style>
</style>