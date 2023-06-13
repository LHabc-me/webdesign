<template>
  <VApp>
    <VMain>
      <router-view v-slot="{Component}">
        <keep-alive>
          <component :is="Component"/>
        </keep-alive>
      </router-view>
    </VMain>
    <ICPRecordNumber class="position-absolute mx-auto" style="bottom: 0;left:0;right: 0;"></ICPRecordNumber>
  </VApp>
</template>
<script setup>
import ICPRecordNumber from '@/component/ICPRecordNumber.vue'
</script>
