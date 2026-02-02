## Architecture and Design Decisions 

### 1) Design Patterns

#### Factory Pattern (Ticket creation)
We use a **Factory Pattern** in `TicketFactory` to centralize ticket creation.  
This avoids duplicating ticket instantiation logic and keeps the creation rules consistent.

Why it is necessary:
- Ticket creation depends on multiple inputs (booking, screening, seat, type, price).
- Centralizing this logic prevents scattered creation logic across services.
- It makes it easier to add new ticket types (e.g., PromotionalTicket) without changing the service layer.

Where it is used:
- `backend/src/main/java/com/example/cinema/service/TicketFactory.java`
- `TicketService` calls the factory in `generateTickets(...)`.

#### Singleton Pattern (Service components)
Spring `@Service` beans are **singleton by default**, so components like `EmailNotificationService`
and `PaymentConfiguration` are implemented as singletons.

Why it is necessary:
- These services represent shared infrastructure and configuration.
- A single instance avoids unnecessary resource usage and ensures consistent behavior.

Where it is used:
- `backend/src/main/java/com/example/cinema/service/EmailNotificationService.java`
- `backend/src/main/java/com/example/cinema/service/PaymentConfiguration.java`

---

### 2) Architectural Pattern (MVC / Layered Architecture)

The system uses a **Layered Architecture aligned with MVC**:

- **Controller Layer (Presentation / Controller):**  
  REST API endpoints handle HTTP requests and responses.  
  Examples:  
  - `BookingController`  
  - `MovieController`  
  - `ScreeningController`

- **Service Layer (Business Logic):**  
  Core logic like seat locking, availability checks, payment processing, and ticket generation.  
  Examples:  
  - `BookingService`  
  - `PaymentService`  
  - `TicketService`

- **Repository Layer (Data Access / Model):**  
  JPA repositories abstract database operations.  
  Examples:  
  - `BookingRepository`  
  - `ScreeningRepository`  
  - `SeatRepository`

This separation ensures:
- Controllers stay thin and focus on request handling.
- Services hold business rules.
- Repositories isolate persistence logic.

---

### 3) REST Service Separation (SOAP/REST Split)

The system is separated into **independent REST services** by domain responsibility:

- **User Service (REST):** user authentication and role management (Customer/Staff/Admin).
- **Movie Service (REST):** movie catalog.
- **Booking Service (REST):** seat selection, booking confirmation, ticket generation.
- **Payment Service (REST):** payment processing (simulated in this project).

Each service is exposed as a REST endpoint, and all services are orchestrated in one system via
Spring Boot controllers and service classes.

---

### 4) Unit Tests (JUnit + Mockito)

We use **JUnit 5** and **Mockito** to test the `BookingService` logic.

Test coverage:
- Seat availability checks
- Successful booking confirmation
- Failure scenarios (seat unavailable)

Location:
- `backend/src/test/java/com/example/cinema/service/BookingServiceTest.java`

---

### 5) Test Scenarios (Table)

| Test ID | Scenario | Description | Expected Result |
|--------:|----------|-------------|-----------------|
| T1 | User login authentication | User credentials checked with Spring Security user details | User can be loaded or rejected |
| T2 | Seat availability | Check if requested seats are AVAILABLE for a screening | Returns true if all seats are free |
| T3 | Booking confirmation | Full booking flow with payment + ticket generation | Booking confirmed, seats BOOKED |

---

### 6) Docker and Deployment

The whole system runs using **Docker Compose**:
- **Backend** (Spring Boot)
- **Frontend** (React + Nginx)
- **Database** (PostgreSQL)

File:
- `docker-compose.yml`

Run:
```
docker compose down -v
docker compose up --build
```

Why it matters:
Docker Compose ensures the project can be started as a **single deployable unit** with isolated services,
meeting the requirement for the highest grading scale (6.0).
