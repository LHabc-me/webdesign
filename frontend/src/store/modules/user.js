import {defineStore} from 'pinia'

export const useUser = defineStore('user', {
  state: () => ({
    id: '',
    name: '',
    email: '',
    isLogin: false,
    coins: 0,//余额
    hot: 0,//热度

    /**@type {''|'guest'|'user'|'author'|'admin'}*/
    type: '',
  }),
  getters: {},
  actions: {},
})
