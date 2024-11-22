<template>
  <section class="view-section" :class="{ visible: isVisible }" ref="viewSection">
    <div class="view-header">
      <h1 class="category">보기</h1>
      <h2 class="title">
        제목 리스트와 갤러리 보기, <br />
        원하는 방식으로 더 쉽게 즐기세요.
      </h2>
      <div class="view-toggle">
        <button :class="{ active: !isGalleryView }" @click="toggleView('list')">
          리스트로 보기
        </button>
        <button :class="{ active: isGalleryView }" @click="toggleView('gallery')">
          갤러리로 보기
        </button>
      </div>
    </div>

    <div class="view-content">
      <div v-if="isGalleryView" class="gallery-view">
        <GalleryItem
          v-for="(item, index) in items"
          :key="'gallery-' + index"
          :imageUrl="`https://via.placeholder.com/150x225?text=Image+${index + 1}`"
          :content="`이미지 설명 ${index + 1}`"
        />
      </div>

      <div v-else class="list-view">
        <ul>
          <li v-for="(item, index) in items" :key="'list-' + index">
            {{ item }}
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import GalleryItem from '../HomeView/GalleryItem.vue'

const items = ['제목 1', '제목 2', '제목 3', '제목 4', '제목 5']

const isGalleryView = ref(false)

const toggleView = (view: 'gallery' | 'list') => {
  isGalleryView.value = view === 'gallery'
}

const isVisible = ref(false)
const viewSection = ref<HTMLElement | null>(null)

const handleIntersection = (entries: IntersectionObserverEntry[]) => {
  const [entry] = entries
  if (entry.isIntersecting) {
    isVisible.value = true
  }
}

let observer: IntersectionObserver | null = null

onMounted(() => {
  observer = new IntersectionObserver(handleIntersection, {
    threshold: 0.1,
  })
  if (viewSection.value) {
    observer.observe(viewSection.value)
  }
})

onUnmounted(() => {
  if (observer && viewSection.value) {
    observer.unobserve(viewSection.value)
  }
})
</script>

<style scoped>
.view-section {
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 100px 10%;
  background-color: var(--base-light);
  color: var(--letter-black);
  opacity: 0;
  transform: translateY(20px);
  transition:
    opacity 0.8s ease-out,
    transform 0.8s ease-out;
}

.view-section.visible {
  opacity: 1;
  transform: translateY(0);
}

.view-header {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.category {
  align-self: flex-start;
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--primary-500);
  margin-bottom: 10px;
}

.title {
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--letter-black);
}

.view-toggle {
  margin-top: 50px;
  margin-left: 0px;
}

.view-toggle button {
  margin-right: 15px;
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

.view-content {
  margin-top: 10px;
  height: 500px;
}

.gallery-view {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
}

.list-view ul {
  list-style: none;
  padding: 0;
}

.list-view li {
  padding: 10px 0;
  border-bottom: 1px solid var(--base);
  font-size: 1.2rem;
}
</style>
