# Proyecto-TipoC

# 🕒 Registro de jornada

¡Bienvenido a **Registro de jornada**! 👋

Esta aplicación es una API REST desarrollada con **Spring Boot** para gestionar departamentos, empleados y sus fichajes de entrada y salida. Permite organizar empleados por departamento, consultar sus datos y revisar el historial de jornada.

## ✨ ¿Qué puedes hacer?

- 🏢 Crear, consultar, modificar y eliminar departamentos.
- 👩‍💻 Crear, consultar, modificar y eliminar empleados.
- 🔗 Asignar un empleado a un departamento.
- ⏱️ Registrar fichajes de entrada y salida.
- 📋 Consultar el historial de fichajes de cada empleado.
- 🛡️ Recibir respuestas de error claras cuando los datos no son válidos o un recurso no existe.

## 🧰 Tecnologías

- ☕ Java 21
- 🍃 Spring Boot
- 🗃️ Spring Data JPA
- 🧪 H2
- ✅ Jakarta Bean Validation
- 📖 OpenAPI y Swagger UI
- 📦 Maven

## 🚀 Cómo ejecutar la aplicación

Necesitas tener instalado **Java 21**. El proyecto incluye el wrapper de Maven, así que no hace falta instalar Maven por separado.

Abre una terminal en la carpeta del proyecto y ejecuta:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### macOS o Linux

```bash
./mvnw spring-boot:run
```

Cuando Spring Boot termine de arrancar, la API estará disponible en:

```text
http://localhost:8080
```

## 📖 Documentación interactiva

Con la aplicación en marcha, abre Swagger UI para consultar y probar los endpoints desde el navegador:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

La especificación OpenAPI en formato JSON está disponible en:

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🧭 Rutas principales

| Recurso | Ruta | Operaciones |
|---|---|---|
| Departamentos | `/api/v1/departamentos` | Crear y listar |
| Departamento por ID | `/api/v1/departamentos/{id}` | Consultar, actualizar y eliminar |
| Empleados | `/api/v1/empleados` | Crear y listar |
| Empleado por ID | `/api/v1/empleados/{id}` | Consultar, actualizar y eliminar |
| Asignación de departamento | `/api/v1/empleados/{empleadoId}/departamento/{deptoId}` | Asignar un departamento |
| Registrar fichaje | `/api/v1/empleados/{empleadoId}/fichar` | Registrar entrada o salida |
| Historial de fichajes | `/api/v1/empleados/{empleadoId}/fichajes` | Consultar el historial |

## 🧾 Ejemplos rápidos

### Crear un departamento

```http
POST /api/v1/departamentos
Content-Type: application/json
```

```json
{
  "name": "Desarrollo",
  "presupuesto": 50000.00
}
```

### Crear un empleado

```http
POST /api/v1/empleados
Content-Type: application/json
```

```json
{
  "empleadoId": null,
  "departamentoId": null,
  "nombreCompleto": "Ana García",
  "cargo": "Desarrolladora",
  "salario": 2800.00
}
```

### Registrar un fichaje automático

```http
POST /api/v1/empleados/1/fichar
```

Sin cuerpo, la API alterna automáticamente entre `ENTRADA` y `SALIDA`.

También se puede indicar el tipo en el cuerpo:

```json
{
  "tipo": "ENTRADA"
}
```

Los valores aceptados son `ENTRADA` y `SALIDA`.

## 🧪 Probar la API con Postman

La colección de peticiones está incluida en el proyecto:

`postman/Registro de jornada.postman_collection.json`

Para cargarla, abre Postman, selecciona **Import → File** y elige ese archivo.

Orden recomendado para probar el flujo:

1. Crear un departamento.
2. Crear un empleado.
3. Asignarlo al departamento.
4. Registrar entrada y salida.
5. Consultar el historial de fichajes.

La petición **Validar Departamento** contiene datos incorrectos a propósito y debe responder `400 Bad Request`.

## ⚠️ Respuestas de error

La API utiliza respuestas `ProblemDetail` para comunicar los errores más habituales:

- `400 Bad Request`: los datos enviados no pasan las validaciones.
- `404 Not Found`: el empleado o departamento solicitado no existe.
- `409 Conflict`: se intenta repetir el último tipo de fichaje.

## 🗂️ Estructura del proyecto

El código está organizado por responsabilidades:

- `controller`: endpoints REST.
- `dto`: objetos de entrada y respuestas de la API.
- `error`: excepciones y tratamiento centralizado de errores.
- `model`: entidades y tipos del dominio.
- `repository`: acceso a datos mediante Spring Data JPA.
- `service`: reglas y operaciones de negocio.
- `validation`: validaciones personalizadas.

¡Arranca la aplicación, abre Swagger o Postman y empieza a probarla! 🚀