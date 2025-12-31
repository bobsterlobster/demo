📦 Система управления заказами (СУЗ) / Order Management System

📋 Описание проекта
Backend-приложение для автоматизации управления заказами, товарами и пользователями. Реализует полный CRUD-функционал через REST API на Spring Boot 4.0.1 + PostgreSQL.

🎯 Основные возможности:

Регистрация пользователей

Управление каталогом товаров (остатки, цены)

Создание заказов с проверкой stock

Обновление статусов заказов (NEW → CONFIRMED → CANCELLED)

Автоматическое Swagger API-документирование

🛠️ Технологический стек
Компонент	Версия	Назначение
Spring Boot	4.0.1	Web-фреймворк
Spring Data JPA	4.0.1	Работа с БД
Hibernate	7.2.0	ORM
PostgreSQL	16.x	База данных
Lombok	1.18.42	Упрощение кода
SpringDoc OpenAPI	2.5.0	Swagger UI
Maven	-	Сборка проекта
📂 Структура проекта
text
demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Главный класс
│   ├── controller/                   # REST контроллеры
│   ├── service/                      # Бизнес-логика
│   ├── repository/                   # JPA репозитории
│   ├── model/                        # JPA Entity
│   └── dto/                          # Data Transfer Objects
└── src/main/resources/
    └── application.yml               # Конфигурация
🚀 Быстрый старт
1. Клонируй проект
bash
git clone <твой-репозиторий>
cd demo
2. Создай базу данных PostgreSQL
sql
CREATE DATABASE orderdb;
3. Импортируй таблицы (src/main/resources/schema.sql)
   
-- users, products, orders, order_items:

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20)
);


CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    stock INTEGER DEFAULT 0
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'NEW' CHECK (status IN ('NEW', 'CONFIRMED', 'CANCELLED'))
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0)
);

4. Запусти проект
bash
mvn spring-boot:run
# или в IntelliJ IDEA: Run 'DemoApplication'
✅ Приложение доступно: http://localhost:8080

🔗 API Документация
Swagger UI: http://localhost:8080/swagger-ui.html

Основные endpoints:
Метод	Endpoint	Описание
POST	/api/users	Создать пользователя
POST	/api/products	Добавить товар
POST	/api/orders	Создать заказ
GET	/api/orders	Получить все заказы
PATCH	/api/orders/{id}/status?status=CONFIRMED	Изменить статус
🧪 Пример запросов (Postman/Swagger)
1. Создать пользователя
json
POST /api/users
{
  "name": "Иван Иванов",
  "email": "ivan@test.ru",
  "phone": "+79991234567"
}
2. Добавить товар
json
POST /api/products
{
  "name": "iPhone 15",
  "description": "Смартфон",
  "price": 100000.00,
  "stock": 10
}
3. Создать заказ
json
POST /api/orders
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
🗄️ Схема базы данных
text
erDiagram
    USERS ||--o{ ORDERS : places
    PRODUCTS ||--o{ ORDER_ITEMS : contains
    ORDERS ||--o{ ORDER_ITEMS : "has items"
    USERS {
        bigint id PK
        varchar name
        varchar email UK
        varchar phone
    }
    PRODUCTS {
        bigint id PK
        varchar name
        numeric price
        int stock
    }
    ORDERS {
        bigint id PK
        bigint user_id FK
        timestamp order_date
        varchar status
    }
    ORDER_ITEMS {
        bigint id PK
        bigint order_id FK
        bigint product_id FK
        int quantity
    }
⚙️ Конфигурация
application.yml:

text
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/orderdb
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  server:
    port: 8080
🧪 Тестирование
bash
# Unit-тесты
mvn test

# Интеграционные тесты
mvn verify
📊 Этапы разработки (академический план)
Этап	Срок	Статус
Анализ требований	2 дня	✅
Проектирование	4 дня	✅
Разработка	10 дней	✅
Тестирование	5 дней	🔄
Документация	3 дня	🔄
👥 Автор
Студент: [Твоё имя]
Учебный проект: Система управления заказами (СУЗ)
Дата: 31.12.2025

📄 Лицензия
MIT License - свободное использование в учебных целях.
