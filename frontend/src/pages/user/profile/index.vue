<template>
  <div class="h-100 profile" layout="column center-center" v-if="loaded"
       :style="{
       'background-image': `url('images/profile-bg-${theme.name}.png')`,
       'background-size': 'cover'
  }">
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
              <div>{{ i18n.global.t('email') }}</div>
              <div>{{ i18n.global.t('user-name') }}</div>
              <div>{{ i18n.global.t('hot') }}</div>
              <div>{{ i18n.global.t('articles') }}</div>
            </div>
          </div>
          <div style="width: 50px"></div>
          <div :style="{color: 'rgba(var(--v-theme-primary), 0.8)'}"
               style="width: 600px">
            <div>{{ user.id }}</div>
            <div>{{ user.email }}</div>
            <div>{{ user.username }}</div>
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

import {onActivated, ref} from 'vue'
import {get, post} from '@/net'
import avatar from '@/assets/images/avatar.png'
import {useRoute, useRouter} from 'vue-router'
import {useTheme} from '@/store/modules/theme'
import {i18n} from '@/i18n'

const imgSrc = ref('')
const bookList = ref([])
const route = useRoute()
const router = useRouter()
const theme = useTheme()

const user = ref({})
const loaded = ref(false)
onActivated(() => {
  const works = [
    get('/api/user/id', {id: parseInt(route.query.id.toString())})
      .then(({data}) => {
        user.value = data
        getAvatar()
      }),
    post('/api/book/search/uploaderId', {uploaderId: parseInt(route.query.id.toString())})
      .then(({data}) => bookList.value = data)
  ]
  Promise.all(works)
    .then(() => {
      loaded.value = true
    })
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
  router.push({path: '/books', query: {book: b.bookId}})
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
