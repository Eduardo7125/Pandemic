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
			int nuevoValor = (int) (this.getPorcentaje() + (int) porcentaje);
			if (nuevoValor <= 100) {
				this.setPorcentaje(nuevoValor);
				System.out.println("The value of vaccine " + this.getNombre() + " has been increased to " + nuevoValor);
			} else {
				completo = true;
				System.out.println("Vaccine " + this.getNombre() + " is already fully developed.");
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

	public boolean isCompleto() {
		return completo;
	}
	
}
