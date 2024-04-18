
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Pantalla_Inicio {
    private static JLabel menuLabel = new JLabel();
    private static JLabel opcionElegidaLabel = new JLabel();
    private static Point mouseDownCompCoords;
    private static Point currCoords;
    private static Point frame1Position;
    
    private static Color currentColor = new Color(173, 216, 230);
    private static boolean toWhite = true;

    public static void main(String[] args) {
        cargarPantallaInicio();
    }

    public static void cargarPantallaInicio() {
        JFrame frame = new JFrame();
        frame.setSize(300, 530);
        frame.setResizable(false);
        frame.setUndecorated(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(currentColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        ImageIcon icono = new ImageIcon("icon.png");
        Image imagen = icono.getImage();
        Image imagenEscalada = imagen.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        JLabel imagenLabel = new JLabel(iconoEscalado);
        imagenLabel.setBounds(110, 123, 80, 80);
        panel.add(imagenLabel);

        JLabel menuLabel = new JLabel("<html><div style='text-align: center;'><h1>PANDEMIC</h1>"
                + "<h2>MENÚ PRINCIPAL</h2><br>");
        menuLabel.setBounds(75, -20, 260, 200);
        panel.add(menuLabel);

        JButton nuevaPartidaButton = new JButton("Nueva Partida");
        nuevaPartidaButton.setBounds(55, 220, 190, 30);
        styleButton(nuevaPartidaButton);
        panel.add(nuevaPartidaButton);
        

        JButton cargarPartidaButton = new JButton("Cargar Partida");
        cargarPartidaButton.setBounds(55, 260, 190, 30);
        styleButton(cargarPartidaButton);
        panel.add(cargarPartidaButton);

        JButton informacionButton = new JButton("Información");
        informacionButton.setBounds(55, 300, 190, 30);
        styleButton(informacionButton);
        panel.add(informacionButton);

        JButton resumenButton = new JButton("Resumen de puntuaciones");
        resumenButton.setBounds(55, 340, 190, 30);
        styleButton(resumenButton);
        panel.add(resumenButton);

        JButton autoresButton = new JButton("Autores");
        autoresButton.setBounds(55, 380, 190, 30);
        styleButton(autoresButton);
        panel.add(autoresButton);

        JButton versionButton = new JButton("Versión");
        versionButton.setBounds(55, 420, 190, 30);
        versionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                frame1Position = frame.getLocation();
                frame.setVisible(false);
                mostrarSegundoFrame();
			}
		});
        styleButton(versionButton);
        panel.add(versionButton);
        

        JButton salirButton = new JButton("Salir");
        salirButton.setBounds(55, 460, 190, 30);
        styleButton(salirButton);
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

        frame.add(panel);
        frame.setVisible(true);
        
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toWhite) {
                    currentColor = new Color(
                            Math.min(currentColor.getRed() + 1, 255),
                            Math.min(currentColor.getGreen() + 1, 255),
                            Math.min(currentColor.getBlue() + 1, 255));
                    if (currentColor.equals(Color.WHITE)) {
                        toWhite = false;
                    }
                } else {
                    currentColor = new Color(
                            Math.max(currentColor.getRed() - 1, 173),
                            Math.max(currentColor.getGreen() - 1, 216),
                            Math.max(currentColor.getBlue() - 1, 230));
                    if (currentColor.equals(new Color(173, 216, 230))) {
                        toWhite = true;
                    }
                }
                panel.repaint();
            }
        });
        timer.start();
    }
    
    private static void styleButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });
    }
    
	public static void cargarRecords() {
		
	}
	
	public static void cargarPantallaGuardado() {
		
	}
	
	public static void cargarPartida() {

		
	}
}
