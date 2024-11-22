<template>
  <div class="signup-container">
    <SignUpHeader />

    <main class="signup-main">
      <p>
        <span>{{ firstLineDisplayed }}</span> <br />
        <span>{{ secondLineDisplayed }}</span>
      </p>
      <form @submit.prevent="handleSignUp">
        <transition name="accordion">
          <div class="form-group" v-if="step >= 1">
            <label for="email">이메일 입력하기 *</label>
            <div class="input-group">
              <input
                type="email"
                id="email"
                v-model="email"
                placeholder="이메일을 입력해주세요"
                :disabled="step > 1"
                required
              />
              <button
                type="button"
                @click="validateEmail"
                class="continue-button"
                v-if="step === 1"
              >
                <span>계속</span>
              </button>
            </div>
          </div>
        </transition>

        <transition name="accordion">
          <div class="form-group" v-if="step >= 2">
            <label for="password">비밀번호 입력하기 *</label>
            <div class="input-group">
              <input
                type="password"
                id="password"
                v-model="password"
                placeholder="비밀번호를 입력해주세요"
                :disabled="step > 2"
                required
              />
              <button
                type="button"
                @click="validatePassword"
                class="continue-button"
                v-if="step === 2"
              >
                <span>계속</span>
              </button>
            </div>
          </div>
        </transition>

        <transition name="accordion">
          <div class="form-group" v-if="step >= 3">
            <label for="username">유저명 입력하기 *</label>
            <div class="input-group">
              <input
                type="text"
                id="username"
                v-model="username"
                placeholder="유저명을 입력해주세요"
                required
              />
              <button
                type="button"
                @click="validateUsername"
                class="continue-button"
                v-if="step === 3"
              >
                <span>계속</span>
              </button>
            </div>
          </div>
        </transition>

        <transition name="accordion">
          <button type="submit" class="login-button" v-if="step === 4">
            <span>가입하기</span>
          </button>
        </transition>
      </form>
    </main>
    <footer class="signup-footer">
      <p>© 2024 Haru Grim, Your Story</p>
    </footer>
  </div>
</template>

<script setup lang="ts">
import SignUpHeader from '@/components/Header/SignUpHeader.vue'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const email = ref('')
const password = ref('')
const username = ref('')
const step = ref(1)
const router = useRouter()

const firstLine = '하루 그림에 오신 것을 환영합니다!'
const secondLine = '오늘 하루를 그림으로 그려볼까요?'

const firstLineDisplayed = ref('')
const secondLineDisplayed = ref('')
const typingInterval = 50

const typeText = () => {
  let firstLineIndex = 0
  let secondLineIndex = 0

  const interval = setInterval(() => {
    if (firstLineIndex < firstLine.length) {
      firstLineDisplayed.value += firstLine[firstLineIndex]
      firstLineIndex++
    } else if (secondLineIndex < secondLine.length) {
      secondLineDisplayed.value += secondLine[secondLineIndex]
      secondLineIndex++
    } else {
      clearInterval(interval)
    }
  }, typingInterval)
}

onMounted(() => {
  typeText()
})

const validateEmail = () => {
  if (!email.value.includes('@')) {
    alert('유효한 이메일 주소를 입력해주세요.')
    return
  }
  step.value = 2
}

const validatePassword = () => {
  if (password.value.length < 6) {
    alert('비밀번호는 최소 6자리 이상이어야 합니다.')
    return
  }
  step.value = 3
}

const validateUsername = () => {
  if (!username.value.trim()) {
    alert('유저명을 입력해주세요.')
    return
  }
  step.value = 4
}

// const handleSignUp = () => {
//   if (email.value && password.value && username.value) {
//     console.log('회원가입 성공:', {
//       email: email.value,
//       password: password.value,
//       username: username.value,
//     })
//     router.push('/welcome')
//   } else {
//     alert('모든 필드를 올바르게 입력해주세요.')
//   }
// }

const handleSignUp = async () => {
  try {
    await axios.post('http://localhost:8080/auth/signup', {
      email: email.value,
      password: password.value,
      username: username.value,
    })

    alert('회원가입 성공! 로그인 페이지로 이동합니다.')
    router.push('/login')
  } catch (error: any) {
    alert('회원가입 실패: ' + (error.response?.data?.message || '알 수 없는 오류'))
  }
}
</script>

<style scoped>
.signup-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  background-color: var(--base-light);
  color: var(--letter-black);
  padding-top: 130px;
}

.signup-main {
  background: var(--background);
  padding: 30px 20px 15px 20px;
  line-height: 1.7;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 600px;
}

.signup-main p {
  font-size: 0.9rem;
  margin-bottom: 20px;
  font-weight: 500;
  min-height: 60px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
  font-size: 0.9rem;
  text-align: left;
}

.input-group {
  display: flex;
  align-items: center;
  gap: 10px;
}

.input-group input {
  flex: 1;
  padding: 10px;
  font-size: 0.7rem;
  border: 1px solid var(--base);
  border-radius: 4px;
}

.continue-button {
  position: relative;
  display: inline-block;
  padding: 10px 15px;
  background-color: var(--background);
  color: var(--success);
  border: 1px solid var(--success);
  border-radius: 4px;
  font-size: 0.9rem;
  cursor: pointer;
  overflow: hidden;
  transition:
    color 0.3s ease,
    border-color 0.3s ease;
}

.continue-button span {
  position: relative;
  z-index: 1;
}

.continue-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background-color: var(--success);
  z-index: 0;
  transition: left 0.3s ease;
}

.continue-button:hover::before {
  left: 0;
}

.continue-button:hover {
  color: var(--background);
}

.login-button {
  position: relative;
  display: inline-block;
  width: 100%;
  padding: 10px;
  background-color: var(--primary-500);
  color: var(--background);
  border: 1px solid var(--primary-500);
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  overflow: hidden;
  transition:
    color 0.3s ease,
    border-color 0.3s ease;
}

.login-button span {
  position: relative;
  z-index: 1;
}

.login-button::before {
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

.login-button:hover::before {
  left: 0;
}

.login-button:hover {
  color: var(--primary-500);
}

.signup-footer {
  margin-top: 20px;
  text-align: center;
  font-size: 0.8rem;
  color: var(--base);
}
</style>
