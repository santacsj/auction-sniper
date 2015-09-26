package org.tdd.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private final SniperListener sniperListener;
    private final Auction auction;
    private boolean isWinning;
    private SniperSnapshot snapshot;

    public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
        this.snapshot = SniperSnapshot.joining(itemId);
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
            snapshot = snapshot.winning(price);
        } else {
            int bid = price + increment;
            auction.bid(bid);
            snapshot = snapshot.bidding(price, bid);
        }
        sniperListener.sniperStateChanged(snapshot);
    }

}
