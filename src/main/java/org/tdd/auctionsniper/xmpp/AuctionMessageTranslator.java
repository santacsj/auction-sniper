package org.tdd.auctionsniper.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.tdd.auctionsniper.AuctionEventListener;
import org.tdd.auctionsniper.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {

    private final String sniperId;
    private final AuctionEventListener listener;

    public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
        this.sniperId = sniperId;
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        try {
            translate(message.getBody());
        } catch (Exception e) {
            listener.auctionFailed();
        }
    }

    private void translate(String body) throws MissingValueException {
        AuctionEvent event = AuctionEvent.from(body);

        switch (event.type()) {
        case "CLOSE":
            listener.auctionClosed();
            break;
        case "PRICE":
            listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
        }
    }

    private static class AuctionEvent {
        private final Map<String, String> fields = new HashMap<String, String>();

        public String type() throws MissingValueException {
            return get("Event");
        }

        public int currentPrice() throws MissingValueException {
            return getInt("CurrentPrice");
        }

        public int increment() throws MissingValueException {
            return getInt("Increment");
        }

        public PriceSource isFrom(String sniperId) throws MissingValueException {
            return sniperId.equals(getBidder()) ? PriceSource.FromSniper
                    : PriceSource.FromOtherBidder;
        }

        private String getBidder() throws MissingValueException {
            return get("Bidder");
        }

        private int getInt(String fieldName) throws MissingValueException {
            return Integer.parseInt(get(fieldName));
        }

        private String get(String fieldName) throws MissingValueException {
            String value = fields.get(fieldName);

            if (value == null) {
                throw new MissingValueException(fieldName);
            }
            return value;
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody))
                event.addField(field);
            return event;
        }

        static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

    }

}
