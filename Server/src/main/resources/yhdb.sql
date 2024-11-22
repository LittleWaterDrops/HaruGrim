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