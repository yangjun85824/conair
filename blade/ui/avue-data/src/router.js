import {
  createRouter,
  createWebHistory
} from 'vue-router';
const vueRouter = createRouter({
  base: import.meta.env.VITE_APP_BASE,
  history: createWebHistory(),
  routes: []
})
export default vueRouter;