CREATE SEQUENCE IF NOT EXISTS event_seq;

CREATE TABLE IF NOT EXISTS events (
    event_id BIGINT NOT NULL DEFAULT nextval('event_seq') PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    organizer VARCHAR(255) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    place VARCHAR(255) NOT NULL
    );
