package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import data_managment.Control_de_datos;

public class Ranking extends JPanel {

    private static final long serialVersionUID = 4806108314364896082L;

    private JTable leaderboardTable;
    private ArrayList<LeaderboardEntry> leaderboardData;

    public Ranking() {
        setLayout(new BorderLayout());

        // Create a panel to contain the table
        JPanel tablePanel = new JPanel(new GridBagLayout());

        // Initialize table
        DefaultTableModel tableModel = new DefaultTableModel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        tableModel.addColumn("Player Name");
        tableModel.addColumn("Rounds");
        tableModel.addColumn("Date");
        tableModel.addColumn("Result");

        leaderboardTable = new JTable(tableModel);

        // Disable column reordering and resizing
        leaderboardTable.getTableHeader().setReorderingAllowed(false);
        leaderboardTable.getTableHeader().setResizingAllowed(false);

        // Set default column widths
        TableColumnModel columnModel = leaderboardTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // Player Name
        columnModel.getColumn(1).setPreferredWidth(55); // Rounds
        columnModel.getColumn(2).setPreferredWidth(75); // Date
        columnModel.getColumn(3).setPreferredWidth(200); // Result

        // Disable cell selection
        leaderboardTable.setCellSelectionEnabled(false);

        // Convert java.util.Date[] to java.sql.Date[]
        java.util.Date[] utilDates = Control_de_datos.RankingDates;
        java.sql.Date[] sqlDates = new java.sql.Date[utilDates.length];
        for (int i = 0; i < utilDates.length; i++) {
            sqlDates[i] = new java.sql.Date(utilDates[i].getTime());
        }

        updateLeaderboard(Control_de_datos.RankingNames, Control_de_datos.RankingRounds,
                sqlDates, Control_de_datos.RankingResult);

        // Add the table to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        tablePanel.add(new JScrollPane(leaderboardTable), gbc);

        // Set the preferred size of the table
        tablePanel.setPreferredSize(new Dimension(600, 400));

        // Add the panel to the center of the BorderLayout
        add(tablePanel, BorderLayout.CENTER);

        // Add a title label
        JLabel titleLabel = new JLabel("Leaderboard");
        add(titleLabel, BorderLayout.NORTH);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public void updateLeaderboard(String[] names, int[] rounds, Date[] dates, String[] results) {
        leaderboardData = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            LeaderboardEntry entry = new LeaderboardEntry(names[i], rounds[i], dates[i], results[i]);
            leaderboardData.add(entry);
        }

        leaderboardData.sort(Comparator.comparingInt(LeaderboardEntry::getRounds).reversed());

        DefaultTableModel tableModel = (DefaultTableModel) leaderboardTable.getModel();
        tableModel.setRowCount(0);
        for (LeaderboardEntry entry : leaderboardData) {
            tableModel.addRow(new Object[] { entry.getPlayerName(), entry.getRounds(), formatDate(entry.getDate()),
                    entry.getResult() });
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
}
