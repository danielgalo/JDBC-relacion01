package prog.ud09.relacion01.p01;

public class Poblacion {

  private String codigo;
  private String nombre;
  private double extension;
  private int pobHombres;
  private int pobMujeres;
  private int vehiculos;
  private int lineasTelefonicas;
  
  public Poblacion() {
    
  }
  
  
  
  public Poblacion(String codigo, String nombre, double extension, int pobHombres, int pobMujeres,
      int vehiculos, int lineasTelefonicas) {
    super();
    this.codigo = codigo;
    this.nombre = nombre;
    this.extension = extension;
    this.pobHombres = pobHombres;
    this.pobMujeres = pobMujeres;
    this.vehiculos = vehiculos;
    this.lineasTelefonicas = lineasTelefonicas;
  }



  public String getCodigo() {
    return codigo;
  }
  
  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public double getExtension() {
    return extension;
  }
  
  public void setExtension(double extension) {
    this.extension = extension;
  }
  
  public int getPobHombres() {
    return pobHombres;
  }
  
  public void setPobHombres(int pobHombres) {
    this.pobHombres = pobHombres;
  }
  
  public int getPobMujeres() {
    return pobMujeres;
  }
  
  public void setPobMujeres(int pobMujeres) {
    this.pobMujeres = pobMujeres;
  }
  
  public int getVehiculos() {
    return vehiculos;
  }
  
  public void setVehiculos(int vehiculos) {
    this.vehiculos = vehiculos;
  }
  
  public int getLineasTelefonicas() {
    return lineasTelefonicas;
  }
  
  public void setLineasTelefonicas(int lineasTelefonicas) {
    this.lineasTelefonicas = lineasTelefonicas;
  }
  
  public int getPoblacionTotal() {
    return pobHombres + pobMujeres;
  }
}
