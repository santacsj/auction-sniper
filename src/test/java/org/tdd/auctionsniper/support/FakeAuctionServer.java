package org.tdd.auctionsniper.support;

import static java.lang.String.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.junit.rules.ExternalResource;

public class FakeAuctionServer extends ExternalResource {

    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private final SingleMessageListener messageListener;
    private Chat currentChat;

    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
        this.messageListener = new SingleMessageListener();
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(ITEM_ID_AS_LOGIN, getItemId()), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener((chat, createdLocally) -> {
            currentChat = chat;
            currentChat.addMessageListener(messageListener);
        });
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        messageListener.receivesAMessage();
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(new Message());
    }

    public void stop() {
        connection.disconnect();
    }

    @Override
    protected void after() {
        stop();
    }

    public String getItemId() {
        return itemId;
    }

}
