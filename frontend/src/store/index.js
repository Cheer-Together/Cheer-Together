import { defineStore } from 'pinia'
import axios from 'axios'
import cheertogether from '@/api/cheertogether'
import Swal from 'sweetalert2'
import router from '@/router/index.js';

export const useMainPageStore = defineStore('mainpage', {
  state: () => (
    { 
      mainNews: [
        {
          id: 0,
          img: require('../assets/image/메인페이지/뉴스/뉴스이미지1.jpg'),
          title: '리버풀 레전드, 슬림→`근육맨` 변신... 보디빌더 뺨치는 엄청난 근육',
          content: '잉글랜드 리버풀의 레전드 페르난도 토레스(38)가 엄청난 근육을 과시했다. 영국의 스포츠바이블은 21일(한국시간) "토레스가 휴가 중 리버풀 팬들과 만나는 영상에서 완벽한 보디빌더처럼 보인다"고 전했다. 영상 속 토레스는 팬들에게 사인을 해주는 팬서비스를 펼쳤는데, 무엇보다 토레스의 탄탄한 근육과 완벽한 식스팩이 눈에 띈다. 현역 시절 토레스는 슬림한 몸매와 빠른 스피드를 자랑했지만, 은퇴 이후 "폭풍" 피트니스 훈련을 통해 몸을 키웠다. 엄청난 운동량에 토레스의 몸이 확실히 커진 것을 느낄 수 있다.현재 토레스는 "친정팀" 아틀레티코 마드리드(스페인) 19세 이하(U-19) 팀 감독을 맡고 있다. 지난 해 10월에는 경기 중 포착된 토레스의 거대한 몸이 큰 화제가 된 바 있다. 당시 영국의 BT스포츠는 "그 어느 누구도 터치라인에서 토레스와 논쟁하지 않을 것"이라고 농담을 건넸다.스페인 출신의 공격수 토레스는 아틀레티코 마드리드, 리버풀, 첼시(잉글랜드), AC밀란(이탈리아) 등에서 뛰었다. 현역 시절 프리미어리그 최고의 공격수 중 한 명으로 평가받았다. 지난 2007년부터 2015년까지 리버풀, 첼시에서 뛰면서 85골을 몰아쳤다. 2010 남아공 월드컵 스페인 우승 멤버이기도 하다.'
        },
        {
          id: 1,
          img: require('../assets/image/메인페이지/뉴스/뉴스이미지2.jpg'),
          title: '"주급 2억8000 스타여, 우리에게로 오라" 승격팀의 패기',
          content: "승격팀 노팅엄 포레스트가 FA 자격을 얻은 스타 제시 린가드 영입전에 뛰어들었다는 소식이다. 린가드는 맨체스터 유나이티드와 잉글랜드 국가대표 출신 스타로 지난달 맨유와의 계약이 최종 만료됐다. 2011년부터 뛴 정든 맨유를 떠나기로 최종 결정이 내려졌다. 이후 여러 클럽 입단을 두고 상황을 보는 중이다. 현지 보도에 따르면 에버턴, 뉴캐슬 유나이티드, 레스터시티, 토트넘 등이 린가드에 관심을 보이고 있다. 웨스트햄은 가장 강력한 영입 후보다. 2020~2021 시즌 후반 린가드를 단기 임대로 영입해 쏠쏠한 재미를 봤던 추억을 잊지 못하고 있다. 16경기 9골을 기록했었다. 웨스트햄은 이후 줄기차게 린가드 완전 영입을 추진했었다. 여기에 미국 MLS 구단, 사우디아라비아 팀들도 린가드에 오퍼를 던지고 있다. 하지만 현지 매체 '가디언'은 여기에 더해 노팅엄이 강력한 경쟁자로 떠오르고 있다고 보도했다. 노팅엄은 지난 시즌 챔피언십에서 프리미어리그 승격에 성공한 팀. 나름의 선수 보강을 하고 있는 가운데 현재 공격수와 중앙 미드필더 수혈에 심혈을 기울이고 있다. 일단은 웨스트햄이 선두 주자인건 확실하다. 다만, 웨스트햄이 주급 18만파운드(약 2억8000만원)를 받는 스타를 데려가는 데 부담을 느끼고 있는 상태다. 때문에 노팅엄이 공격적 베팅을 한다면 린가드의 마음도 흔들릴 수 있다."
        },
        {
          id: 2,
          img: require('../assets/image/메인페이지/뉴스/뉴스이미지3.jpg'),
          title: "호날두 레알행?...아들 입은 유니폼 보면 '이적팀' 안다",
          content: "[마이데일리 = 이석희 기자]영국 프리미어리그 맨체스터 유나이티드에서 벗어나기를 원하고 있는 크리스티아누 호날두. 이달 초 팀에 이적을 요구한 후 지금까지 첼시, 바이에른 뮌헨, PSG 등 여러팀과 접촉을 했지만 거의 거부를 당한 상태이다.이런 상황에서 호날두가 이적하고픈 팀에 대한 힌트가 나왔다고 영국의 ‘미러’지가 21일 보도했다.다름 아닌 호날두의 아들이 레알 마드리드 유니폼을 입고 가족 나들이에 나섰기 때문이댜. ‘미러’는 호날두의 아들이 아빠의 미래가 어디에 있는 지에 대해 큰 힌트를 줄수 있다는 것이다.호날두는 앞에서 이야기했듯이 바이에른 뮌헨, 첼시, 파리 생제르맹에 이어 스페인 아틀레티코 마드리드의 이적 소문도 돌고 있는 상태이다. 보도에 따르면 디에고 시메오네가 전 리버풀 의 프론트맨 루이스 수아레스가 또 한번 대담한 이적 성공을 꿈꾸고 있다고 한다. 그래서 호날두가 아틀레티코 마드리드로 이적할 수도 있다는 것이 소문의 주된 내용이다.그런데 호날두의 막내아들 마테오가 아틀레티코의 완다 메트로폴리타노가 아닌 산티아고 베르나베우로의 이적 가능성에 대해 큰 힌트를 줬다고 ‘미러’는 추측했다.이유를 보면 한 틱톡 이용자가 올린 동영상 때문이다. 호날두의 가족들은 최근 마드리드의 한 센터에서 열린 로잘리아 콘서트 관람을 위해 나들이를 했다.",
        },
        {
          id: 3,
          img: require('../assets/image/메인페이지/뉴스/뉴스이미지4.jpg'),
          title: "[오피셜] '무리뉴가 좋아 로마로 간 사나이' 디발라, 마침내 옷피셜!",
          content: "[STN스포츠] 박재호 기자 = 파울로 디발라(28)가 AS로마의 유니폼을 입었다.AS로마는 21일(한국시간) 홈페이지를 통해 디발라의 영입을 공식 발표했다. 계약 기간은 3년이다. 새로운 등번호는 21번이다. 2015년부터 지난 시즌까지 유벤투스에서 뛴 디발라는 293경기에 출전해 115골 48도움을 기록했다. 세리에A에서 다섯 차례나 우승컵을 들어 올렸다. 이번 여름 유벤투스와 계약이 끝나자 토트넘, 인터밀란, 나폴리 등 여러 팀과 연결됐다. 결국 그의 선택은 조세 무리뉴 감독이 이끄는 AS로마였다. 이탈리아 매체 <풋볼 이탈리아>에 따르면 디발라와 무리뉴 감독은 이적 전 개별적으로 연락을 취해 이적을 논의했다. AS로마는 지난 시즌 세리에A 6위로 마감했지만 UEFA(유럽축구연맹) 유로파 컨퍼런스리그(UECL)에서 우승하며 61년 만에 UEFA 주관 대회 타이틀을 거머쥐었다. 정상급 공격수인 디발라의 합류로 더 높은 곳을 바라볼 수 있게 됐다."
        },
      ]
    }
  ),

})
export const useCommunityStore = defineStore('community', {
  state: () => (
    { 
      toggle: false,
      ids: 2,
      articles : [
        {
          article_id: 0,
          category: '프리미어리그',
          title: '사람가면나쁘다',
          content: '문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인문단 사이 거리 확인 자간 확인',
          updated_date: '8:37',
          author: '닭다리도둑',
          recommended: 27
        },
        {
          article_id: 1,
          category: '라리가',
          title: '이기는팀이우리팀',
          content: '내용은없다',
          updated_date: '8:32',
          author: '아카페라모카라때',
          recommended: 5
        }
      ]
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
    writeArticle(contents) {
      this.articles.push({
        article_id: this.ids++,
        category: contents.category,
        title: contents.title,
        content: contents.content,
        updated_date: contents.updated_date,
        author: contents.author,
        recommended: 0
      })
    }
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

        실패하면

      */
      axios({
        url: cheertogether.members.signUp(),
        method: 'POST',
        data: {
          email : userInfo.email,
          myInfo : userInfo.myInfo,
          nickname : userInfo.nickname,
          password : userInfo.password,
          profileImage : '.',
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
    /*
       로그인 시 사용하는 함수들
    */

    // 일단 대기


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
          console.log(res.data)
      }).catch(err => {
          console.log(err)
      })
    },
    logoutAccount() {
      sessionStorage.removeItem('token')
      this.isLogin = false
    }
  },
})
export const useLeagueStore = defineStore('league', {
  state: () => ({ 
    leagues: [
    ],
    selectLeague: [],
    selectTeam: [],
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
    }
  },
})