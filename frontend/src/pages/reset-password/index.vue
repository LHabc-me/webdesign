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
          {{ $t('reset-password') }}
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
              <VTextField
                v-model="form.password"
                :label="$t('new-password')"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
                :rules="[rules.required, rules.pwdLength]"
              />
              <VTextField
                v-model="form['repeat-password']"
                :label="$t('repeat-password')"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
                :rules="[rules.required, rules.samePwd(form.password)]"
              />
              <VTextField
                v-model="form['verification-code']"
                :label="$t('verification-code')"
                type="text"
                :rules="[rules.required]"
              >
                <template #append-inner>
                  <VBtn class="h-75"
                        :disabled="resendVerificationCodeInterval !== 0"
                        @click="resendVerificationCode"
                        variant="flat"
                        color="primary">
                    {{ $t('send') + resendInterval }}
                  </VBtn>
                </template>
              </VTextField>

              <div class="mt-1 mb-4"
                   layout="row">
                <VCheckbox self="left"
                           v-model="form.remember"
                           :label="$t('remember-me')"
                           color="primary"
                />
                <span self="right">
                <router-link self="right"
                             to="/login"
                             class="mr-2">
                  {{ $t('login') }}
                </router-link>
                <router-link self="right"
                             to="/register">
                  {{ $t('register') }}
                </router-link>
              </span>
              </div>

              <VBtn :block="true"
                    type="submit"
                    @click="resetpwd"
                    color="primary"
                    :loading="loading"
              >
                {{ $t('reset-password') }}
              </VBtn>
            </VCol>
          </VRow>
        </VForm>
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
    message.error(i18n.global.t('invalid-email'))
    return
  }
  resendVerificationCodeInterval.value = 60
  const interval = setInterval(() => {
    resendVerificationCodeInterval.value--
    if (resendVerificationCodeInterval.value === 0) {
      clearInterval(interval)
    }
  }, 1000)

  post('/api/verification-code', {
    email: form.value.email,
  }).then(({data}) => {
    console.log(data)
    if (data.success) {
      message.success(i18n.global.t('verification-code-sent'))
    } else {
      message.error(i18n.global.t('verification-code-sent-failed'))
    }
  })
}


const loading = ref(false)

function resetpwd() {
  console.log('reset-password')
  console.log(rules.email(form.value.email) !== true)
  if (rules.email(form.value.email) !== true ||
    rules.pwdLength(form.value.password) !== true ||
    rules.pwdLength(form.value['repeat-password']) !== true ||
    rules.samePwd(form.value['repeat-password'])(form.value.password) !== true ||
    !form.value['verification-code']) {
    return
  }
  if (form.value.password !== form.value['repeat-password']) {
    return
  }
  loading.value = true

  post('/api/reset-password', {
    email: form.value.email,
    password: form.value.password,
    "verification-code": form.value['verification-code'],
  }).then(({data}) => {
    console.log(data)
    if (data.success) {
      message.success(i18n.global.t('reset-password-success'))
      router.push('/login')
    } else {
      message.error(i18n.global.t('reset-password-failed'))
    }
  }).catch(e => {
    console.log(e)
    message.error(i18n.global.t('reset-password-failed'))
  }).finally(() => {
    loading.value = false
  })
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
