<script setup lang="ts">
import { motion } from 'motion-v'
</script>

<template>
  <header class="hero">
    <motion.h1
        :initial="{ opacity: 0, y: 50 }"
        :animate="{ opacity: 1, y: 0 }"
        :transition="{ duration: 0.6, ease: 'easeOut' }"
    >
      Motion voor Vue
    </motion.h1>

    <motion.div
        class="divider"
        :initial="{ width: 0 }"
        :animate="{ width: '100px' }"
        :transition="{ delay: 0.5, duration: 0.8 }"
    />
  </header>
</template>

<style scoped>
.hero {
  text-align: center;
  margin-bottom: 3rem;
}
h1 {
  font-size: 4rem;
  margin: 0;
}
.divider {
  height: 4px;
  background: #42b883;
  margin: 1rem auto;
}
</style>