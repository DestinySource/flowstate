<script setup lang="ts">
import {ref} from "vue";

const activeItem = ref('Home')
const navItems = ['Home', 'Features', 'Pricing', 'Contact']
</script>
<template>
  <nav class="fixed top-4 left-1/2 z-50 flex w-[90%] -translate-x-1/2 items-center justify-between rounded-full bg-white/5 px-6 py-3 shadow-[0_8px_32px_0_rgba(0,0,0,0.37)] backdrop-blur-md border border-white/10">
    <div class="text-xl font-black tracking-wider bg-clip-text text-transparent bg-linear-to-r from-white via-slate-200 to-slate-400">
      dit moet nog in een navbar
    </div>

    <div class="hidden items-center space-x-2 md:flex">
      <a
          v-for="item in navItems"
          :key="item"
          href="#"
          @click.prevent="activeItem = item"
          class="group relative px-5 py-2 text-sm font-medium transition-colors duration-300"
          :class="activeItem === item ? 'text-white' : 'text-white/70 hover:text-white'"
      >
        <div
            class="absolute inset-0 -z-10 rounded-full bg-white/10 border border-white/15 backdrop-blur-sm shadow-[0_4px_12px_rgba(255,255,255,0.03)] transition-all duration-300 ease-out"
            :class="[
            activeItem === item
              ? 'opacity-100 scale-100'
              : 'opacity-0 scale-75 group-hover:opacity-100 group-hover:scale-100'
          ]"
        ></div>

        {{ item }}
      </a>
    </div>

    <button class="rounded-full bg-white/10 px-5 py-2 text-sm font-medium text-white transition-all hover:bg-white/20 border border-white/20 hover:shadow-[0_0_15px_rgba(255,255,255,0.1)]">
      Sign Up
    </button>
  </nav>
</template>