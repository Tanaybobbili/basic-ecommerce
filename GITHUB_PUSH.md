# GitHub Push Guide

## How to Push Project to GitHub

### Step 1: Initialize Git (if not already done)
Open terminal in your project folder and run:

```bash
git init
```

### Step 2: Add Files to Git
```bash
git add .
```

### Step 3: Make Initial Commit
```bash
git commit -m "Initial commit: Product Store with MongoDB backend and React frontend"
```

### Step 4: Create GitHub Repository

1. Go to https://github.com
2. Click "+" icon (top right)
3. Select "New repository"
4. Fill in:
   - Repository name: `product-store`
   - Description: `Product Store Application with MongoDB Atlas and React frontend`
   - Choose: Public or Private
   - ❌ DON'T check "Initialize with README" (we already have files)
   - ❌ DON'T add .gitignore or license (we already have)
5. Click "Create repository"

### Step 5: Connect and Push

After creating the repo, GitHub will show commands. Run these:

```bash
git remote add origin https://github.com/TANAYBOBBILI/product-store.git
git branch -M main
git push -u origin main
```

## If Git Credentials Required:

If asked for credentials:
- Username: `TANAYBOBBILI`
- Password: Use Personal Access Token (not your GitHub password)

### How to Get Personal Access Token:
1. GitHub → Settings → Developer settings
2. Personal access tokens → Tokens (classic)
3. Generate new token
4. Select scopes: `repo`
5. Generate
6. Copy token and use as password when pushing

## After Pushing:

Your code will be live at:
```
https://github.com/TANAYBOBBILI/product-store
```

## Next Time (Updating Code):

When you make changes:

```bash
git add .
git commit -m "Description of changes"
git push
```
