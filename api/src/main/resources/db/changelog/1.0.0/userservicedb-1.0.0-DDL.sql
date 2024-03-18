--liquibase formatted sql
--changeset annamalai.palanikumar:table_client_ddl_1.0.0
CREATE TABLE client (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (id)
);
--rollback DROP TABLE IF EXISTS client CASCADE;

--changeset annamalai.palanikumar:table_application_ddl_1.0.0
CREATE TABLE application (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    division_id BIGINT NOT NULL,
    client_id BIGINT NULL,
    authorised TINYINT(1),
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES client (id)
);
--rollback DROP TABLE IF EXISTS application CASCADE;

--changeset annamalai.palanikumar:table_user_ddl_1.0.0
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    user_name VARCHAR(255),
    application_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (application_id) REFERENCES application (id)

);
--rollback DROP TABLE IF EXISTS user CASCADE;