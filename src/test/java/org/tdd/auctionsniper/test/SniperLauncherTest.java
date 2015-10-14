package org.tdd.auctionsniper.test;

import org.hamcrest.Matcher;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.*;

public class SniperLauncherTest {

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final States auctionState = context.states("auction state").startsAs("not joined");

    private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
    private final Auction auction = context.mock(Auction.class);
    private final SniperCollector collector = context.mock(SniperCollector.class);
    private final SniperLauncher launcher = new SniperLauncher(auctionHouse, collector);

    @Test
    public void addsNewSniperToCollectorAndThenJoinsAuction() {
        String itemId = "item 123";
        context.checking(new Expectations() {
            {
                allowing(auctionHouse).auctionFor(itemId);
                will(returnValue(auction));

                oneOf(auction).addAuctionEventListener(with(sniperForItem(itemId)));
                when(auctionState.is("not joined"));

                oneOf(collector).addSniper(with(sniperForItem(itemId)));
                when(auctionState.is("not joined"));

                oneOf(auction).join();
                then(auctionState.is("joined"));
            }
        });

        launcher.joinAuction(itemId);
    }

    private Matcher<AuctionSniper> sniperForItem(String itemId) {
        return SamePropertyValuesAs.samePropertyValuesAs(new AuctionSniper(itemId, auction, null));
    }
}
