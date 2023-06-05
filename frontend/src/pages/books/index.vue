<template>
  <div class="h-100" layout="column top-center">
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
                {{ bookParam.original ? '原创' : '非原创' }}
              </span>
            </div>
            <div class="mt-3">{{ bookParam.price }}书币</div>

          </div>
        </div>
      </VRow>
      <VRow>
        <VCol cols="6" class="mx-auto">
          <div layout="row center-spread">
            <VBtn color="primary"
                  @click="purchase"
                  class="w-33">
              购买本书
            </VBtn>
            <VBtn color="primary"
                  @click="beginRead" class="w-33">
              开始阅读
            </VBtn>
          </div>
        </VCol>
      </VRow>
      <VRow>
        <VCol cols="8">
          <div class="elevation-2 pa-5">
            <VTabs v-model="tab" color="primary">
              <VTab value="1">简介</VTab>
              <VTab value="2">评论区</VTab>
            </VTabs>
            <VWindow v-model="tab" style="min-height: 344px">
              <VWindowItem value="1">
                {{ bookParam.description ?? '暂无简介' }}
              </VWindowItem>
              <VWindowItem value="2">
                <VTextarea label="发表评论"
                           class="mt-2"
                           variant="outlined"
                           color="primary"
                           :clearable="true"
                           :auto-grow="true"
                           v-model="reviewContent">
                  <template #append-inner>
                    <VBtn color="primary"
                          style="margin-top: 95px"
                          @click.stop="review">
                      发表
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
              <h1>作者简介</h1>
              <VAvatar size="100"
                       class="mouse-pointer">
                <VImg :src="`avatar/${user.email}`">
                </VImg>
              </VAvatar>
              <div layout="row top-left" class="mt-10">
                <div class="w-33 ml-5">
                  <div>ID</div>
                  <div>用户名</div>
                  <div>热度</div>
                  <div>作品</div>
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
import {useMessage} from "@/store/modules/message"
import {onActivated, ref} from "vue";
import {get, post} from "@/net";
import {useUser} from "@/store/modules/user";
import pdf from '@/assets/images/pdf.png'
import TopUp from "@/component/TopUp.vue";

const route = useRoute()
const bookParam = ref({});


const user = useUser()
const router = useRouter()
const message = useMessage()


const tab = ref('1')
const contents = ref([])

const author = ref({})
const showTopup = ref(false)

function showBookDetail(b) {
  const book = window.btoa(encodeURIComponent(JSON.stringify(b)))
  router.push({path: '/books', query: {book}})
    .then(() => router.go(0))
}

function beginRead() {
  post('/api/book/is-purchased', {bookId: bookParam.value.bookId, userId: parseInt(user.id)})
    .then(({data}) => {
      if (data) {
        localStorage.setItem('recentBookId', bookParam.value.bookId.toString())
        router.push('/recent')
      } else {
        message.error('请先购买本书')
      }
    })
}

const reviewContent = ref('')

function review() {
  if (!reviewContent.value) {
    return
  }
  post('/api/comments/insert',
    {bookId: bookParam.value.bookId, userId: parseInt(user.id), content: reviewContent.value})
    .then(() => {
      message.success('评论成功')
      reviewContent.value = ''
      getComments()
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

onActivated(() => {
  reload()
})

function reload() {
  bookParam.value = JSON.parse(decodeURIComponent(window.atob(route.query.book.toString())))
  get('/api/user/id', {id: parseInt(bookParam.value.uploaderId)})
    .then(({data}) => author.value = data)

  post('/api/book/search/uploaderId', {uploaderId: parseInt(bookParam.value.uploaderId)})
    .then(({data}) => author.value.bookList = data)
  getComments()
}

function purchase() {
  const {price, bookId} = {...bookParam.value}
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
            message.success('购买成功')
            return
          }
          message.error('购买失败')
        })
    })
}
</script>

<style lang="scss" scoped>
//* {
//  border: red solid 1px
//}
</style>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
