package org.tdd.auctionsniper.xmpp.test;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.AuctionEventListener;
import org.tdd.auctionsniper.AuctionEventListener.PriceSource;
import org.tdd.auctionsniper.xmpp.AuctionMessageTranslator;
import org.tdd.auctionsniper.xmpp.XMPPFailureReporter;

public class AuctionMessageTranslatorTest {

    private static final Chat UNUSED_CHAT = null;
    private static final String SNIPER_ID = "sniper";

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final XMPPFailureReporter failureReporter = context.mock(XMPPFailureReporter.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_ID,
            listener, failureReporter);

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

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
        context.checking(new Expectations() {
            {
                exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
            }
        });
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");

        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
        context.checking(new Expectations() {
            {
                exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromSniper);
            }
        });
        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: "
                + SNIPER_ID + ";");

        translator.processMessage(UNUSED_CHAT, message);
    }

    @Test
    public void notifiesAuctionFailedWhenBadMessageReceived() {
        String badMessage = "a bad message";
        expectFailureMessage(badMessage);
        translator.processMessage(UNUSED_CHAT, message(badMessage));
    }

    private Message message(String badMessage) {
        Message message = new Message();
        message.setBody(badMessage);
        return message;
    }

    private void expectFailureMessage(String badMessage) {
        context.checking(new Expectations() {
            {
                oneOf(listener).auctionFailed();
                oneOf(failureReporter).cannotTranslateMessage(with(SNIPER_ID), with(badMessage),
                        with(any(Exception.class)));
            }
        });
    }

    @Test
    public void notifiesAuctionFailedWhenEventTypeMissing() {
        String badMessage = "SOLVersion: 1.1; CurrentPrice: 234; Increment: 5; Bidder: "
                + SNIPER_ID + ";";
        expectFailureMessage(badMessage);
        translator.processMessage(UNUSED_CHAT, message(badMessage));
    }

    /*
     * Note: The book implies to test the missing event type or current price.
     * In reality, in both of these cases the translator will throw some kind of an exception without any additional
     * code, except for Bidder.
     */
    @Test
    public void notifiesAuctionFailedWhenBidderMissing() {
        String badMessage = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5;";
        expectFailureMessage(badMessage);
        translator.processMessage(UNUSED_CHAT, message(badMessage));
    }
}
