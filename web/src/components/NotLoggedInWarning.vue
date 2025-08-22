<script setup>
import Semisolid from "./Semisolid.vue"
</script>

<template>
  <Semisolid color="white">
    <div class="flex-row gap"><img src="/static/apply-changed.png" class="warning" alt="warning"/>您还未登录，只能查看部分地图。</div>
  </Semisolid>
</template>

<style scoped>
.warning {
  height: 16px;
  width: 16px;
}
</style>
