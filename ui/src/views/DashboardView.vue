  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import CustomAnalyticsCard from '@/components/CustomAnalyticsCard.vue'
  import { useAuth } from '@/composables/useAuth'

  const { apiFetch } = useAuth()

  const dashboardData = ref({
    host: [],
    pages: [],
    browser: [],
    os: [],
    device: []
  })

  const userSites = ref<string[]>([])

  const isLoading = ref(true)
  const errorMessage = ref('')
  const currentHost = ref('')
  const currentCutoff = ref('7days')

  const fetchMySites = async () => {
    try {
      isLoading.value = true
      errorMessage.value = ''

      const response = await apiFetch('https://localhost:8443/api/users/sites')

      if (!response.ok) {
        throw new Error(`Systeemfout: status ${response.status}`)
      }

      const data = await response.json()
      userSites.value = data

      if (data && data.length > 0) {
        currentHost.value = data[0]
      } else {
        errorMessage.value = "Er zijn geen websites aan jouw account gekoppeld."
      }

    } catch (error) {
      console.error(error)
      errorMessage.value = "Kon je gekoppelde websites niet ophalen van de server."
    } finally {
      isLoading.value = false
    }
  }

  const fetchDashboardStats = async () => {
    if (!currentHost.value) return

    try {
      isLoading.value = true
      errorMessage.value = ''

      const response = await apiFetch(`https://localhost:8443/api/dashboard/stats?host=${currentHost.value}&cutOff=${currentCutoff.value}`)
      if (!response.ok) {
        errorMessage.value = await response.text()
        return
      }

      dashboardData.value = await response.json()
    } catch (err: any) {
      errorMessage.value = "Kan geen verbinding maken met de Flowstate analytics server."
    } finally {
      isLoading.value = false
    }
  }

  const handleHostChange = (event: Event) => {
    const target = event.target as HTMLSelectElement
    currentHost.value = target.value
    fetchDashboardStats()
  }

  const handleGlobalCutoffChange = (event: Event) => {
    const target = event.target as HTMLSelectElement
    currentCutoff.value = target.value
    fetchDashboardStats()
  }

  onMounted(() => {
    fetchDashboardStats()
    fetchMySites()
  })
  </script>

  <template>
    <div class="flex min-h-screen w-screen bg-bg text-neutral-300 font-sans antialiased selection:bg-pink-500/30 selection:text-white">

      <aside class="flex w-72 min-w-70 flex-col border-r-2 border-pink-500 bg-[#08080B] px-6 py-10 font-mono">
        <div class="mb-8 border-b border-neutral-950 pb-6">
          <div class="text-2xl font-black tracking-wider text-white uppercase">
            FLOWSTATE
          </div>
          <div class="mt-1 text-[10px] font-bold tracking-widest text-pink-500 drop-shadow-[0_0_8px_rgba(236,72,153,0.3)]">
            ANALYTICS_NODE
          </div>
        </div>

        <nav class="flex flex-col gap-2 border-b border-neutral-900 pb-6 mb-6">
          <a href="#" class="flex items-center gap-3 rounded bg-pink-500/5 border border-pink-500/10 px-4 py-2.5 text-xs font-bold tracking-wider text-white">
            <span class="h-1.5 w-1.5 rounded-full bg-pink-500"></span> OVERVIEW
          </a>
          <a href="#" class="flex items-center gap-3 rounded px-4 py-2.5 text-xs font-medium tracking-wider text-neutral-500 hover:bg-neutral-900 hover:text-neutral-200 transition-colors">
            <span class="h-1.5 w-1.5 rounded-full bg-neutral-700"></span> LIVE_TRAFFIC
          </a>
          <a href="#" class="flex items-center gap-3 rounded px-4 py-2.5 text-xs font-medium tracking-wider text-neutral-500 hover:bg-neutral-900 hover:text-neutral-200 transition-colors">
            <span class="h-1.5 w-1.5 rounded-full bg-neutral-700"></span> BEHAVIOR
          </a>
        </nav>

        <nav class="flex flex-col gap-2 border-b border-neutral-900 pb-6">
          <a href="#" class="flex items-center gap-3 rounded px-4 py-2.5 text-xs font-medium tracking-wider text-neutral-500 hover:bg-neutral-900 hover:text-neutral-200 transition-colors">
            <span class="h-1.5 w-1.5 rounded-full bg-neutral-700"></span> SYSTEM_SETTINGS
          </a>
          <a href="#" class="flex items-center gap-3 rounded px-4 py-2.5 text-xs font-medium tracking-wider text-neutral-500 hover:bg-neutral-900 hover:text-neutral-200 transition-colors">
            <span class="h-1.5 w-1.5 rounded-full bg-neutral-700"></span> VERIFICATION
          </a>
          <a href="#" class="flex items-center gap-3 rounded px-4 py-2.5 text-xs font-medium tracking-wider text-neutral-500 hover:bg-neutral-900 hover:text-neutral-200 transition-colors">
            <span class="h-1.5 w-1.5 rounded-full bg-neutral-700"></span> LOGS
          </a>
        </nav>

        <div class="mt-auto pt-4">
          <span class="text-[10px] tracking-widest text-neutral-600 font-bold uppercase">v1.0.1</span>
        </div>
      </aside>

      <div class="grow overflow-y-auto px-8 py-12 lg:px-16">
        <div class="mx-auto w-full max-w-5xl flex flex-col gap-8">

          <header class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-neutral-900 pb-6">
            <div class="font-mono p-6 bg-bg text-white">

              <label class="block text-xs text-pink-500 font-bold mb-2 uppercase">
                Selecteer Target Node:
              </label>

              <div class="relative inline-block">
                <select
                    v-model="currentHost"
                    @change="handleHostChange"
                    class="bg-neutral-900 border border-pink-500/30 text-pink-400 text-xs font-bold rounded p-2 pr-8 outline-none focus:border-pink-500"
                >
                  <option v-for="site in userSites" :key="site" :value="site">
                    {{ site }}
                  </option>
                </select>
              </div>

              <p class="text-xs text-neutral-500 mt-2">
                Actief geselecteerde host: <span class="text-cyan-400 font-bold">{{ currentHost }}</span>
              </p>

              <div v-if="errorMessage" class="text-red-500 text-xs mt-4">
                {{ errorMessage }}
              </div>
            </div>

            <div class="flex items-center gap-4 font-mono">
              <div class="relative">
                <select
                    :value="currentCutoff"
                    @change="handleGlobalCutoffChange"
                    class="h-9 appearance-none rounded border border-cyan-500/20 bg-neutral-900 pl-4 pr-10 text-xs font-bold tracking-wider text-cyan-400 outline-none transition-all hover:border-cyan-500/40 focus:border-cyan-400 focus:ring-1 focus:ring-cyan-400"
                >
                  <option value="24hours">Last 24 hours</option>
                  <option value="7days">Last 7 days</option>
                  <option value="30days">Last 30 days</option>
                </select>
                <span class="absolute right-3.5 top-2.75 pointer-events-none text-[9px] text-cyan-400/70">▼</span>
              </div>

              <div class="text-xs">
                <div v-if="isLoading" class="animate-pulse font-bold tracking-widest text-pink-400 uppercase">
                  CONNECTING...
                </div>
                <div v-else-if="!errorMessage" class="flex items-center gap-2 rounded border border-cyan-500/20 bg-cyan-500/5 px-3 py-1.5 text-xs font-bold tracking-widest text-cyan-400">
                  <span class="relative flex h-2 w-2">
                    <span class="absolute inline-flex h-full w-full animate-ping rounded-full bg-cyan-400 opacity-75"></span>
                    <span class="relative inline-flex h-2 w-2 rounded-full bg-cyan-400"></span>
                  </span>
                  ONLINE
                </div>
              </div>
            </div>
          </header>

          <div v-if="errorMessage" class="flex flex-col rounded border border-red-900 bg-red-950/20 p-5 font-mono shadow-lg shadow-red-950/10 animate-fade-in">
            <span class="text-xs font-black tracking-widest text-red-400 uppercase">!! SYSTEM_ERROR_DISPATCH !!</span>
            <p class="my-3 text-sm text-neutral-300 leading-relaxed">{{ errorMessage }}</p>
            <span class="text-[10px] tracking-wide text-red-400/50">
              Please contact a developer. Thank you for your support 🩷
            </span>
          </div>

          <main class="flex flex-col gap-10">

            <div class="flex flex-col gap-4">
              <h3 class="font-mono text-sm font-black tracking-widest text-neutral-500 uppercase">Site metrics</h3>
              <div class="grid grid-cols-1 gap-6">
                <CustomAnalyticsCard
                    id="Source"
                    :tabs="['host', 'pages']"
                    :cardData="dashboardData"
                />
              </div>
            </div>

            <div class="relative flex py-2 items-center font-mono">
              <div class="grow border-t border-neutral-900"></div>
              <span class="shrink mx-4 text-[10px] font-bold tracking-[0.3em] text-pink-500/40 select-none">DATA_STREAM</span>
              <div class="grow border-t border-neutral-900"></div>
            </div>

            <div class="flex flex-col gap-4">
              <h3 class="font-mono text-sm font-black tracking-widest text-neutral-500 uppercase">User metrics</h3>
              <div class="grid grid-cols-1 gap-6">
                <CustomAnalyticsCard
                    id="System_Environments"
                    :tabs="['browser', 'os' ,'device']"
                    :cardData="dashboardData"
                />
              </div>
            </div>

          </main>
        </div>
      </div>

    </div>
  </template>