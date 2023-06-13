<template>
  <VApp>
    <VAppBar class="elevation-3">
      <template #prepend>
        <VBtn icon="mdi-menu" @click="menuBtnClick"></VBtn>
        <span>{{ $t('website-name') }}</span>
      </template>
      <template #append>
        <VMenu transition="fade-transition">
          <template #activator="{ props }">
            <VBtn v-bind="props"
                  icon="mdi-translate"></VBtn>
          </template>
          <v-list>
            <VListItem v-for="(item, index) in langs"
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
                  icon="mdi-account">
            </VBtn>
          </template>
          <VList>
            <VListItem v-for="(item, index) in user"
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

    <VNavigationDrawer v-model="showMenu"
                       :temporary="true">
      <VList :mandatory="true">
        <div v-for="(list, list_index) in lists" :key="list_index">
          <VDivider v-if="list_index !== 0"/>
          <VListSubheader v-if="list.title">{{ list.title }}</VListSubheader>
          <VListItem v-for="(item, item_index) in list.items"
                     :key="item_index"
                     color="primary"
                     variant="flat"
                     :active="$route.path === item.to"
                     @click="$router.push(item.to)">
            <template #prepend>
              <VIcon :icon="item.icon"></VIcon>
            </template>
            <VListItemTitle>{{ item.text }}</VListItemTitle>
          </VListItem>
        </div>
      </VList>
    </VNavigationDrawer>
    <VMain>
      <router-view class="pa-10"
                   v-slot="{Component}"
                   :key="routerViewKey">
        <keep-alive>
          <component :is="Component"/>
        </keep-alive>
      </router-view>
    </VMain>
    <ICPRecordNumber></ICPRecordNumber>
  </VApp>
</template>

<script setup>
import {ref, computed} from 'vue'
import {useTheme} from '@/store/modules/theme'
import {useI18n} from '@/store/modules/i18n'
import {i18n} from '@/i18n'
import {useUser} from '@/store/modules/user'
import {useMessage} from '@/store/modules/message'
import {useRouter} from 'vue-router'
import ICPRecordNumber from '@/component/ICPRecordNumber.vue'

const theme = useTheme()
const i18nStore = useI18n()
const router = useRouter()
const userStore = useUser()
const message = useMessage()

const showMenu = ref(false)

const routerViewKey = computed(() => {
  if (router.currentRoute.value.path === '/') {
    return '/'
  }
  return router.currentRoute.value.fullPath
})

const langs = [
  {
    name: '简体中文',
    code: 'zh'
  },
  {
    name: 'English',
    code: 'en'
  },
  {
    name: '日本語',
    code: 'ja'
  },
  {
    name: 'Deutsch',
    code: 'de'
  },
  {
    name: 'Русский',
    code: 'ru'
  },
]


const user = computed(() => {
  let user = []
  if (userStore.isLogin) {
    user = [
      {
        name: i18n.global.t('profile'),
        icon: 'mdi-account',
        onClick: () => {
          router.push('/profile')
        }
      },
      {
        name: i18n.global.t('logout'),
        icon: 'mdi-logout',
        onClick: () => {
          userStore.isLogin = false
          router.push('/login')
        }
      }
    ]
  } else {
    user.push({
      name: i18n.global.t('login'),
      icon: 'mdi-login',
      onClick: () => {
        router.push('/login')
      }
    })
  }
  return user
})

const lists = computed(() => {
  const res = [
    {
      items: [{
        text: i18n.global.t('home-page'),
        icon: 'mdi-home',
        to: '/'
      }]
    },
    {
      title: i18n.global.t('user-page'), items: [
        {
          text: i18n.global.t('recently-read'),
          icon: 'mdi-clock',
          to: '/recent'
        },
        {
          text: i18n.global.t('my-collection'),
          icon: 'mdi-star-box',
          to: '/collect'
        },
        {
          text: i18n.global.t('profile'),
          icon: 'mdi-account',
          to: '/profile'
        },
      ]
    },
    {
      title: i18n.global.t('author-page'), items: [
        {
          text: i18n.global.t('book-manage'),
          icon: 'mdi-book-open-blank-variant',
          to: '/author/workspace'
        },
        {
          text: i18n.global.t('upload-book'),
          icon: 'mdi-pencil',
          to: '/author/upload'
        },
      ]
    },
  ]
  if (userStore.type === 'admin') {
    res.push(
      {
        title: i18n.global.t('admin-page'),
        items: [
          {
            text: i18n.global.t('income-and-expenditure'),
            icon: 'mdi-currency-usd',
            to: '/admin/finance'
          },
          {
            text: i18n.global.t('book-manage'),
            icon: 'mdi-fire',
            to: '/admin/books'
          },
          {
            text: i18n.global.t('user-manage'),
            icon: 'mdi-account-multiple',
            to: '/admin/user'
          }
        ]
      })
  }
  return res
})


function menuBtnClick() {
  if (!userStore.isLogin) {
    message.info(i18n.global.t('login-first'))
    return
  }
  showMenu.value = !showMenu.value
}

</script>
