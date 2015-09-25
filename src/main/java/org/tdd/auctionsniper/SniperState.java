package org.tdd.auctionsniper;

public class SniperState {

    public final String itemId;
    public final int lastPrice;
    public final int lastBid;

    public SniperState(final String itemId, final int lastPrice, final int lastBid) {
        this.itemId = itemId;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        result = prime * result + lastBid;
        result = prime * result + lastPrice;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SniperState))
            return false;
        SniperState other = (SniperState) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        if (lastBid != other.lastBid)
            return false;
        if (lastPrice != other.lastPrice)
            return false;
        return true;
    }

}
