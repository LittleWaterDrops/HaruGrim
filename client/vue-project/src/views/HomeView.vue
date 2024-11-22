<template>
  <div class="home-container">
    <HomeHeader />

    <div class="view-actions">
      <button @click="toggleViewMode" class="toggle-view-button">
        {{ viewMode === 'list' ? '갤러리로 보기' : '리스트로 보기' }}
      </button>
      <button @click="toggleReviewForm" class="write-review-button">
        {{ isReviewFormOpen ? '닫기' : '회고 작성' }}
      </button>
    </div>

    <div class="content">
      <div class="content-main">
        <div class="scrollable-content" :class="{ shrink: isReviewFormOpen }">
          <div v-if="viewMode === 'list'" class="list-view">
            <div v-for="item in items" :key="item.id" class="list-item">
              {{ item.title }}
            </div>
          </div>
          <div v-else class="gallery-view">
            <div v-for="item in items" :key="item.id" class="gallery-item"></div>
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
            <button type="submit" class="submit-button">작성하기</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import HomeHeader from '@/components/Header/HomeHeader.vue'
import { ref } from 'vue'

const viewMode = ref('list')
const isReviewFormOpen = ref(false)
const items = ref(Array.from({ length: 20 }, (_, i) => ({ id: i + 1, title: `제목 ${i + 1}` })))
const reviewTitle = ref('')
const reviewContent = ref('')

const toggleViewMode = () => {
  viewMode.value = viewMode.value === 'list' ? 'gallery' : 'list'
}

const toggleReviewForm = () => {
  isReviewFormOpen.value = !isReviewFormOpen.value
}

const submitReview = () => {
  if (reviewTitle.value && reviewContent.value) {
    alert('회고가 작성되었습니다.')
    isReviewFormOpen.value = false
    reviewTitle.value = ''
    reviewContent.value = ''
  }
}
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
  background: var(--base-light);
  border: 1px solid var(--base);
  border-radius: 4px;
}

.review-form {
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

.review-form.visible {
  flex: 0.5;
  width: auto;
  padding: 20px;
  opacity: 1;
  pointer-events: auto;
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
