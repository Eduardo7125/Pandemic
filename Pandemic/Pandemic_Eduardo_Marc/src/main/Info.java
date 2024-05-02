package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

class info extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2242406387998512165L;

    JButton salirButton;
    JPanel buttonPanel;
    JScrollPane scrollPane;
    JLabel informacionLabel;
    JLabel tituloLabel;


    info() {

        setLayout(new BorderLayout());

        buttonPanel = new JPanel(new GridLayout(0, 1));

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

        menu.styleButton(salirButton);

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
    	        menu menu = main.menu.getInstance();
    	        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	        menu.setVisible(true);
    	        getParent().add(menu);
    	        
    	        getParent().revalidate();
    	        getParent().repaint();
                break;
        }

        informacionLabel.setText(informacion);
    }
	private static info instance;
    public static info getInstance() {
        if (instance == null) {
            instance = new info();
        }
        return instance;
    }
}