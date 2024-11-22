<template>
  <div class="signup-container">
    <SignUpHeader />

    <main class="signup-main">
      <p>
        하루 그림에 오신 것을 환영합니다! <br />
        오늘 하루를 그림으로 그려볼까요?
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
                계속
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
                계속
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
                계속
              </button>
            </div>
          </div>
        </transition>

        <transition name="accordion">
          <button type="submit" class="login-button" v-if="step === 4">회원가입</button>
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const username = ref('')
const step = ref(1)
const router = useRouter()

const validateEmail = () => {
  if (!email.value.includes('@')) {
    alert('유효한 이메일 주소를 입력해주세요.')
    return
  }
  console.log('이메일 유효성 확인:', email.value)
  step.value = 2
}

const validatePassword = () => {
  if (password.value.length < 6) {
    alert('비밀번호는 최소 6자리 이상이어야 합니다.')
    return
  }
  console.log('비밀번호 유효성 확인:', password.value)
  step.value = 3
}

const validateUsername = () => {
  if (!username.value.trim()) {
    alert('유저명을 입력해주세요.')
    return
  }
  console.log('유저명 유효성 확인:', username.value)
  step.value = 4
}

const handleSignUp = () => {
  if (email.value && password.value && username.value) {
    console.log('회원가입 성공:', {
      email: email.value,
      password: password.value,
      username: username.value,
    })
    router.push('/welcome')
  } else {
    alert('모든 필드를 올바르게 입력해주세요.')
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
  padding: 30px 20px 5px 20px;
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
  font-size: 1rem;
  border: 1px solid var(--base);
  border-radius: 4px;
}

.continue-button {
  padding: 10px 15px;
  background-color: var(--background);
  color: var(--success);
  border: 1px solid var(--success);
  border-radius: 4px;
  font-size: 0.9rem;
  cursor: pointer;
}

.continue-button:hover {
  background-color: var(--success);
  color: var(--background);
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

.signup-footer {
  margin-top: 20px;
  text-align: center;
  font-size: 0.8rem;
  color: var(--base);
}
</style>
