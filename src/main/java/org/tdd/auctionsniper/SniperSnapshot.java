package org.tdd.auctionsniper;

public class SniperSnapshot {

    public static SniperSnapshot joining(String itemId) {
        return new SniperSnapshot(itemId, 0, 0, SniperState.JOINING);
    }

    public final String itemId;
    public final int lastPrice;
    public final int lastBid;
    public final SniperState state;

    public SniperSnapshot(final String itemId, final int lastPrice, final int lastBid,
            SniperState state) {
        this.itemId = itemId;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
        this.state = state;
    }

    public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
        return new SniperSnapshot(itemId, newLastPrice, newLastBid, SniperState.BIDDING);
    }

    public SniperSnapshot winning(int newLastPrice) {
        return new SniperSnapshot(itemId, newLastPrice, lastBid, SniperState.WINNING);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        result = prime * result + lastBid;
        result = prime * result + lastPrice;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SniperSnapshot))
            return false;
        SniperSnapshot other = (SniperSnapshot) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        if (lastBid != other.lastBid)
            return false;
        if (lastPrice != other.lastPrice)
            return false;
        if (state != other.state)
            return false;
        return true;
    }

}
