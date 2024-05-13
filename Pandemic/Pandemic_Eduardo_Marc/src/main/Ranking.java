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

public class Ranking extends JPanel {

    private JTable leaderboardTable;
    private ArrayList<LeaderboardEntry> leaderboardData;
    private JButton closeButton;

    public Ranking() {
        setLayout(new BorderLayout());

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

        leaderboardTable = new JTable(tableModel);

        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 12));
        leaderboardTable.setRowHeight(20);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumn dateColumn = leaderboardTable.getColumnModel().getColumn(3);
        dateColumn.setCellRenderer(new DateCellRenderer());

        TableColumn resultColumn = leaderboardTable.getColumnModel().getColumn(4);
        resultColumn.setCellRenderer(new ResultCellRenderer());

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
        for (int i = 0; i < leaderboardData.size(); i++) {
            LeaderboardEntry entry = leaderboardData.get(i);
            tableModel.addRow(new Object[] { i + 1, entry.getPlayerName(), entry.getRounds(), formatDate(entry.getDate()),
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
                    setForeground(Color.GREEN); // Color verd per a victories
                } else if (result.equals("Defeat")) {
                    setText("Defeat");
                    setForeground(Color.RED); // Color vermell per a derrotes
                } else {
                    setText(""); // Si no és ni "Victory" ni "Defeat", no mostrar res
                }
            } else {
                super.setValue(value);
            }
        }
    }
}
