INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img01', 'badge01', 'conditions01', now(), now());

INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img02', 'badge02', 'conditions02', now(), now());

INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img03', 'badge03', 'conditions03', now(), now());

INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img04', 'badge04', 'conditions04', now(), now());

INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img05', 'badge05', 'conditions05', now(), now());

INSERT INTO badge_tb(img_url, name, requirements, created_at, last_modified_at)
VALUES ('test_img06', 'badge06', 'conditions06', now(), now());

INSERT INTO user_tb(description, email, img_url, nickname, password, provider, role, created_at, last_modified_at)
VALUES ( 'hello world', 'lsh901673@gmail.com', '', 'lsh', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now());



INSERT INTO user_tb(description, email, img_url, nickname, password, provider, role, created_at, last_modified_at)
VALUES ( 'hello', 'jhy0285@gmail.com', '', '조영진존잘', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now());


INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 1, now(), now());

INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 2, now(), now());



INSERT INTO course_tb(course_id, name, distance, created_at, last_modified_at)
VALUES (1, 'Sinseondae', 3.2, now(), now()),
       (2, 'Galmaet-gil', 2.7, now(), now());

-- INSERT INTO completed_course_tb(completed_course_id, user_id, course_id, is_reviewed, review_content, review_score,
--                                 reviewed_at, created_at, last_modified_at)
-- VALUES (1, 2, 1, 1, 'good', 5, now(), now(), now()),
--        (2, 2, 2, 0, null, null, null, now(), now());



-- 카테고리: 'TOURISM', 'TREKKING', 'DINING', 'ACCOMMODATION', 'OTHER'

-- User ID 1과 2 사용, 카테고리는 'TOURISM', 'DINING' 사용
INSERT INTO post_tb (user_id, category, content, title, like_cnt, created_at, last_modified_at)
VALUES
    (1, 'TOURISM', 'This is a post about tourism.', 'Tourism Post', 10, now(), now()),
    (2, 'DINING', 'This is a post about dining.', 'Dining Post', 5, now(), now()),
    (1, 'TREKKING', 'Trekking experience post content.', 'Trekking Post', 15, now(), now()),
    (2, 'ACCOMMODATION', 'Great accommodation tips.', 'Accommodation Post', 7, now(), now()),
    (2, 'DINING', 'This is a post about ssssdining.', 'Dining Post', 15, now(), now()),
    (1, 'OTHER', 'Random content.', 'Miscellaneous Post', 3, now(), now());


-- PostImage 테이블에 이미지 데이터 추가
-- post_id는 post_tb의 게시글 ID를 참조합니다.

INSERT INTO post_image_tb (post_id, url)
VALUES
    (1, 'https://example.com/image1.jpg'),
    (1, 'https://example.com/image2.jpg'),
    (2, 'https://example.com/image3.jpg'),
    (3, 'https://example.com/image4.jpg'),
    (3, 'https://example.com/image5.jpg'),
    (4, 'https://example.com/image6.jpg');
