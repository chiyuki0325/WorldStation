<script setup>
import {ref, onMounted} from "vue"

const avatarUrl = ref("/unknown.png")
const nickname = ref("点击登录")
const loginUrl = ref("/login")
const loading = ref(true)
const clickToLogin = ref(false)

onMounted(() => {
  fetch("/api/user").then(res => {
    loading.value = false
    if (res.redirected) {
      // 如果是游客，这个 api 会返回 302
      loginUrl.value = res.url
    } else if (res.ok) {
      res.json().then(data =>{
        avatarUrl.value = data.avatar_url || "/unknown.png"
        nickname.value = data.nickname || "点击登录"
      })
    } else {
      console.error("获取用户信息失败:", res.status, res.statusText)
    }
  })
})

const login = () => {
  if (clickToLogin.value) {
    window.location.href = loginUrl.value
  }
}
</script>

<template>
  <div class="user-avatar">
    <img
        class="avatar no-drag"
        :src="avatarUrl"
        alt="头像"
        :class="{'loading': loading, 'click-to-login': clickToLogin}"
        @click="login"
        @error="avatarUrl = '/unknown.png'"
        v-tooltip.left="nickname"
    />
  </div>
</template>

<style scoped>
.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-color: #f0f0f0;
  border: 2px solid #f0f0f0;
}
.avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
}
.loading {
  animation: loading 2s infinite;
}
@keyframes loading {
  0% {
    filter: blur(4px);
  }
  50% {
    filter: blur(2px);
  }
  100% {
    filter: blur(4px);
  }
}
.click-to-login {
  cursor: pointer;
}
</style>
