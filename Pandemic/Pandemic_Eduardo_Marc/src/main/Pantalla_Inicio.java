package main;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

import data_managment.Control_de_datos;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serial;

public class Pantalla_Inicio {
	public static void main(String[] args) {
	    Thread cargaPartidaThread = new Thread(() -> Control_de_datos.cargarPartida());
	    cargaPartidaThread.start();

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
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (graphicsDevice.isFullScreenSupported()) {
            setUndecorated(true);
            setResizable(false);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            System.out.println("El modo de pantalla completa no es soportado por este dispositivo.");
        }

        Lamina1 lamina1 = Lamina1.getInstance();
        
        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(lamina1);

        setVisible(true);
	}
	
}

class Lamina1 extends JPanel implements ActionListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5124796854119688429L;
	ImageIcon icono;
    ImageIcon iconoEscalado;
    ImageIcon iconoNuevaPartida;
    ImageIcon iconoNuevaPartida2;
    ImageIcon iconoCargarPartida;
    ImageIcon iconoCargarPartida2;
    ImageIcon iconoInfo;
    ImageIcon iconoInfo2;
    ImageIcon iconoScore;
    ImageIcon iconoScore2;
    ImageIcon iconoSalir;
    ImageIcon iconoSalir2;
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
    static JButton salirButton;

    Marco marco;

    Lamina1() {
        setLayout(new BorderLayout());

        // Iconos

        icono = new ImageIcon("src//img//icon.png");
        iconoNuevaPartida = new ImageIcon("src//img//nueva_partida.png");
        iconoNuevaPartida2 = new ImageIcon("src//img//nueva_partida2.png");
        iconoCargarPartida = new ImageIcon("src//img//cargar_partida.png");
        iconoCargarPartida2 = new ImageIcon("src//img//cargar_partida2.png");
        iconoInfo = new ImageIcon("src//img//info.png");
        iconoInfo2 = new ImageIcon("src//img//info2.png");
        iconoScore = new ImageIcon("src//img//ranking.png");
        iconoScore2 = new ImageIcon("src//img//ranking2.png");
        iconoSalir = new ImageIcon("src//img//salir.png");
        iconoSalir2 = new ImageIcon("src//img//salir2.png");

        menuLabel1 = new JLabel("<html><div style='text-align:center;'><h1>PANDEMIC</h1><h2>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'>");

        // Etiqueta del menú
        menuLabel1 = new JLabel("<html><div style='text-align:center;'><h1 style='font-size: 35px;'>PANDEMIC</h1><h2 style='font-size: 24px;'>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'></div>");

        menuLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(menuLabel1, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.CENTER);

        // Botones
        nuevaPartidaButton = createButton(iconoNuevaPartida, iconoNuevaPartida2);
        cargarPartidaButton = createButton(iconoCargarPartida, iconoCargarPartida2);
        informacionButton = createButton(iconoInfo, iconoInfo2);
        resumenButton = createButton(iconoScore, iconoScore2);
        salirButton = createButton(iconoSalir, iconoSalir2);


        buttonPanel.add(nuevaPartidaButton);
        buttonPanel.add(cargarPartidaButton);
        buttonPanel.add(informacionButton);
        buttonPanel.add(resumenButton);
        buttonPanel.add(salirButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        bottomPanel = new JPanel();
        version = new JLabel("<html><div style='text-align:center;color: white;'><p>Eduardo/Marc</p><p>Version 1.0</p></div>");


        // Panel inferior y etiqueta de versión
        bottomPanel = new JPanel();
        JLabel version = new JLabel("<html><font color='white'><p>Euardo/Marc</p><div style='text-align:center;'><font color='white'><p>Version 1.0</p></div></font></html>");

        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
	}

    public JButton createButton(ImageIcon icono, ImageIcon iconoHover) {
        JButton button = new JButton();
        button.setIcon(icono);

        styleButton(button);
        add(button, BorderLayout.CENTER);
        button.addActionListener(this);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(iconoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icono);
            }
        });

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == nuevaPartidaButton) {
		    setVisible(false);
		    Lamina3 lamina3 = Lamina3.getInstance();
		    lamina3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		    lamina3.setVisible(true);
		    
		    getParent().add(lamina3);
	        getParent().revalidate();
	        getParent().repaint();
		} else if (e.getSource() == cargarPartidaButton) {
			System.exit(0);
		} else if (e.getSource() == informacionButton) {
			setVisible(false); 

	        Lamina2 lamina2 = Lamina2.getInstance();
	        lamina2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	   
	        lamina2.setVisible(true);
	        getParent().add(lamina2);        
	        getParent().revalidate();
	        getParent().repaint();
		} else if (e.getSource() == resumenButton) {
			System.exit(0);
		}else if (e.getSource() == salirButton) {
			System.exit(0);
		}
		
	}
	private static Lamina1 instance;
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
class Lamina2 extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2242406387998512165L;

    JButton salirButton;
    JPanel buttonPanel;
    JScrollPane scrollPane;
    JLabel informacionLabel;
    JLabel tituloLabel;


    Lamina2() {

        setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(0, 1));

        JButton texto1 = new JButton("INFORMACIÓN");

        JButton texto2 = new JButton("START");
        texto2.addActionListener(this);
        buttonPanel.add(texto2);

        JButton texto3 = new JButton("CITY");
        texto3.addActionListener(this);
        buttonPanel.add(texto3);

        JButton texto4 = new JButton("GAMES");
        texto4.addActionListener(this);
        buttonPanel.add(texto4);

        JButton texto5 = new JButton("PLAYER");
        texto5.addActionListener(this);
        buttonPanel.add(texto5);

        JButton texto6 = new JButton("RESEARCH");
        texto6.addActionListener(this);
        buttonPanel.add(texto6);

        JButton texto7 = new JButton("VACCINE");
        texto7.addActionListener(this);
        buttonPanel.add(texto7);

        JButton texto8 = new JButton("HEAL");
        texto8.addActionListener(this);
        buttonPanel.add(texto8);

        JButton texto9 = new JButton("END");
        texto9.addActionListener(this);
        buttonPanel.add(texto9);

        salirButton = new JButton("MENU");
        salirButton.addActionListener(this);
        buttonPanel.add(salirButton);

        Lamina1.styleButton(salirButton);

        tituloLabel = new JLabel("INFORMACIÓN");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        informacionLabel = new JLabel();
        informacionLabel.setVerticalAlignment(JLabel.TOP);

        scrollPane = new JScrollPane(informacionLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tituloLabel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.WEST); // Cambio en la disposición del panel de botones
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        String informacion = "";

        switch (boton.getText()) {
            case "START":
                tituloLabel.setText("START");
                informacion = "<html><p>At the beginning of each game, vaccines are initialized.<br>"
                        + "At the beginning of each game, cities are initialized.<br>"
                        + "Each city can only be infected with 1 type of infection based on its ID.<br>"
                        + "If there is an outbreak in the adjacent city and both cities have different types of infection, the corresponding infection type will be added.<br>"
                        + "If a city has 3 infections and 1 is added, it is an outbreak. If there is an outbreak, 1 infection is added to the neighboring cities.<br>"
                        + "If there are 3 connected cities, an outbreak occurs in the first city, it jumps to the second, there is also an outbreak, in the third, 2 infections are added. 1 is not added to the connected cities that have had an outbreak in this chain of outbreaks.<br>"
                        + "The number of infected cities at the beginning of the game is a configuration parameter.<br>"
                        + "The number of infected cities each round is a configuration parameter.<br>"
                        + "The number of diseases is a configuration parameter.</p></html>";
                break;
            case "CITY":
                tituloLabel.setText("CITY");
                informacion = "<html><p>Each city has a name, X coordinate, Y coordinate, and type of disease.<br>"
                        + "Each city has different infection levels.<br>"
                        + "Each city stores its adjacent cities.<br>"
                        + "Each city increases the infection level by +1 when it becomes infected again.<br>"
                        + "In each city, if the infection level exceeds 3, the infection spreads to its adjacent cities and (+1 is added to the outbreak counter).</p></html>";
                break;
            case "GAMES":
                tituloLabel.setText("GAMES");
                informacion = "<html><p>Round number.<br>"
                        + "It will have a list with all the cities and actions that can be performed on them.<br>"
                        + "It will have a list with all the vaccines and actions that can be performed on them (generate vaccine, increase%, ...).</p></html>";
                break;
            case "PLAYER":
                tituloLabel.setText("PLAYER");
                informacion = "<html><p>The player will have 4 actions per round.<br>"
                        + "Each turn, the player can create the vaccine (conduct research) or cure cities. Both cannot be done in the same turn.<br>"
                        + "Curing costs 1 action and only affects one city.<br>"
                        + "When conducting research, all 4 actions are spent.<br>"
                        + "The disease of the chosen city can be treated.<br>"
                        + "Each city has a name, X coordinate, Y coordinate, and type of disease.<br>"
                        + "Each city has different infection levels.<br>"
                        + "Each city stores its adjacent cities.<br>"
                        + "Each city increases the infection level by +1 when it becomes infected again.<br>"
                        + "In each city, if the infection level exceeds 3, the infection spreads to its adjacent cities and (+1 is added to the outbreak counter).</p></html>";
                break;
            case "RESEARCH":
                tituloLabel.setText("RESEARCH");
                informacion = "<html><p>Every time research is conducted, the player spends 4 actions and the percentage of the selected vaccine will increase.</p></html>";
                break;
            case "VACCINE":
                tituloLabel.setText("VACCINE");
                informacion = "<html><p>The vaccine consists of 4 parts (1 part per research).<br>"
                        + "1 vaccine per disease type.<br>"
                        + "They have a name, a color, and a development percentage.<br>"
                        + "If the vaccine is developed 100%, it is indicated that the development is already complete.<br>"
                        + "With 1 complete vaccine, an entire city can be cured at once.</p></html>";
                break;
            case "HEAL":
                tituloLabel.setText("HEAL");
                informacion = "<html><p>If when curing 1 infected city, a vaccine developed 100% is not available, only the infection level is reduced by 1.<br>"
                        + "If when curing 1 infected city, a vaccine developed 100% is available, the infection is completely eliminated (infection level 0).</p></html>";
                break;
            case "END":
                tituloLabel.setText("END");
                informacion = "<html><p>At the end of each round, it is checked whether the player has won, lost, or it is unknown.<br>"
                        + "You win by curing all infections.<br>"
                        + "If X outbreaks occur, the game is lost.</p></html>";
                break;
            case "MENU":
    			setVisible(false); 
    	        Lamina1 lamina1 = Lamina1.getInstance();
    	        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	        lamina1.setVisible(true);
    	        getParent().add(lamina1);
    	        
    	        getParent().revalidate();
    	        getParent().repaint();
                break;
        }

        informacionLabel.setText(informacion);
    }
	private static Lamina2 instance;
    public static Lamina2 getInstance() {
        if (instance == null) {
            instance = new Lamina2();
        }
        return instance;
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
		
		JTextArea texto = new JTextArea(7, 50);
		texto.setEditable(false);
		Font font = new Font("Karmatic Arcade", Font.PLAIN, 14);
		texto.setFont(font);
        texto.setForeground(Color.BLUE);
        texto.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(texto);// Establecer el tamaño del JScrollPane

        rightPanel.add(scrollPane, BorderLayout.CENTER);
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            	texto.append(String.valueOf((char) b));
            }
        });

        System.setOut(printStream);
        System.setErr(printStream);
        texto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bottomPanel.add(texto, BorderLayout.CENTER);
		bottomPanel.setBackground(Color.black);
		
		vacunas("Alfa", new Color(118, 189, 248));
		vacunas("Beta", new Color(248, 118, 118));
		vacunas("Gama", new Color(118, 248, 150));
		vacunas("Delta", new Color(236, 248, 118));
		
		rightPanel.setBackground(Color.blue.darker().darker().darker());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
		brotes();
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
	
	public static float valorFloat = (float) 20;
	
	public void vacunas(String nombre, Color color) {
		final JProgressBar vacunaFinal = new JProgressBar();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		ImageIcon icono = new ImageIcon("src//img//contenedor_vacunas.png");
		Image imagen = icono.getImage();
		Image imagenEscalada = imagen.getScaledInstance(75, 39, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		vacunaFinal.setUI(new CustomProgressBarUI(iconoEscalado.getImage()) {
			protected Color getSelectionForeground() {
				return Color.DARK_GRAY;
			}
		});

		vacunaFinal.setMinimum(0);
		vacunaFinal.setMaximum(100);
		vacunaFinal.setStringPainted(true);
		vacunaFinal.setOpaque(false);
		vacunaFinal.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 30));
		
		vacunaFinal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (nombre) {
                    case "Alfa":
                        Control_de_datos.Vacuna.get(0).desarrollarVacuna(valorFloat);
                        vacunaFinal.setValue((int) Control_de_datos.Vacuna.get(0).getPorcentaje());
                        break;
                    case "Beta":
                        Control_de_datos.Vacuna.get(1).desarrollarVacuna(valorFloat);
                        vacunaFinal.setValue((int) Control_de_datos.Vacuna.get(1).getPorcentaje());
                        break;
                    case "Gama":
                        Control_de_datos.Vacuna.get(2).desarrollarVacuna(valorFloat);
                        vacunaFinal.setValue((int) Control_de_datos.Vacuna.get(2).getPorcentaje());
                        break;
                    case "Delta":
                        Control_de_datos.Vacuna.get(3).desarrollarVacuna(valorFloat);
                        vacunaFinal.setValue((int) Control_de_datos.Vacuna.get(3).getPorcentaje());
                        break;
                }
            }
        });

		vacunaFinal.setForeground(color);
		vacunaFinal.setName(nombre);
		rightPanel.add(vacunaFinal, gbc);
			 
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
