package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data_managment.Control_de_datos;
import data_managment.Control_de_partida;
import objects.Ciudad;

public class menu extends JPanel implements ActionListener {

    @Serial
    private static final long serialVersionUID = -5124796854119688429L;
    
    private ImageIcon icono;
    private ImageIcon iconoNuevaPartida;
    private ImageIcon iconoNuevaPartida2;
    private ImageIcon iconoCargarPartida;
    private ImageIcon iconoCargarPartida2;
    private ImageIcon iconoInfo;
    private ImageIcon iconoInfo2;
    private ImageIcon iconoScore;
    private ImageIcon iconoScore2;
    private ImageIcon iconoSalir;
    private ImageIcon iconoSalir2;
    private static Image nuevaImagen;
    private static Image nuevaImagen2;
    private static Image imagen_botones;

    private JPopupMenu dificultad;
    private JMenuItemMenuItemPersonalizado facilItem;
    private JMenuItemMenuItemPersonalizado medioItem;
    private JMenuItemMenuItemPersonalizado dificilItem;

    private JPanel buttonPanel;
    private JPanel bottomPanel;

    private JLabel version;
    private JLabel menuLabel1;

    private JButton searchButton;
    private JButton nuevaPartidaButton;
    private JButton cargarPartidaButton;
    private JButton informacionButton;
    private JButton rankingButton;
    private static JButton salirButton;
    private JButton atrasButton;
    
    private JPanel playerNamePanel;
    
    private JPopupMenu playerNamePopup;
    private JTextField playerNameTextField;
    private JButton okButton;

    menu() {
        Control_de_datos.conectarBaseDatos();

        setLayout(new BorderLayout());

        dificultad = new JPopupMenu();

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

        menuLabel1 = new JLabel(
                "<html><div style='text-align:center;'><h1 style='font-size: 35px;'>PANDEMIC</h1><h2 style='font-size: 24px;'>MENÚ PRINCIPAL</h2><img src='file:src//img//icono_escalado.png'></div>");

        menuLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        add(menuLabel1, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.CENTER);

        botonesCreacion();
        initializePopupMenu();

        add(buttonPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        version = new JLabel(
                "<html><font color='white'><p>Eduardo/Marc</p><div style='text-align:center;'><font color='white'><p>Version 1.0</p></div></font></html>");

        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        searchButton = new JButton("SEARCH");
        searchButton.addActionListener(this);
    }

    public void botonesCreacion() {
        botones();

        buttonPanel.add(nuevaPartidaButton);
        buttonPanel.add(cargarPartidaButton);
        buttonPanel.add(informacionButton);
        buttonPanel.add(rankingButton);
        buttonPanel.add(salirButton);
    }

    public void botones() {
        imageBotones(iconoNuevaPartida, iconoNuevaPartida2);
        nuevaPartidaButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
        imageBotones(iconoCargarPartida, iconoCargarPartida2);
        cargarPartidaButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
        imageBotones(iconoInfo, iconoInfo2);
        informacionButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
        imageBotones(iconoScore, iconoScore2);
        rankingButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
        imageBotones(iconoSalir, iconoSalir2);
        salirButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
    }

    public static void imageBotones(ImageIcon icono, ImageIcon icono2) {
        imagen_botones = icono.getImage();
        nuevaImagen = imagen_botones.getScaledInstance(450, 330, Image.SCALE_SMOOTH);
        imagen_botones = icono2.getImage();
        nuevaImagen2 = imagen_botones.getScaledInstance(450, 330, Image.SCALE_SMOOTH);
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
        	Control_de_partida.playername = null;
            showPlayerNamePopup();
        } else if (e.getSource() == cargarPartidaButton) {
            setVisible(false);
            Control_de_datos.disconnect();
            Control_de_datos.conectarBaseDatos();

            Control_de_datos.selectParidas();

            loadgame loadgame = new loadgame();
            loadgame.setVisible(true);
            getParent().add(loadgame);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == searchButton) {
            setVisible(false);
            Control_de_datos.disconnect();
            Control_de_datos.conectarBaseDatos();

            Control_de_datos.selectRanking();

            loadgame loadgame = new loadgame();
            loadgame.setVisible(true);
            getParent().add(loadgame);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == informacionButton) {
            setVisible(false);

            info informacion = info.getInstance();
            informacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            informacion.setVisible(true);
            getParent().add(informacion);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == rankingButton) {
            setVisible(false);
            Control_de_datos.disconnect();
            Control_de_datos.conectarBaseDatos();

            Control_de_datos.selectRanking();

            Ranking ranking = new Ranking();
            ranking.setVisible(true);
            getParent().add(ranking);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        } else if (e.getSource() == facilItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosFacil.xml";
            Control_de_datos.cargarPartida();
            resetvalores();
            iniciarNuevaPartida();
        } else if (e.getSource() == medioItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosMedio.xml";
            Control_de_datos.cargarPartida();
            resetvalores();
            iniciarNuevaPartida();
        } else if (e.getSource() == dificilItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosDificil.xml";
            Control_de_datos.cargarPartida();
            resetvalores();
            iniciarNuevaPartida();
        } else if (e.getSource() == atrasButton) {
            dificultad.remove(atrasButton);
            atrasButton = null;
            dificultad.setVisible(false);
        } else if (e.getSource() == okButton) {
            String playerName = playerNameTextField.getText();
            Control_de_partida.playername = playerName;
            hidePlayerNamePopup();
            if (atrasButton == null) {
                atrasButton = createButton(new ImageIcon("src//img//atras.png"), new ImageIcon("src//img//atras2.png"));
                dificultad.add(atrasButton);
                dificultad.repaint();
                 
                atrasButton.addActionListener(this);
            }
            
            int x = nuevaPartidaButton.getWidth() / 2 - dificultad.getPreferredSize().width / 2;
            int y = nuevaPartidaButton.getHeight() / 2 + nuevaPartidaButton.getHeight() + 90
                    - dificultad.getPreferredSize().height / 2;
            
            x += nuevaPartidaButton.getLocationOnScreen().x;
            y += nuevaPartidaButton.getLocationOnScreen().y;
            dificultad.show(menu.this, x, y);
        }
    }

    public void resetvalores() {
    	game.partidaInsertada = false;
        Ciudad.resetValues();
        game.brotesStart();
        Control_de_partida.resultado = null;
        Control_de_partida.turno = 1;
        Control_de_partida.acciones = 4;
        Control_de_partida.outbreak = 0;
        Control_de_partida.infectedcities = 0;
        Control_de_partida.citiesleft = Integer.parseInt(Control_de_datos.EnfermedadesActivasDerrota);
        game.brotesvalor = Integer.parseInt(Control_de_datos.NumBrotesDerrota);
    }

    public void iniciarNuevaPartida() {
        setVisible(false);
        dificultad.setVisible(false);
        game juego = new game();
        juego.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        juego.setVisible(true);
        game.actualizarEstadoCiudades();
        getParent().add(juego);
        getParent().revalidate();
        getParent().repaint();
    }

    @SuppressWarnings("serial")
    private void initializePopupMenu() {
        dificultad = new JPopupMenu() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                Image imagenFondo = new ImageIcon("src//img//fondo_dificultad.png").getImage();
                g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };

        Font font = new Font("Arial", Font.PLAIN, 30);

        ImageIcon facilIcon1 = new ImageIcon(
                new ImageIcon("src//img//easy.png").getImage().getScaledInstance(84, 67, Image.SCALE_SMOOTH));
        ImageIcon facilIcon2 = new ImageIcon(
                new ImageIcon("src//img//easy2.png").getImage().getScaledInstance(84, 67, Image.SCALE_SMOOTH));
        facilItem = new JMenuItemMenuItemPersonalizado("        EASY", facilIcon1);
        facilItem.setBorder(BorderFactory.createEmptyBorder(15, 140, -40, 0));
        facilItem.addActionListener(this);
        facilItem.setOpaque(false);
        facilItem.setForeground(Color.WHITE);
        facilItem.setFont(font);
        dificultad.add(facilItem);

        ImageIcon medioIcon1 = new ImageIcon(
                new ImageIcon("src//img//medio.png").getImage().getScaledInstance(120, 95, Image.SCALE_SMOOTH));
        ImageIcon medioIcon2 = new ImageIcon(
                new ImageIcon("src//img//medio2.png").getImage().getScaledInstance(120, 95, Image.SCALE_SMOOTH));
        medioItem = new JMenuItemMenuItemPersonalizado("          NORMAL", medioIcon1);
        medioItem.setBorder(BorderFactory.createEmptyBorder(0, 125, -20, 0));
        medioItem.addActionListener(this);
        medioItem.setOpaque(false);
        medioItem.setForeground(Color.WHITE);
        medioItem.setFont(font);
        dificultad.add(medioItem);

        ImageIcon dificilIcon1 = new ImageIcon(
                new ImageIcon("src//img//hard.png").getImage().getScaledInstance(190, 140, Image.SCALE_SMOOTH));
        ImageIcon dificilIcon2 = new ImageIcon(
                new ImageIcon("src//img//hard2.png").getImage().getScaledInstance(190, 140, Image.SCALE_SMOOTH));
        dificilItem = new JMenuItemMenuItemPersonalizado("              HARD", dificilIcon1);
        dificilItem.setBorder(BorderFactory.createEmptyBorder(-20, 97, -20, 0));
        dificilItem.addActionListener(this);
        dificilItem.setOpaque(false);
        dificilItem.setForeground(Color.WHITE);
        dificilItem.setFont(font);
        dificultad.add(dificilItem);

        Timer timer = new Timer(1000, new ActionListener() {
            boolean alternate = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (alternate) {
                    facilItem.setIcon(facilIcon1);
                    medioItem.setIcon(medioIcon1);
                    dificilItem.setIcon(dificilIcon1);
                } else {
                    facilItem.setIcon(facilIcon2);
                    medioItem.setIcon(medioIcon2);
                    dificilItem.setIcon(dificilIcon2);
                }
                alternate = !alternate;
            }
        });
        timer.start();
        dificultad.setOpaque(false);
        dificultad.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        dificultad.setPreferredSize(new Dimension(1000, 550));
    }

    private void showPlayerNamePopup() {
        playerNamePopup = new JPopupMenu();
        playerNamePopup.setLayout(new BorderLayout());
        playerNamePopup.setBackground(Color.BLUE);

        JLabel playerNameLabel = new JLabel("PLAYER NAME");
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        playerNamePopup.add(playerNameLabel, BorderLayout.NORTH);

        playerNameTextField = new JTextField(12);
        playerNameTextField.setFont(new Font("Arial", Font.PLAIN, 25));
        playerNameTextField.addActionListener(this);
        playerNameTextField.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    okButton.doClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(playerNameTextField.getText().isEmpty()) {
                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        playerNamePopup.add(playerNameTextField, BorderLayout.CENTER);
        playerNameTextField.requestFocusInWindow();

        okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.PLAIN, 20));
        okButton.addActionListener(this);
        okButton.setEnabled(false); // El botón se inicia deshabilitado
        playerNamePopup.add(okButton, BorderLayout.SOUTH);


        int x = (getWidth() - playerNamePopup.getPreferredSize().width) / 2;
        int y = (getHeight() - playerNamePopup.getPreferredSize().height) / 2;
        
        playerNamePopup.show(this, x, y);
    }


    private void hidePlayerNamePopup() {
        playerNamePopup.setVisible(false);
    }
    private static menu instance;

    public static menu getInstance() {
        if (instance == null) {
            instance = new menu();
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

class JMenuItemMenuItemPersonalizado extends JMenuItem {
    /**
     * 
     */
    private static final long serialVersionUID = -9166676475889455823L;

    public JMenuItemMenuItemPersonalizado(String texto, ImageIcon facilIcon) {
        super(texto);
        setIcon((Icon) facilIcon);
        setHorizontalTextPosition(SwingConstants.RIGHT);
    }
}
