<template>
  <div>
    <VRow>
      <VCol cols="10" class="mx-auto">
        <VTable>
          <thead>
          <tr>
            <th class="text-center">图书ID</th>
            <th class="text-center">图书名</th>
            <th class="text-center">是否原创</th>
            <th class="text-center">原作者</th>
            <th class="text-center">图书分类</th>
            <th class="text-center">图书价格</th>
            <th class="text-center">图书简介</th>
            <th class="text-center">热度</th>
            <th class="text-center">操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(book, index) in books" :key="index" class="text-center">
            <td>{{ book.bookId }}</td>
            <td>{{ book.originalFilename }}</td>
            <td>{{ book.original ? '是' : '否' }}</td>
            <td>{{ book.author }}</td>
            <td>{{ book.tag }}</td>
            <td>{{ book.price }}</td>
            <td>{{ book.description ? book.description.length >= 10 ? book.description.slice(0, 10) + '...' : book.description : '暂无简介' }}</td>
            <td>{{ book.hot }}</td>
            <td>
              <VBtn color="primary"
                    @click="showEdit(index)">
                修改
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
          <span class="text-h5">修改图书信息</span>
        </VCardTitle>
        <VCardText>
          <VTextField label="价格" v-model="bookInfoEdit.price" color="primary"></VTextField>
          <VTextarea label="简介" v-model="bookInfoEdit.description" color="primary"></VTextarea>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="submitEdit"
                :loading="loading">
            确定
          </VBtn>
          <VBtn
            @click="edit = false">
            取消
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
    message.error('输入不合法')
    return
  }

  const priceWork = post('/api/book/set/price', {bookId, price})
  const descriptionWork = post('/api/book/set/description', {bookId, description})

  Promise.all([priceWork, descriptionWork])
    .then(() => {
      message.success('修改成功')
      search()
    }).catch(() => {
    message.error('修改失败')
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
