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
    imageUrl: '/images/image1.png',
    title: '축구 경기',
    content: '오늘은 친구들과 함께 축구 경기를 즐겼다. 모두 열심히 뛰어서 정말 재미있었다.',
    date: '2024.11.01',
  },
  {
    imageUrl: '/images/image2.png',
    title: '생일 파티',
    content: '생일 파티에서 친구들이 준비한 케이크와 선물 덕분에 특별한 하루였다.',
    date: '2024.11.05',
  },
  {
    imageUrl: '/images/image3.png',
    title: '학교 생활',
    content: '학교에서 새로운 프로젝트를 시작했다. 친구들과 협력하며 재미있는 하루를 보냈다.',
    date: '2024.11.10',
  },
  {
    imageUrl: '/images/image4.png',
    title: '회사 생활',
    content: '오늘은 회사에서 중요한 발표를 마쳤다. 긴장했지만 팀원들이 도와줘서 성공적으로 끝냈다.',
    date: '2024.11.15',
  },
  {
    imageUrl: '/images/image5.png',
    title: '헬스장 운동',
    content: '헬스장에서 열심히 운동하며 땀을 흘렸다. 몸이 더 건강해지는 기분이 들었다.',
    date: '2024.11.20',
  },
  {
    imageUrl: '/images/image6.png',
    title: '맛있는 저녁',
    content: '친구들과 함께 맛있는 저녁 식사를 했다. 오랜만에 웃고 이야기하며 즐거운 시간을 보냈다.',
    date: '2024.11.22',
  },
  {
    imageUrl: '/images/image7.png',
    title: '데이트',
    content: '오늘은 연인과 함께 공원에서 데이트를 했다. 날씨도 좋고 정말 행복한 하루였다.',
    date: '2024.11.23',
  },
  {
    imageUrl: '/images/image8.png',
    title: '놀이동산',
    content: '놀이동산에서 다양한 놀이기구를 탔다. 정말 신나는 하루를 보냈다.',
    date: '2024.11.24',
  },
  {
    imageUrl: '/images/image9.png',
    title: '영화 관람',
    content: '오랜만에 극장에서 영화 한 편을 관람했다. 감동적인 스토리에 눈물이 났다.',
    date: '2024.11.25',
  },
];


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
