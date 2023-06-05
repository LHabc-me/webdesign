<template>
  <div class="h-100" layout="column center-center">
    <div class="pa-10"
         layout="column cneter-center">
      <VAvatar size="200">
        <VImg :src="imgSrc">
        </VImg>
      </VAvatar>
      <div class="w-100 mt-10" layout="row center-center">
        <div layout="row top-center" class="text-h5">
          <div style="width: 520px;" layout="column top-right">
            <div>
              <div>ID</div>
              <div>电子邮箱</div>
              <div>用户名</div>
              <div>热度</div>
              <div>作品</div>
            </div>
          </div>
          <div style="width: 50px"></div>
          <div :style="{color: 'rgba(var(--v-theme-primary), 0.8)'}"
               style="width: 600px">
            <div>{{ user.id }}</div>
            <div>{{ user.email }}</div>
            <div>{{ user.name }}</div>
            <div>{{ user.hot }}</div>
            <div v-for="book in bookList" @click="showBookDetail(book)"
                 class="primary-color-hover mouse-pointer">
              《{{ book.originalFilename }}》
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import {onActivated, ref} from "vue";
import {get, post} from "@/net";
import avatar from "@/assets/images/avatar.png";
import {useRoute} from "vue-router";
import {useMessage} from "@/store/modules/message";
import {useUser} from "@/store/modules/user";
import {useRouter} from "vue-router";

const imgSrc = ref('')
const bookList = ref([])
const route = useRoute()
const router = useRouter()

let user = ref({})
onActivated(() => {
  get('/api/user/id', {id: parseInt(route.query.id.toString())})
    .then(({data}) => {
      user.value = data
      getAvatar()
    })
  post('/api/book/search/uploaderId', {uploaderId: parseInt(route.query.id.toString())})
    .then(({data}) => bookList.value = data)
})

function getAvatar() {
  get(`/api/avatar/${user.value.email}`)
    .then(({data}) => {
      imgSrc.value = `data:image/png;base64,${data}`
    })
    .catch(() => {
      imgSrc.value = avatar
    })
}

function showBookDetail(b) {
  const book = window.btoa(encodeURIComponent(JSON.stringify(b)))
  router.push({path: '/books', query: {book}})
}
</script>


<style scoped lang="scss">
//* {
//  border: red solid 1px;
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
