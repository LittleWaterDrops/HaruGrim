<template>
  <div>
    <Header />
    <HeroSection />
    <IntroductionSection />
    <ReviewSection />
    <ViewSection />
    <Footer />
  </div>
</template>

<script setup lang="ts">
import Footer from '@/components/Footer/Footer.vue'
import Header from '@/components/Header/Header.vue'
import HeroSection from '@/components/MainViewSection/HeroSection.vue'
import IntroductionSection from '@/components/MainViewSection/IntroductionSection.vue'
import ReviewSection from '@/components/MainViewSection/ReviewSection.vue'
import ViewSection from '@/components/MainViewSection/ViewSection.vue'
</script>
