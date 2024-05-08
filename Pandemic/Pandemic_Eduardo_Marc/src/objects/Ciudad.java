package objects;

import data_managment.*;
import main.game;

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
	
	public void aumentarInfeccion() {
		this.infeccion += 1;
		Control_de_partida.infectedcities++;
	}
	
	boolean cantInfect;
	public void disminuirInfeccion() {
		cantInfect = false;
		if (this.infeccion < 1) {
			cantInfect = true;
			return;
		}
		this.infeccion -= 1;
		Control_de_partida.infectedcities--;
	}
	
	public void propagarInfeccion() {
		System.out.println("INFECTED CITIES BY THE OUTBREAK: ");
		for (String colindantes : this.ciudadesColindantes) {
			Ciudad ciudad = obtenerCiudad(colindantes);
			
	        ciudad.aumentarInfeccion();
	        System.out.println("Name: " + ciudad.getNombre());
	        System.out.println("Virus: " + ciudad.getNombreEnfermedad());
	        System.out.println("Infection: " + ciudad.getInfeccion());
	        game.actualizarEstadoCiudades();
	        if (ciudad.getInfeccion() > 3) {
	        	ciudad.setInfeccion(3);
	        }
	        System.out.println();
		}
	}
	
	public static void resetValues() {
		for (Ciudad ciudad : Control_de_datos.Ciudades) {
			ciudad.setInfeccion(0);
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
	
	public String getNombreEnfermedad() {
        String numeroVirus = enfermedad;
        
        for (Virus virus : Control_de_datos.Virus) {
            if (virus.getIdentificador().equals(numeroVirus)) {
                return virus.getNombre();
            }
        }
        
        return "Virus Desconocido";
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
