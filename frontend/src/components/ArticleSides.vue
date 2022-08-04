<template>
  <div class="community-side">
    <div class="community-news-top">
      <p>오늘의 뉴스</p>
    </div>
    <div v-for="item in news" :key="item.news_id" class="community-news">
      <div class="maincolor" style="width:24px; text-align: center;">
        <p>{{item.news_id+1}}</p>
      </div>
      <div style="display: inline-block;">
        <p class="article-list">{{item.title}}</p>
      </div>
    </div>

    <div class="community-matches-top">
      <p style="font-size: 20px;">오늘의 경기</p>
      <p class="word-link" @click="this.$router.push({ name: 'Schedule',})" style="font-size: 14px;">자세히 보기</p>
    </div>
    <div v-for="match in matches" :key="match.id" class="community-matches">
      <div class="community-matches-teamname" style="justify-content: flex-end;">
        <a>
          {{match.teams[0]}}
        </a>
      </div>
      <div style="width:40px; height:40px; background-color: aquamarine;">
      </div>
      <div style="width:50px; height:60px;">
        <div class="community-matches-time">
          {{match.time}}
        </div>
        <div class="community-matches-versus">
          <p>vs</p>
        </div>
      </div>
      <div style="width:40px; height:40px; background-color:bisque;">
      </div>
      <div class="community-matches-teamname">
        <a>
          {{match.teams[1]}}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
// 아마 뉴스API로 가져올듯? 지금은 그냥 값 넣어보기
const news = [
  {
    news_id: 0,
    title: '그리스 언론 "오늘 황인범 온다...연봉 13억 X 3년 계약" 기대감↑',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 1,
    title: '레알 마드리드 레전드가 어쩌다…中 슈퍼리그 이적설',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 2,
    title: '"여전한 분노" 日, "중국 수비가 전혀 당황하지 않았다"',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 3,
    title: '우리 벤투 감독님이 달라졌어요',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 4,
    title: '"양현준 유니폼 없어요?"…핫플레이어 양현준, 인기 상승 뜨겁네',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 5,
    title: '"NEW 길례르미가 온다!" 황인범에게 그리스 현지 언론도 큰 기대',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 6,
    title: '안정적 수비와 빌드업…뉴페이스 조유민, 김민재 백업으로 급부상',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 7,
    title: '황인범 올림피아코스와 3년 계약, 연봉 100만유로…그리스 매체 보도',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 8,
    title: '부경고 전설의 14번을 아시나요[대통령금배]',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
  {
    news_id: 9,
    title: '"잘 샀다!" 10골 4도움 엄원상, 역대 이적생 중 손꼽히는 최고의 활약',
    content: '뭔가 굉장히 긴 내용이 있을거야'
  },
]

// 경기정보도 API에몽이 가져올거야~ 지금은 그냥 값 넣어보기
const matches = [
  {
    id: 0,
    teams: ['맨체스터 유나이티드', '토트넘 핫스퍼'],
    time: '13:00'
  },
  {
    id: 1,
    teams: ['노팅엄 포레스트', '뉴캐슬 유나이티드'],
    time: '15:00'
  },
  {
    id: 2,
    teams: ['레스터시티', '리버풀'],
    time: '20:00'
  },
  {
    id: 3,
    teams: ['리즈 유나이티드', '토트넘 핫스퍼'],
    time: '23:00'
  },
]
</script>

<style>
.community-side {
  width: 350px;
  margin-top: 140px;
  margin-left: 80px;
}
@media (max-width: 1580px) {
  .community-side {
    margin-top: 120px;
  }
}
.article-list { 
  text-overflow: ellipsis; 
  white-space: nowrap; 
  overflow: hidden; 
  width: 320px;
}
.article-list:hover {
  color: var(--main-color);
  cursor:pointer
}
.community-news-top {
  height:30px; 
  border-bottom: 1px solid #bcbcbc; 
  font-size: 20px;
}
.community-matches-top {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin:60px 0 20px;
  height:30px;
  border-bottom: 1px solid #bcbcbc;
}
.community-news {
  display:flex; 
  height:30px; 
  align-items: center; 
  font-size: 14px;
}
.community-matches {
  display:flex; 
  width:350px; 
  height:60px;
}
.community-matches-teamname {
  display:flex; 
  width:110px; 
  height:40px; 
  font-size: 11px; 
  align-items: center; 
}
.community-matches-time {
  display:flex;
  justify-content: center;
  height:10px;
  font-size: 10px;
}
.community-matches-versus {
  display:flex;
  justify-content: center;
  height:50px;
}
.word-link:hover {
  color: var(--main-color);
  cursor: pointer
}
</style>