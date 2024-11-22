<template>
  <div class="login-container">
    <header class="login-header">
      <router-link to="/" class="logo">
        <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" />
      </router-link>
    </header>

    <div class="title">
      <h1>하루 그림에 로그인 해주세요.</h1>
    </div>

    <main class="login-main">
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="email">이메일</label>
          <input
            type="email"
            id="email"
            v-model="email"
            placeholder="이메일을 입력해주세요"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="비밀번호를 입력해주세요"
            required
          />
        </div>
        <button type="submit" class="login-button">로그인</button>
      </form>
      <p class="signup-text">
        새로 오셨나요? <router-link to="/signup">계정 생성하기</router-link>
      </p>
    </main>
    <footer class="login-footer">
      <p>© 2024 Haru Grim, Your Story</p>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const router = useRouter()

const handleLogin = () => {
  if (email.value && password.value) {
    console.log('로그인 성공:', email.value)
    // router.push('/dashboard')
  } else {
    alert('이메일과 비밀번호를 입력해주세요.')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 30px;
  min-height: 100vh;
  background-color: var(--base-light);
  color: #333;
}

.login-header .logo {
  width: 120px;
  margin-bottom: 20px;
}

.title {
  margin: 20px;
}

.title h1 {
  font-size: 1.7rem;
  color: var(--letter-black);
}

.login-main {
  background-color: var(--background);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.login-main h1 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-size: 0.9rem;
}

.form-group input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: var(--primary-500);
  color: var(--background);
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.login-button:hover {
  background-color: var(--primary-600);
}

.signup-text {
  margin-top: 20px;
  text-align: center;
  font-size: 0.9rem;
}

.signup-text a {
  color: var(--primary-500);
  text-decoration: none;
}

.signup-text a:hover {
  color: var(--primary-600);
  text-decoration: underline;
}

.logo img {
  width: 100px;
  height: auto;
  cursor: pointer;
}

.login-footer {
  margin-top: 20px;
  text-align: center;
  font-size: 0.8rem;
  color: #777;
}
</style>
