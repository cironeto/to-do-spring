CREATE DATABASE to_do_spring;
GO
USE to_do_spring;
GO

INSERT INTO app_user (email, name, password, role)
VALUES ('admin@mail.com', 'admin', '$2a$10$Mop9cpH5cHJiHVA0D2/GaeprNS6cRihp1Xz1YmKeGqpyZhrFCkkOu', 'ADMIN');
INSERT INTO app_user (email, name, password, role)
VALUES ('user@mail.com', 'admin', '$2a$10$Mop9cpH5cHJiHVA0D2/GaeprNS6cRihp1Xz1YmKeGqpyZhrFCkkOu', 'USER');

INSERT INTO task (description, priority, task_completed, user_id)
VALUES ('Lorem Ipsum', 1, 'false', 1);
INSERT INTO task (description, priority, task_completed, user_id)
VALUES ('Lorem Ipsum', 2, 'false', 1);


INSERT INTO task (description, priority, task_completed, user_id)
VALUES ('Lorem Ipsum', 1, 'false', 2);
INSERT INTO task (description, priority, task_completed, user_id)
VALUES ('Lorem Ipsum', 2, 'false', 2);


