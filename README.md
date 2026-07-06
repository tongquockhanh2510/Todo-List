# Todo List App
Frontend Demo: https://todo-list-omega-lac-68.vercel.app
Backend API: https://todo-list-1-yd8l.onrender.com/api/todos
## Tech Stack
- Backend: Spring Boot, PostgreSQL, JPA
- Frontend: React, TypeScript, Vite
- Database: Neon PostgreSQL
- Deploy: Render, Vercel

## Run Backend
cd backend
./gradlew bootRun

## Run Frontend
cd frontend
npm install
npm run dev

## Environment Variables

Backend:
DB_URL=
DB_USERNAME=
DB_PASSWORD=

Frontend:
VITE_API_BASE_URL=http://localhost:8080/api/todos