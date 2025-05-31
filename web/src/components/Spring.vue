<script setup>
// 只是玩了一下 spring 一词多义的梗

import {ref, onMounted, onUnmounted} from 'vue'
const display = ref(false)
const boot = ref(false)

const handleScroll = () => {
  const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
  display.value = scrollTop > 480
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

const springBoot = () => {
  boot.value = true
  setTimeout(() => {
    boot.value = false
  }, 200)
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}

</script>

<template>
  <img src="/springboot.png" alt="回到顶部" class="spring" :class="{
    transparent:!display,
    boot: boot
  }" @click="springBoot" />
</template>

<style scoped>
.spring {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 48px;
  height: 48px;
  cursor: pointer;
  opacity: 1;
  transition: transform, opacity 0.3s ease-in-out;
}

.transparent {
  opacity: 0;
}

.boot {
  transform: scaleY(0.1) translateY(90%);
  animation: boot 0.2s ease-in-out;
}

@keyframes boot {
  0% {
    transform: scaleY(0.1) translateY(90%);
  }
  100% {
    transform: scaleY(1) translateY(0);
  }
}

</style>
