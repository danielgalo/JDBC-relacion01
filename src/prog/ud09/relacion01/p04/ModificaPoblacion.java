package prog.ud09.relacion01.p04;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class ModificaPoblacion {
  
  //Localizacion de la base de datos
  private static final String URL_BD = "jdbc:sqlite:db/poblaciones.db";
  
  /**
   * Método principal
   * @param args
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    //Pido datos
    System.out.println("Introduce los datos para modificar.");
    System.out.print("Código del pueblo: ");
    int codPueblo = Integer.parseInt(sc.nextLine());
    System.out.println("'v' -> Modificar número de vehículos \n'l' -> Modificar número de líneas telefónicas.");
    System.out.print("Introduce opción: ");
    String opcionModificar = sc.nextLine();
    System.out.print("Introduce el valor del nuevo campo: ");
    int campoModificado = Integer.parseInt(sc.nextLine());
    //Ejecuto metodo con los datos pedidos
    updatePueblos(codPueblo, opcionModificar, campoModificado);
    sc.close();
  }

  /**
   * Actualiza los datos de la tabla pueblos según los campos introducidos como parámetros
   * @param codPueblo
   * @param opcionModificar
   * @param campoModificado
   */
  public static void updatePueblos(int codPueblo, String opcionModificar, int campoModificado) {
    Connection conexion = null;
    try {
      //Conexion a la base de datos
      conexion = DriverManager.getConnection(URL_BD);
      //Modificacion (valdrá vehiculos o lineas_tel)
      String modificacion = "";
      //Opción a modificar
      if (opcionModificar.equalsIgnoreCase("v")) {
        modificacion = "vehiculos";
      } else if (opcionModificar.equalsIgnoreCase("l")) {
        modificacion = "lineas_tel";
      }
      //Sentencia
      String sqlUpdate = "UPDATE pueblos SET " + modificacion + " = ? WHERE codigo = ? ";  
      PreparedStatement sentencia = conexion.prepareStatement(sqlUpdate);
      sentencia.setInt(1, campoModificado);
      sentencia.setInt(2, codPueblo);
      //Ejecuta sentencia
      sentencia.executeUpdate();
      System.out.println("Modificación realizada con éxito.");
    } catch (SQLException e) {
      System.err.println("Ocurrió un error modificando la población " + e);
    } finally {
      try {
        //Cerrar conexión siempre
        conexion.close();
      } catch (SQLException e) {
        System.err.println("Ocurrió un error modificando la población " + e);
      }
    }
  }

}
