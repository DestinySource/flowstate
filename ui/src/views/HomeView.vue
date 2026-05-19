<script setup lang="ts">
import { ref } from 'vue'
import { motion } from 'motion-v'
import HeroHeader from '@/components/HeroHeader.vue'
import FeatureCard from '@/components/FeatureCard.vue'
import ApiButton from "@/components/ApiButton.vue";

const showExtra = ref(false)
const apiStatus = ref<'idle' | 'loading' | 'success' | 'error'>('idle')

const features = [
  { id: 1, title: 'Hardware Accelerated', icon: '🚀' },
  { id: 2, title: 'App-like Gestures', icon: '📱' },
  { id: 3, title: 'Layout Animations', icon: '✨' }
]

const showcaseFeatures = [
  { id: 1, title: 'AI-Driven Predictions', description: 'Our custom models analyze terabytes of historical data to forecast trends with unprecedented precision.', icon: '🧠' },
  { id: 2, title: 'Real-Time Data Ingestion', description: 'Process millions of events per second with sub-millisecond latency using our optimized Go pipelines.', icon: '⚡' },
  { id: 3, title: 'Cloud-Native Architecture', description: 'Scale dynamically to meet demand without overprovisioning, thanks to our K8s-based platform.', icon: '☁️' }
]

const analyticsPayload = {
  eventName: "home_visit",
  url: window.location.href,
  siteId: "FLOW-DEV-001",
  visitorId: "user-123"
}

async function handleApiCall() {
  apiStatus.value = 'loading'

  try {
    const response = await fetch('https://localhost:8443/api/v1/analytics', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(analyticsPayload)
    })

    if (!response.ok) throw new Error()

    apiStatus.value = 'success'
    setTimeout(() => apiStatus.value = 'idle', 2500)
  } catch (err) {
    apiStatus.value = 'error'
    setTimeout(() => apiStatus.value = 'idle', 2500)
  }
}
</script>

<template>
  <motion.main
      class="home-view"
      :initial="{ opacity: 0 }"
      :animate="{ opacity: 1 }"
  >
    <HeroHeader />

    <section class="api-section">
      <div class="stitch-divider"></div>

      <div class="api-wrapper">
        <ApiButton :status="apiStatus" @click="handleApiCall" />

        <p v-if="apiStatus === 'success'" class="api-hint">
          Data succesvol opgeslagen in PostgreSQL
        </p>
      </div>

      <div class="stitch-divider"></div>
    </section>

<!--    <section class="showcase-grid">-->
<!--      <FeatureShowcaseCard-->
<!--          v-for="(item, index) in showcaseFeatures"-->
<!--          :key="item.id"-->
<!--          v-bind="item"-->
<!--          :index="index"-->
<!--      />-->
<!--    </section>-->

    <section class="grid-container">
      <FeatureCard
          v-for="(item, index) in features"
          :key="item.id"
          :title="item.title"
          :icon="item.icon"
          :index="index"
      />
    </section>

    <section class="interactive-zone">
      <motion.div
          layout
          class="expandable-box"
          :style="{ borderRadius: '20px' }"
      >
        <motion.h2 layout>Interactieve Layout</motion.h2>

        <button @click="showExtra = !showExtra">
          {{ showExtra ? 'Minder info' : 'Lees meer' }}
        </button>

        <motion.p
            v-if="showExtra"
            :initial="{ opacity: 0, height: 0 }"
            :animate="{ opacity: 1, height: 'auto' }"
            class="description"
        >
          Dankzij de `layout` prop van Motion-v animeert deze container vloeiend mee
          wanneer de content verandert.
        </motion.p>
      </motion.div>
    </section>
  </motion.main>
</template>

<style scoped>
.home-view {
  padding: 2rem;
  max-width: 1100px;
  margin: 0 auto;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-top: 4rem;
}

.interactive-zone {
  margin-top: 6rem;
  display: flex;
  justify-content: center;
}

.expandable-box {
  background: #f0f0f5;
  padding: 2rem;
  width: 400px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.05);
}

button {
  background: #111;
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 1rem;
}

.description {
  margin-top: 1.5rem;
  line-height: 1.6;
  color: #444;
  overflow: hidden;
}

.api-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 4rem 0;
}

.api-wrapper {
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.api-hint {
  font-size: 0.85rem;
  color: #10b981; /* Groen */
  font-weight: 500;
}

.stitch-divider {
  width: 100%;
  height: 1px;
  background: repeating-linear-gradient(90deg, #ddd, #ddd 10px, transparent 10px, transparent 20px);
}
</style>