package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import data_managment.Control_de_datos;
import data_managment.Control_de_partida;
import objects.Ciudad;
import objects.Vacunas;
/**
 * @author Eduardo y Marc
 */
public class gameSAVE extends JPanel implements ActionListener {

    private static final long serialVersionUID = 7725219079694206212L;
    
    public static boolean partidaInsertada = false;
    private JButton SubMenuButton;
    private static JButton salirButton;
    private static JButton nextRoundButton;
    
    private static JPanel topPanel;
    private static JPanel leftPanel;
    private JPanel rightPanel;
    private static JPanel bottomPanel;
    private static JPanel middlePanel;

    private static JLabel RoundNumber;
    private static JLabel RoundNumberlabel;
    private static JLabel ActionNumber;
    private static JLabel infectedCitiesLabel;
    private static JLabel infectedValueLabel;
    private static JLabel infectedCitiesGameOverLabel;
    private static JLabel infectedCitiesGameOverLabelnum;
    private static JLabel actionsValueLabel;
    
    private Timer rotationTimer;
    private int angle = 0;

    private static int brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);
    
    private static int identificadorPartida;
    
    private static int[] valoresVacunas = new int[4];

    @SuppressWarnings("static-access")
	public gameSAVE(int identificadorPartida) {
    	this.identificadorPartida = identificadorPartida;
    	
        setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel();
        leftPanel = new JPanel(new GridLayout(brotesvalor, 1, 10, 10));

        rightPanel = new JPanel(new GridBagLayout());
        middlePanel = new JPanel();
        middlePanel.setOpaque(false);
        
        SubMenuButton = new JButton();
        SubMenuButton.addActionListener(this);
        ImageIcon originalIcon = new ImageIcon("src/img/options.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        SubMenuButton.setIcon(resizedIcon);
        Dimension buttonSize = new Dimension(50, 50);
        SubMenuButton.setPreferredSize(buttonSize);
        
        SubMenuButton.setContentAreaFilled(false);
        SubMenuButton.setBorderPainted(false);
        SubMenuButton.setFocusPainted(false);
        SubMenuButton.setOpaque(false);
        
        SubMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startRotation();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopRotation();
            }
        });
        
        topPanel.add(SubMenuButton, BorderLayout.EAST);

        salirButton = new JButton("MENU");
        salirButton.addActionListener(this);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 30));
        topPanel.setBackground(Color.white);

        bottomPanel.setBackground(Color.black);

        vacunasCompletas();

        rightPanel.setBackground(Color.DARK_GRAY.darker());
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
		brotes();

        leftPanel.setBackground(Color.DARK_GRAY.darker());

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);

        datosPartida();
        
        terminal();
        
        nextRoundButton = new JButton("NEXT ROUND");
        nextRoundButton.addActionListener(this);
        nextRoundButton.setBackground(Color.GRAY);
        nextRoundButton.setForeground(Color.BLUE);
        nextRoundButton.setHorizontalAlignment(SwingConstants.CENTER);
        nextRoundButton.setVerticalAlignment(SwingConstants.CENTER);
        nextRoundButton.setOpaque(true);
        nextRoundButton.setFont(info.fuentecargar2(20));
        nextRoundButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 139), 2),
            BorderFactory.createEmptyBorder(7, 15, 0, 15)
        ));

        nextRoundButton.setFocusPainted(false);
        
        nextRoundButton.addMouseListener(new java.awt.event.MouseAdapter() {
            Border hoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 139), 2),
                BorderFactory.createEmptyBorder(7, 15, 0, 15)
            );

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextRoundButton.setBackground(Color.LIGHT_GRAY);
                nextRoundButton.setForeground(Color.CYAN);
                nextRoundButton.setBorder(hoverBorder);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextRoundButton.setBackground(nextRoundButton.getBackground());
                nextRoundButton.setForeground(Color.BLUE);
                nextRoundButton.setBorder(nextRoundButton.getBorder());
            }
        });

        bottomPanel.add(nextRoundButton);

        
        Thread infection = new Thread(() -> startinfection());
        infection.start();
        ciudades();
        actualizarEstadoCiudades();
    }
    
    private void startRotation() {
        rotationTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 5;
                if (angle >= 360) {
                    angle = 0;
                }
                rotateIcon();
            }
        });
        rotationTimer.start();
    }

    private void stopRotation() {
        if (rotationTimer != null) {
            rotationTimer.stop();
        }
        angle = 0;
        ImageIcon originalIcon = new ImageIcon("src/img/options.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        SubMenuButton.setIcon(resizedIcon);
    }

    private void rotateIcon() {
        ImageIcon originalIcon = new ImageIcon("src/img/options.png");
        Image originalImage = originalIcon.getImage();
        int w = originalImage.getWidth(null);
        int h = originalImage.getHeight(null);

        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.rotate(Math.toRadians(angle), w / 2, h / 2);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        ImageIcon rotatedIcon = new ImageIcon(rotatedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        SubMenuButton.setIcon(rotatedIcon);
    }
    
    public static void ciudadesInfect() {
        for (Ciudad ciudad : Control_de_datos.Ciudades) {
            Control_de_partida.infectedcities += ciudad.getInfeccion();
        }
    }

    public static void datosPartida() {
    	
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
    	
        JPanel panelRondas = new JPanel();
        panelRondas.setBackground(Color.BLACK);
        panelRondas.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        ActionNumber = new JLabel("Actions left: ");
        ActionNumber.setForeground(Color.WHITE);
        ActionNumber.setFont(info.fuentecargar2(20));
        
        actionsValueLabel = new JLabel(Integer.toString(Control_de_partida.acciones));
        actionsValueLabel.setForeground(Color.GREEN); 
        actionsValueLabel.setFont(info.fuentecargar2(20));
        
        panelRondas.add(ActionNumber);
        panelRondas.add(actionsValueLabel);
        
        JPanel panelCiudades = new JPanel();
        panelCiudades.setBackground(Color.BLACK);
        panelCiudades.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        ciudadesInfect();

        infectedCitiesLabel = new JLabel("Infected Cities: ");
        infectedCitiesLabel.setForeground(Color.WHITE);
        infectedCitiesLabel.setFont(info.fuentecargar2(20));
        
        infectedValueLabel = new JLabel(Integer.toString(Control_de_partida.infectedcities));
        infectedValueLabel.setForeground(Color.RED); 
        infectedValueLabel.setFont(info.fuentecargar2(20));
        
        panelCiudades.add(infectedCitiesLabel);
        panelCiudades.add(infectedValueLabel);
        
        JPanel panelGameover = new JPanel();
        panelGameover.setBackground(Color.BLACK);
        panelGameover.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        infectedCitiesGameOverLabel = new JLabel("Cities left: ");
        infectedCitiesGameOverLabel.setForeground(Color.WHITE);
        infectedCitiesGameOverLabel.setFont(info.fuentecargar2(20));
        
        infectedCitiesGameOverLabelnum = new JLabel(Integer.toString(Control_de_partida.citiesleft));
        infectedCitiesGameOverLabelnum.setForeground(Color.YELLOW);
        infectedCitiesGameOverLabelnum.setFont(info.fuentecargar2(20));
        
        panelGameover.add(infectedCitiesGameOverLabel);
        panelGameover.add(infectedCitiesGameOverLabelnum);
        
        panel.add(panelRondas, gbc);
        panel.add(panelCiudades, gbc);
        panel.add(panelGameover, gbc);
        
        bottomPanel.add(panel, BorderLayout.WEST);
        
        RoundNumberlabel = new JLabel("Round: ");
        RoundNumberlabel.setForeground(Color.WHITE);
        RoundNumberlabel.setFont(info.fuentecargar2(50));
        
        RoundNumber = new JLabel(Integer.toString(Control_de_partida.turno));
        RoundNumber.setForeground(Color.GRAY);
        RoundNumber.setFont(info.fuentecargar2(50));
        
        JPanel panelRounds = new JPanel();
        panelRounds.setBackground(Color.DARK_GRAY);
        
        panelRounds.add(RoundNumberlabel);
        panelRounds.add(RoundNumber);
        
        topPanel.add(panelRounds, SwingUtilities.VERTICAL);
        topPanel.setBackground(Color.DARK_GRAY);
    }

    
    public static void actualizarEstadoCiudades() {
        Color verdeSuave = new Color(144, 238, 144);

        for (Component component : middlePanel.getComponents()) {
            if (component instanceof RoundButton) {
            	RoundButton button = (RoundButton) component;
                String cityName = button.getName();
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
        RoundNumber.setText(Integer.toString(Control_de_partida.turno));
        actionsValueLabel.setText(Integer.toString(Control_de_partida.acciones));
        infectedValueLabel.setText(Integer.toString(Control_de_partida.infectedcities));
        infectedCitiesGameOverLabelnum.setText(Integer.toString(Control_de_partida.citiesleft));

        perfectwin();
        GameOver();
    }
    
    public static void perfectwin() {
    	Control_de_partida.numvaccom = 0;
        for (objects.Vacunas vacuna : Control_de_datos.Vacuna) {
        	if (vacuna.getPorcentaje() > 99) {
        		Control_de_partida.numvaccom++;
            }
        }
        Victory();
    }
    
    public static void Victory() {
    	if (Control_de_partida.infectedcities == 0 && Control_de_partida.turno != 1 || Control_de_partida.numvaccom == 4) {
        	Control_de_partida.resultado = "Victory";
        	
        	Control_de_datos.insertarRanking();
        	
        	JLabel victoryLabel = new JLabel("VICTORY");
            victoryLabel.setForeground(Color.GREEN);
            victoryLabel.setFont(new Font("Arial", Font.BOLD, 150));
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
                	borrarPartidaTerminada();
                }
            });
            timer.setRepeats(false);

            timer.start();
        }
    	
    }
    
    public static void GameOver() {
        if (Control_de_partida.outbreak == Integer.parseInt(Control_de_datos.NumBrotesDerrota) || Control_de_partida.infectedcities == Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota)) {
        	Control_de_partida.resultado = "Defeat";
        	
        	Control_de_datos.insertarRanking();
        	
        	JLabel victoryLabel = new JLabel("DEFEAT");
            victoryLabel.setForeground(Color.RED);
            victoryLabel.setFont(new Font("Arial", Font.BOLD, 150));
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
                	borrarPartidaTerminada();
                }
            });
            timer.setRepeats(false);

            timer.start();
        }
    }
    
    private static void borrarPartidaTerminada() {
        Control_de_datos.borrarPartida(identificadorPartida);
        gameSAVE.salirButton.doClick();
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
            RoundButton ciudad = new RoundButton("");
            ciudad.setName(ciudades.getNombre());
            int x = (int) (coordenadas[0] * resol[0]);
            int y = (int) (coordenadas[1] * resol[1]);
            Dimension size = ciudad.getPreferredSize();
            ciudad.setBounds(x, y, size.width, size.height);
            
            Color borderColor = obtenerColorBorde(ciudades.getEnfermedad());
            ciudad.setBorderColor(borderColor);
            
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
                int NumEn = Integer.parseInt(ciudades.getEnfermedad());
                if (NumEn >= 0 && NumEn < Control_de_datos.Vacuna.size()) {
                    
                    Vacunas vacuna = Control_de_datos.Vacuna.get(NumEn);

                    if (vacuna.getPorcentaje() > 99) {
                        Thread curar = new Thread(() -> ciudades.disminuirInfeccionConVacuna());
                        curar.start();
                        System.out.println("Name: " + ciudades.getNombre() + " | Infection: Healed");
                        vacunaUtilizada = true;
                    }
                }
                
                if (!vacunaUtilizada) {
                    ciudades.disminuirInfeccion();
                    System.out.println("Name: " + ciudades.getNombre() + " | Infection: " + ciudades.getInfeccion());
                }
                
                actualizarEstadoCiudades();
                
            });
            
            middlePanel.add(ciudad);
            String nombreCiudad = ciudades.getNombre();
            new Thread(() -> ciudad.setToolTipText(nombreCiudad)).start();
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

        Thread printInfection = new Thread(() -> {
            Control_de_partida.gestionarInfeccion2();

            SwingUtilities.invokeLater(() -> {
                nextRoundButton.setEnabled(true);
            });
        });

        printInfection.start();

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
                    Thread.sleep(5);
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
        nextRoundButton.setEnabled(true);
    } 
    
    public static void brotes() {
        leftPanel.removeAll();

        for (int i = 0; i < brotesvalor; i++) {
            String imagePath;
            if (i < Control_de_partida.outbreak) {
                imagePath = "src/img/brote_activo.png";
            } else {
                imagePath = "src/img/brote_inactivo.png";
            }
            
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);

            JLabel brote = new JLabel(scaledIcon);
            brote.setPreferredSize(new Dimension(75, 75));
            leftPanel.add(brote);
        }

        leftPanel.revalidate();
        leftPanel.repaint();
    }

    public void vacunasCompletas() {
    	int i = 0;
    	for (Vacunas vacunas : Control_de_datos.Vacuna) {
    		valoresVacunas[i] = (int) vacunas.getPorcentaje();
    		i++;
		}
        vacunas("Alfa", new Color(118, 189, 248), "ALFA");
        vacunas("Beta", new Color(248, 118, 118), "BETA");
        vacunas("Gama", new Color(118, 248, 150), "GAMMA");
        vacunas("Delta", new Color(236, 248, 118), "DELTA");
    }

    public static float valorFloat = (float) 25;

    public void vacunas(String nombre, Color color, String labelText) {
        final JProgressBar vacunaFinal = new JProgressBar();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, -25, 0, 5);

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
        
        if (nombre.equalsIgnoreCase("Alfa")) {
        	vacunaFinal.setValue(valoresVacunas[0]);
		}else if (nombre.equalsIgnoreCase("Beta")) {
			vacunaFinal.setValue(valoresVacunas[1]);
		}else if (nombre.equalsIgnoreCase("Gama")) {
			vacunaFinal.setValue(valoresVacunas[2]);
		}else {
			vacunaFinal.setValue(valoresVacunas[3]);
		}
        
        vacunaFinal.setStringPainted(true);
        vacunaFinal.setOpaque(false);
        vacunaFinal.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 30));

        vacunaFinal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Control_de_partida.acciones == 4) {
                    Control_de_partida.acciones = 0;
                    actionsValueLabel.setText(Integer.toString(Control_de_partida.acciones));
                    switch (nombre) {
                        case "Alfa":
                            Thread VacunaAlfa = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(0), vacunaFinal));
                            VacunaAlfa.start();
                            break;
                        case "Beta":
                            Thread VacunaBeta = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(1), vacunaFinal));
                            VacunaBeta.start();
                            break;
                        case "Gama":
                            Thread VacunaGamma = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(2), vacunaFinal));
                            VacunaGamma.start();
                            break;
                        case "Delta":
                            Thread VacunaDelta = new Thread(() -> desarrolloVacunas(Control_de_datos.Vacuna.get(3), vacunaFinal));
                            VacunaDelta.start();
                            break;
                    }
                } else {
                    Thread respuesta = new Thread(() -> System.out.println("You don't have enough actions to perform this action."));
                    respuesta.start();
                }
            }
        });

        vacunaFinal.setForeground(color);
        vacunaFinal.setName(nombre);

        JLabel label = new JLabel(labelText);
        label.setForeground(color);
        label.setFont(info.fuentecargar2(20));

        rightPanel.add(label, gbc);
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
        perfectwin();
    }

    private void MenuPopup() {
        JPopupMenu popupMenu = new JPopupMenu();
        
        JMenuItem infoMenuItem = new JMenuItem("INFORMATION");
        JMenuItem saveMenuItem = new JMenuItem("SAVE");
        JMenuItem savequitMenuItem = new JMenuItem("SAVE AND QUIT");
        JMenuItem exitMenuItem = new JMenuItem("QUIT");
        
        Font font = new Font("Arial", Font.BOLD, 13);
        infoMenuItem.setFont(font);
        saveMenuItem.setFont(font);
        savequitMenuItem.setFont(font);
        exitMenuItem.setFont(font);
        
        infoMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
        saveMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
        savequitMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
        exitMenuItem.setHorizontalAlignment(SwingConstants.CENTER);

        infoMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StateManager.setPreviousClass(gameSAVE.class);
            	
                info informacion = info.getInstance();
                informacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                informacion.setVisible(true);
                getParent().add(informacion);
                getParent().revalidate();
                getParent().repaint();
            }
        });
        
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Control_de_datos.actualizarPartida();
                
                popupMenu.setVisible(false);
            }
        });
        
        savequitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Control_de_datos.actualizarPartida();
                
                salirButton.doClick();
            }
        });
        
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salirButton.doClick();
            }
        });
        
        popupMenu.add(infoMenuItem);
        popupMenu.add(saveMenuItem);
        popupMenu.add(savequitMenuItem);
        popupMenu.add(exitMenuItem);
        
        infoMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                info informacion = info.getInstance();
                informacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                setVisible(false);

                Container parent = getParent();
                parent.add(informacion);
                parent.setComponentZOrder(informacion, 0);
                informacion.setVisible(true);
                parent.revalidate();
                parent.repaint();
            }
        });

        
        popupMenu.show(SubMenuButton, -55, 0);
    }

    public static gameSAVE instance;

    public static gameSAVE getInstance() {
        if (instance == null) {
            instance = new gameSAVE(identificadorPartida);
        }
        return instance;
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
            System.out.println("-----------------------------------------------------------");
            Thread estados = new Thread(() -> actualizarEstadoCiudades());
            estados.start();
            acciones();
        } else if (e.getSource() == SubMenuButton) {
        	MenuPopup();
        }
    }
    
    public class RoundButton extends JButton {
        private static final long serialVersionUID = -361492780559381172L;
        private Color borderColor;

        public RoundButton(String label) {
            super(label);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int diameter = Math.min(getWidth(), getHeight());
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            if (getModel().isArmed()) {
                g2.setColor(Color.lightGray);
            } else {
                g2.setColor(getBackground());
            }

            g2.fillOval(x, y, diameter, diameter);
            g2.dispose();

            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int diameter = Math.min(getWidth(), getHeight());
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            g2.setColor(borderColor != null ? borderColor : getForeground());
            g2.drawOval(x, y, diameter - 1, diameter - 1);
            g2.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            int diameter = Math.min(getWidth(), getHeight());
            int radius = diameter / 2;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            Ellipse2D ellipse = new Ellipse2D.Double(centerX - radius, centerY - radius, diameter, diameter);
            return ellipse.contains(x, y);
        }

        @Override
        public Dimension getPreferredSize() {
            int diameter = Math.max(super.getPreferredSize().width, super.getPreferredSize().height);
            return new Dimension(diameter, diameter);
        }
    }
}
