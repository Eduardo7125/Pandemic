package main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import data_managment.Control_de_datos;
import data_managment.Control_de_partida;
import objects.Vacunas;

public class game extends JPanel implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 7725219079694206212L;

    private ImageIcon background;
    
    private JPanel popupPanel;
    
    private JButton exitButton;
    private JButton SubMenuButton;
    private static JButton salirButton;
    private static JButton salirButton2;
    private static JButton nextRoundButton;
    private static JDialog popupDialog;
    
    private JPanel topPanel;
    private static JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private static JPanel middlePanel;

    private JLabel LabelImagen;
    private JLabel menuLabel1;
    private JLabel brotes;    
    private static JLabel RoundNumber;
    private static JLabel ActionNumber;
    private static JLabel infectedCitiesLabel;
    private static JLabel infectedCitiesGameOverLabel;

    private JProgressBar vacunas;

    static int brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);

    game() {
        setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel();
        leftPanel = new JPanel(new GridLayout(brotesvalor, 0, 10, 10));
        rightPanel = new JPanel(new GridBagLayout());
        middlePanel = new JPanel();
        
        SubMenuButton = new JButton("MENU");
        SubMenuButton.addActionListener(this);
        topPanel.add(SubMenuButton, BorderLayout.EAST);

        salirButton = new JButton("MENU");
        salirButton.addActionListener(this);
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
        
        ActionNumber = new JLabel("Actions left: " + Control_de_partida.acciones);
        ActionNumber.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-660, 45, 5, 5);
        rightPanel.add(ActionNumber, gbc);
        
        infectedCitiesLabel = new JLabel("Infected Cities: " + Control_de_partida.infectedcities);
        infectedCitiesLabel.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-630, 45, 5, 5);
        rightPanel.add(infectedCitiesLabel, gbc);
        
        infectedCitiesGameOverLabel = new JLabel("Cities left: " + Control_de_partida.citiesleft);
        infectedCitiesGameOverLabel.setForeground(Color.WHITE);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-600, 45, 5, 5);
        rightPanel.add(infectedCitiesGameOverLabel, gbc);
        RoundNumber.setText("Round: " + Control_de_partida.turno);
        
        JLabel labelAlfa = new JLabel("ALFA");
        labelAlfa.setForeground(new Color(0, 128, 255));
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-455, 71, 5, 5);
        rightPanel.add(labelAlfa, gbc);

        JLabel labelBeta = new JLabel("BETA");
        labelBeta.setForeground(Color.RED);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-340, 72, 5, 5);
        rightPanel.add(labelBeta, gbc);

        JLabel labelGama = new JLabel("GAMMA");
        labelGama.setForeground(Color.GREEN);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-220, 67, 5, 5);
        rightPanel.add(labelGama, gbc);

        JLabel labelDelta = new JLabel("DELTA");
        labelDelta.setForeground(Color.YELLOW);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(-105, 70, 5, 5);
        rightPanel.add(labelDelta, gbc);

        
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
        ActionNumber.setText("Actions left: " + Control_de_partida.acciones);
        infectedCitiesLabel.setText("Infected Cities: " + Control_de_partida.infectedcities);
        infectedCitiesGameOverLabel.setText("Cities left: " + Control_de_partida.citiesleft);

        Victory();
        GameOver();
    }

    public static void Victory() {
        if (Control_de_partida.infectedcities == 0) {
        	Control_de_partida.resultado = "Victory";
        	
        	Control_de_datos.insertarPartida();
            
        	JLabel victoryLabel = new JLabel("YOU HAVE WON");
            victoryLabel.setForeground(Color.GREEN);
            victoryLabel.setFont(new Font("Arial", Font.BOLD, 250));
            victoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            victoryLabel.setVerticalAlignment(SwingConstants.CENTER);
            victoryLabel.setOpaque(true);
            victoryLabel.setBackground(Color.WHITE);
            victoryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            middlePanel.removeAll();
            middlePanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            middlePanel.add(victoryLabel, gbc);

            middlePanel.revalidate();
            middlePanel.repaint();

            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	System.exit(0);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    public static void GameOver() {
        if (Control_de_partida.outbreak == Integer.parseInt(Control_de_datos.NumBrotesDerrota) || Control_de_partida.infectedcities == Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota)) {
        	Control_de_partida.resultado = "Defeat";
        	
        	Control_de_datos.insertarPartida();
        	
        	JLabel victoryLabel = new JLabel("GAME OVER");
            victoryLabel.setForeground(Color.RED);
            victoryLabel.setFont(new Font("Arial", Font.BOLD, 250));
            victoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            victoryLabel.setVerticalAlignment(SwingConstants.CENTER);
            victoryLabel.setOpaque(true);
            victoryLabel.setBackground(Color.WHITE);
            victoryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            middlePanel.removeAll();
            middlePanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            middlePanel.add(victoryLabel, gbc);

            middlePanel.revalidate();
            middlePanel.repaint();
            
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	System.exit(0);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
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
            JButton ciudad = new JButton(ciudades.getNombre());
            int x = (int) (coordenadas[0] * resol[0]);
            int y = (int) (coordenadas[1] * resol[1]);
            Dimension size = ciudad.getPreferredSize();
            ciudad.setBounds(x, y, size.width, size.height);
            
            
            ciudad.addActionListener(e -> {
                
                if (Control_de_partida.turno == 1) {                    
                    System.out.println("You can't heal cities in round 1");
                    return;                    
                }
                
                if (Control_de_partida.acciones <= 0) {
                    System.out.println("You don't have enough actions to perform this action.");
                    return;                   
                }
                
                boolean vacunaUtilizada = false;
                
                for (objects.Vacunas vacunas : Control_de_datos.Vacuna) {
                    for (objects.Virus virus : Control_de_datos.Virus) {
                        if (vacunas.getNombre().equalsIgnoreCase(virus.getNombre())) {
                            if (vacunas.getPorcentaje() > 99) {
                                Thread curar = new Thread(() -> ciudades.disminuirInfeccionConVacuna(virus.getIdentificador()));
                                curar.start();
                                System.out.println("Name: " + ciudades.getNombre() + " | Infection: Curada");
                                vacunaUtilizada = true;
                            }
                            break;
                        }
                    }
                }
                
                if (!vacunaUtilizada) {
                    ciudades.disminuirInfeccion();
                    System.out.println("Name: " + ciudades.getNombre() + " | Infection: " + ciudades.getInfeccion());
                }
                
                actualizarEstadoCiudades();
                
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

        String imagePath = (Control_de_partida.outbreak > 0 && brotesvalor > Control_de_partida.outbreak)
                ? "src/img/brote_activo.png"
                : "src/img/brote_inactivo.png";

        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        for (int i = 0; i < brotesvalor; i++) {
            JLabel brote = new JLabel(scaledIcon);
            brote.setPreferredSize(new Dimension(75, 75));
            leftPanel.add(brote);
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
                    ActionNumber.setText("Actions left: " + Control_de_partida.acciones);
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
    
    private void MenuPopup() {
        if (popupDialog == null) {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            popupDialog = new JDialog(mainFrame, "OPTIONS MENU", Dialog.ModalityType.APPLICATION_MODAL);
            popupDialog.setSize(200, 200);
            popupDialog.setLocationRelativeTo(mainFrame);
            popupDialog.setResizable(false);

            
            popupPanel = new JPanel();
            popupPanel.setLayout(new GridLayout(2, 1));

            
            exitButton = new JButton("SAVE");
            salirButton2 = new JButton("MENU");

            
            exitButton.addActionListener(this);
            salirButton2.addActionListener(this);
            
            
            popupPanel.add(exitButton);     
            popupPanel.add(salirButton2);
            popupDialog.add(popupPanel);
            
        }

        popupDialog.setVisible(true);
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
        	popupDialog.dispose();
            setVisible(false);
            menu menu = main.menu.getInstance();
            menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            menu.setVisible(true);
            getParent().add(menu);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == nextRoundButton) {
            System.out.println("-----------------------------------------------------------");
            Thread estados = new Thread(() -> actualizarEstadoCiudades());
            estados.start();
            acciones();
        } else if (e.getSource() == SubMenuButton) {
        	MenuPopup();
        } else if (e.getSource() == salirButton2) {
        	popupDialog.dispose();
        	salirButton.doClick();
        } else if (e.getSource() == exitButton){
			Control_de_datos.insertarPartida();
        	popupDialog.dispose();
        	salirButton.doClick();
        }
    }
}