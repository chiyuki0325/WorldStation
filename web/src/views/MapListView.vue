<script setup>
import {ref} from "vue";
import Motd from "../components/Motd.vue";
import FilterBar from "../components/FilterBar.vue";
import WorldMapList from "../components/WorldMapList.vue";
import {useUserIdStore} from "../stores/userId.js";
import NotLoggedInWarning from "../components/NotLoggedInWarning.vue";

const titleFilter = ref("")
const versionFilter = ref("")
const userIdFilter = ref(-1)

function applyFilter(title, version, userId) {
  titleFilter.value = title
  versionFilter.value = version
  userIdFilter.value = userId
}

const userIdStore = useUserIdStore()

</script>

<template>
  <Motd />
  <FilterBar v-if="userIdStore.userId !== -1" @applyFilter="applyFilter" />
  <NotLoggedInWarning v-else />
  <WorldMapList :title="titleFilter" :version="versionFilter" :userId="userIdFilter" />
</template>

<style scoped>

</style>
