<script setup>
import Semisolid from "./Semisolid.vue"
import InputBox from "./InputBox.vue"
import {onMounted, ref} from "vue"
import {GAME_VERSION_INFO} from "../utils.js";
import {useUserIdStore} from "../stores/userId.js";

// refs
const folded = ref(false)
const versions = ref([])  // available versions
const changed = ref(false) // whether the filter has changed and not applied yet
// refs - filters
const title = ref("")
const version = ref("")
const onlyUploader = ref(false)

const userIdStore = useUserIdStore()
const emit = defineEmits(['applyFilter'])

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
  changed.value = false
  emit('applyFilter', title.value, version.value, onlyUploader.value ? userIdStore.userId : -1)
}

function setChanged() {
  changed.value = true
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
          <InputBox v-model="title" @submit="applyFilter" @input="setChanged" />
        </span>
        <span>
          <strong>版本</strong>
          <img :src="GAME_VERSION_INFO[version].icon" v-if="version" height="16" width="16" alt="版本图标"/>
          <select v-model="version" @change="setChanged">
            <option value="">全部版本</option>
            <option v-for="v in versions" :key="v" :value="v">{{GAME_VERSION_INFO[v].name }}</option>
          </select>
        </span>
        <span v-if="userIdStore.userId !== -1">
          <input type="checkbox" v-model="onlyUploader" id="onlyUploader" @change="setChanged"/>
          <label for="onlyUploader">仅显示我上传的地图</label>
        </span>
        <span class="flex-right cursor-click" @click="applyFilter">
          <img :src="changed ? '/apply-changed.png' : '/apply.png'" height="16" width="16" alt="应用筛选"/>
          <span :class="changed ? 'underscore' : ''">应用筛选</span>
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

.underscore {
  text-decoration: underline;
}
</style>
