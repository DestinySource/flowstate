<script setup lang="ts">
import { ref, computed } from 'vue'

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
}

const props = defineProps<Props>()
const activeTab = ref<string>(props.tabs[0] || '')
const isDescending = ref(true)

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
  <section class="motion-v-card" :id="id">
    <div class="card-header">
      <div role="tablist" class="tabs-container">
        <button
            v-for="tab in tabs"
            :key="tab"
            role="tab"
            @click="activeTab = tab"
            :class="['tab-btn', activeTab === tab ? 'tab-active' : 'tab-inactive']"
        >
          {{ tab }}
        </button>
      </div>

      <div class="sort-container">
        <span class="label-caps">Unique Views</span>
        <button @click="isDescending = !isDescending" class="sort-btn" title="Sorteervolgorde aanpassen">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" :class="['sort-icon', isDescending ? 'rotate-0' : 'rotate-180']">
            <path fill-rule="evenodd" d="M13.78 10.47a.75.75 0 0 1 0 1.06l-2.25 2.25a.75.75 0 0 1-1.06 0l-2.25-2.25a.75.75 0 1 1 1.06-1.06l.97.97V5.75a.75.75 0 0 1 1.5 0v5.69l.97-.97a.75.75 0 0 1 1.06 0ZM2.22 5.53a.75.75 0 0 1 0-1.06l2.25-2.25a.75.75 0 0 1-1.06 0l2.25 2.25a.75.75 0 0 1-1.06 1.06l-.97-.97v5.69a.75.75 0 0 1-1.5 0V4.56l-.97.97a.75.75 0 0 1-1.06 0Z" clip-rule="evenodd"></path>
          </svg>
        </button>
      </div>
    </div>

    <div class="card-body">
      <div
          v-for="item in processedData"
          :key="item.name"
          class="data-row"
      >
        <div
            class="data-bar-fill"
            :style="{ width: `${item.barWidth}%` }"
        ></div>

        <div class="data-row-content">
          <div class="data-left">
            <span v-if="activeTab === 'pages'" class="path-prefix">/</span>
            <span v-else class="status-dot"></span>
            <span class="data-name" :title="item.name">{{ item.name }}</span>
          </div>

          <span class="mono-data">
            {{ item.uv.toLocaleString() }}
          </span>
        </div>
      </div>

      <div v-if="!processedData.length" class="empty-state">
        <span class="label-caps opacity-40">NO_DATA_AVAILABLE_FOR_NODE</span>
      </div>
    </div>
  </section>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap');

.motion-v-card {
  background: rgba(22, 22, 26, 0.8);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border: 1px solid #2a2a30;
  border-radius: 4px;
  overflow: hidden;
  font-family: 'Inter', sans-serif;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 24px;
  border-b: 1px solid #2a2a30;
  background: rgba(14, 14, 18, 0.4);
}

.tabs-container {
  display: flex;
  gap: 8px;
}

.tab-btn {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 14px;
  font-weight: 500;
  text-transform: capitalize;
  background: transparent;
  border: none;
  padding: 6px 16px;
  cursor: pointer;
  color: #94949e;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.tab-active {
  color: #ffffff;
  background: rgba(255, 45, 120, 0.15);
  border: 1px solid rgba(255, 45, 120, 0.4);
  box-shadow: 0 0 10px rgba(255, 45, 120, 0.1);
}

.tab-btn:hover:not(.tab-active) {
  color: #e4e1e7;
  background: rgba(255, 255, 255, 0.05);
}

.sort-container {
  display: flex;
  align-items: center;
  gap: 8px;
}

.label-caps {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  color: #94949e;
}

.sort-btn {
  background: transparent;
  border: none;
  color: #94949e;
  cursor: pointer;
  padding: 4px;
  display: flex;
  align-items: center;
  border-radius: 4px;
  transition: color 0.2s;
}

.sort-btn:hover {
  color: #ff71a4;
}

.sort-icon {
  width: 14px;
  height: 14px;
  transition: transform 0.2s ease;
}

.card-body {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-height: 16rem;
}

.data-row {
  position: relative;
  display: flex;
  align-items: center;
  height: 38px;
  width: 100%;
  overflow: hidden;
  border-radius: 4px;
  background: rgba(14, 14, 18, 0.6);
  border: 1px solid rgba(255, 255, 255, 0.02);
}

.data-bar-fill {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background: linear-gradient(90deg, rgba(255, 45, 120, 0.05) 0%, rgba(255, 45, 120, 0.2) 100%);
  border-right: 2px solid #ff2d78;
  box-shadow: inset -5px 0 10px rgba(255, 45, 120, 0.2), 0 0 12px rgba(255, 45, 120, 0.2);
  transition: width 0.6s cubic-bezier(0.16, 1, 0.3, 1);
  z-index: 0;
}

.data-row-content {
  position: relative;
  z-index: 10;
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  padding: 0 16px;
}

.data-left {
  display: flex;
  align-items: center;
  gap: 8px;
  min-w: 0;
}

.path-prefix {
  font-family: 'Space Grotesk', sans-serif;
  color: #ff71a4;
  font-weight: 700;
  user-select: none;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #00f0ff;
  box-shadow: 0 0 6px #00f0ff;
  flex-shrink: 0;
}

.data-name {
  font-size: 14px;
  font-weight: 400;
  color: #e4e1e7;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.mono-data {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 14px;
  font-weight: 500;
  color: #ffffff;
  letter-spacing: 0.02em;
  padding-left: 8px;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 64px 0;
  text-align: center;
}
</style>