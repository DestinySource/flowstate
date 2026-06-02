<script setup lang="ts">
import { ref } from 'vue'
import { useAuth } from '@/composables/useAuth'
import { useRouter } from 'vue-router'

const { login } = useAuth()
const router = useRouter()

const email = ref('')
const password = ref('')
const errorMessage = ref('')
const isLoading = ref(false)

async function handleLogin() {
  if (!email.value || !password.value) {
    errorMessage.value = 'Vul alstublieft alle velden in.'
    return
  }

  isLoading.value = true
  errorMessage.value = ''

  try {
    await login({
      email: email.value,
      password: password.value
    })

    router.push('/dashboard')
  } catch (err) {
    errorMessage.value = err.message || 'Er is iets misgegaan.'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <div class="flex min-h-screen items-center justify-center bg-gray-900 px-4 py-12 sm:px-6 lg:px-8">
    <div class="w-full max-w-md space-y-8 bg-gray-800 p-8 rounded-xl shadow-lg border border-gray-700">
      <div>
        <h2 class="mt-6 text-center text-3xl font-bold tracking-tight text-white">
          Flowstate
        </h2>
        <p class="mt-2 text-center text-sm text-gray-400">
          Log in op jouw account
        </p>
      </div>

      <div v-if="errorMessage" class="bg-red-500/10 border border-red-500 text-red-500 p-3 rounded-lg text-sm text-center">
        {{ errorMessage }}
      </div>

      <form class="mt-8 space-y-6" @submit.prevent="handleLogin">
        <div class="space-y-4 rounded-md shadow-sm">
          <div>
            <label for="email-address" class="block text-sm font-medium text-gray-300 mb-1">E-mailadres</label>
            <input
                id="email-address"
                v-model="email"
                type="email"
                required
                class="relative block w-full rounded-lg border-0 bg-gray-700 py-2.5 px-3 text-white placeholder-gray-400 ring-1 ring-inset ring-gray-600 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                placeholder="naam@voorbeeld.com"
            />
          </div>
          <div>
            <label for="password" class="block text-sm font-medium text-gray-300 mb-1">Wachtwoord</label>
            <input
                id="password"
                v-model="password"
                type="password"
                required
                class="relative block w-full rounded-lg border-0 bg-gray-700 py-2.5 px-3 text-white placeholder-gray-400 ring-1 ring-inset ring-gray-600 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                placeholder="••••••••"
            />
          </div>
        </div>

        <div>
          <button
              type="submit"
              :disabled="isLoading"
              class="group relative flex w-full justify-center rounded-lg bg-indigo-600 py-2.5 px-4 text-sm font-semibold text-white hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 transition-colors duration-200 disabled:opacity-50"
          >
            <span v-if="isLoading">Laden...</span>
            <span v-else>Inloggen</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>