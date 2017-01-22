# README #

* Se incluye un archivo dataset en la raíz del proyecto para pruebas (son aprox 67 números desordenados).

### Requisitos ###

* NodeJS instalado
* Python instalado
* Para hacer funcionar el server de Python, instalar:
pip install requests
* Compilador de java, librerias JDK o OpenJDK

### Clonar repositorio ###

* Abrir la terminal
* Copiar en la terminal: git clone git@bitbucket.org:gmartinez/lab2-sd.git
* Situarse en la carpeta donde se haya guardado el repostorio.
* Escribir: sudo npm install para instalar todo lo que falte.

### Hacer correr el laboratorio ###

* Escribir en la terminal: nodejs server.js nombreArchivo nombreMetodo numeroServidores.
* Con lo de arriba se hará correr el servidor del organizador.
* Ejemplo: nodejs server.js dataset bubblesort 5.
* Mantener la ventana de la terminal abierta.

* nombreArchivo es: dataset, o otro archivo que contenga la información necesaria.
* nombreMetodo puede ser: bubblesort, quicksort, mergesort
* numeroServidores, es la cantidad de servidores que hay implementados.

### Hacer correr los distintos servers ###

* Con la terminal abierta de nodejs de la sección anterior, abrir otra terminal.
* Con esta nueva terminal, dirigirse a la carpeta de los servers, está será /servers

### Conectar server Java ###

* En la carpeta servers/java, ejecutar en la terminal: javac Server.java para compilar el código
* Luego java Server para hacer correr el server de java
* Este server de java recibirá la parte 0 del array divido en n servers. (parte 1 del arreglo)

### Conectar server Python ###

* Para hacer funcionar el server de Python, se necesita la libreria Request, 
para instalarla, escribir en la terminal:
pip install requests

https://github.com/kennethreitz/requests/

* En la carpeta servers/python, ejecutar en la terminal: python server.py para ejecutar el programa.
* Este server de python recibirá la parte 1 del array divido en n servers. (parte 2 del arreglo)

### Conectar server Ruby ###

* Para hacer funcionar el server de Ruby, tiene que ir a la carpeta servers/ruby y escribir en la terminal: ruby server.rb
* Este server de python recibirá la parte 2 del array divido en n servers.
* Versión de Ruby probada: 2.2.1
* Servidor Ruby recibirá la parte 2 del arreglo (parte 3 del arreglo)


### Conectar server PHP ###

* Para hacer funcionar el server en PHP desde la terminal, se necesita instalar los siguientes paquetes:

sudo apt-get install php5-curl
sudo apt-get install php5-cli

* Para hacer correr el servidor de PHP, ir a la carpeta servers/PHP y escribir en la terminal: php server.php

* El server php recibirá la parte 3 del arreglo (parte 4)

### Conectar server C# ###

* Se necesita el IDE Mono para hacer abrir el proyecto y hacerlo correr.
* El server C# recibirá la parte 4 del arreglo (parte 5)
