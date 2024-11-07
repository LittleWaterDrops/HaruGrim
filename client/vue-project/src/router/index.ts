import MainView from '@/views/MainView.vue'
import SecondView from '@/views/SecondView.vue'
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
      path: '/second',
      name: 'second view',
      component: SecondView,
    },
  ],
})

export default router
