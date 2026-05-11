<script setup lang="ts">
import { onMounted } from 'vue'
import { RouterView } from 'vue-router'
import { useInitialize } from "@/composables/Initialize.ts";

const { setUserId } = useInitialize()

onMounted(() => {
  setUserId()
})
</script>

<template>
  <RouterView />
</template>