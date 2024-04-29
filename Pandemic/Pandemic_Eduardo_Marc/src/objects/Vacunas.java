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
		float valor = getPorcentaje();
        if (valor > 100) {
        	completo = true;
        	setPorcentaje(100);
        	return;
        }

        setPorcentaje(valor += porcentaje);
        
        if (valor > 100) {
        	setPorcentaje(100);
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
