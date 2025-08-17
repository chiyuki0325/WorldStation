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
  }
})
</script>

<template>
  <WorldMapList :title="filters.title" :version="filters.version"/>
</template>

<style scoped>

</style>
