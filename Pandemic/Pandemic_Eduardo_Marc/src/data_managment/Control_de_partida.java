package data_managment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import objects.Ciudad;

public class Control_de_partida {

	public static int turno = 1;
	public static int acciones = 4;
	public void iniciarPartida() {
		
	}
	
	public void iniciarPartidaGuardada(String id) {
		
	}
	
	public static void gestionarTurno() {
		turno++;
	}
	
	public void gestionarVacuna() {
		
	}
	
	public static void gestionarInfeccion() {
        // Accede a la lista de ciudades desde Control_de_datos
        ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;

        // Selecciona aleatoriamente cuatro ciudades
        ArrayList<Ciudad> ciudadesAleatorias = seleccionarCuatroCiudades(ciudades);

        // Ejecuta el método aumentarInfeccion() en cada una de las cuatro ciudades seleccionadas
        for (Ciudad ciudad : ciudadesAleatorias) {
            ciudad.aumentarInfeccion();
            System.out.println("Name: " + ciudad.getNombre());
            System.out.println("Virus: " + ciudad.getNombreEnfermedad());
            System.out.println("Infection: " + ciudad.getInfeccion());
            System.out.println();
        }
    }

    // Método para seleccionar aleatoriamente cuatro ciudades de la lista
    private static ArrayList<Ciudad> seleccionarCuatroCiudades(ArrayList<Ciudad> ciudades) {
        ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
        Random rand = new Random();
    	int infect = Integer.parseInt(Control_de_datos.CiudadesInfectadasRonda);
        // Selecciona cuatro índices únicos aleatorios
        for (int i = 0; i < infect; i++) {
            int index = rand.nextInt(ciudades.size());
            Ciudad ciudad = ciudades.get(index);
            ciudadesAleatorias.add(ciudad);
        }

        return ciudadesAleatorias;
    }
	
	public void gestionarBrote() {
		
	}
	
	public void gestionarFinPartida() {
		
	}
	
	public void gestionarFrases() {
		
	}
	
	public void gestionarCura() {
		
	}
}
