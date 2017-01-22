import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.HttpsURLConnection;
 
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Server {
 
	private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception {
 
		double [] arrayOrdenadoEnviar; 
		Server http = new Server(); 
		System.out.println("[Server 0] [GET] Enviando peticion GET al organizador");
		arrayOrdenadoEnviar = http.sendGet();
    System.out.println("[Server 0] El arreglo ordenado es: \n");
		System.out.println(Arrays.toString(arrayOrdenadoEnviar));
 
		System.out.println("\n[Server 0] [POST] Enviando peticion POST al organizador");
		http.sendPost(arrayOrdenadoEnviar);
 
	}

	// HTTP GET request
	private double[] sendGet() throws Exception {
 
		String url  = "http://localhost:8080/part/";
		String part = "0"; //Server java tomara la parte 0
		url         = url.concat(part);

 		// HTTP Get codigo obtenido de: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		// Se recibe del organizador, un tipo de dato StringBuffer
		// Convirtiendo este tipo de dato en objeto JSON a traves de la libreria JSON.simple
		JSONObject json = (JSONObject)new JSONParser().parse(response.toString());
    System.out.println("[Server 0] [GET] Se ha recibido mensaje del organizador");
      System.out.println("[Server 0] [GET] Recibi el nombre del metodo y la parte 0 del array");
		
		// Extraer el nombre del metodo a ocupar y pasarlo a String
		String metodo   = json.get("method").toString();
		//System.out.println(metodo);

		// Extraer la lista(array) a ocupar y dejarlo como array
		String listaString  = json.get("arraySend").toString();
		//System.out.println(listaString);
		
		// JSON deforma el formato, hay que arreglarlo, para dejarlo como una lista de numeros
    listaString = listaString.replace("[", "");
    listaString = listaString.replace("]", "");
    listaString = listaString.replace("\"", "");

    // Guardar array en un array temporal
		String [] arrayTemp        =  listaString.split(",");
		//System.out.println(Arrays.toString(arrayTemp));

		// El archivo tiene numeros decimales grandes
		double [] arrayDesordenado = new double[arrayTemp.length];

		// Pasando el array a Double para guardar toda la cantidad de datos requerida
		for (int i = 0 ; i< arrayTemp.length; i++) {
            arrayDesordenado[i] = Double.parseDouble(arrayTemp[i]);
        }

    //System.out.println(Arrays.toString(arrayDesordenado));
    double [] arrayOrdenado = new double[arrayDesordenado.length];
        
    // Seleccion del metodo
    // Metodos ordenan de menor a mayor, de izq a der
 		if(metodo.equals("bubblesort") == true) {
 			System.out.println("[Server 0]  Se solicito el metodo bubblesort \n");
 			 arrayOrdenado = bubbleSort(arrayDesordenado);
 			 //System.out.println(Arrays.toString(arrayOrdenado));
 			 System.out.println("[Server 0] Se termino de realizar el ordenamiento mediante bubblesort");

 		} else  if (metodo.equals("quicksort") == true) {
 			System.out.println("[Server 0]  Solicito el metodo quicksort \n");
 			// En la primera llamada recibe izq = 0, der = elementos-1
 			// Referencia: http://puntocomnoesunlenguaje.blogspot.com/2012/12/java-quicksort.html
 			arrayOrdenado = quickSort(arrayDesordenado,0,arrayDesordenado.length -1);
 			System.out.println("[Server 0] Se termino de realizar el ordenamiento mediante quicksort"); 

 		} else if (metodo.equals("mergesort") == true ) {
 			System.out.println("[Server 0] Solicito el metodo mergesort \n");
 			arrayOrdenado = mergeSort(arrayDesordenado);
 			System.out.println("[Server 0] Se termino de realizar el ordenamiento mediante mergesort");
 		}

 		return arrayOrdenado;
	}
 
	// HTTP POST request
	// Referencias: https://stackoverflow.com/questions/20740349/http-post-request-with-json-string-in-java
	private void sendPost(double [] arregloOrdenado) throws Exception {
 
		String url = "http://localhost:8080/orderpart/0";
		
		// HTTP Get codigo obtenido de: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

      	URL obj=new URL(url);
      	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      	con.setDoOutput(true);
      	con.setDoInput(true);
      	con.setRequestProperty("Content-Type", "application/json");
      	con.setRequestProperty("Accept", "application/json");
      	con.setRequestMethod("POST");

      	JSONArray jsArray = new JSONArray();
      	for ( int i =0; i < arregloOrdenado.length ; i++ ) {
        	jsArray.add(arregloOrdenado[i]);
      	}

      	OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());

        System.out.println("[Server 0] [POST] Enviando al organizador array ordenado");
        System.out.println("[Server 0] [POST] Haciendo POST");
      	wr.write(jsArray.toString());
      	//wr.write("hola mundo");
      	wr.flush();

      	//display what returns the POST request
      	StringBuilder sb = new StringBuilder();  
      	int HttpResult =con.getResponseCode(); 
      	if(HttpResult ==HttpURLConnection.HTTP_OK){
          BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));  
          String line = null;  
          
          while ((line = br.readLine()) != null) {  
          sb.append(line + "\n");  
          }  

          br.close();  

          System.out.println(""+sb.toString());  

      	} else{
          System.out.println(con.getResponseMessage());  
      	}  
	}

	// Metodo BubbleSort
	// Referencia: http://www.algolist.net/Algorithms/Sorting/Bubble_sort

	public static double[] bubbleSort(double[] arr) {

      boolean swapped = true;
      int j = 0;
      double tmp;

      while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {                                       
                  if (arr[i] > arr[i + 1]) {                          
                        tmp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = tmp;
                        swapped = true;
                  }
            }                
      }
      return arr;
	}

	// Metodo QuickSort
	// Referencia: http://www.algolist.net/Algorithms/Sorting/Quicksort
	public static int partition(double[] arr, int left, int right) {

      int i = left, j = right;
      double tmp;
      double pivot = arr[(left + right) / 2];

      while (i <= j) {
            while (arr[i] < pivot)
                  i++;
            while (arr[j] > pivot)
                  j--;
            if (i <= j) {
                  tmp = arr[i];
                  arr[i] = arr[j];
                  arr[j] = tmp;
                  i++;
                  j--;
            }

      };
      return i;
	}

	public static double[] quickSort(double[] arr, int left, int right) {

      	int index = partition(arr, left, right);
      	if (left < index - 1)
            quickSort(arr, left, index - 1);
      	if (index < right)
            quickSort(arr, index, right);
        return arr;
	}

	// Metodo MergeSort
	// Referencia: http://www.java2novice.com/java-sorting-algorithms/merge-sort/
    public static double[] mergeSort(double[] inputArr) {
    	double[] array; 
        double[] tempMergArr;
        int length;
		array  = inputArr;
		length = inputArr.length;
        tempMergArr = new double[length];
        doMergeSort(inputArr, tempMergArr, 0, length - 1);
        return array;
    }

    private static void doMergeSort(double[] inputArr, double[] tempMergArr, int lowerIndex, int higherIndex) {
         
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(inputArr, tempMergArr,lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(inputArr, tempMergArr,middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(inputArr, tempMergArr, lowerIndex, middle, higherIndex);
        }
    }
 
    // Tira error
    private static void mergeParts(double[] inputArr, double[] tempMergArr, int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = inputArr[i];
        }
        
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                inputArr[k] = tempMergArr[i];
                i++;
            } else {
                inputArr[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        
        while (i <= middle) {
            inputArr[k] = tempMergArr[i];
            k++;
            i++;
        }
 
    }

}
