<script setup lang="ts">
import { ref } from 'vue'
import { motion } from 'motion-v'

const status = ref<'idle' | 'loading' | 'success' | 'error'>('idle')

const payload = {
  eventName: "page_view",
  url: "http://localhost:5173/home",
  referrer: "https://google.com",
  siteId: "FLOW-TEST-001",
  visitorId: "hash-123-abc-postman",
  description: "Handmatige test om de Java Record mapping te verifiëren"
}

async function sendAnalytics() {
  status.value = 'loading'

  try {
    const response = await fetch('https://localhost:8443/api/v1/analytics', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload)
    })

    if (!response.ok) throw new Error('Server error')

    status.value = 'success'
    // Reset naar idle na 3 seconden
    setTimeout(() => { status.value = 'idle' }, 3000)
  } catch (err) {
    console.error(err)
    status.value = 'error'
    setTimeout(() => { status.value = 'idle' }, 3000)
  }
}
</script>

<template>
  <div class="api-container">
    <motion.button
        class="api-button"
        @click="sendAnalytics"
        :disabled="status === 'loading'"
    :while-hover="{
    scale: 1.02,
    boxShadow: '0px 5px 15px rgba(0,0,0,0.1)'
    }"
    :while-press="{ scale: 0.98 }"
    layout
    >
    <!-- Tekst wisselt op basis van status -->
    <span v-if="status === 'idle'">Verstuur Analytics 📊</span>
    <span v-else-if="status === 'loading'">Verzenden...</span>
    <span v-else-if="status === 'success'">Verstuurd! ✅</span>
    <span v-else-if="status === 'error'">Fout bij verzenden ❌</span>
    </motion.button>

    <!-- Visualisatie van de payload voor debugging -->
    <motion.pre
        class="payload-preview"
        :initial="{ opacity: 0.6 }"
        :while-hover="{ opacity: 1 }"
    >
      {{ JSON.stringify(payload, null, 2) }}
    </motion.pre>
  </div>
</template>

<style scoped>
.api-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
  margin: 3rem 0;
}

.api-button {
  all: unset; /* Reset standaard button styles */
  background: #111;
  color: white;
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  min-width: 220px;
  text-align: center;
  transition: background-color 0.3s ease;
}

.api-button:disabled {
  background: #666;
  cursor: not-allowed;
}

.payload-preview {
  background: #1e1e1e;
  color: #a9dc76; /* Mooie groene console kleur */
  padding: 1.5rem;
  border-radius: 8px;
  font-size: 0.8rem;
  width: 100%;
  max-width: 500px;
  overflow-x: auto;
  box-shadow: inset 0 2px 10px rgba(0,0,0,0.5);
}
</style>