package clases;

import java.io.File; // clase para abrir el archivo
import java.io.FileWriter;
import java.io.BufferedWriter; // clase para escribir en el archivo
import java.io.IOException; // clase que contiene las excepciones de io

public class EjercicioDos {
  private static final String RUTA_ARCHIVO = "C:/EjerciciosStreams/uno.java";
  private static final File ARCHIVO = new File(RUTA_ARCHIVO);
  private static final String[] LINEAS_ARCHIVO = {
    "public class uno {",
    "  public static void main(String[] args) {",
    "    for (int i = 0; i <= 10; ++i) {",
    "      if(i==10) {",
    "        System.out.print(i);",
    "      } else {",
    "        System.out.print(i + \", \");",
    "      }",
    "    } ", 
    "  }",
    "}"
  };
  
  private static boolean comprobarArchivo () {
    // Metodo de clase para verificar que el archivo existe y es un directorio
    return ARCHIVO.exists();
  }

  private static void escribirLinea(BufferedWriter escritor, String linea) throws IOException{
    // Al definir el método le especificamos que puede devolver una excepcion de tipo IO
    escritor.write(linea); // Escribimos la linea
    escritor.newLine(); // Introducimos un salto de línea
  }

  private static void escribirArchivo() {
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
      // Inicializo BufferedWriter para poder hacer append de las lineas
      for(int i = 0; i < LINEAS_ARCHIVO.length; i++){
        escribirLinea(escritor, LINEAS_ARCHIVO[i]);
      }
      System.out.println("Archivo escrito correctamente.");
    } catch (IOException e) {
      // Imprimimos la excepion en caso de que ocurra
      System.out.println("Error al escribir sobre el archivo: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    if (comprobarArchivo()) {
      // Si el archivo existe procederemos a escribir sobre él
      escribirArchivo();
    } else {
      System.out.println("Error, el fichero '" + RUTA_ARCHIVO + "' no existe." );
    }
  }
}
