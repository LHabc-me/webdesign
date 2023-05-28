<script setup>

</script>

<template>
  <div>
    <VRow>
      <VCol cols="5"
            class="mx-auto">
        <VRadioGroup :inline="true" layout="row center-center">
          <VRadio label="根据用户名搜索" value="1" color="primary"></VRadio>
          <VRadio label="根据用户id搜索" value="2" color="primary"></VRadio>
          <VRadio label="根据用户邮箱搜索" value="3" color="primary"></VRadio>
        </VRadioGroup>
        <VTextField color="primary"
                    variant="outlined"
                    density="comfortable"
                    :label="$t('search-user')"
                    append-inner-icon="mdi-magnify"
                    :clearable="true"></VTextField>
      </VCol>
      <h1>TODO：管理员修改用户密码、余额、热度、用户名</h1>
    </VRow>
  </div>
</template>

<style scoped lang="scss">
//* {
//  border: red solid 1px;
//}
</style>
<!--@formatter:off-->
<route lang="json5">
{
meta: {
layout: 'main',
}
}
</route>
