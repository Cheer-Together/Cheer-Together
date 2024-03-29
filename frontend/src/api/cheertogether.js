import { API_HOST_URL } from "@/config";

const HOST = API_HOST_URL;

const MEMBERS = "members/";
const LEAGUES = "leagues/";
const NEWS = "news/";
const GAMES = "games/";
const ROOMS = "rooms/";

export default {
  members: {
    emailAuth: () => HOST + MEMBERS + "authenticate/email", // 이메일 인증
    emailDoubleCheck: () => HOST + MEMBERS + "validate/duplicated", // 이메일 중복 확인
    signUp: () => HOST + MEMBERS + "join", // 회원가입
    login: () => HOST + MEMBERS + "login", // 로그인
    findPassword: () => HOST + MEMBERS + "find/password", // 비밀번호 찾기
    profile: (userId) => HOST + MEMBERS + `${userId}`, // 회원 조회
    editUserProfile: (userId) => HOST + MEMBERS + `${userId}`, // 회원 수정
    withdrawal: (userId) => HOST + MEMBERS + `${userId}`, // 회원 탈퇴
    changePassword: (userId) => HOST + MEMBERS + `${userId}/` + "password", // 비밀번호 변경
    plusPoint: (userId) => HOST + MEMBERS + `${userId}/` + "point/plus",
    subtractPoint: (userId) => HOST + MEMBERS + `${userId}/` + "point/subtract", //포인트 감소
    getPointRanking: () => HOST + MEMBERS + "point/ranking",
    reissueToken: () => HOST + MEMBERS + "reissue",
  },
  league: {
    leaguesAll: () => HOST + LEAGUES, // 모든 리그 정보 조회
    leaguesNoTeam: () => HOST + LEAGUES + "noTeam", // 모든 리그 정보 조회 팀x
  },
  team: {
    team: (apiId) =>
      "https://i7b204.p.ssafy.io/cheertogether/teams/" + `${apiId}`,
  },
  news: {
    news: () => HOST + NEWS, // 뉴스 데이터 조회
  },
  game: {
    games: () => HOST + GAMES, // 경기일정 조회
    update: (id) => HOST + GAMES + `${id}`,
    gamesByDate: (leagueApiId) => HOST + GAMES + `${leagueApiId}` + "/date",
    playGameInfo: (gameId) => HOST + GAMES + "game/" + `${gameId}`, // 현재 응원 방의 게임 조회

    gameInfo: () => "https://v3.football.api-sports.io/fixtures/events", // 경기 정보
    gamesLeagueMonth: (leagueId) => HOST + GAMES + `${leagueId}/` + "month",
    gameInfoTest: (gameId) => HOST + GAMES + "game/" + `${gameId}` + "/detail", // 시연용 api 요청
  },
  cheeringSong: {
    cheeringSong: (teamId) =>
      "https://i7b204.p.ssafy.io/cheertogether/cheeringsong/" + `${teamId}`,
  },
  room: {
    rooms: () => HOST + ROOMS, // 모든 응원방 검색
    roomsLeague: (leagueApiId) => HOST + ROOMS + "league/" + `${leagueApiId}`,
    enterRoom: (roomId) => HOST + ROOMS + `${roomId}`,
    search: () => HOST + ROOMS + "search/",
    searchGame: (gameId) => HOST + ROOMS + "game/" + `${gameId}`,
  },
};
