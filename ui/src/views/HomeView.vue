<script setup lang="ts">
import { ref } from 'vue'
import { motion } from 'motion-v'
import HeroHeader from '@/components/HeroHeader.vue'
import FeatureCard from '@/components/FeatureCard.vue'
import ApiButton from "@/components/ApiButton.vue";

const showExtra = ref(false)

const features = [
  { id: 1, title: 'Hardware Accelerated', icon: '🚀' },
  { id: 2, title: 'App-like Gestures', icon: '📱' },
  { id: 3, title: 'Layout Animations', icon: '✨' }
]
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
      <ApiButton />
      <div class="stitch-divider"></div>
    </section>

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
</style>