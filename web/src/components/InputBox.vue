<script setup>
const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  id: {
    type: String,
    default: '',
  },
  style: {
    type: Object,
    default: () => ({}),
  },
})

const emit = defineEmits(['update:modelValue', 'submit', 'input'])
</script>

<template>
  <input
      type="text"
      :id="props.id"
      class="input-box"
      :value="props.modelValue"
      @input="e => { emit('update:modelValue', e.target.value); emit('input', e.target.value) }"
      @keydown.enter="emit('submit', $event.target.value)"
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
