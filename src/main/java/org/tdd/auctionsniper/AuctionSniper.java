package org.tdd.auctionsniper;

import org.jmock.example.announcer.Announcer;

public class AuctionSniper implements AuctionEventListener {

    private final Announcer<SniperListener> sniperListeners = Announcer.to(SniperListener.class);
    private final Auction auction;
    private final Item item;
    private SniperSnapshot snapshot;

    public AuctionSniper(Item item, Auction auction) {
        this.auction = auction;
        this.item = item;
        this.snapshot = SniperSnapshot.joining(item.identifier);
    }

    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch (priceSource) {
        case FromSniper:
            snapshot = snapshot.winning(price);
            break;
        case FromOtherBidder:
            int bid = price + increment;
            if (item.allowsBid(bid)) {
                auction.bid(bid);
                snapshot = snapshot.bidding(price, bid);
            } else {
                snapshot = snapshot.losing(price);
            }
            break;
        }
        notifyChange();
    }

    @Override
    public void auctionFailed() {
        // TODO Auto-generated method stub

    }

    private void notifyChange() {
        sniperListeners.announce().sniperStateChanged(snapshot);
    }

    public SniperSnapshot getSnapshot() {
        return snapshot;
    }

    public void addSniperListener(SniperListener listener) {
        sniperListeners.addListener(listener);
    }

}
