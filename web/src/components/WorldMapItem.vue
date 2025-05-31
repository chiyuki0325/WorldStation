<script setup>
import {GAME_VERSION_INFO, DOWNLOAD_PROVIDER_INFO} from "../utils.js"
import {ref} from "vue";

const {worldMap} = defineProps({
  worldMap: {
    type: Object,
    required: true
  }
});

const ver = ref(GAME_VERSION_INFO[worldMap.gameVersion])
const down = ref(DOWNLOAD_PROVIDER_INFO[worldMap.downloadProvider])

</script>

<template>
  <div class="world-map-item">
    <img
        :src="ver.icon"
        :alt="ver.name"
        class="version-icon no-drag" />
    <div class="flex-column">
      <span>
        <strong>{{worldMap.title}}</strong>
        <span v-if="worldMap.author"> by {{worldMap.author}}</span>
      </span>
      <span>游戏版本: {{ver.name}}</span>
    </div>
    <a class="flex-right-row down"
       :href="worldMap.downloadUrl"
       target="_blank"
       rel="noopener noreferrer">
      <img
          :src="down.icon"
          alt="下载"
          class="download-icon no-drag" />
      <span>
        {{ down.name }}
      </span>
    </a>
  </div>
</template>

<style scoped>
.world-map-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  gap: 1em;
  padding: 0.5em;
  width: 100%;
  border-radius: 8px;
  transition: background-color 100ms ease-in-out;
}
.world-map-item:hover {
  background-color: #f0f0f05a;
}
.flex-column {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.flex-right-row {
  margin-left: auto;
  margin-right: 0.5em;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.5em;
}
.version-icon {
  width: 48px;
}
.download-icon {
  width: 32px;
}
.down {
  color: #1a1a1a;
}
</style>
