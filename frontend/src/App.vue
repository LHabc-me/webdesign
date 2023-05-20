<template>
  <VThemeProvider :theme="theme.name">
    <router-view>
    </router-view>
  </VThemeProvider>
  <v-snackbar location="top"
              v-model="message.visible"
              :color="message.color"
              :timeout="message.timeout"
              height="20px"
              min-width="150px">
    <v-container class="d-flex justify-center">
      {{ message.msg }}
    </v-container>
    <template v-slot:actions>
      <v-btn
        v-if="message.showClose"
        color="red"
        variant="text"
        @click="message.visible = false"
        :color="message.color"
      >
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script setup>
import {useMessage} from "@/store/modules/message"
import {useTheme} from "@/store/modules/theme"

const message = useMessage()
const theme = useTheme()
</script>

<style lang="less">
</style>
