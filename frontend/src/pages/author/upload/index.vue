<template>
  <div class="h-100" layout="column center-center">
    <VForm style="width: 600px;">
      <VFileInput label="点击上传图书" v-model="form.file"></VFileInput>
      <VRadioGroup label="是否原创" v-model="form.isOrigin" color="primary">
        <VRadio label="是" :value="true"></VRadio>
        <VRadio label="否" :value="false"></VRadio>
      </VRadioGroup>
      <VTextField label="原作者" v-model="form.author" :disabled="form.isOrigin"></VTextField>
      <VTextField label="分类" v-model="form.tag"></VTextField>
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
import {useUser} from "@/store/modules/user"

const user = useUser()
const form = ref({
  file: null,
  isOrigin: true,
  author: null,//原作者
  tag: null,
  price: null,
  brief: null,
})

function upload() {
  const form = new FormData()
  for (const key in form.value) {
    form.append(key, form.value[key])
  }
  console.log(form.file)
  form.append('uploaderId', user.id)
  post('/api/upload/author/new-book', form, {}, {'Content-Type': 'multipart/form-data'})
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
