<template>
  <section class="hero">
    <div class="hero-content">
      <h1 class="hero-title fade-in">하루를 돌아보고,<br />나만의 이야기를 그려보세요.</h1>

      <div class="button-group fade-in" v-if="!isLoggedIn">
        <router-link to="/login">
          <button class="login-btn fade-in">
            <span>로그인</span>
          </button>
        </router-link>
        <router-link to="/signUp">
          <button class="signup-btn fade-in">
            <span>회원가입</span>
          </button>
        </router-link>
      </div>

      <div class="button-group fade-in" v-else>
        <router-link to="/home">
          <button class="login-btn fade-in">
            <span>회고 작성</span>
          </button>
        </router-link>
      </div>

      <div class="explore fade-in">
        <p>
          <router-link to="/home">
            <button class="explore-btn">둘러볼게요!</button>
          </router-link>
        </p>
        <small>둘러보신다면, 글쓰기 등의 기능이 제한될 수 있어요.</small>
      </div>
    </div>

    <div class="scroll-icon bounce">⬇</div>
  </section>
</template>

<script setup lang="ts">

import { ref, onMounted } from 'vue';

const isLoggedIn = ref(false); // 로그인 상태

onMounted(() => {
  const token = localStorage.getItem('accessToken');
  isLoggedIn.value = !!token; // 토큰 존재 여부로 로그인 상태 판단
});

</script>

<style>
.hero {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  width: 100%;
  height: 100vh;
  color: var(--letter-black);
  background:
    /* linear-gradient(to bottom, #ffffff, transparent), */ url('https://images.unsplash.com/photo-1543497415-75c0a27177c0?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fCVFQyVCMSU4NSUyMCVFRCU4QyU4QyVFQiU5RSU4MCVFQyU4MyU4OXxlbnwwfHwwfHx8MA%3D%3D')
    no-repeat center center/cover;
}

.hero-content {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  flex-direction: column;
  gap: 20px;
  padding-bottom: 20vh;
}

.hero-title {
  font-size: 3rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--letter-black);
  margin-bottom: 20px;
  animation-delay: 0.3s;
}

.button-group {
  display: flex;
  gap: 50px;
  animation-delay: 0.9s;
}

.login-btn,
.signup-btn {
  position: relative;
  display: inline-block;
  padding: 10px 30px;
  margin-bottom: 2px;
  font-size: 1rem;
  font-weight: 500;
  text-decoration: none;
  text-transform: uppercase;
  background-color: transparent;
  color: var(--letter-black);
  border-radius: 10px;
  cursor: pointer;
  overflow: hidden;
  border: 2px solid transparent;
  outline: 2px solid var(--letter-black);
  transition:
    color 0.3s ease,
    border-radius 0.3s ease,
    transform 0.3s ease;
}

.login-btn:hover,
.signup-btn:hover {
  color: var(--background);
  outline-color: var(--letter-black);
}

.login-btn span,
.signup-btn span {
  position: relative;
  z-index: 1;
}

.login-btn::before,
.signup-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background-color: var(--letter-black);
  z-index: 0;
  transition: left 0.3s ease;
}

.login-btn:hover::before,
.signup-btn:hover::before {
  left: 0;
}

.explore {
  font-size: 0.9rem;
  flex-direction: column;
  text-align: center;
  animation-delay: 1.5s;
}

.explore p button {
  font-size: 0.9rem;
  color: var(--base);
  font-weight: 600;
  margin-bottom: 5px;
  border: 0 solid transparent;
  background-color: transparent;
  cursor: pointer;
}

.explore p button:hover {
  color: var(--letter-black);
}

.scroll-icon {
  margin-bottom: 30px;
  font-size: 1.5rem;
  color: var(--base-light);
}
</style>
