package clases;

import java.util.ArrayList; 
import java.util.List;
import java.util.Arrays;
// Importo la clase ArrayList y List porque me parece interesante para el ejercicio

import java.io.File; // File para comprobar la integridad del archivo
import java.io.FileReader; // FileReader para poder leer archivos
import java.io.BufferedReader; // BufferedReader para leer un archivo linea por linea
import java.io.IOException; // excepciones de la clase io

public class EjercicioCuatro {
  private static final String RUTA_ARCHIVO = "C:/EjerciciosStreams/datos.txt";
  private static final File ARCHIVO = new File(RUTA_ARCHIVO);

  private static boolean comprobarArchivo() {
    // Devolvemos si el archivo existe
    return ARCHIVO.exists();
  }

  private static List<String> lineaToArray(String linea) {
    // Inicializamos el arraylist resultante a vacio
    List <String> resultado = new ArrayList<>();
    // Primero separamos el String en un array de String con el caracter ':'
    String[] partes = linea.split(":");
    String[] palabras = partes[1].split(" ");
    String[] nombre = Arrays.copyOfRange(palabras, 0, palabras.length - 1);

    // Almacenamos el nombre en la primera posición
    resultado.add(String.join(" ", nombre)); // Lo unimos mediante el metodo .join()
    // Almacenamos el número de telefono también
    resultado.add(partes[partes.length - 1]);

    return resultado;
  }

  private static List<List<String>> procesarArchivo(String rutaArchivo) {
    // Inicializo el ArrayList que será una lista de listas de Strings
    List<List<String>> resultado = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
      // Inicializamos el reader en un bloque try-catch para pillar la excepcion
      String linea; // Variable auxiliar
      while ((linea = reader.readLine()) != null) { // Leemos linea por linea
        // Llamamos al metodo que pasa de String a List<String> y lo añadimos al resultado
        resultado.add(lineaToArray(linea)); 
      }
    } catch (IOException e) {
      System.out.println("Error al leer el archivo \"" + RUTA_ARCHIVO + "\": " + e.getMessage());
    }
    
    return resultado;
  }

  private static void imprimirResultados(List<List<String>> resultados) {
    System.out.println("En el fichero \"" + RUTA_ARCHIVO + "\" hay un total de " + resultados.size() + " contactos.");
    System.out.println();

    for (List<String> elemento : resultados) {
      System.err.println("Nombre:\t\t" + elemento.get(0));
      System.err.println("Teléfono:\t" + elemento.get(1));
      System.out.println();
    }
  }

  public static void main(String[] args) {
    // Declaramos la variable resultados y le asignamos lo que devuelve procesarArchivo()
    List<List<String>> resultados;
    if (comprobarArchivo()) {
      // Comprobamos la integridad del archivo
      resultados = procesarArchivo(RUTA_ARCHIVO);
      // Asignamos el resultado de procesarArchivo() a la variable resultados
      imprimirResultados(resultados);
      // Aquí podríamos hacer simplemente imprimirResultados(procesarArchivo(RUTA_ARCHIVO))
      // pero he preferido hacerlo de esta manera para que quede más legible
    } else {
      System.out.println("El archivo \"" + RUTA_ARCHIVO + "\".");
    }
  }
}