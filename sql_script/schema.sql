-- DROP DATABASE [IF EXISTS] meetup;
CREATE DATABASE meetup
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
CREATE SEQUENCE event_seq;
CREATE TABLE events (
    event_id BIGINT NOT NULL DEFAULT nextval('event_seq') PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    organizer VARCHAR(255) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    place VARCHAR(255) NOT NULL
);
