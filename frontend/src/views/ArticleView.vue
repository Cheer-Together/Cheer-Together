<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <ArticleForm v-if="toggle"/>
    <div v-if="!toggle" class="community-main">
      <div style="display:flex; justify-content: space-between; height:60px">
        <div style="width:200px;">
          <v-select
          :items="items"
          label="리그 분류"
          dense
          solo
          ></v-select>
        </div>
        <div>
          <v-btn @click="toggle=true" style="height:34px; margin-left:20px">글 작성하기</v-btn>
        </div>
      </div>
      <div style="display:flex; height:38px; margin:0; text-align: center; align-items: center; border-bottom: 1px solid #bcbcbc;">
        <div style="width:100px">
          <p>리그</p>
        </div>
        <div style="width:380px">
          <p>제목</p>
        </div>
        <div style="width:100px">
          <p>등록일</p>
        </div>
        <div style="width:130px">
          <p>작성자</p>
        </div>
        <div style="width:80px">
          <p>조회수</p>
        </div>
      </div>

      <div v-for="article in articles" :key="article.article_id" style="display:flex; height:36px; margin:0; text-align: center; align-items: center;">
        <div style="width:100px">
          <p>{{article.category}}</p>
        </div>
        <div class="article-list" style="width:380px">
          <p>{{article.title}}</p>
        </div>
        <div style="width:100px">
          <p>{{article.updated_date}}</p>
        </div>
        <div style="width:130px">
          <p>{{article.author}}</p>
        </div>
        <div style="width:80px">
          <p>{{article.recommended}}</p>
        </div>
      </div>

      <div style="display:flex; text-align: center; align-items: center; width:600px; height:36px; margin: 20px auto;">
        <input type="text" size="34px" maxlength="30" placeholder=" 검색할 내용을 입력하세요." style="width:480px; height:34px; border-radius:5px; border: 1px solid #bcbcbc;">
        <v-btn style="height:34px; margin-left:20px">검색</v-btn>
      </div>
      <div style="width:280px; height:32px; margin: 10px auto;">
        <p>페이지네이션</p>
      </div>
    </div>

    <div class="community-side">

      <div style="height:30px; border-bottom: 1px solid #bcbcbc; font-size: 20px;">
        <p>오늘의 뉴우스</p>
      </div>
      <div v-for="item in news" :key="item.news_id" style="display:flex; height:30px; align-items: center; font-size: 14px;">
        <div class="maincolor" style="width:24px; text-align: center;">
          <p>{{item.news_id+1}}</p>
        </div>
        <div style="display: inline-block; text-overflow:ellipsis; white-space: nowrap; overflow: hidden; width: 320px;">
          <p class="article-list">{{item.title}}</p>
        </div>
      </div>

      <div style="margin:60px 0 20px; height:30px; border-bottom: 1px solid #bcbcbc; font-size: 20px;">
        <p>오늘경기</p>
      </div>
      <div v-for="match in matches" :key="match.id" style="display:flex; width:350px; height:60px;">
        <div style="display:flex; width:110px; height:40px; font-size: 11px; align-items: center; justify-content: flex-end;">
          <a>
            {{match.teams[0]}}
          </a>
        </div>
        <div style="width:40px; height:40px; background-color: aquamarine;">
        </div>
        <div style="width:50px; height:60px;">
          <div style="display:flex; justify-content: center; height:10px; font-size: 10px;">
            {{match.time}}
          </div>
          <div style="display:flex; justify-content: center; height:50px;">
            <p>vs</p>
          </div>
        </div>
        <div style="width:40px; height:40px; background-color:bisque;">
        </div>
        <div style="display:flex; width:110px; height:40px; font-size: 11px; align-items: center;">
          <a>
            {{match.teams[1]}}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleForm from "../components/ArticleForm.vue"
import { ref } from "vue"
const toggle = ref(false)
const items = [
  {title: '프리미어리그'},
  {title: '라리가'},
  {title: '세리에A'},
  {title: '분데스리가'},
  {title: '리그 1'},
  {title: 'K리그'},
]

// 아마 article을 DB에서 가져올듯함, 지금은 그냥 값 일일히 넣어보기
const articles = [
  {
    article_id: 0,
    category: '프리미어리그',
    title: '사람가면나쁘다',
    updated_date: '8:37',
    author: '닭다리도둑',
    recommended: 27
  },
  {
    article_id: 1,
    category: '라리가',
    title: '이기는팀이우리팀',
    updated_date: '8:32',
    author: '아카페라모카라때',
    recommended: 5
  }
]

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
.community-main {
  width: 790px;
  margin-top: 60px;
  margin-left: 80px;
}
.community-side {
  width: 350px;
  margin-top: 60px;
  margin-left: 80px;
}
.article-list:hover {
  color: var(--main-color);
  cursor:pointer
}
.maincolor {
  color: var(--main-color);
}
</style>