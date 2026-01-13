# Task Manager（簡易タスク管理）

業務系の開発を想定した、タスクの登録・更新・削除とステータス管理（TODO/DOING/DONE）ができるWebアプリです。

## 使用技術
- Java 17
- Spring Boot
- Spring Data JPA（Hibernate）
- MySQL 8.x
- Thymeleaf

## 機能
- タスク一覧表示
- タスク追加
- タスク編集（タイトル更新）
- タスク削除
- ステータス変更（TODO / DOING / DONE）

## 画面URL
- 一覧画面：`http://localhost:8080/ui/tasks`

## API（REST）
- GET `/tasks` : 一覧取得
- POST `/tasks` : 追加（body: `{ "title": "..." }`）
- PUT `/tasks/{id}` : 更新（body: `{ "title": "..." }`）
- DELETE `/tasks/{id}` : 削除

## セットアップ手順（Windows想定）
1. MySQLを起動し、DBを用意（例：`testdata`）
2. `src/main/resources/application.properties` を設定

例：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/testdata?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo
spring.datasource.username=appuser
spring.datasource.password=（appuserのパスワード）

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
