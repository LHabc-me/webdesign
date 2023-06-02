<template>
  <h1>id为{{ $route.query.id }}的用户的简介</h1>
  <div class="h-100" layout="column center-center">
    <div class="elevation-4 pa-10"
         style="width: 336px; height: 500px;"
         layout="column cneter-center">
      <VAvatar size="100"
               class="mouse-pointer">
        <VImg ref="img"
              src="avatar">
        </VImg>
      </VAvatar>
      <div layout="row center-center"
           class="mt-10">
        <div class="w-33">
          <div>ID</div>
          <div>用户名</div>
          <div>书币余额</div>
          <div>热度</div>
          <div>作品</div>
        </div>
        <div class="w-33 text-light-blue">
          <div>123456</div>
          <div>LHabc</div>
          <div>100</div>
          <div>33</div>
          <div>暂无</div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>

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
