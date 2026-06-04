<script setup lang="ts">
interface DataItem {
  label: string
  value: number
  percentage: number
}

defineProps<{
  title: string
  items: DataItem[]
}>()
</script>

<template>
  <section class="flex flex-col overflow-hidden rounded-[4px] border-[0.5px] border-[#353439] bg-[#1b1b1f] shadow-sm">
    <header class="flex items-center justify-between border-b border-white/5 px-5 py-4">
      <h3 class="font-['Space_Grotesk'] text-[12px] tracking-[0.1em] text-[#888] uppercase">
        {{ title }}
      </h3>
      <span class="text-[11px] text-[#666]">
        Visitors <span class="inline-block">↑↓</span>
      </span>
    </header>

    <div class="flex-grow py-3">
      <div
          v-for="(item, index) in items"
          :key="index"
          class="relative px-3 py-[2px]"
      >
        <svg class="block" width="100%" height="36">
          <rect
              :width="`${item.percentage}%`"
              height="100%"
              rx="4"
              :class="[
              'transition-[width] duration-1000 ease-out',
              index === 0 ? 'fill-[#ffb1c0]/30' : 'fill-[#ffb1c0]/15'
            ]"
          />

          <foreignObject x="0" y="0" width="100%" height="100%">
            <div class="flex h-full items-center justify-between px-3 font-['Inter'] text-[14px] text-[#e4e1e7]">
              <span class="max-w-[80%] truncate whitespace-nowrap">
                {{ item.label }}
              </span>
              <span class="font-['Space_Grotesk'] font-medium">
                {{ item.value }}
              </span>
            </div>
          </foreignObject>
        </svg>
      </div>
    </div>

    <footer class="flex justify-center border-t border-white/5 p-3">
      <button class="flex items-center gap-2 border-none bg-transparent font-['Space_Grotesk'] text-[11px] font-bold text-[#888] transition-colors duration-200 hover:text-[#ffb1c0]">
        <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 3h6v6M9 21H3v-6M21 3l-7 7M3 21l7-7" />
        </svg>
        DETAILS
      </button>
    </footer>
  </section>
</template>