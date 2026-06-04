<script setup lang="ts">
import { ref, computed, watch } from 'vue'

interface AnalyticsItem {
  name: string
  uv: number
}

interface Props {
  id: string
  tabs: string[]
  cardData: Record<string, AnalyticsItem[]>
}

const props = defineProps<Props>()

const activeTab = ref<string>(props.tabs[0] || '')
const isDescending = ref(true)

// Fallback safety if tabs change dynamically
watch(() => props.tabs, (newTabs) => {
  if (newTabs.length && !newTabs.includes(activeTab.value)) {
    activeTab.value = newTabs[0]
  }
}, { deep: true })

const processedData = computed(() => {

  if (activeTab.value === 'host') {
    const rawHostValue = props.cardData?.host
    if (!rawHostValue) return []

    return [{
      name: String(rawHostValue),
      uv: 1,
      barWidth: 100
    }]
  }

  const rawGroup = props.cardData[activeTab.value]
  if (!rawGroup || !rawGroup.length) return []

  const items = [...rawGroup]
  items.sort((a, b) => isDescending.value ? b.uv - a.uv : a.uv - b.uv)

  const maxUv = Math.max(...items.map(i => i.uv), 1)

  return items.map(item => ({
    ...item,
    barWidth: `${(item.uv / maxUv) * 100}%`
  }))
})
</script>

<template>
  <section class="flex flex-col rounded-lg border border-neutral-800 bg-neutral-950 p-6 font-mono text-neutral-200 shadow-xl">
    <div class="mb-6 flex flex-col gap-3">
      <h2 class="text-xs font-black tracking-widest text-neutral-400 uppercase">
        {{ id.replace(/[-_]/g, ' ') }}
      </h2>

      <div class="flex items-center justify-between border-b border-neutral-900 pb-3">
        <div role="tablist" class="flex gap-3">
          <button
              v-for="tab in tabs"
              :key="tab"
              role="tab"
              @click="activeTab = tab"
              :class="[
              'px-2 py-1 text-xs uppercase tracking-wider rounded transition-all',
              activeTab === tab
                ? 'bg-pink-500/10 text-pink-500 font-bold border border-pink-500/20'
                : 'text-neutral-500 hover:text-neutral-300'
            ]"
          >
            {{ tab }}
          </button>
        </div>

        <div class="flex items-center gap-2">
          <span class="text-[11px] uppercase tracking-wider text-neutral-500">
            Unique Views
          </span>
          <button
              @click="isDescending = !isDescending"
              class="rounded p-1 text-neutral-500 hover:bg-neutral-900 hover:text-white transition-colors"
              title="Toggle Sort Order"
          >
            <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 16 16"
                fill="currentColor"
                :class="['h-4 w-4 transition-transform duration-200', { 'rotate-180': !isDescending }]"
            >
              <path fill-rule="evenodd" d="M13.78 10.47a.75.75 0 0 1 0 1.06l-2.25 2.25a.75.75 0 0 1-1.06 0l-2.25-2.25a.75.75 0 1 1 1.06-1.06l.97.97V5.75a.75.75 0 0 1 1.5 0v5.69l.97-.97a.75.75 0 0 1 1.06 0ZM2.22 5.53a.75.75 0 0 1 0-1.06l2.25-2.25a.75.75 0 0 1-1.06 0l2.25 2.25a.75.75 0 0 1-1.06 1.06l-.97-.97v5.69a.75.75 0 0 1-1.5 0V4.56l-.97.97a.75.75 0 0 1-1.06 0Z" clip-rule="evenodd"></path>
            </svg>
          </button>
        </div>
      </div>
    </div>

    <div class="flex flex-col gap-2">
      <div
          v-for="item in processedData"
          :key="item.name"
          class="group relative flex h-8 items-center justify-between overflow-hidden rounded border border-neutral-900/50 bg-neutral-900/20 px-3"
      >
        <div
            class="absolute inset-y-0 left-0 border-r border-pink-500/30 bg-gradient-to-r from-pink-500/0 via-pink-500/5 to-pink-500/10 transition-all duration-500 ease-out"
            :style="{ width: item.barWidth }"
        ></div>

        <div class="z-10 flex w-full items-center justify-between gap-4">
          <div class="flex items-center gap-2 min-w-0">
            <span v-if="activeTab === 'pages'" class="text-pink-500 font-bold select-none text-xs">/</span>
            <span v-else class="h-1 w-1 bg-cyan-400 rounded-full shadow-[0_0_6px_#00f0ff]"></span>

            <span class="truncate text-xs text-neutral-300 font-medium" :title="item.name">
              {{ item.name === '/' ? 'index' : item.name.replace(/^\//, '') }}
            </span>
          </div>

          <span class="text-xs font-bold text-white bg-neutral-900 px-1.5 py-0.5 rounded border border-neutral-800">
            {{ item.uv.toLocaleString() }}
          </span>
        </div>
      </div>

      <div v-if="!processedData.length" class="flex flex-col items-center justify-center py-8 border border-dashed border-neutral-900 rounded">
        <span class="text-[11px] tracking-widest text-neutral-600 uppercase">
          No Data available
        </span>
      </div>
    </div>
  </section>
</template>