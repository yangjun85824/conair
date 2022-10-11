import {createApp} from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import api from './request/api'
// import LeftTree from './components/LeftTree.vue'
// import TableColumn from './components/TableColumn.vue'

const app = createApp(App);

// 将各模块api挂载在vue实例上
app.config.globalProperties.$api = api // 自定义添加

app.use(ElementPlus,{
    locale: zhCn
}).use(store).use(router).use("api",api)
    // .component("LeftTree",LeftTree)//.component('TableColumn',TableColumn)
    .mount('#app')


