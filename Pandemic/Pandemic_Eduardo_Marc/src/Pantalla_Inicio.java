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
	public static void main(String[] args) {
		Marco mimimarco = new Marco();	
	}

}

class Marco extends JFrame{
    private static Point frame1Position;
    ImageIcon icono;
	public Marco(){
		setTitle("Pandemic");
		icono = new ImageIcon("src//img//icon.png");
        setIconImage(icono.getImage());
        
        setSize(300, 520);
        setResizable(false);
        setUndecorated(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                frame1Position = getLocation();
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
	
	ImageIcon icono;
	ImageIcon iconoEscalado;
	ImageIcon iconoNuevaPartida;
	ImageIcon iconoCargarPartida;
	ImageIcon iconoinfo;
	ImageIcon iconoscore;
	Image imagen;
	Image imagenEscalada;
	static Image nuevaImagen;
	static Image imagen_botones;
	
	JPanel gridLabel1;
	JPanel bottomPanel;
	
	JLabel version;
	JLabel menuLabel1;
	
	JButton nuevaPartidaButton;
	JButton cargarPartidaButton;
	JButton informacionButton;
	JButton resumenButton;
	JButton autoresButton;
	JButton salirButton;
	
	
	Lamina1(){
        // Iconos
        icono = new ImageIcon("src//img//icon.png");
        iconoNuevaPartida = new ImageIcon("src//img//nueva_partida.png");
        iconoCargarPartida = new ImageIcon("src//img//cargar_partida.png");
        iconoinfo = new ImageIcon("src//img//info.png");
        iconoscore = new ImageIcon("src//img//score.png");
        imagen = icono.getImage();
        
        // Paneles y etiquetas
        gridLabel1 = new JPanel();
        menuLabel1 = new JLabel("<html><div style='text-align:center;'><h1>PANDEMIC</h1><h2>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'>");
        
        // Escalar la imagen del botón

        
        // Botones
        nuevaPartidaButton = new JButton();
        
        imageBotones(iconoNuevaPartida);
        nuevaPartidaButton.setIcon(new ImageIcon(nuevaImagen));
        
        imageBotones(iconoCargarPartida);
        cargarPartidaButton = new JButton(new ImageIcon(nuevaImagen));
        
        imageBotones(iconoinfo);
        informacionButton = new JButton(new ImageIcon(nuevaImagen));
        
        imageBotones(iconoscore);
        resumenButton = new JButton(new ImageIcon(nuevaImagen));
        autoresButton = new JButton("Autores");
        salirButton = new JButton("Salir");
        
        // Panel inferior y etiqueta de versión
        bottomPanel = new JPanel();
        version = new JLabel("Version 1.0");
        
        // Estilo de los botones
        styleButton(nuevaPartidaButton);
        styleButton(cargarPartidaButton);
        styleButton(informacionButton);
        styleButton(resumenButton);
        styleButton(autoresButton);
        styleButton(salirButton);
        
        // Agregar oyentes a los botones
        nuevaPartidaButton.addActionListener(this);
        cargarPartidaButton.addActionListener(this);
        informacionButton.addActionListener(this);
        resumenButton.addActionListener(this);
        autoresButton.addActionListener(this);
        salirButton.addActionListener(this);
        
        // Configuración del diseño de la lámina
        gridLabel1.setLayout(new GridLayout(0, 1));
        gridLabel1.add(menuLabel1);
        gridLabel1.setOpaque(false);
        add(gridLabel1, BorderLayout.CENTER);
        
        add(nuevaPartidaButton, BorderLayout.CENTER);
        add(cargarPartidaButton, BorderLayout.CENTER);
        add(informacionButton, BorderLayout.CENTER);
        add(resumenButton, BorderLayout.CENTER);
        add(autoresButton, BorderLayout.CENTER);
        add(salirButton, BorderLayout.CENTER);
        
        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);
        
        fondo(); // Configurar fondo
        
        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10)); // Borde vacío
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
        button.setPreferredSize(new Dimension(200, 40));
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
        nuevaImagen = imagen_botones.getScaledInstance(550, 350, Image.SCALE_SMOOTH);
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
