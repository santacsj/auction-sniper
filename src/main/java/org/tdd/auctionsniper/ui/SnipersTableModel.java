package org.tdd.auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.SniperSnapshot;
import org.tdd.auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
    private static final String[] STATUS_TEXT = { "Joining", "Bidding", "Winning", "Lost", "Won" };
    private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0,
            SniperState.JOINING);
    private SniperSnapshot snapshot = STARTING_UP;

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
        case ITEM_IDENTIFIER:
            return snapshot.itemId;
        case LAST_PRICE:
            return snapshot.lastPrice;
        case LAST_BID:
            return snapshot.lastBid;
        case SNIPER_STATE:
            return textFor(snapshot.state);
        default:
            throw new IllegalArgumentException("No column at " + columnIndex);
        }
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    public void sniperStatusChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }

}
