package org.tdd.auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;

import org.tdd.auctionsniper.Main;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public static final String SNIPERS_TABLE_NAME = "snipers table";

    public MainWindow(SnipersTableModel snipers) {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable(snipers));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable) {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable(SnipersTableModel snipers) {
        JTable sniperTable = new JTable(snipers);
        sniperTable.setName(SNIPERS_TABLE_NAME);
        return sniperTable;
    }

}
