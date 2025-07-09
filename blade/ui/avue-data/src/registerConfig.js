import 'virtual:svg-icons-register';
import SvgIcon from "./icons/index.vue";
import DataVVue3 from '@kjgl77/datav-vue3'
import JsonViewer from 'vue-json-viewer'
import 'vue-json-viewer/style.css'

import draggable from '@avue/avue-draggable'
import '@avue/avue-draggable/dist/style.css'
import highlight from '@/page/components/highlight.vue'
import {
  loadScript
} from '@/utils/utils'
import {
  website
} from '@/config.js'
import '@/styles/common.scss'
import '@/utils/es6'
import hljs from 'highlight.js' //导入代码高亮文件
import 'highlight.js/styles/atom-one-dark.css'

import error from './error';

function registerLibs(config, axios, router, app) {
  const $loadingParams = {};
  $loadingParams['element-loading-text'] = "加载中..."
  $loadingParams['element-loading-background'] = "rgba(32,32,35, 0.8)"
  window.axios = axios;
  window.$loadScript = loadScript;
  document.title = website.title
  app.directive('highlight', function (el) {
    let highlight = el.querySelectorAll('pre code');
    highlight.forEach((block) => {
      hljs.highlightBlock(block)
    })
  });
  app.config.globalProperties.$component = app.component
  app.config.globalProperties.$website = config
  app.config.globalProperties.$loadingParams = $loadingParams
  app.use(router);
  app.use(DataVVue3)
  app.use(error);
  app.use(JsonViewer)
  app.use(draggable)
  app.component('avue-highlight', highlight)
  app.component("svg-icon", SvgIcon)
}


function registerRouters(config, router) {
  let mainPath = config.routers.mainPath
  if (!router) return
  const routers = [{
    path: mainPath,
    component: () => import('@/page/index.vue'),
    children: [{
        path: '',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/index.vue')
      },
      {
        path: 'category',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/category.vue'),
      }, {
        path: 'db',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/db.vue'),
      },
      {
        path: 'map',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/map.vue')
      }, {
        path: 'document',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/document.vue')
      },
      {
        path: 'glob',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/glob.vue')
      }, {
        path: 'components',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/components.vue')

      }, {
        path: 'file',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/file.vue')
      }, {
        path: 'task',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/task.vue')
      }, {
        path: 'record',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/record.vue')
      }, {
        path: 'chatgpt',
        component: () => import( /* webpackChunkName: "list" */ '@/page/list/chatgpt.vue')
      }
    ]
  }, {
    path: mainPath + 'build',
    name: 'build',
    component: () => import( /* webpackChunkName: "build" */ '@/page/build.vue')
  }, {
    path: mainPath + 'build/:id',
    name: 'build',
    component: () => import( /* webpackChunkName: "build" */ '@/page/build.vue')
  }, {
    path: mainPath + 'view/:id',
    name: 'view',
    component: () => import( /* webpackChunkName: "build" */ '@/page/view.vue')
  }]
  if (router.addRoutes) {
    router.addRoutes(routers)
  } else {
    routers.forEach((route) => {
      router.addRoute(route)
    })
  }
}
export default function ({
  app,
  config,
  router,
  axios
}) {
  Object.assign(window.$website, config)
  if (!window.$website.routers) window.$website.routers = {}
  window.$website.routers.mainPath = window.$website.routers.mainPath || '/'
  registerRouters(window.$website, router)
  registerLibs(window.$website, axios, router, app)
}