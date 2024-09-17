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

-- INSERT INTO course_tb(course_id, name, distance, created_at, last_modified_at)
-- VALUES (1, 'Sinseondae', 3.2, now(), now()),
--        (2, 'Galmaet-gil', 2.7, now(), now());

-- INSERT INTO completed_course_tb(completed_course_id, user_id, course_id, is_reviewed, review_content, review_score,
--                                 reviewed_at, created_at, last_modified_at)
-- VALUES (1, 2, 1, 1, 'good', 5, now(), now(), now()),
--        (2, 2, 2, 0, null, null, null, now(), now());

INSERT INTO course_tb(course_id, name, distance, difficulty, duration, img_url,
                      rating_average, route_url,
                      mid_latitude, mid_longitude,
                      start_point_name, start_address, start_latitude, start_longitude,
                      end_point_name, end_address, end_latitude, end_longitude,
                      created_at, last_modified_at)
VALUES (1, 'Namparang Trail Course 1', 19.0, 'Moderate', '7h', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005116.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_1%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005116',
        35.128745186417916, 129.07481501067562,
        'Oryukdo Sunrise Park', 'Busan, Nam-gu, 용호제2동 산198', 35.09969000704587, 129.12371004000306,
        'Busan Station', 'Choryang-dong, Busan', 35.11454483494164, 129.0407629776746,
        now(), now()),
       (2, 'Namparang Trail Course 2', 18.9, 'Moderate', '7h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005117.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_2%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005117',
        35.09047477506102, 129.05427125282586,
        'Busan Station', 'Choryang-dong, Busan', 35.11454483494164, 129.0407629776746,
        'Yeongdo Bridge', 'Nampo-dong 1(il)-ga, Yeongdo-gu, Busan', 35.096556935459375, 129.03596760705113,
        now(), now()),
       (3, 'Namparang Trail Course 3', 14.9, 'Moderate', '5h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005118.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_3%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005118',
        35.0599085725844, 129.01863399893045,
        'Yeongdo Bridge', 'Nampo-dong 1(il)-ga, Yeongdo-gu, Busan', 35.096556935459375, 129.03596760705113,
        'Gamcheon Sageori', 'Busan', 35.08746676146984, 129.00448727421463,
        now(), now()),
       (4, 'Namparang Trail Course 4', 21.8, 'Easy', '7h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005119.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_4%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005119',
        35.04962791688741, 128.97025767713785,
        'Gamcheon Sageori', 'Busan', 35.08747070096433, 129.00448073633015,
        'Sinpyeong-dong Intersection', 'Busan', 35.09638778865339, 128.9553397335112,
        now(), now()),
       (5, 'Namparang Trail Course 5', 21.9, 'Easy', '7h', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005120.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_5%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005120',
        35.086464453488595, 128.91221777535975,
        'Sinpyeong-dong Intersection', 'Busan', 35.096398433670394, 128.95534375682473,
        'Sinpyeong Park', '1455 Songjeong-dong, Gangseo-gu, Busan', 35.09804354980587, 128.82239227183163,
        now(), now()),
       (6, 'Namparang Trail Course 6', 14.8, 'Easy', '5h', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000005121.gpx&downloadName=%EB%82%A8%ED%8C%8C%EB%9E%91%EA%B8%B8_6%EC%BD%94%EC%8A%A4.gpx&course_id=T_CRS_MNG0000005121',
        35.11484373360872, 128.7845040578395,
        'Sinpyeong Park', '1455 Songjeong-dong, Gangseo-gu, Busan', 35.09804069995878, 128.82239311002195,
        'Jadeok Intersection', 'Changwon-si', 35.10188925080001, 128.74467291869223,
        now(), now()),
       (7, 'Haeparang Trail Course 1', 16.9, 'Moderate', '6h 30m', 'https://shorturl.at/G5bWW',
        0,
        'https://www.durunubi.kr/download?filePath=/data/koreamobility/course/summap/T_CRS_MNG0000004239.gpx&downloadName=Haeparang_Trail_Course_1.gpx&course_id=T_CRS_MNG0000004239',
        35.122557012364254, 129.12364399060607,
        'Oryukdo Sunrise Park', 'Busan, Nam-gu, 용호제2동 산198', 35.09968874976039, 129.12370610050857,
        'Haeundae-gu', 'Busan', 35.158975124359124, 129.16027123108506,
        now(), now());
