<template>
  <div class="h-100" layout="column top-center">
    <VCol cols="8">
      <VRow>
        <div style="height: 145px;"
             layout="row center-left">
          <VImg src="https://store.sop.org/wp-content/uploads/2019/01/PDF_ICON.png"
                class="h-100"
                max-width="110"
                width="110"
                :cover="true">
          </VImg>
          <div class="h-100 w-100 ml-10" layout="column center-left">
            <div class="font-weight-bold text-grey-darken-3 text-md-h4">
              {{ $route.query.book?.name }}
            </div>
            <div>{{ $route.query.book?.originalFilename }}</div>
            <div class="mt-3">{{ $route.query.book?.price }}书币</div>
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
          <div>
            <VTabs v-model="tab">
              <VTab value="1">简介</VTab>
              <VTab value="2">评论区</VTab>
            </VTabs>
            <VWindow v-model="tab">
              <VWindowItem value="1">
                {{ $route.query.book?.description ?? '暂无简介' }}
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
                     v-for="(item, index) in [1, 2, 3, 4]"
                     :key="index">
                  <div layout="row center-justify">
                    <div class="text-subtitle-1">用户名</div>
                  </div>
                  <div class="text-subtitle-1">评论内容</div>
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

const router = useRouter()
const route = useRoute()
const message = useMessage()

const tab = ref('1')


function beginRead() {
  localStorage.setItem('recentBookId', route.query.book.bookId)
  router.push('/recent')
}

onMounted(() => {
  const img = ref()
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
