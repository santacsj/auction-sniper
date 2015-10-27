package org.tdd.auctionsniper.xmpp;

import org.jivesoftware.smack.XMPPException;

public class XMPPAuctionException extends XMPPException {

    public XMPPAuctionException(String message, Throwable wrappedThrowable) {
        super(message, wrappedThrowable);
    }

}
