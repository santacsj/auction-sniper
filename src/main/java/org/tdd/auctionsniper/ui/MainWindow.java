package org.tdd.auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;

import org.tdd.auctionsniper.Main;
import org.tdd.auctionsniper.SniperState;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public static final String SNIPERS_TABLE_NAME = "snipers table";
    private final SnipersTableModel snipers = new SnipersTableModel();
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_WINNING = "Winning";
    public static final String STATUS_WON = "Won";

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable) {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(snipersTable, BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        JTable sniperTable = new JTable(snipers);
        sniperTable.setName(SNIPERS_TABLE_NAME);
        return sniperTable;
    }

    public void showStatus(String statusText) {
        snipers.setStatusText(statusText);
    }

    public void sniperStatusChanged(SniperState sniperState, String statusText) {
        snipers.sniperStatusChanged(sniperState, statusText);
    }

}
