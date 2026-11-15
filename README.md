# Users Management — Spring Boot, Arquitectura Hexagonal y DDD

Aplicación de gestión de usuarios construida con Java 17 y Spring Boot. La API REST es el punto de entrada activo. El código de la antigua CLI se conserva como adaptador inactivo y no posee un contenedor de dependencias independiente.

Spring es el único *composition root*: `Main` inicia el contexto y las dependencias se resuelven mediante configuración y component scanning de Spring.

## Verificación

```bash
./mvnw clean test
./mvnw clean package
```

En Windows se puede utilizar `mvnw.cmd`.

## Configuración por variables de entorno

Todos los valores de `application.properties` se pueden sobrescribir con variables de entorno (ver `.env.example`). Las más importantes:

| Variable | Local (MySQL/MariaDB) | Render (Supabase) |
|---|---|---|
| `DB_DRIVER` | `mysql` | `postgresql` |
| `DB_HOST` | `localhost` | `aws-1-<region>.pooler.supabase.com` |
| `DB_PORT` | `3306` | `5432` |
| `DB_NAME` | `crud_usuarios` | `postgres` |
| `DB_USERNAME` | `root` | `postgres.<id-del-proyecto>` |
| `DB_PASSWORD` | clave local | clave de la BD de Supabase |
| `DB_SSL_MODE` | (vacío) | `require` |
| `JWT_SECRET` | opcional | 32+ bytes aleatorios en Base64 |
| `PORT` | `8080` | lo define Render |

El cambio de motor (MySQL ↔ PostgreSQL) solo toca la infraestructura: `DatabaseConfig` arma la URL JDBC según `DB_DRIVER`. El SQL de los repositorios es estándar y funciona en ambos motores; dominio y aplicación no cambian.

## Ejecución local

```bash
# 1. Crear la base con src/main/resources/schema.sql (MySQL/MariaDB)
# 2. Compilar y ejecutar
./mvnw clean package
java -jar target/users-management-2.1.0.jar
```

- API: `http://localhost:8080` · Swagger UI: `http://localhost:8080/swagger-ui.html`
- Login: `POST /api/auth/login` con `admin@example.com` / `Admin1234!` y usar el `accessToken` como `Bearer`.

> En Windows, si la ruta del proyecto tiene tildes o espacios, `mvnw spring-boot:run` puede fallar con `ClassNotFoundException: Main`; usar `java -jar` como arriba.

### Docker Compose (API + MySQL)

```bash
docker compose up --build
```

## Despliegue: Render + Supabase

1. **Supabase**: crear un proyecto, abrir *SQL Editor* y ejecutar `database/postgresql/schema.sql` (crea `users` y `universities`, el admin y datos de ejemplo, con RLS activo para bloquear la API REST pública de Supabase).
2. En Supabase: *Connect* → *Direct* → método **Session pooler** y copiar host, puerto y usuario. Se usa el pooler porque la conexión directa es IPv6 y Render no la soporta.
3. **Render**: *New* → *Web Service* (runtime **Docker**, rama `main`, plan Free) y cargar las variables de la tabla anterior (o *New* → *Blueprint* con `render.yaml`).
4. Al quedar *Live*: `https://<servicio>.onrender.com/swagger-ui.html`.

> El plan gratuito de Render suspende el servicio por inactividad (la primera petición puede tardar cerca de un minuto) y bloquea los puertos SMTP: el envío de correos falla pero la API sigue funcionando.

## Integración continua

`.github/workflows/ci.yml` compila y ejecuta las pruebas (`mvn verify`) en cada push y pull request, y construye la imagen Docker. Render despliega automáticamente cada commit que llega a `main` (*Auto-Deploy*).
## Pruebas con Postman

En `postman/` están la colección y dos entornos:

- `users-management-universidades.postman_collection.json`: login (guarda `{{token}}`) y CRUDL de universidades (la creación guarda `{{universityId}}`), con pruebas de código de estado: 401, 200, 201, 409, 400, 204 y 404.
- `local.postman_environment.json` (`http://localhost:8080`) y `render.postman_environment.json` (reemplazar la URL por la del servicio en Render).

En Postman: *Import* → seleccionar los tres archivos → elegir el entorno → *Run collection* (en orden).