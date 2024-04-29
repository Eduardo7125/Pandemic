package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Pantalla_Inicio {
    public static Marco mimimarco;

    public static void main(String[] args) {
        mimimarco = new Marco();
    }
}

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
    ImageIcon iconoCargarPartida2;
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
        iconoCargarPartida2 = new ImageIcon("src//img//cargar_partida2.png");
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
			setVisible(false); 
	        removeAll();
	        Lamina2 lamina2 = new Lamina2();
	        lamina2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        lamina2.setVisible(true);
	        getParent().add(lamina2);
	        
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	        graphicsDevice.setFullScreenWindow(parentFrame);
	        
	        getParent().revalidate();
	        getParent().repaint();
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
    JLabel informacionLabel;
    JLabel tituloLabel;

    Marco marco;

    Lamina2() {

        setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(0, 1)); // Cambio en la disposición del panel de botones

        JButton texto1 = new JButton("INFORMACIÓN");

        JButton texto2 = new JButton("INICIO");
        texto2.addActionListener(this);
        buttonPanel.add(texto2);

        JButton texto3 = new JButton("CIUDAD");
        texto3.addActionListener(this);
        buttonPanel.add(texto3);

        JButton texto4 = new JButton("PARTIDAS");
        texto4.addActionListener(this);
        buttonPanel.add(texto4);

        JButton texto5 = new JButton("JUGADOR");
        texto5.addActionListener(this);
        buttonPanel.add(texto5);

        JButton texto6 = new JButton("INVESTIGACIONES");
        texto6.addActionListener(this);
        buttonPanel.add(texto6);

        JButton texto7 = new JButton("VACUNA");
        texto7.addActionListener(this);
        buttonPanel.add(texto7);

        JButton texto8 = new JButton("CURAR");
        texto8.addActionListener(this);
        buttonPanel.add(texto8);

        JButton texto9 = new JButton("FINALIZAR");
        texto9.addActionListener(this);
        buttonPanel.add(texto9);

        salirButton = new JButton("SALIR");
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
            case "INICIO":
                tituloLabel.setText("INICIO");
                informacion = "<html><p>Al inicio de cada partida se inicializan las vacunas.<br>"
                        + "Al inicio de cada partida se inicializan las ciudades.<br>"
                        + "Cada ciudad solo se puede infectar con 1 tipo de infección en base a id.<br>"
                        + "Si hay un brote en la ciudad adyacente y ambas ciudades son de un tipo distinto de infección, se sumará la infección correspondiente al tipo.<br>"
                        + "Si en una ciudad hay 3 de infección y se le suma 1, eso es un brote. Si hay brote se suma 1 de infección en las ciudades colindantes.<br>"
                        + "Si hay 3 ciudades conectadas, en la 1 hay un brote, salta a la 2, también hay brote, en la 3 se le suma 2 de infección. No se le suma 1 a las ciudades conectadas que hayan tenido brote en esta cadena de brotes.<br>"
                        + "El número de ciudades infectadas al inicio del juego son un parámetro de configuración.<br>"
                        + "El número de ciudades infectadas a cada ronda son un parámetro de configuración.<br>"
                        + "El número de enfermedades son un parámetro de configuración.</p></html>";
                break;
            case "CIUDAD":
                tituloLabel.setText("CIUDAD");
                informacion = "<html><p>Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.<br>"
                        + "Cada ciudad tiene diferentes niveles de infección.<br>"
                        + "Cada ciudad guarda sus ciudades adyacentes.<br>"
                        + "Cada ciudad aumenta en +1 el nivel de infección al volver a infectarse.<br>"
                        + "En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).</p></html>";
                break;
            case "PARTIDAS":
                tituloLabel.setText("PARTIDAS");
                informacion = "<html><p>Número de ronda.<br>"
                        + "Tendrá una lista con todas las ciudades y se podrán realizar acciones en ellas.<br>"
                        + "Tendrá una lista con todas las vacunas y se podrán realizar acciones en ellas (generar vacuna, aumentar%,...).</p></html>";
                break;
            case "JUGADOR":
                tituloLabel.setText("JUGADOR");
                informacion = "<html><p>El jugador tendrá 4 acciones por ronda.<br>"
                        + "Cada turno se puede crear la vacuna (realizar investigación) o curar ciudades. No se pueden hacer las 2 cosas en un mismo turno.<br>"
                        + "Al curar se gasta 1 acción y solo afecta a una ciudad.<br>"
                        + "Al realizar la investigación se gastan las 4 acciones.<br>"
                        + "Se puede tratar la enfermedad de la ciudad que se elija.<br>"
                        + "Cada ciudad tiene 1 nombre, coordenada X, coordenada Y y tipo de enfermedad.<br>"
                        + "Cada ciudad tiene diferentes niveles de infección.<br>"
                        + "Cada ciudad guarda sus ciudades adyacentes.<br>"
                        + "Cada ciudad aumenta en +1 el nivel de infección al volver a infectarse.<br>"
                        + "En cada ciudad, si el nivel de infección supera el 3, la infección se propaga a sus ciudades adyacentes y se suma (+1 al contador de brotes).</p></html>";
                break;
            case "INVESTIGACIONES":
                tituloLabel.setText("INVESTIGACIONES");
                informacion = "<html><p>Cada vez que se realiza una investigación, el jugador gasta 4 acciones y se aumentará un porcentaje de la vacuna seleccionada.</p></html>";
                break;
            case "VACUNA":
                tituloLabel.setText("VACUNA");
                informacion = "<html><p>La vacuna está formada por 4 partes (1 parte por investigación).<br>"
                        + "1 vacuna por tipo de enfermedad.<br>"
                        + "Tienen 1 nombre, 1 color y 1 % de desarrollo.<br>"
                        + "Si la vacuna está desarrollada al 100%, se indica que el desarrollo ya está completo.<br>"
                        + "Con 1 vacuna completa se puede curar una ciudad entera de golpe.</p></html>";
                break;
            case "CURAR":
                tituloLabel.setText("CURAR");
                informacion = "<html><p>Si al curar 1 ciudad infectada no se tiene una vacuna desarrollada al 100%, solo se reduce en 1 el nivel de infección.<br>"
                        + "Si al curar 1 ciudad infectada se tiene una vacuna desarrollada al 100%, se elimina totalmente la infección (nivel de infección 0).</p></html>";
                break;
            case "FINALIZAR":
                tituloLabel.setText("FINALIZAR");
                informacion = "<html><p>En cada ronda se comprueba si el jugador ha ganado, ha perdido o no se sabe.<br>"
                        + "Se gana al curar todas las infecciones.<br>"
                        + "Si se llega a X brotes, se pierde la partida.</p></html>";
                break;
            case "SALIR":
            	System.exit(0);
                break;
        }

        informacionLabel.setText(informacion);
    }
}