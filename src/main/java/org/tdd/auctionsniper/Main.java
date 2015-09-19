package org.tdd.auctionsniper;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.tdd.auctionsniper.ui.MainWindow;

public class Main {
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

    public static void main(String... args) throws Exception {
        Main main = new Main();
        XMPPConnection connection = connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME],
                args[ARG_PASSWORD]);
        Chat chat = connection.getChatManager().createChat(
                auctionId(args[ARG_ITEM_ID], connection), (aChat, message) -> {
                });
        chat.sendMessage(new Message());
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    private static XMPPConnection connectTo(String hostname, String username, String password)
            throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
    }
}
