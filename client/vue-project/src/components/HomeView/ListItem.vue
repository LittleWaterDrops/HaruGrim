<template>
  <div class="list-item">
    <p class="title">{{ title }}</p>
    <p class="date">{{ date }}</p>
  </div>
</template>

<script setup lang="ts">
defineProps({
  title: {
    type: String,
    required: true,
  },
  date: {
    type: String,
    required: true,
  },
})
</script>

<style scoped>
.list-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  background-color: transparent;
  border-bottom: 1px solid var(--base);
  font-size: 1rem;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    color 0.3s ease;
}

.list-item:hover {
  background-color: transparent;
}

.title {
  font-weight: 500;
  color: var(--letter-black);
  transition: color 0.3s ease;
}

.list-item:hover .title {
  color: var(--primary-500);
}

.date {
  font-size: 0.9rem;
  color: var(--base);
  text-align: right;
  transition: color 0.3s ease;
}

.list-item:hover .date {
  color: var(--primary-500);
}
</style>
