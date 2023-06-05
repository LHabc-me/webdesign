<template>
  <div class="h-100" layout="column center-center">
    <div class="elevation-4 pa-10"
         style="width: 336px; height: 500px;"
         layout="column cneter-center">
      <h1>个人资料</h1>
      <VAvatar size="100"
               class="mouse-pointer"
               @click="uploadAvatarInput.click()">
        <VImg :src="imgSrc">
        </VImg>
      </VAvatar>
      <div class="w-100 mt-10" layout="row center-center">
        <input ref="uploadAvatarInput"
               type="file"
               class="d-none"
               accept="image/*"
               @change="onuploadAvatarInputChange"/>
        <div layout="row top-left">
          <div class="w-33">
            <div>ID</div>
            <div>电子邮箱</div>
            <div>用户名</div>
            <div>书币余额</div>
            <div>热度</div>
            <div>作品</div>
          </div>
          <div class="w-66" :style="{color: 'rgba(var(--v-theme-primary), 0.8)'}">
            <div>{{ user.id }}</div>
            <div>{{ user.email }}</div>
            <div>{{ user.name }} <span v-if="false" class="link-color mouse-pointer">修改</span></div>
            <div>{{ user.coins }} <span class="link-color mouse-pointer" @click="topup">充值</span></div>
            <div>{{ user.hot }}</div>
            <div v-for="book in bookList" @click="showBookDetail(book)"
                 class="primary-color-hover mouse-pointer">
              《{{ book.originalFilename }}》
            </div>
          </div>
        </div>
      </div>
    </div>
    <TopUp v-model="showTopup"></TopUp>
  </div>
</template>
<script setup>
import {onActivated, ref} from 'vue'
import {get, post} from '@/net'
import TopUp from '@/component/TopUp.vue'
import {useUser} from '@/store/modules/user'
import {useMessage} from "@/store/modules/message";
import {useRouter} from "vue-router";

const uploadAvatarInput = ref(null)
const user = useUser()
const router = useRouter()
const bookList = ref([])

function onuploadAvatarInputChange(event) {
  const form = new FormData()
  form.append('file', event.target.files[0])
  post('api/upload/user/avatar', form, {}, {'Content-Type': 'multipart/form-data'})
    .then(({data}) => {
      getAvatar()
    })
    .catch((error) => {
      console.log(error)
    })
}

const showTopup = ref(false)

function topup() {
  showTopup.value = true
}

const imgSrc = ref('')
onActivated(() => {
  post('/api/book/search/uploaderId', {uploaderId: parseInt(user.id)})
    .then(({data}) => bookList.value = data)
  getAvatar()
})

function getAvatar() {
  get(`/api/avatar/${user.email}`)
    .then(({data}) => {
      imgSrc.value = `data:image/png;base64,${data}`
    })
}

function showBookDetail(b) {
  const message = useMessage()
  const user = useUser()
  if (!user.isLogin) {
    message.info('请先登录')
    return
  }
  const book = window.btoa(encodeURIComponent(JSON.stringify(b)))
  router.push({path: '/books', query: {book}})
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
