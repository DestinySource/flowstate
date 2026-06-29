<script setup lang="ts">

import Navbar from "@/components/Navbar.vue";
</script>

<template>
  <div class="relative min-h-screen w-full overflow-x-hidden bg-[#0a0f1d] text-white font-sans antialiased">

    <div class="absolute inset-0 overflow-hidden pointer-events-none z-0">
      <div class="absolute top-[-10%] left-[-10%] h-150 w-150 rounded-full bg-linear-to-r from-purple-600/30 to-pink-500/20 blur-[120px] animate-pulse duration-[8s]"></div>
      <div class="absolute top-[40%] right-[-5%] h-125 w-125 rounded-full bg-linear-to-tr from-blue-600/25 to-cyan-400/20 blur-[100px] animate-bounce duration-[12s] opacity-70"></div>
      <div class="absolute bottom-[-10%] left-[20%] h-137.5 w-137.5 rounded-full bg-linear-to-r from-emerald-500/20 to-indigo-600/20 blur-[130px]"></div>
    </div>

    <Navbar></Navbar>

    <section class="relative z-10 mx-auto max-w-7xl px-6 pt-32 pb-20 md:pt-48 flex flex-col items-center text-center">
      <div class="inline-flex items-center gap-2 rounded-full bg-white/5 border border-white/15 px-4 py-1.5 text-xs font-medium text-purple-200 backdrop-blur-sm mb-6 shadow-sm">
        <span class="flex h-2 w-2 rounded-full bg-purple-400 animate-ping"></span>
        Introducing Liquid Glass v2.0
      </div>

      <h1 class="max-w-4xl text-4xl font-extrabold tracking-tight sm:text-6xl md:text-7xl bg-clip-text text-transparent bg-linear-to-b from-white via-white to-slate-400">
        Interfaces that flow like <span class="text-transparent bg-clip-text bg-linear-to-r from-cyan-400 via-pink-400 to-purple-400">Liquid.</span>
      </h1>

      <p class="mt-6 max-w-2xl text-lg text-slate-400 md:text-xl">
        A high-fidelity landing page framework leveraging fluid gradients, frosted panels, and deep refraction to make your UI come alive.
      </p>

      <div class="mt-10 flex flex-wrap items-center justify-center gap-4">
        <router-link
            to="/dashboard"
            class="rounded-xl bg-white text-slate-950 px-6 py-3 font-semibold text-sm shadow-xl hover:bg-slate-100 transition-all transform hover:-translate-y-0.5"
        >
          Get Started Free
        </router-link>
        <button class="rounded-xl bg-white/5 border border-white/10 px-6 py-3 font-semibold text-sm backdrop-blur-md hover:bg-white/10 transition-all transform hover:-translate-y-0.5">
          Live Demo
        </button>
      </div>
    </section>

    <section class="relative z-10 mx-auto max-w-7xl px-6 py-12">
      <div class="grid gap-8 md:grid-cols-3">

        <div class="group relative rounded-2xl bg-white/3 border border-white/10 p-8 backdrop-blur-xl transition-all duration-300 hover:border-white/20 hover:bg-white/6 hover:shadow-[0_20px_50px_rgba(0,0,0,0.3)]">
          <div class="absolute -inset-px rounded-2xl bg-linear-to-tr from-cyan-500/20 to-transparent opacity-0 transition-opacity duration-500 group-hover:opacity-100 pointer-events-none"></div>
          <div class="h-12 w-12 rounded-xl bg-cyan-500/10 border border-cyan-500/20 flex items-center justify-center mb-6 text-cyan-400">
            ⚡
          </div>
          <h3 class="text-xl font-bold text-white mb-2">Refractive Depth</h3>
          <p class="text-slate-400 text-sm leading-relaxed">
            Layers stack seamlessly using varied backdrop blurs and micro-borders to mimic physical curved glass.
          </p>
        </div>

        <div class="group relative rounded-2xl bg-white/3 border border-white/10 p-8 backdrop-blur-xl transition-all duration-300 hover:border-white/20 hover:bg-white/6 hover:shadow-[0_20px_50px_rgba(0,0,0,0.3)]">
          <div class="absolute -inset-px rounded-2xl bg-linear-to-tr from-pink-500/20 to-transparent opacity-0 transition-opacity duration-500 group-hover:opacity-100 pointer-events-none"></div>
          <div class="h-12 w-12 rounded-xl bg-pink-500/10 border border-pink-500/20 flex items-center justify-center mb-6 text-pink-400">
            💧
          </div>
          <h3 class="text-xl font-bold text-white mb-2">Fluid Dynamics</h3>
          <p class="text-slate-400 text-sm leading-relaxed">
            Amorphous background elements shift and pulse, catching light through the transparent containers.
          </p>
        </div>

        <div class="group relative rounded-2xl bg-white/3 border border-white/10 p-8 backdrop-blur-xl transition-all duration-300 hover:border-white/20 hover:bg-white/6 hover:shadow-[0_20px_50px_rgba(0,0,0,0.3)]">
          <div class="absolute -inset-px rounded-2xl bg-linear-to-tr from-purple-500/20 to-transparent opacity-0 transition-opacity duration-500 group-hover:opacity-100 pointer-events-none"></div>
          <div class="h-12 w-12 rounded-xl bg-purple-500/10 border border-purple-500/20 flex items-center justify-center mb-6 text-purple-400">
            🎨
          </div>
          <h3 class="text-xl font-bold text-white mb-2">Pure Customization</h3>
          <p class="text-slate-400 text-sm leading-relaxed">
            Easily modify Tailwind tint percentages and blur radii to shift from heavy frost to water-slick glass.
          </p>
        </div>

      </div>
    </section>

  </div>
</template>

<style scoped>
/* Tailored animations to enhance the fluid background flow */
@keyframes pulse {
  0%, 100% { transform: scale(1) translate(0px, 0px); opacity: 0.6; }
  50% { transform: scale(1.1) translate(30px, -20px); opacity: 0.8; }
}
@keyframes bounce {
  0%, 100% { transform: translate(0px, 0px); }
  50% { transform: translate(-40px, 40px); }
}
.animate-pulse {
  animation: pulse 12s infinite ease-in-out;
}
.animate-bounce {
  animation: bounce 16s infinite ease-in-out;
}
</style>