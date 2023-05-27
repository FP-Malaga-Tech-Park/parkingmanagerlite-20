# Proyecto por David Hormigo Ramírez para ED
## Estructura
El proyecto es un proyecto Spring Web que sigue un patrón MVC para construir su funcionalidad
## Ejecutar el proyecto
### Usando Maven
Crea un fichero application.yml o application.properties para conectarse al servidor MySQL requerido en la raíz de resources.
Ejecuta el comando mvn spring-boot:run dentro de la raíz del proyecto.
Si todo va bien la aplicación servirá en el puerto 8080
### Usando Docker  
Cre el fichero application.yml o application.properties apuntando al servidor en el contenedor definido en el fichero docker-compose.yml de este proyecto.
Ejecuta los siguientes comandos:
docker-compose build .
docker-compose up -d

Si todo va bien la aplicación servirá en el puerto 8080

### Pruebas
mvn test lanzará todas las pruebas del proyecto
#### Integración
Contiene pruebas de integración con Selenium para la funcionalidad de usuarios

