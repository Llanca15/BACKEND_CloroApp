# CloroApp — Backend Microservices (Spring Boot)

Backend distribuido compuesto por dos microservicios desarrollados en Kotlin con Spring Boot 3 y Maven, diseñados para soportar las operaciones de monitoreo y gestión de cloro en bebederos agrícolas para la aplicación móvil **CloroApp**.

---

## 1. Arquitectura de Microservicios

| Microservicio | Puerto | Base de Datos (H2) | Swagger OpenAPI | Descripción |
| :--- | :---: | :---: | :---: | :--- |
| **`ms-monitoreo`** | `8081` | `./data/clorodb-monitoreo` | `http://localhost:8081/swagger-ui.html` | CRUD de puntos de monitoreo y registro de lecturas de cloro (ppm). |
| **`ms-alertas`** | `8082` | `./data/clorodb-alertas` | `http://localhost:8082/swagger-ui.html` | Gestión de alertas por desviación de rango y acciones correctivas. |

---

## 2. Endpoints Disponibles

### A. `ms-monitoreo` (Puerto 8081)

#### Puntos de Medición (`/api/puntos`)
- `GET /api/puntos`: Lista todos los puntos (opcional `?granja=...`).
- `GET /api/puntos/{codigo}`: Detalle de un punto según su código único.
- `POST /api/puntos`: Registra un nuevo punto de medición.
- `PUT /api/puntos/{codigo}`: Actualiza los datos de un punto existente.
- `DELETE /api/puntos/{codigo}`: Elimina el punto y sus lecturas asociadas.

#### Mediciones de Cloro (`/api/mediciones`)
- `GET /api/mediciones`: Historial general de lecturas (opcional `?codigoPunto=...`).
- `GET /api/mediciones/ultimas`: Última medición registrada de cada punto (consumido por la app móvil).
- `GET /api/mediciones/{id}`: Detalle de una medición por su identificador.
- `POST /api/mediciones`: Registra una nueva lectura de cloro en ppm.
- `PUT /api/mediciones/{id}`: Actualiza los datos de una lectura existente.
- `DELETE /api/mediciones/{id}`: Elimina una lectura errónea.

---

### B. `ms-alertas` (Puerto 8082)

#### Alertas (`/api/alertas`)
- `GET /api/alertas`: Lista alertas registradas (opcional `?estado=ABIERTA` o `CERRADA`).
- `GET /api/alertas/{id}`: Detalle de una alerta específica.
- `POST /api/alertas`: Registra una nueva alerta ante desviación crítica o advertencia.
- `PUT /api/alertas/{id}/cerrar`: Cierre y validación de una alerta por supervisor.
- `DELETE /api/alertas/{id}`: Elimina una alerta y sus acciones asociadas.

#### Acciones Correctivas (`/api/acciones`)
- `GET /api/acciones`: Lista acciones registradas (opcional `?alertaId=...`).
- `GET /api/acciones/{id}`: Detalle de una acción correctiva.
- `POST /api/acciones`: Registra una medida correctiva ante una alerta abierta.
- `DELETE /api/acciones/{id}`: Elimina el registro de una acción correctiva.

---

## 3. Instrucciones de Ejecución

### Pruebas y compilación de ambos microservicios:
```bash
./mvnw clean test
```

### Ejecutar `ms-monitoreo`:
```bash
./mvnw spring-boot:run -pl ms-monitoreo
```

### Ejecutar `ms-alertas`:
```bash
./mvnw spring-boot:run -pl ms-alertas
```

Ambos servicios inicializan datos base automáticamente al iniciar por primera vez (`DataInitializer`).
