import {defineStore} from 'pinia'

export const useMessage = defineStore('message', {
  state: () => ({
    msg: '',
    color: '',
    visible: false,
    showClose: false,
    timeout: 3000,
  }),
  getters: {},
  actions: {
    show({msg, color, timeout = 3000, showClose = false}) {
      this.msg = msg
      this.color = color
      this.timeout = timeout
      this.showClose = showClose
      this.visible = true
    },
    info(msg) {
      this.show({msg, color: 'green'})
    },
    success(msg) {
      this.show({msg, color: 'green'})
    },
    warning(msg) {
      this.show({msg, color: 'yellow'})
    },
    error(msg) {
      this.show({msg, color: 'red'})
    },
  },
})
