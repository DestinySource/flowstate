<script setup lang="ts">
import {ref, computed, watch} from 'vue'

interface AnalyticsItem {
  name: string
  uv: number
  revenue: number
  paymentCount: number
}

interface Props {
  id: string
  tabs: string[]
  cardData: Record<string, AnalyticsItem[]>
  defaultCutoff?: string
}

const props = withDefaults(defineProps<Props>(), {
  defaultCutoff: '7days'
})

const emit = defineEmits<{
  (e: 'update:cutoff', value: string): void
}>()

const activeTab = ref<string>(props.tabs[0] || '')
const isDescending = ref(true)
const selectedCutoff = ref<string>(props.defaultCutoff)

watch(() => props.tabs, (newTabs) => {
      if (newTabs.length && !newTabs.includes(activeTab.value)) {
        activeTab.value = newTabs[0]
      }
    }, {deep: true}
)

const handleCutoffChange = () => {
  emit('update:cutoff', selectedCutoff.value)
}

const processedData = computed(() => {
  const rawGroup = props.cardData[activeTab.value]
  if (!rawGroup || !rawGroup.length) return []

  const items = [...rawGroup]
  items.sort((a, b) => isDescending.value ? b.uv - a.uv : a.uv - b.uv)
  const maxUv = Math.max(...items.map(i => i.uv), 1)

  return items.map(item => ({
    ...item,
    barWidth: (item.uv / maxUv) * 100
  }))
})
</script>

<template>
  <section class="stitch-card stitch-card-glow" :id="id">
    <div class="analytics-card-header">
      <div role="tablist" class="analytics-tabs">
        <button
            v-for="tab in tabs"
            :key="tab"
            role="tab"
            @click="activeTab = tab"
            :class="['analytics-tab-btn', { 'is-active': activeTab === tab }]"
        >
          {{ tab }}
        </button>
      </div>

      <div class="analytics-controls">
        <div class="analytics-sort-box">
          <span class="label-caps">Unique Views</span>
          <button @click="isDescending = !isDescending" class="sort-trigger-btn" title="Sorteervolgorde aanpassen">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" :class="['sort-icon-svg', { 'rotate-180': !isDescending }]">
              <path fill-rule="evenodd" d="M13.78 10.47a.75.75 0 0 1 0 1.06l-2.25 2.25a.75.75 0 0 1-1.06 0l-2.25-2.25a.75.75 0 1 1 1.06-1.06l.97.97V5.75a.75.75 0 0 1 1.5 0v5.69l.97-.97a.75.75 0 0 1 1.06 0ZM2.22 5.53a.75.75 0 0 1 0-1.06l2.25-2.25a.75.75 0 0 1-1.06 0l2.25 2.25a.75.75 0 0 1-1.06 1.06l-.97-.97v5.69a.75.75 0 0 1-1.5 0V4.56l-.97.97a.75.75 0 0 1-1.06 0Z" clip-rule="evenodd"></path>
            </svg>
          </button>
        </div>
      </div>
    </div>

    <div class="analytics-card-body">
      <div
          v-for="item in processedData"
          :key="item.name"
          class="analytics-data-row"
      >
        <div
            class="analytics-bar-fill"
            :style="{ width: `${item.barWidth}%` }"
        ></div>

        <div class="analytics-row-content">
          <div class="analytics-row-left">
            <span v-if="activeTab === 'pages'" class="analytics-path-prefix">/</span>
            <span v-else class="analytics-status-dot"></span>
            <span class="analytics-data-name" :title="item.name">{{ item.name }}</span>
          </div>

          <span class="analytics-mono-output">
            {{ item.uv.toLocaleString() }}
          </span>
        </div>
      </div>

      <div v-if="!processedData.length" class="analytics-empty-state">
        <span class="label-caps opacity-40">NO_DATA_AVAILABLE_FOR_NODE</span>
      </div>
    </div>
  </section>
</template>