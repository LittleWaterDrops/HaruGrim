<template>
  <section class="financial-overview">
    <div class="description">
      매일의 회고를 쉽고 아름답게 시각화하세요.<br />
      당신의 하루가 더 특별해지는 순간, 한 장의 그림으로 간편하게 기록하고,<br />
      그림처럼 남은 일상이 더 풍요롭게 느껴질 거예요.
    </div>
  </section>
</template>

<script setup lang="ts"></script>

<style scoped>
.financial-overview {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 80px 20px;
  background-color: var(--base-light);
  color: var(--letter-black);
}

.description {
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.7;
  color: var(--letter-black);
  margin-bottom: 10px;
}
</style>
