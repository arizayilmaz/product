INSERT INTO permissions (name) VALUES
                                   ('PRODUCT_READ'),
                                   ('PRODUCT_CREATE'),
                                   ('PRODUCT_UPDATE'),
                                   ('PRODUCT_DELETE'),
                                   ('USER_MANAGE')
    ON CONFLICT (name) DO NOTHING;
