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
          <VCol style="margin-top: 20px">
            <div>
              <div>高级搜索</div>
              <VDivider></VDivider>
              <div class="mt-3">排序</div>
              <div layout="row center-center">
                <VSelect color="primary"
                         :items="sortBy.name"
                         variant="underlined"
                         density="compact"
                         v-model="sortBy.model"
                         @update:model-value="sortUpdate"
                         class="w-66"
                ></VSelect>
                <VRadioGroup v-model="sortBy.asc" color="primary" density="compact" class="w-33">
                  <VRadio label="升序" :value="true"></VRadio>
                  <VRadio label="降序" :value="false"></VRadio>
                </VRadioGroup>
              </div>
              <VDivider></VDivider>
              <div class="mt-3">分类</div>
              <div>
                <div class="py-1 pe-0">
                  <VChip v-for="tag in tags"
                         :key="tag"
                         :closable="true"
                         @click:close="onRemoveTag(tag)"
                         color="primary"
                         class="mb-2">
                    {{ tag }}
                  </VChip>
                  <VTextField label="添加分类" v-model="addTag" @keyup.enter="onAddTag"
                              color="primary" density="compact" variant="underlined"></VTextField>
                </div>
              </div>
              <div>是否原创</div>
              <div>
                <VRadioGroup v-model="isOriginal" color="primary" density="compact">
                  <VRadio label="全部" :value="null"></VRadio>
                  <VRadio label="原创" :value="true"></VRadio>
                  <VRadio label="非原创" :value="false"></VRadio>
                </VRadioGroup>
              </div>
              <div>价格区间</div>
              <div layout="row center-spread" class="text-subtitle-1"
                   style="margin-top: -20px">
                <VTextField density="default" variant="underlined" color="primary" v-model="priceFrom" class="centered-input"></VTextField>
                至
                <VTextField density="default" variant="underlined" color="primary" v-model="priceTo" class="centered-input"></VTextField>
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
        <div v-if="paginationLength !== 0">
          <div style="min-height: 654px">
            <VList>
              <VListItem v-for="(book, index) in bookListByPage"
                         :key="index">
                <VDivider v-if="index !== 0" class="mb-2"></VDivider>
                <div layout="row"
                     style="height: 145px">
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
                      {{ book.original ? '原创' : '非原创' }}
                    </span>
                      <VDivider :vertical="true" class="mx-1"></VDivider>
                      <span>{{ book.price }}书币</span>
                    </VCardSubtitle>
                    <VCardText>{{ description(book.description) }}</VCardText>
                  </VCard>

                  <div class="h-100"
                       layout="column"
                       self="right"
                       style="min-width: 210px;width: 210px">
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
                            @click="purchase(book)">
                        购买本书
                      </VBtn>
                    </div>
                  </div>
                </div>
              </VListItem>
            </VList>
          </div>
          <VPagination :length="paginationLength"
                       v-model="page"></VPagination>
        </div>
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
    <TopUp v-model="showTopup"></TopUp>
  </div>
</template>

<script setup>
import {useMessage} from "@/store/modules/message"
import {useUser} from "@/store/modules/user"
import {computed, onActivated, ref, watch} from "vue"
import {get, post} from "@/net"
import {useRouter} from "vue-router"
import pdf from '@/assets/images/pdf.png'
import TopUp from "@/component/TopUp.vue"
import {useRoute} from "vue-router"

const message = useMessage()
const user = useUser()
const router = useRouter()
const route = useRoute()


const page = ref(parseInt(route.query.page?.toString() ?? 1))
const bookNumOfPage = ref(4)
const searchContent = ref(`${route.query.q ?? ''}`)
const tags = ref(route.query.tags?.toString().split(',') ?? [])
const addTag = ref()

function original() {
  if (route.query.original === undefined || route.query.original === null) {
    return null
  }
  return route.query.original === 'true'
}

const isOriginal = ref(original())
const priceFrom = ref(isNaN(route.query.priceFrom) ? null : route.query.priceFrom)
const priceTo = ref(isNaN(route.query.priceTo) ? null : route.query.priceTo)
const sortBy = ref({
  name: ['书名', '作者', '价格', '热度'],
  value: ['originalFilename', 'author', 'price', 'hot'],
  model: '书名',
  resultValue: route.query.sort ?? 'originalFilename',
  //是否升序
  asc: (() => {
    return route.query.asc?.toString() !== 'false'
  })()
})

function sortUpdate() {
  sortBy.value.resultValue = sortBy.value.value[sortBy.value.name.indexOf(sortBy.value.model)]
  updateQuery()
}


watch([searchContent, tags, isOriginal, priceFrom, priceTo, sortBy], () => {
  search()
}, {deep: true})

watch(page, () => {
  updateQuery()
})

function updateQuery() {
  const query = {
    page: page.value,
    q: searchContent.value,
    sort: sortBy.value.resultValue,
    tags: tags.value.join(','),
    asc: sortBy.value.asc,
    original: isOriginal.value,
    priceFrom: priceFrom.value,
    priceTo: priceTo.value
  }
  router.push({path: `${route.path}`, query})
}

function onAddTag() {
  if (!addTag.value || tags.value.includes(addTag.value)) {
    return
  }
  if (addTag.value.includes(',')) {
    message.error('标签不能包含逗号')
    return
  }
  tags.value.push(addTag.value)
  addTag.value = ''
}

function onRemoveTag(tag) {
  tags.value.splice(tags.value.indexOf(tag), 1)
}

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
  if (val.length > 120) {
    return val.slice(0, 120) + '...'
  }
  return val
}

onActivated(() => {
  search()
})

function search() {
  if (searchContent.value === '') {
    bookList.value = []
    return
  }
  updateQuery()

  const p = page.value

  post('/api/book/search/keywords', {keywords: searchContent.value}).then(
    ({data}) => {
      // 根据高级搜索过滤
      const tmp = []
      data.forEach(book => {
        if (tags.value.length !== 0 && !tags.value.includes(book.tag)) {
          return
        }
        if (isOriginal.value !== null && book.original !== isOriginal.value) {
          return
        }
        const floor = priceFrom.value ?? 0
        const ceil = priceTo.value ?? Number.MAX_SAFE_INTEGER
        if (!(book.price >= floor && book.price <= ceil)) {
          return
        }
        tmp.push(book)
      })
      tmp.sort((a, b) => {
        let result
        if (sortBy.value.resultValue === 'originalFilename') {
          result = a.originalFilename.localeCompare(b.originalFilename)
        } else if (sortBy.value.resultValue === 'author') {
          result = a.author.localeCompare(b.author)
        } else if (sortBy.value.resultValue === 'price') {
          result = a.price - b.price
        } else if (sortBy.value.resultValue === 'hot') {
          result = a.hot - b.hot
        }
        return sortBy.value.asc ? result : -result
      })
      if (page.value !== p) {
        page.value = 1
      }
      bookList.value = tmp
    }
  )
}


const showTopup = ref(false)

function purchase(book) {
  if (!user.isLogin) {
    message.info('请先登录')
    return
  }
  const {price, bookId, hot} = book
  post('/api/book/is-purchased', {bookId, userId: parseInt(user.id)})
    .then(({data}) => {
      if (data) {
        message.error('您已购买过该书籍')
        return
      }

      if (user.coins < price) {
        message.error('您的书币不足，请充值')
        showTopup.value = true
        return
      }

      post('/api/book/purchase', {bookId, userId: user.id, coins: user.coins - price})
        .then(({data}) => {
          if (data) {
            post('/api/book/set/hot', {bookId, hot: parseInt(hot) + 5})
              .then(search)
            message.success('购买成功')
            return
          }
          message.error('购买失败')
        })
    })
}

function showBookDetail(b) {
  if (!user.isLogin) {
    message.info('请先登录')
    return
  }
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
