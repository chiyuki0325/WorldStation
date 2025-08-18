<script setup>
import {useRouterStore} from "../stores/router.js"
import {onMounted, onUnmounted} from "vue"

const router = useRouterStore()

onMounted(() => {
  window.addEventListener('popstate', router.pop)
})

onUnmounted(() => {
  window.addEventListener('popstate', router.pop)
})
</script>

<template>
<component :is="router.view" />
</template>
