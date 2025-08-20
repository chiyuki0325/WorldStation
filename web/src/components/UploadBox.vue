<script setup>
import {onMounted, ref} from "vue"

const props = defineProps({
  accept: {
    type: String,
    default: 'image/*',
  },
})

const uploadBox = ref(null)
const fileInput = ref(null)

const emits = defineEmits(['file'])

function testAccept(mime) {
  let accept = props.accept
  if (accept.startsWith('*')) {
    return true
  } else if (accept.endsWith('*')) {
    accept = accept.slice(0, -1)  // 去掉末尾的星号
  } else if (accept.startsWith('.')) {
    accept = accept.slice(1)
  }
  return mime.startsWith(accept) || mime.endsWith(accept)
}

onMounted(() => {
  if (uploadBox.value) {
    uploadBox.value.addEventListener('dragover', (event) => {
      event.preventDefault()
      event.stopPropagation()
      // todo dragover
    })

    uploadBox.value.addEventListener('dragleave', (event) => {
      event.preventDefault()
      event.stopPropagation()
      // todo dragleave
    })

    uploadBox.value.addEventListener('drop', (event) => {
      event.preventDefault()
      event.stopPropagation()
      uploadBox.value.classList.remove('dragging')

      const files = event.dataTransfer.files
      if (files.length > 0) {
        const file = files[0]
        // 匹配文件类型
        if (!testAccept(file.type)) {
          alert(`不支持的文件类型: ${file.name}`)
          return
        }
        fileInput.value.files = files
        handleSelectFile(file)
      }
    })
  }
})

function handleSelectFile(file) {
  if (!testAccept(file.type)) {
    alert(`不支持的文件类型: ${file.name}`)
    fileInput.value.value = ''
    return
  }
  emits('file', file)
}

function handleInput(event) {
  const files = event.target.files
  if (files.length > 0) {
    handleSelectFile(files[0])
  }
}
</script>

<template>
  <div class="upload-box" ref="uploadBox">
    <img src="/upload-bg.png" alt="Upload Background" class="upload-bg" draggable="false"/>
    <p>请把要上传的内容拖入虚线框内，<wbr><span style="display: inline-block">或点击“浏览”按钮选择文件。</span></p>
    <div class="flex-row gap" style="max-width: 80%; overflow: hidden;">
      <input type="file" :accept="props.accept" @change="handleInput" ref="fileInput" />
      <slot />
    </div>
  </div>
</template>

<style scoped>
.upload-box {
  border: 2px dashed #aaa;
  border-radius: 8px;
  width: min(600px, 100%);
  height: 400px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 1rem;
  margin: 1rem auto;
}

.upload-bg {
  width: 80%;
  user-select: none;
  -webkit-user-drag: none;
}
</style>
