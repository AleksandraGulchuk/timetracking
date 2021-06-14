DROP
DATABASE IF EXISTS timetracking;

CREATE
DATABASE timetracking;

USE
timetracking;

CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO roles(role)
VALUES ('admin'),
       ('client');

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(250) NOT NULL,
    login    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    role_id  INT          NOT NULL,
    CONSTRAINT fk_users_roles_id
        FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO users(name, login, password, role_id)
VALUES ('Darth Vader', 'admin', '1', 1),
       ('Luke Skywalker', 'client', '2', 2);


CREATE TABLE categories
(
    id       SERIAL PRIMARY KEY,
    category VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO categories (category)
VALUES ('project'),
       ('meeting'),
       ('consultation'),
       ('training');

CREATE TABLE statuses
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO statuses (status)
VALUES ('new'),
       ('changed'),
       ('on update');

CREATE TABLE activities
(
    id                    SERIAL PRIMARY KEY,
    user_id               INT          NOT NULL,
    category_id           INT          NOT NULL,
    title                 VARCHAR(50)  NOT NULL,
    description           VARCHAR(250) NOT NULL,
    creation_date_time    TIMESTAMP    NOT NULL,
    last_update_date_time TIMESTAMP    NOT NULL,
    total_time            BIGINT         NOT NULL,
    status_id             INT          NOT NULL,
    CONSTRAINT fk_activities_users_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_activities_categories_id
        FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_activities_statuses_id
        FOREIGN KEY (status_id) REFERENCES statuses (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (user_id, category_id, title)
);

CREATE TABLE activity_stories
(
    id               SERIAL PRIMARY KEY,
    activity_id      INT       NOT NULL,
    update_date_time TIMESTAMP NOT NULL,
    time_spent       BIGINT      NOT NULL,
    comment          VARCHAR(250),
    CONSTRAINT fk_activity_stories_activities_id
        FOREIGN KEY (activity_id) REFERENCES activities (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE users_create_activity_requests
(
    id                 SERIAL PRIMARY KEY,
    request_date_time TIMESTAMP   NOT NULL,
    user_id            INT         NOT NULL,
    category_id        INT         NOT NULL,
    title              VARCHAR(50) NOT NULL,
    description        VARCHAR(250),
    total_time         BIGINT        NOT NULL,
    comment     VARCHAR(250),
    CONSTRAINT fk_users_create_activity_requests_users_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_users_create_activity_requests_categories_id
        FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE (user_id, category_id, title)
);

CREATE TABLE users_delete_activity_requests
(
    id          SERIAL PRIMARY KEY,
    request_date_time TIMESTAMP   NOT NULL,
    user_id     INT NOT NULL,
    activity_id INT NOT NULL,
    comment VARCHAR(250),
    CONSTRAINT fk_users_delete_requests_users_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_users_delete_requests_activities_id
        FOREIGN KEY (activity_id) REFERENCES activities (id) ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE request_types
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);

INSERT INTO request_types (type)
VALUES ('create NEW'),('delete');

CREATE TABLE admin_comments
(
    id          SERIAL PRIMARY KEY,
    user_id     INT  NOT NULL,
    request_date_time TIMESTAMP NOT NULL,
    request_type_id INT  NOT NULL,
    category_id INT  NOT NULL,
    title VARCHAR(50) NOT NULL,
    comment     VARCHAR(250),
    CONSTRAINT fk_admin_comments_users_id
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_admin_comments_request_types_id
        FOREIGN KEY (request_type_id) REFERENCES request_types (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_admin_comments_activities_id
        FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE
);