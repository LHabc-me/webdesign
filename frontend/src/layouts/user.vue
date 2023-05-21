<template>
  <VApp>
    <VAppBar class="elevation-3">
      <template #prepend>
        <VBtn icon="mdi-menu" @click="showMenu = !showMenu"></VBtn>
        <span>{{ $t('website.name') }}</span>
      </template>
      <template #append>
        <VMenu transition="fade-transition">
          <template #activator="{ props }">
            <VBtn v-bind="props"
                  icon="mdi-translate"></VBtn>
          </template>
          <v-list>
            <VListItem
              v-for="(item, index) in langs"
              :key="index"
              :value="index"
              @click="i18nStore.changeLang(item.code)">
              <VListItemTitle>{{ item.name }}</VListItemTitle>
            </VListItem>
          </v-list>
        </VMenu>
        <VBtn :icon="theme.name === 'light' ? 'mdi-weather-sunny' : 'mdi-weather-night'"
              @click="theme.changeTheme()">
        </VBtn>
        <VMenu transition="fade-transition">
          <template #activator="{ props }">
            <VBtn v-bind="props"
                  icon="mdi-account"
            >
            </VBtn>
          </template>
          <VList>
            <VListItem
              v-for="(item, index) in user"
              :key="index"
              :value="index"
              @click="item.onClick">
              <template #prepend>
                <VIcon :icon="item.icon"
                       class="me-2"></VIcon>
              </template>
              <VListItemTitle>{{ item.name }}</VListItemTitle>
            </VListItem>
          </VList>
        </VMenu>
      </template>
    </VAppBar>
    <VNavigationDrawer v-if="showMenu">
      <div class="h-50">
        <span class="text-h2">1</span>
      </div>
      <VDivider></VDivider>
      <div>
        <span class="text-h2">2</span>
      </div>
    </VNavigationDrawer>
    <VMain>
      <router-view class="pa-10"/>
    </VMain>
  </VApp>
</template>

<script setup>
import {ref, computed} from "vue"
import {useTheme} from "@/store/modules/theme"
import {useI18n} from "@/store/modules/i18n"
import {i18n} from "@/i18n";
import {useUser} from "@/store/modules/user";
import {useRouter} from "vue-router";

const showMenu = ref(false)
const theme = useTheme()
const i18nStore = useI18n()
const router = useRouter()

const langs = [
  {
    name: '中文',
    code: 'zh'
  },
  {
    name: 'English',
    code: 'en'
  },
  // {
  //   name: '日本語',
  //   code: 'ja'
  // },
]


const user = computed(() => {
  let user = []
  const userStore = useUser()
  if (userStore.isLogin) {
    user = [
      {
        name: i18n.global.t('user.profile'),
        icon: 'mdi-account',
        onClick: () => {
        }
      },
      {
        name: i18n.global.t('user.settings'),
        icon: 'mdi-cog',
        onClick: () => {
        }
      },
      {
        name: i18n.global.t('user.logout'),
        icon: 'mdi-logout',
        onClick: () => {
          userStore.isLogin = false
          router.push('/login')
        }
      }
    ]
  } else {
    user.push({
      name: i18n.global.t('user.login'),
      icon: 'mdi-login',
      onClick: () => {
        router.push('/login')
      }
    })
  }
  return user
})
</script>
<style lang="less">
</style>
