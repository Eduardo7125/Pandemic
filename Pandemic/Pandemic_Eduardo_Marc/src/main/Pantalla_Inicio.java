package main;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;

public class Pantalla_Inicio {
	public static void main(String[] args) {
        Marco mimimarco = new Marco();
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
	
	
	Lamina1(){
		
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
	        removeAll();
	        Lamina3 lamina3 = new Lamina3();
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
			setVisible(false); 
	        removeAll();
//	        GraphicsDevice grafica=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();<br />
//	        grafica.setFullScreenWindow(this);
	        Lamina2 lamina2 = new Lamina2();
	        lamina2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        lamina2.setVisible(true);
	        getParent().add(lamina2);
	        
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        parentFrame.setSize(1000, 600);
	        
	        getParent().revalidate();
	        getParent().repaint();
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
class Lamina2 extends JPanel implements ActionListener {
	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 2242406387998512165L;
	
	JButton salirButton;
	JPanel buttonPanel;
	JScrollPane scrollPane;
	JLabel menuLabel1;
    Lamina2() {
    
        setLayout(new BorderLayout());
        
        menuLabel1 = new JLabel("<html><div style='text-align:justify;'><h1>INFORMACIÓN</h1>" + 
        		"<h2>INICIO:</h2>" +
                "<p>- Al inicio de cada partida se inicializan las vacunas.</p>" +
                "<p>- Al inicio de cada partida se inicializan las ciudades.</p>" +
                "<p>- Cada ciudad solo se puede infectar con 1 tipo de infección en base a id.</p>" +
                "<p>- Si hay un brote en la ciudad adyacente y ambas ciudades son de un tipo distinto de infección, se sumará la infección correspondiente al tipo.</p>" +
                "<p>- Si en una ciudad hay 3 de infección y se le suma 1, eso es un brote. Si hay brote se suma 1 de infección en las ciudades colindantes.</p>" +
                "<p>- Si hay 3 ciudades conectadas, en la 1 hay un brote, salta a la 2, también hay brote, en la 3 se le suma 2 de infección. No se le suma 1 a las ciudades conectadas que hayan tenido brote en esta cadena de brotes.</p>" +
                "<p>- El número de ciudades infectadas al inicio del juego son un parámetro de configuración.</p>" +
                "<p>- El Número De Ciudades Infectadas A Cada Ronda Son Un Parámetro De Configuración.</p>" +
                "<p>- El Número De Enfermedades Son Un Parámetro De Configuración.</p>" +
        		"<h2>CIUDAD:</h2>" +
        		"<p>-Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.</p>" +
                "<p>-Cada ciudad tiene diferentes niveles de infección.</p>" +
                "<p>-Cada ciudad guardará sus ciudades adyacentes.</p>" +
                "<p>-Cada ciudad aumenta +1 el nivel de infección al volver a infectarse.</p>" +
                "<p>-En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).</p>" +
                "<h2>PARTIDAS:</h2>" +
                "<p>- Número de ronda.</p>" +
                "<p>- Tendrá una lista con todas las ciudades y se podrán realizar acciones en ellas.</p>" +
                "<p>- Tendrá una lista con todas las vacunas y se podrán realizar acciones en ellas (generar vacuna, aumentar%,...).</p>" +
                "<h2>JUGADOR:</h2>" +
                "<p>- El jugador tendrá 4 acciones por ronda.</p>" +
                "<p>- Cada turno se puede crear la vacuna (realizar investigación) o curar ciudades. No se pueden hacer las 2 cosas en un mismo turno.</p>" +
                "<p>- Al curar se gasta 1 acción y solo afecta a una ciudad.</p>" +
                "<p>- Al realizar la investigación se gastan las 4 acciones.</p>" +
                "<p>- Se puede tratar la enfermedad de la ciudad que se elija.</p>" +
                "<p>- Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.</p>" +
                "<p>- Cada ciudad tiene diferentes niveles de infección.</p>" +
                "<p>- Cada ciudad guardará sus ciudades adyacentes.</p>" +
                "<p>- Cada ciudad aumenta +1 el nivel de infección al volver a infectarse.</p>" +
                "<p>- En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).</p>" +
                "<h2>INVESTIGACIONES:</h2>" +
                "<p>- Cada vez que se realiza una investigación el jugador gasta 4 acciones y se aumentará un % de la vacuna seleccionada.</p>" +
                "<h2>VACUNA:</h2>" +
                "<p>- La vacuna está formada por 4 partes (1 parte por investigación).</p>" +
                "<p>- 1 vacuna por tipo de enfermedad.</p>" +
                "<p>- Tienen 1 nombre, 1 color y 1 % de desarrollo.</p>" +
                "<p>- Si la vacuna está desarrollada al 100% se indica que el desarrollo ya está completo.</p>" +
                "<p>- Con 1 vacuna completa se puede curar una ciudad entera de golpe.</p>" +
                "<h2>CURAR:</h2>" +
                "<p>- Si al curar 1 ciudad infectada no se tiene una vacuna desarrollada (100%), solo se reduce 1 el nivel de infección.</p>" +
                "<p>- Si al curar 1 ciudad infectada se tiene una vacuna (100% de desarrollo la vacuna), se elimina totalmente la infección (nivel de infección 0).</p>" +
                "<h2>FINALIZAR:</h2>" +
                "<p>- En cada ronda se comprueba si el jugador ha ganado, ha perdido o no se sabe.</p>" +
                "<p>- Se gana al curar todas las infecciones.</p>" +
                "<p>- Si se llega a X brotes, se pierde la partida.</p>" +
                "</div></html>");
        salirButton = new JButton("salir");
        salirButton.addActionListener(this);
        
        buttonPanel = new JPanel();
        buttonPanel.add(salirButton);
        
        scrollPane = new JScrollPane(menuLabel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        Lamina1.styleButton(salirButton);

        repaint();
    }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            Image imagenFondo = new ImageIcon("src//img//fondo.jpg").getImage();
            g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		
    		if (e.getSource() == salirButton) {
    			setVisible(false); 
    	        removeAll();
    	        Lamina1 lamina1 = new Lamina1();
    	        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	        lamina1.setVisible(true);
    	        getParent().add(lamina1);
    	        
    	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	        parentFrame.setSize(320, 600);
    	        
    	        getParent().revalidate();
    	        getParent().repaint();
    		}
    		
    	}
    }
class Lamina3 extends JPanel implements ActionListener {
	
	ImageIcon background;
	
	JButton salirButton;
	
	JPanel topPanel;
	JPanel leftPanel;
	JPanel rightPanel;
	JPanel bottomPanel;
	JPanel middlePanel;
	
	JLabel LabelImagen;
	JLabel menuLabel1;
	Lamina3(){
		setLayout(new BorderLayout());
		
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		middlePanel = new JPanel();

		salirButton = new JButton("sdsd");
		salirButton.addActionListener(this);
		topPanel.add(salirButton, BorderLayout.EAST);
		topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 30));
		topPanel.setBackground(Color.white);

		
		bottomPanel.setBackground(Color.black);
		
		leftPanel.setBackground(Color.blue);
		
		rightPanel.setBackground(Color.red);

		middlePanel.setOpaque(false);
		
		
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		add(leftPanel, BorderLayout.EAST);
		add(rightPanel, BorderLayout.WEST);
		add(middlePanel, BorderLayout.CENTER);
		
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
	        removeAll();
	        Lamina1 lamina1 = new Lamina1();
	        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        lamina1.setVisible(true);
	        getParent().add(lamina1);
	        
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        parentFrame.setSize(320, 600);
	        
	        getParent().revalidate();
	        getParent().repaint();
		}
		
	}
}
