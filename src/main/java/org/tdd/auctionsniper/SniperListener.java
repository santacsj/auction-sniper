package org.tdd.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

    public void sniperLost();

    public void sniperBidding();

}
