package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
/**
 * @author Eduardo y Marc
 */
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

        
        addButton("START");
        addButton("CITY");
        addButton("GAMES");
        addButton("PLAYER");
        addButton("RESEARCH");
        addButton("VACCINE");
        addButton("HEAL");
        addButton("END");
        addButton("MENU");

        tituloLabel = new JLabel("INFORMATION");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 40));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        informacionLabel = new JLabel();
        informacionLabel.setVerticalAlignment(JLabel.TOP);
        informacionLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        informacionLabel.setOpaque(true);
        
        scrollPane = new JScrollPane(informacionLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setOpaque(false); 
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(tituloLabel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.WEST);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void addButton(String text) {
        JButton button = createTransparentButton(text);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 80)); 
        buttonPanel.add(button);
    }

    private JButton createTransparentButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        
        button.setContentAreaFilled(false);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 80));
        return button;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        String informacion = "";

        switch (boton.getText()) {
            case "START":
                tituloLabel.setText("START");
                informacion = "<html><div><p>At the beginning of each game, vaccines are initialized.<br>"
                        + "At the beginning of each game, cities are initialized.<br>"
                        + "Each city can only be infected with 1 type of infection based on its ID.<br>"
                        + "If there is an outbreak in the adjacent city and both cities have different types of infection, the corresponding infection type will <br>"
                        + "be added.<br>"
                        + "If a city has 3 infections and 1 is added, it is an outbreak. If there is an outbreak, 1 infection is added to the neighboring cities.<br>"
                        + "If there are 3 connected cities, an outbreak occurs in the first city, it jumps to the second, there is also an outbreak, in the third,<br>"
                        + "2 infections are added. 1 is not added to the connected cities that have had an outbreak in this chain of outbreaks.<br>"
                        + "The number of infected cities at the beginning of the game is a configuration parameter.<br>"
                        + "The number of infected cities each round is a configuration parameter.<br>"
                        + "The number of diseases is a configuration parameter.</p></div></html>";
                break;
            case "CITY":
                tituloLabel.setText("CITY");
                informacion = "<html<div><p>Each city has a name, X coordinate, Y coordinate, and type of disease.<br>"
                        + "Each city has different infection levels.<br>"
                        + "Each city stores its adjacent cities.<br>"
                        + "Each city increases the infection level by +1 when it becomes infected again.<br>"
                        + "In each city, if the infection level exceeds 3, the infection spreads to its adjacent cities and (+1 is added to the outbreak counter).</p></div></html>";
                break;
            case "GAMES":
                tituloLabel.setText("GAMES");
                informacion = "<html><div><p>Round number.<br>"
                        + "It will have a list with all the cities and actions that can be performed on them.<br>"
                        + "It will have a list with all the vaccines and actions that can be performed on them (generate vaccine, increase%, ...).</p></div></html>";
                break;
            case "PLAYER":
                tituloLabel.setText("PLAYER");
                informacion = "<html><div><p>The player will have 4 actions per round.<br>"
                        + "Each turn, the player can create the vaccine (conduct research) or cure cities. Both cannot be done in the same turn.<br>"
                        + "Curing costs 1 action and only affects one city.<br>"
                        + "When conducting research, all 4 actions are spent.<br>"
                        + "The disease of the chosen city can be treated.<br>"
                        + "Each city has a name, X coordinate, Y coordinate, and type of disease.<br>"
                        + "Each city has different infection levels.<br>"
                        + "Each city stores its adjacent cities.<br>"
                        + "Each city increases the infection level by +1 when it becomes infected again.<br>"
                        + "In each city, if the infection level exceeds 3, the infection spreads to its adjacent cities and (+1 is added to the outbreak counter).</p></div></html>";
                break;
            case "RESEARCH":
                tituloLabel.setText("RESEARCH");
                informacion = "<html><div><p>Every time research is conducted, the player spends 4 actions and the percentage of the selected vaccine will increase.</p></div></html>";
                break;
            case "VACCINE":
                tituloLabel.setText("VACCINE");
                informacion = "<html><div><p>The vaccine consists of 4 parts (1 part per research).<br>"
                        + "1 vaccine per disease type.<br>"
                        + "They have a name, a color, and a development percentage.<br>"
                        + "If the vaccine is developed 100%, it is indicated that the development is already complete.<br>"
                        + "With 1 complete vaccine, an entire city can be cured at once.</p></div></html>";
                break;
            case "HEAL":
                tituloLabel.setText("HEAL");
                informacion = "<html><div><p>If when curing 1 infected city, a vaccine developed 100% is not available, only the infection level is reduced by 1.<br>"
                        + "If when curing 1 infected city, a vaccine developed 100% is available, the infection is completely eliminated (infection level 0).</p></div></html>";
                break;
            case "END":
                tituloLabel.setText("END");
                informacion = "<html><div><p>At the end of each round, it is checked whether the player has won, lost, or it is unknown.<br>"
                        + "You win by curing all infections.<br>"
                        + "If all the outbreaks occur, the game is lost.</p></div></html>";
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
