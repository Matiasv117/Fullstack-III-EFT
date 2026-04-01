# Microservicio de Optimización de Citas Médicas

Este microservicio implementa el sistema de reasignación automática de citas médicas para optimizar el uso de horas médicas disponibles cuando ocurren cancelaciones, reduciendo la pérdida y mejorando la eficiencia del sistema de atención.

## Arquitectura

### Patrón Repository
- Utilizado para la persistencia de datos en las entidades Cita, Medico y Horario.
- Implementado con Spring Data JPA para consultas personalizadas.

### Patrón Factory Method
- Aplicado en `OptimizacionFactory` para crear instancias de estrategias de optimización (EstrategiaPorGravedad, EstrategiaFIFO).
- Permite flexibilidad en la selección de algoritmos de reasignación.

### Patrón Circuit Breaker
- Implementado con Resilience4j para manejar fallos en la comunicación con el microservicio de gestión de pacientes.
- Protege contra cascadas de fallos y permite recuperación automática.

## Separación de Responsabilidades
- **Controladores**: Manejan las solicitudes HTTP y delegan a servicios.
- **Servicios**: Contienen la lógica de negocio.
- **Repositorios**: Gestionan el acceso a datos.
- **Entidades**: Representan los modelos de datos.

## Escalabilidad y Desacoplamiento
- Microservicio independiente con API REST.
- Registro en Eureka para descubrimiento de servicios.
- Comunicación asíncrona potencial con otros microservicios.

## Endpoints
- `/citas`: Gestión de citas.
- `/medicos`: Gestión de médicos.
- `/horarios`: Gestión de horarios.
- `/optimizacion`: Procesamiento de cancelaciones y reasignación.

## Configuración
- Puerto: 8084
- Base de datos: H2 en memoria
- Eureka: Registrado en eureka-server:8761

## Justificación de Patrones
- **Repository**: Simplifica el acceso a datos y permite consultas complejas.
- **Factory Method**: Facilita la extensión con nuevas estrategias sin modificar código existente.
- **Circuit Breaker**: Asegura resiliencia en entornos distribuidos, evitando fallos en cascada.
