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
import {onMounted, ref} from 'vue'
import {VuePdf, createLoadingTask} from 'vue3-pdfjs/esm'
import {useTheme} from "@/store/modules/theme"

const pdfSrc = ref('https://raw.githubusercontent.com/mozilla/pdf.js/ba2edeae/web/compressed.tracemonkey-pldi-09.pdf')
const numOfPages = ref(0)
const theme = useTheme()

onMounted(() => {
  const loadingTask = createLoadingTask(pdfSrc.value)
  loadingTask.promise.then(pdf => {
    numOfPages.value = pdf.numPages


  })

})
</script>

<style lang="scss">
.pdf-dark {
  filter: invert(98%);
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
