<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'

const props = defineProps({
  percent: {
    type: Number,
    default: 0,
    validator: (value) => value >= 0 && value <= 100,
  },
})

const containerWidth = ref(0)
const totalGrids = ref(32)
const progressBarContainer = ref(null)

const filledCount = computed(() => {
  return Math.floor((props.percent / 100) * totalGrids.value);
})

const emptyCount = computed(() => {
  return totalGrids.value - filledCount.value;
})

const characterStyle = computed(() => {
  const characterPosition = Math.max((props.percent / 100) * (totalGrids.value * 32) - 32, 0)

  return {
    left: `${characterPosition}px`,
  };
});

let resizeObserver;

onMounted(() => {
  // vibed
  if (progressBarContainer.value) {
    // 创建一个 ResizeObserver 实例
    resizeObserver = new ResizeObserver(entries => {
      // entries[0] 包含了尺寸变化的信息
      const entry = entries[0]
      containerWidth.value = entry.contentRect.width;

      // 根据容器宽度和单个网格宽度，计算可以容纳多少个网格
      // 使用 Math.floor 确保我们不会得到半个网格
      totalGrids.value = Math.floor(containerWidth.value / 32)
    })

    // 开始观察容器元素的尺寸变化
    resizeObserver.observe(progressBarContainer.value)
  }
})

onUnmounted(() => {
  if (resizeObserver && progressBarContainer.value) {
    resizeObserver.unobserve(progressBarContainer.value)
    resizeObserver.disconnect()
  }
})
</script>

<template>
  <div class="progress-bar-parent" ref="progressBarContainer">
    <div class="walking-parent" :style="characterStyle">
      <img class="walking" src="/static/walk.gif" alt="walking character" />
    </div>
    <div class="progress-bar">
      <img
          v-for="n in filledCount"
          :key="'filled-' + n"
          src="/static/filled.png"
          alt="filled"
          class="progress-grid"
      />
      <img
          v-for="n in emptyCount"
          :key="'empty-' + n"
          src="/static/empty.png"
          alt="empty"
          class="progress-grid"
      />
    </div>
  </div>
</template>

<style scoped>
.progress-bar-parent {
  position: relative;
  width: 100%;
  height: 64px;
}

.progress-bar-parent > div {
  line-height: 0;
  height: 32px;
}

.progress-bar {
  display: flex;
  flex-direction: row;
  width: 100%;
  overflow: hidden;
  position: absolute;
  bottom: 0;
  left: 0;
}

.progress-grid {
  width: 32px;
  height: 32px;
  display: block;
}

.walking-parent {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
  transition: left 0.3s linear;
}

.walking {
  width: 32px;
  height: 32px;
  display: block;
}
</style>
