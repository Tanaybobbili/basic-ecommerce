# Project Setup Guide

## Files to Copy to Eclipse IDE

Copy these files and folders from the project:

### ✅ MUST COPY:
1. **src/** folder - Contains all Java source code
   - src/main/java/com/productstore/
   - src/main/resources/
   - src/test/java/com/productstore/
   
2. **pom.xml** - Maven configuration file

3. **.gitignore** - Git ignore file

### ❌ DON'T COPY:
1. **target/** - Auto-generated during build
2. **frontend/** - React frontend (not needed in Eclipse)
3. **.mvn/** - Maven wrapper
4. **mvnw** and **mvnw.cmd** - Maven wrapper files

## How to Import into Eclipse:

1. Open Eclipse IDE
2. File → Import → Maven → Existing Maven Projects
3. Browse to your project folder (without frontend and target)
4. Click Finish
5. Wait for Maven to download dependencies
6. Run ProductStoreApplication.java as Spring Boot App

## Files Structure:
```
product-store/
├── pom.xml                    ✅ COPY
├── src/                       ✅ COPY
│   ├── main/
│   │   ├── java/
│   │   │   └── com/productstore/
│   │   │       ├── ProductStoreApplication.java
│   │   │       ├── controller/ItemController.java
│   │   │       ├── service/ItemService.java
│   │   │       ├── repo/ItemRepo.java
│   │   │       └── model/Item.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/productstore/
│               └── ProductStoreApplicationTests.java
├── target/                    ❌ DON'T COPY
├── frontend/                  ❌ DON'T COPY (Run in VS Code separately)
├── .gitignore                ✅ COPY
└── README.md                  ✅ COPY (optional)
```

## GitHub Setup:

### Step 1: Initialize Git
```bash
git init
git add .
git commit -m "Initial commit: Product Store with MongoDB"
```

### Step 2: Create GitHub Repository
1. Go to github.com
2. Click "+" → New repository
3. Name: product-store
4. Description: Product Store Application with MongoDB
5. Don't initialize with README (already have files)
6. Click "Create repository"

### Step 3: Connect and Push
```bash
git remote add origin https://github.com/YourUsername/product-store.git
git branch -M main
git push -u origin main
```

## Running the Project:

### Backend (Eclipse):
- Right-click ProductStoreApplication.java
- Run As → Spring Boot App
- Server runs on http://localhost:8080

### Frontend (VS Code):
1. Open frontend folder in VS Code
2. Run: npm install
3. Run: npm start
4. Opens on http://localhost:3000

## MongoDB Configuration:
Update application.properties with your MongoDB credentials:
```properties
spring.data.mongodb.uri=mongodb+srv://TANAYBOBBILI:YOUR_PASSWORD@cluster0.lh287h1.mongodb.net/productstore?retryWrites=true&w=majority&appName=Cluster0
```
