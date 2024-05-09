package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import data_managment.Control_de_datos;
import data_managment.Control_de_partida;
import objects.Ciudad;
import objects.Vacunas;

public class game extends JPanel implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 7725219079694206212L;

    ImageIcon background;

    JButton salirButton;
    static JButton nextRoundButton;
    
    JPanel topPanel;
    static JPanel leftPanel;
    JPanel rightPanel;
    JPanel bottomPanel;
    static JPanel middlePanel;

    JLabel LabelImagen;
    JLabel menuLabel1;
    JLabel brotes;
    
    static JLabel RoundNumber;
    static JLabel infectedCitiesLabel;
    static JLabel infectedCitiesGameOverLabel;

    JProgressBar vacunas;

    static int brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);

    game() {
    	Control_de_partida.turno = 1;
        Control_de_partida.infectedcities = 0;
        Control_de_partida.citiesleft = Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota);
        setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel();
        leftPanel = new JPanel(new GridLayout(brotesvalor, 0, 10, 10));
        rightPanel = new JPanel(new GridBagLayout());
        middlePanel = new JPanel();

        salirButton = new JButton("MENU");
        salirButton.addActionListener(this);
        topPanel.add(salirButton, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 30));
        topPanel.setBackground(Color.white);

        terminal();
        bottomPanel.setBackground(Color.black);

        vacunasCompletas();

        rightPanel.setBackground(Color.blue.darker().darker().darker());
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        brotes();

        leftPanel.setBackground(Color.gray);

        middlePanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(-740, 45, 5, 5);
        
        nextRoundButton = new JButton("NEXT ROUND");
        nextRoundButton.addActionListener(this);
        rightPanel.add(nextRoundButton, gbc);
        
        RoundNumber = new JLabel("Round: " + Control_de_partida.turno);
        RoundNumber.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-690, 45, 5, 5);
        rightPanel.add(RoundNumber, gbc);
        
        infectedCitiesLabel = new JLabel("Infected Cities: " + Control_de_partida.infectedcities);
        infectedCitiesLabel.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-660, 45, 5, 5);
        rightPanel.add(infectedCitiesLabel, gbc);
        
        infectedCitiesGameOverLabel = new JLabel("Cities left: " + Control_de_partida.citiesleft);
        infectedCitiesGameOverLabel.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-630, 45, 5, 5);
        rightPanel.add(infectedCitiesGameOverLabel, gbc);
        RoundNumber.setText("Round: " + Control_de_partida.turno);
        infectedCitiesLabel.setText("Infected Cities: " + Control_de_partida.infectedcities);
        infectedCitiesGameOverLabel.setText("Cities left: " + Control_de_partida.citiesleft);
        
        Thread infection = new Thread(() -> startinfection());
        infection.start();
        ciudades();
        actualizarEstadoCiudades();
        
    }
    public static void actualizarEstadoCiudades() {
        Color verdeSuave = new Color(144, 238, 144);

        for (Component component : middlePanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String cityName = button.getText();
                objects.Ciudad ciudad = obtenerCiudadPorNombre(cityName);
                if (ciudad != null) {
                    int infeccion = ciudad.getInfeccion();
                    button.setEnabled(infeccion >= 1);

                    if (!button.isEnabled()) {
                        button.setBackground(verdeSuave);
                        button.setForeground(Color.BLACK);
                    } else {
                        if (infeccion == 1) {
                            button.setBackground(null);
                            button.setForeground(Color.BLACK);
                        } else if (infeccion == 2) {
                            button.setBackground(Color.YELLOW);
                            button.setForeground(Color.BLACK);
                        } else if (infeccion == 3) {
                            button.setBackground(Color.RED);
                            button.setForeground(Color.BLACK);
                        } else if (infeccion > 3) {
                            button.setBackground(Color.BLACK);
                            button.setForeground(Color.RED);
                        }
                    }
                    
                    Color colorBorde = obtenerColorBorde(ciudad.getEnfermedad());
                    button.setBorder(BorderFactory.createLineBorder(colorBorde, 2));
                }
            }
        }
        Control_de_partida.citiesleft = Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota) - Control_de_partida.infectedcities;
        RoundNumber.setText("Round: " + Control_de_partida.turno);
        infectedCitiesLabel.setText("Infected Cities: " + Control_de_partida.infectedcities);
        infectedCitiesGameOverLabel.setText("Cities left: " + Control_de_partida.citiesleft);
    }

    public static objects.Ciudad obtenerCiudadPorNombre(String nombreCiudad) {
        for (objects.Ciudad ciudad : Control_de_datos.Ciudades) {
            if (ciudad.getNombre().equals(nombreCiudad)) {
                return ciudad;
            }
        }
        return null;
    }

    public static Color obtenerColorBorde(String identificadorVirus) {
        switch (identificadorVirus) {
            case "0":
                return Color.BLUE;
            case "1":
                return Color.RED;
            case "2":
                return Color.GREEN;
            case "3":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }

    public void ciudades() {
        middlePanel.setLayout(null);
        Double[] resol = resoluciones();
        
        for (objects.Ciudad ciudades : Control_de_datos.Ciudades) {
            int[] coordenadas = ciudades.getCoordenadas();
            String[] colindantes = ciudades.getCiudadesColindantes();
            JButton ciudad = new JButton(ciudades.getNombre());
            int x = (int) (coordenadas[0] * resol[0]);
            int y = (int) (coordenadas[1] * resol[1]);
            Dimension size = ciudad.getPreferredSize();
            ciudad.setBounds(x, y, size.width, size.height);
            
            int enfermedad = Integer.parseInt(ciudades.getEnfermedad());
            Vacunas vacuna = Control_de_datos.Vacuna.get(enfermedad);
            float porcentajeVacuna = vacuna.getPorcentaje();
            
            ciudad.addActionListener(e -> {
                if (Control_de_partida.turno == 1) {
                    System.out.println("You can't heal cities in round 1");
                } else if (porcentajeVacuna > 99) {
                    ciudades.disminuirInfeccionConVacuna();
                    System.out.println("Name: " + ciudades.getNombre() + " | Infection: " + ciudades.getInfeccion());
                    actualizarEstadoCiudades();
                } else if (Control_de_partida.acciones > 0 && porcentajeVacuna < 99) {
                    ciudades.disminuirInfeccion();
                    System.out.println("Name: " + ciudades.getNombre() + " | Infection: " + ciudades.getInfeccion());
                    actualizarEstadoCiudades(); 
                } else {
                    System.out.println("You don't have enough actions to perform this action.");
                }
            });
            
            middlePanel.add(ciudad);
        }
    }
    
    public Double[] resoluciones() {
        Double[] resultado = new Double[2];
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        
        System.out.println("Screen resolution: " + screenSize.width + "x" + screenSize.height);
        double x = (double) screenSize.width / 1920;
        double y = (double) screenSize.height / 1080;
        resultado[0] = x;
        resultado[1] = y;
        return resultado;
    }
    
    public void acciones() {
        Control_de_partida.acciones = 4;
        Control_de_partida.ResetOutbreak();
        Control_de_partida.gestionarTurno();
        
        JTextArea texto = (JTextArea) bottomPanel.getComponent(0);

        Runnable printInfection = () -> {
            printInfection();
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(printInfection);
        executor.shutdown();
    }

    public void printInfection() {
        nextRoundButton.setEnabled(false);

        Runnable printInfection = () -> {
            Control_de_partida.gestionarInfeccion();

            SwingUtilities.invokeLater(() -> {
                nextRoundButton.setEnabled(true);
            });
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(printInfection);
        executor.shutdown();
    }

    public void terminal() {
        JTextArea texto = new JTextArea(7, 50);
        texto.setEditable(false);
        texto.setFont(new Font("Arial", Font.BOLD, 14));
        texto.setForeground(Color.BLUE);
        texto.setBackground(Color.LIGHT_GRAY);
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                SwingUtilities.invokeLater(() -> {
                    texto.append(String.valueOf((char) b));
                    int lineCount = texto.getLineCount();
                    if (lineCount > 7) {
                        try {
                            int endOfFirstLine = texto.getLineEndOffset(0);
                            texto.replaceRange("", 0, endOfFirstLine);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    texto.setCaretPosition(texto.getDocument().getLength());
                });
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
        texto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottomPanel.add(texto, BorderLayout.CENTER);
    }
    
    static void startinfection() {
    	nextRoundButton.setEnabled(false);
        Control_de_partida.InfeccionInicial();
        nextRoundButton.setEnabled(true);
    } 

    public static void brotes() {
        leftPanel.removeAll();

        for (int i = 0; i < brotesvalor; i++) {
            ImageIcon icono;
            if (Control_de_partida.outbreak > 0 && i < Control_de_partida.outbreak) {
                icono = new ImageIcon("src//img//brote_activo.png");
            } else {
                icono = new ImageIcon("src//img//brote_inactivo.png");
            }
            Image imagen = icono.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagen);
            JLabel brotes = new JLabel(iconoEscalado);
            brotes.setSize(75, 75);
            leftPanel.add(brotes);
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }

    public void vacunasCompletas() {
        vacunas("Alfa", new Color(118, 189, 248));
        vacunas("Beta", new Color(248, 118, 118));
        vacunas("Gama", new Color(118, 248, 150));
        vacunas("Delta", new Color(236, 248, 118));
    }

    public static float valorFloat = (float) 25;

    public void vacunas(String nombre, Color color) {
        final JProgressBar vacunaFinal = new JProgressBar();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        ImageIcon icono = new ImageIcon("src//img//contenedor_vacunas.png");
        Image imagen = icono.getImage().getScaledInstance(75, 39, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagen);

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
                if (Control_de_partida.acciones == 4) {
                    Control_de_partida.acciones = 0;
                    switch (nombre) {
                        case "Alfa":
                            Thread VacunaAlfa = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(0),
                                    vacunaFinal));
                            VacunaAlfa.start();
                            break;
                        case "Beta":
                            Thread VacunaBeta = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(1),
                                    vacunaFinal));
                            VacunaBeta.start();
                            break;
                        case "Gama":
                            Thread VacunaGamma = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(2),
                                    vacunaFinal));
                            VacunaGamma.start();
                            break;
                        case "Delta":
                            Thread VacunaDelta = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(3),
                                    vacunaFinal));
                            VacunaDelta.start();
                            break;
                    }
                } else {
                    Thread respuesta = new Thread(() -> System.out
                            .println("You don't have enough actions to perform this action."));
                    respuesta.start();
                }

            }
        });

        vacunaFinal.setForeground(color);
        vacunaFinal.setName(nombre);
        rightPanel.add(vacunaFinal, gbc);

    }

    public void desarrolloVacunas(Vacunas vacuna, JProgressBar vacunaFinal) {

        int counter = (int) vacuna.getPorcentaje();
        int iteraciones = 0;

        Thread vacunaThread = new Thread(() -> vacuna.desarrollarVacuna(valorFloat));
        vacunaThread.start();
        while (counter < 101 && iteraciones < 26) {
            vacunaFinal.setValue(counter);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter += 1;
            iteraciones++;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(middlePanel.getX(), middlePanel.getY());
        Image imagenFondo = new ImageIcon("src//img//mapa2.png").getImage();
        g2d.drawImage(imagenFondo, 0, 0, middlePanel.getWidth(), middlePanel.getHeight(), this);
        g2d.dispose();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == salirButton) {
            setVisible(false);
            menu menu = main.menu.getInstance();
            menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            menu.setVisible(true);
            getParent().add(menu);

            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == nextRoundButton) {
        	System.out.println("");
        	System.out.println("-----------------------------------------------------------");
        	Thread estados = new Thread(() -> actualizarEstadoCiudades());
        	estados.start();
            acciones();
        }
    }
}
