package main;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;

import data_managment.Control_de_datos;
import objects.Vacunas;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Pantalla_Inicio {
	public static void main(String[] args) {
        Control_de_datos.cargarPartida(); // Uncomment if needed
        SwingUtilities.invokeLater(() -> {
            Marco mimimarco = new Marco();
            mimimarco.setVisible(true);
        });
	}

}

class Marco extends JFrame{
    /**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;
	ImageIcon icono;
	public Marco(){
		setTitle("Pandemic");
		icono = new ImageIcon("src//img//icon.png");
        setIconImage(icono.getImage());
        
        setSize(320, 600);
        setResizable(false);
        setUndecorated(true);

        Lamina1 lamina1 = new Lamina1();
        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		add(lamina1);
		
		moveWindow();
		
		setVisible(true);
	}
    
	private static Point currCoords = new Point();
    private static Point mouseDownCompCoords;
	private static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public void moveWindow() {
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	            mouseDownCompCoords = e.getPoint();
	        }
	    };
	    
	    addMouseListener(mouseListener);
	    
	    MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent e) {
	            currCoords = e.getLocationOnScreen();
	            setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
	        }
	    };
	    
	    addMouseMotionListener(mouseMotionListener);

	    setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
	}
	
}

class Lamina1 extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 6740953577204998600L;
	ImageIcon icono;
	ImageIcon iconoNuevaPartida;
	ImageIcon iconoCargarPartida;
	ImageIcon iconoInfo;
	ImageIcon iconoScore;
	ImageIcon iconoSalir;
	static Image nuevaImagen;
	static Image imagen_botones;
	
	JPanel buttonPanel;
	JPanel bottomPanel;
	
	JLabel version;
	JLabel menuLabel1;
	
	JButton button;
	JButton nuevaPartidaButton;
	JButton cargarPartidaButton;
	JButton informacionButton;
	JButton resumenButton;
	static JButton salirButton;
	private static Lamina1 instance;
	
	Lamina1(){
		
		putClientProperty("JComponent.roundRect", true);
		setLayout(new BorderLayout());
		
        icono = new ImageIcon("src//img//icon.png");
        iconoNuevaPartida = new ImageIcon("src//img//nueva_partida.png");
        iconoCargarPartida = new ImageIcon("src//img//cargar_partida.png");
        iconoInfo = new ImageIcon("src//img//info.png");
        iconoScore = new ImageIcon("src//img//ranking.png");
        iconoSalir = new ImageIcon("src//img//salir.png");

        menuLabel1 = new JLabel("<html><div style='text-align:center;'><h1>PANDEMIC</h1><h2>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'>");
        menuLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(menuLabel1, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.CENTER);

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
        
        bottomPanel = new JPanel();
        version = new JLabel("<html><div style='text-align:center;color: white;'><p>Eduardo/Marc</p><p>Version 1.0</p></div>");
        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
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
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
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
		    setVisible(false);
		    Lamina3 lamina3 = Lamina3.getInstance();
		    getParent().remove(lamina3);
		    lamina3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		    lamina3.setVisible(true);
		    getParent().add(lamina3);
	        
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	        graphicsDevice.setFullScreenWindow(parentFrame);
	        
	        getParent().revalidate();
	        getParent().repaint();
		} else if (e.getSource() == cargarPartidaButton) {
			System.exit(0);
		} else if (e.getSource() == informacionButton) {
//			setVisible(false); 
//	        removeAll();
//	        GraphicsDevice grafica=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();<br />
//	        grafica.setFullScreenWindow(this);
//	        Lamina2 lamina2 = new Lamina2();
//	        lamina2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//	        lamina2.setVisible(true);
//	        getParent().add(lamina2);
//	        
//	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//	        parentFrame.setSize(1000, 600);
//	        
//	        getParent().revalidate();
//	        getParent().repaint();
		} else if (e.getSource() == resumenButton) {
			System.exit(0);
		}else if (e.getSource() == salirButton) {
			System.exit(0);
		}
		
	}
	
    public static Lamina1 getInstance() {
        if (instance == null) {
            instance = new Lamina1();
        }
        return instance;
    }
    
	public static void cargarRecords() {
		
	}
	
	public static void cargarPantallaGuardado() {
		
	}
	
	public static void cargarPartida() {

		
	}
}
class CustomProgressBarUI extends BasicProgressBarUI {
    private Image backgroundImage;

    public CustomProgressBarUI(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        super.paintDeterminate(g, c);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(backgroundImage, 0, 0, c.getWidth(), c.getHeight(), null);

        g2d.dispose();
    }
}
class Lamina3 extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7725219079694206212L;

	ImageIcon background;
	
	JButton salirButton;
	
	JPanel topPanel;
	JPanel leftPanel;
	JPanel rightPanel;
	JPanel bottomPanel;
	JPanel middlePanel;
	
	JLabel LabelImagen;
	JLabel menuLabel1;
	JLabel brotes;
	
	JProgressBar vacunas;
	private static Lamina3 instance;
	int brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);
	
	Lamina3(){
		
		setLayout(new BorderLayout());
		
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel();
		leftPanel = new JPanel(new GridLayout(brotesvalor, 0, 10, 10));
		rightPanel = new JPanel(new GridBagLayout());
		middlePanel = new JPanel();

		salirButton = new JButton("sdsd");
		salirButton.addActionListener(this);
		topPanel.add(salirButton, BorderLayout.EAST);
		topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 30));
		topPanel.setBackground(Color.white);

		brotes();
		bottomPanel.setBackground(Color.black);
		
		vacunas();
		rightPanel.setBackground(new Color(0,0,0,128));
		
		leftPanel.setBackground(new Color(0,0,0,128));

		middlePanel.setOpaque(false);
		
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		add(rightPanel, BorderLayout.EAST);
		add(leftPanel, BorderLayout.WEST);
		add(middlePanel, BorderLayout.CENTER);
		
	}
	public void brotes() {
		ImageIcon icono = new ImageIcon("src//img//brote_inactivo.png");
        Image imagen = icono.getImage();
        Image imagenEscalada = imagen.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		for (int i = 0; i < brotesvalor; i++) {
			brotes = new JLabel(iconoEscalado);
			brotes.setSize(75, 75);
			leftPanel.add(brotes);
		}
	}
	
	public void vacunas() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		ImageIcon icono = new ImageIcon("src//img//contenedor_vacunas.png");
		Image imagen = icono.getImage();
		Image imagenEscalada = imagen.getScaledInstance(75, 39, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		// Array para almacenar las barras de progreso
		JProgressBar[] barras = new JProgressBar[4];

		for (int i = 0; i < 4; i++) {
			vacunas = new JProgressBar();
			vacunas.setUI(new CustomProgressBarUI(iconoEscalado.getImage()) {
				protected Color getSelectionForeground() {
					return Color.DARK_GRAY;
				}
			});

			vacunas.setMinimum(0);
			vacunas.setMaximum(100);
			vacunas.setValue(45);
			vacunas.setStringPainted(true);
			vacunas.setOpaque(false);
			vacunas.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 30));

			switch (i) {
				case 0:
					vacunas.setName("Alfa");
					vacunas.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							float valorFloat = (float) 20;
							Vacunas vacunas2 = new Vacunas(null, null, 0);
							vacunas2.desarrollarVacuna(vacunas, valorFloat);
						}
					});
					vacunas.setForeground(new Color(118, 189, 248));
					break;
				case 1:
					vacunas.setName("Alfa");
					vacunas.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							float valorFloat = (float) 20;
							Vacunas vacunas2 = new Vacunas(null, null, 0);
							vacunas2.desarrollarVacuna(vacunas, valorFloat);
						}
					});
					vacunas.setForeground(new Color(248, 118, 118));
					break;
				case 2:
					vacunas.setName("Alfa");
					vacunas.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							float valorFloat = (float) 20;
							Vacunas vacunas2 = new Vacunas(null, null, 0);
							vacunas2.desarrollarVacuna(vacunas, valorFloat);
						}
					});
					vacunas.setForeground(new Color(118, 248, 150));
					break;
				default:
					vacunas.setName("Alfa");
					vacunas.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							float valorFloat = (float) 20;
							Vacunas vacunas2 = new Vacunas(null, null, 0);
							vacunas2.desarrollarVacuna(vacunas, valorFloat);
						}
					});
					vacunas.setForeground(new Color(236, 248, 118));
					break;
			}

			barras[i] = vacunas;
		}
	}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Image imagenFondo = new ImageIcon("src//img//mapa.jpg").getImage();
        g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == salirButton) {
			setVisible(false); 
	        Lamina1 lamina1 = Lamina1.getInstance();
	        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        lamina1.setVisible(true);
	        getParent().add(lamina1);
	        
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        parentFrame.setSize(320, 600);
	        
	        getParent().revalidate();
	        getParent().repaint();
		}
		
	}
    public static Lamina3 getInstance() {
        if (instance == null) {
            instance = new Lamina3();
        }
        return instance;
    }
}
