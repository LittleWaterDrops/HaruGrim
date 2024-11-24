<template>
  <section class="view-section" :class="{ visible: isVisible }" ref="viewSection">
    <div class="view-header">
      <h1 class="category">보기</h1>
      <h2 class="title">
        제목 리스트와 갤러리 보기, <br />
        원하는 방식으로 더 쉽게 즐기세요.
      </h2>
      <ViewToggle :current-view="isGalleryView ? 'gallery' : 'list'" @update:view="toggleView" />
    </div>

    <div class="view-content">
      <div v-if="isGalleryView" class="gallery-view">
        <GalleryItem
          v-for="(item, index) in items"
          :key="'gallery-' + index"
          :imageUrl="item.imageUrl"
          :title="item.title"
          :content="item.content"
        />
      </div>

      <div v-else class="list-view">
        <ListItem
          v-for="(item, index) in items"
          :key="'list-' + index"
          :title="item.title"
          :date="item.date"
        />
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import GalleryItem from '../HomeView/GalleryItem.vue'
import ListItem from '../HomeView/ListItem.vue'
import ViewToggle from '../HomeView/ViewToggle.vue'

const items = [
  {
    imageUrl: 'https://via.placeholder.com/150x225?text=Image+1',
    title: '제목 1',
    content: '제목 1',
    date: '2024.11.24',
  },
  {
    imageUrl: 'https://via.placeholder.com/150x225?text=Image+2',
    title: '제목 2',
    content: '제목 1',
    date: '2024.11.24',
  },
  {
    imageUrl: 'https://via.placeholder.com/150x225?text=Image+3',
    title: '제목 3',
    content: '제목 1',
    date: '2024.11.24',
  },
  {
    imageUrl: 'https://via.placeholder.com/150x225?text=Image+3',
    title: '제목 4',
    content: '제목 1',
    date: '2024.11.24',
  },
  {
    imageUrl: 'https://via.placeholder.com/150x225?text=Image+3',
    title: '제목 5',
    content: '제목 1',
    date: '2024.11.24',
  },
]

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
  margin-bottom: 50px;
}

.view-content {
  margin-top: 10px;
  height: auto;
}

.gallery-view {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  gap: 30px;
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
