<template>
  <header class="signup-header">
    <router-link to="/" class="logo">
      <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" />
    </router-link>
    <div class="login-text">
      <div class="already-text">이미 계정이 있나요?</div>
      <router-link to="/login" class="login-link">로그인 -></router-link>
    </div>
  </header>
</template>

<script setup lang="ts"></script>

<style scoped>
.signup-header {
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

.signup-header .login-text {
  flex-direction: row;
  display: flex;
  gap: 10px;
}

.signup-header .already-text {
  align-self: center;
  font-size: 0.9rem;
  color: var(--base);
  text-decoration: none;
}

.signup-header .login-link {
  align-self: center;
  font-size: 0.9rem;
  color: var(--letter-black);
  text-decoration: none;
}

.signup-header .login-link:hover {
  text-decoration: underline;
}

.signup-header .logo img {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
