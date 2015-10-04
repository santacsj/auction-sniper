package org.tdd.auctionsniper.ui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.*;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements SniperListener {
    private static final String[] STATUS_TEXT = { "Joining", "Bidding", "Winning", "Lost", "Won" };
    public static final SniperSnapshot JOINING = SniperSnapshot.joining("");

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    private SniperSnapshot snapshot = JOINING;
    private List<SniperSnapshot> snapshots = new LinkedList<SniperSnapshot>();

    @Override
    public int getRowCount() {
        return snapshots.size();
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
    }

    @Override
    public void sniperStateChanged(SniperSnapshot newSnapshot) {
        this.snapshot = newSnapshot;
        fireTableRowsUpdated(0, 0);
    }

    public void addSniper(SniperSnapshot snapshot) {
        int rowIndex = getRowCount();
        snapshots.add(snapshot);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

}
