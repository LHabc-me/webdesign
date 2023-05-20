<template>
  <div>
    <v-btn icon="mdi-arrow-left"
           @click="click">
      1234
    </v-btn>
    <br/>
    {{ $t('welcome') }}
  </div>
</template>

<script setup>
import {useMessage} from "@/store/modules/message";
import {useUser} from "@/store/modules/user";
import {useRouter} from 'vue-router'

const message = useMessage()
const user = useUser()
const router = useRouter()

function click() {
  router.push('/login')
}

</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'user',
    requireLogin: true,
    allowGuest: true,
  }
}
</route>
