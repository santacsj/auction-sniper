package org.tdd.auctionsniper.support;

import static org.tdd.auctionsniper.support.FakeAuctionServer.*;

import org.junit.rules.ExternalResource;
import org.tdd.auctionsniper.Main;
import org.tdd.auctionsniper.SniperState;
import org.tdd.auctionsniper.ui.MainWindow;
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
        driver.hasTitle(MainWindow.APPLICATION_TITLE);
        driver.hasColumnTitles();
        driver.showsSniperStatus(SnipersTableModel.JOINING.itemId,
                SnipersTableModel.JOINING.lastPrice, SnipersTableModel.JOINING.lastBid,
                SnipersTableModel.textFor(SnipersTableModel.JOINING.state));
    }

    public void showsSniperHasLostAuction(FakeAuctionServer auction) {
        driver.showsSniperStatus(SnipersTableModel.textFor(SniperState.LOST));
    }

    public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemId, lastPrice, lastBid,
                SnipersTableModel.textFor(SniperState.BIDDING));

    }

    public void hasShownSniperIsWinning(FakeAuctionServer auction, int lastBid) {
        driver.showsSniperStatus(itemId, lastBid, lastBid,
                SnipersTableModel.textFor(SniperState.WINNING));

    }

    public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
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
