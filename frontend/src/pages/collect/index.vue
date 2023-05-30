<template>
  <div class="w-75 mx-auto">
    <h1>TODO：用户删除图书</h1>
    <VBtn color="primary" @click="showBadge = !showBadge">管理图书</VBtn>
    <VRow v-for="(books, i) in booksList"
          :key="i">
      <VCol v-for="(book, j) in books"
            :key="j"
            cols="2"
            layout="column center-center">

        <VBadge class="mouse-pointer" :model-value="showBadge">
          <template #badge>
            <VIcon icon="mdi-close"></VIcon>
          </template>
          <div style="width: 110px;height: 145px;"
               class="mouse-normal">
            <VImg src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
                  class="h-100"
                  :cover="true">
            </VImg>
          </div>
        </VBadge>
      </VCol>
    </VRow>
  </div>
</template>
<script setup>
import {ref, computed} from 'vue'

const showBadge = ref(false)
const bookNumEachRow = 6

const books = ref([
  {name: '1'},
  {name: '2'},
  {name: '3'},
  {name: '4'},
  {name: '5'},
  {name: '6'},
  {name: '7'},
]);

function deleteBook(i, j) {
  books.value.splice(i * bookNumEachRow + j, 1)
}

//转为二维数组，每个数组长度为6
const booksList = computed(() => {
  const list = []
  for (let i = 0; i < books.value.length; i += bookNumEachRow) {
    list.push(books.value.slice(i, i + bookNumEachRow))
  }
  return list
})
</script>
<style scoped lang="scss">
//* {
//  border: red 1px solid;
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
