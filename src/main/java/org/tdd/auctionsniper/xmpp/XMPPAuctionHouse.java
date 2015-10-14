package org.tdd.auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.tdd.auctionsniper.*;

public class XMPPAuctionHouse implements AuctionHouse {

    public static XMPPAuctionHouse connect(String hostname, String username, String password)
            throws XMPPException {
        return new XMPPAuctionHouse(connection(hostname, username, password));
    }

    private static XMPPConnection connection(String hostname, String username, String password)
            throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, Main.AUCTION_RESOURCE);
        return connection;
    }

    private final XMPPConnection connection;

    public XMPPAuctionHouse(XMPPConnection connection) {
        this.connection = connection;
    }

    @Override
    public Auction auctionFor(String itemId) {
        return new XMPPAuction(connection, itemId);
    }

    @Override
    public void disconnect() {
        connection.disconnect();
    }
}
