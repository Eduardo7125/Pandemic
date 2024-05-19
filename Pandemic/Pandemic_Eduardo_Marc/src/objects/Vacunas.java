package objects;

/**
 * En esta clase se gestionan las distintas variables que afectan a las Vacunas.
 * @author Eduardo y Marc
 */
public class Vacunas {

    private String nombre;
    private String color;
    private float porcentaje;
    private boolean completo;

    /**
     * Constructor para las vacunas.
     * 
     * @param nombre     Nombre de la vacuna.
     * @param color      Color de la vacuna.
     * @param porcentaje Porcentaje de desarrollo de la vacuna.
     */
    public Vacunas(String nombre, String color, float porcentaje) {
        this.nombre = nombre;
        this.color = color;
        this.porcentaje = porcentaje;
    }

    /**
     * Desarrollo de la vacuna (aumentar su porcentaje de desarrollo).
     * 
     * @param porcentajeIncremento Porcentaje que aumenta el desarrollo de la vacuna.
     */
    public void desarrollarVacuna(float porcentajeIncremento) {
        completo = false;
        int nuevoValor = (int) (this.getPorcentaje() + porcentajeIncremento);
        if (nuevoValor <= 100) {
            this.setPorcentaje(nuevoValor);
            System.out.println("The development of the vaccine " + this.getNombre() + " has increased to " + nuevoValor);
        } else {
            completo = true;
            System.out.println("The vaccine " + this.getNombre() + " has been completed.");
        }
    }

    /**
     * Obtener el nombre de la vacuna.
     * 
     * @return Nombre de la vacuna.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer el nombre de la vacuna.
     * 
     * @param nombre Nuevo nombre de la vacuna.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener el color de la vacuna.
     * 
     * @return Color de la vacuna.
     */
    public String getColor() {
        return color;
    }

    /**
     * Establecer el color de la vacuna.
     * 
     * @param color Nuevo color de la vacuna.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Obtener el porcentaje de desarrollo de la vacuna.
     * 
     * @return Porcentaje de desarrollo de la vacuna.
     */
    public float getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establecer el porcentaje de desarrollo de la vacuna.
     * 
     * @param porcentaje Nuevo porcentaje de desarrollo de la vacuna.
     */
    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Verificar si la vacuna está completamente desarrollada.
     * 
     * @return true si la vacuna está completa, false si no está completa.
     */
    public boolean isCompleto() {
        return completo;
    }
}
