package org.tdd.auctionsniper.test;

import org.junit.Assert;
import org.junit.Test;
import org.tdd.auctionsniper.Defect;
import org.tdd.auctionsniper.SniperState;

public class SniperStateTest {

    @Test
    public void joiningChangesToLostWhenAuctionClosed() {
        Assert.assertEquals(SniperState.LOST, SniperState.JOINING.whenAuctionClosed());
    }

    @Test
    public void biddingChangesToLostWhenAuctionClosed() {
        Assert.assertEquals(SniperState.LOST, SniperState.BIDDING.whenAuctionClosed());
    }

    @Test
    public void winningChangesToWonWhenAuctionClosed() {
        Assert.assertEquals(SniperState.WON, SniperState.WINNING.whenAuctionClosed());
    }

    @Test(expected = Defect.class)
    public void lostIsATerminalState() {
        SniperState.LOST.whenAuctionClosed();
    }

    @Test(expected = Defect.class)
    public void wonIsATerminalState() {
        SniperState.WON.whenAuctionClosed();
    }
}
