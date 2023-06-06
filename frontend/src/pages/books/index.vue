<template>
  <div class="h-100" layout="column top-center" v-if="loaded">
    <VCol cols="8">
      <VRow>
        <div style="height: 145px;"
             layout="row center-left">
          <VImg :src="pdf"
                class="h-100"
                max-width="110"
                width="110"
                :cover="true">
          </VImg>
          <div class="h-100 w-100 ml-10" layout="column center-left">
            <div class="font-weight-bold text-md-h4">
              {{ bookParam.originalFilename }}
            </div>
            <div>
              <span>{{ bookParam.tag }}</span>
              <VDivider :vertical="true" class="mx-1"></VDivider>
              <span class="book-status">
                {{ bookParam.original ? i18n.global.t('original') : i18n.global.t('not-original') }}
              </span>
            </div>
            <div class="mt-3">{{ bookParam.price }}{{ i18n.global.t('book-coins') }}</div>

          </div>
        </div>
      </VRow>
      <VRow>
        <VCol cols="6" class="mx-auto">
          <div layout="row center-spread">
            <VBtn color="primary"
                  @click="purchase"
                  class="w-33">
              {{ i18n.global.t('purchase-this-book') }}
            </VBtn>
            <VBtn color="primary"
                  @click="beginRead" class="w-33">
              {{ i18n.global.t('begin-reading') }}
            </VBtn>
          </div>
        </VCol>
      </VRow>
      <VRow>
        <VCol cols="8">
          <div class="elevation-2 pa-5">
            <VTabs v-model="tab" color="primary">
              <VTab value="1">{{ i18n.global.t('description') }}</VTab>
              <VTab value="2">{{ i18n.global.t('comment-area') }}</VTab>
            </VTabs>
            <VWindow v-model="tab" style="min-height: 344px">
              <VWindowItem value="1">
                {{ bookParam.description || i18n.global.t('no-description') }}
              </VWindowItem>
              <VWindowItem value="2">
                <VTextarea :label="$t('post-comment')"
                           class="mt-2"
                           variant="outlined"
                           color="primary"
                           :clearable="true"
                           :auto-grow="true"
                           v-model="reviewContent">
                  <template #append-inner>
                    <VBtn color="primary"
                          style="margin-top: 95px"
                          @click.stop="review"
                          :loading="loading">
                      {{ i18n.global.t('post') }}
                    </VBtn>
                  </template>
                </VTextarea>

                <div class="mt-2"
                     v-for="(content, index) in contents"
                     :key="index">
                  <div>
                    <div class="text-subtitle-1">{{ content.username }}</div>
                    <div class="w-100" style="white-space:normal; word-break:break-all;overflow:hidden;"
                    >{{ content.content }}
                    </div>
                    <VDivider></VDivider>
                  </div>
                </div>
              </VWindowItem>
            </VWindow>
          </div>
        </VCol>
        <VCol cols="4">
          <div class="h-100" layout="column top-center">
            <div class="pa-10 elevation-2"
                 style="min-height: 430px;"
                 layout="column top-center">
              <h1>{{ i18n.global.t('author-description') }}</h1>
              <VAvatar size="100"
                       class="mouse-pointer">
                <VImg :src="imgSrc">
                </VImg>
              </VAvatar>
              <div layout="row top-left" class="mt-10">
                <div class="w-33 ml-5">
                  <div>ID</div>
                  <div>{{ i18n.global.t('user-name') }}</div>
                  <div>{{ i18n.global.t('hot') }}</div>
                  <div>{{ i18n.global.t('articles') }}</div>
                </div>
                <div class="w-66" :style="{color: 'rgba(var(--v-theme-primary), 0.8)'}">
                  <div>{{ author.id }}</div>
                  <div>{{ author.username }}</div>
                  <div>{{ author.hot }}</div>
                  <div v-for="book in author.bookList" @click="showBookDetail(book)"
                       class="primary-color-hover mouse-pointer">
                    《{{ book.originalFilename }}》
                  </div>
                </div>
              </div>
            </div>
          </div>
        </VCol>
      </VRow>
    </VCol>
    <TopUp v-model="showTopup"></TopUp>
  </div>
</template>

<script setup>
import {useRouter, useRoute} from 'vue-router'
import {useMessage} from '@/store/modules/message'
import {onActivated, ref} from 'vue'
import {get, post} from '@/net'
import {useUser} from '@/store/modules/user'
import pdf from '@/assets/images/pdf.png'
import TopUp from '@/component/TopUp.vue'
import avatar from '@/assets/images/avatar.png'
import {i18n} from "@/i18n"

const route = useRoute()
const bookParam = ref({});


const user = useUser()
const router = useRouter()
const message = useMessage()


const tab = ref('1')
const contents = ref([])

const author = ref({})
const showTopup = ref(false)
const imgSrc = ref()

function showBookDetail(b) {
  loaded.value = false
  router.push({path: '/books', query: {book: b.bookId}})
    .then(() => router.go(0))
}

function beginRead() {
  post('/api/book/is-purchased', {bookId: bookParam.value.bookId, userId: parseInt(user.id)})
    .then(({data}) => {
      if (data) {
        localStorage.setItem('recentBookId', bookParam.value.bookId.toString())
        router.push('/recent')
      } else {
        message.error(i18n.global.t('please-purchase-this-book-first'))
      }
    })
}

const reviewContent = ref('')
const loading = ref(false)

function review() {
  loading.value = true
  if (!reviewContent.value) {
    return
  }
  post('/api/comments/insert',
    {bookId: bookParam.value.bookId, userId: parseInt(user.id), content: reviewContent.value})
    .then(() => {
      message.success(i18n.global.t('commit-success-and-hot-increase-1'))
      reviewContent.value = ''
    })
    .then(() => {
      post('/api/admin/update/hot', {id: parseInt(author.value.id), hot: parseInt(author.value.hot) + 1})
    })
    .then(() => {
      post('/api/admin/update/hot', {id: parseInt(user.id), hot: parseInt(user.hot.toString()) + 1})
    })
    .then(() => {
      post('/api/book/set/hot', {bookId: bookParam.value.bookId, hot: parseInt(bookParam.value.hot) + 1})
    })
    .then(reload)
    .finally(() => {
      loading.value = false
    })
}

function getComments() {
  post('/api/comments/find/bookId', {bookId: bookParam.value.bookId})
    .then(res1 => {
      //data:[{userId, content，id]
      const data1 = res1.data
      const tmp = []
      const works = []
      for (let i = 0; i < data1.length; i++) {
        works.push(get('/api/user/id', {id: data1[i].userId})
          .then(res2 => {
            const data2 = res2.data
            if (data2.username && data1[i].content) {
              tmp.push({username: data2.username, content: data1[i].content})
            }
          })
        )
      }
      Promise.all(works).then(() => {
        contents.value = tmp.reverse()
      })
    })
}

const loaded = ref(false)
onActivated(() => {
  reload()
})

function reload() {
  post('/api/book/search/bookId', {bookId: route.query.book})
    .then(({data}) => {
      bookParam.value = data
    })
    .then(() => {
      get('/api/user/id', {id: parseInt(bookParam.value.uploaderId)})
        .then(({data}) => author.value = data)
        .then(() => post('/api/book/search/uploaderId', {uploaderId: parseInt(bookParam.value.uploaderId)})
          .then(({data}) => author.value.bookList = data))
        .then(getAvatar)
        .then(getComments)
        .finally(() => {
          loaded.value = true
        })
    })
}

function getAvatar() {
  get(`/api/avatar/${author.value.email}`)
    .then(({data}) => {
      imgSrc.value = `data:image/png;base64,${data}`
    })
    .catch(() => {
      imgSrc.value = avatar
    })
}

function purchase() {
  const {price, bookId} = {...bookParam.value}
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
            message.success(i18n.global.t('purchase-success'))
            post('/api/book/set/hot', {bookId: bookParam.value.bookId, hot: parseInt(bookParam.value.hot) + 5})
            return
          }
          message.error(i18n.global.t('purchase-fail'))
        })
    })
}
</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
    requireLogin: true,
  }
}
</route>
