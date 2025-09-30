# Product API

Spring Boot ile geliştirilmiş **Product API** projesi.  
JWT tabanlı Authentication/Authorization, rol & permission sistemi ve ürün/kategori yönetimi içerir.

---

## 🚀 Başlangıç

### 1. Uygulamayı Çalıştır
```bash
mvn spring-boot:run
```

### 2. Varsayılan Admin Kullanıcı
Migration dosyaları ile birlikte gelen hazır admin:

- **Username:** `admin`  
- **Password:** `admin123`  

Bu kullanıcı ile giriş yaptıktan sonra **token** alıp tüm endpointlere erişebilirsiniz.

---

## 🔑 Authentication

- `POST /api/auth/register` → Yeni kullanıcı kaydı
- `POST /api/auth/login` → Token alma

#### Login Örneği
```bash
curl -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

Yanıt:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Bu token'i Authorization header’da kullanın:
```
-H "Authorization: Bearer <token>"
```

---

## 👤 Users

- `POST /api/users/register`
```bash
curl -X POST http://localhost:8080/api/users/register   -H "Content-Type: application/json"   -d '{
    "username": "user1",
    "password": "123456",
    "email": "user1@mail.com"
  }'
```

- `GET /api/users/{username}` (ADMIN)
```bash
curl -X GET http://localhost:8080/api/users/admin   -H "Authorization: Bearer <token>"
```

---

## 📦 Products

- `GET /api/products`
```bash
curl -X GET http://localhost:8080/api/products   -H "Authorization: Bearer <token>"
```

- `POST /api/products`
```bash
curl -X POST http://localhost:8080/api/products   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "Laptop",
    "description": "Gaming laptop",
    "price": 2500.00,
    "stockQuantity": 10,
    "sku": "LP-001",
    "categoryIds": ["{{categoryId}}"]
  }'
```

- `PUT /api/products/{id}`
```bash
curl -X PUT http://localhost:8080/api/products/{{id}}   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "Updated Laptop",
    "description": "Gaming laptop RTX",
    "price": 3000.00,
    "stockQuantity": 8,
    "sku": "LP-001",
    "categoryIds": ["{{categoryId}}"]
  }'
```

- `DELETE /api/products/{id}`
```bash
curl -X DELETE http://localhost:8080/api/products/{{id}}   -H "Authorization: Bearer <token>"
```

---

## 🏷️ Categories

- `GET /api/categories`
```bash
curl -X GET http://localhost:8080/api/categories   -H "Authorization: Bearer <token>"
```

- `POST /api/categories`
```bash
curl -X POST http://localhost:8080/api/categories   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "Electronics",
    "description": "Electronic devices"
  }'
```

- `PUT /api/categories/{id}`
```bash
curl -X PUT http://localhost:8080/api/categories/{{id}}   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "Electronics Updated",
    "description": "Updated description"
  }'
```

- `DELETE /api/categories/{id}`
```bash
curl -X DELETE http://localhost:8080/api/categories/{{id}}   -H "Authorization: Bearer <token>"
```

---

## 🛠️ Admin

Sadece **ADMIN** rolü erişebilir:

- `GET /api/admin/users`
```bash
curl -X GET http://localhost:8080/api/admin/users   -H "Authorization: Bearer <token>"
```

- `POST /api/admin/roles`
```bash
curl -X POST http://localhost:8080/api/admin/roles   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "MODERATOR"
  }'
```

- `POST /api/admin/permissions`
```bash
curl -X POST http://localhost:8080/api/admin/permissions   -H "Content-Type: application/json"   -H "Authorization: Bearer <token>"   -d '{
    "name": "ORDER_MANAGE"
  }'
```

- `POST /api/admin/users/{userId}/roles/{roleId}`
```bash
curl -X POST http://localhost:8080/api/admin/users/{{userId}}/roles/{{roleId}}   -H "Authorization: Bearer <token>"
```

- `POST /api/admin/roles/{roleId}/permissions/{permissionId}`
```bash
curl -X POST http://localhost:8080/api/admin/roles/{{roleId}}/permissions/{{permissionId}}   -H "Authorization: Bearer <token>"
```

---

## ✅ Roller & Permissionlar

- **USER**
  - Ürün & kategori görüntüleme

- **SELLER**
  - Ürün CRUD işlemleri

- **ADMIN**
  - Tüm izinler + kullanıcı/rol/permission yönetimi
