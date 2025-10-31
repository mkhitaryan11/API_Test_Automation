#!/bin/bash

# Simple Test Runner - No History Tracking
# Use this for quick test runs during development

echo "🧪 API Test Automation - Simple Test Runner"
echo "============================================="

# Check if test parameter is provided
if [ -z "$1" ]; then
    echo "📋 Running ALL tests..."
    mvn clean test
else
    echo "📋 Running test: $1"
    mvn clean test -Dtest="$1"
fi

# Check if tests passed
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Tests completed successfully!"
    echo ""
    echo "📊 To view Allure report:"
    echo "   mvn allure:serve"
    echo ""
else
    echo ""
    echo "❌ Tests failed!"
    echo ""
    echo "📊 To view Allure report (including failures):"
    echo "   mvn allure:serve"
    echo ""
    exit 1
fi

