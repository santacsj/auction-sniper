package org.tdd.auctionsniper.ui;

import java.awt.*;

import javax.swing.*;

import org.jmock.example.announcer.Announcer;
import org.tdd.auctionsniper.Main;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public static final String SNIPERS_TABLE_NAME = "snipers table";
    public static final String APPLICATION_TITLE = "Auction Sniper";
    public static final String NEW_ITEM_ID_NAME = "item id";
    public static final String JOIN_BUTTON_NAME = "join button";

    private Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);

    public MainWindow(SnipersTableModel snipers) {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        setTitle(APPLICATION_TITLE);
        fillContentPane(makeSnipersTable(snipers), makeControls());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable, JPanel controlsPanel) {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
        contentPane.add(controlsPanel, BorderLayout.NORTH);
    }

    private JTable makeSnipersTable(SnipersTableModel snipers) {
        JTable sniperTable = new JTable(snipers);
        sniperTable.setName(SNIPERS_TABLE_NAME);
        return sniperTable;
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());
        final JTextField itemIdField = new JTextField();
        itemIdField.setColumns(25);
        itemIdField.setName(NEW_ITEM_ID_NAME);
        controls.add(itemIdField);

        JButton joinAuctionButton = new JButton("Join Auction");
        joinAuctionButton.setName(JOIN_BUTTON_NAME);
        joinAuctionButton.addActionListener(e -> userRequests.announce().joinAuction(
                itemIdField.getText()));
        controls.add(joinAuctionButton);
        return controls;
    }

    public void addUserRequestListener(UserRequestListener userRequestListener) {
        userRequests.addListener(userRequestListener);
    }

}
