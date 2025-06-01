# ProTube

> A YouTube-inspired video-sharing platform built with Java (Spring Boot) and React.  
> Originally developed as a university lab project; now open-sourced for my GitHub portfolio.

---

## 🚀 Project Overview

**ProTube** is a full-stack web application that mimics core YouTube features:
- Users can register, log in, and log out.
- Authenticated users can upload videos, comment on videos, and like/dislike content.
- Implements day/night mode, video search (powered by MongoDB), and user acceptance ratio metrics.
- Includes a separate Python “video grabber” tool to fetch video metadata, thumbnails, and generate sample content during development.

This project is split into three main components:

1. **Backend**: A Spring Boot application (Java) that exposes REST endpoints, handles authentication, and communicates with a PostgreSQL database (main) and MongoDB (for search indexing).
2. **Frontend**: A React (TypeScript) single-page application that consumes the backend API.
3. **Video Grabber Tool**: A standalone Python script to scrape video metadata/thumbnail files based on a list of URLs (used initially to seed the application with sample videos).

---

## 🏗 Architecture & Tech Stack

| Component        | Technology                                  |
|------------------|---------------------------------------------|
| Backend          | Java 17, Spring Boot, Maven, PostgreSQL     |
| Frontend         | React 18, TypeScript, Vite, Axios, Tailwind CSS |
| Video Grabber    | Python 3.9, `requests`, `Pillow` (for thumbnails)  |
| Databases        | PostgreSQL (main), MongoDB (search index)   |
| Authentication   | Spring Security (BCrypt password hashing)   |
| Testing          | JUnit 5 (backend), React Testing Library + Jest (frontend) |
| Containerization | Docker (optional; `compose.yml` provided)   |
| CI/CD (future)   | GitHub Actions (recommended for builds/tests) |

### Folder Structure

```
.
├── backend/                # Spring Boot application
│   ├── src/
│   │   ├── main/java/...
│   │   ├── main/resources/
│   │   │   ├── application.properties
│   │   │   └── templates/index.html
│   │   └── test/java/...
│   └── pom.xml
│
├── frontend/               # React (TypeScript) application
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── App.tsx
│   │   ├── main.tsx
│   │   └── ...
│   ├── .env.development
│   ├── .env.production
│   ├── package.json
│   └── vite.config.ts
│
├── tooling/                # Miscellaneous utilities
│   └── videoGrabber/       # Python script for grabbing video data
│       ├── main.py
│       ├── dependencies.txt
│       └── README.md       
│
├── resources/              # PNG screenshots and docs for setup
│   ├── env-variables.png
│   ├── mvp-authentication.png
│   └── ...other screenshots
│
├── compose.yml             # Docker Compose to run Postgres and MongoDB
├── mongo-init.js           # Initial script to seed MongoDB with collections/indexes
├── .gitignore
└── README.md               # ← This file
```

---

## ✅ Features

- **User Authentication & Authorization**
  - Register, log in, and log out.
  - BCrypt-encrypted passwords.
  - Protects routes so only authenticated users can upload, comment, or like.

- **Video Management**
  - Upload MP4 videos (with a thumbnail generated during upload).
  - Delete your own videos.
  - View public feeds of all uploaded videos.

- **Comments & Reactions**
  - Leave comments on any video’s detail page.
  - Edit or delete your own comments.
  - Like or dislike videos; displays user acceptance ratio (likes ÷ total votes).

- **Search & Discovery**
  - Search bar at the top navigates results in real time.
  - Search functionality powered by MongoDB for efficient indexing and text search.
  - Filters based on video title, tags, or category.

- **UI/UX Enhancements**
  - Light/Dark (Day/Night) mode—auto-detects system theme by default, switchable via a toggle.
  - Responsive design (mobile-friendly layout).

- **Database Setup**
  - **PostgreSQL** as the primary relational database for storing users, videos, comments, and votes.
  - **MongoDB** to index video metadata for fast search queries and text-based filters.

- **Comprehensive Testing**
  - Backend unit and integration tests with JUnit 5.
  - Frontend component tests with React Testing Library + Jest.

---

## 📦 Prerequisites

Before running anything locally, ensure you have:

1. **Java 17 SDK** (check with `java -version`)
2. **Maven 3.6+** (check with `mvn -v`)
3. **Node.js 14+ & npm 6+** (check with `node -v` and `npm -v`)
4. **Python 3.9+** (for the video grabber tool)
5. **PostgreSQL 13+**
6. **MongoDB 5+**
7. **Git** (for cloning)
8. **Docker & Docker Compose** (optional, but recommended for running databases)

---

## ⚙️ Getting Started

Follow these steps to run ProTube locally.

### 1. Clone the Repo

```bash
git clone https://github.com/ajutra/protube
cd protube
```

### 2. Running Databases with Docker Compose

A `compose.yml` file in the root directory allows you to easily start both PostgreSQL and MongoDB:

```bash
docker-compose -f compose.yml up -d
```

This will launch two containers:
- **postgres** (postgres-dev, port 5432)
- **mongodb** (mongodb-dev, port 27017)

#### 2.1 PostgreSQL Setup

By default, the PostgreSQL container initializes a database named `protube` and a user `root` with password `secret`. To customize, you can edit `compose.yml`.

Once PostgreSQL is running, you can access it:
```bash
psql -h localhost -p 5432 -U root -d protube
```

#### 2.2 MongoDB Setup

MongoDB container initializes the `protube` database using credentials root/secret. To seed initial collections or indexes for video search, `mongo-init.js` (in the root) will run on startup.

Verify MongoDB is running:
```bash
mongo --username root --password secret --authenticationDatabase admin --port 27017
```

### 3. Configure Environment Variables

Copy and modify the example `.env` files for both backend and frontend.

#### Backend

The Spring Boot application expects the following environment variables:

```bash
export ENV_PROTUBE_DB=protube
export ENV_PROTUBE_DB_USER=root
export ENV_PROTUBE_DB_PWD=secret
export ENV_PROTUBE_STORE_DIR=/absolute/path/to/video/storage
```

These map to:
- `spring.datasource.url=jdbc:postgresql://localhost:5432/${ENV_PROTUBE_DB}`
- `spring.datasource.username=${ENV_PROTUBE_DB_USER}`
- `spring.datasource.password=${ENV_PROTUBE_DB_PWD}`
- `spring.data.mongodb.uri=mongodb://${ENV_PROTUBE_DB_USER}:${ENV_PROTUBE_DB_PWD}@localhost:27017`
- `spring.data.mongodb.database=${ENV_PROTUBE_DB}`

Ensure `ENV_PROTUBE_STORE_DIR` points to where videos (and thumbnails) will be stored on your local machine.

#### Frontend

Rename and edit:

```bash
cd frontend
cp .env.development .env.local
```

Set the API endpoint URLs:

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_WS_URL=ws://localhost:8080/ws
```

If you run the backend on a different port or domain, update accordingly.

### 4. (Optional) Seed Static Videos

If you want to populate the app with sample video files, use the **Video Grabber** script:

#### Dependencies:

* yt-dlp 
  * On Linux / Windows with WSL
    * ```commandline
      sudo wget https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -O /usr/local/bin/yt-dlp
      // And after:
      sudo chmod a+rx /usr/local/bin/yt-dlp
      ```
    * On mac
      ```commandline
      brew install yt-dlp
      ```
* ffmpeg
    * On Windows: Download the ffmpeg executable from FFmpeg’s official website. Extract the files and add the bin
      directory to your system’s PATH.
    * On macOS: Use Homebrew to install ffmpeg:
      ```commandline
      brew install ffmpeg
      ```
    * On Linux:  On Linux / Windows with WSL Install ffmpeg using your package manager. For example, on Ubuntu:
      ```commandline
        sudo apt-get update
        sudo apt-get install ffmpeg
      ```
#### Run the script:

```bash
cd tooling/videoGrabber
pip install -r dependencies.txt

# Edit `resources/video_list.txt` to include URLs of public MP4s.
python3 main.py   --id=1   --store=../sample_videos   --recreate=True   --videos=../../../resources/video_list.txt
```

- This generates `*.mp4`, `*.webp`, and `*.json` for each URL listed in `video_list.txt`.
- Move the output into `backend/src/main/resources/videos/` if you want them served directly (adjust paths).


### 5. Build & Run the Backend

```bash
cd backend

mvn clean install

# Run the Spring Boot app:
mvn spring-boot:run
```

By default, the backend runs on `http://localhost:8080`.  
You can also package as a JAR:
```bash
mvn package
java -jar target/protube-back-0.0.1-SNAPSHOT.jar
```

### 6. Build & Run the Frontend

Open a new terminal:

```bash
cd frontend
npm install

npm run dev
```

- The React dev server should start on `http://localhost:3000` (or another port if 3000 is in use).  
- It proxies API requests to the backend (`/api/*` → `http://localhost:8080/api/*`) as defined in `vite.config.ts`.

---

## 📚 Usage

1. **Register a New Account**  
   Click “Register” in the top‐right corner. Use any email and a strong password.

2. **Log In**  
   Once registered, click “Log In” and enter your credentials.

3. **Browse Videos**  
   The home page lists all uploaded videos. Click on a thumbnail to view details, watch the video, comment, and like/dislike.

4. **Upload Your Video**  
   - Go to **Profile → My Videos**.  
   - Click **Upload Video**.  
   - Choose an MP4 file (max size depends on your `application.properties` max‐upload settings).  
   - Add title, description, category, and tags, then click “Submit.”  
   - Once uploaded, the video appears in your “My Videos” list.

5. **Comment & React**  
   On any video’s detail page:
   - Leave a comment in the “Comments” section.
   - Click the thumbs-up/thumbs-down icons to like or dislike.  
   - View the “Acceptance Ratio” (e.g., 15/20 = 75% likes).

6. **Search & Filter**  
   Use the search bar at the top center to filter videos by title or tags. Results update as you type, using MongoDB-powered search.

7. **Day/Night Mode**  
   Toggle between light and dark themes using the moon/sun icon in the header. Your preference is stored locally.

---

## 🧪 Testing

### Backend Tests

```bash
cd backend
mvn test
```

- JUnit 5 runs all unit and integration tests under `src/test/java`.
- Reports appear in `backend/target/surefire-reports/`.

### Frontend Tests

```bash
cd frontend
npm run test
```

- Uses Jest + React Testing Library.
- Coverage reports (optional) appear under `frontend/coverage/`.

---

## 📂 Directory Breakdown

```
backend/                     
├── src/
│   ├── main/java/com/tecnocampus/LS2/protube_back
│   │   ├── adapter/                  # Controllers & adapters for REST endpoints
│   │   ├── application/              # Application runner and config classes
│   │   ├── domain/                   # Entities, services, and business logic
│   │   └── port/                     # Port definitions (interfaces) for Clean Architecture
│   ├── main/resources/
│   │   ├── application.properties    # Defines both PostgreSQL and MongoDB connections
│   │   └── templates/index.html      # Base template (if serving static pages)
│   └── test/java/...                 # Unit/integration tests
├── pom.xml
└── compose.yml                       # Docker Compose for Postgres + MongoDB

frontend/                    
├── public/                           # Static assets (favicon, logo, index.html)
├── src/
│   ├── components/                   # Reusable React components
│   ├── pages/                        # Route pages (Home, VideoDetail, Profile, etc.)
│   ├── services/                     # Axios API wrappers
│   ├── App.tsx
│   └── main.tsx
├── .env.*                            # Environment variable templates
└── package.json

tooling/videoGrabber/        
├── main.py                           # Python script: fetches video, thumbnail, metadata
├── dependencies.txt                  # `pip install -r dependencies.txt`
└── README.md                         # Usage instructions for the grabber tool

mongo-init.js                         # Initializes MongoDB collections/indexes for search
resources/
└── *.png                             # Setup screenshots (for local dev)
```

---

## 🤝 Contributing

Contributions are welcome! If you spot a bug or want to add a feature:

1. Fork the repository.
2. Create a new branch:  
   ```bash
   git checkout -b feature/my-new-feature
   ```
3. Commit your changes:  
   ```bash
   git commit -m "Add some feature"
   ```
4. Push to your branch:  
   ```bash
   git push origin feature/my-new-feature
   ```
5. Open a Pull Request.

Please follow the existing code style.

---

## 📜 License

This project is licensed under the **GNU GPLv3 License**. See [LICENSE](LICENSE) for details.

---

## ✉️ Contact

**Authors:** Aitor Juanola, Laura Parra and Nil Roset  

**GitHub:** 

[github.com/ajutra](https://github.com/ajutra)

[github.com/LaauParraa](https://github.com/LaauParraa)
            
[github.com/nilroset](https://github.com/nilroset)
