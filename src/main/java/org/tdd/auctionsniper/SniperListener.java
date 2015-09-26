package org.tdd.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

    public void sniperStateChanged(SniperSnapshot newSnapshot);

    public void sniperLost();

    public void sniperWon();

}
