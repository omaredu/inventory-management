# Gestión de Inventario

Una aplicación de Android para la gestión de inventario desarrollada con Kotlin y Jetpack Compose.

## Descripción del Proyecto

Esta aplicación ayuda a gestionar inventarios al rastrear productos, sus cantidades y sus valores. Proporciona funciones para agregar, actualizar y eliminar productos, así como para ver estadísticas del inventario.

## Estructura del Proyecto

| Paquete/Directorio                                   | Descripción                                                                    |
| ---------------------------------------------------- | ------------------------------------------------------------------------------ |
| `app/src/main/java/com/omaredu/inventory_management/` | Paquete raíz para todo el código de la aplicación                              |
| `data/model/`                                        | Modelos de datos que representan las entidades principales de la aplicación     |
| `data/repository/`                                   | Repositorios que gestionan operaciones de datos y sirven como fuentes de datos para la interfaz de usuario |
| `navigation/`                                        | Componentes de navegación que definen el gráfico de navegación y las rutas de las pantallas |
| `ui/`                                                | Componentes de la interfaz de usuario organizados por pantalla/función         |
| `ui/theme/`                                          | Componentes relacionados con el tema, incluyendo colores, tipografía y formas  |
| `ui/dashboard/`                                      | Pantalla de panel que muestra estadísticas del inventario                      |
| `ui/products/`                                       | Pantalla de lista de productos que muestra todos los productos con capacidades de gestión |
| `ui/addproduct/`                                     | Pantalla para agregar nuevos productos al inventario                           |
| `ui/productdetail/`                                  | Pantalla que muestra información detallada sobre un producto específico        |

## Componentes Clave

### Modelos de Datos

| Modelo            | Descripción                                                                           |
| ----------------- | ------------------------------------------------------------------------------------- |
| `Product`         | Representa un producto en el inventario con ID, nombre, descripción, precio y cantidad |
| `DashboardState`  | Representa el estado de la pantalla del panel con información estadística             |

### Repositorios

| Repositorio         | Descripción                                                                      |
| ------------------- | -------------------------------------------------------------------------------- |
| `ProductRepository` | Gestiona las operaciones de datos de productos utilizando un patrón singleton con almacenamiento en memoria |

### Pantallas

| Pantalla              | Descripción                                                                           |
| --------------------- | ------------------------------------------------------------------------------------- |
| `DashboardScreen`     | Pantalla principal que muestra estadísticas del inventario y navegación para la gestión de productos |
| `ProductListScreen`   | Muestra todos los productos con funciones para aumentar/disminuir cantidad y eliminar productos |
| `AddProductScreen`    | Formulario para agregar nuevos productos al inventario                                |
| `ProductDetailScreen` | Vista detallada de un producto específico                                             |

### Navegación

| Componente       | Descripción                                                        |
| ---------------- | ------------------------------------------------------------------ |
| `AppNavigation`  | Define el gráfico de navegación de la aplicación con rutas a todas las pantallas |
| `Screen`         | Clase sellada que define todas las rutas de navegación en la aplicación |

## Funcionalidades

- Ver estadísticas del inventario (total de productos, artículos en stock, artículos con bajo stock, valor total)
- Ver y gestionar el inventario de productos
- Agregar nuevos productos al inventario
- Ver información detallada de productos
- Actualizar cantidades de productos
- Eliminar productos del inventario
- Navegación entre diferentes pantallas

## Tecnologías Utilizadas

- **Kotlin** - Lenguaje de programación principal
- **Jetpack Compose** - Herramienta moderna de UI declarativa para Android
- **Arquitectura MVVM** - Separación de la lógica de negocio y la interfaz de usuario utilizando ViewModel
- **Componente de Navegación** - Para gestionar la navegación entre pantallas
- **Diseño Material 3** - Componentes y estilos modernos de Material Design

## Notas de Implementación Actual

- La aplicación utiliza almacenamiento en memoria a través del patrón singleton `ProductRepository`
- El umbral de bajo stock está configurado en 5 artículos
- La moneda está formateada en Pesos Mexicanos (MXN)

