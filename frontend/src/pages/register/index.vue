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
          {{ $t('register.welcome') }}
        </h5>
        <p class="mb-0">
          {{ $t('register.register-your-account') }}
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="() => {}">
          <VCol cols="12">
            <VTextField v-if="true"
                        v-model="form.email"
                        :label="$t('register.email')"
                        type="email"
                        :rules="[rules.required, rules.email]"
            />
            <VTextField
              v-model="form.username"
              :label="$t('register.username')"
              :rules="[rules.required, rules.counter(3, 20)]"
            />
            <VTextField
              v-model="form.password"
              :label="$t('register.password')"
              :type="isPasswordVisible ? 'text' : 'password'"
              :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
              @click:append-inner="isPasswordVisible = !isPasswordVisible"
              :rules="[rules.required, rules.counter(6, 20)]"
            />
            <VTextField
              v-model="form['repeat-password']"
              :label="$t('register.repeat-password')"
              :type="isPasswordVisible ? 'text' : 'password'"
              :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
              @click:append-inner="isPasswordVisible = !isPasswordVisible"
              :rules="[rules.required, rules.sameAs(form.password)]"
            />
            <VTextField
              v-model="form['verification-code']"
              :label="$t('register.verification-code')"
              type="text"
              :rules="[rules.required]"
            >
              <template #append>
                <VBtn class="h-75">
                  {{ $t('register.get-verification-code') }}
                </VBtn>
              </template>
            </VTextField>

            <div class="mt-1 mb-4"
                 layout="row">
              <VCheckbox
                self="left"
                v-model="form.remember"
                :label="$t('register.remember-me')"
              />
              <span self="right">
                  {{ $t('register.already-have-account') }}
                  <a self="right"
                     href="/login">
                    {{ $t('register.login') }}
                  </a>
                </span>
            </div>

            <VBtn
              :block="true"
              type="submit"
              @click="register"
            >
              {{ $t('register.register') }}
            </VBtn>
          </VCol>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="less">
</style>

<script setup>
import {ref} from "vue";
import {post} from "@/net";
import {useMessage} from "@/store/modules/message";
import {i18n} from "@/i18n";

const message = useMessage()
const form = ref({
  email: '',
  username: '',
  password: '',
  "repeat-password": '',
  "verification-code": '',
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
  sameAs: target => value => value === target || i18n.global.t('register.two-passwords-not-match')
}

function register() {
  console.log('register')
  if (!form.value.username ||
    !form.value.password ||
    !form.value['repeat-password'] ||
    !form.value['verification-code']) {
    return
  }
  if (form.value.password !== form.value['repeat-password']) {
    return
  }
  post('/api/register', {
    name: form.value.username,
    pwd: form.value.password
  }).then(({data}) => {
    console.log(data)
    if (data.message === 'success') {
      message.info(i18n.global.t('register.register-success'))
    } else {
      message.error(i18n.global.t('register.register-failed'))
    }
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
