package org.tdd.auctionsniper.xmpp;

import org.jivesoftware.smack.*;
import org.jmock.example.announcer.Announcer;
import org.tdd.auctionsniper.Auction;
import org.tdd.auctionsniper.AuctionEventListener;

public class XMPPAuction implements Auction {

    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

    private final Announcer<AuctionEventListener> auctionEventListeners = Announcer
            .to(AuctionEventListener.class);
    private final Chat chat;

    public XMPPAuction(XMPPConnection connection, String auctionId) {
        AuctionMessageTranslator translator = translatorFor(connection);
        this.chat = connection.getChatManager().createChat(auctionId, translator);
        addAuctionEventListener(chatDisconnectFor(translator));
    }

    private AuctionEventListener chatDisconnectFor(final AuctionMessageTranslator translator) {
        return new AuctionEventListener() {

            @Override
            public void auctionClosed() {
                // empty method
            }

            @Override
            public void currentPrice(int price, int increment, PriceSource priceSource) {
                // empty method
            }

            @Override
            public void auctionFailed() {
                XMPPAuction.this.chat.removeMessageListener(translator);
            }
        };
    }

    private AuctionMessageTranslator translatorFor(XMPPConnection connection) {
        return new AuctionMessageTranslator(connection.getUser(), auctionEventListeners.announce(), null);
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