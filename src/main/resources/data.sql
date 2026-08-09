INSERT INTO users(username, password)
VALUES ('admin', '1234');

INSERT INTO users(username, password)
VALUES ('test1', '1234');

INSERT INTO users(username, password)
VALUES ('test2', '1234');

INSERT INTO users(username, password)
VALUES ('test3', '1234');

INSERT INTO users(username, password)
VALUES ('test4', '1234');

INSERT INTO users(username, password)
VALUES ('test5', '1234');



INSERT INTO rooms(name, capacity)
VALUES ('소회의실', 4);

INSERT INTO rooms(name, capacity)
VALUES ('중회의실', 10);

INSERT INTO rooms(name, capacity)
VALUES ('대회의실', 30);

INSERT INTO rooms(name, capacity)
VALUES ('세미나실', 40);

-- 예약 테스트 데이터
-- 2026년 8월 전체에 골고루 분산

-- admin
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (1, 1, '2026-08-03', '09:00', '10:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (3, 1, '2026-08-18', '14:00', '15:00', '예약완료');


-- test1
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (2, 2, '2026-08-05', '10:00', '11:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (1, 2, '2026-08-24', '13:00', '14:00', '예약완료');


-- test2
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (3, 3, '2026-08-07', '11:00', '12:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (2, 3, '2026-08-27', '15:00', '16:00', '예약완료');


-- test3
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (1, 4, '2026-08-11', '10:00', '11:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (3, 4, '2026-08-20', '09:00', '10:00', '예약완료');


-- test4
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (2, 5, '2026-08-14', '09:00', '10:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (1, 5, '2026-08-29', '13:00', '14:00', '예약완료');


-- test5
INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (3, 6, '2026-08-16', '10:00', '11:00', '예약완료');

INSERT INTO reservation
(room_id, user_id, reserve_date, start_time, end_time, status)
VALUES
    (2, 6, '2026-08-31', '15:00', '16:00', '예약완료');