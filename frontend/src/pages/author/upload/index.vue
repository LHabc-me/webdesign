<template>
  <div class="h-100" layout="column center-center">
    <div style="width: 600px;">
      <VFileInput :label="$t('click-to-upload-book')"
                  accept="application/pdf"
                  v-model="form.files"
                  color="primary"></VFileInput>
      <VRadioGroup :label="$t('is-original')" v-model="form.isOriginal" color="primary">
        <VRadio :label="$t('yes')" :value="true"></VRadio>
        <VRadio :label="$t('no')" :value="false"></VRadio>
      </VRadioGroup>
      <VTextField :label="$t('original-author')" v-model="form.author" :disabled="form.isOriginal" color="primary"></VTextField>
      <VTextField :label="$t('type')" v-model="form.tag" color="primary"></VTextField>
      <VTextField :label="$t('price-fixing')" v-model="form.price" color="primary"></VTextField>
      <VTextarea :label="$t('description')" v-model="form.brief" color="primary"></VTextarea>
      <VBtn type="submit"
            color="primary"
            :block="true"
            @click.prevent="upload"
            :loading="loading">
        {{ i18n.global.t('upload') }}
      </VBtn>
    </div>
  </div>
</template>

<script setup>
import {ref, watchEffect} from 'vue'
import {post} from '@/net'
import {useUser} from '@/store/modules/user'
import {useMessage} from '@/store/modules/message'
import {i18n} from '@/i18n'

const user = useUser()
const message = useMessage()
const form = ref({
  files: null,
  isOriginal: true,
  author: null,//原作者
  tag: null,
  price: 0,
  brief: '',
})

watchEffect(() => {
  if (form.value.isOriginal) {
    form.value.author = user.name
  }
})

const loading = ref(false)

function upload() {
  loading.value = true

  if (!form.value.files ||
    !form.value.author ||
    !form.value.tag ||
    isNaN(parseInt(form.value.price))) {
    message.error(i18n.global.t('invalid-input'))
    loading.value = false
    return
  }

  const formUpload = new FormData()
  formUpload.append('file', form.value.files[0])
  formUpload.append('isOriginal', form.value.isOriginal)
  formUpload.append('author', form.value.author)
  formUpload.append('tag', form.value.tag)
  formUpload.append('price', parseInt(form.value.price))
  formUpload.append('description', form.value.brief)
  formUpload.append('uploaderId', parseInt(user.id))
  post('/api/upload/book', formUpload, {}, {'Content-Type': 'multipart/form-data'})
    .then(() => {
      message.success(i18n.global.t('upload-success-hot-increase-10'))
      form.value = {
        files: null,
        isOriginal: true,
        author: null,
        tag: null,
        price: 0,
        brief: '',
      }
    })
    .then(() => {
      post('/api/admin/update/hot', {userId: parseInt(user.id), hot: parseInt(user.hot.toString()) + 1})
    })
    .catch(() => {
      message.error(i18n.global.t('upload-fail'))
    })
    .finally(() => {
      loading.value = false
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
