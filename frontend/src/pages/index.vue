<template>
  <div class="h-100 pb-0">
    <VRow>
      <VCol cols="2">
        <VRow>
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
        </VRow>
        <VRow>
          <VCol style="margin-top: 50px;">
            <div>
              <div>高级搜索</div>
              <VDivider></VDivider>
              <div class="mt-3">分类</div>
              <div>
                <VRadio model-value="1" label="编程读物" value="1" color="primary" density="compact"></VRadio>
                <VRadio model-value="2" label="经典名著" value="2" color="primary" density="compact"></VRadio>
              </div>
              <div class="mt-3">是否原创</div>
              <div>
                <VRadioGroup model-value="1" color="primary" density="compact">
                  <VRadio label="全部" value="1"></VRadio>
                  <VRadio label="原创" value="2"></VRadio>
                  <VRadio label="非原创" value="3"></VRadio>
                </VRadioGroup>
              </div>
              <div>价格区间</div>
              <div layout="row center-spread" class="text-subtitle-1">
                <VTextField density="default" variant="underlined"></VTextField>
                至
                <VTextField density="default" variant="underlined"></VTextField>
                书币
              </div>
            </div>
          </VCol>
        </VRow>
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

        <div>
          <VList>
            <VListItem v-for="(item, index) in [1, 2, 3, 4]"
                       :key="index">
              <VDivider v-if="index !== 0" class="mb-2"></VDivider>
              <div layout="row"
                   style="height: 145px;"
                   @click="()=>{}">
                <VImg src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
                      class="h-100"
                      max-width="110"
                      width="110"
                      :cover="true"></VImg>
                <VCard class="h-100 elevation-0">
                  <VCardTitle>
                    <span class="primary-color-hover mouse-pointer">书名</span>
                  </VCardTitle>
                  <VCardSubtitle layout="row">
                    <span class="primary-color-hover mouse-pointer" @click="$router.push({path: '/user/profile', query: { id: 1 }})">作者</span>
                    <VDivider :vertical="true" class="mx-1"></VDivider>
                    <span>分类</span>
                    <VDivider :vertical="true" class="mx-1"></VDivider>
                    <span class="book-status">是否原创</span>
                  </VCardSubtitle>
                  <VCardText>作品简介</VCardText>
                </VCard>

                <div class="h-100"
                     layout="column"
                     self="right"
                     style="width: 210px">
                  <div class="h-50">
                    <span class="float-end">
                      <VIcon icon="mdi-fire"></VIcon>
                      <span class="text-center">热度100</span>
                    </span>
                  </div>
                  <div class="h-50"
                       layout="row bottom-justify">
                    <VBtn color="primary"
                          @click="$router.push({path: '/books', query: { id: 1 }})">
                      书籍详情
                    </VBtn>
                    <VBtn variant="outlined"
                          @click="message.success('收藏成功！')">加入收藏
                    </VBtn>
                  </div>
                </div>
              </div>
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
import {ref} from "vue";

const message = useMessage()
const user = useUser()

const searchContent = ref('')
const searchMenu = ref([])

</script>

<style lang="scss" scoped>
//* {
//  border: red 1px solid;
//}
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
