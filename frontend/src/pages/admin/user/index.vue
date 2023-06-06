<template>
  <div>
    <VRow>
      <VCol cols="5"
            class="mx-auto">
        <VRadioGroup :inline="true" layout="row center-center" v-model="type">
          <VRadio :label="$t('search-by-user-id')" value="id" color="primary"></VRadio>
          <VRadio :label="$t('search-by-user-email')" value="email" color="primary"></VRadio>
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
          <tr class="text-center">
            <th class="text-center">{{ $t('user-id') }}</th>
            <th class="text-center">{{ $t('user-name') }}</th>
            <th class="text-center">{{ $t('user-email') }}</th>
            <th class="text-center">{{ $t('user-balance') }}</th>
            <th class="text-center">{{ $t('user-hot') }}</th>
            <th class="text-center">{{ $t('operation') }}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(user, index) in users" :key="index" class="text-center">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.coins }}</td>
            <td>{{ user.hot }}</td>
            <td>
              <VBtn color="primary"
                    @click="showEdit(index)">
                {{ $t('modify') }}
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
          <span class="text-h5">{{ $t('modify-user-info') }}</span>
        </VCardTitle>
        <VCardText>
          <VTextField :label="$t('balance')" v-model="userInfoEdit.coins" color="primary"></VTextField>
          <VTextField :label="$t('hot')" v-model="userInfoEdit.hot" color="primary"></VTextField>
        </VCardText>
        <VCardActions>
          <VSpacer></VSpacer>
          <VBtn color="primary"
                variant="flat"
                @click="submitEdit"
                :loading="loading">
            {{ $t('confirm') }}
          </VBtn>
          <VBtn
            @click="edit = false">
            {{ $t('cancel') }}
          </VBtn>
        </VCardActions>
      </v-card>
    </VDialog>
  </div>

</template>

<script setup>
import {ref, watch} from 'vue'
import {get, post} from '@/net'
import {useMessage} from '@/store/modules/message'
import {i18n} from '@/i18n'

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

const loading = ref(false)

function submitEdit() {
  loading.value = true
  const id = parseInt(userInfoEdit.value.id)
  const hot = parseInt(userInfoEdit.value.hot)
  const coins = parseInt(userInfoEdit.value.coins)

  if (isNaN(id) || isNaN(hot) || isNaN(coins)) {
    message.error(i18n.global.t('invalid-input'))
    loading.value = false
    return
  }

  const hotWork = post('/api/admin/update/hot', {
    id,
    hot
  })

  const coinsWork = post('/api/admin/update/coins', {
    id,
    coins
  })

  Promise.all([hotWork, coinsWork]).then(() => {
    message.success(i18n.global.t('modify-success'))
    search()
  }).catch(() => {
    message.error(i18n.global.t('modify-fail'))
  }).finally(() => {
    loading.value = false
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

<!--@formatter:off-->
<route lang="json5">
{
  meta: {
    layout: 'main',
    requireLogin: true,
  }
}
</route>
