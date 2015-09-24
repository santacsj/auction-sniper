package org.tdd.auctionsniper;

public interface AuctionEventListener {

    public enum PriceSource {
        FromSniper, FromOtherBidder;
    }

    void auctionClosed();

    void currentPrice(int price, int increment, PriceSource priceSource);

}
