import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', {
  state: () => ({ count: 0 }),
  getters: {
    double: state => state.count * 2,
  },
  actions: {
    increment() {
      this.count++
    },
  },
})
export const useCommunityStore = defineStore('community', {
  state: () => (
    { 
      toggle: false,
      articles : [
        {
          article_id: 0,
          category: '프리미어리그',
          title: '사람가면나쁘다',
          content: '내용은없다',
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
    communityToggle() {
      if (this.toggle) {
        this.toggle = false
      } else {
        this.toggle = true
      }
    },
  },
})
