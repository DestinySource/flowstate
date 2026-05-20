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
  <div class="motion-v-dashboard-bg">
    <div class="dashboard-container">

      <header class="dashboard-header">
        <div>
          <h1 class="headline-h1">> FLOWSTATE_ANALYTICS</h1>
          <p class="target-sub">
            ACTIVE_TARGET: <span class="neon-pink-text">{{ currentHost }}</span>
          </p>
        </div>

        <div class="status-indicator">
          <div v-if="isLoading" class="connecting-pulse">
            CONNECTING_TO_CORE...
          </div>
          <div v-else-if="!errorMessage" class="online-tag">
            <span class="pulse-dot"></span>
            CORE_ONLINE
          </div>
        </div>
      </header>

      <div v-if="errorMessage" class="error-dispatch-box">
        <span class="error-title">[SECURITY DISPATCH ERROR]</span>
        <p class="error-message">{{ errorMessage }}</p>
        <span class="error-footer">SYSTEM_HALTED // CHECK_DB_VERIFICATION_STATUS</span>
      </div>

      <main v-else-if="!isLoading" class="dashboard-grid">
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

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500&family=Space+Grotesk:wght@500;600;700&display=swap');

.motion-v-dashboard-bg {
  min-height: 1100vh;
  background-color: #0b0b0e;
  color: #e4e1e7;
  font-family: 'Inter', sans-serif;
  padding: 40px 32px;
}

.dashboard-container {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.dashboard-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #1c1c21;
  padding-bottom: 16px;
}

.headline-h1 {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 32px;
  font-weight: 700;
  line-height: 1.1;
  letter-spacing: -0.02em;
  color: #ffffff;
  margin: 0;
}

.target-sub {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 14px;
  color: #94949e;
  margin-top: 6px;
  letter-spacing: 0.02em;
}

.neon-pink-text {
  color: #ff2d78;
  font-weight: 600;
  text-shadow: 0 0 8px rgba(255, 45, 120, 0.4);
}

.connecting-pulse {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.1em;
  color: #ff71a4;
  animation: pulse 1.5s infinite ease-in-out;
}

.online-tag {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.1em;
  color: #00f0ff;
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(0, 240, 255, 0.05);
  padding: 6px 12px;
  border-radius: 4px;
  border: 1px solid rgba(0, 240, 255, 0.2);
}

.pulse-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #00f0ff;
  position: relative;
}

.pulse-dot::after {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: #00f0ff;
  border-radius: 50%;
  animation: rip 1.5s infinite ease-out;
}

.error-dispatch-box {
  background: rgba(147, 0, 10, 0.1);
  border: 1px solid #93000a;
  border-radius: 4px;
  padding: 24px;
  font-family: 'Space Grotesk', sans-serif;
  box-shadow: 0 0 20px rgba(147, 0, 10, 0.15);
}

.error-title {
  color: #ffb4ab;
  font-weight: 700;
  letter-spacing: 0.05em;
  display: block;
}

.error-message {
  font-family: 'Inter', sans-serif;
  font-size: 15px;
  color: #e4e1e7;
  margin: 12px 0;
  opacity: 0.9;
}

.error-footer {
  font-size: 11px;
  color: rgba(255, 180, 171, 0.5);
  letter-spacing: 0.1em;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}

@keyframes pulse {
  0%, 100% { opacity: 0.4; }
  50% { opacity: 1; }
}

@keyframes rip {
  0% { transform: scale(1); opacity: 1; }
  100% { transform: scale(3); opacity: 0; }
}
</style>