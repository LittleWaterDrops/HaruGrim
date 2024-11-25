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

-- CREATE TABLE images (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     review_id BIGINT NOT NULL, -- 회고 ID (foreign key)
--     image_url VARCHAR(255) NOT NULL, -- 이미지가 저장된 URL
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 이미지 생성 시각
--     FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE -- 회고 삭제 시 관련 이미지도 삭제
-- );

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


CREATE TABLE email_verifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- 사용자 ID (foreign key)
    verification_token VARCHAR(255) NOT NULL, -- 이메일 인증 토큰
    expires_at TIMESTAMP NOT NULL, -- 토큰 만료 시간
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 토큰 생성 시간
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE -- 사용자 삭제 시 관련 인증 기록도 삭제
);

INSERT INTO users (username, email, password, email_verified) VALUES
('testuser1', 'testuser1@example.com', 'password1', TRUE),
('testuser2', 'testuser2@example.com', 'password2', FALSE),
('testuser3', 'testuser3@example.com', 'password3', TRUE),
('testuser4', 'testuser4@example.com', 'password4', TRUE),
('testuser5', 'testuser5@example.com', 'password5', FALSE);

INSERT INTO reviews (user_id, title, content, image_urls, created_at, updated_at) VALUES
(1, '축구 경기', '오늘은 친구들과 함께 축구 경기를 즐겼다. 모두 열심히 뛰어서 정말 재미있었다.', '["/images/image1.png"]', '2024-11-01 14:30:00', '2024-11-01 16:00:00'),
(1, '생일 파티', '생일 파티에서 친구들이 준비한 케이크와 선물 덕분에 특별한 하루였다.', '["/images/image2.png"]', '2024-11-05 14:30:00', '2024-11-05 16:00:00'),
(1, '학교 생활', '학교에서 새로운 프로젝트를 시작했다. 친구들과 협력하며 재미있는 하루를 보냈다.', '["/images/image3.png"]', '2024-11-10 14:30:00', '2024-11-10 16:00:00'),
(1, '회사 생활', '오늘은 회사에서 중요한 발표를 마쳤다. 긴장했지만 팀원들이 도와줘서 성공적으로 끝냈다.', '["/images/image4.png"]', '2024-11-15 14:30:00', '2024-11-15 16:00:00'),
(1, '헬스장 운동', '헬스장에서 열심히 운동하며 땀을 흘렸다. 몸이 더 건강해지는 기분이 들었다.', '["/images/image5.png"]', '2024-11-20 14:30:00', '2024-11-20 16:00:00'),
(1, '맛있는 저녁', '친구들과 함께 맛있는 저녁 식사를 했다. 오랜만에 웃고 이야기하며 즐거운 시간을 보냈다.', '["/images/image6.png"]', '2024-11-22 14:30:00', '2024-11-22 16:00:00'),
(1, '데이트', '오늘은 연인과 함께 공원에서 데이트를 했다. 날씨도 좋고 정말 행복한 하루였다.', '["/images/image7.png"]', '2024-11-23 14:30:00', '2024-11-23 16:00:00'),
(1, '놀이동산', '놀이동산에서 다양한 놀이기구를 탔다. 정말 신나는 하루를 보냈다.', '["/images/image8.png"]', '2024-11-24 14:30:00', '2024-11-24 16:00:00'),
(1, '영화 관람', '오랜만에 극장에서 영화 한 편을 관람했다. 감동적인 스토리에 눈물이 났다.', '["/images/image9.png"]', '2024-11-25 14:30:00', '2024-11-25 16:00:00');


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
