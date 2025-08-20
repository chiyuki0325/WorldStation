<template>
  <Semisolid color="white">
    <p>您访问的页面不存在！</p>
    <p>点击<a @click="router.push('/')">这里</a>返回首页。</p>
  </Semisolid>
</template>

<script setup>
import Semisolid from "../components/Semisolid.vue";
import {useRouterStore} from "../stores/router.js";
const router = useRouterStore()
</script>
