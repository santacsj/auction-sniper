package org.tdd.auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.tdd.auctionsniper.*;

public class XMPPAuctionHouse implements AuctionHouse {

    private static final String AUCTION_RESOURCE = "Auction";

    private static final String ITEM_ID_AS_LOGIN = "auction-%s";

    private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    public static XMPPAuctionHouse connect(String hostname, String username, String password)
            throws XMPPException {
        return new XMPPAuctionHouse(connection(hostname, username, password));
    }

    private static XMPPConnection connection(String hostname, String username, String password)
            throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, XMPPAuctionHouse.AUCTION_RESOURCE);
        return connection;
    }

    private final XMPPConnection connection;

    public XMPPAuctionHouse(XMPPConnection connection) {
        this.connection = connection;
    }

    @Override
    public Auction auctionFor(Item item) {
        return new XMPPAuction(connection, auctionId(item.identifier, connection));
    }

    @Override
    public void disconnect() {
        connection.disconnect();
    }

}
