import { createApp } from 'vue'
import FloatingVue from 'floating-vue'
import './style.css'
import 'floating-vue/dist/style.css'
import App from './App.vue'

createApp(App).use(FloatingVue).mount('#app')
