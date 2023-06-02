<template>
  <div class="h-100" layout="column center-center">
    <div style="width: 600px;">
      <VFileInput label="点击上传图书" v-model="form.files"></VFileInput>
      <VRadioGroup label="是否原创" v-model="form.isOriginal" color="primary">
        <VRadio label="是" :value="true"></VRadio>
        <VRadio label="否" :value="false"></VRadio>
      </VRadioGroup>
      <VTextField label="原作者" v-model="form.author" :disabled="form.isOriginal"></VTextField>
      <VTextField label="分类" v-model="form.tag"></VTextField>
      <VTextField label="定价" v-model="form.price"></VTextField>
      <VTextarea label="简介" v-model="form.brief"></VTextarea>
      <VBtn type="submit"
            color="primary"
            :block="true"
            @click.prevent="upload">
        上传
      </VBtn>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {post} from "@/net";
import {useUser} from "@/store/modules/user"

const user = useUser()
const form = ref({
  files: null,
  isOriginal: true,
  author: null,//原作者
  tag: null,
  price: null,
  brief: null,
})

function upload() {
  console.log(form.value.files[0])


  const formUpload = new FormData()
  formUpload.append('file', form.value.files[0])
  formUpload.append('isOriginal', form.value.isOriginal)
  formUpload.append('author', form.value.author)
  formUpload.append('tag', form.value.tag)
  formUpload.append('price', parseInt(form.value.price))
  formUpload.append('description', form.value.brief)
  formUpload.append('uploaderId', parseInt(user.id))
  post('/api/upload/book', formUpload, {}, {'Content-Type': 'multipart/form-data'})
    .then(({data}) => {
      console.log(data)
    })
    .catch((error) => {
      console.log(error)
    })
}
</script>


<style scoped lang="scss">
//* {
//  border: red solid 1px;
//
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
