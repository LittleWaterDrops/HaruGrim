<template>
  <header :class="['header', { 'header-scrolled': isScrolled }]">
    <div class="header-content">
      <div class="logo" @click="reloadPage">
        <!-- <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" /> -->
        <img src="`/images/logo3.png`" alt="로고" />
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const reloadPage = () => {
  window.location.href = '/'
}

const isScrolled = ref(false)

const handleScroll = () => {
  isScrolled.value = window.scrollY > 0
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
.header {
  width: 100%;
  padding: 0 10%;
  padding-top: 10px;
  padding-bottom: 5px;
  background-color: var(--background);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
}

.header-scrolled {
  border-bottom: 1px solid var(--base-light);
}

.logo img {
  width: 100px;
  height: 30px;
  cursor: pointer;
}
</style>
