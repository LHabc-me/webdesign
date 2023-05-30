<template>
  <div class="h-100" layout="column center-center">
    <VForm style="width: 600px;">
      <VFileInput label="点击上传图书" v-model="form.file"></VFileInput>
      <VRadioGroup label="是否原创" v-model="form.isOrigin" color="primary">
        <VRadio label="是" :value="true"></VRadio>
        <VRadio label="否" :value="false"></VRadio>
      </VRadioGroup>
      <VTextField label="原作者" v-model="form.writer" :disabled="form.isOrigin"></VTextField>
      <VTextField label="分类" v-model="form.type"></VTextField>
      <VTextField label="定价" v-model="form.price"></VTextField>
      <VTextarea label="简介" v-model="form.brief"></VTextarea>
      <VBtn type="submit"
            color="primary"
            :block="true"
            @click.prevent="upload">
        上传
      </VBtn>
    </VForm>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {post} from "@/net";

const form = ref({
  file: null,
  isOrigin: true,
  writer: null,
  type: null,
  price: null,
  brief: null,
})

function upload() {
  const form = new FormData()
  for (const key in form.value) {
    form.append(key, form.value[key])
  }
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
