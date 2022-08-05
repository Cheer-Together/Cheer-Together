<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="mypage">
      <!-- 헤더 -->
      <div class="mypage-header">
        <!-- 이미지 닉네임 소개 -->
        <div style="display:flex;">
          <!-- 프로필 이미지 -->
          <div class="mypage-header-image">
            <img :src="user.profileImage" alt="" width="200" height="200">
          </div>
          <!-- 닉네임 소개 -->
          <div>
            <!-- 닉네임 -->
            <div class="mypage-header-nickname">
              {{ user.nickname }}
            </div>
            <!-- 소개 -->
            <div class="mypage-header-info">
              <!-- {{ user.myInfo }} -->
              일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십
            </div>
          </div>
        </div>
        <!-- 회원정보수정 -->
        <div  class="mypage-header-editinfo">
          <v-icon>
            mdi-pencil-box
          </v-icon>
          회원정보수정
        </div>
      </div>
      <!-- 본문 -->
      <div class="mypage-section">

        <!-- 좋아하는 리그 -->
        <div class="mypage-section-favorite-league">
          <div class="mypage-section-favorite-league-title">
            {{ user.nickname }}님이 좋아하는 리그
          </div>
          <div style="display:flex;">
            <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
            <img :src="favoriteLeague.league.logo" alt="" >
            <div class="mypage-favorite-league-title">
              {{ favoriteLeague.league.hanName }}
            </div>
          </div>
          </div>
        </div>

        <!-- 좋아하는 팀 -->
        <div class="mypage-section-favorite-league">
          <div class="mypage-section-favorite-league-title">
            {{ user.nickname }}님이 좋아하는 팀
          </div>
          <div style="display:flex;">
            <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
              <img :src="favoriteLeague.league.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.league.hanName }}
            </div>
            </div>
                        <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
              <img :src="favoriteLeague.league.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.league.hanName }}
            </div>
            </div>
                        <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
              <img :src="favoriteLeague.league.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.league.hanName }}
            </div>
            </div>
                        <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
              <img :src="favoriteLeague.league.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.league.hanName }}
            </div>
            </div>
                        <div class="mypage-favorite-league" v-for="favoriteLeague in user.favoriteLeagueList" :key="favoriteLeague.id">
              <img :src="favoriteLeague.league.logo" alt="" >
              <div class="mypage-favorite-league-title">
                {{ favoriteLeague.league.hanName }}
            </div>
            </div>
            
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"

const user = {
  email: "tjdjer5@tjdejr.com",
  nickname: "tjdejr",
  profileImage: require('../assets/image/로고.png'),
  role: "user",
  myInfo: "tjdejr",
  favoriteLeagueList: [
    {
      id: 4,
      league: {
        id: 1,
        teamList: [
          {
            "id": 1,
            "name": "Arsenal",
            "logo": "https://media.api-sports.io/football/teams/42.png",
            "code": "ARS",
            "apiId": 42,
            "hanName": "아스널"
          },
          {
            "id": 2,
            "name": "Aston Villa",
            "logo": "https://media.api-sports.io/football/teams/66.png",
            "code": "AST",
            "apiId": 66,
            "hanName": "아스톤 빌라"
          },
          {
            "id": 3,
            "name": "Bournemouth",
            "logo": "https://media.api-sports.io/football/teams/35.png",
            "code": "BOU",
            "apiId": 35,
            "hanName": "본머스"
          },
          {
            "id": 4,
            "name": "Brentford",
            "logo": "https://media.api-sports.io/football/teams/55.png",
            "code": "BRE",
            "apiId": 55,
            "hanName": "브렌트포드"
          },
          {
            "id": 5,
            "name": "Brighton",
            "logo": "https://media.api-sports.io/football/teams/51.png",
            "code": "BRI",
            "apiId": 51,
            "hanName": "브라이턴 & 호브 알비온"
          },
          {
            "id": 6,
            "name": "Chelsea",
            "logo": "https://media.api-sports.io/football/teams/49.png",
            "code": "CHE",
            "apiId": 49,
            "hanName": "첼시"
          },
          {
            "id": 7,
            "name": "Crystal Palace",
            "logo": "https://media.api-sports.io/football/teams/52.png",
            "code": "CRY",
            "apiId": 52,
            "hanName": "크리스탈 팰리스"
          },
          {
            "id": 8,
            "name": "Everton",
            "logo": "https://media.api-sports.io/football/teams/45.png",
            "code": "EVE",
            "apiId": 45,
            "hanName": "에버튼"
          },
          {
            "id": 9,
            "name": "Fulham",
            "logo": "https://media.api-sports.io/football/teams/36.png",
            "code": "FUL",
            "apiId": 36,
            "hanName": "풀럼"
          },
          {
            "id": 10,
            "name": "Leeds",
            "logo": "https://media.api-sports.io/football/teams/63.png",
            "code": "LEE",
            "apiId": 63,
            "hanName": "리즈 유나이티드"
          },
          {
            "id": 11,
            "name": "Leicester",
            "logo": "https://media.api-sports.io/football/teams/46.png",
            "code": "LEI",
            "apiId": 46,
            "hanName": "레스터 시티"
          },
          {
            "id": 12,
            "name": "Liverpool",
            "logo": "https://media.api-sports.io/football/teams/40.png",
            "code": "LIV",
            "apiId": 40,
            "hanName": "리버풀"
          },
          {
            "id": 13,
            "name": "Manchester City",
            "logo": "https://media.api-sports.io/football/teams/50.png",
            "code": "MAC",
            "apiId": 50,
            "hanName": "맨체스터 시티"
          },
          {
            "id": 14,
            "name": "Manchester United",
            "logo": "https://media.api-sports.io/football/teams/33.png",
            "code": "MUN",
            "apiId": 33,
            "hanName": "맨체스터 유나이티드"
          },
          {
            "id": 15,
            "name": "Newcastle",
            "logo": "https://media.api-sports.io/football/teams/34.png",
            "code": "NEW",
            "apiId": 34,
            "hanName": "뉴캐슬 유나이티드"
          },
          {
            "id": 16,
            "name": "Nottingham Forest",
            "logo": "https://media.api-sports.io/football/teams/65.png",
            "code": "NOT",
            "apiId": 65,
            "hanName": "노팅엄 포레스트"
          },
          {
            "id": 17,
            "name": "Southampton",
            "logo": "https://media.api-sports.io/football/teams/41.png",
            "code": "SOU",
            "apiId": 41,
            "hanName": "사우스햄튼"
          },
          {
            "id": 18,
            "name": "Tottenham",
            "logo": "https://media.api-sports.io/football/teams/47.png",
            "code": "TOT",
            "apiId": 47,
            "hanName": "토트넘 홋스퍼"
          },
          {
            "id": 19,
            "name": "West Ham",
            "logo": "https://media.api-sports.io/football/teams/48.png",
            "code": "WES",
            "apiId": 48,
            "hanName": "웨스트햄 유나이티드"
          },
          {
            "id": 20,
            "name": "Wolves",
            "logo": "https://media.api-sports.io/football/teams/39.png",
            "code": "WOL",
            "apiId": 39,
            "hanName": "울버햄튼원더러스"
          }
        ],
        name: "Premier League",
        logo: "https://media.api-sports.io/football/leagues/39.png",
        apiId: 39,
        hanName: "프리미어리그"
      }
    },

  ]
}
</script>

<style>
.mypage {
  width: 100%;
  margin: 80px 0 0 210px;
}
.mypage-header {
  margin: 50px auto;
  width: 1300px;
  height: 350px;
  border-bottom: 1px solid #b6b9ba;
  padding: 75px 0 0 42.5px;
}
.mypage-header-image {
  width: 200px;
  height: 200px;
  border-radius: 150px;
  border: 1px solid #b6b9ba;
  margin-right:50px;
  overflow: hidden;
}
.mypage-header-nickname {
  padding: 7px;
  width: 300px;
  height: 50px;
  font-size: 32px;
}
.mypage-header-info {
  margin-top: 30px;
  padding: 10px;
  width: 940px;
  height: 120px;
  font-size: 17px;
  background-color: var( --sub-color );
}
.mypage-header-editinfo {
  margin: 20px 40px 0 auto;
  padding: 5px;
  width: 160px;
  height: 37px;
  background-color: blanchedalmond;
  border-radius: 30px;
  font-size: 18px;
  text-align: center;
}
.mypage-header-editinfo:hover {
  cursor: pointer;
}
.mypage-section {
  margin: 0 auto;
  width: 1300px;
  height: 700px;
}
.mypage-section-favorite-league {
  padding: 10px;
  height: 300px;
}
.mypage-section-favorite-league-title {
  font-size: 25px;
  font-family: var(--bold-font);
}
.mypage-favorite-league {
  margin: 20px;
  width: 150px;
  height: 150px;
}
.mypage-favorite-league-title {
  margin-top: 10px;
  font-size: 18px;
  width: 150px;
  text-align: center;
}
.mypage-section-favorite-team {
    padding: 10px;
  height: 300px;

}
</style>