// Utilities
import {createPinia} from 'pinia'
import {watch} from "vue";
import piniaPersist from "pinia-plugin-persist";

const pinia = createPinia()
pinia.use(piniaPersist)

export default pinia
