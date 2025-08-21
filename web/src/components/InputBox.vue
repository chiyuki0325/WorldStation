<script setup>
import {ref} from 'vue'
const modelValue = ref("")
const props = defineProps({
  id: {
    type: String,
    default: '',
  },
  style: {
    type: Object,
    default: () => ({}),
  },
})
</script>

<template>
  <input
    type="text"
    :id="props.id"
    class="input-box"
    @keydown.enter="$emit('submit', $event.target.value)"
    v-model="modelValue"
    @input="$emit('update:modelValue', $event.target.value); $emit('input', $event.target.value)"
    :style="props.style"
    />
</template>

<style scoped>
input {
  background: none;
  border: none;
  border-bottom: 2px solid black;
  color: black;
  min-width: 10em;
  max-width: 10em;
}

input:focus {
  outline: none;
}
</style>
