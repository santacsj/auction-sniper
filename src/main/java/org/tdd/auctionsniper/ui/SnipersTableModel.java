package org.tdd.auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.SniperSnapshot;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel {
    private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, null);
    private String statusText = MainWindow.STATUS_JOINING;
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
            return statusText;
        default:
            throw new IllegalArgumentException("No column at " + columnIndex);
        }
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperSnapshot sniperState, String statusText) {
        this.sniperState = sniperState;
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }

}
