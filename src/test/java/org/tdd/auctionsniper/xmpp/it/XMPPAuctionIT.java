package org.tdd.auctionsniper.xmpp.it;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.XMPPConnection;
import org.junit.*;
import org.tdd.auctionsniper.*;
import org.tdd.auctionsniper.support.ApplicationRunner;
import org.tdd.auctionsniper.support.FakeAuctionServer;
import org.tdd.auctionsniper.xmpp.XMPPAuction;

public class XMPPAuctionIT {

    @Rule
    public final FakeAuctionServer auctionServer = new FakeAuctionServer("item-54321");

    private XMPPConnection connection;

    @Before
    public void setUpConnection() throws Exception {
        connection = new XMPPConnection(FakeAuctionServer.XMPP_HOSTNAME);
        connection.connect();
        connection.login(ApplicationRunner.SNIPER_ID, ApplicationRunner.SNIPER_PASSWORD,
                FakeAuctionServer.AUCTION_RESOURCE);
    }

    @After
    public void tearDownConnection() {
        connection.disconnect();
    }

    @Test
    public void receivesEventsFromAuctionServerAfterJoining() throws Exception {
        CountDownLatch autionWasClosed = new CountDownLatch(1);

        Auction auction = new XMPPAuction(connection, auctionServer.getItemId());
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

        };
    }
}
