import {createRouter, createWebHistory} from 'vue-router'
import index from '../views/index.vue'
import dynjavaindex from '../views/dynjava/index'

const routes = [
    {
        path: '/',
        name: 'index',
        component: index
    },{
        path: '/dynjavaindex',
        name: 'dynjavaindex',
        component: dynjavaindex
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
