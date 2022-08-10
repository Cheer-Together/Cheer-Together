const HOST = 'https://i7b204.p.ssafy.io/cheertogether/'

const MEMBERS = 'members/'
const LEAGUES = 'leagues/'
const NEWS = 'news/'
const GAMES = 'games/'
const ROOMS = 'rooms/'

export default {
  members: {
    emailAuth: () => HOST + MEMBERS + 'authenticate/email', // 이메일 인증
    emailDoubleCheck: () => HOST + MEMBERS + 'validate/duplicated', // 이메일 중복 확인
    signUp: () => HOST + MEMBERS + 'join', // 회원가입
    login: () => HOST + MEMBERS + 'login', // 로그인
    findPassword: () => HOST + MEMBERS + 'find/password', // 비밀번호 찾기
    profile: (userId) => HOST + MEMBERS + `${userId}`, // 회원 조회
    editUserProfile: (userId) => HOST + MEMBERS + `${userId}` // 회원 수정
    
  },
  league: {
    leaguesAll: () => HOST + LEAGUES, // 모든 리그 정보 조회
    leaguesNoTeam: () => HOST + LEAGUES + 'noTeam', // 모든 리그 정보 조회 팀x
  },
  news: {
    news: () => HOST +  NEWS // 뉴스 데이터 조회
  },
  game: {
    games: () => HOST + GAMES, // 경기일정 조회
    gamesLeagueMonth: (leagueId) => HOST + GAMES + `${leagueId}/` + 'month'
  },
  room: {
    rooms: () => HOST + ROOMS, // 모든 응원방 검색
  }
}
