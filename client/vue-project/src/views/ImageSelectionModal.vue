<template>
    <div v-if="isVisible" class="image-selection-modal">
      <h3>생성된 이미지 중 선택하세요</h3>
      <div class="image-options">
        <img
          v-for="(image, index) in images"
          :key="index"
          :src="image"
          class="image-option"
          @click="$emit('select', image)"
          :class="{ selected: selectedImage === image }"
        />
      </div>
      <button @click="$emit('confirm', selectedImage)" :disabled="!selectedImage" class="confirm-button">
        선택 완료
      </button>
    </div>
  </template>
  
  <script setup>
  defineProps({
    isVisible: Boolean,
    images: {
      type: Array,
      required: true,
    },
    selectedImage: {
      type: String,
      default: '',
    },
  });
  </script>
  
  <style scoped>
  .image-selection-modal {
  position: fixed; /* 화면에 고정 */
  top: 50%; /* 세로 중앙 정렬 */
  left: 50%; /* 가로 중앙 정렬 */
  transform: translate(-50%, -50%); /* 정확히 중앙 배치 */
  width: 80%; /* 모달의 너비 */
  max-width: 500px; /* 최대 너비 */
  background-color: white; /* 배경색 */
  border-radius: 8px; /* 모서리 둥글게 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3); /* 그림자 */
  padding: 20px; /* 내부 여백 */
  z-index: 1000; /* 다른 요소보다 위에 표시 */
}

  
  .image-options {
    display: flex;
    gap: 10px;
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
  </style>
  