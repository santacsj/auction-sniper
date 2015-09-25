package org.tdd.auctionsniper.test;

import org.jmock.Expectations;
import org.jmock.States;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.tdd.auctionsniper.*;
import org.tdd.auctionsniper.AuctionEventListener.PriceSource;

public class AuctionSniperTest {

    private final static String ITEM_ID = "";

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final Auction auction = context.mock(Auction.class);
    private final States sniperState = context.states("sniper");
    private final AuctionSniper sniper = new AuctionSniper(ITEM_ID, auction, sniperListener);

    @Test
    public void reportsLostWhenAuctionClosesImmediately() {
        context.checking(new Expectations() {
            {
                atLeast(1).of(sniperListener).sniperLost();
            }
        });
        sniper.auctionClosed();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() {
        context.checking(new Expectations() {
            {
                ignoring(auction);

                allowing(sniperListener).sniperBidding(with(any(SniperState.class)));
                then(sniperState.is("bidding"));

                atLeast(1).of(sniperListener).sniperLost();
                when(sniperState.is("bidding"));
            }
        });

        sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
        sniper.auctionClosed();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
        int price = 1001;
        int increment = 25;
        int bid = price + increment;

        context.checking(new Expectations() {
            {
                oneOf(auction).bid(price + increment);
                atLeast(1).of(sniperListener).sniperBidding(new SniperState(ITEM_ID, price, bid));
            }
        });

        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {
            {
                atLeast(1).of(sniperListener).sniperWinning();
            }
        });

        sniper.currentPrice(123, 45, PriceSource.FromSniper);
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {
            {

                ignoring(auction);

                allowing(sniperListener).sniperWinning();
                then(sniperState.is("winning"));

                atLeast(1).of(sniperListener).sniperWon();
                when(sniperState.is("winning"));
            }
        });

        sniper.currentPrice(123, 45, PriceSource.FromSniper);
        sniper.auctionClosed();
    }

}
