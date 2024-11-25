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
      <div v-if="isImageSelectionVisible">
        <div class="image-selection-modal">
          <h3>생성된 이미지 중 선택하세요</h3>
          <div class="image-options">
            <img
              v-for="(image, index) in generatedImages"
              :key="index"
              :src="image"
              class="image-option"
              @click="selectImage(image)"
              :class="{ selected: selectedImage === image }"
            />
          </div>
          <button @click="finalizeReview" :disabled="!selectedImage" class="confirm-button">
            선택 완료
          </button>
        </div>
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
                :date="formatDateToYMD(item.updatedAt)"
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
                style="margin-top: 20px; position: relative"
              >
                <span v-if="!isSubmitting">이미지 생성</span>
                <span v-else>
                  <div class="loading-spinner"></div>
                  이미지 생성 중...
                </span>
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
import axios from 'axios'
import router from '@/router'

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

const formatDateToYMD = (dateString: string | undefined): string => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toISOString().split('T')[0]; // YYYY-MM-DD 형식 반환
};



const items = ref([
  {
    id: 1,
    title: '축구 경기',
    content: '오늘은 친구들과 함께 축구 경기를 즐겼다. 모두 열심히 뛰어서 정말 재미있었다.',
    imageUrl: '/images/image1.png',
    createdAt: '2024-11-01 14:30',
    updatedAt: '2024-11-01 16:00',
  },
  {
    id: 2,
    title: '생일 파티',
    content: '생일 파티에서 친구들이 준비한 케이크와 선물 덕분에 특별한 하루였다.',
    imageUrl: '/images/image2.png',
    createdAt: '2024-11-05 14:30',
    updatedAt: '2024-11-05 16:00',
  },
  {
    id: 3,
    title: '학교 생활',
    content: '학교에서 새로운 프로젝트를 시작했다. 친구들과 협력하며 재미있는 하루를 보냈다.',
    imageUrl: '/images/image3.png',
    createdAt: '2024-11-10 14:30',
    updatedAt: '2024-11-10 16:00',
  },
  {
    id: 4,
    title: '회사 생활',
    content:
      '오늘은 회사에서 중요한 발표를 마쳤다. 긴장했지만 팀원들이 도와줘서 성공적으로 끝냈다.',
    imageUrl: '/images/image4.png',
    createdAt: '2024-11-15 14:30',
    updatedAt: '2024-11-15 16:00',
  },
  {
    id: 5,
    title: '헬스장 운동',
    content: '헬스장에서 열심히 운동하며 땀을 흘렸다. 몸이 더 건강해지는 기분이 들었다.',
    imageUrl: '/images/image5.png',
    createdAt: '2024-11-20 14:30',
    updatedAt: '2024-11-20 16:00',
  },
  {
    id: 6,
    title: '맛있는 저녁',
    content:
      '친구들과 함께 맛있는 저녁 식사를 했다. 오랜만에 웃고 이야기하며 즐거운 시간을 보냈다.',
    imageUrl: '/images/image6.png',
    createdAt: '2024-11-22 14:30',
    updatedAt: '2024-11-22 16:00',
  },
  {
    id: 7,
    title: '데이트',
    content: '오늘은 연인과 함께 공원에서 데이트를 했다. 날씨도 좋고 정말 행복한 하루였다.',
    imageUrl: '/images/image7.png',
    createdAt: '2024-11-23 14:30',
    updatedAt: '2024-11-23 16:00',
  },
  {
    id: 8,
    title: '놀이동산',
    content: '놀이동산에서 다양한 놀이기구를 탔다. 정말 신나는 하루를 보냈다.',
    imageUrl: '/images/image8.png',
    createdAt: '2024-11-24 14:30',
    updatedAt: '2024-11-24 16:00',
  },
  {
    id: 9,
    title: '영화 관람',
    content: '오랜만에 극장에서 영화 한 편을 관람했다. 감동적인 스토리에 눈물이 났다.',
    imageUrl: '/images/image9.png',
    createdAt: '2024-11-25 14:30',
    updatedAt: '2024-11-25 16:00',
  },
])




const reviewTitle = ref('')
const reviewContent = ref('')

const toggleViewMode = (view: 'list' | 'gallery') => {
  viewMode.value = view
}

const handleWriteButtonClick = () => {

  if (isReviewFormOpen.value || isReviewViewOpen.value) {
    isReviewFormOpen.value = false;
    isReviewViewOpen.value = false;
    selectedItem.value = null;
    resetReviewForm();
  } else {
    const token = localStorage.getItem('accessToken'); // Access Token 확인

    if (!token) {
      alert('로그인 상태가 아닙니다. 회고 작성을 위해 로그인이 필요합니다.');
      return;
    }

    isReviewFormOpen.value = true;
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

const isImageSelectionVisible = ref(false) // 이미지 선택 UI 표시 여부
const generatedImages = ref<string[]>([]) // 생성된 이미지 리스트
const selectedImage = ref<string>('') // 사용자가 선택한 이미지

const selectImage = (image: string) => {
  selectedImage.value = image // 선택한 이미지 저장
}

const submitReview = async () => {
  if (!reviewTitle.value || !reviewContent.value) {
    alert('제목과 내용을 입력해주세요.')
    return
  }

  try {
    isSubmitting.value = true

    // 1. 이미지 3개 생성 요청
    const token = localStorage.getItem('accessToken')
    const response = await axios.post(
      'http://localhost:8080/reviews/text-to-image',
      { title: reviewTitle.value, content: reviewContent.value },
      { headers: { Authorization: `Bearer ${token}` } },
    )

    generatedImages.value = response.data // 이미지 URL 배열
    console.log('Generated images:', generatedImages.value)

    // 2. 이미지 선택 모달 표시
    isImageSelectionVisible.value = true
  } catch (error) {
    console.error('이미지 생성 중 오류:', error)
    alert('이미지 생성 중 문제가 발생했습니다.')
  } finally {
    isSubmitting.value = false
  }
}

const finalizeReview = async () => {
  if (!selectedImage.value) {
    alert('이미지를 선택해주세요.')
    return
  }

  try {
    isSubmitting.value = true

    // 서버로 보낼 데이터
    const token = localStorage.getItem('accessToken')
    const response = await axios.post(
      'http://localhost:8080/reviews',
      {
        title: reviewTitle.value, // 회고 제목
        content: reviewContent.value, // 회고 내용
        imageUrls: [selectedImage.value], // 선택된 이미지를 리스트로 전달
      },
      { headers: { Authorization: `Bearer ${token}` } },
    )

    const createdReview = response.data
    items.value.push({
      id: createdReview.id,
      title: reviewTitle.value,
      content: reviewContent.value,
      imageUrl: selectedImage.value, // 갤러리 리스트에서는 단일 이미지로 표시
      createdAt: createdReview.createdAt,
      updatedAt: createdReview.updatedAt,
    })
    console.log(items.value)

    alert('회고가 작성되었습니다!')
    resetReviewForm()
  } catch (error) {
    console.error('회고 저장 중 오류:', error)
    alert('회고 저장 중 문제가 발생했습니다.')
  } finally {
    isSubmitting.value = false
    isImageSelectionVisible.value = false
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
    /* linear-gradient(to bottom, #ffffff, transparent), */ url('https://cdn.pixabay.com/photo/2021/03/23/22/00/eggs-6118730_1280.png')
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

.image-selection-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  width: 80%;
  max-width: 500px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  padding: 20px;
  z-index: 1000;
}

.image-options {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin: 20px 0;
}

.image-option {
  width: 100px;
  height: auto;
  cursor: pointer;
  border: 2px solid transparent;
  transition: border-color 0.3s ease;
}

.image-option.selected {
  border-color: #4caf50;
}

.confirm-button {
  display: block;
  margin: 0 auto;
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.confirm-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

/* 로딩 스피너 스타일 */
.loading-spinner {
  display: inline-block;
  width: 16px;
  height: 16px;
  margin-right: 8px;
  border: 2px solid transparent;
  border-top: 2px solid var(--primary-500);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

/* 로딩 스피너 애니메이션 */
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
