<template>
  <div>
    <h1>个人资料</h1>
    <VAvatar size="100"
             class="mouse-pointer"
             @click="uploadAvatarInput.click()">
      <VImg ref="img"
            src="avatar">
      </VImg>
    </VAvatar>
    <input ref="uploadAvatarInput"
           type="file"
           class="d-none"
           accept="image/*"/>
    <div>用户名</div>
    <div>电子邮箱</div>
    <div>书币余额</div>
  </div>
</template>
<script setup>
import {onMounted, ref} from "vue"
import {post} from "@/net"

const uploadAvatarInput = ref(null)
onMounted(() => {
  uploadAvatarInput.value.addEventListener('change', (event) => {
    const form = new FormData()
    form.append("file", event.target.files[0])
    post("api/user/avatar", form, {}, {"Content-Type": "multipart/form-data"})
      .then(({data}) => {
        console.log(data)
      })
      .catch((error) => {
        console.log(error)
      })
  })
})
</script>
<style scoped lang="scss">

</style>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
