package clases;

import java.util.ArrayList; 
import java.util.List;
// Importo la clase ArrayList y List porque me parece interesante para el ejercicio

import java.io.File; // File para comprobar la integridad del archivo
import java.io.FileReader; // FileReader para poder leer archivos
import java.io.BufferedReader; // BufferedReader para leer un archivo linea por linea
import java.io.IOException; // excepciones de la clase io
import java.io.RandomAccessFile; // Clase para manejar archivos de acceso aleatorio

// REUTILIZO PRACTICAMENTE TODO EL CODIGO DE LA CLASE DEL EJERCICIO ANTERIOR

public class EjercicioCinco {
  private static final String RUTA_ARCHIVO = "C:/EjerciciosStreams/datos.txt";
  // Constantes que fijen un tamaño tanto a nombre como a telefono
  private static final int NAME_SIZE = 30;
  // Generalizo para números de 9 digitos sin prefijos internacionales
  private static final int NUMTEL_SIZE = 9;
  // Finalmente guardamos en una constante la ruta del archivo a crear
  private static final String RUTA_ARCHIVO_NUEVO = "C:/EjerciciosStreams/datosAleatorio.bin";
  // Definimos tambien para leer el archivo el tamaño del registro que será Nombre + Telefono + 4 bits internos UTF
  private static final int TAMANO_REGISTRO = NAME_SIZE + NUMTEL_SIZE + 4;

  private static boolean comprobarArchivo(File archivo) {
    // Devolvemos si el archivo existe
    return archivo.exists();
  }

  private static List<String> lineaToArray(String linea) {
    // Inicializamos el arraylist resultante a vacio
    List <String> resultado = new ArrayList<>();
    // Primero separamos el String en un array de String con el metodo split mediante la cadena "Telefono:"
    String[] partes = linea.split("Teléfono:");

    // Almacenamos el nombre en la primera posición
    resultado.add(partes[0].replace("Nombre:", "")); // Almacenamos el nombre eliminadno la parte primera
    // Almacenamos el número de telefono también, mediante trim() para eliminar espacios en blanco
    resultado.add(partes[1].trim());

    return resultado;
  }

  private static List<List<String>> procesarArchivo(String rutaArchivo) {
    // Inicializo el ArrayList que será una lista de listas de Strings
    List<List<String>> resultado = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
      // Inicializamos el reader en un bloque try-catch para pillar la excepcion
      String linea; // Variable auxiliar
      while ((linea = reader.readLine()) != null) { // Leemos linea por linea
        // Llamamos al metodo que pasa de String a List<String> y lo añadimos al resultado
        resultado.add(lineaToArray(linea)); 
      }
    } catch (IOException e) {
      System.out.println("Error al leer el archivo \"" + rutaArchivo + "\": " + e.getMessage());
    }
    
    return resultado;
  }

  private static String ajustarTamano(String dato, int tamano) {
    // Método para ajustar el mismo tamaño a todos los nombres y numeros de telefono
    if (dato.length() > tamano) {
      // si el tamaño es mayor truncamos la cadena 
      return dato.substring(0, tamano).trim();
    } else {
      // si el tamaño es menor o igual añadimos espacios para rellenar
      return String.format("%-" + tamano + "s", dato); // %-Xs siendo X la longitud mínima luego rellena con espacios a la derecha
    }
  }

  private static void escribirArchivoAleatorio(String rutaArchivo, List<List<String>> datos) {
    try (RandomAccessFile archivoAleatorio = new RandomAccessFile(rutaArchivo, "rw")) {
      String nombre;
      String telefono;
      for (List<String> elemento: datos) { // Recorremos la matriz de datos
        // Definimos las variables controlando su longitud
        nombre = ajustarTamano(elemento.get(0), NAME_SIZE);
        telefono = ajustarTamano(elemento.get(1), NUMTEL_SIZE);
        // Escribimos el en el archivo .bin
        archivoAleatorio.writeUTF(nombre);
        archivoAleatorio.writeUTF(telefono);
      }
      System.out.println("Archivo creado correctamente.");
    } catch (IOException e) {
      System.out.println("Error al crear el archivo \"" + rutaArchivo + "\".");
    }
  }

  private static void leerArchivoAleatorio(String rutaArchivo, int tamanoResgistro) {
    try(RandomAccessFile archivoAleatorio = new RandomAccessFile(rutaArchivo, "r")) {
      // Abrimos el archivo en modo lectura "r"
      long numContactos = archivoAleatorio.length() / tamanoResgistro; // calculamos el total de registros
      System.out.println("El archivo \"" + rutaArchivo + "\" tiene " + numContactos + " contactos.");

      for (int i = 0; i < numContactos; i++){
        // iteramos sobre el total de contactos
        // archivoAleatorio.seek(i * tamanoResgistro); // ponemos el puntero al inicio de cada registro

        // LA LINEA DE ARRIBA DA PROBLEMAS, NO LO ENTIENDO PORQUE FIJO EL TAMAÑO 
        // DE CADA REGISTRO CONTANDO CON LOS 2 BYTES EXTRA QUE INTRODUCE UTF8 
        // CADA VEZ QUE LLAMO A .writeUTF() NO ENTIENDO POR QUÉ SALTA EXCEPCIÓN DE
        // FINAL DE ARCHIVO CUANDO LEE EL SEGUNDO REGISTRO DEL .bin

        String nombre = archivoAleatorio.readUTF().trim();
        String telefono = archivoAleatorio.readUTF().trim();
        System.out.println("Nombre:\t\t" + nombre);
        System.out.println("Teléfono:\t" + telefono);
      } 
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error en la lectura del archivo \"" + "\": " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    // Comprobamos si existe el archivo
    File archivoALeer = new File(RUTA_ARCHIVO);
    if (comprobarArchivo(archivoALeer)) {
      List<List<String>> datos = procesarArchivo(RUTA_ARCHIVO); // Leemos el .txt

      // escribimos el "datosAleatorios.bin"
      escribirArchivoAleatorio(RUTA_ARCHIVO_NUEVO, datos);

      // Una vez ya se ha creado el archivo podemos leerlo sin problema comprobando si el archivo existe
      File archivoAleatorio = new File(RUTA_ARCHIVO_NUEVO);
      if (comprobarArchivo(archivoAleatorio)) {
        System.out.println("#######################################################");
        leerArchivoAleatorio(RUTA_ARCHIVO_NUEVO, TAMANO_REGISTRO);
      } else {
        System.out.println("El archivo \"" + RUTA_ARCHIVO_NUEVO + "\" no existe.");
      }

    } else {
      System.out.println("El archivo \"" + RUTA_ARCHIVO + "\" no existe.");
    }
  }
}