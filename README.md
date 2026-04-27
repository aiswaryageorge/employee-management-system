#  Employee Management System

A production-ready full-stack **Employee Management System** built using **React, Spring Boot, Spring Security, JWT, MySQL, Docker, Nginx, and AWS**.

This project demonstrates real-world software engineering practices including secure authentication, CRUD operations, cloud deployment, reverse proxy setup, SSL, and managed database integration.

---

##  Tech Stack

* **Frontend**: React.js, Axios, CSS, React Router
* **Backend**: Spring Boot, Spring Security, JWT JWT Authentication, REST APIs
* **Database**: MySQL, AWS RDS (Managed MySQL)
* **DevOps**: Docker, Docker Compose, Nginx Reverse Proxy, HTTPS SSL, Elastic IP

---

##  Features

### Authentication & Security
- JWT Login / Register
- Role-Based Access Control (Admin / User)
- Protected Routes
- Secure API Access

### Employee Management
- Add Employees
- Edit Employees
- Delete Employees
- Search Employees
- Pagination
- Sorting (Name / Salary)

### User Experience
- Responsive UI
- Toast Notifications
- Loading States
- Clean Dashboard Layout

### Deployment Features
- Dockerized Frontend + Backend
- Reverse Proxy with Nginx
- HTTPS Enabled
- AWS RDS Database
- Elastic IP Stable Hosting

---

##  Architecture

````
React Frontend
      ↓
Nginx Reverse Proxy (HTTPS)
      ↓
Spring Boot REST API
      ↓
AWS RDS MySQL
````
---

##  Setup Instructions

### 1. Clone the repo

```
git clone <your-repo-url>
cd employee-management-system
```

### 2. Start Backend + DB (Docker)

```
docker-compose up -d mysql backend
```

### 3. Start Frontend

```
cd frontend
npm install
npm start
```

---

##  Environment Variables

Create `.env` in frontend:

```
REACT_APP_API_URL=http://localhost:8080
```

---

## 📸 Screenshots

### Login
![Login](frontend/public/screenshots/login.png)

### Dashboard
![Dashboard](frontend/public/screenshots/dashboard.png)

### Pagination
![Pagination](frontend/public/screenshots/pagination.png)

### Search
![Search](frontend/public/screenshots/search.png)

---

## Deployment Highlights
* Deployed full-stack app on AWS EC2
* Configured Nginx reverse proxy
* Enabled HTTPS SSL access
* Migrated local MySQL to AWS RDS
* Fixed React refresh routing in production
* Used Docker Compose for container orchestration

## Future Improvements
* GitHub Actions CI/CD
* Monitoring & Logging
* Custom Domain with Route53
* Kubernetes Deployment


## Author
Aiswarya George
