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
import {onActivated, onMounted, ref, watch} from 'vue'
import {VuePdf, createLoadingTask} from 'vue3-pdfjs/esm'
import {useTheme} from "@/store/modules/theme"
import {useRoute} from "vue-router"
import {post} from "@/net";

const route = useRoute()
const pdfSrc = ref('https://raw.githubusercontent.com/mozilla/pdf.js/ba2edeae/web/compressed.tracemonkey-pldi-09.pdf')
const numOfPages = ref(0)
const theme = useTheme()

onActivated(() => {
  const form = new FormData()
  form.append('bookID', route.query.bookID)
  post('api/book/send', form).then(({data}) => {
    pdfSrc.value = data
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
