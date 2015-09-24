package org.tdd.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private final SniperListener sniperListener;
    private final Auction auction;
    private boolean isWinning;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
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
            auction.bid(price + increment);
            sniperListener.sniperBidding();
        }
    }

}
