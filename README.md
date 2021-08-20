# API REST EL PUNTO
API REST que proporciona iformación consumida por la app movil de 'El Punto'

## Características
- [x] Registro y edición de usuario con roles
- [x] Subida y edición de foto para usuario
- [x] Login de usuarios
- [x] Listar usuarios completos y segun su ID
- [x] Mostrar la foto del usuario según su ID
- [x] Eliminar usuario
- [x] Listar roles de usuario
- [x] Listar categorías con respectivos productos
- [x] Crear nueva categoría
- [x] Obtener producto según ID
- [x] Subir, editar y mostrar foto de producto
- [x] Registro y eliminación de producto
- [x] Generar pedido de venta
- [x] Listar pedidos de venta
- [x] Anular pedidos de venta
- [x] Mostrar detalle del pedido de venta

## Tecnologías usadas
 - Java 14
 - Spring Boot 2.5.1
 - MariaDB

## A tener en cuenta
Puerto usado para correr la API: 9898
Se puede cambiar dirigiendose a la carpeta application.properties
`server.port=9898`

Tamaño máximo para subir foto: 10MB
Se puede aumentar o disminuir en application.properties
`spring.servlet.multipart.max-file-size=10MB`


