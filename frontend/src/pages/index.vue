<template>
  <div :class="{'h-100': true, 'pb-0': i18n.global.locale.value === 'zh'}">
    <VRow>
      <VCol cols="2">
        <VRow>
          <div @click="goURL('https://lhabc-me.github.io')" class="w-100">
            <VCarousel :cycle="true"
                       interval="3000"
                       :show-arrows="false"
                       :hide-delimiters="true"
                       height="300"
                       class="w-100">
              <VCarouselItem v-for="(url, index) in imgLeft"
                             :src="url"
                             :key="index"
                             class="mouse-pointer">
              </VCarouselItem>
            </VCarousel>
          </div>
        </VRow>
        <VRow>
          <VCol style="margin-top: 20px">
            <div>
              <div>{{ i18n.global.t('advanced-search') }}</div>
              <VDivider></VDivider>
              <div class="mt-3">{{ i18n.global.t('sort') }}</div>
              <div layout="row center-center">
                <VSelect color="primary"
                         :items="selectItems"
                         variant="underlined"
                         density="compact"
                         @update:model-value="sortUpdate"
                         class="w-66"
                         :model-value="selectModelValue"
                         return-object>
                </VSelect>
                <VRadioGroup v-model="sortBy.asc" color="primary" density="compact" class="w-33">
                  <VRadio :label="$t('ascending')" :value="true"></VRadio>
                  <VRadio :label="$t('descending')" :value="false"></VRadio>
                </VRadioGroup>
              </div>
              <VDivider></VDivider>
              <div class="mt-3">{{ i18n.global.t('type') }}</div>
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
                  <VTextField :label="$t('add-tag')" v-model="addTag" @keyup.enter="onAddTag"
                              color="primary" density="compact" variant="underlined"></VTextField>
                </div>
              </div>
              <div>{{ i18n.global.t('is-original') }}</div>
              <div>
                <VRadioGroup v-model="isOriginal" color="primary" density="compact">
                  <VRadio :label="$t('all')" :value="null"></VRadio>
                  <VRadio :label="$t('original')" :value="true"></VRadio>
                  <VRadio :label="$t('not-original')" :value="false"></VRadio>
                </VRadioGroup>
              </div>
              <div>{{ i18n.global.t('price-range') }}</div>
              <div layout="row center-spread" class="text-subtitle-1"
                   style="margin-top: -20px">
                <VTextField density="default" variant="underlined" color="primary" v-model="priceFrom" class="centered-input"></VTextField>
                {{ i18n.global.t('to') }}
                <VTextField density="default" variant="underlined" color="primary" v-model="priceTo" class="centered-input"></VTextField>
                {{ i18n.global.t('book-coins') }}
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
                      {{ book.original ? i18n.global.t('original') : i18n.global.t('not-original') }}
                    </span>
                      <VDivider :vertical="true" class="mx-1"></VDivider>
                      <span>{{ book.price }}{{ i18n.global.t('book-coins') }}</span>
                    </VCardSubtitle>
                    <VCardText>{{ description(book.description) }}</VCardText>
                  </VCard>

                  <div class="h-100 w-33"
                       layout="column"
                       self="right"
                       style="width: 210px">
                    <div class="h-50">
                    <span class="float-end">
                      <VIcon icon="mdi-fire" color="red"></VIcon>
                      <span class="text-center">{{ i18n.global.t('hot') }}{{ book.hot }}</span>
                    </span>
                    </div>
                    <div class="h-50"
                         layout="row bottom-right">
                      <VBtn color="primary"
                            @click="showBookDetail(book)"
                            class="mr-5">
                        {{ i18n.global.t('book-detail') }}
                      </VBtn>
                      <VBtn variant="outlined"
                            @click="purchase(book)">
                        {{ i18n.global.t('purchase-this-book') }}
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
        <div v-if="searching"></div>
        <div v-else-if="searchContent && bookList.length === 0"
             layout="row center-center"
             class="h-50">
          <div style="color: rgb(var(--v-theme-primary))"
               class="text-h4 font-weight-bold">
            {{ i18n.global.t('no-matching-books-found') }}(◞‸◟)
          </div>
        </div>
        <div v-else></div>
      </VCol>
      <VCol cols="2">
        <div @click="goURL('https://delete-cloud.github.io')" class="w-100"
             style="height: 700px">
          <VImg class="h-100" src="images/handwriting.gif"
                :class="{'dark': theme.name === 'dark'}">
          </VImg>
        </div>
      </VCol>
    </VRow>
    <TopUp v-model="showTopup"></TopUp>
  </div>
</template>

<script setup>
import {useMessage} from "@/store/modules/message"
import {useUser} from "@/store/modules/user"
import {computed, onActivated, ref, watch} from "vue"
import {useTheme} from "@/store/modules/theme"
import {post} from "@/net"
import {useRouter} from "vue-router"
import pdf from '@/assets/images/pdf.png'
import TopUp from "@/component/TopUp.vue"
import {useRoute} from "vue-router"
import {i18n} from "@/i18n";

const message = useMessage()
const user = useUser()
const router = useRouter()
const route = useRoute()
const theme = useTheme()


const imgLeft = ref([
  'images/book1.jpg',
  'images/book2.jpg',
  'images/book3.jpg',
  'images/book4.jpg'])

const page = ref(parseInt(route.query.page?.toString() ?? 1))
const bookNumOfPage = ref(4)
const searchContent = ref(`${route.query.q ?? ''}`)
const tags = ref((() => {
  const result = route.query.tags?.toString().split(',')
  if (!result || (result.length === 1 && result[0] === '')) {
    return []
  }
  return result
})())
const addTag = ref()

const isOriginal = ref((() => {
  if (route.query.original === undefined || route.query.original === null) {
    return null
  }
  return route.query.original === 'true'
})())
const priceFrom = ref(isNaN(route.query.priceFrom) ? null : route.query.priceFrom)
const priceTo = ref(isNaN(route.query.priceTo) ? null : route.query.priceTo)
const sortBy = ref({
  value: route.query.sort ?? 'originalFilename',
  //是否升序
  asc: (() => {
    return route.query.asc?.toString() !== 'false'
  })()
})


const selectItems = computed(() => {
  return [
    {title: i18n.global.t('book-name'), value: 'originalFilename'},
    {title: i18n.global.t('author'), value: 'author'},
    {title: i18n.global.t('price'), value: 'price'},
    {title: i18n.global.t('hot'), value: 'hot'}
  ]
})

const selectModelValue = computed(() => {
  return selectItems.value.find(item => item.value === sortBy.value.value)
})

function sortUpdate(obj) {
  sortBy.value.value = obj.value
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
    sort: sortBy.value.value,
    tags: tags.value.join(','),
    asc: sortBy.value.asc,
    original: isOriginal.value,
    priceFrom: priceFrom.value,
    priceTo: priceTo.value
  }
  return router.push({path: `${route.path}`, query})
}

function onAddTag() {
  if (!addTag.value || tags.value.includes(addTag.value)) {
    return
  }
  if (addTag.value.includes(',')) {
    message.error(i18n.global.t('tags-cant-contain-comma'))
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
    return i18n.global.t('no-description')
  }
  if (val.length > 120) {
    return val.slice(0, 120) + '...'
  }
  return val
}

onActivated(() => {
  search()
})

const searching = ref(false)

function search() {
  updateQuery()

  const p = page.value
  searching.value = true

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
        if (sortBy.value.value === 'originalFilename') {
          result = a.originalFilename.localeCompare(b.originalFilename)
        } else if (sortBy.value.value === 'author') {
          result = a.author.localeCompare(b.author)
        } else if (sortBy.value.value === 'price') {
          result = a.price - b.price
        } else if (sortBy.value.value === 'hot') {
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
    .finally(() => {
      searching.value = false
    })
}


const showTopup = ref(false)

function purchase(book) {
  if (!user.isLogin) {
    message.info(i18n.global.t('login-first'))
    return
  }
  const {price, bookId, hot} = book
  post('/api/book/is-purchased', {bookId, userId: parseInt(user.id)})
    .then(({data}) => {
      if (data) {
        message.error(i18n.global.t('you-have-already-purchased-the-book'))
        return
      }

      if (user.coins < price) {
        message.error(i18n.global.t('your-book-coins-are-not-enough-please-top-up'))
        showTopup.value = true
        return
      }

      post('/api/book/purchase', {bookId, userId: user.id, coins: user.coins - price})
        .then(({data}) => {
          if (data) {
            post('/api/book/set/hot', {bookId, hot: parseInt(hot) + 5})
              .then(search)
            message.success(i18n.global.t('purchase-success'))
            return
          }
          message.error(i18n.global.t('purchase-fail'))
        })
    })
}

function showBookDetail(b) {
  if (!user.isLogin) {
    message.info(i18n.global.t('login-first'))
    return
  }
  router.push({path: '/books', query: {book: b.bookId}})
}

function goURL(url) {
  window.open(url)
}

</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
    requireLogin: true,
    allowGuest: true,
  }
}
</route>
