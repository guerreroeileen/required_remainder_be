#!/bin/bash

# Build script for Required Remainder Backend

echo "🏗️ Building Required Remainder Backend..."

# Clean and build the project
echo "📦 Building JAR file..."
./gradlew clean build -x test

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ JAR build successful"
    
    # Build Docker image
    echo "🐳 Building Docker image..."
    docker build -t required-remainder-backend .
    
    if [ $? -eq 0 ]; then
        echo "Docker build successful"
        echo "Next steps:"
        echo "Tag the image: docker tag required-remainder-backend:latest <ECR_URI>:<TAG>"
        echo "Push to ECR: docker push <ECR_URI>:<TAG>"
        echo "3. Update CloudFormation stack with DeployService=true"
    else
        echo "Docker build failed"
        exit 1
    fi
else
    echo "❌ JAR build failed"
    exit 1
fi 