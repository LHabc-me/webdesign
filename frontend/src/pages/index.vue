<template>
  <v-btn icon="mdi-arrow-left"
         @click="click">
    1234
  </v-btn>
  <br/>
  {{ $t('welcome') }}
</template>

<script setup>
import {useMessage} from "@/store/modules/message";
import {useRouter} from 'vue-router'

const message = useMessage()
const router = useRouter()

function click() {
  router.push('login')
}

</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    requiresAuth: true
  }
}
</route>
