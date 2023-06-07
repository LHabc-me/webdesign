<template>
  <div class="h-100">
    <VuePdf v-if="!noRecent && !loading"
            v-for="page in numOfPages"
            :key="page"
            :class="{'dark': theme.name === 'dark'}"
            :src="pdfSrc"
            :page="page"
            :enable-text-selection="true"
            :enable-annotations="false"
            :ref="pdfPages"/>
    <div v-else-if="loading" class="h-100" layout="row center-center">
      <VProgressCircular indeterminate
                         color="primary"
                         :size="70"
                         :width="7">
      </VProgressCircular>
    </div>
    <div v-else class="h-100" layout="row center-center">
      <div class="text-h4 font-weight-bold primary-color">
        {{ i18n.global.t('no-recent') }}(´･_･`)
      </div>
    </div>
  </div>
</template>

<script setup>
import {onActivated, ref, watch} from 'vue'
import {VuePdf} from 'vue3-pdfjs/esm'
import {useTheme} from '@/store/modules/theme'
import {post} from '@/net'
import {createLoadingTask} from 'vue3-pdfjs'
import {i18n} from "@/i18n";

const pdfSrc = ref()
const numOfPages = ref(0)
const theme = useTheme()
const pdfPages = ref([])

const noRecent = ref(false)
const loading = ref(true)
//当pdf加载完成后
watch(pdfPages, () => {
  console.log('pdfPages changed')
})
onActivated(() => {
  noRecent.value = false
  loading.value = true
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
    .catch(() => {
      noRecent.value = true
    })
    .finally(() => {
      loading.value = false
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
