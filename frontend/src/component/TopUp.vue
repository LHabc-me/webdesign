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
              @click="topup"
              :loading="loading">
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
import {updateUserInfo} from "@/utils/updateUserInfo";
import {useUser} from "@/store/modules/user";
import {post} from "@/net";
import {useMessage} from "@/store/modules/message";
import {i18n} from "@/i18n"

const user = useUser()
const message = useMessage()
const props = defineProps({
  modelValue: Boolean,
})
const emit = defineEmits(['update:modelValue'])
const topUpInfoEdit = ref({})

const loading = ref(false)

function topup() {
  loading.value = true
  post('/api/user/recharge', {coins: parseInt(topUpInfoEdit.value.coins) + parseInt(user.coins)})
    .then(() => {
      message.success(i18n.global.t('topup-success'))
      emit('update:modelValue', false)
      updateUserInfo()
    })
    .catch(() => {
      message.error(i18n.global.t('topup-fail'))
    })
    .finally(() => {
      loading.value = false
    })
}
</script>


<style scoped lang="scss">

</style>
