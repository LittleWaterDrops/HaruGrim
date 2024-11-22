<template>
  <div class="home-container">
    <transition name="fade">
      <HomeHeader v-if="isHeaderVisible" />
    </transition>

    <transition name="fade">
      <div class="view-actions" v-if="isViewActionsVisible">
        <button @click="toggleViewMode" class="toggle-view-button">
          {{ viewMode === 'list' ? '갤러리로 보기' : '리스트로 보기' }}
        </button>
        <button @click="handleWriteButtonClick" class="write-review-button">
          {{ isReviewFormOpen || isReviewViewOpen ? '닫기' : '회고 작성' }}
        </button>
      </div>
    </transition>

    <transition name="fade">
      <div class="content" v-if="isContentVisible">
        <div class="content-main">
          <div class="scrollable-content" :class="{ shrink: isReviewFormOpen || isReviewViewOpen }">
            <div v-if="viewMode === 'list'" class="list-view">
              <div
                v-for="item in items"
                :key="item.id"
                class="list-item"
                @click="openReviewView(item)"
              >
                {{ item.title }}
              </div>
            </div>
            <div v-else class="gallery-view">
              <div
                v-for="item in items"
                :key="item.id"
                class="gallery-item"
                @click="openReviewView(item)"
                :style="{ backgroundImage: `url(${item.imageUrl})` }"
              ></div>
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
import { ref, onMounted } from 'vue'

const viewMode = ref('list')
const isReviewFormOpen = ref(false)
const isReviewViewOpen = ref(false)
const isSubmitting = ref(false)
const selectedItem = ref(null)
const isHeaderVisible = ref(false)
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

const toggleViewMode = () => {
  viewMode.value = viewMode.value === 'list' ? 'gallery' : 'list'
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
    isHeaderVisible.value = true
    setTimeout(() => {
      isViewActionsVisible.value = true
      setTimeout(() => {
        isContentVisible.value = true
      }, 300)
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

.view-actions button {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  background-color: var(--primary-500);
  color: white;
  cursor: pointer;
}

.view-actions button:hover {
  background-color: var(--primary-600);
}

.content {
  flex: 1;
  display: flex;
  overflow: hidden;
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
}

.scrollable-content.shrink {
  flex: 0.5;
}

.list-view {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.list-item {
  padding: 15px;
  background: var(--base-light);
  border: 1px solid var(--base);
  border-radius: 4px;
  cursor: pointer;
}

.gallery-view {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}

.gallery-item {
  width: 100px;
  height: 100px;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  border-radius: 4px;
  cursor: pointer;
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
