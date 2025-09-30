INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('USER'),
                             ('SELLER')
    ON CONFLICT (name) DO NOTHING;
