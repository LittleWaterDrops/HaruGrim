<template>
  <div
    class="gallery-item"
    :class="{ 'no-hover': !canHover }"
    :style="{ backgroundImage: `url(${imageUrl})` }"
  >
    <div class="overlay">
      <p class="description">{{ content }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  imageUrl: {
    type: String,
    required: true,
  },
  content: {
    type: String,
    required: true,
  },
  canHover: {
    type: Boolean,
    default: true,
  },
})
</script>

<style scoped>
.gallery-item {
  position: relative;
  width: 150px;
  height: 225px;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  border-radius: 8px;
  cursor: pointer;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
}

.gallery-item:hover {
  transform: scale(1.05);
}

.gallery-item.no-hover {
  pointer-events: none;
  transform: none;
}

.gallery-item.no-hover:hover {
  transform: none;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0);
  border-radius: inherit;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  opacity: 0;
  transition:
    background 0.3s ease,
    opacity 0.3s ease;
}

.gallery-item:hover .overlay {
  background: rgba(0, 0, 0, 0.6);
  opacity: 1;
}

.gallery-item.no-hover .overlay {
  background: rgba(0, 0, 0, 0);
  opacity: 0;
}

.description {
  font-size: 0.9rem;
  padding: 5px;
  line-height: 1.4;
}
</style>
