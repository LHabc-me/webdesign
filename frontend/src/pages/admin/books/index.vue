<template>
  <div>
    <VRow>
      <VCol cols="6"
            class="mx-auto">
        <VRadioGroup :inline="true" layout="row center-center" v-model="type">
          <VRadio :label="$t('search-by-book-name')" value="bookName" color="primary"></VRadio>
          <VRadio :label="$t('search-by-book-id')" value="bookId" color="primary"></VRadio>
          <VRadio :label="$t('search-by-uploader-id')" value="userId" color="primary"></VRadio>
          <VRadio :label="$t('search-by-uploader-email')" value="email" color="primary"></VRadio>
        </VRadioGroup>
        <VTextField color="primary"
                    variant="outlined"
                    density="comfortable"
                    :label="$t('search-book')"
                    append-inner-icon="mdi-magnify"
                    :clearable="true"
                    v-model="searchContent"
                    @click="search"></VTextField>
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="10" class="mx-auto">
        <VTable>
          <thead>
          <tr>
            <th class="text-center">{{ i18n.global.t('uploader-id') }}</th>
            <th class="text-center">{{ i18n.global.t('uploader-name') }}</th>
            <th class="text-center">{{ i18n.global.t('uploader-email') }}</th>
            <th class="text-center">{{ i18n.global.t('book-id') }}</th>
            <th class="text-center">{{ i18n.global.t('book-name') }}</th>
            <th class="text-center">{{ i18n.global.t('is-original') }}</th>
            <th class="text-center">{{ i18n.global.t('original-author') }}</th>
            <th class="text-center">{{ i18n.global.t('book-tags') }}</th>
            <th class="text-center">{{ i18n.global.t('book-price') }}</th>
            <th class="text-center">{{ i18n.global.t('book-description') }}</th>
            <th class="text-center">{{ i18n.global.t('hot') }}</th>
            <th class="text-center">{{ i18n.global.t('operation') }}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="({user, book}, index) in info" :key="index" class="text-center">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ book.bookId }}</td>
            <td>{{ book.originalFilename }}</td>
            <td>{{ book.original ? i18n.global.t('yes') : i18n.global.t('no') }}</td>
            <td>{{ book.author }}</td>
            <td>{{ book.tag }}</td>
            <td>{{ book.price }}</td>
            <td>{{ book.description ? book.description.length >= 10 ? book.description.slice(0, 10) + '...' : book.description : '暂无简介' }}</td>
            <td>{{ book.hot }}</td>
            <td>
              <VBtn color="primary"
                    @click="showEdit(index)">
                {{ i18n.global.t('modify') }}
              </VBtn>
            </td>
          </tr>
          </tbody>
        </VTable>
      </VCol>
    </VRow>
    <VDialog v-model="edit"
             width="550">
      <v-card>
        <VCardTitle>
          <span class="text-h5">{{ i18n.global.t('modify-book-info') }}</span>
        </VCardTitle>
        <VCardText>
          <VTextField :label="$t('price')" v-model="bookInfoEdit.price" color="primary"></VTextField>
          <VTextField :label="$t('hot')" v-model="bookInfoEdit.hot" color="primary"></VTextField>
          <VTextarea :label="$t('description')" v-model="bookInfoEdit.description" color="primary"></VTextarea>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="submitEdit"
                :loading="loading">
            {{ i18n.global.t('confirm') }}
          </VBtn>
          <VBtn
            @click="edit = false">
            {{ i18n.global.t('cancel') }}
          </VBtn>
        </VCardActions>
      </v-card>
    </VDialog>
  </div>

</template>

<script setup>
import {ref, watch} from 'vue'
import {get, post} from '@/net'
import {useMessage} from '@/store/modules/message'
import {i18n} from '@/i18n'

const message = useMessage()
const edit = ref(false)
const bookInfoEdit = ref({
  bookId: '',
  hot: '',
  price: '',
  description: '',
})

function showEdit(index) {
  bookInfoEdit.value.bookId = info.value[index].book.bookId
  bookInfoEdit.value.hot = info.value[index].book.hot
  bookInfoEdit.value.price = info.value[index].book.price
  bookInfoEdit.value.description = info.value[index].book.description
  edit.value = true
}

const loading = ref(false)

function submitEdit() {
  loading.value = true
  const bookId = bookInfoEdit.value.bookId.toString()
  const hot = parseInt(bookInfoEdit.value.hot)
  const price = parseInt(bookInfoEdit.value.price)
  const description = bookInfoEdit.value.description.toString()

  if (isNaN(hot) || isNaN(price)) {
    message.error(i18n.global.t('invalid-input'))
    loading.value = false
    return
  }

  const hotWork = post('/api/book/set/hot', {bookId, hot})
  const priceWork = post('/api/book/set/price', {bookId, price})
  const descriptionWork = post('/api/book/set/description', {bookId, description})

  Promise.all([hotWork, priceWork, descriptionWork])
    .then(() => {
      message.success(i18n.global.t('modify-success'))
      search()
    }).catch(() => {
    message.error(i18n.global.t('modify-fail'))
  }).finally(() => {
    loading.value = false
    edit.value = false
  })
}

const info = ref([])

const type = ref('bookName')
const searchContent = ref('')


async function getUserInfoById(userId) {
  if (!parseInt(userId)) {
    return
  }
  let user = null
  await get('/api/user/id', {id: parseInt(userId)})
    .then(({data}) => {
      user = data
    })
  return user
}

async function getUserInfoByEmail(email) {
  if (!email) {
    return
  }
  let user = null
  await get('/api/user/email', {email})
    .then(({data}) => {
      user = data
    })
  return user
}

async function getBookInfoByBookId(bookId) {
  if (!bookId) {
    return
  }
  let book = null
  await post('/api/book/search/bookId', {bookId})
    .then(({data}) => {
      book = data
    })
  return book
}

async function getBookInfoByName(bookName) {
  if (!bookName) {
    return
  }
  let book = null
  await post('/api/book/search/keywords', {keywords: bookName})
    .then(({data}) => {
      book = data
    })
  return book
}

async function getBookInfoByUploaderId(uploaderId) {
  let book = null
  await post('/api/book/search/uploaderId', {uploaderId})
    .then(({data}) => {
      book = data
    })
  return book
}

function search() {
  if (!searchContent.value) {
    info.value = []
    return
  }
  switch (type.value) {
    case 'bookName':
      getBookInfoByName(searchContent.value).then(books => {
        const tmp = []
        const works = []
        books.forEach(book => {
          works.push(getUserInfoById(book.uploaderId)
            .then(user => {
              tmp.push({user, book})
            }))
        })
        Promise.all(works)
          .then(() => {
            info.value = tmp
          })
      })
      break
    case 'bookId':
      getBookInfoByBookId(searchContent.value)
        .then(book => {
          if (!book) {
            info.value = []
            return
          }
          getUserInfoById(book.uploaderId).then(user => {
            info.value = [{user, book}]
          })
        })
      break
    case 'userId':
      getUserInfoById(searchContent.value).then(user => {
        getBookInfoByUploaderId(user.id).then(books => {
          const tmp = []
          books.forEach(book => {
            tmp.push({user, book})
          })
          info.value = tmp
        })
      })
      break
    case 'email':
      getUserInfoByEmail(searchContent.value).then(user => {
        getBookInfoByUploaderId(user.id).then(books => {
          const tmp = []
          books.forEach(book => {
            tmp.push({user, book})
          })
          info.value = tmp
        })
      })
      break
  }
}

watch(searchContent, search)
watch(type, search)
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
