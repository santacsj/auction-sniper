package org.tdd.auctionsniper.test;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;
import org.tdd.auctionsniper.AuctionMessageTranslator;

public class AuctionMessageTranslatorTest {

    private static final Chat UNUSED_CHAT = null;

    private final AuctionMessageTranslator translator = new AuctionMessageTranslator();

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");

        translator.processMessage(UNUSED_CHAT, message);
    }
}
