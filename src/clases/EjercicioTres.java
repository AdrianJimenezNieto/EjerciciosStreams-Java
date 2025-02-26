package clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// He reutilizado el código del ejercicio anterior, simplemente hay que cambiar las rutas
// y el contenido de LINEAS_ARCHIVO para que escriba el programa correspondiente

public class EjercicioTres {
  private static final String RUTA_ARCHIVO = "C:/EjerciciosStreams/dos.java";
  private static final File ARCHIVO = new File(RUTA_ARCHIVO);
  private static final String[] LINEAS_ARCHIVO = {
    "import java.util.Scanner;" ,
    "public class dos {" ,
    "  public static double calcularArea(double b, double h) {" ,
    "    return b * h / 2;" ,
    "  }" ,
    "  public static void main(String[] args) {" ,
    "    Scanner s = new Scanner(System.in);" ,
    "    System.out.println(\"Introduzca la altura del triángulo (en centímetros):\");" ,
    "    double h = s.nextDouble();" ,
    "    System.out.println(\"Introduzca la base del triángulo (en centímetros):\");" ,
    "    double b = s.nextDouble();" ,
    "    System.out.println(\"El área del triángulo de base \" + b + \" cm y altura \" + h + \" cm es de \" + calcularArea(b, h) + \" cm2.\");" ,
    "    s.close();" ,
    "  }" ,
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
