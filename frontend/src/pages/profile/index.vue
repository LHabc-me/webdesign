<template>
  <div class="h-100" layout="column center-center" v-if="loaded"
       :style="{
       'background-image': `url('images/profile-bg-${theme.name}.png')`,
       'background-size': 'cover'
  }">
    <div class="pa-10"
         layout="column cneter-center">
      <VAvatar size="200"
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
        <div layout="row top-center" class="text-h5">
          <div style="width: 520px;" layout="column top-right">
            <div>
              <div>ID</div>
              <div>{{ i18n.global.t('email') }}</div>
              <div>{{ i18n.global.t('user-name') }}</div>
              <div>{{ i18n.global.t('book-coins-balance') }}</div>
              <div>{{ i18n.global.t('hot') }}</div>
              <div>{{ i18n.global.t('articles') }}</div>
            </div>
          </div>
          <div style="width: 50px"></div>
          <div :style="{color: 'rgba(var(--v-theme-primary), 0.8)'}"
               style="width: 600px">
            <div>{{ user.id }}</div>
            <div>{{ user.email }}</div>
            <div>{{ user.name }}</div>
            <div>{{ user.coins }} <span class="text-green-darken-2 mouse-pointer" @click="topup">{{ i18n.global.t('topup') }}</span></div>
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
import {useRouter} from "vue-router"
import avatar from '@/assets/images/avatar.png'
import {updateUserInfo} from '@/utils/updateUserInfo'
import {useTheme} from '@/store/modules/theme'
import {i18n} from '@/i18n'

const uploadAvatarInput = ref(null)
const user = useUser()
const router = useRouter()
const bookList = ref([])
const theme = useTheme()

function onuploadAvatarInputChange(event) {
  const form = new FormData()
  form.append('file', event.target.files[0])
  post('api/upload/user/avatar', form, {}, {'Content-Type': 'multipart/form-data'})
    .then(getAvatar)
}

const showTopup = ref(false)

function topup() {
  showTopup.value = true
}

const imgSrc = ref('')
const loaded = ref(false)
onActivated(() => {
  const works = [
    updateUserInfo(),
    post('/api/book/search/uploaderId', {uploaderId: parseInt(user.id)})
      .then(({data}) => bookList.value = data),
    getAvatar()
  ]
  Promise.all(works)
    .then(() => loaded.value = true)
})

function getAvatar() {
  return get(`/api/avatar/${user.email}`)
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
