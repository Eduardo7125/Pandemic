
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Pantalla_Inicio {
    private static JLabel menuLabel = new JLabel();
    private static JLabel opcionElegidaLabel = new JLabel();
    private static Point mouseDownCompCoords;
    private static Point currCoords;
    private static Point frame1Position;
    
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
        versionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                frame1Position = frame.getLocation();
                frame.setVisible(false);
                mostrarSegundoFrame();
			}
		});
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
        
        moveWindow(frame);

        frame.add(panel);
        frame.setVisible(true);
    }
    public static void mostrarSegundoFrame() {
        JFrame frame2 = new JFrame("Segundo Frame");
        frame2.setSize(300, 470);
        frame2.setResizable(false);
        frame2.setUndecorated(true);

        frame2.setLocation(frame1Position);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        frame2.setContentPane(contentPane);

        JLabel menuLabel = new JLabel("<html><div style='text-align: center;'><h1>PANDEMIC</h1>"
                + "<h2>MENÚ PRINCIPAL</h2><br>");
        menuLabel.setBounds(70, 0, 260, 200);
        contentPane.add(menuLabel);

        JButton nuevaPartidaButton = new JButton("Menu/");
        nuevaPartidaButton.setBounds(55, 150, 190, 30);
        contentPane.add(nuevaPartidaButton);

        moveWindow(frame2);
        frame2.setVisible(true);
    }
    
    public static void moveWindow(JFrame frame) {
    	frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

	    frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
    
	public static void cargarRecords() {
		
	}
	
	public static void cargarPantallaGuardado() {
		
	}
	
	public static void cargarPartida() {

		
	}
}
