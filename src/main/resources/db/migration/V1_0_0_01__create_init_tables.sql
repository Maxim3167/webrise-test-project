CREATE TABLE users(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE subscriptions(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE user_subscriptions(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT REFERENCES users (id),
    subscription_id BIGINT REFERENCES subscriptions (id)
);