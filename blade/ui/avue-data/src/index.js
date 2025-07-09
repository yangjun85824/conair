import AvueData from '@/page/view.vue';
import registerConfig from '@/registerConfig';

const install = (Vue, ops) => {
  Vue.component('avue-data', AvueData)
}

// 如果是直接引入的
if (typeof window !== 'undefined' && window.Vue) {
  install(window.Vue)
}

export default {
  install,
  registerConfig
}

export {
  install,
  registerConfig
}