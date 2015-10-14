package org.tdd.auctionsniper;

import java.util.ArrayList;

import org.jmock.example.announcer.Announcer;

public class SniperPortfolio implements SniperCollector {

    private final ArrayList<AuctionSniper> notToBeGCd = new ArrayList<AuctionSniper>();
    private final Announcer<PortfolioListener> portfolioListeners = Announcer
            .to(PortfolioListener.class);

    @Override
    public void addSniper(AuctionSniper sniper) {
        notToBeGCd.add(sniper);
        portfolioListeners.announce().sniperAdded(sniper);
    }

    public void addPortfolioListener(PortfolioListener listener) {
        portfolioListeners.addListener(listener);
    }

}
