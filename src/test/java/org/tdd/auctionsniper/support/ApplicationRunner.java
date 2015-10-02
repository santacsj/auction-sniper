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

    public void startBiddingIn(FakeAuctionServer... auctions) {
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(arguments(auctions));
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

    protected static String[] arguments(FakeAuctionServer... auctions) {
        String[] arguments = new String[auctions.length + 3];
        arguments[0] = XMPP_HOSTNAME;
        arguments[1] = SNIPER_ID;
        arguments[2] = SNIPER_PASSWORD;
        for (int i = 0; i < auctions.length; i++)
            arguments[i + 3] = auctions[i].getItemId();
        return arguments;
    }

    public void showsSniperHasLostAuction(FakeAuctionServer auction) {
        driver.showsSniperStatus(SnipersTableModel.textFor(SniperState.LOST));
    }

    public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid,
                SnipersTableModel.textFor(SniperState.BIDDING));

    }

    public void hasShownSniperIsWinning(FakeAuctionServer auction, int lastBid) {
        driver.showsSniperStatus(auction.getItemId(), lastBid, lastBid,
                SnipersTableModel.textFor(SniperState.WINNING));

    }

    public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
        driver.showsSniperStatus(auction.getItemId(), lastPrice, lastPrice,
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
