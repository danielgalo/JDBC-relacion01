package prog.ud09.relacion01.p02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import prog.ud09.relacion01.p01.Poblacion;

public class ConsultaPoblacion {

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
    
    System.out.println("Introduce los parámetros de búsqueda.");
    System.out.print("Nombre: ");
    String nombre = sc.nextLine();
    System.out.print("Población mínima: ");
    int pobMin = Integer.parseInt(sc.nextLine());
    System.out.print("Población máxima: ");
    int pobMax = Integer.parseInt(sc.nextLine());
    
    List<Poblacion> lista = consultaPueblos(nombre, pobMin, pobMax); 
    imprimePoblaciones(lista);
    
    sc.close();
  }

  public static List<Poblacion> consultaPueblos(String nombre, int pobMin, int pobMax) {    
    Connection conexion = null;
    List<Poblacion> salida=  new ArrayList<>();
    try {
      //Conexion a la base de datos
      conexion = DriverManager.getConnection(URL_BD);
      //Obtener sentencia
      PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM pueblos "
          + "WHERE nombre LIKE ? AND pob_total BETWEEN ? AND ?;");
      sentencia.setString(1, "%" + nombre + "%");
      sentencia.setInt(2, pobMin);
      sentencia.setInt(3, pobMax);
      //Ejecutar sentencia
      ResultSet resultado = sentencia.executeQuery();
      while (resultado.next()) {
        Poblacion poblacion = new Poblacion(resultado.getString(C_CODIGO),
          resultado.getString(C_NOMBRE), resultado.getDouble(C_EXTENSION),
          resultado.getInt(C_POB_HOMBRES), resultado.getInt(C_POB_MUJERES),
          resultado.getInt(C_VEHICULOS), resultado.getInt(C_LINEAS_TEL));
        salida.add(poblacion);
      }
      System.out.println("SELECT realizado correctamente.");
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
