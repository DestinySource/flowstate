import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DashboardView from "@/views/DashboardView.vue"
import LoginView from "@/views/LoginView.vue"
import RegisterView from "@/views/RegisterView.vue";
import { useAuth } from "@/composables/useAuth.ts"
import AddSiteView from "@/views/AddSiteView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        { path: '/', name: 'home', component: HomeView },
        { path: '/dashboard', name: 'dashboard', component: DashboardView, meta: { requiresAuth: true } },
        { path: '/login', name: 'login', component: LoginView, meta: { guestOnly: true } },
        { path: '/register', name: 'register', component: RegisterView, meta: { guestOnly: true } },
        { path: '/registersite', name: 'registerSite', component: AddSiteView, meta: { requiresAuth: true, adminOnly: true } }
    ],
})

router.beforeEach(async (to, from, next) => {
    const { isAuthenticated, tryRestoreSession, user } = useAuth()

    await tryRestoreSession()

    if (to.meta.requiresAuth && !isAuthenticated.value) {
        return next({ name: 'login' })
    }

    if (to.meta.guestOnly && isAuthenticated.value) {
        return next({ name: 'dashboard' })
    }

    if (to.meta.adminOnly && user.value?.role !== 'ADMIN') {
        return next({ name: 'dashboard' })
    }
    next()
})

export default router