# Eclipse IDE Setup Guide

## Files to Copy to Eclipse IDE for Spring Boot Project

### ✅ COPY THESE FILES:
```
ecom-proj/
├── pom.xml                          ← Maven dependencies
├── .gitignore                       ← Git ignore rules
├── mvnw                             ← Maven wrapper
├── mvnw.cmd                         ← Maven wrapper (Windows)
└── src/                             ← ALL Java source code
    ├── main/
    │   ├── java/
    │   │   └── com/productstore/    ← Your Java files
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
            └── com/productstore/
                └── ProductStoreApplicationTests.java
```

### ❌ DON'T COPY:
- `target/` folder (auto-generated)
- `frontend/` folder (separate React app)
- `.mvn/` folder (Maven wrapper)

## Step-by-Step Eclipse Import:

1. **Open Eclipse IDE**

2. **Import Project:**
   - File → Import
   - Maven → Existing Maven Projects
   - Next

3. **Select Project:**
   - Browse → Select `ecom-proj` folder
   - ✅ Check pom.xml
   - Finish

4. **Wait for Build:**
   - Maven will download dependencies
   - Project should appear in Package Explorer

5. **Run the Application:**
   - Right-click `ProductStoreApplication.java`
   - Run As → Spring Boot App
   - Wait for "Started ProductStoreApplication"

## Verify Setup:
- Check that src folder has all files
- Check that pom.xml is in root
- No errors in Eclipse
