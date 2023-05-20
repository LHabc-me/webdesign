import App from './App.vue'
import {registerPlugins} from '@/plugins'
import {createApp} from "vue"
import "@/assets/css/global.less"
import {i18n} from "@/i18n"

const app = createApp(App)
registerPlugins(app)
app.use(i18n)
app.mount('#app')
