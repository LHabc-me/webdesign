<template>
  <div class="pa-4 h-100"
       layout="column center-center">
    <VCard class="pa-4 pt-7"
           max-width="448"
           layout="column center-center">
      <VCardItem layout="column center-center">
        <VCardTitle class="font-weight-semibold text-2xl text-uppercase">
          {{ $t('website.name') }}
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 font-weight-semibold mb-1">
          {{ $t('login.welcome') }}
        </h5>
        <p class="mb-0">
          {{ $t('login.login-and-continue') }}
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="() => {}">
          <VRow>
            <VCol cols="12">
              <VTextField
                v-if="true"
                v-model="form.email"
                :label="$t('login.email')"
                type="email"
                :rules="[rules.required, rules.email]"
              />
              <VTextField
                v-model="form.username"
                :label="$t('login.username')"
                :rules="[rules.required, rules.counter(3, 20)]"
              />
              <VTextField v-model="form.password"
                          :label="$t('login.password')"
                          :type="isPasswordVisible ? 'text' : 'password'"
                          :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
                          @click:append-inner="isPasswordVisible = !isPasswordVisible"
                          :rules="[rules.required, rules.counter(6,20)]"/>

              <div class="mt-1 mb-4"
                   layout="row">
                <VCheckbox self="left"
                           v-model="form.remember"
                           :label="$t('login.remember-me')"/>

                <a self="right"
                   class="ms-2 mb-1"
                   href="/forgetpassword">
                  {{ $t('login.forget-password') }}
                </a>
              </div>

              <!-- login button -->
              <VBtn
                :block="true"
                type="submit"
                @click="login"
              >
                {{ $t('login.login') }}
              </VBtn>
            </VCol>

            <div class="mt-5"
                 layout="row center-center">
              <span>
                {{ $t('login.dont-have-account') }}
                <router-link to="/register">{{ $t('login.register') }}</router-link>
                {{ $t('login.or') }}
                <router-link to="/">{{ $t('login.continue-as-guest') }}</router-link>
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
import logo from '@/assets/logo.png'
import {ref} from "vue";
import {post} from "@/net";
import {i18n} from "@/i18n";

const form = ref({
  email: '',
  username: '',
  password: '',
  remember: false,
})
const isPasswordVisible = ref(false)

const rules = {
  required: value => !!value || i18n.global.t('register.required'),
  counter: (i, j) => value => value.length >= i && value.length <= j || i18n.global.t('register.length-should-be-between-i-and-j', {i, j}),
  email: value => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || i18n.global.t('register.invalid-e-mail')
  },
}

function login() {
  console.log('login')
  if (!form.value.username || !form.value.password) {
    return
  }
  post('/api/login', {
    name: form.value.username,
    pwd: form.value.password
  }).then(({data}) => {
    console.log(data)
  })
}

</script>
<!--@formatter:off-->
<route>
{
  meta: {
    layout: 'blank',
  }
}
</route>
