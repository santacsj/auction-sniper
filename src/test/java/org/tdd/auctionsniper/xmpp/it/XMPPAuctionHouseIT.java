package org.tdd.auctionsniper.xmpp.it;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.tdd.auctionsniper.*;
import org.tdd.auctionsniper.support.ApplicationRunner;
import org.tdd.auctionsniper.support.FakeAuctionServer;
import org.tdd.auctionsniper.xmpp.XMPPAuctionHouse;

public class XMPPAuctionHouseIT {

    @Rule
    public final FakeAuctionServer auctionServer = new FakeAuctionServer("item-54321");

    private XMPPAuctionHouse auctionHouse;

    @Before
    public void setUpConnection() throws Exception {
        auctionHouse = XMPPAuctionHouse.connect(FakeAuctionServer.XMPP_HOSTNAME,
                ApplicationRunner.SNIPER_ID, ApplicationRunner.SNIPER_PASSWORD);
    }

    @After
    public void tearDownConnection() {
        auctionHouse.disconnect();
    }

    @Test
    public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
        CountDownLatch autionWasClosed = new CountDownLatch(1);

        Auction auction = auctionHouse.auctionFor(new Item(auctionServer.getItemId(),
                Integer.MAX_VALUE));
        auction.addAuctionEventListener(auctionClosedListener(autionWasClosed));

        auctionServer.startSellingItem();
        auction.join();
        auctionServer.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
        auctionServer.announceClosed();

        assertTrue("should have been closed", autionWasClosed.await(2, TimeUnit.SECONDS));
    }

    private AuctionEventListener auctionClosedListener(final CountDownLatch autionWasClosed) {
        return new AuctionEventListener() {

            @Override
            public void auctionClosed() {
                autionWasClosed.countDown();
            }

            @Override
            public void currentPrice(int price, int increment, PriceSource priceSource) {
                // not implemented
            }

            @Override
            public void auctionFailed() {
                // TODO Auto-generated method stub

            }

        };
    }
}
