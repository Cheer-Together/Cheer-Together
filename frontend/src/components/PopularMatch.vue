<template>
  <div class="popularMatch">
    <div class="popularMatch-title">
      실시간 인기 응원방
    </div>
    <div class="popularMatch-section">
      <!-- 첫 번째 -->
      <div class="popularMatch-section-item" @click="this.$router.push({ name:'MatchScreen', })">
        <div class="popularMatch-section-item-image">
          <img :src="popularThumbnail.first" width="300" height="150">
        </div>
        <div class="popularMatch-section-item-title">
          [K리그] 제주 VS 전북 명경기
        </div>
      </div>
      <!-- 두 번째 -->
      <div class="popularMatch-section-item">
        <div class="popularMatch-section-item-image">
          <img :src="popularThumbnail.second" width="300" height="150">
        </div>
        <div class="popularMatch-section-item-title">
          박지성 골!!! 한국축구 짱
        </div>
      </div>
      <!-- 세 번째 -->
      <div class="popularMatch-section-item">
        <div class="popularMatch-section-item-image">
          <img :src="popularThumbnail.third" width="300" height="150">
        </div>
        <div class="popularMatch-section-item-title">
          손흥민의 찰칵
        </div>
      </div>
      <!-- 네 번째 -->
      <div class="popularMatch-section-item">
        <div class="popularMatch-section-item-image">
          <img :src="popularThumbnail.fourth" width="300" height="150">
        </div>
        <div class="popularMatch-section-item-title">
          손흥민의 호우!!!!!
        </div>
      </div>
      <!-- 다섯 번째 -->
      <div class="popularMatch-section-item">
        <div class="popularMatch-section-item-image">
          <img :src="popularThumbnail.fifth" width="300" height="150">
        </div>
        <div class="popularMatch-section-item-title">
          손흥민의 너로 정했다
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const popularThumbnail = {
  first: require('../assets/image/메인페이지/인기응원방썸네일1.png'),
  second: require('../assets/image/메인페이지/인기응원방썸네일2.jpg'),
  third: require('../assets/image/메인페이지/인기응원방썸네일3.jpg'),
  fourth: require('../assets/image/메인페이지/인기응원방썸네일4.jpg'),
  fifth: require('../assets/image/메인페이지/인기응원방썸네일5.jpg'),
}
</script>

<style>
.popularMatch {
  margin-bottom: 21px;
}
.popularMatch-title {
  font-size: 18px;
  padding: 5.5px 0;
}
.popularMatch-section {
  margin-top: 11px;
  width: 1580px;
  height: 170px;
  text-align: center;
  background-color: var(--sub-color);
  display: flex;
}
.popularMatch-section-item {
  width: 300px;
  height: 170px;
  margin-left: 20px;
}
.popularMatch-section-item:hover {
  cursor: pointer;
}
.popularMatch-section-item:first-child {
  margin: 0px;
}
.popularMatch-section-item-image {
  height: 150px;
}
.popularMatch-section-item-title {
  font-size: 12px;
  width: 300px;
  height: 20px;
  background-color: #222222;
  color: #ffffff;
}
@media (max-width: 1580px) {
.popularMatch {
  margin-bottom: 16px;
}
.popularMatch-title {
  font-size: 15px;
}
.popularMatch-section {
  margin-top: 8px;
  width: 1300px;
  height: 150px;
  overflow-x: hidden;
}
.popularMatch-section-title {
  padding-top: 78px ;
}
.popularMatch-section-item {
  width: 248px;
  height: 150px;
  margin-left: 15px;
}
.popularMatch-section-item-image{
  height: 130px;
}
.popularMatch-section-item-image img {
  width: 248px;
  height: 130px;
}
.popularMatch-section-item-title {
  font-size: 12px;
  width: 248px;
  height: 20px;
  background-color: #222222;
  color: #ffffff;
}
}
</style>