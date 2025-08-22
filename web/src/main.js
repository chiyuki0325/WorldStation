import { createApp } from 'vue'
import FloatingVue from 'floating-vue'
import './css/style.css'
import 'floating-vue/dist/style.css'
import App from './App.vue'
import {createPinia} from "pinia";

createApp(App).use(createPinia()).use(FloatingVue).mount('#app')
