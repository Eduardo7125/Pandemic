package objects;

import javax.swing.JProgressBar;

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
	public void desarrollarVacuna(JProgressBar barra, float porcentaje) {
		completo = false;
		int nuevoValor = barra.getValue() + (int) porcentaje;
		if (nuevoValor <= barra.getMaximum()) {
			barra.setValue(nuevoValor);
			System.out.println("Se aumentó el valor de la vacuna " + barra.getName() + " a " + nuevoValor);
		} else {
			completo = true;
			System.out.println("La vacuna " + barra.getName() + " ya está completamente desarrollada.");
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
