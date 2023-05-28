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
          <div class="h-100">
            <div class="font-weight-bold text-grey-darken-3 text-md-h4">书名</div>
            <div>类别</div>
            <VBtn color="primary"
                  @click="$router.push('/recent')">
              开始阅读
            </VBtn>
            <VBtn variant="outlined"
                  @click="message.success('收藏成功！')">
              加入收藏
            </VBtn>
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
                评论区
              </VWindowItem>
            </VWindow>
          </div>
        </VCol>
        <VCol cols="4">
          <div class="h-100" layout="column center-center">
            <div class="pa-10"
                 style="height: 500px;"
                 layout="column cneter-center">
              <h1>作者简介</h1>
              <VAvatar size="100"
                       class="mouse-pointer"
                       @click="uploadAvatarInput.click()">
                <VImg ref="img"
                      src="avatar">
                </VImg>
              </VAvatar>
              <div>
                <input ref="uploadAvatarInput"
                       type="file"
                       class="d-none"
                       accept="image/*"
                       @change="onuploadAvatarInputChange"/>
                <div>id：123456</div>
                <div>用户名：LHabc</div>
                <div>书龄：20天</div>
                <div>作品：暂无</div>
              </div>
            </div>
          </div>
        </VCol>
      </VRow>
    </VCol>
  </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import {useMessage} from "@/store/modules/message"
import {ref} from "vue";

const route = useRoute()
const message = useMessage()

const tab = ref('1')
</script>

<style lang="scss" scoped>
* {
  border: red solid 1px
}
</style>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
