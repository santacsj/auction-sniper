package org.tdd.auctionsniper.ui;

import java.awt.*;

import javax.swing.*;

import org.jmock.example.announcer.Announcer;
import org.tdd.auctionsniper.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    public static final String SNIPERS_TABLE_NAME = "snipers table";
    public static final String APPLICATION_TITLE = "Auction Sniper";
    public static final String NEW_ITEM_ID_NAME = "item id";
    public static final String JOIN_BUTTON_NAME = "join button";
    public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";

    private Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);

    public MainWindow(SniperPortfolio portfolio) {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        setTitle(APPLICATION_TITLE);
        fillContentPane(makeSnipersTable(portfolio), makeControls());
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

    private JTable makeSnipersTable(SniperPortfolio portfolio) {
        SnipersTableModel model = new SnipersTableModel();
        portfolio.addPortfolioListener(model);
        JTable sniperTable = new JTable(model);
        sniperTable.setName(SNIPERS_TABLE_NAME);
        return sniperTable;
    }

    private JPanel makeControls() {
        JPanel controls = new JPanel(new FlowLayout());

        controls.add(new JLabel("Item:"));

        final JTextField itemIdField = new JTextField();
        itemIdField.setColumns(9);
        itemIdField.setName(NEW_ITEM_ID_NAME);
        controls.add(itemIdField);

        controls.add(new JLabel("Stop price:"));

        final JFormattedTextField stopPriceField = new JFormattedTextField();
        stopPriceField.setColumns(7);
        stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
        controls.add(stopPriceField);

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
