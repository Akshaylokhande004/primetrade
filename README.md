# PrimeTrade - Crypto Trade Management System

## 🚀 Live Demo

Frontend:
https://primetrade-ashen.vercel.app/

Backend:
https://primetrade-1-f9wq.onrender.com

---

## 🏗 Architecture

- Spring Boot (Backend)
- React + Vite (Frontend)
- PostgreSQL (Render)
- JWT Authentication
- RBAC (ADMIN / USER)
- Dockerized Deployment

---

## 🔐 Features

- User Registration
- JWT Login Authentication
- Role-Based Access Control
- Create / View / Delete Trades
- Admin can view all trades
- Users can view only their trades

---

## 🛠 Tech Stack

Backend:
- Spring Boot 3
- Spring Security
- JPA / Hibernate
- PostgreSQL
- Docker

Frontend:
- React
- Axios
- Vite

---

## 📬 API Documentation

Postman collection included:
primetrade-postman-collection.json

---

## 👤 Demo Credentials

Admin:
email: admin@example.com
password: password123

User:
email: user1@example.com
password: password123

## 🔄 System Design

Client (React) → REST API (Spring Boot) → PostgreSQL

## 📈 Scalability & Deployment

The backend is designed to support horizontal scaling.

- Stateless JWT authentication (no server-side sessions)
- Externalized PostgreSQL database
- Dockerized deployment
- Environment-based configuration
- Cloud hosted on Render

Because the application is stateless, multiple backend instances can run behind a load balancer without session conflicts.

Authentication:
JWT-based stateless authentication.

Caching:
Redis implemented (local), removed for production deployment.

## Admin User Seeding

The system supports automatic admin user creation via environment variables.

On application startup:
- If ADMIN_EMAIL and ADMIN_PASSWORD are defined
- And the admin user does not exist
- The system creates an ADMIN account

This ensures secure and configurable admin initialization without hardcoded credentials.
