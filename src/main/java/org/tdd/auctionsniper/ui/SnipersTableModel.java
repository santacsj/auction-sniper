package org.tdd.auctionsniper.ui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.tdd.auctionsniper.*;

@SuppressWarnings("serial")
public class SnipersTableModel extends AbstractTableModel implements SniperListener,
        PortfolioListener {
    private static final String[] STATUS_TEXT = { "Joining", "Bidding", "Winning", "Losing",
            "Lost", "Won", "Failed" };
    public static final SniperSnapshot JOINING = SniperSnapshot.joining("");

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }

    private List<SniperSnapshot> snapshots = new LinkedList<SniperSnapshot>();

    @Override
    public void sniperAdded(AuctionSniper sniper) {
        addSniper(sniper.getSnapshot());
        sniper.addSniperListener(new Main.SwingThreadSniperListener(this));
    }

    public void addSniper(SniperSnapshot snapshot) {
        int rowIndex = getRowCount();
        snapshots.add(snapshot);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

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
        int row = rowMatching(newSnapshot);
        snapshots.set(row, newSnapshot);
        fireTableRowsUpdated(row, row);
    }

    private int rowMatching(SniperSnapshot snapshot) {
        for (int i = 0; i < snapshots.size(); ++i) {
            if (snapshot.isForSameItemAs(snapshots.get(i)))
                return i;
        }
        throw new Defect("Cannot find match for " + snapshot);
    }

}
