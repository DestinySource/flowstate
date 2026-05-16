<script setup lang="ts">
import { motion } from 'motion-v'

// We ontvangen de status van de parent
defineProps<{
  status: 'idle' | 'loading' | 'success' | 'error'
}>()

// We laten de parent weten dat er geklikt is
const emit = defineEmits(['click'])
</script>

<template>
  <motion.button
      class="api-button"
      @click="emit('click')"
      :disabled="status === 'loading'"
      :while-hover="{
      scale: 1.05,
      backgroundColor: '#222',
      boxShadow: '0px 0px 20px rgba(0,0,0,0.2)'
    }"
      :while-press="{ scale: 0.95 }"
      layout
  >
    <div class="content-wrapper">
      <span v-if="status === 'idle'">Verstuur Data 📊</span>
      <span v-else-if="status === 'loading'" class="loader"></span>
      <span v-else-if="status === 'success'">Gelukt! ✅</span>
      <span v-else-if="status === 'error'">Foutje ❌</span>
    </div>
  </motion.button>
</template>

<style scoped>
.api-button {
  all: unset;
  background: #111;
  color: white;
  padding: 14px 32px;
  border-radius: 14px;
  font-weight: 600;
  cursor: pointer;
  min-width: 200px;
  text-align: center;
  transition: background-color 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
}

.api-button:disabled {
  background: #444;
  cursor: not-allowed;
}

.loader {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: #fff;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>