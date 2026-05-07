<script setup lang="ts">
import { motion } from 'motion-v'

defineProps<{
  title: string
  icon: string
  index: number
}>()
</script>

<template>
  <!-- whileInView zorgt ervoor dat de animatie start bij het scrollen -->
  <motion.div
      class="card"
      :initial="{ opacity: 0, scale: 0.8 }"
      :while-in-view="{ opacity: 1, scale: 1 }"
      :viewport="{ once: true }"
      :transition="{ delay: index * 0.15 }"
      :while-hover="{ y: -10, transition: { duration: 0.2 } }"
      :while-press="{ scale: 0.95 }"
  >
    <div class="icon">{{ icon }}</div>
    <h3>{{ title }}</h3>
  </motion.div>
</template>

<style scoped>
.card {
  background: white;
  border: 1px solid #eee;
  padding: 2.5rem;
  border-radius: 24px;
  text-align: center;
  cursor: pointer;
}
.icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}
</style>