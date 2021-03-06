package org.tdd.auctionsniper.support;

import static org.hamcrest.CoreMatchers.*;
import static org.tdd.auctionsniper.support.FakeAuctionServer.*;

import java.io.IOException;

import javax.swing.SwingUtilities;

import org.junit.rules.ExternalResource;
import org.tdd.auctionsniper.*;
import org.tdd.auctionsniper.ui.MainWindow;
import org.tdd.auctionsniper.ui.SnipersTableModel;

public class ApplicationRunner extends ExternalResource {

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + FakeAuctionServer.XMPP_HOSTNAME
            + "/" + FakeAuctionServer.AUCTION_RESOURCE;

    private AuctionSniperDriver driver;
    private AuctionLogDriver logDriver = new AuctionLogDriver();

    public void startBiddingIn(FakeAuctionServer... auctions) {
        startSniper();
        for (FakeAuctionServer auction : auctions) {
            openBiddingFor(auction, Integer.MAX_VALUE);
        }
    }

    public void startBiddingWithStopPrice(FakeAuctionServer auction, int stopPrice) {
        startSniper();
        openBiddingFor(auction, stopPrice);
    }

    private void openBiddingFor(FakeAuctionServer auction, int stopPrice) {
        String itemId = auction.getItemId();
        driver.startBiddingFor(itemId, stopPrice);
        driver.showsSniperStatus(itemId, 0, 0, SnipersTableModel.textFor(SniperState.JOINING));
    }

    private void startSniper() {
        logDriver.clearLog();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(arguments());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock();
        driver = AuctionSniperDriver.withTimeout(1000);
        driver.hasTitle(MainWindow.APPLICATION_TITLE);
        driver.hasColumnTitles();
    }

    protected static String[] arguments() {
        String[] arguments = new String[3];
        arguments[0] = XMPP_HOSTNAME;
        arguments[1] = SNIPER_ID;
        arguments[2] = SNIPER_PASSWORD;
        return arguments;
    }

    /**
     * @see ahttp://higherorderlogic.com/2009/03/java-synchronisation-bug-on-osx/
     */
    private void makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                }
            });
        } catch (Exception e) {
            throw new Defect(e);
        }
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

    public void hasShownSniperIsLosing(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid,
                SnipersTableModel.textFor(SniperState.LOSING));
    }

    public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
        driver.showsSniperStatus(auction.getItemId(), lastPrice, lastPrice,
                SnipersTableModel.textFor(SniperState.WON));
    }

    public void showsSniperHasFailed(FakeAuctionServer auction) {
        driver.showsSniperStatus(auction.getItemId(), 0, 0,
                SnipersTableModel.textFor(SniperState.FAILED));
    }

    public void showsSniperHasLostAuction(FakeAuctionServer auction, int lastPrice, int lastBid) {
        driver.showsSniperStatus(auction.getItemId(), lastPrice, lastBid,
                SnipersTableModel.textFor(SniperState.LOST));
    }

    public void reportsInvalidMessage(FakeAuctionServer auction, String brokenMessage)
            throws IOException {
        logDriver.hasEntry(containsString(brokenMessage));
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
