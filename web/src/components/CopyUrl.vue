<script setup>
import {useClipboard} from "@vueuse/core"

const props = defineProps({
  url: {
    type: String,
    required: true,
  },
})

const {text, copy, copied, isSupported} = useClipboard({source: props.url})

</script>

<template>
  <div>
    <p>上传成功！链接为：<code>{{ url }}</code></p>
    <button @click="copy(props.url)" :disabled="!isSupported">复制链接</button>
    <span v-if="copied" style="color: green; margin-left: 10px;">已复制到剪贴板！</span>
    <span v-else-if="!isSupported" style="color: red; margin-left: 10px;">浏览器不支持，请手动复制。</span>
  </div>
</template>

<style scoped>

</style>
