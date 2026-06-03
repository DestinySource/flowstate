import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DashboardView from "@/views/DashboardView.vue";
import LoginView from "@/views/LoginView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView,
        },
        {
            path: '/dashboard',
            name: 'dashboard',
            component: DashboardView,
            meta: { requiresAuth: true }
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { guestOnly: true }
        }
    ],
})

router.beforeEach((to, from, next) => {
    const isAuthenticated = sessionStorage.getItem("fs_token")

    if (to.meta.requiresAuth && !isAuthenticated) {
        return next({ name: 'login'})
    }

    if (to.meta.guestOnly && isAuthenticated){
        return next({ name: 'dashboard'})
    }

    next()
})
export default router
