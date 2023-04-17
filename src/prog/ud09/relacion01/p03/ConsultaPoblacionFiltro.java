package prog.ud09.relacion01.p03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import prog.ud09.relacion01.p01.Poblacion;

public class ConsultaPoblacionFiltro {

  //Localizacion de la base de datos
  private static final String URL_BD = "jdbc:sqlite:db/poblaciones.db";
  //Campos de la tabla Poblaciones
  private static final String C_NOMBRE = "nombre";
  private static final String C_POB_HOMBRES = "pob_hombres";
  private static final String C_VEHICULOS = "vehiculos";
  private static final String C_LINEAS_TEL = "lineas_tel";
  private static final String C_POB_MUJERES = "pob_mujeres";
  private static final String C_EXTENSION = "extension";
  private static final String C_CODIGO = "codigo";
  
  /**
   * Método principal
   * @param args
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    //Pedir datos
    System.out.println("Introduce los parámetros de búsqueda.");
    System.out.print("Nombre: ");
    String nombre = sc.nextLine();
    System.out.print("Población mínima: ");
    int pobMin = Integer.parseInt(sc.nextLine());
    System.out.print("Población máxima: ");
    int pobMax = Integer.parseInt(sc.nextLine());
    //Opción de ordenación
    System.out.println("Ordenar por... \n 0.- Nada \n 1.- Nombre ascendente "
        + "\n 2.- Nombre desdendente");
    System.out.print("Seleccione opción: ");
    int opcion = Integer.parseInt(sc.nextLine());
    sc.close();
    
    //Crear lista a imprimir, asignarle el método consultaPueblos que devuelve una lista
    List<Poblacion> lista = consultaPueblos(nombre, pobMin, pobMax, opcion); 
    //Imprimir lista
    imprimePoblaciones(lista);
  }

  public static List<Poblacion> consultaPueblos(String nombre, int pobMin, int pobMax, int opcion) {    
    Connection conexion = null;
    List<Poblacion> salida = new ArrayList<>();
    try {
      //Conexion a la base de datos
      conexion = DriverManager.getConnection(URL_BD);
      //Sentencia sql
      String sqlSelect = ("SELECT * FROM pueblos "
          + "WHERE nombre LIKE ? AND pob_total BETWEEN ? AND ?");
      //Opción de ordenación elegida
      if (opcion == 1) {
        //Ascendente
        sqlSelect = sqlSelect + "ORDER BY " + C_NOMBRE + " ASC";
      } else if (opcion == 2){
        //Descendente
        sqlSelect = sqlSelect + "ORDER BY " + C_NOMBRE + " DESC";
      }
      //Preparar sentencia
      PreparedStatement sentencia = conexion.prepareStatement(sqlSelect);
      sentencia.setString(1, "%" + nombre + "%");
      sentencia.setInt(2, pobMin);
      sentencia.setInt(3, pobMax);
      //Ejecutar sentencia
      ResultSet resultado = sentencia.executeQuery();
      //Recorrer tabla, añadirla a la lista
      while (resultado.next()) {
        Poblacion poblacion = new Poblacion(resultado.getString(C_CODIGO),
          resultado.getString(C_NOMBRE), resultado.getDouble(C_EXTENSION),
          resultado.getInt(C_POB_HOMBRES), resultado.getInt(C_POB_MUJERES),
          resultado.getInt(C_VEHICULOS), resultado.getInt(C_LINEAS_TEL));
        salida.add(poblacion);
      }
      System.out.println("SELECT realizado correctamente.");
      //Cerrar resultado
      resultado.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        //Cerrar conexión siempre
        conexion.close();
      } catch (SQLException e) {
        System.err.println("Ocurrió un error añadiendo la población " + e);
      }
    }
    //Devuelvo la lista
    return salida;
  }
  
  private static void imprimePoblaciones(List<Poblacion> poblaciones) {
    System.out.println("Poblaciones encontradas");
    System.out.println("CODIGO           NOMBRE             EXT   P_TOT  P_HOM  P_MUJ VEHIC LINEAS");
    System.out.println("--------------------------------------------------------------------------");
    for (Poblacion poblacion: poblaciones) {
      System.out.printf("%5s %-28s %5.1f %6d %6d %6d %5d %6d",
        poblacion.getCodigo(), poblacion.getNombre(), poblacion.getExtension(),
        poblacion.getPoblacionTotal(), poblacion.getPobHombres(), poblacion.getPobMujeres(),
        poblacion.getVehiculos(), poblacion.getLineasTelefonicas());
      System.out.println();
    }
  }

}
