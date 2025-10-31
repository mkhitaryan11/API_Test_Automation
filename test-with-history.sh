#!/bin/bash

# Script to run tests and build Allure history properly

echo "🚀 API Test Automation with History Tracking"
echo "============================================="

# Step 1: Copy previous history from last report to current results (if exists)
if [ -d "target/site/allure-maven-plugin/history" ]; then
    echo "📦 Copying history from previous report..."
    mkdir -p target/allure-results/history
    cp -r target/site/allure-maven-plugin/history/* target/allure-results/history/
    echo "✅ History copied successfully"
else
    echo "ℹ️  No previous history found (this is normal for first run)"
fi

# Step 2: Run tests (customize the test parameter as needed)
echo ""
echo "🧪 Running tests..."
if [ -z "$1" ]; then
    # Run all tests if no parameter provided
    mvn test
else
    # Run specific test if parameter provided
    mvn test -Dtest="$1"
fi

# Check if tests passed
if [ $? -eq 0 ]; then
    echo "✅ Tests completed successfully"
else
    echo "❌ Tests failed, but continuing to generate report..."
fi

# Step 3: Generate Allure report
echo ""
echo "📊 Generating Allure report..."
mvn allure:report

# Step 4: Show summary
echo ""
echo "============================================="
echo "✅ Done! History has been preserved."
echo ""
echo "📈 To view the report with history:"
echo "   mvn allure:serve"
echo ""
echo "🔄 To run more tests and build more history:"
echo "   ./test-with-history.sh"
echo "   or"
echo "   ./test-with-history.sh UserApiTests#testGetCurrentUserProfile"
echo "============================================="

