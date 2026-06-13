import { ref, computed } from 'vue'

export interface UserProfile {
    id: number;
    username: string;
    role: string;
}

const user = ref<UserProfile | null>(null)
let tokenInMemory: string | null = null

export function useAuth() {
    const isAuthenticated = computed(() => !!user.value)

    async function apiFetch(url: string, options: RequestInit = {}) {
        const incomingHeaders = (options.headers as Record<string, string>) || {}

        const headers: Record<string, string> = {
            'Content-Type': 'application/json',
            ...incomingHeaders,
        }

        if (tokenInMemory) {
            headers['Authorization'] = `Bearer ${tokenInMemory}`
        }

        return fetch(url, { ...options, headers })
    }

    async function login(credentials: Record<string, string>) {
        try {
            const response = await fetch('https://localhost:8443/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials),
            })

            if (!response.ok) {
                throw new Error('ACCESS_DENIED: Ongeldige inloggegevens.')
            }

            const data: { id: number; username: string; role: string; accessToken: string } = await response.json()

            tokenInMemory = data.accessToken

            user.value = {
                id: data.id,
                username: data.username,
                role: data.role
            }

            sessionStorage.setItem('fs_token', data.accessToken)
            sessionStorage.setItem('fs_user', JSON.stringify(user.value))

        } catch (error) {
            console.error('Auth Error:', error)
            throw error
        }
    }

    async function register(credentials: Record<string, string>) {
        try {
            const response = await fetch('https://localhost:8443/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials),
            })

            if (!response.ok) {
                throw new Error('ACCESS_DENIED: Ongeldige inloggegevens.')
            }

            const { username, ...loginPayload } = credentials;
            await login(loginPayload);
        } catch (error) {
            console.error('Auth Error:', error)
            throw error
        }
    }

    function tryRestoreSession() {
        const savedToken = sessionStorage.getItem('fs_token')
        const savedUser = sessionStorage.getItem('fs_user')

        if (savedToken && savedUser) {
            tokenInMemory = savedToken
            user.value = JSON.parse(savedUser)
        }
    }

    function logout() {
        tokenInMemory = null
        user.value = null
        sessionStorage.removeItem('fs_token')
        sessionStorage.removeItem('fs_user')
    }

    return {
        user,
        isAuthenticated,
        login,
        register,
        logout,
        tryRestoreSession,
        apiFetch
    }
}