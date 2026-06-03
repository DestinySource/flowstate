<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CustomAnalyticsCard from '@/components/CustomAnalyticsCard.vue'
import { useAuth } from '@/composables/useAuth'

const { apiFetch } = useAuth()

const dashboardData = ref({
  hostname: [],
  pages: [],
  browser: [],
  os: [],
  device: []
})

const isLoading = ref(true)
const errorMessage = ref('')
const currentHost = ref(window.location.host)

const currentCutoff = ref('7days')

const fetchDashboardStats = async () => {
  try {
    isLoading.value = true
    errorMessage.value = ''

    const response = await apiFetch(`https://localhost:8443/api/dashboard/stats?host=${currentHost.value}&cutOff=${currentCutoff.value}`)
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

const handleGlobalCutoffChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  currentCutoff.value = target.value
  fetchDashboardStats()
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
          <div class="header-left-zone">
            <h1 class="dashboard-title">FLOWSTATE ANALYTICS</h1>
            <p class="dashboard-subtitle">
              ACTIVE_TARGET: <span class="text-neon-pink">{{ currentHost }}</span>
            </p>
          </div>

          <div class="header-control-zone">
            <div class="global-filter-wrapper">
              <select
                  :value="currentCutoff"
                  @change="handleGlobalCutoffChange"
                  class="global-filter-select"
              >
                <option value="24hours">Last 24 hours</option>
                <option value="7days">Last 7 days</option>
                <option value="30days">Last 30 days</option>
              </select>
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
          </div>
        </header>

        <div v-if="errorMessage" class="alert-dispatch">
          <span class="alert-title">ERROR</span>
          <p class="alert-message">{{ errorMessage }}</p>
          <span class="alert-footer">Please contact a developer! We will try to fix this problem. And thank you for your help 🩷</span>
        </div>

        <main class="dashboard-grid">

          <div class="dashboard-section-block">
            <h2 class="text-h2 section-title-spacing">SITE_METRICS</h2>
            <div class="site-metrics-grid">
              <CustomAnalyticsCard
                  id="Source"
                  :tabs="['hostname', 'pages']"
                  :cardData="dashboardData"
              />
            </div>
          </div>

          <div class="stitch-divider"></div>

          <div class="dashboard-section-block">
            <h2 class="text-h2 section-title-spacing">USER_METRICS</h2>
            <div class="user-metrics-grid">
              <CustomAnalyticsCard
                  id="SystemBrowsers"
                  :tabs="['browser', 'os' ,'device']"
                  :cardData="dashboardData"
              />
            </div>
          </div>

        </main>
      </div>
    </div>

  </div>
</template>