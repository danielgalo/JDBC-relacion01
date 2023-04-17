package prog.ud09.relacion01.p05;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class EliminaPoblacion {
  
  //Localizacion de la base de datos
  private static final String URL_BD = "jdbc:sqlite:db/poblaciones.db";
  
  /**
   * Método principal
   * @param args
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    //Pido datos
    System.out.println("Eliminar pueblos.");
    System.out.println("'C' -> Eliminar por codigo \n'E' -> Eliminar por extensión."
        + "\n'P' -> Eliminar por población total");
    System.out.print("Introduce opción: ");
    String opcionModificar = sc.nextLine();
    System.out.print("Introduce el filtro a eliminar: ");
    String filtroEliminar = sc.nextLine();
    eliminaPueblo(opcionModificar, filtroEliminar);
    sc.close();
  }

  /**
   * Elimina pueblo por opción dada
   * @param opcionModificar
   */
  public static void eliminaPueblo(String opcionModificar, String filtroEliminar) {
    Connection conexion = null;
    Scanner sc = new Scanner(System.in);
    try {
      //Conexion a la base de datos
      conexion = DriverManager.getConnection(URL_BD);
      //Modificacion (valdrá vehiculos o lineas_tel)
      String modificacion = "";
      //Opción a modificar
      if (opcionModificar.equalsIgnoreCase("C")) {
        modificacion = "codigo";
      } else if (opcionModificar.equalsIgnoreCase("E")) {
        modificacion = "extension";
      } else if (opcionModificar.equalsIgnoreCase("P")) {
        modificacion = "pob_total";
      }
      //Sentencia
      String sqlUpdate = "DELETE FROM pueblos WHERE " + modificacion + " = ?";  
      PreparedStatement sentencia = conexion.prepareStatement(sqlUpdate);
      sentencia.setString(1, filtroEliminar);
      //Ejecuta sentenc
      sentencia.executeUpdate();
      sc.close();
      System.out.println("Eliminación realizada con éxito.");
    } catch (SQLException e) {
      System.err.println("Ocurrió un error eliminando la población " + e);
    } finally {
      try {
        //Cerrar conexión siempre
        conexion.close();
      } catch (SQLException e) {
        System.err.println("Ocurrió un error eliminando la población " + e);
      }
    }
  }

}
