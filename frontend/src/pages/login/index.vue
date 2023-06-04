<template>
  <div class="pa-4 h-100"
       layout="column center-center">
    <VCard class="pa-4 pt-7"
           width="448">
      <VCardItem layout="column center-center">
        <VCardTitle class="font-weight-semibold text-2xl text-uppercase">
          {{ $t('website-name') }}
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 font-weight-semibold mb-1">
          {{ $t('website-welcome') }}
        </h5>
        <p class="mb-0">
          {{ $t('login-and-continue') }}
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="() => {}">
          <VRow>
            <VCol cols="12">
              <VTextField v-if="true"
                          v-model="form.email"
                          :label="$t('email')"
                          type="email"
                          :rules="[rules.required, rules.email]"
              />
              <VTextField v-model="form.password"
                          :label="$t('password')"
                          :type="isPasswordVisible ? 'text' : 'password'"
                          :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                          @click:append-inner="isPasswordVisible = !isPasswordVisible"
                          :rules="[rules.required, rules.pwdLength]"/>

              <div class="mt-1 mb-4"
                   layout="row">
                <VCheckbox self="left"
                           v-model="form.remember"
                           :label="$t('remember-me')"
                           color="primary"/>

                <router-link self="right"
                             class="ms-2 mb-1"
                             to="/reset-password">
                  {{ $t('forget-password') }}
                </router-link>
              </div>

              <VBtn
                :block="true"
                type="submit"
                @click="login"
                color="primary"
                :loading="loading">
                {{ $t('login') }}
              </VBtn>
            </VCol>

            <div class="mt-5"
                 layout="row center-center">
              <span>
                {{ $t('dont-have-account') }}
                <router-link to="/register">{{ $t('register') }}</router-link>
                {{ $t('or') }}
                <router-link to="/" @click="loginAsGuest">{{ $t('continue-as-guest') }}</router-link>
              </span>
            </div>

          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="less">
</style>

<script setup>
import {onMounted, ref} from 'vue'
import {post} from '@/net'
import {i18n} from '@/i18n'
import {useUser} from '@/store/modules/user'
import {useMessage} from '@/store/modules/message'
import {useRouter} from 'vue-router'
import {rules} from '@/assets/script/rules'
import {useTheme} from 'vuetify'
import {updateUserInfo} from '@/utils/updateUserInfo'

const message = useMessage()
const user = useUser()
const router = useRouter()
const theme = useTheme()

const form = ref({
  email: '',
  password: '',
  remember: false,
})
const isPasswordVisible = ref(false)
const loading = ref(false)


onMounted(() => {
  if (localStorage.getItem('remember')) {
    form.value.email = localStorage.getItem('email')
    form.value.password = localStorage.getItem('password')
    form.value.remember = true
  }
})

function login() {
  console.log('login')
  if (rules.email(form.value.email) !== true ||
    rules.pwdLength(form.value.password) !== true) {
    return
  }
  loading.value = true
  post('/api/login', {
    email: form.value.email,
    password: form.value.password
  }).then(({data}) => {
    if (data.success) {
      if (form.value.remember) {
        localStorage.setItem('remember', 'true')
        localStorage.setItem('email', form.value.email)
        localStorage.setItem('password', form.value.password)
      } else {
        localStorage.removeItem('remember')
        localStorage.removeItem('email')
        localStorage.removeItem('password')
      }

      localStorage.setItem('token', data.token)
      message.success(i18n.global.t('login-success'))
      updateUserInfo()
      router.push('/')
    } else {
      message.error(i18n.global.t('login-failed'))
    }
  }).catch(e => {
    console.log(e)
    message.error(i18n.global.t('login-failed'))
  }).finally(() => {
    loading.value = false
  })
}


function loginAsGuest() {
  console.log('loginAsGuest')
  user.type = 'guest'
}
</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
