<template>
  <header class="home-header">
    <router-link to="/" class="logo">
      <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" />
    </router-link>
  </header>
</template>

<script setup lang="ts"></script>

<style scoped>
.home-header {
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 0 10%;
  padding-top: 10px;
  padding-bottom: 5px;
  background-color: transparent;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
}

.home-header .logo img {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
