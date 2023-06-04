<template>
  <VDialog :model-value="modelValue"
           width="566">
    <VCard>
      <VCardTitle>
        <span class="text-h5">充值</span>
      </VCardTitle>
      <VCardText>
        <VTextField label="充值金额" v-model="topUpInfoEdit.coins" color="primary">
        </VTextField>
      </VCardText>
      <VCardActions>
        <VSpacer></VSpacer>
        <VBtn color="primary"
              variant="flat"
              @click="$emit('update:modelValue', false)">
          确定
        </VBtn>
        <VBtn @click="$emit('update:modelValue', false)">
          取消
        </VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>

<script setup>

import {ref} from 'vue'

const props = defineProps({
  modelValue: Boolean,
})
const emit = defineEmits(['update:modelValue'])
const topUpInfoEdit = ref({coins: 0})
</script>


<style scoped lang="scss">

</style>
