INSERT INTO permissions (name) VALUES
                                   ('CATEGORY_READ'),
                                   ('CATEGORY_CREATE'),
                                   ('CATEGORY_UPDATE'),
                                   ('CATEGORY_DELETE')
    ON CONFLICT (name) DO NOTHING;


INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON true
WHERE r.name = 'ADMIN'
    ON CONFLICT DO NOTHING;
