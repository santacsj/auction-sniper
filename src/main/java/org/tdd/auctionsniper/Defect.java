package org.tdd.auctionsniper;

@SuppressWarnings("serial")
public class Defect extends RuntimeException {

    public Defect(String message) {
        super(message);
    }

    public Defect(Throwable cause) {
        super(cause);
    }

}
