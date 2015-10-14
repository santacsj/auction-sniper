package org.tdd.auctionsniper;

public interface AuctionHouse {
    Auction auctionFor(String itemId);

    void disconnect();
}
