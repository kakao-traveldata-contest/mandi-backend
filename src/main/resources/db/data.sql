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
VALUES
    ('hello world', 'lsh901673@gmail.com', '', 'lsh', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now()),
    ('hello', 'jhy0285@gmail.com', '', '조영진', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now()),
    ('hello', 'hcy1722@korea.ac.kr', '', 'hcy', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now()),
    ('hello', 'sangmin0205@gmail.com', '', 'sm', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now()),
    ('hello', 'chosohi20@gmail.com', '', 'csh', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now()),
    ('hello', 'vnclfjqm12@gmail.com', '', 'lsy', '1234', 'PROVIDER_GOOGLE', 'ROLE_USER', now(), now())
;

INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 1, now(), now());

INSERT INTO user_badge_tb(user_id, badge_id, created_at, last_modified_at)
VALUES(1, 2, now(), now());

-- 카테고리: 'TOURISM', 'TREKKING', 'DINING', 'ACCOMMODATION', 'OTHER'

-- User ID 1과 2 사용, 카테고리는 'TOURISM', 'DINING' 사용
INSERT INTO post_tb (user_id, category, content, title, like_cnt, created_at, last_modified_at)
VALUES
    (1, 'TOURISM', 'This is a post about tourism.', 'Tourism Post', 10, now(), now()),
    (2, 'DINING', 'This is a post about dining.', 'Dining Post', 5, now(), now());


INSERT INTO course_tb(course_id, name, distance, difficulty, duration, img_url,
                      rating_average, route_url,
                      mid_latitude, mid_longitude,
                      start_point_name, start_address, start_latitude, start_longitude,
                      end_point_name, end_address, end_latitude, end_longitude,
                      created_at, last_modified_at)
VALUES (1, 'Namparang Trail Course 1', 19.0, 'Moderate', '7h', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_1.gpx',
        35.128745186417916, 129.07481501067562,
        'Oryukdo Sunrise Park', 'Busan, Nam-gu, 용호제2동 산198', 35.09969000704587, 129.12371004000306,
        'Busan Station', 'Choryang-dong, Busan', 35.11454483494164, 129.0407629776746,
        now(), now()),
       (2, 'Namparang Trail Course 2', 18.9, 'Moderate', '7h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_2.gpx',
        35.09047477506102, 129.05427125282586,
        'Busan Station', 'Choryang-dong, Busan', 35.11454483494164, 129.0407629776746,
        'Yeongdo Bridge', 'Nampo-dong 1(il)-ga, Yeongdo-gu, Busan', 35.096556935459375, 129.03596760705113,
        now(), now()),
       (3, 'Namparang Trail Course 3', 14.9, 'Moderate', '5h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_3.gpx',
        35.0599085725844, 129.01863399893045,
        'Yeongdo Bridge', 'Nampo-dong 1(il)-ga, Yeongdo-gu, Busan', 35.096556935459375, 129.03596760705113,
        'Gamcheon Sageori', 'Busan', 35.08746676146984, 129.00448727421463,
        now(), now()),
       (4, 'Namparang Trail Course 4', 21.8, 'Easy', '7h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_4.gpx',
        35.04962791688741, 128.97025767713785,
        'Gamcheon Sageori', 'Busan', 35.08747070096433, 129.00448073633015,
        'Sinpyeong-dong Intersection', 'Busan', 35.09638778865339, 128.9553397335112,
        now(), now()),
       (5, 'Namparang Trail Course 5', 21.9, 'Easy', '7h', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_5.gpx',
        35.086464453488595, 128.91221777535975,
        'Sinpyeong-dong Intersection', 'Busan', 35.096398433670394, 128.95534375682473,
        'Sinpyeong Park', '1455 Songjeong-dong, Gangseo-gu, Busan', 35.09804354980587, 128.82239227183163,
        now(), now()),
       (6, 'Namparang Trail Course 6', 14.8, 'Easy', '5h', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/NamparangTrail_6.gpx',
        35.11484373360872, 128.7845040578395,
        'Sinpyeong Park', '1455 Songjeong-dong, Gangseo-gu, Busan', 35.09804069995878, 128.82239311002195,
        'Jadeok Intersection', 'Changwon-si', 35.10188925080001, 128.74467291869223,
        now(), now()),
       (7, 'Haeparang Trail Course 1', 16.9, 'Moderate', '6h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://mandi-courses.s3.ap-northeast-2.amazonaws.com/HaeparangTrail_1.gpx',
        35.122557012364254, 129.12364399060607,
        'Oryukdo Sunrise Park', 'Busan, Nam-gu, 용호제2동 산198', 35.09968874976039, 129.12370610050857,
        'Haeundae-gu', 'Busan', 35.158975124359124, 129.16027123108506,
        now(), now());

INSERT INTO completed_course_tb(completed_course_id, user_id, course_id, distance, trekking_path_image_url, is_reviewed,
                                review_content, review_score, reviewed_at, started_at, completed_at, created_at, last_modified_at)
VALUES (1, 3, 1, 0.875, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 1, 'good', 5, now(), DATEADD('MINUTE', -35, now()), now(), now(), now()),
       (2, 3, 2, 1.23, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 1, 'nice', 4, now(), DATEADD('MINUTE', -60, now()), now(), now(), now()),
       (3, 3, 3, 2.7, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 0, null, null, null, DATEADD('MINUTE', -84, now()), now(), now(), now()),
       (4, 4, 4, 0.875, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 1, 'good', 5, now(), DATEADD('MINUTE', -27, now()), now(), now(), now()),
       (5, 4, 5, 1.23, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 1, 'nice', 4, now(), DATEADD('MINUTE', -60, now()), now(), now(), now()),
       (6, 4, 6, 4.0, 'https://github.com/user-attachments/assets/03fea9d2-c196-4a8a-84e0-4e10c36ecfc6', 0, null, null, null, DATEADD('MINUTE', -72, now()), now(), now(), now())
;

-- 기본 댓글 (parent_comment_id가 NULL)
INSERT INTO comment_tb (post_id, parent_comment_id, user_id, content, like_cnt, created_at, last_modified_at)
VALUES
    (1, NULL, 1, 'This is a comment on the first post.', 5, now(), now()),  -- comment_id = 1
    (2, NULL, 2, 'Another comment on a different post.', 3, now(), now()); -- comment_id = 2

-- 대댓글 (parent_comment_id가 기존 댓글의 comment_id)
INSERT INTO comment_tb (post_id, parent_comment_id, user_id, content, like_cnt, created_at, last_modified_at)
VALUES
    (1, 1, 2, 'This is a reply to the first comment on post 1.', 2, now(), now()),  -- comment_id = 3
    (2, 2, 1, 'A reply to the second post comment.', 1, now(), now()),            -- comment_id = 4
    (2, 2, 1, 'A reply to the 젠장 마커스! post comment.', 1, now(), now());   -- comment_id = 5
