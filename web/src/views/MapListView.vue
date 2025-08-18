<script setup>
import {ref} from "vue";
import Motd from "../components/Motd.vue";
import FilterBar from "../components/FilterBar.vue";
import WorldMapList from "../components/WorldMapList.vue";

const titleFilter = ref("")
const versionFilter = ref("")
const userIdFilter = ref(-1)

function applyFilter(title, version, userId) {
  titleFilter.value = title
  versionFilter.value = version
  userIdFilter.value = userId
}

</script>

<template>
  <Motd />
  <FilterBar @applyFilter="applyFilter" />
  <WorldMapList :title="titleFilter" :version="versionFilter" :userId="userIdFilter" />
</template>

<style scoped>

</style>
