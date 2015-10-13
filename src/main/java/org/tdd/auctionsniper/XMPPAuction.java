package org.tdd.auctionsniper;

import org.jivesoftware.smack.*;
import org.jmock.example.announcer.Announcer;

public class XMPPAuction implements Auction {

    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";

    private static final String AUCTION_ID_FORMAT = Main.ITEM_ID_AS_LOGIN + "@%s/"
            + Main.AUCTION_RESOURCE;

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(XMPPAuction.AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    private final Announcer<AuctionEventListener> auctionEventListeners = Announcer
            .to(AuctionEventListener.class);
    private final Chat chat;

    public XMPPAuction(XMPPConnection connection, String itemId) {
        this.chat = connection.getChatManager()
                .createChat(
                        auctionId(itemId, connection),
                        new AuctionMessageTranslator(connection.getUser(), auctionEventListeners
                                .announce()));
    }

    @Override
    public void join() {
        sendMessage(XMPPAuction.JOIN_COMMAND_FORMAT);
    }

    @Override
    public void bid(int amount) {
        sendMessage(String.format(XMPPAuction.BID_COMMAND_FORMAT, amount));
    }

    @Override
    public void addAuctionEventListener(AuctionEventListener listener) {
        auctionEventListeners.addListener(listener);
    }

    private void sendMessage(String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

}