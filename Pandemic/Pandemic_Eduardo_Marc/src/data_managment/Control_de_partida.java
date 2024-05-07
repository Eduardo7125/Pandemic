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
        // Accede a la lista de ciudades desde Control_de_datos
        ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;

        // Selecciona aleatoriamente cuatro ciudades
        ArrayList<Ciudad> ciudadesAleatorias = seleccionarCiudades(ciudades);

        // Ejecuta el método aumentarInfeccion() en cada una de las cuatro ciudades seleccionadas
        for (Ciudad ciudad : ciudadesAleatorias) {
            ciudad.aumentarInfeccion();
            System.out.println("Name: " + ciudad.getNombre() + " Virus: " + ciudad.getNombreEnfermedad());
        }
        System.out.println();
    }

	
    private static ArrayList<Ciudad> InfeccionInicial(ArrayList<Ciudad> ciudades) {
        ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
        Random rand = new Random();
    	int infect = Integer.parseInt(Control_de_datos.CiudadesInfectadasInicio);
        // Selecciona cuatro índices únicos aleatorios
        for (int i = 0; i < infect; i++) {
            int index = rand.nextInt(ciudades.size());
            Ciudad ciudad = ciudades.get(index);
            ciudadesAleatorias.add(ciudad);
        }

        return ciudadesAleatorias;
    }
    
	
	public static void gestionarInfeccion() {
        ArrayList<Ciudad> ciudades = Control_de_datos.Ciudades;

        ArrayList<Ciudad> ciudadesAleatorias = seleccionarCiudades(ciudades);

        for (Ciudad ciudad : ciudadesAleatorias) {
            ciudad.aumentarInfeccion();
            System.out.println("Name: " + ciudad.getNombre());
            System.out.println("Virus: " + ciudad.getNombreEnfermedad());
            System.out.println("Infection: " + ciudad.getInfeccion());
            System.out.println();
            if (ciudad.getInfeccion() > 3) {
            	outbreak++;
            	game.brotes();
        		System.out.println("AN OUTBREAK IS HAPPENING");
        		ciudad.setInfeccion(3);
        		ciudad.propagarInfeccion();
        		gestionarInfeccion();
            }
            if (outbreak >= Integer.parseInt(Control_de_datos.NumBrotesDerrota) || outbreak >= Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota)) {
            	mostrarPantallaError();
            }
        }
    }

    // Método para seleccionar aleatoriamente cuatro ciudades de la lista
    private static ArrayList<Ciudad> seleccionarCiudades(ArrayList<Ciudad> ciudades) {
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
    

//	private static ArrayList<Ciudad> seleccionarCiudades(ArrayList<Ciudad> ciudades) {
//	    ArrayList<Ciudad> ciudadesAleatorias = new ArrayList<>();
//	    // Simplemente agregamos la misma ciudad cuatro veces
//	    for (int i = 0; i < 1; i++) {
//	        Ciudad ciudad = ciudades.get(0); // Tomamos la primera ciudad de la lista
//	        ciudadesAleatorias.add(ciudad);
//	    }
//	    return ciudadesAleatorias;
//	}
	
    private static void mostrarPantallaError() {
        JFrame errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        errorFrame.setUndecorated(true); // Sin bordes
        errorFrame.setResizable(false); // No se puede redimensionar
        errorFrame.setAlwaysOnTop(true); // Siempre al frente

        // Pantalla completa
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(errorFrame);

        // Panel de mensajes de error
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel errorMessage = new JLabel("GAME OVER");
        errorMessage.setForeground(Color.RED);
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.setFont(new Font("Arial", Font.PLAIN, 40));

        panel.add(errorMessage, BorderLayout.CENTER);
        errorFrame.add(panel);

        // Timer para cerrar el panel después de cierto tiempo
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorFrame.dispose(); // Cerrar el panel de error
                System.exit(0); // Terminar la aplicación
            }
        });
        timer.setRepeats(false); // Ejecutar solo una vez
        timer.start();

        errorFrame.setVisible(true); // Mostrar el panel de error
    }
	
	public void gestionarFinPartida() {
		
	}
	
	public void gestionarFrases() {
		
	}
	
	public void gestionarCura() {
		
	}
}
