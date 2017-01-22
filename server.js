var express        = require("express");
var app            = express();
var bodyParser     = require("body-parser");
var methodOverride = require("method-override");
var mongoose       = require('mongoose');

var http  = require('http');
var server= http.createServer(app);
var router= express.Router();
var port  = 8080;
server.listen(port);

// Routing -------------------------------------------------------------------------

app.use("/", express.static(__dirname + '/'));
app.use(router);

// Para hacer POST ----------------------------------------------------------------

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(methodOverride());

console.log('[Organizador] Organizador disponible en el puerto: ' + port);
// Main ---------------------------------------------------------------------------

// Pasar comandos por la terminal: nodejs server.js fichero tipoOrdenamiento numeroServidores ---------------------
var fileName      = process.argv[2];
var orderMethod   = process.argv[3];
var n             = process.argv[4];
var arrayOrdenado = [];

console.log("[Organizador] Archivo: " + fileName);
console.log("[Organizador] Metodo de ordenamiento: " + orderMethod);
console.log("[Organizador] Numero de servidores: " + n);
console.log("-------------------------------------------------------- \n");


// Abro el archivo y su contenido queda guardado en data
fs       = require('fs');
var data = fs.readFileSync(fileName,'utf8')

// Convierto contenidos del fichero en un array grande
var array = data.toString().split("\n"); 
var len   = array.length,out = [], i = 0;


// Divido el array grande en n arrays
// n arrays quedan guardados en el array out
// Referencia: https://stackoverflow.com/questions/8188548/splitting-a-js-array-into-n-arrays
while (i < len) {
	var size = Math.ceil((len - i) / n--);
	out.push(array.slice(i, i += size));
}

//console.log(out);

// 0: Server java: Server.java
// 1: Server python: server.py
// 2: Server ruby: server.rb
// 3: Server PHP: server.php
// 4: Server C#: abrir en mono


app.get('/part/:number', function(req, res){
	console.log('[Organizador] [GET] El server ' + req.params.number + ' hizo GET');

	// Rescato el numero del server
  var number = req.params.number;

  // Como respuesta a la peticion le envio el nombre del metodo y la parte del array desordenado
  // Todo empaquetado en un mensaje JSON
	var response = {"method": orderMethod ,"arraySend":out[number]}; // Mensaje JSON
  console.log("[Organizador] [GET] Enviando nombre del metodo: " + orderMethod + " y parte: " + number + " del arreglo"); 
  // Enviar JSON al servidor que hizo GET
  res.json(response);
  //console.log(response);

});


app.post('/orderpart/:number', function(req, res, next){
  console.log('[Organizador] [POST] El server ' + req.params.number + ' hizo POST');
  
  // Rescato el numero del server
  var number = req.params.number;

  var arrayRecibido;
  //console.log(request.body);
  //console.log(JSON.stringify(req.body));

  // Recibir un array en JSON del servidor
  console.log("[Organizador] [POST] Se recibio desde el [Server " + number + "] " + "la parte: " + number + " ordenada del arreglo"); 
  arrayRecibido = JSON.stringify(req.body)
  
  // test
  console.log("[Organizador] [POST] Se recibio desde el server esta parte: \n")
  //console.log(arrayRecibido);

  // Arreglar el formato

  if (number == '2') {

     console.log(arrayRecibido);

      // Ruby lo toma como ['"valor"', '"valor2"', etc]

      arrayRecibido = arrayRecibido.replace("[", "");
      arrayRecibido = arrayRecibido.replace("]", "");
      arrayRecibido = arrayRecibido.replace(/"/g, "");
      arrayRecibido = arrayRecibido.split(",");

  }

  else {
      arrayRecibido = arrayRecibido.replace("[", "");
      arrayRecibido = arrayRecibido.replace("]", "");
      arrayRecibido = arrayRecibido.split(",");
  }
 
  console.log(arrayRecibido);

  // Concatenando los arrays que vayan llegando
  console.log("\nConcatenando lo recibido: \n");
  arrayOrdenado = arrayOrdenado.concat(arrayRecibido);

  for (var j = 0; j < arrayOrdenado.length; j++) {
      arrayOrdenado[j] = parseFloat(arrayOrdenado[j]);
    };
  console.log(arrayOrdenado);

  // Ordenando el arreglo concatenado
  console.log("\nOrdenando el array concatenado mediante merge sort: \n");
  arrayOrdenado = mergeSort(arrayOrdenado);
  console.log(arrayOrdenado);
});

// Funcion mergesort para ordenar toda la lista que se hizo merge
// Referencia: http://www.stoimen.com/blog/2010/07/02/friday-algorithms-javascript-merge-sort/

function mergeSort(arr)
{
    if (arr.length < 2)
        return arr;
 
    var middle = parseInt(arr.length / 2);
    var left   = arr.slice(0, middle);
    var right  = arr.slice(middle, arr.length);
 
    return merge(mergeSort(left), mergeSort(right));
}
 
function merge(left, right)
{
    var result = [];
 
    while (left.length && right.length) {
        if (left[0] <= right[0]) {
            result.push(left.shift());
        } else {
            result.push(right.shift());
        }
    }
 
    while (left.length)
        result.push(left.shift());
 
    while (right.length)
        result.push(right.shift());
 
    return result;
}
