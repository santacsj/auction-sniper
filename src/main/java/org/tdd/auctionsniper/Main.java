package org.tdd.auctionsniper;

import javax.swing.SwingUtilities;

import org.tdd.auctionsniper.ui.MainWindow;

public class Main {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_NAME = "";

    public static void main(String xmppHostname, String sniperId, String sniperPassword,
            String itemId) throws Exception {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
    }
}
