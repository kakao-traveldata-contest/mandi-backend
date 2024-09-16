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
VALUES ( '오하이요 세상아', 'jhy0285@gmail.com', '', '조영진존잘', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now());


INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 1, now(), now());

INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 2, now(), now());


INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(2, 1, now(), now());

INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(2, 2, now(), now());

INSERT INTO course_tb(course_id, name, distance, created_at, last_modified_at)
VALUES (1, 'Sinseondae', 3.2, now(), now()),
       (2, 'Galmaet-gil', 2.7, now(), now());

-- INSERT INTO completed_course_tb(completed_course_id, user_id, course_id, is_reviewed, review_content, review_score,
--                                 reviewed_at, created_at, last_modified_at)
-- VALUES (1, 2, 1, 1, 'good', 5, now(), now(), now()),
--        (2, 2, 2, 0, null, null, null, now(), now());
