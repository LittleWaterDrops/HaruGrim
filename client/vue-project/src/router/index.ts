import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import MainView from '@/views/MainView.vue'
import ProfileView from '@/views/ProfileView.vue'
import SignUpView from '@/views/SignUpView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main view',
      component: MainView,
    },
    {
      path: '/login',
      name: 'login view',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'signup view',
      component: SignUpView,
    },
    {
      path: '/home',
      name: 'home view',
      component: HomeView,
    },
    {
      path: '/profile',
      name: 'profile view',
      component: ProfileView,
    },
  ],
})

export default router
