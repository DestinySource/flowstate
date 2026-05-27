<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CustomAnalyticsCard from '@/components/CustomAnalyticsCard.vue'

const dashboardData = ref({
  hostname: [],
  pages: []
})

const isLoading = ref(true)
const errorMessage = ref('')
const currentHost = ref(window.location.host)
const currentCutoff = ref('7days')

const fetchDashboardStats = async () => {
  try {
    isLoading.value = true
    errorMessage.value = ''

    const response = await fetch(`https://localhost:8443/api/dashboard/stats?host=${currentHost.value}&cutOff=${currentCutoff.value}`)
    if (!response.ok) {
      errorMessage.value = await response.text()
      return
    }

    dashboardData.value = await response.json()
  } catch (err: any) {
    errorMessage.value = "Kan geen verbinding maken met de Flowstate analytics server."
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  fetchDashboardStats()
})
</script>

<template>
  <div class="app-container">

    <aside class="sidebar-display">
      <div class="sidebar-block brand-block">
        <div class="sidebar-logo">FLOWSTATE</div>
      </div>

      <nav class="sidebar-block sidebar-nav">
        <a href="#" class="sidebar-link is-active">
          <span class="sidebar-link-dot"></span> OVERVIEW
        </a>
        <a href="#" class="sidebar-link">
          <span class="sidebar-link-dot"></span> LIVE_TRAFFIC
        </a>
        <a href="#" class="sidebar-link">
          <span class="sidebar-link-dot"></span> BEHAVIOR
        </a>
      </nav>

      <nav class="sidebar-block sidebar-nav">
        <a href="#" class="sidebar-link">
          <span class="sidebar-link-dot"></span> SYSTEM_SETTINGS
        </a>
        <a href="#" class="sidebar-link">
          <span class="sidebar-link-dot"></span> VERIFICATION
        </a>
        <a href="#" class="sidebar-link">
          <span class="sidebar-link-dot"></span> LOGS
        </a>
      </nav>

      <div class="sidebar-footer-block">
        <span class="label-caps opacity-40">v1.0.4-beta</span>
      </div>
    </aside>

    <div class="dashboard-view">
      <div class="dashboard-container">

        <header class="dashboard-header">
          <div>
            <h1 class="dashboard-title">FLOWSTATE ANALYTICS</h1>
            <p class="dashboard-subtitle">
              ACTIVE_TARGET: <span class="text-neon-pink">{{ currentHost }}</span>
            </p>
          </div>

          <div class="status-indicator">
            <div v-if="isLoading" class="pulse-connection">
              CONNECTING
            </div>
            <div v-else-if="!errorMessage" class="badge-online">
              <span class="badge-dot"></span>
              ONLINE
            </div>
          </div>
        </header>

        <div v-if="errorMessage" class="alert-dispatch">
          <span class="alert-title">ERROR</span>
          <p class="alert-message">{{ errorMessage }}</p>
          <span class="alert-footer">Please contact a developer! We will try to fix this problem. And thank you for your help 🩷</span>
        </div>

        <main class="dashboard-grid">
          <CustomAnalyticsCard
              id="Source"
              :tabs="['hostname', 'pages']"
              :cardData="dashboardData"
              :defaultCutoff="currentCutoff"
              @update:cutoff="(newValue) => { currentCutoff = newValue; fetchDashboardStats(); }"
          />
        </main>

      </div>
    </div>

  </div>
</template>