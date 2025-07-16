<script setup>
// 无限滚动的世界地图列表
// credits: https://learnvue.co/articles/vue-infinite-scrolling

import {ref, onMounted, onUnmounted} from 'vue'
import WorldMapItem from "./WorldMapItem.vue";
import {useUrlStore} from "../stores/url.js";

// 页面大小
const PAGE_SIZE = 20

// 变量
let currentPage = 0
let scrolledToEnd = false
let requestPending = false

// stores
const urlStore = useUrlStore()

// refs
const worldMaps = ref([])
const scrollComponent = ref(null)
const needsLogin = ref(false)

const getWorldMaps = async (page = 0, pageSize = PAGE_SIZE) => {
  const result = await fetch(`/api/worldmaps?page=${page}&pageSize=${pageSize}`, {
    redirect: "manual"
  })
  if (result.type.endsWith("redirect") || result.status === 302) {
    needsLogin.value = true
    return []
  }
  if (!result.ok) {
    throw new Error(`获取世界地图失败: ${result.statusText}`)
  }
  const data = await result.json()
  const worldmaps = data.data || []
  if (worldmaps.length < pageSize) {
    // 如果返回的世界地图数量少于 pageSize，说明已经没有更多数据了
    scrolledToEnd = true
  }

  return worldmaps
}


onMounted(() => {
  // 监听滚动事件
  window.addEventListener("scroll", handleScroll)
  // 一开始先整点地图
  currentPage = 0
  loadMoreWorldMaps()
})

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll)
})

const loadMoreWorldMaps = async () => {
  if (scrolledToEnd || !scrollComponent.value) return

  requestPending = true
  const newWorldMaps = await getWorldMaps(currentPage)
  worldMaps.value.push(...newWorldMaps)
  currentPage += 1
  requestPending = false
}

const handleScroll = (event) => {
  if (requestPending || scrolledToEnd || !scrollComponent.value) return

  const element = scrollComponent.value
  if (element.getBoundingClientRect().bottom < window.innerHeight) {
    loadMoreWorldMaps()
  }
}


</script>

<template>
  <div ref="scrollComponent" class="worldmap-list">
    <WorldMapItem v-for="wm in worldMaps" :world-map="wm" :key="wm.id"/>
    <!-- 为了防止某些人的显示器超级无敌大 -->
    <div v-if="needsLogin">
      <span>
        <a @click="urlStore.jumpToLogin()">登录</a>以查看更多地图
      </span>
    </div>
    <div v-else>
      <span @click="loadMoreWorldMaps" v-if="!scrolledToEnd">
        加载更多地图
      </span>
    </div>
  </div>
</template>

<style scoped>
.worldmap-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
