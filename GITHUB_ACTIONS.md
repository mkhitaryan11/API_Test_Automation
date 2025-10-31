# GitHub Actions - Simple Guide

## What This Does

Your tests will run automatically on GitHub every time you push code or create a pull request.

## Setup (2 Steps)

### Step 1: Push to GitHub

```bash
# Add the workflow file
git add .github/

# Commit
git commit -m "Add GitHub Actions for automated testing"

# Push
git push origin main
```

### Step 2: View Results

1. Go to your GitHub repository: https://github.com/mkhitaryan11/API_Test_Automation
2. Click the **Actions** tab
3. You'll see your tests running!

## When Tests Run

Tests automatically run when you:
- Push code to `main`, `master`, or `develop` branches
- Create or update a pull request
- Click "Run workflow" button in Actions tab (manual trigger)

## View Test Reports

After tests complete:

1. Go to **Actions** tab
2. Click on any workflow run
3. Scroll down to **Artifacts** section
4. Download **allure-report**
5. Extract and open `index.html` in your browser

Reports include:
- Test results (passed/failed/skipped)
- Request/response logs
- Detailed error messages
- Execution time

## Artifacts Available

Each test run saves (kept for 30 days):
- **test-results** - Raw test results
- **allure-report** - HTML report (detailed)
- **test-logs** - Execution logs

## Status Badge

Your README now shows a badge with test status:

![API Tests](https://github.com/mkhitaryan11/API_Test_Automation/actions/workflows/api-tests.yml/badge.svg)

- 🟢 Green = Tests passing
- 🔴 Red = Tests failing
- 🟡 Yellow = Tests running

## Configuration (Optional)

### Add Secrets for API Credentials

If you need to configure API URL or credentials:

1. Go to: **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add your secrets (e.g., `API_BASE_URL`, `API_USERNAME`)
4. Update `.github/workflows/api-tests.yml` - uncomment the env section (around line 56):

```yaml
- name: 🧪 Run API Tests
  run: mvn test
  env:
    API_BASE_URL: ${{ secrets.API_BASE_URL }}
    API_USERNAME: ${{ secrets.API_USERNAME }}
```

## Troubleshooting

### Tests fail on GitHub but pass locally?
- Check if API endpoint is accessible from GitHub servers
- Add any required credentials as GitHub Secrets

### Workflow not triggering?
- Make sure you pushed to `main`, `master`, or `develop` branch
- Check that Actions are enabled in repository Settings

### Can't download artifacts?
- Wait for the workflow to complete
- Artifacts are only available for 30 days

## Workflow File

The workflow is defined in: `.github/workflows/api-tests.yml`

It does:
1. Sets up Java 11
2. Installs Maven dependencies (cached for speed)
3. Runs `mvn test`
4. Generates Allure reports
5. Uploads results as artifacts

## That's It!

Just push your code and GitHub will run your tests automatically. Check the Actions tab to see results.

