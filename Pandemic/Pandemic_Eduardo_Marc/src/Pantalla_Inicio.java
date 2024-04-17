import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pantalla_Inicio {
	private static JFrame frame = new JFrame("Java Swing Example");
    private static JPanel panel = new JPanel();
    private static JLabel menuLabel = new JLabel();
    private static JLabel opcionElegidaLabel = new JLabel();
    private static Point mouseDownCompCoords;

    public static void main(String[] args) {
        cargarPantallaInicio();
    }

    public static void cargarPantallaInicio() {
        JFrame frame = new JFrame();
        frame.setSize(300, 470);
        frame.setResizable(false);
        frame.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JLabel menuLabel = new JLabel("<html><div style='text-align: center;'><h1>PANDEMIC</h1>"
                + "<h2>MENÚ PRINCIPAL</h2><br>");
        menuLabel.setBounds(70, 0, 260, 200);
        panel.add(menuLabel);

        JButton nuevaPartidaButton = new JButton("Nueva Partida");
        nuevaPartidaButton.setBounds(55, 150, 190, 30);
        panel.add(nuevaPartidaButton);

        JButton cargarPartidaButton = new JButton("Cargar Partida");
        cargarPartidaButton.setBounds(55, 190, 190, 30);
        panel.add(cargarPartidaButton);

        JButton informacionButton = new JButton("Información");
        informacionButton.setBounds(55, 230, 190, 30);
        panel.add(informacionButton);

        JButton resumenButton = new JButton("Resumen de puntuaciones");
        resumenButton.setBounds(55, 270, 190, 30);
        panel.add(resumenButton);

        JButton autoresButton = new JButton("Autores");
        autoresButton.setBounds(55, 310, 190, 30);
        panel.add(autoresButton);

        JButton versionButton = new JButton("Versión");
        versionButton.setBounds(55, 350, 190, 30);
        panel.add(versionButton);

        JButton salirButton = new JButton("Salir");
        salirButton.setBounds(55, 390, 190, 30);
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(salirButton);

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.add(panel);
        frame.setVisible(true);
    }
	
	public static void cargarRecords() {
		
	}
	
	public static void cargarPantallaGuardado() {
		
	}
	
	public static void cargarPartida() {
		
	}
}
