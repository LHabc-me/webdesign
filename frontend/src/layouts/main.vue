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

    <keep-alive>
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
    </keep-alive>
    <VMain>
      <router-view class="pa-10 pb-0 mb-0"
                   v-slot="{Component}">
        <keep-alive>
          <component :is="Component"/>
        </keep-alive>
      </router-view>
    </VMain>
  </VApp>
</template>

<script setup>
import {ref, computed} from "vue"
import {useTheme} from "@/store/modules/theme"
import {useI18n} from "@/store/modules/i18n"
import {i18n} from "@/i18n";
import {useUser} from "@/store/modules/user";
import {useMessage} from "@/store/modules/message";
import {useRouter} from "vue-router";

const theme = useTheme()
const i18nStore = useI18n()
const router = useRouter()
const userStore = useUser()
const message = useMessage()

const showMenu = ref(false)

const langs = [
  {
    name: '简体中文',
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
  if (true || userStore.isLogin) {
    user = [
      {
        name: i18n.global.t('profile'),
        icon: 'mdi-account',
        onClick: () => {
        }
      },
      {
        name: i18n.global.t('设置'),
        icon: 'mdi-cog',
        onClick: () => {
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

const lists = [
  {
    items: [{
      text: i18n.global.t('home-page'),
      icon: 'mdi-home',
      to: '/'
    }]
  },
  {
    title: '读者专区', items: [
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
      }
    ]
  },
  {
    title: '作者专区', items: [
      {
        text: '管理作品',
        icon: 'mdi-book-open-blank-variant',
        to: '/author/workspace'
      },
      {
        text: '上传作品',
        icon: 'mdi-pencil',
        to: '/author/upload'
      },
      {
        text: '作者中心',
        icon: 'mdi-flag',
        to: '/author/center'
      }
    ]
  },
  {
    title: '管理员专区', items: [
      {
        text: '收支情况',
        icon: 'mdi-currency-usd',
        to: '/admin/finance'
      },
      {
        text: '活动管理',
        icon: 'mdi-party-popper',
        to: '/admin/activity'
      },
      {
        text: '热度管理',
        icon: 'mdi-fire',
        to: '/admin/hot'
      },
      {
        text: '用户管理',
        icon: 'mdi-account-multiple',
        to: '/admin/user'
      }
    ]
  },
]

function menuBtnClick() {
  if (!userStore.isLogin) {
    message.info(i18n.global.t('login-first'))
    // return
  }
  showMenu.value = !showMenu.value
}

</script>
