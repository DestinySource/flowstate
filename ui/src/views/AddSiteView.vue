<script setup lang="ts">
import { useRouter } from "vue-router";
import { ref } from "vue";
import { useAuth } from "@/composables/useAuth.ts";

const router = useRouter()
const { apiFetch } = useAuth()

const sitename = ref('')
const errorMessage = ref('')
const isLoading = ref(false)

async function handleRegister() {
  if (isLoading.value) return;

  isLoading.value = true;
  errorMessage.value = '';

  try {
    const response = await apiFetch('https://localhost:8443/api/sites/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        sitename: sitename.value
      }),
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || `Server responded with status: ${response.status}`);
    }

    const data = await response.json();

    sitename.value = '';
    router.push({ name: 'dashboard' });

  } catch (error: any) {
    console.error('Registration failed:', error);
    errorMessage.value = error.message || 'Connection lost. Failed to initialize site.';
  } finally {
    isLoading.value = false;
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
          Add a new site to track.
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
          <label for="sitename" class="block text-[11px] font-bold tracking-wider text-neutral-500 uppercase mb-2">
            Sitename
          </label>
          <div class="relative flex items-center">
            <span class="absolute left-3 h-1 w-1 bg-cyan-400 rounded-full shadow-[0_0_6px_#00f0ff]"></span>
            <input
                id="sitename"
                v-model="sitename"
                type="text"
                required
                class="block w-full rounded border border-neutral-900/50 bg-neutral-900/20 py-2.5 pl-7 pr-3 text-xs text-white placeholder-neutral-700 outline-none transition-all duration-300 focus:border-pink-500/40 focus:bg-neutral-900/50 focus:ring-1 focus:ring-pink-500/10"
            />
          </div>

          <div class="pt-2">
            <button
                type="submit"
                :disabled="isLoading"
                class="relative flex w-full justify-center rounded border border-pink-500/20 bg-pink-500/10 py-2.5 text-xs font-bold tracking-widest text-pink-500 uppercase outline-none transition-all duration-200 hover:bg-pink-500/15 hover:border-pink-500/30 focus:ring-1 focus:ring-pink-500/30 disabled:opacity-40 disabled:cursor-not-allowed"
            >
              <span v-if="isLoading" class="animate-pulse tracking-widest">INITIALIZING_SESSION...</span>
              <span v-else class="flex items-center gap-1.5">
              Register Site
            </span>
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
