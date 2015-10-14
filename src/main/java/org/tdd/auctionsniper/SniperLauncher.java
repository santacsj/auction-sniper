package org.tdd.auctionsniper;

import java.util.ArrayList;

import org.tdd.auctionsniper.Main.SwingThreadSniperListener;
import org.tdd.auctionsniper.ui.SnipersTableModel;
import org.tdd.auctionsniper.ui.UserRequestListener;

public class SniperLauncher implements UserRequestListener {
    private final ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
    private final AuctionHouse auctionHouse;
    private final SnipersTableModel snipers;

    public SniperLauncher(AuctionHouse auctionHouse, SnipersTableModel snipers) {
        this.auctionHouse = auctionHouse;
        this.snipers = snipers;
    }

    @Override
    public void joinAuction(String itemId) {
        snipers.addSniper(SniperSnapshot.joining(itemId));
        Auction auction = auctionHouse.auctionFor(itemId);
        notToBeGCd.add(auction);
        auction.addAuctionEventListener(new AuctionSniper(itemId, auction,
                new SwingThreadSniperListener(snipers)));
        auction.join();
    }

}