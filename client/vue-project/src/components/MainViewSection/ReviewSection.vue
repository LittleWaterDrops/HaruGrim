<template>
  <section class="review-section" :class="{ visible: isVisible }" ref="reviewSection">
    <div class="review-content">
      <div>
        <h1 class="category">AI</h1>
        <h2 class="title">
          회고를 이미지로,<br />
          생각을 시각화하는 새로운 방식.
        </h2>
      </div>

      <div class="marquee">
        <div class="marquee-track">
          <GalleryItem
            v-for="(image, index) in images"
            :key="index"
            :imageUrl="image"
            title=""
            content=""
            :canHover="false"
            :style="{ flex: '0 0 auto', width: '300px', height: '450px' }"
          />
          <GalleryItem
            v-for="(image, index) in images"
            :key="'duplicate-' + index"
            :imageUrl="image"
            title=""
            content=""
            :canHover="false"
            :style="{ flex: '0 0 auto', width: '300px', height: '450px' }"
          />
        </div>
      </div>

      <p class="description">
        작성하신 회고를 바탕으로 <br />
        DALL-E를 사용하여 이미지를 만들어드려요.
      </p>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import GalleryItem from '../HomeView/GalleryItem.vue'

const images = [
  // 'https://via.placeholder.com/300x400?text=Image+1',
  // 'https://via.placeholder.com/300x400?text=Image+2',
  // 'https://via.placeholder.com/300x400?text=Image+3',
  // 'https://via.placeholder.com/300x400?text=Image+4',
  '/images/image1.png',
  '/images/image2.png',
  '/images/image3.png',
  '/images/image4.png',
  '/images/image5.png',
  '/images/image6.png',
  '/images/image7.png',
  '/images/image8.png',
  '/images/image9.png',
]

const isVisible = ref(false)
const reviewSection = ref<HTMLElement | null>(null)

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
  if (reviewSection.value) {
    observer.observe(reviewSection.value)
  }
})

onUnmounted(() => {
  if (observer && reviewSection.value) {
    observer.unobserve(reviewSection.value)
  }
})
</script>

<style scoped>
.review-section {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 100px 0px;
  background-color: var(--background);
  color: var(--letter-black);
  opacity: 0;
  transform: translateY(20px);
  transition:
    opacity 0.8s ease-out,
    transform 0.8s ease-out;
}

.review-section.visible {
  opacity: 1;
  transform: translateY(0);
}

.review-content {
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 70px;
}

.category {
  padding: 0 10%;
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--primary-500);
  margin-bottom: 10px;
}

.title {
  padding: 0 10%;
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--letter-black);
}

.marquee {
  width: 100%;
  overflow: hidden;
  position: relative;
}

.marquee-track {
  display: flex;
  gap: 50px;
  animation: marquee 10s linear infinite;
}

@keyframes marquee {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}

.description {
  padding: 0 10%;
  text-align: end;
  font-size: 1.2rem;
  font-weight: 500;
  line-height: 1.7;
  color: var(--letter-black);
}
</style>
