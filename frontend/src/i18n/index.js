import {createI18n} from 'vue-i18n'
import zh from './zh.json'
import en from './en.json'

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

