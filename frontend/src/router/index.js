import { createRouter, createWebHistory } from 'vue-router'
import MainPageView from '../views/MainPageView.vue'
import ArticleView from '../views/ArticleView.vue'
import ScheduleView from '../views/ScheduleView.vue'
import SignupView from '../views/SignupView.vue'

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
    path: '/signup',
    name: 'Signup',
    component: SignupView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
