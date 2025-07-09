import {
  createApp
} from 'vue'
import axios from './axios';
import router from './router/';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import Avue from '@smallwei/avue';

import '@smallwei/avue/lib/index.css';
import App from './App.vue'
import {
  registerConfig
} from './index'
const app = createApp(App)

app.use(ElementPlus, {
  locale: zhCn
})
app.use(Avue, {
  axios
})
registerConfig({
  app,
  config: {
    keys: '联系Avue客服获取'
  },
  router,
  axios
})
app.mount('#app')