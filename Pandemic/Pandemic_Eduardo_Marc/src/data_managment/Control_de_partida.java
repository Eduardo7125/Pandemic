package data_managment;
import main.game;

import main.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import objects.Ciudad;


public class Control_de_partida {

	public static int turno = 1;
	public static int acciones = 4;
	public static int outbreak = 0;
	public static int infectedcities = 0;
	public static int citiesleft;
	
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
	        System.out.println("Name: " + ciudad.getNombre());
	        System.out.println("Virus: " + ciudad.getNombreEnfermedad());
	        System.out.println("Infection: " + ciudad.getInfeccion());
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
	        if (outbreak == Integer.parseInt(Control_de_datos.NumBrotesDerrota) || infectedcities == Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota)) {
	        	GameOver();
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
    
//	private static ArrayList<Ciudad> seleccionarCiudadesParaInfeccion(ArrayList<Ciudad> ciudades) {
//	    ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
//	    for (int i = 0; i < 1; i++) {	
//	        Ciudad ciudad = ciudades.get(0);
//	        ciudadesAleatorias.add(ciudad);
//	    }
//	    return ciudadesAleatorias;
//	}
	
    private static void GameOver() {
        JFrame errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorFrame.setUndecorated(true);
        errorFrame.setResizable(false);
        errorFrame.setAlwaysOnTop(true);

        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(errorFrame);

        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel errorMessage = new JLabel("GAME OVER");
        errorMessage.setForeground(Color.RED);
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.setFont(new Font("Arial", Font.PLAIN, 100));

        panel.add(errorMessage, BorderLayout.CENTER);
        errorFrame.add(panel);

        
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.dispose();
                System.exit(0);
            }
        });
        timer.setRepeats(false);
        timer.start();

        errorFrame.setVisible(true);
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
