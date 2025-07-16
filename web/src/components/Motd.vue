<script setup>
import {onMounted, ref} from "vue";
import {marked} from "marked";
import Semisolid from "./Semisolid.vue";

const content = ref([])

onMounted(() => {
  fetch("/api/motd")
      .then(response => {
        if (!response.ok) {
          throw new Error("获取 MOTD 失败: " + response.statusText);
        }
        return response.json();
      })
      .then(json => json?.data?.filter(it => it.enabled)?.map(it => it.content))
      .then(lines => {
        content.value = lines.map(line => marked.parse(line));
      })
})
</script>

<template>
  <Semisolid color="blue" v-if="content.length > 0">
    <span v-for="line in content" v-html="line" :key="line"/>
  </Semisolid>
</template>

<style scoped>
</style>
