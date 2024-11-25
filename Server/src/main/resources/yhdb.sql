DROP DATABASE yhdb;
CREATE DATABASE yhdb;
USE yhdb;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL, -- 사용자명
    email VARCHAR(100) UNIQUE NOT NULL, -- 이메일
    password VARCHAR(255) NOT NULL, -- 비밀번호 (해시 처리)
    email_verifㄴed BOOLEAN DEFAULT FALSE, -- 이메일 인증 여부
    social_provider VARCHAR(50) NULL, -- 소셜 로그인 제공자 (ex: 'GOOGLE', 'KAKAO')
    social_id VARCHAR(255) NULL, -- 소셜 로그인 ID
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시각
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시각
    deleted_at TIMESTAMP NULL DEFAULT NULL -- 삭제 시각 (소프트 삭제)
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- 사용자 ID (foreign key)
    title VARCHAR(255) NOT NULL, -- 회고 제목
    content TEXT NOT NULL, -- 회고 내용
    image_urls JSON NULL, -- 이미지 url 리스트
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시각
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시각
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE -- 사용자 삭제 시 관련 회고도 삭제
);

CREATE TABLE tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- 사용자 ID (foreign key)
    token_type VARCHAR(16) NOT NULL, -- 토큰 유형 ('ACCESS' 또는 'REFRESH')
    token VARCHAR(512) NOT NULL, -- 토큰 값 (JWT)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시각
    expires_at TIMESTAMP NOT NULL, -- 만료 시각
    UNIQUE(token), -- 토큰 값은 유일해야 함
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, -- 사용자 삭제 시 토큰도 삭제
    INDEX(user_id) -- user_id 검색 성능 최적화
);


-- 사용자 전체 데이터 확인
SELECT * FROM users;

-- 특정 사용자(username)로 확인
SELECT * FROM users WHERE username = 'testuser1';

-- 이메일 인증 여부 확인
SELECT id, username, email, email_verified FROM users WHERE email_verified = TRUE;

-- 소셜 로그인 사용자 확인
SELECT id, username, social_provider, social_id FROM users WHERE social_provider IS NOT NULL;

-- 삭제된 사용자 확인
SELECT id, username, deleted_at FROM users WHERE deleted_at IS NOT NULL;

-- 전체 리뷰 데이터 확인
SELECT * FROM reviews;

-- 특정 사용자(user_id)의 리뷰 확인
SELECT * FROM reviews WHERE user_id = 10;

-- 특정 제목(title)으로 리뷰 확인
SELECT * FROM reviews WHERE title LIKE '%회고%';

-- 작성된 이미지 URL 리스트 확인
SELECT id, title, image_urls FROM reviews;

-- 작성 시간 기준으로 정렬된 리뷰 목록 확인
SELECT * FROM reviews ORDER BY created_at DESC;

-- 전체 토큰 데이터 확인
SELECT * FROM tokens;

DELETE FROM tokens
WHERE user_id = 2 AND token_type = "REFRESH";

-- 특정 사용자(user_id)의 토큰 확인
SELECT * FROM tokens WHERE user_id = 10;

-- Refresh Token만 조회
SELECT * FROM tokens WHERE token_type = 'REFRESH';

-- 만료된 토큰 확인
SELECT * FROM tokens WHERE expires_at < NOW();

-- 특정 토큰 값(token)으로 조회
SELECT * FROM tokens WHERE token = 'your-refresh-token';

-- 토큰 생성 시간 기준으로 정렬
SELECT * FROM tokens ORDER BY created_at DESC;

-- 전체 이메일 인증 데이터 확인
SELECT * FROM email_verifications;

-- 특정 사용자(user_id)의 이메일 인증 기록 확인
SELECT * FROM email_verifications WHERE user_id = 1;

-- 만료된 이메일 인증 토큰 확인
SELECT * FROM email_verifications WHERE expires_at < NOW();

-- 특정 인증 토큰(verification_token)으로 조회
SELECT * FROM email_verifications WHERE verification_token = 'your-verification-token';

-- 로그인 후 토큰 확인
SELECT * FROM tokens WHERE user_id = (SELECT id FROM users WHERE username = 'yoo');

DELETE FROM tokens WHERE user_id = 8;

SELECT * FROM tokens WHERE token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMCIsImlhdCI6MTczMjE2NzkyNCwiZXhwIjoxNzMyNzcyNzI0fQ.KQARB629oeAy90yFZc1AM_wnJP6dgUK0rilCjbZIKorcHjBsYGBYoWnVXuKErre7tgW6Nz4WRCdN_mdHSo6sBA' AND token_type = 'REFRESH';
