CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS users
(
    id          int8 primary key,
    username    VARCHAR(25) NOT NULL UNIQUE,
    email       VARCHAR(40) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    role        VARCHAR(15) NOT NULL,
    user_status VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS playlist
(
    id      int8 primary key,
    title   VARCHAR(30) NOT NULL DEFAULT 'Playlist',
    user_id int8 references users(id) on delete cascade,
    is_main boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS playlist_position
(
    id      int8 primary key,
    position int4,
    title VARCHAR(50) NOT NULL,
    music_track_id int8,
    playlist_id int8 references playlist (id),
    unique (playlist_id, position)
);

CREATE TABLE IF NOT EXISTS music_track
(
    id      int8 primary key,
    track_name VARCHAR(50) NOT NULL,
    duration int8 default NULL,
    url VARCHAR(700) UNIQUE NOT NULL,
    is_need_proxy boolean,
    creation_date date default current_date
);

ALTER TABLE playlist_position
    ADD CONSTRAINT FK_music_track_id FOREIGN KEY (music_track_id) references music_track (id);

CREATE TABLE IF NOT EXISTS pattern
(
    id      int8 primary key,
    title VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS music_track__pattern
(
    pattern_id int8 references pattern(id) on delete CASCADE,
    track_id int8 references music_track(id) on delete CASCADE
);

CREATE SEQUENCE music_track_id_seq START 50 INCREMENT 50; --hibernate incr
CREATE SEQUENCE pattern_id_seq START 50 INCREMENT 50;
CREATE SEQUENCE playlist_id_seq START 50 INCREMENT 50;
CREATE SEQUENCE positions_id_seq START 50 INCREMENT 50;
CREATE SEQUENCE users_id_seq START 50 INCREMENT 50;