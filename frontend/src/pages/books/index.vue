<template>
  <div class="h-100" layout="column top-center">
    <VCol cols="8">
      <VRow>
        <div style="height: 145px;"
             layout="row center-left">
          <VImg src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
                class="h-100"
                max-width="110"
                width="110"
                :cover="true">
          </VImg>
          <div class="h-100 w-100" layout="column center-left">
            <div class="font-weight-bold text-grey-darken-3 text-md-h4">书名</div>
            <div>类别</div>
            <div layout="row center-justify"
                 self="right"
                 style="width: 210px;margin-right: 200px">
              <VBtn color="primary"
                    @click="beginRead()">
                开始阅读
              </VBtn>
              <VBtn variant="outlined"
                    @click="message.success('收藏成功！')">
                加入收藏
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
                简介
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
                          style="margin-top: 90px"
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
                <VImg ref="img"
                      src="avatar">
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
import {useRouter} from 'vue-router'
import {useMessage} from "@/store/modules/message"
import {ref} from "vue";

const router = useRouter()
const message = useMessage()

const tab = ref('1')

function beginRead() {
  localStorage.setItem('recentBookId', '9d05bb9e-6b56-485a-88dd-3f0d34f167a9.pdf')
  router.push('/recent')
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
