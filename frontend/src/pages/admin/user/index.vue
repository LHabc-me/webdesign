<template>
  <div>
    <VRow>
      <VCol cols="5"
            class="mx-auto">
        <VRadioGroup :inline="true" layout="row center-center" v-model="type">
          <VRadio v-if="false" label="根据用户名搜索" value="name" color="primary"></VRadio>
          <VRadio label="根据用户ID搜索" value="id" color="primary"></VRadio>
          <VRadio label="根据用户邮箱搜索" value="email" color="primary"></VRadio>
        </VRadioGroup>
        <VTextField color="primary"
                    variant="outlined"
                    density="comfortable"
                    :label="$t('search-user')"
                    append-inner-icon="mdi-magnify"
                    :clearable="true"
                    v-model="searchContent"
                    @click="search"></VTextField>
      </VCol>
    </VRow>
    <VRow>
      <VCol cols="8" class="mx-auto">
        <VTable>
          <thead>
          <tr>
            <th class="text-left">用户ID</th>
            <th class="text-left">用户名</th>
            <th class="text-left">用户邮箱</th>
            <th class="text-left">用户余额</th>
            <th class="text-left">用户热度</th>
            <th class="text-left">操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(user, index) in users" :key="index">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.coins }}</td>
            <td>{{ user.hot }}</td>
            <td>
              <VBtn color="primary"
                    @click="showEdit(index)">修改
              </VBtn>
            </td>
          </tr>
          </tbody>
        </VTable>
      </VCol>
    </VRow>
    <VDialog v-model="edit"
             width="550">
      <v-card>
        <VCardTitle>
          <span class="text-h5">修改用户信息</span>
        </VCardTitle>
        <VCardText>
          <VTextField label="余额" v-model="userInfoEdit.coins" color="primary"></VTextField>
          <VTextField label="热度" v-model="userInfoEdit.hot" color="primary"></VTextField>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="editInfo">
            确定
          </VBtn>
          <VBtn
            @click="edit = false">
            取消
          </VBtn>
        </VCardActions>
      </v-card>
    </VDialog>
  </div>

</template>

<script setup>
import {ref, watch} from 'vue'
import {get, post} from "@/net";
import {useMessage} from "@/store/modules/message";

const edit = ref(false)
const userInfoEdit = ref({
  id: '',
  hot: '',
  coins: ''
})
const message = useMessage()

function showEdit(index) {
  userInfoEdit.value.id = users.value[index].id
  userInfoEdit.value.hot = users.value[index].hot
  userInfoEdit.value.coins = users.value[index].coins
  console.log(userInfoEdit.value)
  edit.value = true
}

function editInfo() {
  const hot = post('/api/admin/update/hot', {
    userId: parseInt(userInfoEdit.value.id),
    hot: parseInt(userInfoEdit.value.hot)
  })

  const coins = post('/api/admin/update/coins', {
    userId: parseInt(userInfoEdit.value.id),
    coins: parseInt(userInfoEdit.value.coins)
  })

  Promise.all([hot, coins]).then(() => {
    message.success('修改成功')
    search()
  }).catch(() => {
    message.error('修改失败')
  }).finally(() => {
    edit.value = false
  })
}

const users = ref()
const type = ref('id')
const searchContent = ref('')

function search() {
  if (!searchContent.value) {
    users.value = []
    return
  }
  const obj = {}
  obj[type.value] = searchContent.value
  get(`/api/user/${type.value}`, obj)
    .then(({data}) => {
      if (data instanceof Array) {
        users.value = data
      } else {
        users.value = [data]
      }
    })
}

watch(searchContent, search)
watch(type, search)
</script>

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
