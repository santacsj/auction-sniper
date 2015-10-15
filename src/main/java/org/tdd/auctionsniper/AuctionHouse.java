package org.tdd.auctionsniper;

public interface AuctionHouse {
    Auction auctionFor(Item item);

    void disconnect();
}
