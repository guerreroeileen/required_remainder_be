# Required Remainder Backend

A Spring Boot application that solves the "Required Remainder" algorithmic problem. The application finds the maximum integer k such that 0 ≤ k ≤ n and k mod x = y for given values of x, y, and n.

## 🚀 Features

- **RESTful API**: REST endpoints for solving remainder problems
- **Input Validation**: Comprehensive validation with detailed error messages
- **Exception Handling**: Error handling with trace IDs
- **High Performance**: Optimized algorithm for large numbers (up to 10^9)
- **Comprehensive Testing**: Unit and integration tests with >80% coverage
- **AWS Integration**: Elastic Beanstalk deployment with Docker
- **GitHub Actions**: Automated CI/CD pipeline with test validation

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.5.3
- **Language**: Java 17
- **Build Tool**: Gradle
- **Validation**: Bean Validation (Jakarta)
- **Testing**: JUnit 5 + Mockito
- **Containerization**: Docker with multi-stage builds

### Infrastructure (AWS)
- **Compute**: Elastic Beanstalk with Docker
- **Container Registry**: ECR
- **Load Balancer**: Application Load Balancer (managed by EB)
- **Networking**: VPC with public subnets
- **Logging**: CloudWatch Logs

## 📋 Prerequisites

### Local Development
- Java 17 or higher
- Gradle 8.x
- Docker (optional, for containerized builds)

### AWS Deployment
- AWS CLI configured
- AWS credentials with appropriate permissions
- GitHub Actions secrets configured

## 🛠️ Setup Instructions

### Local Development

1. **Build the project**
   ```bash
   ./gradlew build
   ```

2. **Run tests**
   ```bash
   ./gradlew test
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`

4. **Build Docker image (optional)**
   ```bash
   ./gradlew build
   docker build -t required-remainder-backend .
   ```

### AWS Deployment

The backend is automatically deployed via GitHub Actions when you push to the main branch. The workflow will:

1. Build and test the application
2. Build Docker image
3. Push to ECR
4. Deploy to Elastic Beanstalk

## 📚 API Documentation


### Endpoints

#### POST `/api/required-remainder/solve`

Solves multiple test cases and returns the maximum k values.

**Request Body:**
```json
[
  {
    "x": 7,
    "y": 5,
    "n": 12345
  },
  {
    "x": 5,
    "y": 0,
    "n": 4
  }
]
```

**Response:**
```json
[12339, 0]
```

**Constraints:**
- `x`: 2 ≤ x ≤ 10^9 (divisor)
- `y`: 0 ≤ y < x (remainder)
- `n`: y ≤ n ≤ 10^9 (upper bound)

### cURL Examples

#### Single Test Case
```bash
curl -X POST http://localhost:8080/api/required-remainder/solve \
  -H "Content-Type: application/json" \
  -d '[
    {
      "x": 7,
      "y": 5,
      "n": 12345
    }
  ]'
```

#### Multiple Test Cases
```bash
curl -X POST http://localhost:8080/api/required-remainder/solve \
  -H "Content-Type: application/json" \
  -d '[
    {
      "x": 7,
      "y": 5,
      "n": 12345
    },
    {
      "x": 5,
      "y": 0,
      "n": 4
    },
    {
      "x": 10,
      "y": 5,
      "n": 15
    }
  ]'
```

## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

### Run Tests with Coverage
```bash
./gradlew test jacocoTestReport
```

### Test Structure
- **Unit Tests**: Service layer and business logic
- **Integration Tests**: Controller endpoints and validation
- **Coverage**: Aim for >80% code coverage

### Test Files
- `RequiredRemainderServiceTest.java`: Service layer tests
- `RequiredRemainderControllerTest.java`: Controller integration tests

## 🔧 Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile | `dev` |
| `SERVER_PORT` | Application port | `8080` |
| `SERVER_ADDRESS` | Server address | `0.0.0.0` |

### Application Properties

Key configurations in `application.properties`:

```properties
# Server configuration
server.port=8080
server.address=0.0.0.0

# Validation
spring.validation.enabled=true
spring.validation.bean-validation.enabled=true

# Logging
logging.level.com.example.required_remainder_be=DEBUG

# Actuator
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

## 🚀 Deployment

### GitHub Actions Workflow

The backend uses GitHub Actions for CI/CD. The workflow (`/.github/workflows/aws.yml`) includes:

1. **Setup**: Install Java and Gradle
2. **Test**: Run unit tests and fail if any test fails
3. **Build**: Compile and package the application
4. **Docker Build**: Create Docker image with the application
5. **ECR Push**: Push image to Amazon ECR
6. **EB Deploy**: Deploy to Elastic Beanstalk environment

### Required Secrets

Configure these secrets in your GitHub repository:

| Secret | Description        | Required |
|--------|--------------------|----------|
| `AWS_ACCESS_KEY_ID` | AWS access key     | ✅ |
| `AWS_SECRET_ACCESS_KEY` | AWS secret key     | ✅ |
| `ECR_REPOSITORY` | AWS ECR repository | ✅ |

### Docker Configuration

The application uses a multi-stage Docker build:

```dockerfile
# Build stage
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Runtime stage
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 🔍 Monitoring

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

### Application Info
```bash
curl http://localhost:8080/actuator/info
```

### CloudWatch Logs
Logs are automatically sent to CloudWatch with the log group: `/aws/elasticbeanstalk/required-remainder-backend-env/var/log/eb-docker/containers/eb-current-app/`

## 🔐 Security

### Input Validation
- All inputs are validated using Bean Validation
- Custom validation for business rules
- Detailed error messages with trace IDs

### Error Handling
- Global exception handler
- Proper HTTP status codes
- Structured error responses

## 🐛 Troubleshooting

### Common Issues

1. **Java Version**
   - Ensure Java 17 is installed and configured
   - Check JAVA_HOME environment variable

2. **Build Issues**
   - Clear Gradle cache: `./gradlew clean`
   - Check for dependency conflicts

3. **Deployment Issues**
   - Verify AWS credentials and permissions
   - Check Elastic Beanstalk environment status
   - Review CloudWatch logs for errors

