package org.tdd.auctionsniper.xmpp;

@SuppressWarnings("serial")
public class MissingValueException extends Exception {

    public MissingValueException(String name) {
        super(name);
    }

}
