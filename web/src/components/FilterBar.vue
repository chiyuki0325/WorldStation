<script setup>
import Semisolid from "./Semisolid.vue"
import InputBox from "./InputBox.vue"
import {onMounted, ref} from "vue"
import {GAME_VERSION_INFO} from "../utils.js";

// refs
const folded = ref(false)
const versions = ref([])
// refs - filters
const title = ref("")
const version = ref("")

onMounted(() => {
  if (window.innerWidth < 600) {
    fold()
  } else {
    unfold()
  }
})

function fold() {
  folded.value = true
}

function unfold() {
  folded.value = false
  initVersions()
}

function initVersions() {
  if (versions.value.length > 0) return
  fetch("/api/versions")
      .then(res => res.json())
      .then(j => {
        versions.value = j.data.filter(v => v !== 'UNKNOWN')
      })
      .catch(err => {
        console.error("Failed to fetch versions:", err)
      })
}

function applyFilter() {
  console.log("Applying filter...")
}
</script>

<template>
  <Semisolid color="white">
    <div class="flex-row">
      <img src="/pipe.png" height="16" width="16" alt="筛选"/>
      <strong>筛选地图 ...</strong>
      <span class="flex-right" v-if="folded" @click="unfold">展开 ⏷</span>
      <span class="flex-right" v-else @click="fold">收起 ⏶</span>
    </div>
    <div v-if="!folded">
      <hr/>
      <div class="flex-row filter-bar">
        <span>
          <strong>标题</strong>
          <InputBox v-model="title" @submit="applyFilter"/>
        </span>
        <span>
          <strong>版本</strong>
          <img :src="GAME_VERSION_INFO[version].icon" v-if="version" height="16" width="16" alt="版本图标"/>
          <select v-model="version">
            <option value="">全部版本</option>
            <option v-for="v in versions" :key="v" :value="v">{{GAME_VERSION_INFO[v].name }}</option>
          </select>
        </span>
        <span class="flex-right cursor-click" @click="applyFilter">
          <img src="/apply.png" height="16" width="16" alt="应用筛选"/>
          <span>应用筛选</span>
        </span>
      </div>
    </div>
  </Semisolid>
</template>

<style scoped>
.flex-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.8em;
  flex-wrap: wrap;
}

.filter-bar > span {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.2em;
}

.flex-right {
  margin-left: auto;
}

.cursor-click {
  cursor: pointer;
}
</style>
