# language: es
Característica: Gestion de sorteos
  
  Escenario: Navegación a la lista de sorteos
    Dado esta en la pagina inicial
    Cuando el usuario hace click sobre el botón de Sorteos
    Entonces la url de la pagina lista de sorteos es correcta
  
  Escenario: Crear un sorteo correctamente
    Dado esta en la pagina creación de sorteos
    Cuando relleno el campo descripción con Nuevo Sorteo
    Y el usuario hace click sobre el botón de crear sorteo
    Entonces la url de la pagina lista de sorteos es correcta
    Y se ha persistido el sorteo en la base de datos
  