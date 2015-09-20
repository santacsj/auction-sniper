package org.tdd.auctionsniper.test;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.AuctionEventListener;
import org.tdd.auctionsniper.AuctionMessageTranslator;

public class AuctionMessageTranslatorTest {

    private static final Chat UNUSED_CHAT = null;

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator();

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {
        context.checking(new Expectations() {
            {
                oneOf(listener).auctionClosed();
            }
        });
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");

        translator.processMessage(UNUSED_CHAT, message);
    }
}
