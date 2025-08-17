<script setup>
import WorldMapList from "./WorldMapList.vue";

const filters = defineProps({
  title: {
    type: String,
    default: ""
  },
  version: {
    type: String,
    default: ""
  },
  userId: {
    type: Number,
    default: -1
  }
})
</script>

<template>
  <WorldMapList :title="filters.title" :version="filters.version" :userId="filters.userId" />
</template>

<style scoped>

</style>
