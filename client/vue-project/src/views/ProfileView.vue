<template>
  <div class="profile-container">
    <header class="profile-header">
      <button class="back-button" @click="goBack">←</button>
      <div class="logo">
        <img src="/images/logo3.png" alt="로고" />
      </div>
    </header>

    <main class="profile-main">
      <!-- 로딩 상태 -->
      <div v-if="loading" class="loading-message">프로필 정보를 불러오는 중...</div>

      <!-- 에러 메시지 -->
      <div v-if="error" class="error-message">{{ error }}</div>

      <!-- 프로필 정보 -->
      <div v-else>
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
        <button class="delete-account-button" @click="deleteAccount">
          <span>회원 탈퇴</span>
        </button>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const profile = ref({
  username: '',
  email: '',
  password: '********',
})


const loading = ref(true); // 로딩 상태
const error = ref(''); // 에러 메시지

const fetchProfile = async () => {
  try {
    loading.value = true;
    error.value = '';

    const token = localStorage.getItem('accessToken'); // 토큰 가져오기
    if (!token) {
      throw new Error('로그인 상태가 아닙니다. 다시 로그인해주세요.');
    }

    const response = await axios.get('http://localhost:8080/users/my', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    profile.value = {
      username: response.data.username,
      email: response.data.email,
      password: '********', // 비밀번호는 항상 마스킹 처리
    };
  } catch (err) {
    console.error('프로필 정보를 가져오는 중 오류:', err);
    error.value = '프로필 정보를 불러오는 중 오류가 발생했습니다.';
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchProfile();
});


const goBack = () => {
  router.back()
}

const deleteAccount = async () => {
  const token = localStorage.getItem('accessToken'); // Access Token 가져오기

  if (!token) {
    alert('로그인 상태가 아닙니다. 다시 로그인해주세요.');
    router.push('/login');
    return;
  }

  const confirmed = confirm('정말로 회원 탈퇴를 진행하시겠습니까?');
  if (!confirmed) {
    return;
  }

  try {
    await axios.delete('http://localhost:8080/auth/delete', {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 Access Token 포함
      },
    });

    // 로컬 스토리지에서 Access Token 삭제
    localStorage.removeItem('accessToken');

    alert('회원 탈퇴가 완료되었습니다.');
    router.push('/');
  } catch (error: any) {
    console.error('회원 탈퇴 실패:', error.response?.data || error.message);
    alert('회원 탈퇴 실패: ' + (error.response?.data?.message || '서버 오류'));
  }
};

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
  width: 150px;
  height: auto;
}

.profile-main {
  width: 100%;
  background: #fff;
  padding: 50px 30px 40px 30px;
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
  position: relative;
  display: inline-block;
  width: 100%;
  margin-top: 30px;
  padding: 10px;
  background-color: var(--background);
  color: var(--danger);
  font-size: 1rem;
  border: 1px solid var(--danger);
  border-radius: 4px;
  cursor: pointer;
  overflow: hidden;
  transition:
    color 0.3s ease,
    border-color 0.3s ease;
}

.delete-account-button span {
  position: relative;
  z-index: 1;
}

.delete-account-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background-color: var(--danger);
  z-index: 0;
  transition: left 0.3s ease;
}

.delete-account-button:hover::before {
  left: 0;
}

.delete-account-button:hover {
  color: var(--background);
}
</style>
