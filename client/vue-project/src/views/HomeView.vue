<template>
  <div class="home-container">
    <HomeHeader />

    <transition name="fade">
      <div class="view-actions" v-if="isViewActionsVisible">
        <div class="view-toggle">
          <button :class="{ active: viewMode === 'list' }" @click="toggleViewMode('list')">
            리스트로 보기
          </button>
          <button :class="{ active: viewMode === 'gallery' }" @click="toggleViewMode('gallery')">
            갤러리로 보기
          </button>
        </div>
        <button @click="handleWriteButtonClick" class="write-review-button view-toggle-button">
          {{ isReviewFormOpen || isReviewViewOpen ? '닫기' : '회고 작성' }}
        </button>
      </div>
    </transition>

    <transition name="fade">
      <div class="content" v-if="isContentVisible">
        <div class="content-main">
          <div class="scrollable-content" :class="{ shrink: isReviewFormOpen || isReviewViewOpen }">
            <div v-if="viewMode === 'list'" class="list-view">
              <ListItem
                v-for="(item, index) in items"
                :key="item.id"
                :title="item.title"
                :date="'2024.11.24'"
                @click="openReviewView(item)"
                :style="{ animationDelay: `${index * 50}ms` }"
                class="accordion-item"
              />
              <div class="list-view-end-space"></div>
            </div>
            <div v-else class="gallery-view">
              <GalleryItem
                v-for="(item, index) in items"
                :key="item.id"
                @click="openReviewView(item)"
                :imageUrl="item.imageUrl"
                :content="item.content"
                :style="{ animationDelay: `${index * 50}ms` }"
                class="accordion-item"
              />
            </div>
          </div>

          <div class="review-form" :class="{ visible: isReviewFormOpen }">
            <h3>회고 작성</h3>
            <form @submit.prevent="submitReview">
              <div class="form-group">
                <label for="review-title">제목</label>
                <input type="text" id="review-title" v-model="reviewTitle" required />
              </div>
              <div class="form-group">
                <label for="review-content">회고</label>
                <textarea id="review-content" v-model="reviewContent" required></textarea>
              </div>
              <button type="submit" class="submit-button" :disabled="isSubmitting">
                {{ isSubmitting ? '저장 중...' : '작성하기' }}
              </button>
            </form>
          </div>

          <div class="review-view" :class="{ visible: isReviewViewOpen }">
            <h3>{{ selectedItem?.title }}</h3>
            <img :src="selectedItem?.imageUrl" alt="이미지" class="review-image" />
            <p>{{ selectedItem?.content }}</p>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/Header/HomeHeader.vue'
import GalleryItem from '@/components/HomeView/GalleryItem.vue'
import ListItem from '@/components/HomeView/ListItem.vue'
import { ref, onMounted } from 'vue'

const viewMode = ref('list')
const isReviewFormOpen = ref(false)
const isReviewViewOpen = ref(false)
const isSubmitting = ref(false)
const selectedItem = ref(null)
const isViewActionsVisible = ref(false)
const isContentVisible = ref(false)

const items = ref(
  Array.from({ length: 20 }, (_, i) => ({
    id: i + 1,
    title: `제목 ${i + 1}`,
    content: `이것은 제목 ${i + 1}의 회고 내용입니다.`,
    imageUrl: `https://via.placeholder.com/100?text=Item+${i + 1}`,
  })),
)

const reviewTitle = ref('')
const reviewContent = ref('')

const resetReviewForm = () => {
  reviewTitle.value = ''
  reviewContent.value = ''
}

const toggleViewMode = (view) => {
  viewMode.value = view
}

const handleWriteButtonClick = () => {
  if (isReviewFormOpen.value || isReviewViewOpen.value) {
    isReviewFormOpen.value = false
    isReviewViewOpen.value = false
    selectedItem.value = null
    resetReviewForm()
  } else {
    isReviewFormOpen.value = true
  }
}

const openReviewView = (item) => {
  if (isReviewFormOpen.value) {
    isReviewFormOpen.value = false
    resetReviewForm()
    setTimeout(() => {
      selectedItem.value = item
      isReviewViewOpen.value = true
    }, 300)
  } else {
    selectedItem.value = item
    isReviewViewOpen.value = true
  }
}

const mockApiCall = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(`https://via.placeholder.com/100?text=New+Item`)
    }, 1000)
  })
}

const submitReview = async () => {
  if (reviewTitle.value && reviewContent.value) {
    isSubmitting.value = true
    try {
      const imageUrl = await mockApiCall()
      const newItem = {
        id: items.value.length + 1,
        title: reviewTitle.value,
        content: reviewContent.value,
        imageUrl,
      }
      items.value.push(newItem)
      alert('회고가 작성되었습니다.')
    } catch (error) {
      alert('회고 작성 중 문제가 발생했습니다.')
    } finally {
      isSubmitting.value = false
      isReviewFormOpen.value = false
      resetReviewForm()
    }
  }
}

onMounted(() => {
  setTimeout(() => {
    isViewActionsVisible.value = true
    setTimeout(() => {
      isContentVisible.value = true
    }, 300)
  }, 200)
})
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.view-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 10%;
  background-color: var(--background);
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 60px;
  z-index: 1000;
  margin-top: 60px;
}

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

.write-review-button {
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: var(--letter-black);
  background-color: transparent;
  transition: background-color 0.3s ease;
}

.write-review-button.active {
  color: var(--primary-500);
}

.write-review-button:hover {
  color: var(--primary-600);
}

.content {
  flex: 1;
  display: flex;
  overflow: hidden;
  padding: 0 10%;
}

.content-main {
  display: flex;
  flex: 1;
}

.scrollable-content {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  transition: flex 0.3s ease-in-out;
  scrollbar-width: none;
}

.scrollable-content::-webkit-scrollbar {
  display: none;
}

.scrollable-content.shrink {
  flex: 0.5;
}

.list-view {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.list-view-end-space {
  height: 20px;
}

.gallery-view {
  display: flex;
  flex-wrap: wrap;
  gap: 30px;
  justify-content: flex-start;
}

.accordion-item {
  animation: accordionFadeIn 0.25s ease-in-out forwards;
  opacity: 0;
}

@keyframes accordionFadeIn {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.review-form,
.review-view {
  flex: 0;
  width: 0;
  padding: 0;
  background: var(--background);
  border-left: 1px solid var(--base);
  opacity: 0;
  pointer-events: none;
  overflow: hidden;
  transition:
    flex 0.3s ease-in-out,
    width 0.3s ease-in-out,
    opacity 0.3s ease-in-out,
    padding 0.3s ease-in-out;
}

.review-form.visible,
.review-view.visible {
  flex: 0.5;
  width: auto;
  padding: 20px;
  opacity: 1;
  pointer-events: auto;
}

.review-image {
  width: 100%;
  height: auto;
  margin-bottom: 10px;
  border-radius: 4px;
  transition: opacity 0.3s ease-in-out;
}

.review-form h3 {
  margin-bottom: 10px;
}

.review-form .form-group {
  margin-bottom: 15px;
}

.review-form .form-group label {
  display: block;
  margin-bottom: 5px;
}

.review-form .form-group input,
.review-form .form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid var(--base);
  border-radius: 4px;
}

.review-form .submit-button {
  width: 100%;
  padding: 10px;
  background: var(--primary-500);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.review-form .submit-button:hover {
  background: var(--primary-600);
}
</style>
