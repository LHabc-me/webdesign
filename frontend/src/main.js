import App from './App.vue'
import {registerPlugins} from '@/plugins'
import {createApp} from "vue"
import {i18n} from "@/i18n"
import ToastPlugin from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-default.css';
import "@/assets/css/global.less"

const app = createApp(App)

registerPlugins(app)
app.use(i18n).use(ToastPlugin)
app.mount('#app')
