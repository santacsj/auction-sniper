package org.tdd.auctionsniper.support;

import org.jivesoftware.smack.*;

public class FakeAuctionServer {

    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";

    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;

    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(String.format(ITEM_ID_AS_LOGIN, getItemId()), AUCTION_PASSWORD,
                AUCTION_RESOURCE);
        connection.getChatManager().addChatListener((chat, createdLocally) -> currentChat = chat);
    }

    public void hasReceivedJoinRequestFromSniper() {
        // TODO Auto-generated method stub

    }

    public void announceClosed() {
        // TODO Auto-generated method stub

    }

    public String getItemId() {
        return itemId;
    }

}
