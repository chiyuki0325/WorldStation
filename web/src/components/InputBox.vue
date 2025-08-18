<script setup>
import {ref} from 'vue'
const modelValue = ref("")
</script>

<template>
  <input
    type="text"
    class="input-box"
    @keydown.enter="$emit('submit', $event.target.value)"
    v-model="modelValue"
    @input="$emit('update:modelValue', $event.target.value); $emit('input', $event.target.value)"
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
