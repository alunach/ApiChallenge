# ApiChallenge

API RESTful desarrollada en Java con Spring Boot, que permite el registro de usuarios, generación y validación de tokens JWT, y persistencia en base de datos en memoria. La arquitectura está basada en el enfoque **hexagonal (puertos y adaptadores)** para separar claramente el dominio de la infraestructura.

## 1. Funcionalidades

1. Registro de usuario con validación de email y contraseña por expresiones regulares.
2. Validación de unicidad del correo electrónico.
3. Generación y persistencia de tokens JWT.
4. Estructura de respuesta estándar en formato JSON.
5. Persistencia en base de datos H2.
6. Documentación con Swagger.
7. Arquitectura hexagonal.
8. Configuración vía `application.properties`.

## 2. Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JWT (jjwt 0.12.x)
- Maven
- Swagger / OpenAPI
- Arquitectura Hexagonal

## 3. Estructura del proyecto

```
src
├── main
│   ├── java
│   │   └── com.api.challenge
│   │       ├── application          # Casos de uso y servicios de aplicación
│   │       ├── domain               # Lógica de dominio y modelos
│   │       ├── infrastructure
│   │       │   ├── controller       # Controladores REST
│   │       │   ├── persistence      # Entidades JPA y repositorios
│   │       │   ├── repository       # Adaptador de persistencia
│   │       │   └── security         # JWT y configuración de seguridad
│   │       └── shared               # Clases comunes, excepciones, utilidades
│   └── resources
│       └── application.properties
```

## 4. Instalación y ejecución

```bash
# Clonar el repositorio
git clone https://github.com/alunach/ApiChallenge.git
cd ApiChallenge

# Construir el proyecto
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
```

La API estará disponible en: [http://localhost:9090](http://localhost:9090)

## 5. Documentación Swagger (En construcción)

Puedes acceder a Swagger UI en:

```
http://localhost:8080/swagger-ui/index.html
```

## 6. Endpoints principales

### POST /api/register

**Request:**

```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
```

**Response (200 OK):**

```json
{
  "id": "UUID",
  "created": "2024-04-20T10:00:00",
  "modified": "2024-04-20T10:00:00",
  "lastLogin": "2024-04-20T10:00:00",
  "token": "jwt-token",
  "isActive": true,
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [...]
}
```

## 7. Errores comunes

| Código | Mensaje                               |
|--------|----------------------------------------|
| 400    | El correo no tiene un formato válido.  |
| 400    | El password no tiene un formato válido.|
| 409    | El correo ya registrado.               |

## 8. Pruebas

Pruebas unitarias próximamente.

## 9. Scripts útiles

- Base de datos H2 accesible desde: `http://localhost:9090/h2-console`
  - JDBC URL: `jdbc:h2:~/test`
  - Usuario: `sa`
  - Contraseña: *(vacía)*

## 10. Licencia

MIT © [@alunach](https://github.com/alunach)