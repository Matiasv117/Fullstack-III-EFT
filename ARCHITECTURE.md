# Arquitectura Completa de Microservicios

## Estructura del Proyecto

```
Fullstack-III-EFT/
├── eureka-server/              # Servidor de Descubrimiento de Servicios
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/saludrednorte/eureka/
│           │       └── EurekaServerApplication.java
│           └── resources/
│               ├── application.properties
│               └── application.yml
│
├── api-gateway/                # API Gateway para enrutamiento
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/saludrednorte/gateway/
│           │       └── ApiGatewayApplication.java
│           └── resources/
│               ├── application.properties
│               └── application.yml
│
├── ms-gestionpacientes/        # Microservicio de Gestión de Pacientes y Lista de Espera
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
│
├── ms-notificaciones/          # Microservicio de Notificaciones
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
│
└── ms-optimizacion/            # Microservicio de Optimización
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            └── resources/
```

## Puertos de Ejecución

- **Eureka Server**: Puerto 8761 - http://localhost:8761
- **API Gateway**: Puerto 8080 - http://localhost:8080
- **ms-gestionpacientes**: Puerto 8083 - http://localhost:8083
- **ms-notificaciones**: Puerto 8085 - http://localhost:8085
- **ms-optimizacion**: Puerto 8084 - http://localhost:8084

## Rutas del API Gateway

El gateway enruta las solicitudes de la siguiente manera:

```
GET/POST http://localhost:8080/api/listas-espera/**     → ms-gestionpacientes:8083
GET/POST http://localhost:8080/lista-espera/**          → ms-gestionpacientes:8083
GET/POST http://localhost:8080/pacientes/**             → ms-gestionpacientes:8083
GET/POST http://localhost:8080/api/notifications/**     → ms-notificaciones:8085
GET/POST http://localhost:8080/optimizacion/**          → ms-optimizacion:8084
```

## Cómo Ejecutar

### 1. Iniciar Eureka Server

```bash
cd eureka-server
./mvnw spring-boot:run
```

### 2. Iniciar API Gateway

```bash
cd api-gateway
./mvnw spring-boot:run
```

### 3. Iniciar los Microservicios

```bash
# En terminales separadas
cd ms-gestionpacientes
./mvnw spring-boot:run

cd ms-notificaciones
./mvnw spring-boot:run

cd ms-optimizacion
./mvnw spring-boot:run
```

## Verificar Servicios Registrados

Una vez que todos los servicios estén ejecutándose, accede a:
- **Eureka Dashboard**: http://localhost:8761

Deberías ver registrados todos los servicios:
- ms-listas-espera
- ms-notificaciones
- ms-optimizacion
- api-gateway

## Comunicación entre Servicios

Los microservicios se comunican automáticamente a través de:
- **OpenFeign**: Para llamadas HTTP entre servicios
- **Eureka**: Para descubrimiento dinámico de servicios
- **Resilience4j**: Para manejo de fallos y circuit breaker

## Ejemplos de Uso

### A través del API Gateway:

```bash
# Registrar paciente
curl -X POST http://localhost:8080/pacientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Perez","dni":"12345678"}'

# Obtener lista de espera
curl http://localhost:8080/lista-espera

# Crear notificación
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{"pacienteId":1,"tipo":"PACIENTE_ASIGNADO","mensaje":"Paciente registrado"}'

# Obtener lista de espera desde optimización
curl http://localhost:8080/optimizacion/lista-espera
```

### Directamente a los microservicios:

```bash
curl http://localhost:8083/pacientes
curl http://localhost:8085/api/notifications
curl http://localhost:8084/optimizacion/lista-espera
```

