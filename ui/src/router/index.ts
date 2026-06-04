import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DashboardView from "@/views/DashboardView.vue"
import LoginView from "@/views/LoginView.vue"
import RegisterView from "@/views/RegisterView.vue";
import { useAuth } from "@/composables/useAuth.ts"

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        { path: '/', name: 'home', component: HomeView },
        { path: '/dashboard', name: 'dashboard', component: DashboardView, meta: { requiresAuth: true } },
        { path: '/login', name: 'login', component: LoginView, meta: { guestOnly: true } },
        { path: '/register', name: 'register', component: RegisterView, meta: { guestOnly: true } }
    ],
})

router.beforeEach(async (to, from, next) => {
    const { isAuthenticated, tryRestoreSession } = useAuth()

    await tryRestoreSession()

    if (to.meta.requiresAuth && !isAuthenticated.value) {
        return next({ name: 'login' })
    }

    if (to.meta.guestOnly && isAuthenticated.value) {
        return next({ name: 'dashboard' })
    }

    next()
})

export default router