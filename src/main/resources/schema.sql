CREATE TABLE member
(
    id         SERIAL PRIMARY KEY,
    user_id    VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    nickname   VARCHAR(255) NOT NULL,
    stream_key VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL
);

CREATE TABLE refreshtoken
(
    id            SERIAL PRIMARY KEY,
    refresh_token VARCHAR(255) NOT NULL,
    user_id       VARCHAR(255) NOT NULL
);