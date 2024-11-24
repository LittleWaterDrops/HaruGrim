<template>
  <div
    class="gallery-item"
    :class="{ 'no-hover': !canHover }"
    :style="{ backgroundImage: `url(${imageUrl})` }"
  >
    <div class="overlay">
      <p class="description title">{{ title }}</p>
      <p class="description content">{{ content }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  imageUrl: {
    type: String,
    required: true,
  },
  title: {
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
  flex-direction: column;
  padding: 10px;
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
  line-height: 1.4;
  margin: 5px 0;
  overflow: hidden;
  text-overflow: ellipsis;
}

.description.title {
  font-weight: bold;
  white-space: nowrap;
}

.description.content {
  font-size: 0.8rem;
  max-height: calc(1.4em * 5);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  display: block;
  line-clamp: 5;
  box-orient: vertical;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
}
</style>
