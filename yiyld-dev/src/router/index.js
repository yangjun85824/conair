import {createRouter, createWebHistory} from 'vue-router'
import index from '../views/index.vue'
import eipIndex from '@/views/eip-index'

const routes = [
    {
        path: '/',
        name: 'index',
        component: index
    },
    {
        path: '/tableDataList',
        name: 'tableDataList',
        component: () => import('../views/testlist.vue')
    },
    {
        path: '/eip-index',
        name: 'eipIndex',
        component: eipIndex
    },
    {
        path: '/eip-rest-index',
        name: 'eipRestIndex',
        component: () => import('../views/eip-rest-index.vue')
    },
    {
        path: '/javajx-index',
        name: 'javajx-index',
        component: () => import('../views/javajx-index.vue')
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
