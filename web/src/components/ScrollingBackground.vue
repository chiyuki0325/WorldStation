<script setup>
import {useDark} from '@vueuse/core'

const isDark = useDark()
</script>

<template>
  <div class="scrolling-background">
    <div class="background-image" v-for="i in 10" :key="i">
      <img src="/background-dark.png" alt="背景图" v-if="isDark"/>
      <img src="/background-light.png" alt="背景图" v-else/>
    </div>
  </div>
</template>

<style scoped>
.scrolling-background {
  overflow-x: hidden;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
  height: 100vh;
  width: max-content;
  animation: scrollBackground 60s linear infinite;
  display: flex;
  flex-direction: row;
}

.background-image > img {
  height: 100vh;
  -webkit-user-drag: none;
  user-drag: none;
  -webkit-user-select: none;
  user-select: none;
  pointer-events: none;
}


@keyframes scrollBackground {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}
</style>
