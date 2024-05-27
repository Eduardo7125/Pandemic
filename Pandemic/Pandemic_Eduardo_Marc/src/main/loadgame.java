package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.*;

import data_managment.Control_de_datos;
import data_managment.Datos_partida;

public class loadgame extends JPanel {

	public static boolean cargarP;
    private static final long serialVersionUID = 1803883461317339869L;
    private JTable leaderboardTableEasy, leaderboardTableMedium, leaderboardTableHard;
    private ArrayList<LeaderboardEntry> leaderboardDataEasy, leaderboardDataMedium, leaderboardDataHard;
    private ArrayList<Datos_partida> saveFiles;
    private JButton closeButton;

    public loadgame() {
        setLayout(new BorderLayout());
        
        leaderboardDataEasy = new ArrayList<>();
        leaderboardDataMedium = new ArrayList<>();
        leaderboardDataHard = new ArrayList<>();
        
        if (saveFiles != null) {
            leaderboardDataEasy.clear();
            leaderboardDataMedium.clear();
            leaderboardDataHard.clear();;
            
            saveFiles.clear();
		}
        
        saveFiles = Control_de_datos.saveFiles;

        leaderboardTableEasy = createLeaderboardTable();
        leaderboardTableMedium = createLeaderboardTable();
        leaderboardTableHard = createLeaderboardTable();

        addMouseListenerToTable(leaderboardTableEasy, leaderboardDataEasy);
        addMouseListenerToTable(leaderboardTableMedium, leaderboardDataMedium);
        addMouseListenerToTable(leaderboardTableHard, leaderboardDataHard);

        JPanel easyPanel = createDifficultyPanel("Easy", leaderboardTableEasy);
        JPanel mediumPanel = createDifficultyPanel("Medium", leaderboardTableMedium);
        JPanel hardPanel = createDifficultyPanel("Hard", leaderboardTableHard);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 3));
        tablesPanel.add(easyPanel);
        tablesPanel.add(mediumPanel);
        tablesPanel.add(hardPanel);

        add(tablesPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("      SAVED GAMES");
        titleLabel.setFont(info.fuentecargar2(30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        closeButton = new JButton("MENU");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menu menu = main.menu.getInstance();
                menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                menu.setVisible(true);
                getParent().add(menu);
                getParent().revalidate();
                getParent().repaint();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(closeButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        for (Datos_partida files : saveFiles) {
            if (files.getDificultad().equals("Facil")) {
                updateLeaderboard(files, leaderboardDataEasy, leaderboardTableEasy);
            } else if (files.getDificultad().equals("Medio")) {
                updateLeaderboard(files, leaderboardDataMedium, leaderboardTableMedium);
            } else {
                updateLeaderboard(files, leaderboardDataHard, leaderboardTableHard);
            }
        }
    }

    private JTable createLeaderboardTable() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            private static final long serialVersionUID = -4814450097661192875L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("PLAYER NAME");
        tableModel.addColumn("ROUNDS");
        tableModel.addColumn("TURNS");
        tableModel.addColumn("OUTBREAKS");
        tableModel.addColumn("MODE");
        tableModel.addColumn("ID"); 
        
        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = -4964336313599035200L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(info.fuentecargar(14));
        table.setFont(info.fuentecargar(12));
        table.setRowHeight(20);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 4; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private JPanel createDifficultyPanel(String difficulty, JTable table) {
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        JLabel label = new JLabel(difficulty.toUpperCase());
        label.setFont(info.fuentecargar2(20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void updateLeaderboard(Datos_partida files, ArrayList<LeaderboardEntry> leaderboardData, JTable leaderboardTable) {
        String difficultyText = "";
        switch (files.getDificultad()) {
            case "Facil":
                difficultyText = "Easy";
                break;
            case "Medio":
                difficultyText = "Medium";
                break;
            case "Dificil":
                difficultyText = "Hard";
                break;
        }
        
        LeaderboardEntry entry = new LeaderboardEntry(files.getPlayer(), files.getRondas(), files.getAcciones(), files.getBrotes(), files.getIdentificador(), difficultyText);
        leaderboardData.add(entry);

        leaderboardData.sort(new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
                return Integer.compare(entry1.getRounds(), entry2.getRounds());
            }
        });

        DefaultTableModel tableModel = (DefaultTableModel) leaderboardTable.getModel();
        tableModel.setRowCount(0);
        for (LeaderboardEntry entry2 : leaderboardData) {
            tableModel.addRow(new Object[]{entry2.getPlayerName(), entry2.getRounds(), entry2.getArrTurno(), entry2.getArrOutreak(), entry2.getDifficulty(), entry2.getIdentificador()});
        }
    }

    private void addMouseListenerToTable(JTable table, ArrayList<LeaderboardEntry> leaderboardData) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < leaderboardData.size()) {
                    performCellAction(row, leaderboardData, table);
                }
            }
        });
    }
    
    private void updateLeaderboards() {
        leaderboardDataEasy.clear();
        leaderboardDataMedium.clear();
        leaderboardDataHard.clear();

        for (Datos_partida files : saveFiles) {
            if (files.getDificultad().equals("Facil")) {
                updateLeaderboard(files, leaderboardDataEasy, leaderboardTableEasy);
            } else if (files.getDificultad().equals("Medio")) {
                updateLeaderboard(files, leaderboardDataMedium, leaderboardTableMedium);
            } else {
                updateLeaderboard(files, leaderboardDataHard, leaderboardTableHard);
            }
        }
    }
    
    private void performCellAction(int row, ArrayList<LeaderboardEntry> leaderboardData, JTable leaderboardTable) {
        if (row >= 0 && row < leaderboardData.size()) {
            LeaderboardEntry entry = leaderboardData.get(row);
            
            JTable selectedTable = null;
            if (leaderboardTable == leaderboardTableEasy) {
                selectedTable = leaderboardTableEasy;
            } else if (leaderboardTable == leaderboardTableMedium) {
                selectedTable = leaderboardTableMedium;
            } else if (leaderboardTable == leaderboardTableHard) {
                selectedTable = leaderboardTableHard;
            }
            
            if (selectedTable != null) {
                TableModel model = selectedTable.getModel();
                int selectedRow = selectedTable.convertRowIndexToModel(row);
                
                int identificadorPartida = (int) model.getValueAt(selectedRow, 5);


                if (cargarP == false) {
                    JPanel buttonPanel = new JPanel();

                    JButton confirmItem = new JButton("CONFIRM");
                    JButton rejectItem = new JButton("REJECT");

                    confirmItem.setPreferredSize(new Dimension(140, 60));
                    rejectItem.setPreferredSize(new Dimension(140, 60));

                    buttonPanel.add(rejectItem);
                    buttonPanel.add(confirmItem);

                    JPopupMenu popupMenu = new JPopupMenu();
                    popupMenu.add(buttonPanel);

                    confirmItem.addActionListener(e -> {
                        Control_de_datos.borrarPartida(identificadorPartida);

                        if (entry.getDifficulty().equals("Facil")) {
                            leaderboardDataEasy.removeIf(item -> item.getIdentificador() == identificadorPartida);
                        } else if (entry.getDifficulty().equals("Medio")) {
                            leaderboardDataMedium.removeIf(item -> item.getIdentificador() == identificadorPartida);
                        } else {
                            leaderboardDataHard.removeIf(item -> item.getIdentificador() == identificadorPartida);
                        }

                        updateLeaderboards();
                        buttonPanel.setVisible(false);
                        popupMenu.setVisible(false);
                        closeButton.doClick();
                    });

                    rejectItem.addActionListener(e -> {
                        buttonPanel.setVisible(false);
                        popupMenu.setVisible(false);
                    });
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int screenWidth = screenSize.width;
                    int screenHeight = screenSize.height;

                    int x = (screenWidth - popupMenu.getPreferredSize().width) / 2;
                    int y = (screenHeight - popupMenu.getPreferredSize().height) / 2;

                    popupMenu.show(this, x, y);
                } else {
                    setVisible(false);
                	UIManager.put("MenuItem.selectionBackground", null);
                	UIManager.put("MenuItem.selectionForeground", null);
                    if (Control_de_datos.partidaCargada == true) {
                    	menu.resetvalores();
					}
                    Control_de_datos.selectDatos(identificadorPartida);
                    iniciarSavePartida(identificadorPartida);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Out of range: " + row);
            }
        }
    }

    private void iniciarSavePartida(int identificadorPartida) {
    	gameSAVE.instance = null;
        gameSAVE juego = new gameSAVE(identificadorPartida);
        juego.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        juego.setVisible(true);
        gameSAVE.actualizarEstadoCiudades();
        getParent().add(juego);
        getParent().revalidate();
        getParent().repaint();
    }
        
    static void resetLeaderboardData() {
    	Control_de_datos.saveFiles.clear();
    }

    private static class LeaderboardEntry {
        private String playerName;
        private int rounds;
        private int arrTurno;
        private int arrOutreak;
        private int identificador;
        private String difficulty;

        public LeaderboardEntry(String playerName, int rounds, int arrTurno, int arrOutreak, int identificador, String difficulty) {
            this.playerName = playerName;
            this.rounds = rounds;
            this.arrTurno = arrTurno;
            this.arrOutreak = arrOutreak;
            this.identificador = identificador;
            this.difficulty = difficulty;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getRounds() {
            return rounds;
        }

        public int getArrTurno() {
            return arrTurno;
        }

        public int getArrOutreak() {
            return arrOutreak;
        }

        public int getIdentificador() {
            return identificador;
        }

        public String getDifficulty() {
            return difficulty;
        }
    }


    @SuppressWarnings("unused")
	private class ResultCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        public ResultCellRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public void setValue(Object value) {
            if (value instanceof String) {
                String result = (String) value;
                if (result.equals("Victory")) {
                    setText("Victory");
                    setForeground(Color.GREEN);
                } else if (result.equals("Defeat")) {
                    setText("Defeat");
                    setForeground(Color.RED);
                } else {
                    setText("");
                }
            } else {
                super.setValue(value);
            }
        }
    }
}
