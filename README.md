# CRYPTOFUND

## Descripción del proyecto
La plataforma de crowdfunding CryptoFund es una plataforma descentralizada que permite a los usuarios crear, administrar e invertir en campañas de crowdfunding utilizando criptomonedas. La plataforma está construida sobre la tecnología blockchain y aprovecha los contratos inteligentes para automatizar el proceso de crowdfunding y asegurar que los términos de la campaña se ejecuten según lo acordado. La plataforma está diseñada para ser fácil de usar y accesible para un público amplio, al mismo tiempo que ofrece características avanzadas para inversores y creadores de proyectos experimentados.

## Diagrama de arquitectura
![](https://github.com/Kayeddy/cryptofund_workshop/blob/development/Arquitectura%20Taller.jpg)

## Descripción de funcionalidades
### Módulo campañas: 
Agrupa todas las funcionalidades de las campañas, específicamente la gestión que se realiza para su creación, edición y eliminación hecho por los usuarios, al igual que poder visualizar todas las campañas creadas.
### Módulo donación:
Agrupa todas las funcionalidades de las donaciones, específicamente la gestión que se realiza para la creación de una donación hecha por un usuario, al igual que poder visualizar todas las donaciones realizadas a una campaña especifica o por un usuario especifico.
### Módulo usuario:
Agrupa todas las funcionalidad de los usuarios, especificamente la gestión que se realiza para su creación, edición y eliminación, al igual que poder conectar su wallet para realizar donaciones a las campañas ya creadas.

## Paso a paso para ejecutar la app
1. Se descargan las imágenes de Docker en el siguiente repositorio: https://hub.docker.com/repositories/fmontoya988.
2. Al descargarlos, se ejecutan.
3. Se abre la terminal y se debe estar situado en la carpeta \cryptofund_workshop\client
4. Se instala Node.js
5. Al instalarlo, se ejecuta el siguiente comando en la carpeta asignada: "npm i vite".
6. Al terminar, el mismo npm posiblemente le pide una actualización, se instala (opcional).
7. Se ejecuta el siguiente comando: "npm i".
8. Por último, se ejecuta "npm run dev" y te brinda el URL para poder visualizar la app.

## Paso a paso para ejecutar los tests
1. Se despliega la carpeta server.
2. Dar clic derecho a cualquiera de los módulos desplegados.
3. Dar clic en "Run all tests".
4. Instalar lo que te pidan (opcional).
5. Visualizar los resultados.
