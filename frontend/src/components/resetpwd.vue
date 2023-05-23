<template>
  <VForm @submit.prevent="() => {}">
    <VCol cols="12">
      <VTextField v-if="true"
                  v-model="form.email"
                  :label="$t('forgetpassword.email')"
                  type="email"
                  :rules="[rules.required, rules.email]"
      />
      <VTextField
        v-model="form.username"
        :label="$t('forgetpassword.username')"
        :rules="[rules.required, rules.userNameLength]"
      />
      <VTextField
        v-model="form.password"
        :label="$t('forgetpassword.password')"
        :type="isPasswordVisible ? 'text' : 'password'"
        :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
        @click:append-inner="isPasswordVisible = !isPasswordVisible"
        :rules="[rules.required, rules.pwdLength]"
      />
      <VTextField
        v-model="form['repeat-password']"
        :label="$t('forgetpassword.repeat-password')"
        :type="isPasswordVisible ? 'text' : 'password'"
        :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
        @click:append-inner="isPasswordVisible = !isPasswordVisible"
        :rules="[rules.required, rules.samePwd(form.password)]"
      />
      <VTextField
        v-model="form['verification-code']"
        :label="$t('forgetpassword.verification-code')"
        type="text"
        :rules="[rules.required]"
      >
        <template #append-inner>
          <VBtn class="h-75"
                :disabled="resendVerificationCodeInterval !== 0"
                @click="resendVerificationCode"
                variant="flat"
                color="primary">
            {{ $t('forgetpassword.send') + resendInterval }}
          </VBtn>
        </template>
      </VTextField>

      <div class="mt-1 mb-4"
           layout="row">
        <VCheckbox self="left"
                   v-model="form.remember"
                   :label="$t('forgetpassword.remember-me')"
                   color="primary"
        />
        <span self="right">
                {{ $t('forgetpassword.already-have-account') }}
                <router-link self="right"
                             to="/login">
                  {{ $t('forgetpassword.login') }}
                </router-link>
              </span>
      </div>

      <VBtn :block="true"
            type="submit"
            @click="register"
            color="primary"
            :loading="loading"
      >
        {{ $t('forgetpassword.register') }}
      </VBtn>
    </VCol>
  </VForm>
</template>
<script setup>

import {rules} from "@/assets/script/rules";
</script>
