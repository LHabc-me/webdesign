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
          <VCol style="margin-top: 50px">
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
              <div style="margin-top: -15px">价格区间</div>
              <div layout="row center-spread" class="text-subtitle-1"
                   style="margin-top: -20px">
                <VTextField density="default" variant="underlined" color="primary"></VTextField>
                至
                <VTextField density="default" variant="underlined" color="primary"></VTextField>
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
                      :clearable="true"
                      @click="search">
            <VMenu activator="parent">
              <VList>
                <VListItem v-for="i in searchMenu"
                           :key="i"
                           :value="i"
                           @click="searchContent = i">
                  <VListItemTitle>{{ i }}</VListItemTitle>
                </VListItem>
              </VList>
            </VMenu>
          </VTextField>
        </VCol>

        <div style="min-height: 654px">
          <VList>
            <VListItem v-for="(book, index) in bookListByPage"
                       :key="index">
              <VDivider v-if="index !== 0" class="mb-2"></VDivider>
              <div layout="row"
                   style="height: 145px"
                   @click="()=>{}">
                <VImg :src="pdf"
                      class="h-100"
                      max-width="110"
                      width="110"
                      :cover="true"></VImg>
                <VCard class="h-100 elevation-0">
                  <VCardTitle>
                    <span class="primary-color-hover mouse-pointer"
                          @click="showBookDetail(book)">
                      {{ book.originalFilename }}
                    </span>
                  </VCardTitle>
                  <VCardSubtitle layout="row">
                    <span class="primary-color-hover mouse-pointer" @click="$router.push({path: '/user/profile', query: { id: book.uploaderId }})">
                      {{ book.author }}
                    </span>
                    <VDivider :vertical="true" class="mx-1"></VDivider>
                    <span>{{ book.tag }}</span>
                    <VDivider :vertical="true" class="mx-1"></VDivider>
                    <span class="book-status">
                      {{ book.isOriginal ? '原创' : '非原创' }}
                    </span>
                    <VDivider :vertical="true" class="mx-1"></VDivider>
                    <span>{{ book.price }}书币</span>
                  </VCardSubtitle>
                  <VCardText>{{ description(book.description) }}</VCardText>
                </VCard>

                <div class="h-100"
                     layout="column"
                     self="right"
                     style="width: 210px">
                  <div class="h-50">
                    <span class="float-end">
                      <VIcon icon="mdi-fire" color="red"></VIcon>
                      <span class="text-center">热度{{ book.hot }}</span>
                    </span>
                  </div>
                  <div class="h-50"
                       layout="row bottom-justify">
                    <VBtn color="primary"
                          @click="showBookDetail(book)">
                      书籍详情
                    </VBtn>
                    <VBtn variant="outlined"
                          @click="message.success('购买成功！')">
                      购买本书
                    </VBtn>
                  </div>
                </div>
              </div>
            </VListItem>
          </VList>
        </div>
        <VPagination v-if="paginationLength !== 0"
                     :length="paginationLength"
                     v-model="page"></VPagination>
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
import {useMessage} from "@/store/modules/message"
import {useUser} from "@/store/modules/user"
import {computed, ref, watch} from "vue"
import {get, post} from "@/net"
import {useRouter} from "vue-router"
import pdf from '@/assets/images/pdf.png'

const message = useMessage()
const user = useUser()
const router = useRouter()

const page = ref(1)
const bookNumOfPage = ref(4)
const searchContent = ref('')

const searchMenu = computed(() => {
  return bookList.value.map(i => i.originalFilename)
})

const paginationLength = computed(() => {
  return Math.ceil(bookList.value.length / bookNumOfPage.value)
})

const bookList = ref([])
const bookListByPage = computed(() => {
  const start = (page.value - 1) * bookNumOfPage.value
  const end = start + bookNumOfPage.value
  return bookList.value.slice(start, end)
})

function description(val) {
  if (!val) {
    return '暂无简介'
  }
  if (val.length > 20) {
    return val.slice(0, 20) + '...'
  }
  return val
}


function search() {
  if (searchContent.value === '') {
    bookList.value = []
    return
  }
  post('/api/book/search/keywords', {keywords: searchContent.value}).then(
    ({data}) => {
      bookList.value = data
    }
  )
}

watch(searchContent, search)

function showBookDetail(b) {
  const book = window.btoa(encodeURIComponent(JSON.stringify(b)))
  router.push({path: '/books', query: {book}})
}
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
