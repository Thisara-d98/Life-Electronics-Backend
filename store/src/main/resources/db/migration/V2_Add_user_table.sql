CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(255) NOT NULL CHECK (role IN ('ADMIN', 'CLIENT'))
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(user_name);