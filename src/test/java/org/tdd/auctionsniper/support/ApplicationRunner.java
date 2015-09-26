package org.tdd.auctionsniper.support;

import static org.tdd.auctionsniper.support.FakeAuctionServer.*;

import org.junit.rules.ExternalResource;
import org.tdd.auctionsniper.Main;
import org.tdd.auctionsniper.SniperState;
import org.tdd.auctionsniper.ui.SnipersTableModel;

public class ApplicationRunner extends ExternalResource {

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + FakeAuctionServer.XMPP_HOSTNAME
            + "/" + FakeAuctionServer.AUCTION_RESOURCE;

    private AuctionSniperDriver driver;
    private String itemId;

    public void startBiddingIn(FakeAuctionServer auction) {
        itemId = auction.getItemId();

        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(SnipersTableModel.textFor(SniperState.JOINING));
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(SnipersTableModel.textFor(SniperState.LOST));
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid,
                SnipersTableModel.textFor(SniperState.BIDDING));

    }

    public void hasShownSniperIsWinning(int lastBid) {
        driver.showsSniperStatus(itemId, lastBid, lastBid,
                SnipersTableModel.textFor(SniperState.WINNING));

    }

    public void showsSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemId, lastPrice, lastPrice,
                SnipersTableModel.textFor(SniperState.WON));
    }

    public void stop() {
        if (driver != null)
            driver.dispose();
    }

    @Override
    protected void after() {
        stop();
    }

}
