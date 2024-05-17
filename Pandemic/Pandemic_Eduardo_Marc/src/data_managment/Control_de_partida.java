package data_managment;
import main.game;

import java.util.ArrayList;
import java.util.Random;

import objects.Ciudad;
/**
 * @author Eduardo y Marc
 */
public class Control_de_partida {

	public static String playername = "a";
	public static int turno = 1;
	public static int acciones = 4;
	public static int outbreak = 0;
	public static int infectedcities = 0;
	public static int citiesleft;
	public static String resultado;
	
	public void iniciarPartida() {
		
	}
	
	public void iniciarPartidaGuardada(String id) {
		
	}
	
	public static void gestionarTurno() {
		turno++;
	}
	
	public void gestionarVacuna() {
		
	}
	
	public static void InfeccionInicial() {
		for (objects.Vacunas vacunas : Control_de_datos.Vacuna) {
            for (objects.Virus virus : Control_de_datos.Virus) {
                if (vacunas.getNombre().equalsIgnoreCase(virus.getNombre())) {
                	System.out.println(vacunas.getNombre() + " " + virus.getNombre());
                }
            }
		}
	    ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;
	    
	    ArrayList<Ciudad> ciudadesAleatorias = SelecionarCiudadesInfeccionInicial(ciudades);

	    for (Ciudad ciudad : ciudadesAleatorias) {
	        ciudad.aumentarInfeccion();
	        System.out.println("Name: " + ciudad.getNombre() + " | Virus: " + ciudad.getNombreEnfermedad());
	        game.actualizarEstadoCiudades();
	    }
	    System.out.println();
	}

	public static ArrayList<Ciudad> SelecionarCiudadesInfeccionInicial(ArrayList<Ciudad> ciudades) {
	    ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
	    Random rand = new Random();
	    int infect = Integer.parseInt(Control_de_datos.CiudadesInfectadasInicio);
	    for (int i = 0; i < infect; i++) {
	        int index = rand.nextInt(ciudades.size());
	        Ciudad ciudad = ciudades.get(index);
	        ciudadesAleatorias.add(ciudad);
	    }

	    return ciudadesAleatorias;
	}
    
	
	public static void gestionarInfeccion() {
	    ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;

	    ArrayList<Ciudad> ciudadesAleatorias = seleccionarCiudadesParaInfeccion(ciudades);

	    for (Ciudad ciudad : ciudadesAleatorias) {
	        ciudad.aumentarInfeccion();
	        System.out.println("Name: " + ciudad.getNombre() + " | Virus: " + ciudad.getNombreEnfermedad() + " | Infection: " + ciudad.getInfeccion());
	        game.actualizarEstadoCiudades();
	        if (ciudad.getInfeccion() > 3) {
	        	ciudad.setOutbreakHappened(true);
	            outbreak++;
	            game.brotes();
	            System.out.println("AN OUTBREAK IS HAPPENING");
	            ciudad.setInfeccion(3);
	            game.actualizarEstadoCiudades();
	            ciudad.propagarInfeccion();
	        }
	    }
	}

	private static ArrayList<Ciudad> seleccionarCiudadesParaInfeccion(ArrayList<Ciudad> ciudades) {
	    ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
	    Random rand = new Random();
	    int infect = Integer.parseInt(Control_de_datos.CiudadesInfectadasRonda);
	    int count = 0;
	    while (count < infect) {
	        int index = rand.nextInt(ciudades.size());
	        Ciudad ciudad = ciudades.get(index);
	        if (!ciudadesAleatorias.contains(ciudad)) {
	            ciudadesAleatorias.add(ciudad);
	            count++;
	        }
	    }
	    return ciudadesAleatorias;
	}
    
	public static void ResetOutbreak() {
	    ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;

	    for (Ciudad ciudad : ciudades) {
	    	ciudad.setOutbreakHappened(false);
	        }
	}
	
	public void gestionarFinPartida() {
		
	}
	
	public void gestionarFrases() {
		
	}
	
	public void gestionarCura() {
		
	}
}
