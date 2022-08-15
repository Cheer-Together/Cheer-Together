import { defineStore } from "pinia";
import axios from "axios";
import cheertogether from "@/api/cheertogether";
import Swal from "sweetalert2";
import router from "@/router/index.js";
import jwt_decode from "jwt-decode";
import { createRoom, getRoomInfo, getPopularRooms } from "@/api/room";
import { ref } from "vue";

export const useCommunityStore = defineStore("community", {
  state: () => ({
    toggle: false,
    isModify: false,
    modifingArticleId: false,
    modifingArticleContent: "",
    modifingArticleTitle: "",
    modifingArticleApiId: 39,
  }),
  actions: {
    communityMounted() {
      this.toggle = false;
    },
    communityToggle() {
      if (this.toggle) {
        this.toggle = false;
      } else {
        this.toggle = true;
      }
    },
    communityModify(article) {
      this.isModify = true;
      this.toggle = true;
      this.modifingArticleId = article.id;
      this.modifingArticleContent = article.content;
      this.modifingArticleTitle = article.title;
      this.modifingArticleApiId = article.apiId;
      router.push({ name: "Article" });
    },
  },
});
export const useAccountStore = defineStore("account", {
  state: () => ({
    loginDialog: false,
    isLogin: sessionStorage.getItem("token") ?? false,
    emailDoubleChecked: false,
    emailAuthCodeChecked: false,
    emailAuthCode: "AAAAAAAAAAA",
    passwordAccordance: "",
    passwordAccordance2: "",
    isPushEmail: false,
    isAllowPassword: false,
    isShowPasswordError: "",
    isShowPasswordError2: "",
    profile: {
      email: "",
      favoriteLeagueList: [],
      favoriteTeamList: [],
      myInfo: "",
      nickname: "",
      profileImage: "",
      role: "",
      point: "",
    },
    otherProfile: {
      email: "",
      favoriteLeagueList: [],
      favoriteTeamList: [],
      myInfo: "",
      nickname: "",
      profileImage: "",
      role: "",
      point: "",
    },
    profileId: false,
    isChangePasswordModal: false,
  }),
  getters: {},
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
      const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/;

      if (validateEmail.test(email)) {
        this.emailDoubleCheck(email);
        return;
      }
      Swal.fire({
        icon: "error",
        title: "유효한 이메일 형식이 아닙니다.",
      });
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
        method: "GET",
        params: {
          email: email,
        },
      })
        .then((res) => {
          console.log(res.data);
          this.emailAuthCode = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
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
        method: "GET",
        params: {
          email: email,
        },
      })
        .then(() => {
          this.emailDoubleChecked = true;
          Swal.fire({
            icon: "success",
            title: "중복 확인 완료",
          });
        })
        .catch((err) => {
          if (err.response.status === 400) {
            Swal.fire({
              icon: "error",
              title: "이미 가입한 이메일 입니다.",
            });
          } else {
            Swal.fire({
              icon: "error",
              title: "일단 400 외의 에러",
            });
          }
        });
    },

    emailAuthCodeCheck(inputCode) {
      /* 
      인증 코드 확인
        성공하면

        실패하면

      */
      if (this.emailAuthCode === inputCode) {
        this.emailAuthCodeChecked = true;

        Swal.fire({
          icon: "success",
          title: "인증 성공",
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "인증 실패",
        });
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
      const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;

      if (validatePassword.test(password)) {
        this.isAllowPassword == true;
        this.isShowPasswordError = false;
        return;
      }
      this.isShowPasswordError = true;
    },

    signUp(userInfo) {
      /* 
      POST: 회원 가입
        성공하면
          회원가입
        실패하면
    
      */
      axios({
        url: cheertogether.members.signUp(),
        method: "POST",
        data: {
          email: userInfo.email,
          favoriteLeagueList: userInfo.favoriteLeagueList,
          favoriteTeamList: userInfo.favoriteTeamList,
          myInfo: userInfo.myInfo,
          nickname: userInfo.nickname,
          password: userInfo.password,
          role: "USER",
        },
      })
        .then(() => {
          router.push({ name: "MainPage" });
        })
        .catch((err) => {
          console.log(err);
        });
    },

    withdrawal(userId) {
      /* 
      DELETE: 회원 탈퇴를 진행한다.
        성공하면
          유저 정보를 profile에 저장한다.
        실패하면

      */
      axios({
        url: cheertogether.members.withdrawal(userId),
        method: "DELETE",
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    changePassword(userInfo) {
      /* 
      POST: 회원의 비밀번호를 변경한다.
        성공하면
          비밀번호변경
        실패하면

      */
      axios({
        url: cheertogether.members.changePassword(userInfo.userId),
        method: "POST",
        data: {
          curPassword: userInfo.password1,
          newPassword: userInfo.password2,
        },
      })
        .then((res) => {
          console.log(res.data);
          this.isChangePasswordModal = false;
          Swal.fire({
            icon: "success",
            title: "비밀번호가 변경되었습니다.",
          });
        })
        .catch((err) => {
          console.log(err);
        });
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
        method: "GET",
        params: {
          id: userId,
        },
      })
        .then((res) => {
          console.log("유저정보 : " + res.data.point);
          this.profile = res.data;
          if (this.profile.favoriteTeamList.length > 0) {
            this.profile["profileImage"] = this.profile.favoriteTeamList[0].logo;
          } else {
            this.profile["profileImage"] = require("../assets/image/로고.png");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    presentUserProfile(userId) {
      /* 
      GET: 
        성공하면
          유저 정보를 profile에 저장한다.
        실패하면

      */
      axios({
        url: cheertogether.members.profile(userId),
        method: "GET",
        params: {
          id: userId,
        },
      })
        .then((res) => {
          this.otherProfile = res.data;

          if (this.otherProfile.favoriteTeamList.length > 0) {
            this.otherProfile["profileImage"] = this.otherProfile.favoriteTeamList[0].logo;
          } else {
            this.otherProfile["profileImage"] = require("../assets/image/로고.png");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    editUserProfile(userId) {
      /* 
      GET: 로그인 한 유저 아이디를 통해 유저 정보를 프로필에 저장
        성공하면
          유저 정보를 profile에 저장한다.
        실패하면

      */
      axios({
        url: cheertogether.members.editUserProfile(userId),
        method: "PUT",
        data: {
          favoriteLeagueList: this.profile.favoriteLeagueList,
          favoriteTeamList: this.profile.favoriteTeamList,
          myInfo: this.profile.myInfo,
          nickname: this.profile.nickname,
          profileImage: this.profile.profileImage,
        },
      })
        .then((res) => {
          router.push({ name: "Mypage", params: { userid: this.profileId } });
          this.userProfile(this.profileId);
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    loginDialogToggle() {
      /* 
      세션 스토리지에 토큰이 존재하지 않을 경우 (비 로그인 유저인 경우)
      로그인 모달을 열거나 닫습니다.
      */
      if (!sessionStorage.getItem("token")) {
        if (this.loginDialog) {
          this.loginDialog = false;
        } else {
          this.loginDialog = true;
        }
      }
    },

    loginAccount(user) {
      /*
      email과 password를 담은 user: Object를 입력받아 로그인을 시도합니다.
      로그인에 성공한 경우 응답(토큰)을 세션 스토리지에 저장합니다.
      TODO : 로그인에 실패한 경우 에러 메세지 표시, 로그인에 성공한 경우 유저 정보를 모아 스토어에 저장
      */
      console.log(user);
      axios({
        url: cheertogether.members.login(),
        method: "POST",
        data: user,
      })
        .then((res) => {
          sessionStorage.setItem("token", res.data);
          this.isLogin = true;
          const decoded = ref("");
          decoded.value = jwt_decode(res.data);
          this.profileId = decoded.value.sub;
          this.userProfile(decoded.value.sub);
          router.push({ name: "MainPage" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    kakaoLogin() {
      const API_KEY = process.env.VUE_APP_KAKAO_LOGIN_API_KEY;
      const REDIRECT_URI = process.env.VUE_APP_KAKAO_LOGIN_REDIRECT_URI;
      const url = "https://kauth.kakao.com/oauth/authorize?client_id=" + decodeURIComponent(API_KEY) + "&redirect_uri=" + decodeURIComponent(REDIRECT_URI) + "&response_type=code";
      window.location.replace(url);
    },
    socialLoginComplete(token) {
      this.isLogin = true;
      let userId = jwt_decode(token);
      this.profileId = userId;
      this.userProfile(userId);
    },
    logoutAccount() {
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("isSocialLogin");
      this.isLogin = false;
      Swal.fire({
        icon: "success",
        title: "성공적으로 로그아웃 되었습니다.",
      });
    },
    findPassword(email) {
      axios({
        url: cheertogether.members.findPassword(),
        method: "GET",
        params: {
          email: email,
        },
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
export const useLeagueStore = defineStore("league", {
  state: () => ({
    leagues: [],
    leaguesNoTeam: [],
    selectFavoriteTeam: "프리미어리그",
    favoriteLeague: false,
    favoriteTeam: false,
  }),
  actions: {
    leaguesAll() {
      /*
      앱 시작할 때 실행 시키고, 전체 데이터를 leagues에 담는다.
      */
      axios({
        url: cheertogether.league.leaguesAll(),
        method: "GET",
      })
        .then((res) => {
          this.leagues = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getLeaguesNoTeam() {
      /*
      앱 시작할 때 실행 시키고, 전체 데이터를 leagues에 담는다.
      */
      axios({
        url: cheertogether.league.leaguesNoTeam(),
        method: "GET",
      })
        .then((res) => {
          this.leaguesNoTeam = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
export const useScheduleStore = defineStore("schedule", {
  state: () => ({
    gamesAll: [], // 전체 경기 목록
    gamesMonth: [], // 월별 경기 목록
  }),
  persist: true,
  actions: {
    moveSchedulePage(date) {
      // SideBar에 '경기 일정'을 누르면 EPL 8월 스케쥴을 담아온다.
      axios({
        url: cheertogether.game.gamesLeagueMonth("39"),
        method: "GET",
        params: { date: date },
      }).then((res) => {
        (this.gamesMonth = []), (this.gamesMonth = res.data);
        router.push({ name: "Month", params: { leaguename: "프리미어리그", month: "8" } });
      });
    },

    clickLeague(event) {
      // 현재 라우터에 색깔 입히기
      const activeTag = document.querySelector(".league-active");
      activeTag.classList.remove("league-active");
      const clickedTag = event.target;
      clickedTag.classList.add("league-active");
      // 리그 클릭 시 가장 앞 달로 강제 이동
      const activeMonthTag = document.querySelector(".item-active");
      activeMonthTag.classList.remove("item-active");
      const firstMonthTag = document.querySelector(".schedule-page-month-item p");
      firstMonthTag.classList.add("item-active");
      // 해당 리그의 8월 정보를 보여준다.
      // state 변경하기
      if (clickedTag.innerText === "프리미어리그") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("39"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "프리미어리그", month: "8" } });
        });
      } else if (clickedTag.innerText === "라리가") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("140"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "라리가", month: "8" } });
        });
      } else if (clickedTag.innerText === "세리에 A") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("135"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "세리에 A", month: "8" } });
        });
      } else if (clickedTag.innerText === "분데스리가") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("78"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "분데스리가", month: "8" } });
        });
      } else if (clickedTag.innerText === "리그 1") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("61"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "리그 1", month: "8" } });
        });
      } else if (clickedTag.innerText === "K리그") {
        axios({
          url: cheertogether.game.gamesLeagueMonth("292"),
          method: "GET",
          params: { date: "2022-08" },
        }).then((res) => {
          (this.gamesMonth = []), (this.gamesMonth = res.data);
          router.push({ name: "Month", params: { leaguename: "K리그", month: "8" } });
        });
      }
    },

    clickMonth(leagueId, event) {
      // 색 바꾸기
      if (document.querySelector(".item-active")) {
        document.querySelector(".item-active").classList.remove("item-active");
      }
      const clickedTag = event.target;
      clickedTag.classList.add("item-active");
      const activeMonth = clickedTag.innerText.slice(-3, -1).trim();
      let alteredDate = "";
      if (activeMonth === "8" || activeMonth === "9") {
        alteredDate = "2022-0" + activeMonth;
      } else if (activeMonth === "10" || activeMonth === "11" || activeMonth === "12") {
        alteredDate = "2022-" + activeMonth;
      } else if (activeMonth === "1" || activeMonth === "2" || activeMonth === "3" || activeMonth === "4" || activeMonth === "5") {
        alteredDate = "2023-0" + activeMonth;
      }
      // 월별로 잘라서 담아주기
      axios({
        url: cheertogether.game.gamesLeagueMonth(`${leagueId}`),
        method: "GET",
        params: { date: alteredDate },
      }).then((res) => {
        (this.gamesMonth = []), (this.gamesMonth = res.data);
        // month parameter 담아서 라우터 이동
        router.push({ name: "Month", params: { month: `${activeMonth}` } });
      });
    },
  },
});
export const useOnAirStore = defineStore("onair", {
  state: () => ({
    rooms: [],
    makeRoomDialog: false,
  }),
  actions: {
    moveOnairPage() {
      axios({
        url: cheertogether.room.rooms(),
        method: "GET",
      })
        .then((res) => {
          this.rooms = res.data;
          router.push({ name: "Onair", params: { leaguename: "모든 응원방 목록" } });
        })
        .catch((err) => {
          console.log(err);
        });
    },

    moveLeagueRooms(event) {
      const leagues = [
        { id: "39", league: "프리미어리그" },
        { id: "140", league: "라리가" },
        { id: "135", league: "세리에 A" },
        { id: "78", league: "분데스리가" },
        { id: "61", league: "리그 1" },
        { id: "292", league: "K리그 1" },
      ];
      // 색 입히기
      if (document.querySelector(".sideBar-subtitle-active")) {
        const fromSubtitle = document.querySelector(".sideBar-subtitle-active");
        fromSubtitle.classList.remove("sideBar-subtitle-active");
      }
      const toSubtitle = event.target;
      toSubtitle.classList.add("sideBar-subtitle-active");

      for (let item of leagues) {
        if (toSubtitle.innerText === item.league) {
          axios({
            url: cheertogether.room.roomsLeague(item.id),
            method: "GET",
          })
            .then((res) => {
              this.rooms = res.data;
              router.push({ name: "Onair", params: { leaguename: `${item.league}` } });
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }
    },

    enterRoom(roomId) {
      if (!sessionStorage.getItem("token")) {
        Swal.fire({
          title: "로그인이 필요합니다",
          icon: "warning",
        });
        console.log(sessionStorage.getItem("token"));
      } else {
        axios({
          url: cheertogether.room.enterRoom(roomId),
          method: "GET",
        }).then((res) => {
          if (res.data.status === "PUBLIC") {
            router.push({ name: "Room", params: { session: `${res.data.sessionId}` } });
          } else if (res.data.status === "PRIVATE") {
            Swal.fire({
              title: "비밀번호를 입력하세요",
              icon: "info",
              input: "password",
              inputPlaceholder: "********",
              inputAttributes: {
                maxlength: 10,
                autocapitalize: "off",
                autocorrect: "off",
              },
            }).then((pw) => {
              if (pw.value === res.data.password) {
                router.push({ name: "Room", params: { session: `${res.data.sessionId}` } });
              } else {
                Swal.fire({
                  title: "비밀번호가 틀렸습니다",
                  icon: "error",
                });
              }
            });
          }
        });
      }
    },

    makeRoomDialogToggle() {
      if (this.makeRoomDialog) {
        this.makeRoomDialog = false;
      } else {
        this.makeRoomDialog = true;
      }
    },
    generateRandomString(num) {
      const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
      let result = "";
      const charactersLength = characters.length;
      for (let i = 0; i < num; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
      }
      console.log("makeRoom: " + result);
      return result;
    },
    async moveRoom(gameId, name, roomStatus, password, managerId) {
      let sessionId = this.generateRandomString(10);
      let status = roomStatus ? "PRIVATE" : "PUBLIC";
      console.log(gameId);
      let data = {
        gameId: gameId.id,
        name: name,
        roomStatus: status,
        password: password,
        managerId: managerId,
        sessionId: sessionId,
      };
      console.log(data);
      await createRoom(data).then(() => router.push({ name: "Room", params: { session: sessionId } }));
      console.log("CREATED");
    },
  },
});
export const useNavbarStore = defineStore("navbar", {
  state: () => ({
    isClickProfile: false,
  }),
});
export const useNewsStore = defineStore("news", {
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
        method: "GET",
        params: {
          subject: subject,
        },
      })
        .then((res) => {
          this.news = [];
          res.data.forEach((e) => {
            let title = e.title;
            let description = e.description;
            title = title.replaceAll("&apos;", "'");
            title = title.replaceAll("&quot;", '"');
            description = title.replaceAll("&apos;", "'");
            description = title.replaceAll("&quot;", '"');
            this.news.push({ link: e.link, title: title, description: description });
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
async function test(leagueApiId, date) {
  const response = await axios.get(cheertogether.game.gamesByDate(leagueApiId), { params: { date: date } });
  return response.data;
}
export const useGameStore = defineStore("game", {
  state: () => ({
    gamesAll: [],
    todayGames: [{}, {}, {}, {}, {}, {}],
    monthGames: [],
    today: [new Date(), new Date(), new Date(), new Date(), new Date(), new Date()],
    nextDate: [],
    preDate: [],
    month: [],
    date: [],
    day: [],
    dayName: ["일", "월", "화", "수", "목", "금", "토"],
    leagueApiId: [39, 140, 135, 78, 61, 292],
    startDate: [new Date(2022, 8, 5), new Date(2022, 8, 12), new Date(2022, 8, 13), new Date(2022, 8, 5), new Date(2022, 8, 5), new Date(2022, 2, 18)],
    endDate: [new Date(2023, 5, 28), new Date(2023, 6, 4), new Date(2023, 6, 3), new Date(2023, 5, 26), new Date(2023, 6, 2), new Date(2022, 9, 17)],
  }),
  actions: {
    yyyymmdd(dateIn) {
      var yyyy = dateIn.getFullYear();
      var mm = dateIn.getMonth() + 1; // getMonth() is zero-based
      var dd = dateIn.getDate();
      return String(10000 * yyyy + 100 * mm + dd); // Leading zeros for mm and dd
    },
    async generateTodayGames() {
      for (let i = 0; i < 6; i++) {
        this.month[i] = this.today[i].getMonth() + 1;
        this.date[i] = this.today[i].getDate();
        this.day[i] = this.dayName[this.today[i].getDay()];
        this.todayGames[i] = await test(this.leagueApiId[i], this.yyyymmdd(this.today[i]));
        while (this.todayGames[i].length == 0) {
          this.nextDate[i] = this.today[i];
          this.nextDate[i].setDate(this.nextDate[i].getDate() + 1);
          this.today[i] = new Date(this.nextDate[i]);
          this.month[i] = this.today[i].getMonth() + 1;
          this.date[i] = this.today[i].getDate();
          this.day[i] = this.dayName[this.today[i].getDay()];
          this.todayGames[i] = await test(this.leagueApiId[i], this.yyyymmdd(this.today[i]));
        }
      }
    },
    async clickNextDate(i) {
      if (this.today[i].getFullYear() == this.endDate[i].getFullYear() && this.today[i].getMonth() + 1 == this.endDate[i].getMonth() && this.today[i].getDate() - 1 == this.endDate[i].getDate()) {
        return false;
      }
      do {
        console.log(this.today[i].getDate() - 1);
        console.log(this.startDate[i].getDate());
        this.nextDate[i] = this.today[i];
        this.nextDate[i].setDate(this.nextDate[i].getDate() + 1);
        this.today[i] = new Date(this.nextDate[i]);
        this.todayGames[i] = await test(this.leagueApiId[i], this.yyyymmdd(this.today[i]));
        if (this.todayGames[i].length != 0) {
          this.month[i] = this.today[i].getMonth() + 1;
          this.date[i] = this.today[i].getDate();
          this.day[i] = this.dayName[this.today[i].getDay()];
        }
      } while (this.todayGames[i].length == 0);
    },
    async clickPreDate(i) {
      if (
        this.today[i].getFullYear() == this.startDate[i].getFullYear() &&
        this.today[i].getMonth() + 1 == this.startDate[i].getMonth() &&
        this.today[i].getDate() - 1 == this.startDate[i].getDate()
      ) {
        console.log("check");
        return false;
      }
      do {
        this.preDate[i] = this.today[i];
        this.preDate[i].setDate(this.preDate[i].getDate() - 1);
        this.today[i] = new Date(this.preDate[i]);
        this.todayGames[i] = await test(this.leagueApiId[i], this.yyyymmdd(this.today[i]));
        if (this.todayGames[i].length != 0) {
          this.month[i] = this.today[i].getMonth() + 1;
          this.date[i] = this.today[i].getDate();
          this.day[i] = this.dayName[this.today[i].getDay()];
        }
      } while (this.todayGames[i].length == 0);
    },
    getGamesByDate(leagueApiId, dateyyyy, i) {
      axios({
        url: cheertogether.game.gamesByDate(leagueApiId),
        method: "GET",
        params: {
          date: dateyyyy,
        },
      }).then((res) => {
        this.todayGames[i] = res.data;
      });
    },
  },
});
export const useRoomStore = defineStore("room", {
  state: () => ({
    roomInfo: undefined,
    roomsAll: [
      {
        gameId: 0,
        managerId: "힐히샴샤호휵",
        name: "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십",
        password: "",
        roomId: 0,
        status: "PRIVATE",
      },
      {
        gameId: 1,
        managerId: "",
        name: "",
        password: "",
        roomId: 1,
        status: "PRIVATE",
      },
    ],
    popularRooms: [],
    playTeams: {
      id: 31,
      home: {
        leagueName: "",
        name: "",
        hanName: "",
        logo: "",
        code: "",
        apiId: 0,
      },
      away: {
        leagueName: "",
        name: "",
        hanName: "",
        logo: "",
        code: "",
        apiId: 1,
      },
      kickoff: "",
      stadium: "",
      status: "",
      homeScore: 0,
      awayScore: 2,
      apiId: 867946,
      leagueApiId: 39,
    },
    goal: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
    homeGoalPoint : 0,
    awayGoalPoint : 0,
    isClickSettingButton: false,
    isClickBillboard: false,
    isClickGameInfo: false,
    isClickChatting: "",
    isClickLayout: false,
    isClickSetting: false,
    isClickPredictButton: false,
    screenWidth: "1400px",
    screenHeight: "800px",
    gameInfo: [],
    gameInfoHalf: [],
    mic: true,

    predictMonth: "",
    predictDate: "",
    predictDay: "",
    predictTime: "",
    
    gamePredictionDeadline: "",
  }),
  actions: {
    getRooms() {
      /* 
      GET: 응원방 조회
        성공하면
         
        실패하면

      */
      axios({
        url: cheertogether.room.rooms(),
        method: "GET",
      })
        .then((res) => {
          console.log(res.data);
          // this.roomsAll = res.data
        })
        .catch((err) => {
          console.log(err);
        });
    },

    async getInfo(sessionId) {
      await getRoomInfo(
        sessionId,
        (res) => {
          console.log(res);
          this.roomInfo = res.data;
          this.getPlayTeams(res.data.gameId);
        },
        (err) => {
          console.log(err);
        }
      );
    },

    async getPopularRooms(){
      await getPopularRooms(
        (res) => {
          this.popularRooms = res.data;
        },
        (err) => {
          console.log(err);
        }
      )
    },

    getPlayTeams(gameId) {
      /* 
    GET: 경기 정보를 불러옴
      성공하면

      실패하면
        에러 메시지 표시
    */
      axios({
        url: cheertogether.game.playGameInfo(gameId),
        method: "GET",
      })
        .then((res) => {
          console.log(res.data);
          let date = new Date(res.data.kickoff);
          date.setTime(date.getTime() + 10 * 60000);
          this.gamePredictionDeadline = date;

          this.playTeams = res.data;
          this.predictMonth = res.data.kickoff.substring(5, 7);
          this.predictDate = res.data.kickoff.substring(8, 10);

          const year = res.data.kickoff.substring(0, 4);
          const myStr = year + "-" + this.predictMonth + "-" + this.predictDate;
          var week = ["일", "월", "화", "수", "목", "금", "토"];
          var today = new Date(myStr).getDate();
          console.log(year, this.predictMonth, this.predictDate, myStr);
          this.predictDay = week[today];
          this.predictTime = res.data.kickoff.substring(11, 16);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    getGameInfo(apiId) {
      /* 
      GET: 경기 정보를 불러옴
        성공하면
  
        실패하면
          에러 메시지 표시
      */
      axios({
        url: cheertogether.game.gameInfo(),
        method: "GET",
        headers: {
          "x-rapidapi-host": process.env.VUE_APP_X_RAPIDAPI_HOST,
          "x-rapidapi-key": process.env.VUE_APP_X_RAPIDAPI_KEY,
        },
        params: {
          fixture: apiId,
        },
      })
        .then((res) => {
          this.gameInfo = [];
          this.gameInfoHalf = [];
          this.goal = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];

          this.homeGoalPoint = 0
          this.awayGoalPoint = 0

          res.data.response.reverse().forEach((e) => {
            if (e.type === "Goal" && e.team.id == this.playTeams.home.apiId) {
              this.goal[this.homeGoalPoint]["homeGoal"] = e.player.name;
              this.homeGoalPoint = this.homeGoalPoint + 1
            }
            else if (e.type === "Goal") {
              this.goal[this.awayGoalPoint]["awayGoal"] = e.player.name;
              this.awayGoalPoint = this.awayGoalPoint + 1
            }
            if (e.time.elapsed <= 45) {
              this.gameInfo.push(e);
            } 
            else {
              this.gameInfoHalf.push(e);
            }


          });

          this.goal.homeGoal = this.homeGoal
          this.goal.awayGoal = this.awayGoal
        })
        .catch((err) => {
          console.log(err);
        });
    },
    subtractPoint(memberId, team, pointToSend) {
      axios({
        url: cheertogether.members.subtractPoint(memberId),
        method: "PUT",
        data: { point: pointToSend },
      })
        .then(() =>
          Swal.fire({
            icon: "success",
            title: team + "팀에 " + pointToSend + "개의 축구공을 걸었습니다!⚽️",
          })
        )
        .catch((e) => console.log(e));
    },
    update(id, apiId) {
      axios({
        url: cheertogether.game.update(id),
        method: "PUT",
        params: {
          apiId: apiId,
        },
      }).then((res) => {
        console.log("경기정보 업데이트 후 응답 : " + res.data);
        this.playTeams = res.data;
      });
    },
  },
});
export const useGamePredictionStore = defineStore("gamePrediction", {
  state: () => {
    return {
      predictedPoint: 0,
      team1_point: 0,
      team1_count: 0,
      team2_point: 0,
      team2_count: 0,
      team1_predict_list: [],
      team2_predict_list: [],
      isPredictedList: [],
    };
  },
  persist: {
    storage: localStorage,
  },
  actions: {
    distributePoints() {
      const home = useRoomStore().playTeams.homeScore;
      const away = useRoomStore().playTeams.awayScore;

      if(this.predictedPoint >= 1) {
        if(home > away) {
          const perPoint = ((this.team1_point + this.team2_point) / this.team1_count) * this.predictedPoint;
          for(let member of this.team1_predict_list) {
            if(member == useAccountStore().profileId) {
              axios({
                url: cheertogether.members.plusPoint(member),
                method: "PUT",
                data: { point: perPoint },
              })
              .then(() => {
                Swal.fire({
                  icon: "success",
                  title: "🎉 승부예측 성공 🎉\n" + perPoint + "개 축구공 획득!⚽️",
                });
              })
              .catch(e => console.log(e));
              break;
            }
          }
        } else if(home < away) {
          const perPoint = ((this.team1_point + this.team2_point) / this.team2_count) * this.predictedPoint;
          for (let member of this.team2_predict_list) {
            if (member == useAccountStore().profileId) {
              axios({
                url: cheertogether.members.plusPoint(member),
                method: "PUT",
                data: { point: perPoint },
              })
                .then(() => {
                  Swal.fire({
                    icon: "success",
                    title: "🎉 승부예측 성공 🎉\n" + perPoint + "개 축구공 획득!⚽️",
                  });
                })
                .catch((e) => console.log(e));
              break;
            }
          }
        } else {
          axios({
            url: cheertogether.members.plusPoint(this.useAccountStore().profileId),
            method: "PUT",
            data: { point: this.predictedPoint },
          })
            .then(() => {
              Swal.fire({
                icon: "success",
                title: "🎉 무승부 🎉\n" + this.predictedPoint + "개 축구공을 돌려받습니다!⚽️",
              });
            })
            .catch((e) => console.log(e));
        }
      }
      this.predictedPoint = 0;
      this.team1_point = 0;
      this.team1_count = 0;
      this.team2_point = 0;
      this.team2_count = 0;
      this.team1_predict_list = [];
      this.team2_predict_list = [];
      this.isPredictedList = [];
    }
  }
});
