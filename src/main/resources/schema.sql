
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS rooms;

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(30) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       capacity INT
);

CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             room_id BIGINT REFERENCES rooms(id),
                             user_id BIGINT REFERENCES users(id),
                             reserve_date DATE NOT NULL,
                             start_time TIME NOT NULL,
                             end_time TIME NOT NULL,
                             status VARCHAR(20) NOT NULL DEFAULT '예약완료'  -- 예약완료 / 예약취소
);