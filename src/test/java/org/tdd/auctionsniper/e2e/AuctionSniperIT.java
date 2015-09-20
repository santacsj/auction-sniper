package org.tdd.auctionsniper.e2e;

import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.support.ApplicationRunner;
import org.tdd.auctionsniper.support.FakeAuctionServer;

public class AuctionSniperIT {

    @Rule
    public final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    @Rule
    public final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding();

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

}