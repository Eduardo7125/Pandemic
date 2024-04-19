import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Pantalla_Inicio {
	public static void main(String[] args) {
		Marco mimimarco = new Marco();	
	}

}

class Marco extends JFrame{
    private static Point frame1Position;
    ImageIcon icono;
	public Marco(){
		setTitle("Pandemic");
		icono = new ImageIcon("icon.png");
        setIconImage(icono.getImage());
        
        setSize(300, 560);
        setResizable(false);
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                frame1Position = getLocation();
            }
        });
        Lamina1 lamina1 = new Lamina1();
		add(lamina1);
		
		fondo();
		moveWindow();
		
		setVisible(true);
	}
	
	private static boolean toWhite = true;
	private static Color currentColor = new Color(173, 216, 230);
    public void fondo() {
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
                repaint();
            }
        });
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(currentColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
    
	private static Point currCoords = new Point();
    private static Point mouseDownCompCoords;
	private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public void moveWindow() {
    	addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

	    addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }
	
}

class Lamina1 extends JPanel implements ActionListener{
	
	ImageIcon icono;
	ImageIcon iconoEscalado;
	Image imagen;
	Image imagenEscalada;

	JPanel bottomPanel;
	
	JLabel version;
	JLabel menuLabel1;
	JLabel menuLabel2;
	JLabel imagenLabel;
	
	JButton nuevaPartidaButton;
	JButton cargarPartidaButton;
	JButton informacionButton;
	JButton resumenButton;
	JButton autoresButton;
	JButton salirButton;
	
	Lamina1(){
		
        icono = new ImageIcon("icon.png");
        imagen = icono.getImage();
        
        imagenEscalada = imagen.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconoEscalado = new ImageIcon(imagenEscalada);
        imagenLabel = new JLabel(iconoEscalado);
	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(7, 7, 7, 7);
		
        menuLabel1 = new JLabel("<html><div style'text-align:center;'><h1>PANDEMIC</h1>");
        menuLabel2 = new JLabel("<html><div style'text-align:center;'><h2>MENÚ PRINCIPAL</h2>");
		nuevaPartidaButton = new JButton("Nueva Partida");
		cargarPartidaButton = new JButton("Cargar Partida");
		informacionButton = new JButton("Información");
		resumenButton = new JButton("Resumen de puntuaciones");
		autoresButton = new JButton("Autores");
		salirButton = new JButton("Salir");
		bottomPanel = new JPanel();
		version = new JLabel("Version 1.0");
		
		styleButton(nuevaPartidaButton);
		styleButton(cargarPartidaButton);
		styleButton(informacionButton);
		styleButton(resumenButton);
		styleButton(autoresButton);
		styleButton(salirButton);
		
		nuevaPartidaButton.addActionListener(this);
		cargarPartidaButton.addActionListener(this);
		informacionButton.addActionListener(this);
		resumenButton.addActionListener(this);
		autoresButton.addActionListener(this);
		salirButton.addActionListener(this);
		
        add(menuLabel1, BorderLayout.NORTH);
        gbc.gridy++;
        add(menuLabel2, BorderLayout.NORTH);
        gbc.gridy++;
        add(imagenLabel, BorderLayout.CENTER);
        gbc.gridy++;
		add(nuevaPartidaButton, BorderLayout.CENTER);
		gbc.gridy++;
		add(cargarPartidaButton, BorderLayout.CENTER);
		gbc.gridy++;
		add(informacionButton, BorderLayout.CENTER);
		gbc.gridy++;
		add(resumenButton, BorderLayout.CENTER);
		gbc.gridy++;
		add(autoresButton, BorderLayout.CENTER);
		gbc.gridy++;
		add(salirButton, BorderLayout.CENTER);
		bottomPanel.add(version);
	    add(bottomPanel, BorderLayout.SOUTH);
		
	    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	   
	}
    
    public static void styleButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == nuevaPartidaButton) {
			System.exit(0);
		} else if (e.getSource() == cargarPartidaButton) {
			System.exit(0);
		} else if (e.getSource() == informacionButton) {
//			lamina2.area1.setText("Has pulsado el bot�n Este");
		} else if (e.getSource() == resumenButton) {
			System.exit(0);
		} else if (e.getSource() == autoresButton) {
			System.exit(0);
		}else if (e.getSource() == salirButton) {
			System.exit(0);
		}
		
	}
	
    
	public static void cargarRecords() {
		
	}
	
	public static void cargarPantallaGuardado() {
		
	}
	
	public static void cargarPartida() {

		
	}
}
