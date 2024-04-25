package objects;

public class Vacunas {

	private String nombre;
	private String color;
	private float porcentaje;

	public Vacunas(String nombre, String color, float porcentaje) {
		this.nombre = nombre;
		this.color = color;
		this.porcentaje = porcentaje;
	}
	
	boolean completo;
	public void desarrollarVacuna(float porcentaje) {
		completo = false;
        if (this.porcentaje > 100) {
        	completo = true;
        	this.porcentaje = 100;
        	return;
        }

        this.porcentaje += porcentaje;
        
        if (this.porcentaje > 100) {
        	this.porcentaje = 100;
        	return;
        }
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public float getPorcentaje() {
		return porcentaje;
	}
	
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}
