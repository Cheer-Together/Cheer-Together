import { createRouter, createWebHistory } from 'vue-router'
import MainPageView from '../views/MainPageView.vue'
import ArticleView from '../views/ArticleView.vue'
import ScheduleView from '../views/ScheduleView.vue'
import ArticleDetailView from '../views/ArticleDetailView.vue'
import SignupView from '../views/SignupView.vue'
import OnAirRoomView from '../views/OnAirRoomView.vue'
import MyPageView from '../views/MyPageView.vue'
import MyPageEditView from '../views/MyPageEditView.vue'
import SocialSignupView from '../views/SocialSignupView'
import RoomVue from '../views/room/RoomView';
import ScheduleSelectMonth from '../components/ScheduleSelectMonth.vue'
import { useAccountStore } from "@/store"

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPageView
  },
  {
    path: '/article',
    name: 'Article',
    component: ArticleView
  },
  {
    path: '/schedule/:leaguename',
    name: 'Schedule',
    component: ScheduleView,
    children : [
      {path: ':month', name: 'Month', component: ScheduleSelectMonth}
    ]
  },
  {
    path: '/article/:articleid',
    name: 'ArticleDetail',
    component: ArticleDetailView
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
  {
    path: '/signup/social',
    name: 'SocialSignup',
    component: SocialSignupView
  },
  {
    path: '/onair/:leaguename',
    name: 'Onair',
    component: OnAirRoomView
  },
  {
    path: '/mypage/:userid',
    name: 'Mypage',
    component: MyPageView
  },
  {
    path: '/room/:session',
    name: 'Room',
    component: RoomVue
  },
  {
    path: '/mypage/:userid/edit',
    name: 'MypageEdit',
    component: MyPageEditView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const accountStore = useAccountStore()
  const token = sessionStorage.getItem('token')

  //authPages에 로그인이 필요한 RouterView를 등록하면 됨
  const authPages = ['ArticleDetail', 'Room', 'Mypage']

  const isAuthRequired = authPages.includes(to.name)
  if (isAuthRequired && !token) {
    accountStore.loginDialogMsg = '로그인이 필요한 서비스입니다.'
    accountStore.loginDialogToggle()
  } else {
    next()
  }
})

export default router
