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
  <div class="dashboard-view">
    <div class="dashboard-container">

      <header class="dashboard-header">
        <div>
          <h1 class="dashboard-title">FLOWSTATE_ANALYTICS</h1>
          <p class="dashboard-subtitle">
            ACTIVE_TARGET: <span class="text-neon-pink">{{ currentHost }}</span>
          </p>
        </div>

        <div class="status-indicator">
          <div v-if="isLoading" class="pulse-connection">
            CONNECTING_TO_CORE...
          </div>
          <div v-else-if="!errorMessage" class="badge-online">
            <span class="badge-dot"></span>
            CORE_ONLINE
          </div>
        </div>
      </header>

      <div v-if="errorMessage" class="alert-dispatch">
        <span class="alert-title">[SECURITY DISPATCH ERROR]</span>
        <p class="alert-message">{{ errorMessage }}</p>
        <span class="alert-footer">SYSTEM_HALTED // CHECK_DB_VERIFICATION_STATUS</span>
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
</template>