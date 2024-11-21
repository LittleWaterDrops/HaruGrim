<template>
  <footer class="footer">
    <div class="footer-content">
      <span class="link">서비스 이용약관</span>
      <span> | </span>
      <span class="link">개인정보 처리방침</span>
      <p>
        <strong>(주)하루그림</strong> | 대표: 유영신 | 주소: 서울 강남구 테헤란로 212, 멀티캠퍼스
        3층
      </p>

      <div class="last-line">
        <div>Copyright © 하루그림(주) All Rights Reserved.</div>
        <div class="footer-icons">
          <a
            href="https://github.com/LittleWaterDrops/YH-final-project"
            target="_blank"
            class="icon"
            aria-label="GitHub"
          >
            <img
              src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"
              alt="GitHub"
            />
          </a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts"></script>

<style scoped>
.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 10%;
  background-color: var(--background);
  color: var(--letter-black);
  border-top: 1px solid var(--base-light);
}

.link:hover {
  color: var(--primary-700);
  cursor: pointer;
}

.footer-content {
  flex: 1;
  font-size: 0.9rem;
  line-height: 1.5;
}

.footer-content p {
  margin: 5px 0;
}

.last-line {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.footer-icons .icon img {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: var(--base-light);
  transition: background-color 0.3s ease;
}
</style>
