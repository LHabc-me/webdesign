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
                    v-model="searchContent"
                    @click="search"></VTextField>
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="10" class="mx-auto">
        <VTable>
          <thead>
          <tr>
            <th class="text-center">上传者ID</th>
            <th class="text-center">上传者用户名</th>
            <th class="text-center">上传者邮箱</th>
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
          <tr v-for="({user, book}, index) in info" :key="index" class="text-center">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
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
          <VTextField label="热度" v-model="bookInfoEdit.hot" color="primary"></VTextField>
          <VTextarea label="简介" v-model="bookInfoEdit.description" color="primary"></VTextarea>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="submitEdit">
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
import {get, post} from '@/net'
import {useMessage} from "@/store/modules/message";

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

function submitEdit() {
  const bookId = bookInfoEdit.value.bookId.toString()
  const hot = parseInt(bookInfoEdit.value.hot)
  const price = parseInt(bookInfoEdit.value.price)
  const description = bookInfoEdit.value.description.toString()

  if (isNaN(hot) || isNaN(price)) {
    message.error('输入不合法')
    return
  }

  const hotWork = post('/api/book/set/hot', {bookId, hot})
  const priceWork = post('/api/book/set/price', {bookId, price})
  const descriptionWork = post('/api/book/set/description', {bookId, description})

  Promise.all([hotWork, priceWork, descriptionWork])
    .then(() => {
      message.success('修改成功')
      search()
    }).catch(() => {
    message.error('修改失败')
  }).finally(() => {
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
          works.push(getUserInfoById(book.uploaderId).then(user => {
            tmp.push({user, book})
          }))
        })
        Promise.all(works).then(() => {
          info.value = tmp
        })
      })
      break
    case 'bookId':
      getBookInfoByBookId(searchContent.value).then(book => {
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
