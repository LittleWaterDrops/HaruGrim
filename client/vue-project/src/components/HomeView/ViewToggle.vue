<template>
  <div class="view-toggle">
    <button :class="{ active: currentView === 'list' }" @click="updateView('list')">
      리스트 보기
    </button>
    <button :class="{ active: currentView === 'gallery' }" @click="updateView('gallery')">
      갤러리 보기
    </button>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  currentView: 'list' | 'gallery'
}>()

const emit = defineEmits<{
  (event: 'update:view', view: 'list' | 'gallery'): void
}>()

const updateView = (view: 'list' | 'gallery') => {
  emit('update:view', view)
}
</script>

<style scoped>
.view-toggle {
  display: flex;
  gap: 15px;
}

.view-toggle button {
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: var(--letter-black);
  background-color: transparent;
  transition: background-color 0.3s ease;
}

.view-toggle button.active {
  color: var(--primary-500);
}

.view-toggle button:hover {
  color: var(--primary-600);
}
</style>
