<script setup>
import ScrollingBackground from "./components/ScrollingBackground.vue";
import Semisolid from "./components/Semisolid.vue";
import Header from "./components/Header.vue";
import MainContent from "./components/MainContent.vue";
import Spring from "./components/Spring.vue";
import Footer from "./components/Footer.vue";
</script>

<template>
  <ScrollingBackground />
  <Header />
  <Semisolid color="white">
    <strong>TODO: 搜索栏</strong>
  </Semisolid>
  <Semisolid color="blue">
    <MainContent />
  </Semisolid>
  <Footer />
  <Spring />
</template>

<style scoped>
</style>
