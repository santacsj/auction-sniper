package org.tdd.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private final SniperListener sniperListener;
    private final Auction auction;
    private final String itemId;
    private boolean isWinning;

    public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
        this.itemId = itemId;
    }

    @Override
    public void auctionClosed() {
        if (isWinning)
            sniperListener.sniperWon();
        else
            sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = PriceSource.FromSniper == priceSource;

        if (isWinning) {
            sniperListener.sniperWinning();
        } else {
            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperBidding(new SniperState(itemId, price, bid));
        }
    }

}
