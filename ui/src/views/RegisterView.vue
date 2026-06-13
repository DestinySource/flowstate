<script setup lang="ts">
import { ref } from 'vue'
import { useAuth } from '@/composables/useAuth'
import { useRouter } from 'vue-router'

const { register } = useAuth()
const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const errorMessage = ref('')
const isLoading = ref(false)

async function handleRegister() {
  if (!email.value || !password.value) {
    errorMessage.value = 'Please fill in all fields'
    return
  }

  isLoading.value = true
  errorMessage.value = ''

  try {
    await register({
      username: username.value,
      email: email.value,
      password: password.value
    })

    await router.push('/login')
  } catch (err: any) {
    errorMessage.value = err.message || 'Authentication issue'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="flex min-h-screen w-screen items-center justify-center bg-bg px-4 font-mono antialiased text-neutral-200">
    <div class="w-full max-w-md rounded-lg border border-neutral-800 bg-neutral-950 p-8 shadow-xl relative overflow-hidden">

      <div class="absolute top-0 inset-x-0 h-0.5 bg-linear-to-r from-transparent via-pink-500/40 to-transparent"></div>

      <div class="mb-8 text-center flex flex-col gap-1">
        <h2 class="text-3xl font-black tracking-widest text-white uppercase">
          Flowstate
        </h2>
        <p class="text-s font-black tracking-widest text-neutral-400 uppercase">
         Register an new account.
        </p>
      </div>

      <div
          v-if="errorMessage"
          class="mb-6 bg-pink-500/5 border border-pink-500/20 text-pink-500 p-3 rounded text-xs text-center uppercase tracking-wide font-bold"
      >
        !! {{ errorMessage }} !!
      </div>

      <form class="space-y-5" @submit.prevent="handleRegister">
        <div>
        <label for="username" class="block text-[11px] font-bold tracking-wider text-neutral-500 uppercase mb-2">
          Username
        </label>
        <div class="relative flex items-center">
          <span class="absolute left-3 h-1 w-1 bg-cyan-400 rounded-full shadow-[0_0_6px_#00f0ff]"></span>
          <input
              id="username"
              v-model="username"
              type="text"
              required
              class="block w-full rounded border border-neutral-900/50 bg-neutral-900/20 py-2.5 pl-7 pr-3 text-xs text-white placeholder-neutral-700 outline-none transition-all duration-300 focus:border-pink-500/40 focus:bg-neutral-900/50 focus:ring-1 focus:ring-pink-500/10"
              placeholder="Username"
          />
        </div>

        <div>
          <label for="email-address" class="block text-[11px] font-bold tracking-wider text-neutral-500 uppercase mb-2">
            Email
          </label>
          <div class="relative flex items-center">
            <span class="absolute left-3 h-1 w-1 bg-cyan-400 rounded-full shadow-[0_0_6px_#00f0ff]"></span>
            <input
                id="email-address"
                v-model="email"
                type="email"
                required
                class="block w-full rounded border border-neutral-900/50 bg-neutral-900/20 py-2.5 pl-7 pr-3 text-xs text-white placeholder-neutral-700 outline-none transition-all duration-300 focus:border-pink-500/40 focus:bg-neutral-900/50 focus:ring-1 focus:ring-pink-500/10"
                placeholder="email@address.com"
            />
          </div>
        </div>

        <div>
          <label for="password" class="block text-[11px] font-bold tracking-wider text-neutral-500 uppercase mb-2">
            Password
          </label>
          <div class="relative flex items-center">
            <span class="absolute left-3 h-1 w-1 bg-cyan-400 rounded-full shadow-[0_0_6px_#00f0ff]"></span>
            <input
                id="password"
                v-model="password"
                type="password"
                required
                class="block w-full rounded border border-neutral-900/50 bg-neutral-900/20 py-2.5 pl-7 pr-3 text-xs text-white placeholder-neutral-700 outline-none transition-all duration-300 focus:border-pink-500/40 focus:bg-neutral-900/50 focus:ring-1 focus:ring-pink-500/10"
                placeholder="••••••••"
            />
          </div>
        </div>

        <div class="pt-2">
          <button
              type="submit"
              :disabled="isLoading"
              class="relative flex w-full justify-center rounded border border-pink-500/20 bg-pink-500/10 py-2.5 text-xs font-bold tracking-widest text-pink-500 uppercase outline-none transition-all duration-200 hover:bg-pink-500/15 hover:border-pink-500/30 focus:ring-1 focus:ring-pink-500/30 disabled:opacity-40 disabled:cursor-not-allowed"
          >
            <span v-if="isLoading" class="animate-pulse tracking-widest">INITIALIZING_SESSION...</span>
            <span v-else class="flex items-center gap-1.5">
              Login
            </span>
          </button>
        </div>
        </div>
      </form>

      <div class="mt-8 pt-4 border-t border-neutral-900 text-center">
        <span class="text-[10px] tracking-widest text-neutral-600 uppercase font-medium">
          FLOWSTATE SYSTEM ARCHITECTURE v1.1.0
        </span>
      </div>

    </div>
  </div>
</template>