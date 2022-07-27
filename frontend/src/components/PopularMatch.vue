<template>
  <div class="PopularMatch">
    <div class="PopularMatch-title">
      실시간 인기 응원방
    </div>
    <div class="PopularMatch-section">
      <!-- 첫 번째 -->
      <div class="PopularMatch-section-item">
        <div class="PopularMatch-section-item-image">
          <img :src="popularThumbnail.first" width="300" height="150">
        </div>
        <div class="PopularMatch-section-item-title">
          [K리그] 제주 VS 전북 명경기
        </div>
      </div>
      <!-- 두 번째 -->
      <div class="PopularMatch-section-item">
        <div class="PopularMatch-section-item-image">
          <img :src="popularThumbnail.second" width="300" height="150">
        </div>
        <div class="PopularMatch-section-item-title">
          박지성 골!!! 한국축구 짱
        </div>
      </div>
      <!-- 세 번째 -->
      <div class="PopularMatch-section-item">
        <div class="PopularMatch-section-item-image">
          <img :src="popularThumbnail.third" width="300" height="150">
        </div>
        <div class="PopularMatch-section-item-title">
          손흥민의 찰칵
        </div>
      </div>
      <!-- 네 번째 -->
      <div class="PopularMatch-section-item">
        <div class="PopularMatch-section-item-image">
          <img :src="popularThumbnail.fourth" width="300" height="150">
        </div>
        <div class="PopularMatch-section-item-title">
          손흥민의 호우!!!!!
        </div>
      </div>
      <!-- 다섯 번째 -->
      <div class="PopularMatch-section-item">
        <div class="PopularMatch-section-item-image">
          <img :src="popularThumbnail.fifth" width="300" height="150">
        </div>
        <div class="PopularMatch-section-item-title">
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
.PopularMatch {
  margin-bottom: 21px;
}
.PopularMatch-title {
  font-size: 18px;
  padding: 5.5px 0;
}
.PopularMatch-section {
  margin-top: 11px;
  width: 1580px;
  height: 170px;
  text-align: center;
  background-color: var(--sub-color);
  display: flex;
}
.PopularMatch-section-item {
  width: 300px;
  height: 170px;
  margin-left: 20px;
}
.PopularMatch-section-item:hover {
  cursor: pointer;
}
.PopularMatch-section-item:first-child {
  margin: 0px;
}
.PopularMatch-section-item-image {
  height: 150px;
}
.PopularMatch-section-item-title {
  font-size: 12px;
  width: 300px;
  height: 20px;
  background-color: #222222;
  color: #ffffff;
}
@media (max-width: 1580px) {
.PopularMatch {
  margin-bottom: 16px;
}
.PopularMatch-title {
  font-size: 15px;
}
.PopularMatch-section {
  margin-top: 8px;
  width: 1300px;
  height: 150px;
  overflow-x: hidden;
}
.PopularMatch-section-title {
  padding-top: 78px ;
}
.PopularMatch-section-item {
  width: 248px;
  height: 150px;
  margin-left: 15px;
}
.PopularMatch-section-item-image{
  height: 130px;
}
.PopularMatch-section-item-image img {
  width: 248px;
  height: 130px;
}
.PopularMatch-section-item-title {
  font-size: 12px;
  width: 248px;
  height: 20px;
  background-color: #222222;
  color: #ffffff;
}
}
</style>