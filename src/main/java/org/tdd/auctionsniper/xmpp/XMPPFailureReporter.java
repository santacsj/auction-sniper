package org.tdd.auctionsniper.xmpp;

public interface XMPPFailureReporter {
    void cannotTranslateMessage(String autionId, String failureMessage, Exception exception);
}
