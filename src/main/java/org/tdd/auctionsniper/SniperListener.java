package org.tdd.auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {

    public void sniperStateChanged(SniperSnapshot sniperState);

    public void sniperLost();

    public void sniperWinning();

    public void sniperWon();

}
