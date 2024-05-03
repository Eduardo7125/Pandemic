package objects;

import java.util.Arrays;

import data_managment.*;

public class Ciudad {	

	private String nombre;
	private int[] coordenadas;
	private String enfermedad;
	private int infeccion;
	private String[] ciudadesColindantes;
	
	public Ciudad(String nombre, int[] coordenadas, String enfermedad, int infeccion, String[] ciudadesColindantes) {
		this.nombre = nombre;
		this.coordenadas = coordenadas;
		this.enfermedad = enfermedad;
		this.infeccion = infeccion;
		this.ciudadesColindantes = ciudadesColindantes;
	}
	
	@Override
	public String toString() {
	    return "Ciudad{" +
	            "nombre='" + nombre + '\'' +
	            ", coordenadas=[" + coordenadas[0] + "," + coordenadas[1] + "]" +
	            ", enfermedad='" + enfermedad + '\'' +
	            ", infeccion=" + infeccion +
	            ", ciudadesColindantes=" + Arrays.toString(ciudadesColindantes) +
	            '}';
	}
	
	public void aumentarInfeccion() {
		this.infeccion += 1;
		if (this.infeccion > 3) {
			infeccion = 3;
			propagarInfeccion();
		}
	}
	
	boolean cantInfect;
	public void disminuirInfeccion() {
		cantInfect = false;
		if (this.infeccion < 1) {
			cantInfect = true;
			return;
		}
		this.infeccion -= 1;
	}
	
	public void propagarInfeccion() {
		for (String colindantes : this.ciudadesColindantes) {
			Ciudad ciudad = obtenerCiudad(colindantes);
			
	        ciudad.aumentarInfeccion();
		}
	}
	
	public Ciudad obtenerCiudad(String ciudadColindante) {
		for (Ciudad ciudad : Control_de_datos.Ciudades) {
	        if (ciudad.getNombre().equals(ciudadColindante)) {
	            return ciudad;
	        }
	    }
	    return null;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int[] getCoordenadas() {
		return coordenadas;
	}
	
	public void setCoordenadas(int[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	public String getEnfermedad() {
		return enfermedad;
	}
	
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	
	public int getInfeccion() {
		return infeccion;
	}
	
	public void setInfeccion(int infeccion) {
		this.infeccion = infeccion;
	}
	
	public String[] getCiudadesColindantes() {
		return ciudadesColindantes;
	}
	
	public void setCiudadesColindantes(String[] ciudadesColindantes) {
		this.ciudadesColindantes = ciudadesColindantes;
	}
}
