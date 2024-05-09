package objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data_managment.*;
import main.game;

public class Ciudad {	

	private String nombre;
	private int[] coordenadas;
	private String enfermedad;
	private int infeccion;
	private String[] ciudadesColindantes;
	private boolean OutbreakHappened;
	
	public Ciudad(String nombre, int[] coordenadas, String enfermedad, int infeccion, String[] ciudadesColindantes, boolean OutbreakHappened) {
		this.nombre = nombre;
		this.coordenadas = coordenadas;
		this.enfermedad = enfermedad;
		this.infeccion = infeccion;
		this.ciudadesColindantes = ciudadesColindantes;
		this.OutbreakHappened = OutbreakHappened;
	}
	
	public void aumentarInfeccion() {
		this.infeccion += 1;
		Control_de_partida.infectedcities++;
	}
	
	public void disminuirInfeccion() {
			this.infeccion -= 1;
			Control_de_partida.acciones--;
			Control_de_partida.infectedcities--;
			if (Control_de_partida.infectedcities == 0) {
				Victory();
	        }
			return;
	}
	
	public void disminuirInfeccionConVacuna() {
			Control_de_partida.acciones--;
			Control_de_partida.infectedcities = Control_de_partida.infectedcities - this.infeccion;
			this.infeccion = 0;
			if (Control_de_partida.infectedcities == 0) {
				Victory();
	        }
			return;
	}
	
	private static void Victory() {
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

        JLabel errorMessage = new JLabel("YOU HAVE WON");
        errorMessage.setForeground(Color.GREEN);
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
	
	public void propagarInfeccion() {
	    System.out.println("INFECTED CITIES BY THE OUTBREAK: ");
	    for (String colindantes : this.ciudadesColindantes) {
	        Ciudad ciudad = obtenerCiudad(colindantes);
	        if (!ciudad.getOutbreakHappened()) {
	            ciudad.aumentarInfeccion();
	            System.out.println("Name: " + ciudad.getNombre());
	            System.out.println("Virus: " + ciudad.getNombreEnfermedad());
	            System.out.println("Infection: " + ciudad.getInfeccion());
	            game.actualizarEstadoCiudades();
	            ciudad.setOutbreakHappened(true);
	            if (ciudad.getInfeccion() > 3) {
	                ciudad.setInfeccion(3);
	                propagarInfeccion2(ciudad);
	            }
	        }
	    }
	    System.out.println();
	}

	public void propagarInfeccion2(Ciudad ciudad) { 
	    for (String colindantes : ciudad.getCiudadesColindantes()) {
	        Ciudad ciudadColindante = obtenerCiudad(colindantes);
	        if (!ciudadColindante.getOutbreakHappened()) {
	            ciudadColindante.aumentarInfeccion();
	            System.out.println("Name: " + ciudadColindante.getNombre());
	            System.out.println("Virus: " + ciudadColindante.getNombreEnfermedad());
	            System.out.println("Infection: " + ciudadColindante.getInfeccion());
	            game.actualizarEstadoCiudades();
	            ciudadColindante.setOutbreakHappened(true);
	            if (ciudadColindante.getInfeccion() > 3) {
	                ciudadColindante.setInfeccion(3);
	                propagarInfeccion2(ciudadColindante);
	            }
	        }
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
        
        return "Unkown virus";
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

	public boolean getOutbreakHappened() {
		return OutbreakHappened;
	}

	public void setOutbreakHappened(boolean outbreakHappened) {
		OutbreakHappened = outbreakHappened;
	}
	
	
}
