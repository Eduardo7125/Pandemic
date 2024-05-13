package objects;

/**
 * En esta clase se gestionan las distintas variables que afectan a los virus.
 * @author Eduardo y Marc
 */
public class Virus {

    private String identificador;
    private String nombre;
    private String color;

    /**
     * Constructor para los Virus.
     * 
     * @param identificador Identificador del virus.
     * @param nombre       Nombre del virus.
     * @param color        Color del virus.
     */
    public Virus(String identificador, String nombre, String color) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.color = color;
    }

    /**
     * Obtener el identificador del virus.
     * 
     * @return Identificador del virus.
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establecer el identificador del virus.
     * 
     * @param identificador Nuevo identificador del virus.
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtener el nombre del virus.
     * 
     * @return Nombre del virus.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer el nombre del virus.
     * 
     * @param nombre Nuevo nombre del virus.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obteener el color del virus.
     * 
     * @return Color del virus.
     */
    public String getColor() {
        return color;
    }

    /**
     * Establecer el color del virus.
     * 
     * @param color Nuevo color del virus.
     */
    public void setColor(String color) {
        this.color = color;
    }
}
