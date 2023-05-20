import {defineStore} from 'pinia'
import {i18n} from "@/i18n";
import {watchEffect} from "vue";

export const useI18n = defineStore('i18n',
  {
    state: () => ({
      lang: i18n.global.locale.value,
    }),
    getters: {},
    actions: {
      changeLang(lang) {
        this.lang = lang
      }
    },
    persist: {
      enabled: true,
      strategies: [{
        key: 'i18n',
        storage: localStorage,
      }],
    }
  })

watchEffect(() => {
  i18n.global.locale.value = useI18n().lang
})
