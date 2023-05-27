<template>
  <div class="h-100">
    <div class="h-50" layout="column center-center">
      <VBtn class="bg-grey rounded-xl"
            width="300"
            height="300"
            @click="uploadBook.click()">
        <VIcon icon="mdi-plus" size="100"></VIcon>
      </VBtn>
    </div>
    <div class="h-50" layout="column center-center">
      <VForm>
        <input ref="uploadBook"
               type="file"
               class="d-none"
               accept="image/*"/>
      </VForm>
    </div>

  </div>
</template>

<script setup>
import {ref} from "vue";

const uploadBook = ref(null)

</script>


<style scoped lang="scss">
* {
  border: red solid 1px;

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
