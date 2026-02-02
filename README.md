# 🎬 Online Cinema Ticket Booking and Reservation System

Full-stack web application for cinema reservations and ticket sales.  
Backend uses Spring Boot (REST API) and PostgreSQL. Frontend uses React (Vite).

---
<img width="1409" height="932" alt="Screenshot 2026-02-02 175223" src="https://github.com/user-attachments/assets/5cb86b88-0543-4a9f-abb5-854c5f19d1b6" />

## ✨ Features
- Movie catalog with posters
- Screening schedule per movie
- Seat selection with real-time availability
- Booking confirmation with ticket generation
- Payment flow (simulated)
- PDF receipt download

---
<img width="1144" height="900" alt="Screenshot 2026-02-02 175245" src="https://github.com/user-attachments/assets/8c34a3bf-618c-4829-95e5-96e5ed3dd956" />

## 🧰 Tech Stack

### 🖥️ Backend
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security (BCrypt)
- PostgreSQL
- Maven

### 🌐 Frontend
- React 18
- Vite
- Axios
- jsPDF
- CSS3

---

## 🚀 Quick Start (Docker Compose)

```bash
docker compose down -v
docker compose up --build
```

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`

---

## 🧪 Local Development (optional)

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## 🧭 Usage Flow
1. Login (demo mode)
2. Select a movie
3. Choose a screening time
4. Select seats
5. Confirm booking and get ticket receipt

---

## 🔌 API Endpoints

### Movies
- `GET /api/movies`

### Screenings
- `GET /api/screenings/movie/{movieId}`
- `GET /api/screenings/{screeningId}/seats`

### Bookings
- `POST /api/bookings`
```json
{
  "userId": 1,
  "screeningId": 1,
  "seatIds": [1, 2, 3],
  "firstName": "John",
  "lastName": "Doe",
  "age": 25,
  "paymentMethod": "CARD",
  "promoCode": "OPTIONAL"
}
```

---

## 🌱 Default Data (Seed)
- 7 movies with poster URLs
- 1 cinema hall (5 rows × 8 seats)
- screenings created for each movie
- seats created per screening
- demo customer: `demo@example.com / password`

---

## 🗂️ Project Structure

```
movie app/
├── backend/
│   ├── src/main/java/com/example/cinema/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access
│   │   ├── model/          # Entity models
│   │   ├── dto/            # Data transfer objects
│   │   └── config/         # Configuration classes
│   └── pom.xml
├── frontend/
│   ├── public/posters/     # Poster images
│   ├── src/
│   │   ├── App.jsx         # Main React component
│   │   ├── index.css       # Styles
│   │   └── main.jsx        # Entry point
│   └── vite.config.js
├── docker-compose.yml
├── REPORT.md
└── README.md
```

---

## 📝 Notes
- Poster files are served from `frontend/public/posters`.
- If you change seed data or schema, restart with `docker compose down -v`.
# Online Cinema Ticket Booking and Reservation System

Full-stack web application for cinema reservations and ticket sales.  
Backend uses Spring Boot (REST API) and PostgreSQL. Frontend uses React (Vite).

## Features
- Movie catalog with posters
- Screening schedule per movie
- Seat selection with real-time availability
- Booking confirmation with ticket generation
- Payment flow (simulated)
- PDF receipt download

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security (BCrypt)
- PostgreSQL
- Maven

### Frontend
- React 18
- Vite
- Axios
- jsPDF
- CSS3

## Quick Start (Docker Compose)

```bash
docker compose down -v
docker compose up --build
```

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`

## Local Development (optional)

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Usage Flow

<img width="1536" height="1024" alt="ChatGPT Image Jan 28, 2026, 04_48_11 PM" src="https://github.com/user-attachments/assets/c9a40ffe-5967-49e6-a7ae-e9cda25348f1" />


1. Login (demo mode)
2. Select a movie
3. Choose a screening time
4. Select seats
5. Confirm booking and get ticket receipt

## API Endpoints

### Movies
- `GET /api/movies`

### Screenings
- `GET /api/screenings/movie/{movieId}`
- `GET /api/screenings/{screeningId}/seats`
- 

### Bookings
- `POST /api/bookings`
```json
{
  "userId": 1,
  "screeningId": 1,
  "seatIds": [1, 2, 3],
  "firstName": "John",
  "lastName": "Doe",
  "age": 25,
  "paymentMethod": "CARD",
  "promoCode": "OPTIONAL"
}
```

## Default Data (Seed)
- 7 movies with poster URLs
- 1 cinema hall (5 rows × 8 seats)
- screenings created for each movie
- seats created per screening
- demo customer: `demo@example.com / password`

## Project Structure

```
movie app/
├── backend/
│   ├── src/main/java/com/example/cinema/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access
│   │   ├── model/          # Entity models
│   │   ├── dto/            # Data transfer objects
│   │   └── config/         # Configuration classes
│   └── pom.xml
├── frontend/
│   ├── public/posters/     # Poster images
│   ├── src/
│   │   ├── App.jsx         # Main React component
│   │   ├── index.css       # Styles
│   │   └── main.jsx        # Entry point
│   └── vite.config.js
├── docker-compose.yml
├── REPORT.md
└── README.md
```

## Notes
- Poster files are served from `frontend/public/posters`.
- If you change seed data or schema, restart with `docker compose down -v`.
# Online Cinema Ticket Booking System

A full-stack web application for managing cinema reservations and ticket sales. Built with Spring Boot (Backend) and React (Frontend).

## Features

- 🎬 **Movie Selection**: Browse and select from 7 available movies with poster images
- 🎫 **Seat Booking**: Interactive seat map with real-time availability (5 rows × 8 seats)
- 👤 **Customer Information**: Collect customer details (name, surname, age) during booking
- 📄 **PDF Receipt**: Download booking confirmation as PDF
- 🔄 **Navigation**: Forward/Backward navigation buttons with step validation
- 🔒 **Security**: Spring Security with CORS configuration
- 💾 **Database**: H2 in-memory database (PostgreSQL supported)

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (default) / PostgreSQL (optional)
- **Lombok**
- **Maven**

### Frontend
- **React 18.2**
- **Vite 5.0**
- **Axios**
- **jsPDF** (for PDF generation)
- **CSS3**

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17+** (JDK)
- **Maven** (must be accessible via `mvn` command)
- **Node.js** (v18+ recommended)
- **npm** (comes with Node.js)
- **PostgreSQL** (optional - H2 database is used by default)

### Installing Maven (if not already installed)

#### Windows
1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to `C:\tools\apache-maven-x.x.x`
3. Add `C:\tools\apache-maven-x.x.x\bin` to your system PATH environment variable
4. Verify installation:
   ```bash
   mvn -v
   ```

#### Linux/Mac
```bash
sudo apt install maven  # Ubuntu/Debian
brew install maven      # macOS
```

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd "movie app"
```

### 2. Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. (Optional) Configure database in `src/main/resources/application.properties`:
   - Default: H2 in-memory database (no configuration needed)
   - PostgreSQL: Uncomment PostgreSQL configuration lines

3. Start the backend server:
   ```bash
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

   **Note**: On Windows PowerShell, if `mvn` is not recognized, add Maven to PATH temporarily:
   ```powershell
   $env:Path += ";C:\tools\apache-maven-3.9.12\bin"
   mvn spring-boot:run
   ```

### 3. Frontend Setup

1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Add movie poster images (optional but recommended):
   - Place 7 movie poster images in `src/assets/movies/` with these exact filenames:
     - `inception.jpg`
     - `matrix.jpg`
     - `interstellar.jpg`
     - `dark-knight.jpg`
     - `avatar.jpg`
     - `titanic.jpg`
     - `joker.jpg`
   - Images should be square/landscape format (recommended: 300x400px or similar)

4. Start the development server:
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

## How to Run

### Quick Start

1. **Start Backend** (Terminal 1):
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   Wait for: `Tomcat started on port 8080`

2. **Start Frontend** (Terminal 2):
   ```bash
   cd frontend
   npm run dev
   ```
   Wait for: `Local: http://localhost:5173`

3. **Open Browser**:
   Navigate to `http://localhost:5173`

## Usage Guide

### Step 1: Login
- Enter any username (demo mode - no authentication required)
- Click "Login"

### Step 2: Select Movie
- Browse the 7 available movies displayed as cards
- Click on a movie card to select it (selected card will have a blue border)
- Click "Next →" button to proceed to booking

### Step 3: Book Seats
- Fill in customer information:
  - **First Name**
  - **Last Name**
  - **Age**
- Select seats from the seat map:
  - **Gray**: Available
  - **Green**: Selected
  - **Red**: Already booked
- Review selected seats count and total price
- Click "Confirm Booking" or "Next →" button

### Step 4: View Receipt
- Review booking confirmation with:
  - Ticket ID
  - Customer name and age
  - Movie title
  - Selected seats
  - Price per seat and total price
  - Booking date/time
  - Sold by information
- **Download PDF**: Click "Download PDF" to save receipt as PDF
- **Book Another Ticket**: Start a new booking process
- **← Back**: Return to booking screen (form data preserved)

## Navigation

- **Forward (Next →)**: Only enabled when current step is completed
  - Movie Selection: Enabled when a movie is selected
  - Booking: Enabled when form is filled and seats are selected
- **Backward (← Back)**: Always enabled (except on first screen)
  - Allows returning to previous steps

## API Endpoints

### Movies
- `GET /api/movies` - Get all available movies

### Bookings
- `GET /api/bookings/seats` - Get all seats with availability status
- `POST /api/bookings` - Create a new booking
  ```json
  {
    "userId": 1,
    "movieId": 1,
    "seatIds": [1, 2, 3],
    "firstName": "John",
    "lastName": "Doe",
    "age": 25
  }
  ```

## Database Schema

### Movies
- `id` (Long, Primary Key)
- `title` (String)
- `genre` (String)
- `durationMinutes` (Integer)
- `posterUrl` (String)

### Seats
- `id` (Long, Primary Key)
- `rowLabel` (String) - e.g., "A", "B", "C"
- `seatNumber` (Integer) - e.g., 1, 2, 3
- `status` (Enum: AVAILABLE, LOCKED, BOOKED)
- `lockedByUserId` (Long, nullable)
- `lockTime` (LocalDateTime, nullable)

### Tickets
- `id` (Long, Primary Key)
- `userId` (Long)
- `movie` (Movie, ManyToOne)
- `seats` (List<Seat>, ManyToMany)
- `customerFirstName` (String)
- `customerLastName` (String)
- `customerAge` (Integer)
- `totalPrice` (Double)
- `bookingDate` (LocalDateTime)

## Configuration

### Backend (`application.properties`)
```properties
# H2 Database (Default)
spring.datasource.url=jdbc:h2:mem:cinemadb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# PostgreSQL (Optional - uncomment to use)
# spring.datasource.url=jdbc:postgresql://localhost:5432/cinema_db
# spring.datasource.username=postgres
# spring.datasource.password=password
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Frontend (`vite.config.js`)
- Proxy configuration: `/api` requests are forwarded to `http://localhost:8080`
- Development server port: `5173`

## Default Data

On first startup, the application automatically creates:
- **40 seats** (5 rows: A-E, 8 seats per row)
- **7 movies**:
  1. Inception (Sci-Fi, 148 min)
  2. The Matrix (Action, 136 min)
  3. Interstellar (Sci-Fi, 169 min)
  4. The Dark Knight (Action, 152 min)
  5. Avatar (Adventure, 162 min)
  6. Titanic (Romance, 195 min)
  7. Joker (Drama, 122 min)

## Troubleshooting

### Maven not found
- **Windows**: Add Maven bin directory to PATH environment variable
- **Linux/Mac**: Install via package manager (`apt`, `brew`, etc.)

### Port already in use
- Backend (8080): Change port in `application.properties`: `server.port=8081`
- Frontend (5173): Change port in `vite.config.js`: `server.port: 5174`

### CORS errors
- Ensure backend is running on `http://localhost:8080`
- Check `SecurityConfig.java` for allowed origins

### Movie images not showing
- Verify images are in `frontend/src/assets/movies/`
- Check filenames match exactly (case-sensitive)
- Ensure image files are valid (JPG/PNG format)

## Project Structure

```
movie app/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/cinema/
│   │       │   ├── controller/     # REST controllers
│   │       │   ├── service/        # Business logic
│   │       │   ├── repository/     # Data access
│   │       │   ├── model/          # Entity models
│   │       │   ├── dto/            # Data transfer objects
│   │       │   └── config/         # Configuration classes
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── assets/
│   │   │   └── movies/            # Movie poster images
│   │   ├── App.jsx                # Main React component
│   │   ├── index.css              # Styles
│   │   └── main.jsx               # Entry point
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## License

This project is open source and available for educational purposes.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Contact

For questions or issues, please open an issue on the repository.
