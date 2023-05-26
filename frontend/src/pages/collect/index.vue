<template>
  <div class="w-75 mx-auto">
    <VRow v-for="i in 6"
          :key="i">
      <VCol v-for="j in 6"
            :key="j"
            cols="2"
            layout="column center-center">
        <div style="width: 110px;height: 145px;background: red"></div>
      </VCol>
    </VRow>
  </div>
</template>
<script setup>

</script>
<style scoped lang="scss">
* {
  border: red 1px solid;
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
