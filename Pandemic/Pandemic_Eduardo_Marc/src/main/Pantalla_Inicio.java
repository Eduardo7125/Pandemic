package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
<<<<<<< HEAD
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Pantalla_Inicio {
    public static Marco mimimarco;
=======
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

import data_managment.Control_de_datos;

import java.awt.*;
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
>>>>>>> Eduardo

    public static void main(String[] args) {
        mimimarco = new Marco();
    }
}

<<<<<<< HEAD
class Marco extends JFrame {

    ImageIcon icono;

    public Marco() {
        setTitle("Pandemic");
        icono = new ImageIcon("src//img//icon.png");
        setIconImage(icono.getImage());

        setSize(320, 600);
        setResizable(false);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {

            }
        });

        Lamina1 lamina1 = new Lamina1(this);
        lamina1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(lamina1);

        moveWindow();

        setVisible(true);
    }

    private static Point currCoords = new Point();
    private static Point mouseDownCompCoords;
    private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public void moveWindow() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mouseDownCompCoords = evt.getPoint();
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                currCoords = evt.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    public void cambiarPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

}

class Lamina1 extends JPanel implements ActionListener {

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
    static JButton salirButton;

    Marco marco;

    Lamina1(Marco marco) {
        this.marco = marco;
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
        version = new JLabel("<html><p>Euardo/Marc</p><div style='text-align:center;'><p>Version 1.0</p></div>");
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
=======
class Marco extends JFrame{
    /**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;
	public Marco(){
		setTitle("Pandemic");
        setIconImage(new ImageIcon("src//img//icon.png").getImage());
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (graphicsDevice.isFullScreenSupported()) {
            setUndecorated(true);
            setResizable(false);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            System.out.println("El modo de pantalla completa no es soportado por este dispositivo.");
        }

        menu menu = main.menu.getInstance();
        
        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(menu);

        setVisible(true);
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

>>>>>>> Eduardo
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(backgroundImage, 0, 0, c.getWidth(), c.getHeight(), null);

        g2d.dispose();
    }
<<<<<<< HEAD

    public static void styleButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
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
            marco.cambiarPanel(new Lamina2(marco));
        } else if (e.getSource() == resumenButton) {
            System.exit(0);
        } else if (e.getSource() == salirButton) {
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

    private static final long serialVersionUID = 2242406387998512165L;

    JButton salirButton;
    JPanel buttonPanel;
    JScrollPane scrollPane;
    JLabel menuLabel1;

    Marco marco;

    Lamina2(Marco marco) {
        this.marco = marco;

        setLayout(new BorderLayout());

        buttonPanel = new JPanel(); // Inicializar el panel de botones

        JButton texto1 = new JButton("INFORMACIÓN");
        buttonPanel.add(texto1);

        JButton texto2 = new JButton("INICIO"
                + "Al inicio de cada partida se inicializan las vacunas.\n"
                + "Al inicio de cada partida se inicializan las ciudades.\n"
                + "Cada ciudad solo se puede infectar con 1 tipo de infección en base a id.\n"
                + "Si hay un brote en la ciudad adyacente y ambas ciudades son de un tipo distinto de infección, se sumará la infección correspondiente al tipo.\n"
                + "Si en una ciudad hay 3 de infección y se le suma 1, eso es un brote. Si hay brote se suma 1 de infección en las ciudades colindantes.\n"
                + "Si hay 3 ciudades conectadas, en la 1 hay un brote, salta a la 2, también hay brote, en la 3 se le suma 2 de infección. No se le suma 1 a las ciudades conectadas que hayan tenido brote en esta cadena de brotes.\n"
                + "El número de ciudades infectadas al inicio del juego son un parámetro de configuración.\n"
                + "El número de ciudades infectadas a cada ronda son un parámetro de configuración.\n"
                + "El número de enfermedades son un parámetro de configuración.");
        buttonPanel.add(texto2);

        JButton texto3 = new JButton("CIUDAD"
                + "Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.\n"
                + "Cada ciudad tiene diferentes niveles de infección.\n"
                + "Cada ciudad guarda sus ciudades adyacentes.\n"
                + "Cada ciudad aumenta en +1 el nivel de infección al volver a infectarse.\n"
                + "En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).");
        buttonPanel.add(texto3);

        JButton texto4 = new JButton("PARTIDAS"
                + "Número de ronda.\n"
                + "Tendrá una lista con todas las ciudades y se podrán realizar acciones en ellas.\n"
                + "Tendrá una lista con todas las vacunas y se podrán realizar acciones en ellas (generar vacuna, aumentar%,...).");
        buttonPanel.add(texto4);

        JButton texto5 = new JButton("JUGADOR"
                + "El jugador tendrá 4 acciones por ronda.\n"
                + "Cada turno se puede crear la vacuna (realizar investigación) o curar ciudades. No se pueden hacer las 2 cosas en un mismo turno.\n"
                + "Al curar se gasta 1 acción y solo afecta a una ciudad.\n"
                + "Al realizar la investigación se gastan las 4 acciones.\n"
                + "Se puede tratar la enfermedad de la ciudad que se elija.\n"
                + "Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.\n"
                + "Cada ciudad tiene diferentes niveles de infección.\n"
                + "Cada ciudad guarda sus ciudades adyacentes.\n"
                + "Cada ciudad aumenta en +1 el nivel de infección al volver a infectarse.\n"
                + "En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).");
        buttonPanel.add(texto5);

        JButton texto6 = new JButton("INVESTIGACIONES"
                + "Cada vez que se realiza una investigación, el jugador gasta 4 acciones y se aumentará un porcentaje de la vacuna seleccionada.");
        buttonPanel.add(texto6);

        JButton texto7 = new JButton("VACUNA"
                + "La vacuna está formada por 4 partes (1 parte por investigación).\n"
                + "1 vacuna por tipo de enfermedad.\n"
                + "Tienen 1 nombre, 1 color y 1 % de desarrollo.\n"
                + "Si la vacuna está desarrollada al 100%, se indica que el desarrollo ya está completo.\n"
                + "Con 1 vacuna completa se puede curar una ciudad entera de golpe.");
        buttonPanel.add(texto7);

        JButton texto8 = new JButton("CURAR"
                + "Si al curar 1 ciudad infectada no se tiene una vacuna desarrollada al 100%, solo se reduce en 1 el nivel de infección.\n"
                + "Si al curar 1 ciudad infectada se tiene una vacuna desarrollada al 100%, se elimina totalmente la infección (nivel de infección 0).");
        buttonPanel.add(texto8);

        JButton texto9 = new JButton("FINALIZAR"
                + "En cada ronda se comprueba si el jugador ha ganado, ha perdido o no se sabe.\n"
                + "Se gana al curar todas las infecciones.\n"
                + "Si se llega a X brotes, se pierde la partida.");
        buttonPanel.add(texto9);

        salirButton = new JButton("salir");
        salirButton.addActionListener(this);
        buttonPanel.add(salirButton);

        Lamina1.styleButton(salirButton);

        scrollPane = new JScrollPane(menuLabel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        fondo();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salirButton) {
            marco.cambiarPanel(new Lamina1(marco));
        }
    }
}
=======
}
>>>>>>> Eduardo
