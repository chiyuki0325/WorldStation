<script setup>
import ScrollingBackground from "./components/ScrollingBackground.vue";
import Semisolid from "./components/Semisolid.vue";
import Header from "./components/Header.vue";
import MainContent from "./components/MainContent.vue";
import Spring from "./components/Spring.vue";
import Footer from "./components/Footer.vue";
import Motd from "./components/Motd.vue";
</script>

<template>
  <ScrollingBackground />
  <Header />
  <Motd />
  <Semisolid color="white">
    <strong>TODO: 搜索栏</strong>
  </Semisolid>
  <MainContent />
  <Footer />
  <Spring />
</template>

<style scoped>
</style>
