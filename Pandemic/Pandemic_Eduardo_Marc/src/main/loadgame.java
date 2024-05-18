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

        JLabel titleLabel = new JLabel("<html><div style='padding-left: 20%'>LEADERBOARD</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
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
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("PLAYER NAME");
        tableModel.addColumn("ROUNDS");
        tableModel.addColumn("TURNS");
        tableModel.addColumn("OUTBREAKS");

        JTable table = new JTable(tableModel) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
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
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public void updateLeaderboard(Datos_partida files, ArrayList<LeaderboardEntry> leaderboardData, JTable leaderboardTable) {
        LeaderboardEntry entry = new LeaderboardEntry(files.getPlayer(), files.getRondas(), files.getAcciones(), files.getBrotes(), files.getIdentificador());
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
            tableModel.addRow(new Object[]{entry2.getPlayerName(), entry2.getRounds(), entry2.getArrTurno(), entry2.getArrOutreak()});
        }
    }

    private void addMouseListenerToTable(JTable table, ArrayList<LeaderboardEntry> leaderboardData) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < leaderboardData.size()) {
                    performCellAction(row, leaderboardData);
                }
            }
        });
    }

        private void performCellAction(int row, ArrayList<LeaderboardEntry> leaderboardData) {
        if (row >= 0 && row < leaderboardData.size()) {
            LeaderboardEntry entry = leaderboardData.get(row);
            int identificador = entry.getIdentificador();
            
            if(cargarP == false) {
            Control_de_datos.borrarPartida(row);
            JOptionPane.showMessageDialog(null, "Savefile deleted: " + identificador);
            } else {
            	Control_de_datos.selectDatos(row);
//            menus.resetvalores();
            game juego = new game();
            juego.setVisible(true);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Out of range: " + row);
        }
    }

    private static class LeaderboardEntry {
        private String playerName;
        private int rounds;
        private int arrTurno;
        private int arrOutreak;
        private int identificador;

        public LeaderboardEntry(String playerName, int rounds, int arrTurno, int arrOutreak, int identificador) {
            this.playerName = playerName;
            this.rounds = rounds;
            this.arrTurno = arrTurno;
            this.arrOutreak = arrOutreak;
            this.identificador = identificador;
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
    }

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
