import {createI18n} from 'vue-i18n'
import zh from './translations/zh.json'
import en from './translations/en.json'

const messages = {
  zh,
  en,
}

const i18nConfig = {
  legacy: false,
  locale: 'zh',
  fallbackLocale: 'en',
  messages,
}

const i18n = new createI18n(i18nConfig)

export {i18n}

