# Payment Service MVP

A simple payment processing web application built with Spring Boot and vanilla JavaScript.

## Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.2.5
- Spring Security (JWT authentication)
- Spring Data JPA
- MySQL Database
- Gradle

**Frontend:**
- HTML5
- JavaScript
- CSS3

## Application Flow

### Authentication
1. User goes to `/login.html`
2. Login with username: `admin`, password: `admin123`
3. System generates JWT token and stores in localStorage
4. User redirected to payments page

### Payment Management
1. User lands on `/payments.html` showing existing payments table
2. Click "Make Payment" → goes to `/payment-form.html`
3. Fill payment form (name, email, phone, amount)
4. Submit payment → redirects back to payments page with updated list
5. Click "Logout" → clears token and goes back to login

## Setup

1. Set environment variable:
   ```bash
   export MYSQL_DATABASE_PASSWORD=your_mysql_password
   ```

2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

3. Open browser:
   - Frontend: http://localhost:8080/login.html
   - API Documentation: http://localhost:8080/swagger-ui/index.html

## API Endpoints

**Authentication:**
- `POST /auth/login` - Login with username/password

**Payments:**
- `GET /api/v1/payments/get` - Get all payments
- `POST /api/v1/payments/payment` - Create new payment

## Database

Uses MySQL database with auto-generated `payment` table containing:
- payment_id, name, email, phone_number, amount, status, created_at