<template>
  <div>
    <VRow>
      <VCol cols="6"
            class="mx-auto">
        <VRadioGroup :inline="true" layout="row center-center" v-model="type">
          <VRadio label="根据图书名搜索" value="bookName" color="primary"></VRadio>
          <VRadio label="根据图书ID搜索" value="bookId" color="primary"></VRadio>
          <VRadio label="根据上传者ID搜索" value="userId" color="primary"></VRadio>
          <VRadio label="根据上传者邮箱搜索" value="email" color="primary"></VRadio>
        </VRadioGroup>
        <VTextField color="primary"
                    variant="outlined"
                    density="comfortable"
                    :label="$t('search-book')"
                    append-inner-icon="mdi-magnify"
                    :clearable="true"
                    v-model="searchContent"></VTextField>
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="10" class="mx-auto">
        <VTable>
          <thead>
          <tr>
            <th class="text-left">上传者ID</th>
            <th class="text-left">上传者用户名</th>
            <th class="text-left">上传者邮箱</th>
            <th class="text-left">图书ID</th>
            <th class="text-left">图书名</th>
            <th class="text-left">是否原创</th>
            <th class="text-left">原作者</th>
            <th class="text-left">图书分类</th>
            <th class="text-left">图书价格</th>
            <th class="text-left">图书简介</th>
            <th class="text-left">热度</th>
            <th class="text-left">操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="({user, book}, index) in info" :key="index">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ book.bookId }}</td>
            <td>{{ book.name }}</td>
            <td>{{ book.isOriginal }}</td>
            <td>{{ book.author }}</td>
            <td>{{ book.tag }}</td>
            <td>{{ book.price }}</td>
            <td>{{ book.description?.length >= 10 ? book.description.slice(0, 10) + '...' : book.description }}</td>
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
             :persistent="true"
             width="550">
      <v-card>
        <VCardTitle>
          <span class="text-h5">修改图书信息</span>
        </VCardTitle>
        <VCardText>
          <VTextField label="价格" v-model="bookInfoEdit.price" color="primary"></VTextField>
          <VTextField label="热度" v-model="bookInfoEdit.hot" color="primary"></VTextField>
          <VTextarea label="简介" v-model="bookInfoEdit.description" color="primary"></VTextarea>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="edit = false">
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
import {ref, watch} from 'vue'
import {get} from '@/net'

const edit = ref(false)
const bookInfoEdit = ref({hot: 0, price: 0, description: ''})

function showEdit(index) {
  bookInfoEdit.value.hot = info[index].book.hot
  bookInfoEdit.value.price = info[index].book.price
  bookInfoEdit.value.description = info[index].book.description
  edit.value = true
}


const info = [
  {
    user: {},
    book: {},
  }
]

const type = ref('bookName')
const searchContent = ref('')


async function getUserInfoById(userId) {
  let user = null
  await get('/api/user/id', {id: userId})
    .then(({data}) => {
      user = data
    })
  return user
}

async function getUserInfoByEmail(email) {
  let user = null
  await get('/api/user/email', {email})
    .then(({data}) => {
      user = data
    })
  return user
}

async function getBookInfoById(bookId) {
  let book = null
  await get('/api/book/search/uuid', {bookId})
    .then(({data}) => {
      book = data
    })
  return book
}

async function getBookInfoByName(bookName) {
  let book = null
  await get('/api/book/search/keywords', {keywords: bookName})
    .then(({data}) => {
      book = data
    })
  return book
}

watch(searchContent, () => {
  switch (type.value) {
    case 'bookName':
      getBookInfoByName(searchContent.value).then(books => {
        info.value = []
        books.forEach(book => {
          getUserInfoById(book.uploaderId).then(user => {
            info.value.push({user, book})
          })
        })
      })
      break
    case 'bookId':
      getBookInfoById(searchContent.value).then(book => {
        getUserInfoById(book.uploaderId).then(user => {
          info.value = [{user, book}]
        })
      })
      break
    case 'userId':
      getUserInfoById(searchContent.value).then(user => {
        getBookInfoById(user.bookId).then(book => {
          info.value = [{user, book}]
        })
      })
      break
    case 'email':
      getUserInfoByEmail(searchContent.value).then(user => {
        getBookInfoById(user.bookId).then(book => {
          info.value = [{user, book}]
        })
      })
      break
  }
})
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
