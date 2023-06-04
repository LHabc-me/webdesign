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
            <div class="font-weight-bold text-grey-darken-3 text-md-h4">
              {{ bookParam.originalFilename }}
            </div>
            <div>
              <span>{{ bookParam.tag }}</span>
              <VDivider :vertical="true" class="mx-1"></VDivider>
              <span class="book-status">
                {{ bookParam.isOriginal ? '原创' : '非原创' }}
              </span>
            </div>
            <div class="mt-3">{{ bookParam.price }}书币</div>
            <div self="right"
                 style="margin-right: 100px">
              <VBtn color="primary"
                    @click="beginRead();message.success('购买成功！')">
                购买本书
              </VBtn>
            </div>
          </div>
        </div>
      </VRow>

      <VRow>
        <VCol cols="8">
          <div class="elevation-2 pa-5">
            <VTabs v-model="tab">
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
                           :auto-grow="true">
                  <template #append-inner>
                    <VBtn color="primary"
                          style="margin-top: 95px"
                          @click.stop="()=>{}">
                      发表
                    </VBtn>
                  </template>
                </VTextarea>
                <div class="mt-2"
                     layout="column"
                     v-for="(content, index) in contents"
                     :key="index">
                  <div layout="row center-justify">
                    <div class="text-subtitle-1">{{ content.username }}</div>
                  </div>
                  <div class="text-subtitle-1">{{ content.content }}</div>
                  <VDivider></VDivider>
                </div>
              </VWindowItem>
            </VWindow>
          </div>
        </VCol>
        <VCol cols="4">
          <div class="h-100" layout="column top-center">
            <div class="pa-10 elevation-2"
                 style="height: 430px;"
                 layout="column cneter-center">
              <h1>作者简介</h1>
              <VAvatar size="100"
                       class="mouse-pointer">
                <VImg ref="img">
                </VImg>
              </VAvatar>
              <div layout="row center-center" class="mt-10">
                <div class="w-33 ml-5">
                  <div>ID</div>
                  <div>用户名</div>
                  <div>热度</div>
                  <div>作品</div>
                </div>
                <div class="w-33 text-light-blue">
                  <div>123456</div>
                  <div>LHabc</div>
                  <div>33</div>
                  <div>暂无</div>
                </div>
              </div>
            </div>
          </div>
        </VCol>
      </VRow>
    </VCol>
  </div>
</template>

<script setup>
import {useRouter, useRoute} from 'vue-router'
import {useMessage} from "@/store/modules/message"
import {onMounted, ref} from "vue";
import {get, post} from "@/net";
import {useUser} from "@/store/modules/user";
import pdf from '@/assets/images/pdf.png'

const route = useRoute()
let bookParam = JSON.parse(decodeURIComponent(window.atob(route.query.book.toString())))


const user = useUser()
const router = useRouter()
const message = useMessage()


const tab = ref('1')
const contents = ref([])

function beginRead() {
  localStorage.setItem('recentBookId', bookParam.bookId.toString())
  router.push('/recent')
}


function review(content) {
  if (!content) {
    return
  }
  post('/api/comments/insert', {bookId: bookParam.bookId, userId: user.id, content})

}

onMounted(() => {
  post('/api/comments/find/uuid', {bookId: bookParam.bookId})
    .then(({data1}) => {
      //data:[{userId, content]
      for (let i = 0; i < data1.length; i++) {
        get('/api/user/id', {id: data1[i].userId})
          .then(({data2}) => {
            contents.value.push({username: data2.username, content: data1.content})
          })
      }
    })
})
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
