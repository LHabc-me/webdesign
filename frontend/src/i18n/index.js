import {createI18n} from 'vue-i18n'
import zh from './zh.json'

const messages = {
  zh
}

const i18n = new createI18n({
  legacy: false,
  locale: 'zh',
  fallbackLocale: 'zh',
  messages,
})

export {i18n}

