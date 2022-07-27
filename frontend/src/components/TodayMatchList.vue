<template>
  <div class="TodayMatchList">
    <div class="TodayMatchList-title">
      경기 일정
    </div>
    <div class="TodayMatchList-section">
      <!-- 프리미어리그 -->
      <div class="TodayMatchList-section-item">
        <!-- 리그 이름 + 더보기 있는 영역 -->
        <div class="TodayMatchList-item-header">
          <div class="TodayMatchList-section-title">
            프리미어리그
          </div>
          <div class="TodayMatchList-section-etc">
            더보기
            <v-icon style="width:10px; font-size:20px;">
              mdi-chevron-right
            </v-icon>
          </div>
        </div>
        <!-- 경기 일정 영역 -->
        <div class="TodayMatchList-item-section">
           <!-- 상단 하늘색 영역 -->
          <div class="TodayMatchList-item-section-header">
            <div>
              <v-icon style="width:10px; font-size:20px; margin-right: 5px;">
                mdi-chevron-left
              </v-icon>
                07.26 <span style="font-size:10px;">(화)</span>
              <v-icon style="width:10px; font-size:20px; margin-left: 5px;">
                mdi-chevron-right
              </v-icon>
            </div>
          </div>
          <!-- 내부 경기 상세 내용 -->
          <div class="TodayMatchList-item-match">
            <div class="TodayMatchList-item-match-team1">
              <div style="margin-right:5px">
                맨체스터 유나이티드
              </div>
              <img :src="premierLeague.맨유" width="25">
            </div>

            <div class="TodayMatchList-item-match-versus">
              <div>
                vs
              </div>

              <div style="color:grey;">
                20:00
              </div>
            </div>

            <div class="TodayMatchList-item-match-team2">
              <img :src="premierLeague.토트넘" width="25">
              <div style="margin-left:5px">
                토트넘 훗스퍼
              </div>
            </div>
          </div>

          <div class="TodayMatchList-item-match">
            <div class="TodayMatchList-item-match-team1">
              <div style="margin-right:5px">
                아스날
              </div>
              <img :src="premierLeague.아스날" width="25">
            </div>

            <div class="TodayMatchList-item-match-versus">
              <div>
                vs
              </div>

              <div style="color:grey;">
                20:00
              </div>
            </div>

            <div class="TodayMatchList-item-match-team2">
              <img :src="premierLeague.에버튼" width="25">
              <div style="margin-left:5px">
                에버튼
              </div>
            </div>
          </div>

          <div class="TodayMatchList-item-match">
            <div class="TodayMatchList-item-match-team1">
              <div style="margin-right:5px">
                첼시
              </div>
              <img :src="premierLeague.첼시" width="25">
            </div>

            <div class="TodayMatchList-item-match-versus">
              <div>
                vs
              </div>

              <div style="color:grey;">
                20:00
              </div>
            </div>

            <div class="TodayMatchList-item-match-team2">
              <img :src="premierLeague.리즈유나이티드" width="25">
              <div style="margin-left:5px">
                리즈 유나이티드
              </div>
            </div>
          </div>

          <div class="TodayMatchList-item-match">
            <div class="TodayMatchList-item-match-team1">
              <div style="margin-right:5px">
                레스터 시티
              </div>
              <img :src="premierLeague.레스터시티" width="25">
            </div>

            <div class="TodayMatchList-item-match-versus">
              <div>
                vs
              </div>

              <div style="color:grey;">
                20:00
              </div>
            </div>

            <div class="TodayMatchList-item-match-team2">
              <img :src="premierLeague.사우스햄튼" width="25">
              <div style="margin-left:5px">
                사우스 햄튼
              </div>
            </div>
          </div>
          
          <div class="TodayMatchList-item-match">
            <div class="TodayMatchList-item-match-team1">
              <div style="margin-right:5px">
                멘체스터 시티
              </div>
              <img :src="premierLeague.맨체스터시티" width="25">
            </div>

            <div class="TodayMatchList-item-match-versus">
              <div>
                vs
              </div>

              <div style="color:grey;">
                20:00
              </div>
            </div>

            <div class="TodayMatchList-item-match-team2">
              <img :src="premierLeague.아스톤빌라" width="25">
              <div style="margin-left:5px">
                아스톤 빌라
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 라리가 -->
      <div class="TodayMatchList-section-item">
        <!-- 리그 이름 + 더보기 있는 영역 -->
        <div class="TodayMatchList-item-header">
          <div class="TodayMatchList-section-title">
            라리가
          </div>
          <div class="TodayMatchList-section-etc">
            더보기
            <v-icon style="width:10px; font-size:20px;">
              mdi-chevron-right
            </v-icon>
          </div>
        </div>
        <!-- 경기 일정 영역 -->
        <div class="TodayMatchList-item-section">
           <!-- 상단 하늘색 영역 -->
          <div class="TodayMatchList-item-section-header">
            <div>
              <v-icon style="width:10px; font-size:20px; margin-right: 5px;">
                mdi-chevron-left
              </v-icon>
                07.26 <span style="font-size:10px;">(화)</span>
              <v-icon style="width:10px; font-size:20px; margin-left: 5px;">
                mdi-chevron-right
              </v-icon>
            </div>
          </div>
          <!-- 내부 경기 상세 내용 -->
          <div class="TodayMatchList-item-match-none" >
            오늘은 경기가 없습니다.
          </div>
        </div>
      </div>
      <!-- 세리에 A -->
      <div class="TodayMatchList-section-item">
        <!-- 리그 이름 + 더보기 있는 영역 -->
        <div class="TodayMatchList-item-header">
          <div class="TodayMatchList-section-title">
            세리에A
          </div>
          <div class="TodayMatchList-section-etc">
            더보기
            <v-icon style="width:10px; font-size:20px;">
              mdi-chevron-right
            </v-icon>
          </div>
        </div>
        <!-- 경기 일정 영역 -->
        <div class="TodayMatchList-item-section">
           <!-- 상단 하늘색 영역 -->
          <div class="TodayMatchList-item-section-header">
            <div>
              <v-icon style="width:10px; font-size:20px; margin-right: 5px;">
                mdi-chevron-left
              </v-icon>
                07.26 <span style="font-size:10px;">(화)</span>
              <v-icon style="width:10px; font-size:20px; margin-left: 5px;">
                mdi-chevron-right
              </v-icon>
            </div>
          </div>
          <!-- 내부 경기 상세 내용 -->
          <div class="TodayMatchList-item-match-none" >
            오늘은 경기가 없습니다.
          </div>
        </div>
      </div>
      <!-- 분데스리가 -->
    </div>
  </div>
</template>

<script setup>
const premierLeague = {
  맨유: require('../assets/image/프리미어리그/맨체스터유나이티드.png'),
  토트넘: require('../assets/image/프리미어리그/토트넘로고.png'),
  아스날: require('../assets/image/프리미어리그/아스날.png'),
  에버튼: require('../assets/image/프리미어리그/에버튼.png'),
  첼시: require('../assets/image/프리미어리그/첼시.png'),
  리즈유나이티드: require('../assets/image/프리미어리그/리즈유나이티드.png'),
  레스터시티: require('../assets/image/프리미어리그/레스터시티.png'),
  사우스햄튼: require('../assets/image/프리미어리그/사우스햄튼.png'),
  맨체스터시티: require('../assets/image/프리미어리그/맨체스터시티.png'),
  아스톤빌라: require('../assets/image/프리미어리그/아스톤빌라.png'),
}
</script>

<style>
.TodayMatchList {
  margin-bottom: 40px;
}
.TodayMatchList-title {
  font-size: 18px;
  padding: 5.5px 0;
}
.TodayMatchList-section {
  margin-top: 11px;
  width: 1580px;
  display: flex;
  font-size: 15px;
}
.TodayMatchList-section-item {
  width: 515px;
  height: 388px;
  margin-left: 20px;
}
.TodayMatchList-section-item:first-child {
  margin-left: 0px;
}
.TodayMatchList-item-header{
  display: flex;
  justify-content: space-between;
}
.TodayMatchList-section-title {
  height: 20px;
  margin: 9px 0;
}
.TodayMatchList-section-etc{
  height: 20px;
  margin: 9px 0;
}
.TodayMatchList-item-section {
  width: 515px;
  height: 350px;
  border: 1px solid var(--main-color);
}
.TodayMatchList-item-section-header {
  width: 513px;
  border-bottom: 1px solid var(--main-color);
  background-color: var(--sub-color);
  height: 50px;
  text-align: center;
}
.TodayMatchList-item-section-header > div{
  padding-top: 13.25px;
}
.TodayMatchList-item-match {
  text-align: center;
  display: flex;
  align-items: center;
  margin: 10px 0; 
}
.TodayMatchList-item-match-team1 {
  display: flex;
  width:200px;
  height: 25px;
  justify-content: flex-end;
  align-items: center;
}
.TodayMatchList-item-match-versus{
  width: 113px;
}
.TodayMatchList-item-match-team2 {
  display: flex;
  width:200px;
  height: 25px;
  justify-content: flex-start;
  align-items: center;

}
.TodayMatchList-item-match-none {
  text-align: center;
  margin-top: 10px;
}
@media (max-width: 1580px) {
.TodayMatchList {
  margin-bottom: 16px;
}
.TodayMatchList-title {
  font-size: 15px;
}
.TodayMatchList-section {
  margin-top: 8px;
  width: 1300px;
  font-size: 15px;
}
.TodayMatchList-item-section {
  width: 420px;
}
.TodayMatchList-item-section-header {
  width: 418px;
}
.TodayMatchList-item-match {
  font-size:12px;
}
}
</style>