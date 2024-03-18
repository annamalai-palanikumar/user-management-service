--liquibase formatted sql

--changeset gandhimathi.karunanithi:table_client_dml_1.0.0
INSERT INTO client (id, name, description) VALUES (1, 'Authorized Application''s Client', 'Client Description');
--rollback delete from application where id in (1);

--changeset gandhimathi.karunanithi:table_application_dml_1.0.0
INSERT INTO application (id, name, description, division_id, client_id, authorised) VALUES (1, 'Authorized Application', 'Authorized Application Description', 1, 1, true);
--rollback delete from application where id in (1);

--changeset gandhimathi.karunanithi:table_user_dml_1.0.0
INSERT INTO user (id, email, name, password, user_name, application_id) VALUES (1, 'me@annamalai.er.in', 'Annamalai Palanikumar', '$2a$10$4/nxqZdxIc3AszaQqAuN9.HaPbIyC9RVKFt0GocQrpfqUcbxSibRe', 'annamalai', 1);
--rollback delete from user where id in (1);