<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { motion } from 'motion-v'

interface Props {
  title: string
  description: string
  icon: string
  index?: number
}

const props = withDefaults(defineProps<Props>(), {
  index: 0,
})

const cardRef = ref<HTMLElement | null>(null)
const mousePos = reactive({ x: 0, y: 0 })
const isHovering = ref(false)

const tiltStyle = computed(() => {
  if (!isHovering.value) return { transform: 'perspective(1000px) rotateX(0deg) rotateY(0deg)' }

  const intensiteit = 30;
  const rotateX = -mousePos.y / intensiteit;
  const rotateY = mousePos.x / intensiteit;

  return {
    transform: `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`,
    '--mouse-x': `${mousePos.x + (cardRef.value?.offsetWidth || 0) / 2}px`,
    '--mouse-y': `${mousePos.y + (cardRef.value?.offsetHeight || 0) / 2}px`,
  }
})

function handleMouseMove(e: MouseEvent) {
  if (!cardRef.value) return
  const rect = cardRef.value.getBoundingClientRect()

  mousePos.x = e.clientX - rect.left - rect.width / 2
  mousePos.y = e.clientY - rect.top - rect.height / 2
}


const cardEntrance = {
  initial: { opacity: 0, y: 100, scale: 0.9 },
  animate: { opacity: 1, y: 0, scale: 1 },
  transition: {
    delay: props.index * 0.25,
    duration: 1.2,
    ease: [0.19, 1, 0.22, 1]
  }
}

const contentReveal = {
  initial: { opacity: 0, x: -20 },
  animate: { opacity: 1, x: 0 },
  transition: {
    delay: (props.index * 0.25) + 0.8,
    duration: 0.8,
    ease: 'easeOut'
  }
}
</script>

<template>
  <div class="card-perspective-wrapper">
    <motion.div
        ref="cardRef"
        class="showcase-card"
        v-bind="cardEntrance"
        :style="tiltStyle"
        @mousemove="handleMouseMove"
        @mouseenter="isHovering = true"
        @mouseleave="isHovering = false"
    >
      <div class="glow-effect"></div>

      <div class="card-content">
        <motion.div class="icon-wrapper" v-bind="contentReveal">
          <span class="icon">{{ icon }}</span>
          <div class="icon-pulse"></div>
        </motion.div>

        <motion.h3 class="title" v-bind="contentReveal">
          {{ title }}
        </motion.h3>

        <motion.p class="description" v-bind="contentReveal">
          {{ description }}
        </motion.p>
      </div>
    </motion.div>
  </div>
</template>

<style scoped>
.card-perspective-wrapper {
  perspective: 1000px;
  transform-style: preserve-3d;
  width: 100%;
}

.showcase-card {
  --mouse-x: 50%;
  --mouse-y: 50%;

  background: #111;
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 24px;
  padding: 2.5rem;
  width: 100%;
  min-height: 350px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: transform 0.1s ease-out, border-color 0.4s ease;
  transform-style: preserve-3d;

.showcase-card:hover {
  border-color: rgba(255, 0, 127, 0.3);
}

.glow-effect {
  position: absolute;
  inset: -2px;
  background: radial-gradient(
      600px circle at var(--mouse-x) var(--mouse-y),
      rgba(255, 0, 127, 0.15),
      transparent 40%
  );
  opacity: 0;
  transition: opacity 0.5s ease;
  pointer-events: none;
  z-index: 0;
}

.showcase-card:hover .glow-effect {
  opacity: 1;
}

.card-content {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  transform: translateZ(20px);
}

.icon-wrapper {
  position: relative;
  display: inline-flex;
}

.icon {
  font-size: 3rem;
  filter: drop-shadow(0 0 10px rgba(255, 0, 127, 0.5));
}

.icon-pulse {
  position: absolute;
  inset: -10px;
  background: rgba(255, 0, 127, 0.1);
  border-radius: 50%;
  filter: blur(10px);
  animation: pulse 2s infinite ease-in-out;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); opacity: 0.5; }
  50% { transform: scale(1.1); opacity: 0.8; }
}

.title {
  font-family: 'Syne', sans-serif;
  font-size: 1.6rem;
  font-weight: 800;
  margin: 0;
  letter-spacing: -0.02em;
}

.description {
  font-size: 1rem;
  color: #aaa;
  line-height: 1.6;
  margin: 0;
  max-width: 90%;
}
}
</style>