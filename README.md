# Flowstate

Flowstate is a lightweight web analytics engine built with Spring Boot, Vue 3, and PostgreSQL. It captures frontend events, stores them in a local database, and shows the data in a recruiter-friendly dashboard so you can demonstrate both the product and the implementation.

## What This Project Does

Flowstate has two parts:

1. A Spring Boot backend that accepts analytics events and serves dashboard statistics.
2. A Vue 3 frontend that shows a landing page, sends a test analytics event, and renders a dashboard view with live stats.

When you click the action button on the home page, the app sends a JSON payload to the backend, which stores it in PostgreSQL. The dashboard then reads aggregated analytics from the API and displays browser, OS, device, hostname, and page information.

## What It Looks Like

The home screen is a motion-heavy landing page with a prominent hero section, a live analytics action button, feature cards, and an expandable content block.

The dashboard screen looks like a dark analytics console with a left navigation rail, a host-aware header, a global time filter, connection status, and metric cards for site and user analytics.

## Tech Stack

- Backend: Spring Boot 3, Java 21, Spring Web, Spring Data JPA, Validation
- Database: PostgreSQL
- Frontend: Vue 3, TypeScript, Vite, vue-router, Motion One
- Local HTTPS: Vite runs on `https://localhost:5173`, and the backend runs on `https://localhost:8443`

## Requirements

- Java 21
- Node.js 20 or newer
- npm
- PostgreSQL running locally

## Local Setup

### 1. Start PostgreSQL

Create a local PostgreSQL database and make sure the credentials in `src/main/resources/application.properties` match your local setup.

Default values in this repo:

- Database: `Databasename`
- Username: `username`
- Password: `password`

If you want to use different values, update `src/main/resources/application.properties` before starting the backend.

### 2. Run the backend

Open a terminal in the project root and start the Spring Boot app.

Linux and macOS:

```bash
./mvnw spring-boot:run
```

Windows PowerShell or Command Prompt:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend starts on `https://localhost:8443`.

Useful endpoint:

- Health/status: `https://localhost:8443/api/v1/analytics/status`

### 3. Run the frontend

Open a second terminal in the `ui` folder and install dependencies if needed.

Linux and macOS:

```bash
cd ui
npm install
npm run dev
```

Windows PowerShell or Command Prompt:

```powershell
cd ui
npm install
npm run dev
```

The frontend starts on `https://localhost:5173`.

### 4. Open the app

Open the Vite URL in your browser. Because the project uses local HTTPS with a self-signed certificate, your browser may show a security warning the first time. That is expected for local development.

## How It Works

- The frontend generates or reuses a visitor id in local storage.
- The home page sends analytics events to `POST /api/v1/analytics`.
- The backend stores the event in PostgreSQL.
- The dashboard calls `GET /api/dashboard/stats` with the current host and time filter.
- The UI renders the returned metrics in custom analytics cards.

## Backend API

### `GET /api/v1/analytics/status`
Returns a simple live-check message.

### `GET /api/v1/analytics`
Returns all stored analytics events.

### `GET /api/v1/analytics/{id}`
Returns one analytics event by id.

### `GET /api/v1/analytics/site?url=...`
Returns analytics events for a specific site URL.

### `POST /api/v1/analytics`
Creates a new analytics event.

### `GET /api/dashboard/stats?host=...&cutOff=...`
Returns grouped dashboard statistics for the selected host and time range.

## Project Structure

```text
flowstate/
├── src/main/java/...        # Spring Boot backend
├── src/main/resources       # Application configuration
├── ui/                      # Vue frontend
└── README.md                # Project overview and setup
```

## For Recruiters

If you want to understand the value quickly, start here:

1. Open the home page to see the visual presentation and event tracking demo.
2. Click the action button to confirm analytics events are being sent.
3. Open the dashboard to see how the stored data is grouped and displayed.
4. Check the backend endpoints to see the API contract and persistence layer.

## Notes

- The project uses local HTTPS in development, so certificate warnings are normal.
- If the frontend cannot reach the backend, confirm PostgreSQL is running and the backend is listening on port `8443`.
- If you change the backend port or database credentials, update the frontend API URLs and the Spring configuration accordingly.
