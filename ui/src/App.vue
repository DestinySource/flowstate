<script setup lang="ts">
import { onMounted } from 'vue'
import { RouterView } from 'vue-router'
import { useInitialize } from "@/composables/Initialize.ts";
import { useAuth } from "@/composables/useAuth.ts";

const { setUserId } = useInitialize()
const { tryRestoreSession } = useAuth()

onMounted(() => {
  setUserId()
  tryRestoreSession()
})
</script>

<template>
  <RouterView />
</template>