package org.tdd.auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.SniperSnapshot;
import org.tdd.auctionsniper.SniperState;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
    private static final String[] STATUS_TEXT = { MainWindow.STATUS_JOINING,
            MainWindow.STATUS_BIDDING };
    private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0,
            SniperState.JOINING);
    private String state = MainWindow.STATUS_JOINING;
    private SniperSnapshot sniperState = STARTING_UP;

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
            return sniperState.itemId;
        case LAST_PRICE:
            return sniperState.lastPrice;
        case LAST_BID:
            return sniperState.lastBid;
        case SNIPER_STATE:
            return state;
        default:
            throw new IllegalArgumentException("No column at " + columnIndex);
        }
    }

    public void setStatusText(String statusText) {
        this.state = statusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperSnapshot newSnapshot) {
        this.sniperState = newSnapshot;
        this.state = STATUS_TEXT[newSnapshot.state.ordinal()];
        fireTableRowsUpdated(0, 0);
    }

}
