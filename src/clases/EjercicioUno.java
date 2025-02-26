package clases;
import java.io.File; // Importamos la clase File para el manejo de archivos, directorios,...
import java.io.IOException; // Importamos las excepciones de la librería io

public class EjercicioUno {
  private static final String NOMBRE_CARPETA = "EjerciciosStreams";
  private static final String RUTA_DIRECTORIO = "C:/EjerciciosStreams";
  private static final String[] NOMBRES_ARCHIVOS = {"uno", "dos"};
  private static File directorio = new File(RUTA_DIRECTORIO);

  private static boolean existeDirectorio() {
    // Metodo que devuelve si existe el directorio EjerciciosStreams
    // Usaremos los metodos .exists() y .isDirectory() de la clase File
    return directorio.exists() && directorio.isDirectory();
  }

  private static void crearCarpeta() {
    if(directorio.mkdir()) {
      System.out.println("Carpeta creada correctamente.");
    } else {
      System.out.println("Error al crear la carpeta.");
    }
  }

  private static void crearArchivo(String nombreArchivo) {
    File archivo = new File(RUTA_DIRECTORIO + "/" + nombreArchivo + ".java");

    // Encerramos la operación de crear un archivo en un bloque try/catch para manejar las posibles excepciones
    try {
      if(archivo.createNewFile()) {
        // Al llamar al metodo File.createNewFile() este nos devuelve true si el
        // archivo se crea correctamente por eso podemos hacer esta sentencia if/else
        // Si el archivo es creado correctamente imprimimos por pantalla
        System.out.println("El archivo '" + archivo.getName() + "' se se ha creado correctamente.");
      } else {
        System.out.println("El archivo '" + archivo.getName() + "' ya existe.");
      }
    } catch (IOException e) {
      System.out.println("Error al crear el archivo " + archivo.getName() + ": " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    // Comprobamos si la carpeta existe en el directorio C:/
    if(!existeDirectorio()) {
      crearCarpeta();
    } else {
      System.out.println("La carpeta " + NOMBRE_CARPETA + " ya existe.");
    }

    // Creación de los archivos "uno.java" y "dos.java"
    for (int i = 0; i < NOMBRES_ARCHIVOS.length; i++) {
      // Bucle que recorre el array de Strings NOMBRES_ARCHIVOS para crear tantos
      // archivos como nombres hayan en el array.
      crearArchivo(NOMBRES_ARCHIVOS[i]);
    }
  }
}
