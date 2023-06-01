<template>
  <div class="h-100" layout="column center-center">
    <div class="elevation-4 pa-10"
         style="width: 336px; height: 500px;"
         layout="column cneter-center">
      <h1>个人资料</h1>
      <VAvatar size="100"
               class="mouse-pointer"
               @click="uploadAvatarInput.click()">
        <VImg ref="img"
              src="avatar">
        </VImg>
      </VAvatar>
      <div class="w-100" layout="row center-center">
        <input ref="uploadAvatarInput"
               type="file"
               class="d-none"
               accept="image/*"
               @change="onuploadAvatarInputChange"/>
        <div layout="row center-center">
          <div class="w-33">
            <div>ID</div>
            <div>电子邮箱</div>
            <div>用户名</div>
            <div>书币余额</div>
            <div>热度</div>
            <div>作品</div>
          </div>
          <div class="w-66 text-light-blue">
            <div>123456</div>
            <div>lhabc.me@outlook.com</div>
            <div>LHabc <span class="link-color mouse-pointer">修改</span></div>
            <div>100 <span class="link-color mouse-pointer">充值</span></div>
            <div>33</div>
            <div>暂无</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {ref} from 'vue'
import {post} from '@/net'

const uploadAvatarInput = ref(null)

function onuploadAvatarInputChange(event) {
  const form = new FormData()
  form.append('file', event.target.files[0])
  post('api/upload/user/avatar', form, {}, {'Content-Type': 'multipart/form-data'})
    .then(({data}) => {
      console.log(data)
    })
    .catch((error) => {
      console.log(error)
    })
}
</script>
<style scoped lang="scss">

</style>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
