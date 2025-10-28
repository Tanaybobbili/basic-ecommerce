# Product Store Application

A full-stack product management application with Spring Boot backend and React frontend.

## Backend (Spring Boot)

### Requirements
- Java 21
- Maven
- Eclipse IDE

### Setup
1. Import the project into Eclipse
2. Run `ProductStoreApplication.java`
3. Backend will start on http://localhost:8080

### Features
- REST API for managing items
- H2 in-memory database
- Image upload support
- Search functionality

### API Endpoints
- GET `/api/items` - Get all items
- GET `/api/item/{id}` - Get item by ID
- POST `/api/item` - Add new item
- PUT `/api/item/{id}` - Update item
- DELETE `/api/item/{id}` - Delete item
- GET `/api/item/{id}/image` - Get item image
- GET `/api/items/search?keyword={keyword}` - Search items

## Frontend (React)

### Requirements
- Node.js
- npm

### Setup
1. Navigate to frontend directory: `cd frontend`
2. Install dependencies: `npm install`
3. Start the application: `npm start`
4. Frontend will start on http://localhost:3000

### Features
- Display all items
- Search items
- Add new items with image upload
- Delete items
- Responsive design

## Configuration

Backend runs on port 8080 and frontend runs on port 3000. CORS is configured to allow frontend to communicate with backend.

### Run Commands (PowerShell)
- Backend:
```
./mvnw spring-boot:run
```
- Frontend:
```
npm start
```

## Database

The application uses mongodb atlas for database
