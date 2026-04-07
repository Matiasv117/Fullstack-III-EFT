# ms-notificaciones

Microservicio de notificaciones del sistema RedNorte.

## Estado actual

El servicio permite:

- crear notificaciones
- consultar notificaciones por ID
- consultar notificaciones por paciente
- listar notificaciones pendientes
- enviar una notificación por defecto o por canal
- consultar canales disponibles
- verificar salud del servicio

> Nota: los canales `EMAIL`, `SMS` y `PUSH` están simulados. No hay integración real con proveedores externos todavía.

## Endpoints

Base path: `/api/notifications`

### Notificaciones

- `POST /api/notifications`
- `GET /api/notifications/pending`
- `GET /api/notifications/{id}`
- `GET /api/notifications/paciente/{pacienteId}`
- `POST /api/notifications/{id}/send`
- `POST /api/notifications/{id}/send-by-channel?canal=EMAIL|SMS|PUSH`
- `POST /api/notifications/send-all`

### Información

- `GET /api/notifications/info/channels`
- `GET /api/notifications/info/health`

## Ejemplo de creación

```http
POST /api/notifications
Content-Type: application/json

{
  "pacienteId": 123,
  "tipo": "CITA_CONFIRMADA",
  "mensaje": "Su cita ha sido confirmada para mañana a las 10:00"
}
```

## Modelo de datos

### Tipos de notificación

- `CITA_CONFIRMADA`
- `CITA_CANCELADA`
- `RECORDATORIO_CITA`
- `CAMBIO_HORARIO`
- `PACIENTE_ASIGNADO`
- `CAMBIO_PRIORIDAD`
- `POSICION_ACTUALIZADA`

### Estados

- `PENDIENTE`
- `ENVIADA`
- `REINTENTANDO`
- `FALLIDA`

## Configuración

El servicio usa:

- Spring Boot 2.7.12
- Java 11
- H2 en memoria
- JPA
- Scheduler habilitado

Configuración principal en `src/main/resources/application.properties`.

## Estructura principal

```text
src/main/java/com/saludrednorte/ms_notificaciones/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── scheduler/
└── service/
```

## Pruebas

El proyecto incluye pruebas unitarias e integración para controller, DTOs, entidad y flujo principal.

## Pendientes reales

- integrar proveedores reales de email, SMS y push
- cambiar H2 por PostgreSQL si se va a desplegar en producción
- mejorar la estrategia de reintentos asíncronos si hace falta alta disponibilidad

## Ejecutar

```bash
mvn test
```


