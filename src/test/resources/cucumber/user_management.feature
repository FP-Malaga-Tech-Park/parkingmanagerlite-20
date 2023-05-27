# language: es
Característica: Gestion de usuarios
  
  Escenario: Navegación a la lista de usuarios
    Dado esta en la pagina inicial
    Cuando el usuario hace click sobre el botón de Usuarios
    Entonces la url de la pagina lista de usuarios es correcta

  
  Escenario: Comprobar que el formulario de creación de usuarios tiene todos los elementos
    Dado esta en la pagina creación de usuarios
    Entonces se muestra un campo de correo electrónico
    Y se muestra un campo de nombre
    Y se muestra un campo de primer apellido
    Y se muestra un campo de segundo apellido
    Y se muestra un botón de creación

  Escenario: Crear un usuario correctamente
    Dado esta en la pagina creación de usuarios
    Y el correo usuario@correo.com no esta asignado a otro usuario
    Cuando relleno el campo correo electrónico con usuario@correo.com
    Y relleno el campo nombre con David
    Y relleno el campo primer apellido con Hormigo
    Y el usuario hace click sobre el botón de crear usuario
    Entonces la url de la pagina lista de usuarios es correcta
    Y se ha persistido el usuario en la base de datos

  Escenario: Correo duplicado
    Dado esta en la pagina creación de usuarios
    Y el correo usuario@correo.com existe en la base de datos
    Cuando relleno el campo correo electrónico con usuario@correo.com
    Y relleno el campo nombre con David
    Y relleno el campo primer apellido con Hormigo
    Y el usuario hace click sobre el botón de crear usuario
    Entonces la url de la pagina creación de usuarios es correcta
    Y no se ha persistido el usuario en la base de datos
  
  Escenario: No se ha rellenado el primer apellido
    Dado esta en la pagina creación de usuarios
    Y el correo usuario@correo.com no esta asignado a otro usuario
    Cuando relleno el campo correo electrónico con usuario@correo.com
    Y relleno el campo nombre con David
    Y el usuario hace click sobre el botón de crear usuario
    Entonces la url de la pagina creación de usuarios es correcta
    Y no se ha persistido el usuario en la base de datos
  
  Escenario: No se ha rellenado el nombre
    Dado esta en la pagina creación de usuarios
    Y el correo usuario@correo.com no esta asignado a otro usuario
    Cuando relleno el campo correo electrónico con usuario@correo.com
    Y relleno el campo primer apellido con Hormigo
    Y el usuario hace click sobre el botón de crear usuario
    Entonces la url de la pagina creación de usuarios es correcta
    Y no se ha persistido el usuario en la base de datos

  Escenario: No se ha rellenado el correo
    Dado esta en la pagina creación de usuarios
    Cuando relleno el campo primer apellido con Hormigo
    Y relleno el campo nombre con David
    Y el usuario hace click sobre el botón de crear usuario
    Entonces la url de la pagina creación de usuarios es correcta
    Y no se ha persistido el usuario en la base de datos    