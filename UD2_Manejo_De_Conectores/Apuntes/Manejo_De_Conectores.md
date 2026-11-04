# ACCESO A DATOS : Manejo de conectores

## Índice

1. <span></span>[Introducción.](#base-de-datos-relacionales)
2. [Desafse Objeto-Relacional.](#desfase-objeto-relacional)
3. [Conectores de Bases de Datos](#conectores-de-bases-de-datos)
4. [Introducción JDBC: Arquitectura y tipos de drivers.]()
5. [Ejecución de sentencias para definiciones de datos.]()
6. [Ejecución de sentencias de manipulación de datos.]()
7. [Ejecución de consultas.]()
8. [Gestión de transacciones.]()
9. [Manejo de excepciones y recursos.]()

10) [Metadatos.]()
11) [Patrones de diseño: MVC y DAO.]()
12) [Elaboración de un CRUD.]()

## Base De Datos Relacionales

Una **base de datos** es un conjunto organizado de datos almacenados estructurados que permiten operaciones de acceso, actualización y gestión eficientes.

Las **bases de datos relacionales** son las más utilizadas debido a su robustez, madurez y amplia adopción.

**Características principales:**

- La información se organiza mediantes filas y columnas.
- Cada fila es un registro y cada columna un atributo.
- Se pueden relacionar las diferentes tablas mediantes claves primarias y foráneas.
- Permiten aplicar diferentes restricciones para mantener la consistencia de datos.
- Utiliza SQL como lenguajeestándar para las diferentes operaciones.

**Ejemplos**: MySQL, PostgresSQL, Oracle...

## Desfase Objeto-Relacional

En el desarrollo de aplicaciones es habitual utilizar dos modelos diferentes de representar la información:

- **Orientado a objetos**: Lo utilizan programas donde os datos se representan mediante objetos, clases, atributos y relaciones, como Java.
- **Relacional**: Se utiliza donde los datos se organizan mediante tablas, filas, columnas y relaciones entre tablas.

A veces, debido a la diferencia entre los dos modelos, puede haber problemas al pasar objetos Java a bases de datos relacionales o al pasar los datos de la base a un objeto Java. A este problema se le denomina **Desfase Objeto-Relacional**.

Para relacionar ambos modelos debemos hacer conversiones entre objetos Java y registros de la base de datos.

## Conectores De Bases De Datos

Un **conector de bases de datos** es un componente software que permite la comunicación entre una aplicación con un sitema gestor de bases de datos (**SGBD**).

Los conectores facilitan muchas de las oepraciones como establecer conexiones, ejecutar sentencias SQL y recuperar resultados.

En java hay dos elementos fundamentales:

- **API JDBC**: Conjunto de clases e interfaces estándar proporcionada por Java para acceder a las BBDD.
- **Driver JDBC**: biblioteca que implementa las herramienras necesarias para comunicarse con un SGBD concret.

Por lo general los fabricantes de BBDD proporcionan driver compatibles con JDBC.

**Ejemplo**: Java -- MySQL &rarr; MySQL **Connector**

## JDBC

### Indice

1.  [Introducción](#qué-es)
2.  [Arquitectura](#arquitectura-jdbc)
3.  [Drivers](#tipos-de-drivers-jdbc)
4.  []()
5.  []()

### ¿Qué es?

JDBC (**Java DataBase Connectivity**) es la API estándar que Java proporciona para la comunicación entre la aplicación y las BBDD además de proporcionar las clases e interfaces necesarias para realizar las operaciones sobre las BBDD generalmente relacionales.

**Carcaterísticas principales**:

- Permite establecer conexiones con BBDD.
- Facilita la ejecución de sentencias SQL así como la recuperación y procesamiento de los resultados de las consultas.
- Proporciona herramientas para la gestión de transacciones y control de errores comunes.
- Cuenta con un interfaz de programación comú independientemente de la SGBD utilizada.

El JDBC **no resuelve los problemas de desfase Objeto-Relacional**. El programador debe hacer la comversión entre objetos Java, registros de las tablas de la BBDD y viceversa.

### Arquitectura JDBC

Utiliza una arquitectura modular formada por 4 componentes principales:

- **Aplicación Java**: programa desarrollado por un programador y que interactúa con la BBDD.
- **API JDBC** (java.sql y javax.sql): conjunto de clases e interfaces que ofrece herramientas estándar de acceso a la BBDD.
- **Driver JDBC**: biblioteca específica que implementa la comunicación entre la aplicación y la BBDD.
- **SGBD**: software encargado de almacenar, gestionar y recuperar los datos.

Gracias a esta arquitectura, se pueden implementar diferentes SGBD sin tener que modificar todo el código. Aunque seguramente sea necesario hacer cambios en las URL's, las dependencias y las sentencias concretas de cada SGBD.

### Tipos De Drivers JDBC

Existen cuatro tipos de drivers que se clasifican según su forma de comunicación con la BBDD:

- **Tipo 1** (JDBC-ODBC Bridge Driver): traduce las llamadas JDBC a llamadas ODBC. Tecnología obsoleta, elimnada del JDK a partir de Java 8.
  - llamadas JDBC &rarr; llamadas ODBC;
- **Tipo 2** (Native-API Driver): depende de componentes instalados en el sistema ya que utiliza bibliotecas nativas del proveedor de la BBDD.
- **Tipo 3** (Network Protocol Driver // MiddleWare): emvía las solicitudes del JDBC a un servidor intermedio que se encarga de la comunicación con la BBDD.
  - solicitudes JDBC &rarr; servidor (MiddleWare) &rarr; BBDD
- **Tipo 4** (Pure Java Driver // Direct-To-DataBase): implementado completamente en Java y se comunica con el SGBD directamente mediante su protocolo de red, suministrado por el fabricante de la BBDD. Traduce directamente las llamadas JDBC en protocolo nativo del SGBD.
  - JDBC &rarr; SGBD

Los drivers tipo 4 son los más utilizados actualmente gracias a su portabilidad y rendimineto a parte de no necesitar bibliotecas nativas adicionales.

#### Driver JDBC Para MySQL

MySQL es una BDD relacional muy compatible con JDBC mediante drivers específicos.
**Drivers**:

- MySQLConnector/J
  - tipo 4
  - descarga: [MySQLConnector](https://dev.mysql.com/downloads/connector/j/)
  - Proyecto Maven:
  ```xml
  <dependencies>
      <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.7.0</version>
      </dependency>
  </dependencies>
  ```

#### Driver JDBC Para H2

H2 es un SGBD ligero y de código abierto desarrollado en Java.
Puede funcinar en modo embebido o cliente-servidor, útil para pruebas, desarrollo y aplicaciones Java que necesitan una BBDD sencilla.

**Drivers**:

- H2 DataBase Engine (org.h2.Driver)
  - tipo 4
  - descarga: [H2](https://h2database.com/html/download.html)
  - Proyecto Maven:
  ```xml
  <dependencies>
      <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.5.252</version>
      </dependency>
  </dependencies>
  ```
  Tanto el motor H2 como su driver JDBC se distribuyen en la misma dependencia.

### URL JDBC

La Url JDBC identifica el tipo de BBDD y contiene la información necesaria para establecer la conexión.

El formato cambia según la SGBD utilizada.
**Ejemplo**: jdbc:mysql://localhost:3306/mi_base <span>13<span>
