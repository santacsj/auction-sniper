package org.tdd.auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.*;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements SniperListener {
    private static final String[] STATUS_TEXT = { "Joining", "Bidding", "Winning", "Lost", "Won" };
    private static final SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0,
            SniperState.JOINING);

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

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
        return Column.at(columnIndex).valueIn(snapshot);
    }

    @Override
    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }

}
