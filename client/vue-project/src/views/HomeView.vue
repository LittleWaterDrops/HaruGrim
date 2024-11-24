<template>
  <div class="home-container">
    <HomeHeader />

    <transition name="fade">
      <div class="view-actions" v-if="isViewActionsVisible">
        <ViewToggle :current-view="viewMode" @update:view="toggleViewMode" />
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
              <h3>리스트로 보기</h3>
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
              <h3>갤러리로 보기</h3>
              <div class="gallery-field">
                <GalleryItem
                  v-for="(item, index) in items"
                  :key="item.id"
                  @click="openReviewView(item)"
                  :imageUrl="item.imageUrl"
                  :title="item.title"
                  :content="item.content"
                  :style="{ animationDelay: `${index * 50}ms` }"
                  class="accordion-item"
                />
              </div>
            </div>
          </div>

          <div class="review-form" :class="{ visible: isReviewFormOpen }">
            <h3>회고 작성</h3>
            <form @submit.prevent="submitReview">
              <div class="form-group">
                <label for="review-title">제목</label>
                <input
                  type="text"
                  id="review-title"
                  v-model="reviewTitle"
                  required
                  placeholder="회고의 제목을 입력하세요"
                />
              </div>
              <div class="form-group">
                <label for="review-content">회고 내용</label>
                <textarea
                  id="review-content"
                  v-model="reviewContent"
                  required
                  maxlength="400"
                  placeholder="회고 내용을 입력하세요 (최대 400자)"
                  rows="6"
                ></textarea>
                <div class="char-counter">{{ reviewContent.length }} / 400</div>
              </div>
              <button
                type="submit"
                class="submit-button"
                :disabled="isSubmitting"
                style="margin-top: 20px"
              >
                <span>{{ isSubmitting ? '저장 중...' : '작성하기' }}</span>
              </button>
            </form>
          </div>

          <div class="review-view" :class="{ visible: isReviewViewOpen }">
            <h3 class="review-title">{{ selectedItem?.title }}</h3>
            <p class="review-meta">
              작성일: <span class="date">{{ formatDate(selectedItem?.createdAt) }}</span
              ><br />
              수정일: <span class="date">{{ formatDate(selectedItem?.updatedAt) }}</span>
            </p>
            <img :src="selectedItem?.imageUrl" alt="이미지" class="review-image" />
            <p class="review-content">{{ selectedItem?.content }}</p>
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
import ViewToggle from '@/components/HomeView/ViewToggle.vue'
import { ref, onMounted } from 'vue'

const viewMode = ref<'list' | 'gallery'>('list')
const isReviewFormOpen = ref(false)
const isReviewViewOpen = ref(false)
const isSubmitting = ref(false)
const selectedItem = ref<{
  id: number
  title: string
  content: string
  imageUrl: string
  createdAt: string
  updatedAt: string
} | null>(null)
const isViewActionsVisible = ref(false)
const isContentVisible = ref(false)

const items = ref(
  Array.from({ length: 20 }, (_, i) => ({
    id: i + 1,
    title: `제목 ${i + 1}`,
    content: `이것은 제목 ${i + 1}의 회고 내용입니다.`,
    imageUrl: `https://via.placeholder.com/100?text=Item+${i + 1}`,
    createdAt: '2024-11-24 14:30',
    updatedAt: '2024-11-24 16:00',
  })),
)

const reviewTitle = ref('')
const reviewContent = ref('')

const toggleViewMode = (view: 'list' | 'gallery') => {
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

const openReviewView = (item: {
  id: number
  title: string
  content: string
  imageUrl: string
  createdAt: string
  updatedAt: string
}) => {
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

const resetReviewForm = () => {
  reviewTitle.value = ''
  reviewContent.value = ''
}

const mockApiCall = () => {
  return new Promise<string>((resolve) => {
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
        createdAt: new Date().toString(),
        updatedAt: new Date().toString(),
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

const formatDate = (dateString: string | undefined): string => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: true,
  })
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

.write-review-button {
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: var(--letter-black);
  background-color: transparent;
  transition:
    background-color 0.3s ease,
    color 0.3s ease;
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
  padding: 0 8%;
  background:
    /* linear-gradient(to bottom, #ffffff, transparent), */ url('https://images.unsplash.com/photo-1543497415-75c0a27177c0?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fCVFQyVCMSU4NSUyMCVFRCU4QyU4QyVFQiU5RSU4MCVFQyU4MyU4OXxlbnwwfHwwfHx8MA%3D%3D')
    no-repeat center center/cover;
}

.content-main {
  display: flex;
  flex: 1;
}

.scrollable-content {
  flex: 1;
  overflow-y: auto;
  padding: 30px 40px 30px 40px;
  transition: flex 0.3s ease-in-out;
  scrollbar-width: none;
  background: var(--background);
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
  flex-direction: column;
}

.gallery-field {
  margin-top: 10px;
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap;
  gap: 30px;
  justify-content: flex-start;
}

.accordion-item {
  animation: accordionFadeIn 0.25s ease-in-out forwards;
  opacity: 0;
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
  padding: 30px 40px 30px 40px;
  opacity: 1;
  pointer-events: auto;
}

.review-image {
  width: 300px;
  height: auto;
  margin: 35px auto;
  border-radius: 4px;
  display: block;
  transition: opacity 0.3s ease-in-out;
}

.review-form h3 {
  margin-bottom: 10px;
  color: var(--letter-black);
}

.review-view h3 {
  font-size: 1.7rem;
  font-weight: 500;
  margin-bottom: 10px;
  color: var(--letter-black);
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
  font-size: 0.9rem;
  border: 1px solid var(--base);
  border-radius: 4px;
}

.review-form .form-group textarea {
  resize: none;
}

.review-form .char-counter {
  margin-top: 5px;
  font-size: 0.8rem;
  text-align: right;
  color: var(--primary-600);
}

.submit-button {
  position: relative;
  display: inline-block;
  width: 100%;
  margin-top: 15px;
  padding: 10px;
  background-color: var(--primary-500);
  color: var(--background);
  border: 1px solid var(--primary-500);
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  overflow: hidden;
  transition:
    color 0.3s ease,
    border-color 0.3s ease;
}

.submit-button span {
  position: relative;
  z-index: 1;
}

.submit-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background-color: var(--background);
  z-index: 0;
  transition: left 0.3s ease;
}

.submit-button:hover::before {
  left: 0;
}

.submit-button:hover {
  color: var(--primary-500);
}

.review-meta {
  font-size: 0.9rem;
  color: var(--letter-black);
  margin-bottom: 10px;
  line-height: 1.4;
  text-align: right;
}

.review-meta .date {
  font-size: 0.8rem;
  font-weight: 600;
}

.review-content {
  margin-top: 15px;
  font-size: 1rem;
  color: var(--letter-black);
  line-height: 1.7;
}
</style>
