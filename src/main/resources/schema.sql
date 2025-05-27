-- auto-generated definition
create table users
(
    id       bigint auto_increment
        primary key,
    username varchar(50)                           not null,
    password varchar(100)                          not null,
    email    varchar(255)                          not null,
    constraint username
        unique (username)
);

CREATE TABLE favorites (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           bus_stop_id VARCHAR(255) NOT NULL,
                           bus_stop_name VARCHAR(255) NOT NULL,
                           bus_line_no VARCHAR(20) NOT NULL,
                           next_stop_name VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                           CONSTRAINT uniq_favorite UNIQUE (user_id, bus_stop_id, bus_line_no)
);

CREATE TABLE bus_stop (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          bus_stop_id VARCHAR(20) NOT NULL,
                          bus_stop_name VARCHAR(255) NOT NULL,
                          line_no VARCHAR(20) NOT NULL,
                          next_stop_name VARCHAR(255) NOT NULL
);