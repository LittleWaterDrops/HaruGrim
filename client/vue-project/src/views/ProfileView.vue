<template>
  <div class="profile-container">
    <header class="profile-header">
      <button class="back-button" @click="goBack">←</button>
      <div class="logo">
        <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" />
      </div>
    </header>

    <main class="profile-main">
      <div class="form-group">
        <label for="username">유저명</label>
        <input type="text" id="username" v-model="profile.username" readonly />
      </div>
      <div class="form-group">
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="profile.email" readonly />
      </div>
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="profile.password" readonly />
      </div>
      <button class="delete-account-button" @click="deleteAccount">회원 탈퇴</button>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const profile = ref({
  username: '사용자명',
  email: 'example@example.com',
  password: '********',
})

const goBack = () => {
  router.back()
}

const deleteAccount = () => {
  const confirmed = confirm('정말로 회원 탈퇴를 진행하시겠습니까?')
  if (confirmed) {
    alert('회원 탈퇴가 완료되었습니다.')
    router.push('/')
  }
}
</script>

<style scoped>
.profile-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 600px;
  margin: 0 auto;
  padding-top: 30px;
}

.profile-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
}

.back-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 5px;
  color: var(--letter-black);
}

.logo {
  flex: 1;
  text-align: center;
}

.logo img {
  width: 100px;
  height: auto;
}

.profile-main {
  width: 100%;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-size: 1rem;
  color: var(--letter-black);
  font-weight: 600;
}

.form-group input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid var(--base);
  border-radius: 4px;
  background-color: var(--base-light);
  color: var(--letter-black);
}

.form-group input:readonly {
  background-color: var(--base-light);
}

.delete-account-button {
  width: 100%;
  padding: 10px;
  background-color: var(--base-light);
  color: var(--base);
  font-size: 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.delete-account-button:hover {
  background-color: var(--danger);
  color: var(--background);
  font-weight: 500;
}
</style>
