<script setup lang="ts">
import { motion } from 'motion-v'

defineProps<{
  title: string
  icon: string
  index: number
}>()
</script>

<template>
  <motion.div
      class="card stitch-card"
      :while-hover="{
          borderColor: '#FF2D78',
          scale: 1.1,
          transition: { duration: 0.2 }  }"
  >
    <div class="label-caps" style="color: var(--tertiary); font-size: 10px; margin-bottom: 8px;">
      SYSTEM_NODE_0{{ index + 1 }}
    </div>
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