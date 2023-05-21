<template>
  <VThemeProvider :theme="theme.name">
    <router-view>
    </router-view>
  </VThemeProvider>
</template>

<script setup>
import {useTheme} from "@/store/modules/theme"

const theme = useTheme()
</script>

<style lang="less">
</style>
