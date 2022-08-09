import { defineStore } from 'pinia'
import axios from 'axios'
import cheertogether from '@/api/cheertogether'
import Swal from 'sweetalert2'
import router from '@/router/index.js';
import jwt_decode from "jwt-decode"
import { ref } from "vue"


export const useCommunityStore = defineStore('community', {
  state: () => (
    { 
      toggle: false,
    }
  ),
  actions: {
    communityMounted() {
      this.toggle = false
    },
    communityToggle() {
      if (this.toggle) {
        this.toggle = false
      } else {
        this.toggle = true
      }
    },
  },
})
export const useAccountStore = defineStore('account', {
  state: () => ({ 
    loginDialog: false,
    isLogin: sessionStorage.getItem("token")??false,
    emailDoubleChecked: false,
    emailAuthCodeChecked: false,
    emailAuthCode: 'AAAAAAAAAAA',
    passwordAccordance: '',
    passwordAccordance2: '',
    isPushEmail: false,
    isAllowPassword: false,
    isShowPasswordError: '',
    myImage: '',
    profile: {
      email: '',
      favoriteLeagueList: [],
      favoriteTeamList: [],
      myInfo: '',
      nickname: '',
      profileImage: '',
      role: '',
    },
    profileId: false,
  }),
  getters: {
  },
  actions: {
    /*
      회원가입 시 사용하는 함수들
    */
    checkEmail(email) {
      /*
        이메일 유효성 검사
        들어온 값이 @ .com 의 올바른 형식인지 판단
        성공하면
          유효한 이메일이기 때문에 중복체크 함수로 이동
        실패하면
          실패 alert
      */
      const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/

      if (validateEmail.test(email)) {
          this.emailDoubleCheck(email)
          return
      }
      Swal.fire({
        icon: 'error',
        title: '유효한 이메일 형식이 아닙니다.',
      })
    }, 

    emailAuth(email) {
      /* 
      GET: 사용자에게 이메일 인증을 보냄
        성공하면
          해당 메일에 인증코드를 발송하고
          emailAuthCode를 인증코드로 변환
        실패하면
          에러 메시지 표시
      */

      axios({
        url: cheertogether.members.emailAuth(),
        method: 'GET',
        params: {
          email: email,
        }  
      })
        .then(res => {
          console.log(res.data)
          this.emailAuthCode = res.data
        })
        .catch(err => {
          console.log(err)
        })
    },

    emailDoubleCheck(email) {
      /* 
      GET: 이메일 중복 체크
        성공하면
          중복 확인 완료 alert
          이메일로 인증번호 전송
        실패하면
          400 에러: 이미 가입한 이메일로 에러 표시
          그 외 에러: 아직 미정
      */
      axios({
        url: cheertogether.members.emailDoubleCheck(),
        method: 'GET',
        params: {
          email: email,
        }  
      })
        .then(res => {
          console.log(res)
          this.emailDoubleChecked = true
          Swal.fire({
            icon: 'success',
            title: '중복 확인 완료',
          })
        })
        .catch(err => {
          if (err.response.status === 400) {
            Swal.fire({
              icon: 'error',
              title: '이미 가입한 이메일 입니다.',
            })
          }
          else {
            Swal.fire({
              icon: 'error',
              title: '일단 400 외의 에러',
            })
          }
          
        })
    },    

    emailAuthCodeCheck(inputCode) {
      /* 
      인증 코드 확인
        성공하면

        실패하면

      */
     console.log(this.emailAuthCode)
     console.log(inputCode)
      if (this.emailAuthCode === inputCode) {
        this.emailAuthCodeChecked = true

        Swal.fire({
          icon: 'success',
          title: '인증 성공',
        })
      }
      else {
        Swal.fire({
          icon: 'error',
          title: '인증 실패',
        })
      }
    }, 
    
    checkPassword(password) {
        /*
        비밀번호 유효성 검사
        영어, 숫자, 특수문자 조합 8- 20자 
        성공하면
          유효한 비밀번호이기 때문에 중복체크 함수로 이동
        실패하면
          실패 alert
      */
      const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/

      if (validatePassword.test(password)) {
        this.isAllowPassword == true
        this.isShowPasswordError = false
        console.log('ㄴㄴㄴ')
        return
    }
    this.isShowPasswordError = true
    console.log(this.isShowPasswordError)
    },

    signUp(userInfo) {
      /* 
      POST: 회원 가입
        성공하면
          회원가입
        실패하면

      */
      console.log(userInfo)
      axios({
        url: cheertogether.members.signUp(),
        method: 'POST',
        data: {
          email : userInfo.email,
          favoriteLeagueList : userInfo.favoriteLeagueList,
          favoriteTeamList: userInfo.favoriteTeamList,
          myInfo : userInfo.myInfo,
          nickname : userInfo.nickname,
          password : userInfo.password,
          profileImage : userInfo.profileImage,
          role : 'user'
        }  
      })
        .then(res => {
          console.log(res)
          router.push({name:'MainPage'})
        })
        .catch(err => {
          console.log(err)
          
        })
    },
    
    //   로그인 시 사용하는 함수들
    userProfile(userId) {
      /* 
      GET: 로그인 한 유저 아이디를 통해 유저 정보를 프로필에 저장
        성공하면
          유저 정보를 profile에 저장한다.
        실패하면

      */
      axios({
        url: cheertogether.members.profile(userId),
        method: 'GET',
        params: {
          id: userId
        }  
      })
        .then(res => {
          console.log(res.data)
          this.profile = res.data
        })
        .catch(err => {
          console.log(err)
          
        })
    },

    editUserProfile(userId) {
      /* 
      GET: 로그인 한 유저 아이디를 통해 유저 정보를 프로필에 저장
        성공하면
          유저 정보를 profile에 저장한다.
        실패하면

      */
    console.log(this.profile)
        axios({
          url: cheertogether.members.editUserProfile(userId),
          method: 'PUT',
          data: {
            favoriteLeagueList: this.profile.favoriteLeagueList,
            favoriteTeamList: this.profile.favoriteTeamList,
            myInfo: this.profile.myInfo,
            nickname: this.profile.nickname,
            profileImage: this.profile.profileImage,
          }
        })
          .then(res => {
            console.log(res.data)
            router.push({name: 'Mypage' , params: {userid: this.profileId } })
            this.userProfile(this.profileId)
          })
          .catch(err => {
            console.log(err)
            
          })
    },

    loginDialogToggle(){
      /* 
      세션 스토리지에 토큰이 존재하지 않을 경우 (비 로그인 유저인 경우)
      로그인 모달을 열거나 닫습니다.
      */
      if(!sessionStorage.getItem("token"))
      {
        if (this.loginDialog) {
          this.loginDialog = false
        } else {
          this.loginDialog = true
        }
      }
    },
  
    loginAccount(user) {
      /*
      email과 password를 담은 user: Object를 입력받아 로그인을 시도합니다.
      로그인에 성공한 경우 응답(토큰)을 세션 스토리지에 저장합니다.
      TODO : 로그인에 실패한 경우 에러 메세지 표시, 로그인에 성공한 경우 유저 정보를 모아 스토어에 저장
      */
      console.log(user)
      axios({
        url: cheertogether.members.login(),
        method: 'POST',
        data: user
      }).then(res => {
          sessionStorage.setItem('token', res.data)
          this.isLogin = true

          const toke = ref(sessionStorage.getItem('token')??'')
          const decoded = ref('')
          if (toke.value) {
            decoded.value = jwt_decode(toke.value)
          }
          this.prfileID = decoded.value.sub
          this.userProfile(decoded.value.sub)
          console.log(res.data)
      }).catch(err => {
          console.log(err)
      })
    },
    kakaoLogin() {
      const API_KEY = process.env.VUE_APP_KAKAO_LOGIN_API_KEY;
      const REDIRECT_URI = process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI;
      const url = "https://kauth.kakao.com/oauth/authorize?client_id=" + decodeURIComponent(API_KEY) + "&redirect_uri=" + decodeURIComponent(REDIRECT_URI) + "&response_type=code";
      window.location.replace(url);
    },
    logoutAccount() {
      sessionStorage.removeItem('token')
      this.isLogin = false
      Swal.fire({
        icon: 'success',
        title: '성공적으로 로그아웃 되었습니다.',
      })
    },
    findPassword(email) {
      axios({
        url: cheertogether.members.findPassword(),
        method: 'GET',
        params: {
          email: email,
        }  
      }).then(res => {
          console.log(res.data)
      }).catch(err => {
          console.log(err)
      })
    }
  },
})
export const useLeagueStore = defineStore('league', {
  state: () => ({ 
    leagues: [
    ],
    leaguesNoTeam: [],
    selectFavoriteTeam: '프리미어리그',
    favoriteLeague: false,
    favoriteTeam: false,
    }
  ),
  actions: {
    leaguesAll () {
      /*
      앱 시작할 때 실행 시키고, 전체 데이터를 leagues에 담는다.
      */
      axios({
        url: cheertogether.league.leaguesAll(),
        method: 'GET',
      })
        .then(res => {
          console.log(res.data)
          this.leagues = res.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    getLeaguesNoTeam () {
      /*
      앱 시작할 때 실행 시키고, 전체 데이터를 leagues에 담는다.
      */
      axios({
        url: cheertogether.league.leaguesNoTeam(),
        method: 'GET',
      })
        .then(res => {
          console.log(res.data)
          this.leaguesNoTeam = res.data
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
})
export const useScheduleStore = defineStore('schedule', {
  state: () => ({
    gamesAll: [], // 전체 경기 목록
    gamesMonth: [], // 월별 경기 목록

  }),
  actions: {
    moveSchedulePage(){
      // SideBar에 '경기 일정'을 누르면 경기 정보를 불러와 store에 저장
      axios({
        url: cheertogether.games.scheduleList(),
        method: 'GET'
      })
      .then(res => {
        this.gamesAll = res.data
        // 처음에는 EPL, 8월을 보여준다
        // 현재는 EPL밖에 없어서 임시로 짜놓은 코드임
        this.gamesMonth = []
        for(let game of this.gamesAll){
          if(game.kickoff.startsWith('2022-08') && game.leagueApiId === 39) {
            this.gamesMonth.push(game)
          } else { break; }
        }
        console.log(this.gamesMonth)
      })
      // router 이동
      router.push({name: 'Month', params: {leaguename: '프리미어리그', month: '8'}})
    },

    clickLeague(event) {
      // 현재 라우터에 색깔 입히기
      const activeTag = document.querySelector('.league-active')
      activeTag.classList.remove('league-active')
      const clickedTag = event.target
      clickedTag.classList.add('league-active')
      // 리그 클릭 시 가장 앞 달로 강제 이동
      const activeMonthTag = document.querySelector('.item-active')
      activeMonthTag.classList.remove('item-active')
      const firstMonthTag = document.querySelector('.schedule-page-month-item p')
      firstMonthTag.classList.add('item-active')
      // 해당 리그의 8월 정보를 보여준다.
      // state 변경하기
      if(clickedTag.innerText === '프리미어리그'){
        this.gamesMonth = []
        for(let game of this.gamesAll){
          if(game.kickoff.substring(6, 7) === '8' && game.home.leagueName === 'Premier League') {
            this.gamesMonth.push(game)
          } else { break; }}        
        
      // league 이름 담아서 라우터 이동
      router.push({name : 'Month', params: {leaguename: `${clickedTag.innerText}`, month: '8'} })
      }
    },

    clickMonth (event) {
      // 색 바꾸기
      const activeTag = document.querySelector('.item-active')
      activeTag.classList.remove('item-active')
      const clickedTag = event.target
      clickedTag.classList.add('item-active')
      // 월별로 잘라서 담아주기

      // month parameter 담아서 라우터 이동
      router.push({name: 'Month', params: {month: `${clickedTag.innerText.slice(-3, -1).trim()}`} })
      
    }
  }
})
export const useOnAirStore = defineStore('onair', {
  state: () => ({
    allRooms: [],
    roomsPL: [],
    roomsLL: [],
    roomsSA: [],
    roomsBL: [],
    roomsL1: [],
    roomsKL: [],
    makeRoomDialog: false,
  }),
  actions: {
    moveLeagueRooms(event){
      const leagues = ['프리미어리그', '라리가', '세리에 A', '분데스리가', '리그 1', 'K리그 1']
      if(document.querySelector('.sideBar-subtitle-active')){
        const fromSubtitle = document.querySelector('.sideBar-subtitle-active')
        fromSubtitle.classList.remove('sideBar-subtitle-active')
      }
      const toSubtitle = event.target
      toSubtitle.classList.add('sideBar-subtitle-active')
      for(let league of leagues){
        if(toSubtitle.innerText === league ) {
          this.fetchRooms()
          router.push({name: 'Onair' , params: {leaguename: `${toSubtitle.innerText}`} })
        } 
      }
      
    },

    fetchRooms(){
      // 데이터 받아오기
      axios({
        url: cheertogether.rooms.loadRooms(),
        method: 'GET',
      })
        .then(res => {
          this.allRooms = res.data
        })
        .catch(err => {
          console.log(err.message)
        })
      // if (leagueName === '프리미어리그'){
      //   this.rooms = 
      // } else if (leagueName === '라리가'){
      //   this.rooms = this.tmp_laliga
      // } else if (leagueName === '세리에 A'){
      //   this.rooms = this.tmp_serie
      // } else if (leagueName === '분데스리가'){
      //   this.rooms = this.tmp_bundesliga
      // } else if (leagueName === '리그 1'){
      //   this.rooms = this.tmp_ligue1
      // } else if (leagueName === 'K리그 1'){
      //   this.rooms = this.tmp_k_league
      // }
    },

    makeRoomDialogToggle() {
      if (this.makeRoomDialog) {
        this.makeRoomDialog = false
      } else {
        this.makeRoomDialog = true
      }
    }
  }
})
export const useNavbarStore = defineStore('navbar', {
  state: () => (
    { 
      isClickProfile: false,
    })
})
export const useMatchScreenStore = defineStore('match', {
  state: () => (
    { 
      isClickChatting: '',
    })
})
export const useNewsStore = defineStore('news', {
  state: () => ({ 
    news: [],
  }),
  actions: {
    getNews(subject) {
      /* 
      GET: 뉴스 데이터를 가져옴
        성공하면
         
        실패하면

      */
        axios({
          url: cheertogether.news.news(),
          method: 'GET',
          params: {
            subject : subject
          }
        })
          .then(res => {
            console.log(res.data)
            res.data.forEach((e) => {
              let title = e.title
              title = title.replaceAll('&apos;', "'")
              title = title.replaceAll('&quot;', '"')
              this.news.push({link: e.link, title: title})
            })
          })
          .catch(err => {
            console.log(err)
            
          })
    },

  }
})
export const useGameStore = defineStore('game', { 
  state: () => ({ 
    gamesAll: [],
    todayGames: [],
    monthGames: [],
  }),
  actions: {
    getGames(inputMonth) {
      /* 
      GET: 경기 일정 데이터 조회
        성공하면
         
        실패하면

      */
      axios({
        url: cheertogether.game.games(),
        method: 'GET',
      })
      .then(res => {

        let today = new Date();   

        let year = today.getFullYear(); // 년도
        let month = today.getMonth() + 1;  // 월
        let date = today.getDate();  // 날짜
        // let day = today.getDay();  // 요일
        if (0 < month < 10) {
          month = '0' + month
        }
        if (0 < inputMonth < 10) {
          inputMonth = '0' + inputMonth
        }
        if (0 < date < 10) {
          date = '0' + date
        }
        // myToday 는 오늘날
        // const myToday = year + '-' + month + '-' + date 
        const myMonth = year + '-' + inputMonth
        const myToday = '2022-08-06'

        // 모든 경기 정보
        this.gamesAll = res.data

        // 오늘 경기 정보
        res.data.filter((e) => {
          if(e.kickoff.startsWith(myToday)) {
            this.todayGames.push(e)       
          }
        })

        // inputMonth로 달을 받아 옴
        res.data.filter((e) => {
          if(e.kickoff.startsWith(myMonth)) {
            this.monthGames.push(e)       
          }
        })
        console.log(myToday)
        console.log(this.todayGames)
        console.log(this.monthGames)

      })
      .catch(err => {
        console.log(err)
        
      })
    },
  }
})