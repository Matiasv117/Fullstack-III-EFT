# 📊 GUÍA INSFORGE - Backend as a Service para IA

**Fecha:** 28 de Abril, 2026  
**Propósito:** Entender cómo funciona Insforge y cómo integrarlo

---

## ¿QUÉ ES INSFORGE?

Insforge es un **Backend-as-a-Service (BaaS)** diseñado específicamente para agentes de IA.

### En palabras simples:
```
BD Tradicional (PostgreSQL):
  Tú creas tablas → Escribes SQL → Guardas datos → Recuperas datos

Insforge:
  Tú describes tablas en lenguaje natural → Insforge crea API REST → 
  Llamas a API con HTTP → Insforge maneja BD automáticamente
```

### Ventajas:
- ✅ No necesitas gestionar servidor de BD
- ✅ API REST automática
- ✅ Autenticación integrada
- ✅ Fácil de entender desde código
- ✅ Perfecto para demostración (no hay que explicar SQL)

### Desventajas:
- ❌ Requiere conexión a internet (es en la nube)
- ❌ Es nuevo (menos documentación)
- ❌ Costo potencial (hay que ver)
- ❌ Vendor lock-in (si Insforge cierra, buscas alternativa)

---

## CÓMO FUNCIONA (VISUALMENTE)

```
┌─────────────────────────────────────────────────────────────┐
│                    TU APLICACIÓN                            │
│  ms-gestionpacientes, ms-notificaciones, ms-optimizacion   │
└────────────┬────────────────────────────────────────────────┘
             │ HTTP Request
             │ (JSON payload)
             ↓
┌─────────────────────────────────────────────────────────────┐
│                    INSFORGE API                             │
│  https://api.insforge.io/v1                                │
│                                                             │
│  POST /collections/pacientes                               │
│  GET /collections/pacientes                                │
│  PUT /collections/pacientes/{id}                           │
│  DELETE /collections/pacientes/{id}                        │
└────────────┬────────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────────┐
│                    INSFORGE BD (Cloud)                      │
│  PostgreSQL / MongoDB / Similar                            │
│  (Tú no la gestiones, Insforge lo hace)                    │
└─────────────────────────────────────────────────────────────┘
```

---

## PRIMEROS PASOS CON INSFORGE

### PASO 1: Crear cuenta
1. Ir a https://insforge.io (o URL que tu profesor diga)
2. Sign up / Registrarse
3. Verificar email
4. Loguear

### PASO 2: Crear proyecto
1. En dashboard → "New Project"
2. Nombre: "SaludRedNorte"
3. Create

### PASO 3: Obtener credenciales
Después de crear proyecto, buscar:
- **API Key** - Token para autenticarte
- **Project ID** - Identificador del proyecto
- **API URL** - `https://api.insforge.io/v1` (o similar)

Guardar en `.env`:
```env
INSFORGE_API_KEY=xyz...
INSFORGE_PROJECT_ID=123...
INSFORGE_API_URL=https://api.insforge.io/v1
```

---

## CREAR MODELOS (TABLAS) EN INSFORGE

### Opción 1: Dashboard web
1. Ir a Insforge dashboard
2. "Data Models" o "Collections"
3. "Create Collection"
4. Nombre: "pacientes"
5. Agregar campos:
   - `id` (auto-increment)
   - `nombre` (text)
   - `apellido` (text)
   - `dni` (text, unique)
   - `email` (text)
   - `createdAt` (timestamp)

### Opción 2: API (Más rápido)
```bash
curl -X POST https://api.insforge.io/v1/collections \
  -H "Authorization: Bearer $INSFORGE_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "pacientes",
    "fields": {
      "id": { "type": "int", "autoIncrement": true },
      "nombre": { "type": "string", "required": true },
      "apellido": { "type": "string", "required": true },
      "dni": { "type": "string", "unique": true },
      "email": { "type": "string" },
      "createdAt": { "type": "timestamp", "default": "now" }
    }
  }'
```

### Modelos que necesitas crear:
1. **pacientes**
   - id, nombre, apellido, dni, email, createdAt

2. **notificaciones**
   - id, pacienteId, tipo, mensaje, canal, estado, creadoAt, enviadoAt

3. **citas**
   - id, pacienteId, medicoId, fecha, estado, createdAt

---

## INTEGRACIÓN EN JAVA/SPRING

### Opción A: RestTemplate (Simple)

```java
// InsforgeService.java
@Service
public class InsforgeService {
    
    @Value("${insforge.api.url}")
    private String apiUrl;
    
    @Value("${insforge.api.key}")
    private String apiKey;
    
    private RestTemplate restTemplate = new RestTemplate();
    
    public Map<String, Object> createPaciente(Paciente p) {
        String url = apiUrl + "/collections/pacientes";
        
        // Preparar headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Preparar body
        Map<String, Object> body = new HashMap<>();
        body.put("nombre", p.getNombre());
        body.put("apellido", p.getApellido());
        body.put("dni", p.getDni());
        body.put("email", p.getEmail());
        
        // Hacer request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        
        return response.getBody();
    }
    
    public List<Map<String, Object>> getPacientes() {
        String url = apiUrl + "/collections/pacientes";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, 
            HttpMethod.GET, request, List.class);
        
        return response.getBody();
    }
}
```

### Opción B: HttpClient (Alternativa)

```java
@Service
public class InsforgeService {
    
    @Value("${insforge.api.url}")
    private String apiUrl;
    
    @Value("${insforge.api.key}")
    private String apiKey;
    
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public Map<String, Object> createPaciente(Paciente p) throws Exception {
        String url = apiUrl + "/collections/pacientes";
        
        Map<String, Object> body = Map.of(
            "nombre", p.getNombre(),
            "apellido", p.getApellido(),
            "dni", p.getDni(),
            "email", p.getEmail()
        );
        
        String jsonBody = objectMapper.writeValueAsString(body);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        return objectMapper.readValue(response.body(), Map.class);
    }
}
```

---

## ACTUALIZAR SERVICES PARA USAR INSFORGE

### ANTES (Usando BD local)
```java
@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository repository;  // ← BD local
    
    public Paciente registrarPaciente(Paciente paciente) {
        return repository.save(paciente);  // ← Guarda en H2
    }
}
```

### DESPUÉS (Usando Insforge)
```java
@Service
public class PacienteService {
    
    @Autowired
    private InsforgeService insforgeService;  // ← BD en la nube
    
    public Paciente registrarPaciente(Paciente paciente) {
        // Guardar en Insforge
        Map<String, Object> resultado = insforgeService.createPaciente(paciente);
        
        // Convertir resultado a entidad
        Paciente p = new Paciente();
        p.setId(((Number) resultado.get("id")).longValue());
        p.setNombre((String) resultado.get("nombre"));
        p.setApellido((String) resultado.get("apellido"));
        
        return p;
    }
}
```

---

## MANEJO DE ERRORES

```java
public Paciente registrarPaciente(Paciente paciente) {
    try {
        Map<String, Object> resultado = insforgeService.createPaciente(paciente);
        
        if (resultado == null || resultado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Insforge no respondió correctamente");
        }
        
        // Convertir y retornar
        return convertirAEntidad(resultado);
        
    } catch (Exception e) {
        logger.error("Error al guardar en Insforge: {}", e.getMessage());
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, 
            "Base de datos no disponible", e);
    }
}
```

---

## EJEMPLO COMPLETO: REGISTRAR PACIENTE

```java
// 1. PacienteController recibe request
@PostMapping
public Paciente registrarPaciente(@RequestBody Paciente paciente) {
    return service.registrarPaciente(paciente);
}

// 2. PacienteService procesa
@Service
public class PacienteService {
    
    @Autowired
    private InsforgeService insforgeService;
    
    @Autowired
    private NotificationClient notificationClient;
    
    public Paciente registrarPaciente(Paciente paciente) {
        // Guardar en Insforge
        Map resultado = insforgeService.createPaciente(paciente);
        Paciente saved = convertir(resultado);
        
        // Notificar (como antes)
        try {
            NotificationRequestDTO notif = new NotificationRequestDTO();
            notif.setPacienteId(saved.getId());
            notif.setTipo("PACIENTE_REGISTRADO");
            notif.setMensaje("Paciente registrado");
            notificationClient.createNotification(notif);
        } catch (Exception e) {
            logger.warn("Notificación falló pero paciente guardado", e);
        }
        
        return saved;
    }
}

// 3. InsforgeService hace la llamada HTTP
@Service
public class InsforgeService {
    
    public Map<String, Object> createPaciente(Paciente p) {
        // ... código mostrado arriba ...
    }
}

// 4. Insforge (en la nube) guarda en BD
// Y retorna la respuesta
```

---

## OPERACIONES COMUNES (CRUD)

### CREATE (Crear)
```java
public Map<String, Object> create(String coleccion, Map<String, Object> datos) {
    String url = apiUrl + "/collections/" + coleccion;
    HttpEntity<Map> request = new HttpEntity<>(datos, getHeaders());
    return restTemplate.postForObject(url, request, Map.class);
}
```

### READ (Leer)
```java
public List<Map> getAll(String coleccion) {
    String url = apiUrl + "/collections/" + coleccion;
    HttpEntity<String> request = new HttpEntity<>(getHeaders());
    ResponseEntity<List> response = restTemplate.exchange(url, 
        HttpMethod.GET, request, List.class);
    return response.getBody();
}

public Map<String, Object> getById(String coleccion, Long id) {
    String url = apiUrl + "/collections/" + coleccion + "/" + id;
    HttpEntity<String> request = new HttpEntity<>(getHeaders());
    return restTemplate.getForObject(url, Map.class, request);
}
```

### UPDATE (Actualizar)
```java
public Map<String, Object> update(String coleccion, Long id, 
                                   Map<String, Object> datos) {
    String url = apiUrl + "/collections/" + coleccion + "/" + id;
    HttpEntity<Map> request = new HttpEntity<>(datos, getHeaders());
    return restTemplate.exchange(url, HttpMethod.PUT, request, 
        Map.class).getBody();
}
```

### DELETE (Borrar)
```java
public void delete(String coleccion, Long id) {
    String url = apiUrl + "/collections/" + coleccion + "/" + id;
    HttpEntity<String> request = new HttpEntity<>(getHeaders());
    restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
}
```

---

## TESTING CON CURL

```bash
# 1. Crear paciente
curl -X POST http://localhost:8080/pacientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez","dni":"12345678","email":"juan@example.com"}'

# 2. Listar pacientes (desde Insforge)
curl http://localhost:8080/pacientes

# 3. Obtener paciente específico
curl http://localhost:8080/pacientes/1

# 4. Actualizar
curl -X PUT http://localhost:8080/pacientes/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Updated"}'

# 5. Eliminar
curl -X DELETE http://localhost:8080/pacientes/1
```

---

## VARIABLES DE ENTORNO

En `application.yml`:
```yaml
insforge:
  api:
    url: ${INSFORGE_API_URL:https://api.insforge.io/v1}
    key: ${INSFORGE_API_KEY}
    project-id: ${INSFORGE_PROJECT_ID}
```

En `.env`:
```env
INSFORGE_API_URL=https://api.insforge.io/v1
INSFORGE_API_KEY=tu_api_key_aqui
INSFORGE_PROJECT_ID=tu_project_id_aqui
```

En `docker-compose.yml`:
```yaml
services:
  ms-gestionpacientes:
    environment:
      INSFORGE_API_KEY: ${INSFORGE_API_KEY}
      INSFORGE_PROJECT_ID: ${INSFORGE_PROJECT_ID}
      INSFORGE_API_URL: ${INSFORGE_API_URL}
```

---

## VENTAJAS PARA TU PROYECTO

✅ **Simple:** Solo HTTP, sin SQL complejo  
✅ **Moderno:** Usado por agentes de IA (como tú mismo usas Cursor)  
✅ **Explicable:** "Usamos un backend en la nube que maneja la BD por nosotros"  
✅ **Poco código:** No necesitas escribir queries SQL  
✅ **Demostrable:** El profesor verá que funciona en la nube

---

## ERRORES COMUNES

| Error | Causa | Solución |
|-------|-------|----------|
| 401 Unauthorized | API Key inválida | Verificar `INSFORGE_API_KEY` |
| 404 Not Found | Colección no existe | Crear colección en Insforge |
| 500 Bad Request | JSON inválido | Verificar estructura de datos |
| Connection refused | Insforge caído | Esperar o usar Mock data |
| Lentitud | Internet lento | Es normal, es HTTPS |

---

## ALTERNATIVA SI INSFORGE FALLA

Si en algún momento Insforge no funciona, puedes:
1. **Usar H2 (en memoria)** - Temporal para testing
2. **Usar Mock data** - Simular respuestas
3. **Volver a PostgreSQL** - Más trabajo pero funciona

---

## RESUMEN

**Insforge es:**
- Un backend en la nube
- Se comunica por HTTP/REST
- Perfecto para demostración
- No hay que instalar/configurar BD
- Ideal para este proyecto

**Lo único que necesitas:**
- Cuenta en Insforge
- API Key
- Crear modelos
- Llamar a API desde Java

¡Está casi listo! 🚀

---

Próximo paso: Vuelve a **PLAN_ACCION_V2.md** y comienza FASE 1.

