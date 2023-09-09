use vibely_social_webapp;
INSERT INTO user_role (user_id, role_id)
VALUE (1, 2);

UPDATE user
SET email = 'admin@facogi.com', password = ' $2a$12$nDfO.d/CwBRCmo3AXqKVUOkRmvG9Dx4iAlZpELZ0ldJo8ulF9.ecy '
WHERE id = 1;