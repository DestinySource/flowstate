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
  <section class="custom-card">
    <header class="card-header">
      <h3 class="label-caps">{{ title }}</h3>
      <span class="visitor-label">Visitors <span class="arrow">↑↓</span></span>
    </header>

    <div class="data-container">
      <div v-for="(item, index) in items" :key="index" class="data-row">
        <svg class="bar-svg" width="100%" height="36">
          <rect
              class="bar-rect"
              x="0" y="0"
              :width="`${item.percentage}%`"
              height="100%"
              rx="4"
          />

          <foreignObject x="0" y="0" width="100%" height="100%">
            <div class="row-content">
              <span class="row-label">{{ item.label }}</span>
              <span class="row-value mono-data">{{ item.value }}</span>
            </div>
          </foreignObject>
        </svg>
      </div>
    </div>

    <footer class="card-action">
      <button class="details-btn">
        <svg viewBox="0 0 24 24" width="14" height="14" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 3h6v6M9 21H3v-6M21 3l-7 7M3 21l7-7" />
        </svg>
        DETAILS
      </button>
    </footer>
  </section>
</template>

<style scoped>
/* Jouw specifieke custom-card CSS */
.custom-card {
  overflow: hidden;
  border-radius: var(--rounded-box, 4px); /* Aangepast naar 4px per je brand specs */
  border-width: .5px;
  border-color: #353439; /* surface-variant */
  background-color: #1b1b1f; /* surface-container-low */
  box-shadow: 0 1px 3px 0 rgba(0,0,0,.1), 0 1px 2px -1px rgba(0,0,0,.1);
  display: flex;
  flex-direction: column;
}

.card-header {
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.label-caps {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 12px;
  color: #888;
  letter-spacing: 0.1em;
}

.visitor-label {
  font-size: 11px;
  color: #666;
}

.data-container {
  padding: 12px 0;
  flex-grow: 1;
}

.data-row {
  padding: 2px 12px;
  position: relative;
}

.bar-svg {
  display: block;
}

.bar-rect {
  fill: rgba(255, 177, 192, 0.15); /* primary-container met opacity */
  transition: width 1s ease-out;
}

/* Voor de geselecteerde/hoogste row zoals in image_d52650.png */
.data-row:first-child .bar-rect {
  fill: rgba(255, 177, 192, 0.3);
}

.row-content {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 12px;
  color: #e4e1e7; /* on-surface */
  font-family: 'Inter', sans-serif;
  font-size: 14px;
}

.row-label {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 80%;
}

.row-value {
  font-family: 'Space Grotesk', sans-serif;
  font-weight: 500;
}

.card-action {
  padding: 12px;
  display: flex;
  justify-content: center;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
}

.details-btn {
  background: transparent;
  border: none;
  color: #888;
  font-family: 'Space Grotesk', sans-serif;
  font-size: 11px;
  font-weight: 700;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: color 0.2s;
}

.details-btn:hover {
  color: #ffb1c0; /* primary */
}
</style>