<script setup>
import HelloWorld from './components/HelloWorld.vue'
import ScrollingBackground from "./components/ScrollingBackground.vue";
import Semisolid from "./components/Semisolid.vue";
import Header from "./components/Header.vue";
</script>

<template>
  <ScrollingBackground />
  <Header />
  <Semisolid color="white">
    <h1>Welcome to Vite + Vue</h1>
    <p>
      This is a simple example of a Vue application using Vite as the build tool.
      You can edit the components and see the changes reflected instantly.
    </p>
  </Semisolid>
  <Semisolid color="blue">
    <h1>Welcome to Vite + Vue</h1>
    <p>
      This is a simple example of a Vue application using Vite as the build tool.
      You can edit the components and see the changes reflected instantly.
    </p>
  </Semisolid>
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
  <HelloWorld msg="Vite + Vue" />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
