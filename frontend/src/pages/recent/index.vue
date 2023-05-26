<template>
  <div>
    <h1>File</h1>
    <vue-pdf-embed :source="source1"/>
  </div>
</template>

<script>
import VuePdfEmbed from 'vue-pdf-embed'


export default {
  components: {
    VuePdfEmbed,
  },
  data() {
    return {
      source1: 'CMake手册详解[格式整理+带标签].pdf',
    }
  },
}
</script>
<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
  }
}
</route>
