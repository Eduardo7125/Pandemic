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

public class loadgame extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1962141026846897594L;
	private JTable leaderboardTable;
    private ArrayList<LeaderboardEntry> leaderboardData;
    private JButton closeButton;

    public loadgame() {
        setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("PLAYER NAME");
        tableModel.addColumn("ROUNDS");
        tableModel.addColumn("OUTBRAKS");
        tableModel.addColumn("ACTIONS");

        leaderboardTable = new JTable(tableModel);

        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 12));
        leaderboardTable.setRowHeight(20);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        leaderboardTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        leaderboardTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        leaderboardTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        TableColumn dateColumn = leaderboardTable.getColumnModel().getColumn(2);
        dateColumn.setCellRenderer(new DateCellRenderer());

        java.util.Date[] utilDates = Control_de_datos.RankingDates;
        java.sql.Date[] sqlDates = new java.sql.Date[utilDates.length];
        for (int i = 0; i < utilDates.length; i++) {
            sqlDates[i] = new java.sql.Date(utilDates[i].getTime());
        }

        updateLeaderboard(Control_de_datos.RankingNames, Control_de_datos.RankingRounds,
                sqlDates, Control_de_datos.RankingResult);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("LEADERBOARD");
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
}
