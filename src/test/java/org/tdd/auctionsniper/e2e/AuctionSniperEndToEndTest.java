package org.tdd.auctionsniper.e2e;

import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.support.ApplicationRunner;
import org.tdd.auctionsniper.support.FakeAuctionServer;

public class AuctionSniperEndToEndTest {

    @Rule
    public final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    @Rule
    public final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

}