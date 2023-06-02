<template>
  <div>
    <VuePdf v-for="page in numOfPages"
            :key="page"
            :class="{'pdf-dark': theme.name === 'dark'}"
            :src="pdfSrc"
            :page="page"
            :enable-text-selection="false"
            :enable-annotations="false"/>
  </div>
</template>

<script setup>
import {onActivated, ref} from 'vue'
import {VuePdf} from 'vue3-pdfjs/esm'
import {useTheme} from "@/store/modules/theme"
import {post} from "@/net";
import {createLoadingTask} from "vue3-pdfjs"

const pdfSrc = ref()
const numOfPages = ref(0)
const theme = useTheme()

onActivated(() => {
  post('/api/book/send', {bookId: localStorage.getItem('recentBookId')}, {}, {responseType: 'blob'})
    .then(({data}) => {
      pdfSrc.value = {data: window.atob(data)}
      const loadingTask = createLoadingTask(pdfSrc.value)
      loadingTask.promise.then(pdf => {
        numOfPages.value = pdf.numPages
      })
    })
})
</script>

<style lang="scss" scoped>
.pdf-dark {
  filter: invert(93%);
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
