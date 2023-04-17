package prog.ud09.relacion01.p01;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AddPoblacion {

  //Localizacion de la base de datos
  private static final String URL_BD = "jdbc:sqlite:db/poblaciones.db";
  //Parte de la sentencia sql insert de la tabla pueblos
  private static final String CAMPOS_PUEBLOS = "(codigo, nombre, extension, pob_total, pob_hombres, pob_mujeres, vehiculos, lineas_tel) ";

  /**
   * Método principal
   * @param args
   */
  public static void main(String[] args) {
    //Creo objeto poblacion, le asigno al metodo de pedir datos
    Poblacion poblacion = leePoblacion();
    //Guardar la poblacion en base de datos
    guardaPoblacionEnBD(poblacion);
  }

  /**
   * Guarda la población que se le pasa en una base de datos
   * @param objeto poblacion
   */
  private static void guardaPoblacionEnBD(Poblacion poblacion) {
    Connection conexion = null;
    try {
      //Conexion a la base de datos
      conexion = DriverManager.getConnection(URL_BD);
      //Obtener sentencia
      Statement sentencia = conexion.createStatement();
      //Crear sentencia
      String sqlInsert = "INSERT INTO pueblos " + CAMPOS_PUEBLOS
          + "VALUES ('" 
          + poblacion.getCodigo() 
          + "', '" 
          + poblacion.getNombre() 
          + "', "
          + poblacion.getExtension() 
          + ", "
          + poblacion.getPoblacionTotal() 
          + ", " 
          + poblacion.getPobHombres() 
          + ", " 
          + poblacion.getPobMujeres() 
          + ", "
          + poblacion.getVehiculos() 
          + ", " 
          + poblacion.getVehiculos() + ")";
      //Ejecutar sentencia
      sentencia.executeUpdate(sqlInsert);
      System.out.println("Inserción realizada correctamente.");
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
  }
  
  /**
   * Metodo para pedir datos de la población al usuario
   * @return objeto poblacion con datos pedidos
   */
  private static Poblacion leePoblacion() {
    Scanner sc = new Scanner(System.in);
    Poblacion pob = new Poblacion();
 
    System.out.println("Nueva ciudad: ");
    System.out.print("Introduce el Código: ");
    pob.setCodigo(sc.nextLine());
    
    System.out.print("Introduce el Nombre: ");
    pob.setNombre(sc.nextLine());
    
    System.out.print("Introduce la Extensión: ");
    pob.setExtension(Double.parseDouble(sc.nextLine()));
    
    System.out.print("Introduce la Población de Hombres: ");
    pob.setPobHombres(Integer.parseInt(sc.nextLine()));
    
    System.out.print("Introduce la Población de Mujeres: ");
    pob.setPobMujeres(Integer.parseInt(sc.nextLine()));
    
    System.out.print("Introduce el número de Vehículos: ");
    pob.setPobHombres(Integer.parseInt(sc.nextLine()));
    
    System.out.print("Introduce el número de Líneas telefónicas: ");
    pob.setPobHombres(Integer.parseInt(sc.nextLine()));
    sc.close();
    return pob;
  }
  
}
