<template>
  <div class="h-100" layout="column center-center">
    <h1>TODO：作者上传图书，设定原作者、是否原创、图书分类、价格、简介</h1>
    <VForm>
      <VRow>
        <VCol cols="6">
          1234
        </VCol>
      </VRow>
    </VForm>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {post} from "@/net";

const uploadBook = ref(null)
const uploadBookArea = ref(null)

function upload(file) {
  const form = new FormData()
  form.append('file', file)
  post('api/upload/author/new-book', form, {}, {'Content-Type': 'multipart/form-data'})
    .then(({data}) => {
      console.log(data)
    })
    .catch((error) => {
      console.log(error)
    })
}

function onDropUploadBook(event) {
  const dataTransfer = event.dataTransfer
  console.log(dataTransfer.files[0])
  upload(dataTransfer.files[0])
}

</script>


<style scoped lang="scss">
* {
  border: red solid 1px;

}
</style>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
