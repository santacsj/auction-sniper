package org.tdd.auctionsniper.ui.test;

import static org.junit.Assert.*;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;
import org.tdd.auctionsniper.SniperSnapshot;
import org.tdd.auctionsniper.SniperState;
import org.tdd.auctionsniper.ui.Column;
import org.tdd.auctionsniper.ui.SnipersTableModel;

public class SnipersTableModelTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final TableModelListener listener = context.mock(TableModelListener.class);
    private final SnipersTableModel model = new SnipersTableModel();

    @Before
    public void attachModelListener() {
        model.addTableModelListener(listener);
    }

    @Test
    public void hasEnoughColumns() {
        Assert.assertThat(model.getColumnCount(), Matchers.equalTo(Column.values().length));
    }

    @Test
    public void setSniperValuesInColumns() {
        context.checking(new Expectations() {
            {
                oneOf(listener).tableChanged(with(aRowChangeEvent()));
            }
        });

        model.sniperStateChanged(new SniperSnapshot("item id", 555, 666, SniperState.BIDDING));

        assertColumnEquals(Column.ITEM_IDENTIFIER, "item id");
        assertColumnEquals(Column.LAST_PRICE, 555);
        assertColumnEquals(Column.LAST_BID, 666);
        assertColumnEquals(Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.BIDDING));
    }

    @Test
    public void setsUpColumnHeadings() {
        for (Column column : Column.values())
            Assert.assertEquals(column.name, model.getColumnName(column.ordinal()));
    }

    @Test
    public void notifiesListenersWhenAddingASniper() {
        SniperSnapshot joining = SniperSnapshot.joining("item123");
        context.checking(new Expectations() {
            {
                oneOf(listener).tableChanged(with(anInsertionAtRow(0)));
            }
        });

        assertEquals(0, model.getRowCount());

        model.addSniper(joining);

        assertEquals(1, model.getRowCount());
        assertRowMatchesSnapshot(0, joining);
    }

    private void assertColumnEquals(Column column, Object expected) {
        int rowIndex = 0;
        int columnIndex = column.ordinal();
        Assert.assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }

    private Matcher<TableModelEvent> aRowChangeEvent() {
        return SamePropertyValuesAs.samePropertyValuesAs(new TableModelEvent(model, 0));
    }

    private Matcher<TableModelEvent> anInsertionAtRow(int row) {
        return SamePropertyValuesAs.samePropertyValuesAs(new TableModelEvent(model, row, row,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    private void assertRowMatchesSnapshot(int row, SniperSnapshot snapshot) {
        assertColumnEquals(Column.ITEM_IDENTIFIER, snapshot.itemId);
        assertColumnEquals(Column.LAST_PRICE, snapshot.lastPrice);
        assertColumnEquals(Column.LAST_BID, snapshot.lastBid);
        assertColumnEquals(Column.SNIPER_STATE, snapshot.state);
    }

}
