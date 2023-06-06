import {createI18n} from 'vue-i18n'
import zh from './translations/zh.json'
import en from './translations/en.json'
import ja from './translations/ja.json'
import ru from './translations/ru.json'
import de from './translations/de.json'

const messages = {
  zh,
  en,
  ja,
  ru,
  de,
}

const i18nConfig = {
  legacy: false,
  locale: 'zh',
  fallbackLocale: 'en',
  messages,
}

const i18n = new createI18n(i18nConfig)

export {i18n}

