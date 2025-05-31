<script setup>
import {useDark} from '@vueuse/core'

const isDark = useDark()
</script>

<template>
  <div class="scrolling-background">
    <div class="background-image" v-for="i in 10" :key="i">
      <img class="no-drag" src="/background-dark.png" alt="背景图" v-if="isDark"/>
      <img class="no-drag" src="/background-light.png" alt="背景图" v-else/>
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
  display: flex;
  flex-direction: row;
  /* 白天背景图的宽度，是夜晚背景图的二倍 */
  animation: scrollBackground 160s linear infinite;
}

.background-image > img {
  height: 100vh;
}

@media (prefers-color-scheme: dark) {
  .scrolling-background {
    /* 夜晚背景图的宽度要更窄一些 */
    animation: scrollBackground 80s linear infinite;
  }
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
