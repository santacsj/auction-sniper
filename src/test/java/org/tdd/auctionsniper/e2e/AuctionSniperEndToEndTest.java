package org.tdd.auctionsniper.e2e;

import org.junit.*;
import org.tdd.auctionsniper.support.*;

public class AuctionSniperEndToEndTest {

    @Rule
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    @Rule
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

}