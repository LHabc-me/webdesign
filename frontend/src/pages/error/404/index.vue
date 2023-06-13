<template>
  <div class="h-screen" layout="column center-center">
    <span class="text-h1 font-weight-bold">404</span>
    <VBtn color="primary"
          @click="$router.go(-1)">
      {{ i18n.global.t('back') }}
    </VBtn>
  </div>
</template>

<script setup>
import {i18n} from '@/i18n'
</script>

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'blank',
  }
}
</route>
