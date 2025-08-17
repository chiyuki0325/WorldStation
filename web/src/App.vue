<script setup>
import ScrollingBackground from "./components/ScrollingBackground.vue";
import Semisolid from "./components/Semisolid.vue";
import Header from "./components/Header.vue";
import MainContent from "./components/MainContent.vue";
import Spring from "./components/Spring.vue";
import Footer from "./components/Footer.vue";
import Motd from "./components/Motd.vue";
import FilterBar from "./components/FilterBar.vue";

import {ref} from "vue";

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
  <ScrollingBackground />
  <Header />
  <Motd />
  <FilterBar @applyFilter="applyFilter" />
  <MainContent :title="titleFilter" :version="versionFilter" :userId="userIdFilter" />
  <Footer />
  <Spring />
</template>

<style scoped>
</style>
