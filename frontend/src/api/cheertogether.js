const HOST = 'http://i7b204.p.ssafy.io/cheertogether/'

const MEMBERS = 'members/'
// const article = 'article/'

export default {
  members: {
    emailAuth: () => HOST + MEMBERS + 'authenticate/email',
    emailDoubleCheck: () => HOST + MEMBERS + 'validate/duplicated',
    signUp: () => HOST + MEMBERS + 'join',
    login: () => HOST + MEMBERS + 'login',
  },
  league: {
    leaguesAll: () => HOST + 'leagues',


  },
  article: {

  },
}
