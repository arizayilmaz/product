CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE permissions (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                            user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                            role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

CREATE TABLE role_permissions (
                                  role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                                  permission_id UUID REFERENCES permissions(id) ON DELETE CASCADE,
                                  PRIMARY KEY (role_id, permission_id)
);
