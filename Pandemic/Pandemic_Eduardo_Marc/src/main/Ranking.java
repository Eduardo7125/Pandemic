package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.*;

import data_managment.Control_de_datos;

/**
 * Author: Eduardo y Marc
 */
public class Ranking extends JPanel {

    private static final long serialVersionUID = 1803883461317339869L;
    private JTable leaderboardTableEasy, leaderboardTableMedium, leaderboardTableHard;
    private ArrayList<LeaderboardEntry> leaderboardDataEasy, leaderboardDataMedium, leaderboardDataHard;
    private JButton closeButton;

    public Ranking() {
        setLayout(new BorderLayout());

        // Initialize the leaderboard data lists
        leaderboardDataEasy = new ArrayList<>();
        leaderboardDataMedium = new ArrayList<>();
        leaderboardDataHard = new ArrayList<>();

        // Create the tables for each difficulty
        leaderboardTableEasy = createLeaderboardTable();
        leaderboardTableMedium = createLeaderboardTable();
        leaderboardTableHard = createLeaderboardTable();

        // Create the panels for each difficulty
        JPanel easyPanel = createDifficultyPanel("Easy", leaderboardTableEasy);
        JPanel mediumPanel = createDifficultyPanel("Medium", leaderboardTableMedium);
        JPanel hardPanel = createDifficultyPanel("Hard", leaderboardTableHard);

        // Create a vertical panel to organize the tables
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

        java.util.Date[] utilDates = Control_de_datos.RankingDates;
        java.sql.Date[] sqlDates = new java.sql.Date[utilDates.length];
        for (int i = 0; i < utilDates.length; i++) {
            sqlDates[i] = new java.sql.Date(utilDates[i].getTime());
        }

        // Update the leaderboard tables for each difficulty
        for (int i = 0; i < Control_de_datos.RankingDificulty.length; i++) {
            if (Control_de_datos.RankingDificulty[i].equals("Facil")) {
                updateLeaderboard(Control_de_datos.RankingNames[i], Control_de_datos.RankingRounds[i],
                        sqlDates[i], Control_de_datos.RankingResult[i], leaderboardDataEasy, leaderboardTableEasy);
            } else if (Control_de_datos.RankingDificulty[i].equals("Medio")) {
                updateLeaderboard(Control_de_datos.RankingNames[i], Control_de_datos.RankingRounds[i],
                        sqlDates[i], Control_de_datos.RankingResult[i], leaderboardDataMedium, leaderboardTableMedium);
            } else {
                updateLeaderboard(Control_de_datos.RankingNames[i], Control_de_datos.RankingRounds[i],
                        sqlDates[i], Control_de_datos.RankingResult[i], leaderboardDataHard, leaderboardTableHard);
            }
        }
    }

//    public static void main(String [] args) {
//        Control_de_datos.disconnect();
//        Control_de_datos.conectarBaseDatos();
//
//        Control_de_datos.selectRanking();
//        for (int i = 0; i < Control_de_datos.RankingDificulty.length; i++) {
//            if (Control_de_datos.RankingDificulty[i].equals("Facil")) {
//            	System.out.println(Control_de_datos.RankingNames[i]);
//            	System.out.println(Control_de_datos.RankingRounds[i]);
//            	System.out.println(Control_de_datos.RankingResult[i]);
//            	System.out.println(Control_de_datos.RankingDificulty[i]);
//            	
//            	System.out.println();
//            } else if (Control_de_datos.RankingDificulty[i].equals("Medio")) {
//            	System.out.println(Control_de_datos.RankingNames[i]);
//            	System.out.println(Control_de_datos.RankingRounds[i]);
//            	System.out.println(Control_de_datos.RankingResult[i]);
//            	System.out.println(Control_de_datos.RankingDificulty[i]);
//             	System.out.println();
//            } else {
//            	System.out.println(Control_de_datos.RankingNames[i]);
//            	System.out.println(Control_de_datos.RankingRounds[i]);
//            	System.out.println(Control_de_datos.RankingResult[i]);
//            	System.out.println(Control_de_datos.RankingDificulty[i]);
//             	System.out.println();
//            }
//        }
//    }
    
    private JTable createLeaderboardTable() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("PLACEMENT");
        tableModel.addColumn("PLAYER NAME");
        tableModel.addColumn("ROUNDS");
        tableModel.addColumn("DATE");
        tableModel.addColumn("RESULT");

        JTable table = new JTable(tableModel){
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
        for (int i = 0; i < 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumn dateColumn = table.getColumnModel().getColumn(3);
        dateColumn.setCellRenderer(new DateCellRenderer());

        TableColumn resultColumn = table.getColumnModel().getColumn(4);
        resultColumn.setCellRenderer(new ResultCellRenderer());

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

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public void updateLeaderboard(String name, int rounds, Date date, String result,
            ArrayList<LeaderboardEntry> leaderboardData, JTable leaderboardTable) {
    	LeaderboardEntry entry = new LeaderboardEntry(name, rounds, date, result);
    	leaderboardData.add(entry);

		leaderboardData.sort(new Comparator<LeaderboardEntry>() {
			@Override
			public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
				if (entry1.getResult().equals("Victory") && entry2.getResult().equals("Defeat")) {
					return -1;
				} else if (entry1.getResult().equals("Defeat") && entry2.getResult().equals("Victory")) {
					return 1;
				} else {
					return Integer.compare(entry1.getRounds(), entry2.getRounds());
				}
			}
		});
		
		DefaultTableModel tableModel = (DefaultTableModel) leaderboardTable.getModel();
		tableModel.setRowCount(0);
		for (int i = 0; i < leaderboardData.size(); i++) {
			LeaderboardEntry entry2 = leaderboardData.get(i);
			tableModel.addRow(new Object[]{i + 1, entry2.getPlayerName(), entry2.getRounds(), formatDate(entry2.getDate()),
					entry2.getResult()});
		}
	}

    private static class LeaderboardEntry {
        private String playerName;
        private int rounds;
        private Date date;
        private String result;

        public LeaderboardEntry(String playerName, int rounds, Date date, String result) {
            this.playerName = playerName;
            this.rounds = rounds;
            this.date = date;
            this.result = result;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getRounds() {
            return rounds;
        }

        public Date getDate() {
            return date;
        }

        public String getResult() {
            return result;
        }
    }

    private class DateCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        public DateCellRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public void setValue(Object value) {
            if (value instanceof Date) {
                setText(formatDate((Date) value));
            } else {
                super.setValue(value);
            }
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
                    setForeground(Color.GREEN); // Green color for victories
                } else if (result.equals("Defeat")) {
                    setText("Defeat");
                    setForeground(Color.RED); // Red color for defeats
                } else {
                    setText(""); // If it's neither "Victory" nor "Defeat", show nothing
                }
            } else {
                super.setValue(value);
            }
        }
    }
}