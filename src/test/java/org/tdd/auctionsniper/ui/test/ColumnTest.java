package org.tdd.auctionsniper.ui.test;

import org.junit.Assert;
import org.junit.Test;
import org.tdd.auctionsniper.SniperSnapshot;
import org.tdd.auctionsniper.SniperState;
import org.tdd.auctionsniper.ui.Column;
import org.tdd.auctionsniper.ui.SnipersTableModel;

public class ColumnTest {

    @Test
    public void testValueIn() {
        SniperSnapshot snapshot = new SniperSnapshot("item-123", 1, 2, SniperState.BIDDING);

        Column[] columns = Column.values();

        Object[] expected = { snapshot.itemId, snapshot.lastPrice, snapshot.lastBid,
                SnipersTableModel.textFor(snapshot.state) };

        for (int i = 0; i < columns.length; ++i)
            Assert.assertEquals(expected[i], columns[i].valueIn(snapshot));
    }

}
