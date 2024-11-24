<template>
  <header class="home-header">
    <router-link to="/" class="logo">
      <img src="https://via.placeholder.com/100x50?text=Logo" alt="로고" />
    </router-link>

    <div class="profile-menu">
      <button @click="toggleDropdown" class="profile-button">
        <img src="https://via.placeholder.com/40x40?text=User" alt="프로필" />
      </button>
      <transition name="dropdown">
        <div v-show="isDropdownOpen" class="dropdown">
          <ul>
            <li @click="logout">로그아웃</li>
            <li @click="viewProfile">회원 정보 조회</li>
          </ul>
        </div>
      </transition>
    </div>
  </header>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'

const isDropdownOpen = ref(false)
const router = useRouter()

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value
}

const logout = async () => {
  const token = localStorage.getItem('accessToken'); // Access Token 가져오기

  if (!token) {
    alert('로그인 상태가 아닙니다. 다시 로그인해주세요.');
    router.push('/login');
    return;
  }

  try {
    await axios.post(
      'http://localhost:8080/auth/logout',
      {}, // 요청 본문은 비어 있음
      {
        headers: {
          Authorization: `Bearer ${token}`, // Authorization 헤더에 Access Token 포함
        },
      }
    );

    // Access Token 삭제
    localStorage.removeItem('accessToken');

    alert('로그아웃되었습니다.');
    router.push('/');
  } catch (error: any) {
    console.error('로그아웃 실패:', error.response?.data || error.message);
    alert('로그아웃 실패: ' + (error.response?.data?.message || '서버 오류'));
  }
};



const viewProfile = () => {
  alert('회원 정보 조회 페이지로 이동합니다.')
  isDropdownOpen.value = false
  router.push('/profile')
}
</script>

<style scoped>
.home-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 0 10%;
  padding-top: 10px;
  padding-bottom: 5px;
  background-color: transparent;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1002;
}

.home-header .logo img {
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.profile-menu {
  position: relative;
  z-index: 1001;
}

.profile-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  outline: none;
}

.profile-button img {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

.dropdown {
  position: absolute;
  top: 50px;
  right: 0;
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
  z-index: 1002;
  min-width: 150px;
}

.dropdown ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.dropdown li {
  padding: 10px 20px;
  cursor: pointer;
  font-size: 14px;
  white-space: nowrap;
}

.dropdown li:hover {
  background-color: #f5f5f5;
}
</style>
