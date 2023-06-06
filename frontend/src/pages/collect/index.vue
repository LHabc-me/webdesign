<template>
  <div class="w-75 mx-auto">
    <div class="w-50 mx-auto mb-10">
      <VBtn :color="showBadge ? 'error' : 'primary'"
            @click="showBadge = !showBadge"
            :block="true">
        {{ showBadge ? i18n.global.t('confirm-delete') : i18n.global.t('manage-books') }}
      </VBtn>
    </div>
    <VRow v-for="(books, i) in booksList"
          :key="i"
          class="mx-auto">
      <VCol v-for="(book, j) in books"
            :key="j"
            cols="2"
            class="mb-5 mx-auto"
            layout="row cneter-center">
        <VBadge class="mouse-pointer" :model-value="showBadge" v-if="!book.fake">
          <template #badge>
            <VIcon icon="mdi-close" @click="deleteBook(i, j)"></VIcon>
          </template>
          <div style="width: 110px;height: 145px;"
               :class="{'mouse-normal': showBadge}"
               @click="showBookDetail(book)">
            <VImg :src="pdf"
                  class="h-100"
                  :cover="true">
            </VImg>
            <div class="text-center font-weight-bold">{{ book.originalFilename }}</div>
          </div>
        </VBadge>
        <div v-else style="width: 110px;height: 145px;">
        </div>
      </VCol>
    </VRow>
  </div>
</template>
<script setup>
import {ref, computed, onActivated} from 'vue'
import {post} from "@/net"
import {useUser} from "@/store/modules/user"
import pdf from '@/assets/images/pdf.png'
import {useRouter} from "vue-router"
import {i18n} from "@/i18n";

const showBadge = ref(false)
const bookNumEachRow = 5
const user = useUser()
const router = useRouter()

const books = ref([])

function deleteBook(i, j) {
  books.value.splice(i * bookNumEachRow + j, 1)
}

//转为二维数组，每个数组长度为6
const booksList = computed(() => {
  if (!books.value || books.value.length === 0) {
    return []
  }
  const list = []
  for (let i = 0; i < books.value.length; i += bookNumEachRow) {
    list.push(books.value.slice(i, i + bookNumEachRow))
  }
  while (list[list.length - 1].length < bookNumEachRow) {
    list[list.length - 1].push({fake: true})
  }
  return list
})

onActivated(() => {
  post('/api/book/purchase/search', {userId: parseInt(user.id)})
    .then(({data}) => {
      books.value = data
    })
})

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
