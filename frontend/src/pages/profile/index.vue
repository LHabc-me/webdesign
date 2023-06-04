<template>
  <div class="h-100" layout="column center-center">
    <div class="elevation-4 pa-10"
         style="width: 336px; height: 500px;"
         layout="column cneter-center">
      <h1>个人资料</h1>
      <VAvatar size="100"
               class="mouse-pointer"
               @click="uploadAvatarInput.click()">
        <VImg ref="img">
        </VImg>
      </VAvatar>
      <div class="w-100 mt-10" layout="row center-center">
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
            <div>{{ user.id }}</div>
            <div>{{ user.email }}</div>
            <div>{{ user.name }} <span v-if="false" class="link-color mouse-pointer">修改</span></div>
            <div>{{ user.coins }} <span class="link-color mouse-pointer" @click="topup">充值</span></div>
            <div>{{ user.hot }}</div>
            <div>暂无</div>
          </div>
        </div>
      </div>
    </div>
    <TopUp v-model="showTopup"></TopUp>
  </div>
</template>
<script setup>
import {ref} from 'vue'
import {post} from '@/net'
import TopUp from '@/component/TopUp.vue'
import {useUser} from '@/store/modules/user'

const uploadAvatarInput = ref(null)
const user = useUser()

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

const showTopup = ref(false)

function topup() {
  showTopup.value = true
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
