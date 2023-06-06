<template>
  <div>
    <VRow>
      <VCol cols="10" class="mx-auto">
        <VTable>
          <thead>
          <tr>
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
          <tr v-for="(book, index) in books" :key="index" class="text-center">
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
import {onActivated, ref} from 'vue'
import {post} from '@/net'
import {useMessage} from '@/store/modules/message'
import {useUser} from '@/store/modules/user'
import {i18n} from '@/i18n'


const message = useMessage()
const user = useUser()
const edit = ref(false)
const bookInfoEdit = ref({
  bookId: '',
  price: '',
  description: '',
})
const books = ref([])

function showEdit(index) {
  bookInfoEdit.value.bookId = books.value[index].bookId
  bookInfoEdit.value.price = books.value[index].price
  bookInfoEdit.value.description = books.value[index].description
  edit.value = true
}

const loading = ref(false)

function submitEdit() {
  loading.value = true
  const bookId = bookInfoEdit.value.bookId.toString()
  const price = parseInt(bookInfoEdit.value.price)
  const description = bookInfoEdit.value.description.toString()

  if (isNaN(price)) {
    message.error(i18n.global.t('invalid-input'))
    loading.value = false
    return
  }

  const priceWork = post('/api/book/set/price', {bookId, price})
  const descriptionWork = post('/api/book/set/description', {bookId, description})

  Promise.all([priceWork, descriptionWork])
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


onActivated(search)

function search() {
  post('/api/book/search/uploaderId', {uploaderId: user.id})
    .then(({data}) => {
      books.value = data
    })
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
