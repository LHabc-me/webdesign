<template>
  <div class="h-100">
    <VRow>
      <VCol cols="4" class="text-h3 text-center">左侧区域</VCol>
      <VCol cols="4">
        <VCarousel :cycle="true"
                   interval="3000"
                   :show-arrows="false"
                   :hide-delimiters="true"
                   height="300">
          <VCarouselItem
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            :cover="true"
          ></VCarouselItem>
          <VCarouselItem
            src="https://cdn.vuetifyjs.com/images/cards/hotel.jpg"
            :cover="true"
          ></VCarouselItem>
          <VCarouselItem
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            :cover="true"
          ></VCarouselItem>
        </VCarousel>
      </VCol>
      <VCol cols="4" class="text-h3 text-center">右侧区域</VCol>
    </VRow>
    <VRow class="h-50">
      <VCol cols="12" class="text-h3" layout="row center-center">下侧区域</VCol>
    </VRow>
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
  message.success('success')
  message.error('error')
  message.warning('warning')
  message.info('info')
}

</script>

<style scoped>
* {
  border: red 1px solid;
}
</style>

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
