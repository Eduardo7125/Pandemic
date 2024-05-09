package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import data_managment.Control_de_datos;
import objects.Ciudad;

public class menu extends JPanel implements ActionListener {

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
    static Image nuevaImagen2;
    static Image imagen_botones;

    JPopupMenu dificultad;
    JMenuItemMenuItemPersonalizado facilItem;
    JMenuItemMenuItemPersonalizado medioItem;
    JMenuItemMenuItemPersonalizado dificilItem;

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
    JButton atrasButton;

    Marco marco;

    menu() {
        setLayout(new BorderLayout());

        dificultad = new JPopupMenu();

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
                "<html><div style='text-align:center;color: white;'><p>Eduardo/Marc</p><p>Version 1.0</p></div>");

        bottomPanel = new JPanel();
        JLabel version = new JLabel(
                "<html><font color='white'><p>Euardo/Marc</p><div style='text-align:center;'><font color='white'><p>Version 1.0</p></div></font></html>");

        bottomPanel.add(version);
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
    }

    public void botonesCreacion() {
        botones();

        buttonPanel.add(nuevaPartidaButton);
        buttonPanel.add(cargarPartidaButton);
        buttonPanel.add(informacionButton);
        buttonPanel.add(resumenButton);
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
        resumenButton = createButton(new ImageIcon(nuevaImagen), new ImageIcon(nuevaImagen2));
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
            if (atrasButton == null) {
                atrasButton = createButton(new ImageIcon("src//img//atras.png"), new ImageIcon("src//img//atras2.png"));
                dificultad.add(atrasButton);
                dificultad.repaint();
                 
                atrasButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dificultad.remove(atrasButton);
                        atrasButton = null; 
                        dificultad.setVisible(false);          
                    }
                });
            }
            
            int x = nuevaPartidaButton.getWidth() / 2 - dificultad.getPreferredSize().width / 2;
            int y = nuevaPartidaButton.getHeight() / 2 + nuevaPartidaButton.getHeight() + 90
                    - dificultad.getPreferredSize().height / 2;
            
            x += nuevaPartidaButton.getLocationOnScreen().x;
            y += nuevaPartidaButton.getLocationOnScreen().y;

            dificultad.show(menu.this, x, y);
        } else if (e.getSource() == cargarPartidaButton) {
            System.exit(0);
        } else if (e.getSource() == informacionButton) {
            setVisible(false);

            info informacion = info.getInstance();
            informacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            informacion.setVisible(true);
            getParent().add(informacion);
            getParent().revalidate();
            getParent().repaint();
        } else if (e.getSource() == resumenButton) {
            System.exit(0);
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        } else if (e.getSource() == facilItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosFacil.xml";
            Control_de_datos.cargarPartida();
            iniciarNuevaPartida();
        } else if (e.getSource() == medioItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosMedio.xml";
            Control_de_datos.cargarPartida();
            iniciarNuevaPartida();
        } else if (e.getSource() == dificilItem) {
            Control_de_datos.ficheroXML = "src//files//parametrosDificil.xml";
            Control_de_datos.cargarPartida();
            iniciarNuevaPartida();
        }

    }

    private void iniciarNuevaPartida() {
        setVisible(false);
        dificultad.setVisible(false);
        Ciudad.resetValues();
        game juego = new game();
        juego.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        juego.setVisible(true);
        game.actualizarEstadoCiudades();
        getParent().add(juego);
        getParent().revalidate();
        getParent().repaint();
    }

    private void initializePopupMenu() {
        dificultad = new JPopupMenu();

        ImageIcon facilIcon1 = new ImageIcon(
                new ImageIcon("src//img//easy.png").getImage().getScaledInstance(84, 67, Image.SCALE_SMOOTH));
        ImageIcon facilIcon2 = new ImageIcon(
                new ImageIcon("src//img//easy2.png").getImage().getScaledInstance(84, 67, Image.SCALE_SMOOTH));
        facilItem = new JMenuItemMenuItemPersonalizado("EASY", facilIcon1);
        facilItem.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        facilItem.addActionListener(this);
        dificultad.add(facilItem);

        ImageIcon medioIcon1 = new ImageIcon(
                new ImageIcon("src//img//medio.png").getImage().getScaledInstance(150, 125, Image.SCALE_SMOOTH));
        ImageIcon medioIcon2 = new ImageIcon(
                new ImageIcon("src//img//medio2.png").getImage().getScaledInstance(150, 125, Image.SCALE_SMOOTH));
        medioItem = new JMenuItemMenuItemPersonalizado("NORMAL", medioIcon1);
        medioItem.setBorder(BorderFactory.createEmptyBorder(0, 120, 0, 0));
        medioItem.addActionListener(this);
        dificultad.add(medioItem);

        ImageIcon dificilIcon1 = new ImageIcon(
                new ImageIcon("src//img//hard.png").getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH));
        ImageIcon dificilIcon2 = new ImageIcon(
                new ImageIcon("src//img//hard2.png").getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH));
        dificilItem = new JMenuItemMenuItemPersonalizado("HARD", dificilIcon1);
        dificilItem.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        dificilItem.addActionListener(this);
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

        dificultad.setPreferredSize(new Dimension(800, 550));
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
