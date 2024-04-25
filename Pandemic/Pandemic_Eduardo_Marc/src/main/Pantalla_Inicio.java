package main;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class Pantalla_Inicio {
	public static void main(String[] args) {
		Marco mimimarco = new Marco();	
	}

}

class Marco extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon icono;
	public Marco(){
		setTitle("Pandemic");
		icono = new ImageIcon("src//img//icon.png");
        setIconImage(icono.getImage());
        
        setSize(320, 600);
        setResizable(false);
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
     
            }
        });

        Lamina1 lamina1 = new Lamina1();
        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		add(lamina1);
		
		moveWindow();
		
		setVisible(true);
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6740953577204998600L;
	/**
	 * 
	 */
	ImageIcon icono;
	ImageIcon iconoEscalado;
	ImageIcon iconoNuevaPartida;
	ImageIcon iconoCargarPartida;
	ImageIcon iconoInfo;
	ImageIcon iconoScore;
	ImageIcon iconoSalir;
	Image imagen;
	Image imagenEscalada;
	static Image nuevaImagen;
	static Image imagen_botones;
	
	JPanel buttonPanel;
	JPanel gridLabel1;
	JPanel bottomPanel;
	
	JLabel version;
	JLabel menuLabel1;
	
	JButton button;
	JButton nuevaPartidaButton;
	JButton cargarPartidaButton;
	JButton informacionButton;
	JButton resumenButton;
	JButton salirButton;
	
	
	Lamina1(){
		
		setLayout(new BorderLayout());
		
		// Iconos
        icono = new ImageIcon("src//img//icon.png");
        iconoNuevaPartida = new ImageIcon("src//img//nueva_partida.png");
        iconoCargarPartida = new ImageIcon("src//img//cargar_partida.png");
        iconoInfo = new ImageIcon("src//img//info.png");
        iconoScore = new ImageIcon("src//img//ranking.png");
        iconoSalir = new ImageIcon("src//img//salir.png");

        // Etiqueta del menú
        menuLabel1 = new JLabel("<html><div style='text-align:center;'><h1>PANDEMIC</h1><h2>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'>");
        menuLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(menuLabel1, BorderLayout.NORTH);

        // Panel para los botones
        buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.CENTER);
        // Botones
        nuevaPartidaButton = createButton(iconoNuevaPartida);
        cargarPartidaButton = createButton(iconoCargarPartida);
        informacionButton = createButton(iconoInfo);
        resumenButton = createButton(iconoScore);
        salirButton = createButton(iconoSalir);

        buttonPanel.add(nuevaPartidaButton);
        buttonPanel.add(cargarPartidaButton);
        buttonPanel.add(informacionButton);
        buttonPanel.add(resumenButton);
        buttonPanel.add(salirButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // Panel inferior y etiqueta de versión
        bottomPanel = new JPanel();
        version = new JLabel("<html><div style='float:left;'><p>Euardo/Marc</p></div><div style='text-align:center;'><p>Version 1.0</p></div>");
        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        fondo(); // Configurar fondo

        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10)); // Borde vacío
	}
	
    public JButton createButton(ImageIcon icono) {
        button = new JButton();
        imageBotones(icono);
        button.setIcon(new ImageIcon(nuevaImagen));
        styleButton(button);
        add(button, BorderLayout.CENTER);
        button.addActionListener(this);
        return button;
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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Image imagenFondo = new ImageIcon("src//img//fondo.jpg").getImage();
        g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }
    public static void styleButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
//        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });
    }
	
    public static void imageBotones(ImageIcon icono) {
        imagen_botones = icono.getImage();
        nuevaImagen = imagen_botones.getScaledInstance(450, 300, Image.SCALE_SMOOTH);
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
