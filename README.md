# Required Remainder Backend

A Spring Boot application that solves the "Required Remainder" algorithmic problem. The application finds the maximum integer k such that 0 ≤ k ≤ n and k mod x = y for given values of x, y, and n.

## 🚀 Features

- **RESTful API**: REST endpoints for solving remainder problems
- **Input Validation**: Comprehensive validation with detailed error messages
- **Exception Handling**: Error handling with trace IDs
- **High Performance**: Optimized algorithm for large numbers (up to 10^9)
- **Comprehensive Testing**: Unit and integration tests with >80% coverage
- **AWS Integration**: CloudFormation templates for infrastructure deployment
- **GitHub Actions**: Actions for deploy changes into the previously deployed infrastructure

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.5.3
- **Language**: Java 24
- **Build Tool**: Gradle
- **Validation**: Bean Validation (Jakarta)
- **Testing**: JUnit 5 + Mockito

### Infrastructure (AWS)
- **Compute**: ECS Fargate
- **Container Registry**: ECR
- **Load Balancer**: Application Load Balancer
- **Networking**: VPC with public subnets
- **Logging**: CloudWatch Logs

## 📋 Prerequisites

### Local Development
- Java 24 or higher
- Gradle 8.x
- Docker (optional)

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

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

   The application will start on `http://localhost:8080`


### AWS Deployment

The backend is automatically deployed via GitHub Actions when you push to the main branch. The workflow will:

1. Build and test the application
2. Build Docker image
3. Push to ECR
4. Create/update ECS service

## 📚 API Documentation

### Base URL
- **Local**: `http://localhost:8080`
- **AWS**: `http://<load-balancer-dns>`

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


## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

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

The backend uses GitHub Actions for CI/CD. The workflow (`/.github/workflows/aws.yml`) will:

1. **Build and Test**: Compile, run tests, and check coverage
2. **Docker Build**: Create Docker image with the application
3. **ECR Push**: Push image to Amazon ECR
4. **ECS Deployment**: Create or update ECS service

### Required Secrets

Configure these secrets in your GitHub repository:

| Secret | Description        |
|--------|--------------------|
| `AWS_ACCESS_KEY_ID` | AWS access key     |
| `AWS_SECRET_ACCESS_KEY` | AWS secret key     |
| `ECR_REPOSITORY` | AWS ECR repository |


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
Logs are automatically sent to CloudWatch with the log group: `/ecs/required-remainder-backend`
