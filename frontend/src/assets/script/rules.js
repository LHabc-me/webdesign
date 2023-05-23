import {i18n} from "@/i18n";

const rules = {
  required: value => !!value || i18n.global.t('required'),
  pwdLength: value => value.length >= 6 && value.length <= 20 || i18n.global.t('length-should-be-between-i-and-j', {i: 6, j: 20}),
  userNameLength: value => value.length >= 3 && value.length <= 20 || i18n.global.t('length-should-be-between-i-and-j', {i: 3, j: 20}),
  email: value => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || i18n.global.t('invalid-email')
  },
  samePwd: target => value => value === target || i18n.global.t('two-passwords-not-match')
}

export {rules}
