import { createRouter, createWebHistory } from 'vue-router'
import MainPageView from '../views/MainPageView.vue'
import ArticleView from '../views/ArticleView.vue'
import ScheduleView from '../views/ScheduleView.vue'
import ArticleDetailView from '../views/ArticleDetailView.vue'
import SignupView from '../views/SignupView.vue'
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
    path: '/schedule',
    name: 'Schedule',
    component: ScheduleView
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
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const accountStore = useAccountStore()
  const token = sessionStorage.getItem('token')

  //authPages에 로그인이 필요한 RouterView를 등록하면 됨
  const authPages = ['ArticleDetail',]

  const isAuthRequired = authPages.includes(to.name)
  if (isAuthRequired && !token) {
    accountStore.loginDialogToggle()
  } else {
    next()
  }
})

export default router
