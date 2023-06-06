<template>
  <div>
    <VuePdf v-for="page in numOfPages"
            :key="page"
            :class="{'dark': theme.name === 'dark'}"
            :src="pdfSrc"
            :page="page"
            :enable-text-selection="true"
            :enable-annotations="false"
            :ref="pdfPages"/>
  </div>
</template>

<script setup>
import {onActivated, ref, watch} from 'vue'
import {VuePdf} from 'vue3-pdfjs/esm'
import {useTheme} from '@/store/modules/theme'
import {post} from '@/net'
import {createLoadingTask} from 'vue3-pdfjs'

const pdfSrc = ref()
const numOfPages = ref(0)
const theme = useTheme()
const pdfPages = ref([])


//当pdf加载完成后
watch(pdfPages, () => {
  console.log('pdfPages changed')
})
onActivated(() => {
  post('/api/book/send', {bookId: localStorage.getItem('recentBookId')}, {}, {responseType: 'blob'})
    .then(({data}) => {
      pdfSrc.value = {data: window.atob(data)}
      const loadingTask = createLoadingTask(pdfSrc.value)
      loadingTask.promise.then(pdf => {
        numOfPages.value = Math.min(5, pdf.numPages)
        setInterval(function increase() {
          if (numOfPages.value < pdf.numPages) {
            numOfPages.value += Math.min(20, pdf.numPages - numOfPages.value)
          } else {
            clearInterval(increase)
          }
        }, 0)
      })
    })
})
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
