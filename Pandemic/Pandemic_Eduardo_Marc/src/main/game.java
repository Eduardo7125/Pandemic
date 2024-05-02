package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.Timer;

import data_managment.Control_de_datos;
import objects.Vacunas;

class game extends JPanel implements ActionListener {
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

	private static game instance;
	int brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);
	
	game(){
		
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
		
		terminal();
		bottomPanel.setBackground(Color.black);
		
		vacunasCompletas();
		
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
	
	public void terminal() {
		JTextArea texto = new JTextArea(7, 50);
		texto.setEditable(false);
        texto.setFont(new Font("Arial", Font.BOLD, 14));
        texto.setForeground(Color.BLUE);
        texto.setBackground(Color.LIGHT_GRAY);
        PrintStream printStream = new PrintStream(new OutputStream() {
        	@Override
            public void write(int b) throws IOException {
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
            }
        });

        System.setOut(printStream);
        System.setErr(printStream);
        texto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bottomPanel.add(texto, BorderLayout.CENTER);
	}
	
	public void brotes() {
		ImageIcon icono = new ImageIcon("src//img//brote_inactivo.png");
        Image imagen = icono.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagen);
		for (int i = 0; i < brotesvalor; i++) {
			brotes = new JLabel(iconoEscalado);
			brotes.setSize(75, 75);
			leftPanel.add(brotes);
		}
	}
	
	public void vacunasCompletas() {
		vacunas("Alfa", new Color(118, 189, 248));
		vacunas("Beta", new Color(248, 118, 118));
		vacunas("Gama", new Color(118, 248, 150));
		vacunas("Delta", new Color(236, 248, 118));
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
                switch (nombre) {
                    case "Alfa":
                    	desarrolloVacunas(Control_de_datos.Vacuna.get(0) , vacunaFinal);
                        break;
                    case "Beta":
                    	desarrolloVacunas(Control_de_datos.Vacuna.get(1) , vacunaFinal);
                        break;
                    case "Gama":
                    	desarrolloVacunas(Control_de_datos.Vacuna.get(2) , vacunaFinal);
                        break;
                    case "Delta":
                    	desarrolloVacunas(Control_de_datos.Vacuna.get(3) , vacunaFinal);
                        break;
                }
            }
        });

		vacunaFinal.setForeground(color);
		vacunaFinal.setName(nombre);
		rightPanel.add(vacunaFinal, gbc);
			 
	}
	
    public void desarrolloVacunas(Vacunas vacuna, JProgressBar vacunaFinal) {
    	
    	new Thread(() -> {
    	    int counter = (int) vacuna.getPorcentaje();
    	    vacuna.desarrollarVacuna(valorFloat);
    	    int iteraciones = 0;

    	    while (counter < 101 && iteraciones < 21) {
    	        vacunaFinal.setValue(counter);
    	        try {
    	            Thread.sleep(30);
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
    	        counter += 1;
    	        iteraciones++;
    	    }
    	}).start();
        
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
	        menu menu = main.menu.getInstance();
	        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	        menu.setVisible(true);
	        getParent().add(menu);
	        
	        getParent().revalidate();
	        getParent().repaint();
		}
		
	}
    public static game getInstance() {
        if (instance == null) {
            instance = new game();
        }
        return instance;
    }
}