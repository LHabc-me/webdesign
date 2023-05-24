<template>
  <div class="h-100">
    <VRow>
      <VCol cols="2">
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
      <VCol cols="8">
        <VCol cols="9"
              class="mx-auto">
          <VTextField color="primary"
                      variant="outlined"
                      density="comfortable"
                      :label="$t('search-book')"
                      append-inner-icon="mdi-magnify"
                      v-model="searchContent"
                      :clearable="true">
            <VMenu activator="parent">
              <VList>
                <VListItem v-for="i in searchMenu"
                           :key="i"
                           :value="i">
                  <VListItemTitle>{{ i }}</VListItemTitle>
                </VListItem>
              </VList>
            </VMenu>
          </VTextField>
        </VCol>
        <div style="min-height: 70.5vh">
          <VList>
            <VListItem v-for="i in 4" :key="i">
              <VCard max-height="200">
                <VImg src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
                      height="145"
                      width="120"
                      cover></VImg>
              </VCard>
            </VListItem>
          </VList>
        </div>
        <VPagination :length="5"></VPagination>
      </VCol>
      <VCol cols="2">
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

    </VRow>
  </div>
</template>

<script setup>
import {useMessage} from "@/store/modules/message";
import {useUser} from "@/store/modules/user";
import {useRouter} from 'vue-router'
import {ref, watch, watchEffect} from "vue";

const message = useMessage()
const user = useUser()
const router = useRouter()

const searchContent = ref('')
const searchMenu = ref([])

</script>

<style lang="scss" scoped>
* {
  border: red 1px solid;
}

</style>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
    //requireLogin: true,
    allowGuest: true,
  }
}
</route>
