DO $$
DECLARE
admin_id uuid;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin') THEN
        INSERT INTO users (id, username, password, email)
        VALUES (
            gen_random_uuid(),
            'admin',
            -- Şifre: admin123 (BCrypt hash)
            '$2a$10$DM3iPaSQ1a6WL6WxmIRmgudsvDj5LTf9Vrr3zImLGqVRlcmXxnTrS',
            'admin@example.com'
        )
        RETURNING id INTO admin_id;

INSERT INTO user_roles (user_id, role_id)
SELECT admin_id, r.id FROM roles r WHERE r.name = 'ADMIN';
END IF;
END $$;
