<template>
  <div class="pa-4 h-100"
       layout="column center-center">
    <VCard class="pa-4 pt-7"
           max-width="448"
           layout="column center-center">
      <VCardItem class="justify-center">

        <VCardTitle class="font-weight-semibold text-2xl text-uppercase">
          {{ $t('website.name') }}
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 font-weight-semibold mb-1">
          {{ $t('forgetpassword.welcome') }}
        </h5>
        <p class="mb-0">
          {{ $t('forgetpassword.register-your-account') }}
        </p>
      </VCardText>

      <VCardText>
        
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="less">
</style>

<script setup>
import {computed, ref} from "vue";
import {get, post} from "@/net";
import {useMessage} from "@/store/modules/message";
import {i18n} from "@/i18n";
import {useRouter} from "vue-router";
import {useUser} from "@/store/modules/user";
import {rules} from "@/assets/script/rules";

const message = useMessage()
const router = useRouter()
const user = useUser()

const form = ref({
  email: '',
  username: '',
  password: '',
  "repeat-password": '',
  "verification-code": '',
  remember: false,
})
const isPasswordVisible = ref(false)
const resendVerificationCodeInterval = ref(0)
const resendInterval = computed(() => resendVerificationCodeInterval.value > 0 ? `(${resendVerificationCodeInterval.value})` : '')


function resendVerificationCode() {
  if (rules.email(form.value.email) !== true) {
    message.error(i18n.global.t('rules.invalid-email'))
    return
  }
  resendVerificationCodeInterval.value = 60
  const interval = setInterval(() => {
    resendVerificationCodeInterval.value--
    if (resendVerificationCodeInterval.value === 0) {
      clearInterval(interval)
    }
  }, 1000)

  post('/api/register/verification-code', {
    email: form.value.email,
  }).then(({data}) => {
    console.log(data)
    if (data.success) {
      message.success(i18n.global.t('forgetpassword.verification-code-sent'))
    } else {
      message.error(i18n.global.t('forgetpassword.verification-code-sent-failed'))
    }
  })
}


const loading = ref(false)

function register() {
  console.log('register')
  if (rules.email(form.value.email) !== true ||
    rules.userNameLength(form.value.username) !== true ||
    rules.pwdLength(form.value.password) !== true ||
    rules.pwdLength(form.value['repeat-password']) !== true ||
    !form.value['verification-code']) {
    return
  }
  if (form.value.password !== form.value['repeat-password']) {
    return
  }
  loading.value = true

  post('/api/register', {
    email: form.value.email,
    name: form.value.username,
    pwd: form.value.password,
    "verification-code": form.value['verification-code'],
  }).then(({data}) => {
    console.log(data)
    if (data.message === 'success') {
      message.success(i18n.global.t('forgetpassword.register-success'))
      user.type = data.type
      user.id = data.id
      message.success('login.success')
      router.push('/')
    } else {
      message.error(i18n.global.t('forgetpassword.register-failed'))
    }
  }).catch(e => {
    console.log(e)
    message.error(i18n.global.t('forgetpassword.register-failed'))
  }).finally(() => {
    loading.value = false
  })
}

</script>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'user',
  }
}
</route>
