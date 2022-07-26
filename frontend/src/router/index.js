import { createRouter, createWebHistory } from 'vue-router'
import MainPageView from '../views/MainPageView.vue'
import ArticleView from '../views/ArticleView.vue'

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPageView
  },
  {
    path: '/articleview',
    name: 'ArticleView',
    component: ArticleView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
