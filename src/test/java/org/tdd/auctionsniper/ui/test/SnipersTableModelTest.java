package org.tdd.auctionsniper.ui.test;

import static org.hamcrest.Matchers.*;
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
        SniperSnapshot joining = SniperSnapshot.joining("item id");
        SniperSnapshot bidding = joining.bidding(555, 666);
        context.checking(new Expectations() {
            {
                allowing(listener).tableChanged(with(anyInsertionEvent()));
                oneOf(listener).tableChanged(with(aChangeInRow(0)));
            }
        });

        model.addSniper(joining);
        model.sniperStateChanged(bidding);

        assertRowMatchesSnapshot(0, bidding);
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

    @Test
    public void holdsSnipersInAdditionOrder() {
        context.checking(new Expectations() {
            {
                ignoring(listener);
            }
        });

        model.addSniper(SniperSnapshot.joining("item 0"));
        model.addSniper(SniperSnapshot.joining("item 1"));

        assertEquals("item 0", cellValue(0, Column.ITEM_IDENTIFIER));
        assertEquals("item 1", cellValue(1, Column.ITEM_IDENTIFIER));
    }

    private Object cellValue(int row, Column column) {
        return model.getValueAt(row, column.ordinal());
    }

    private void assertColumnEquals(int row, Column column, Object expected) {
        Assert.assertEquals(expected, cellValue(row, column));
    }

    private void assertRowMatchesSnapshot(int row, SniperSnapshot snapshot) {
        assertColumnEquals(row, Column.ITEM_IDENTIFIER, snapshot.itemId);
        assertColumnEquals(row, Column.LAST_PRICE, snapshot.lastPrice);
        assertColumnEquals(row, Column.LAST_BID, snapshot.lastBid);
        assertColumnEquals(row, Column.SNIPER_STATE, SnipersTableModel.textFor(snapshot.state));
    }

    private Matcher<TableModelEvent> aChangeInRow(int row) {
        return SamePropertyValuesAs.samePropertyValuesAs(new TableModelEvent(model, row));
    }

    private Matcher<TableModelEvent> anInsertionAtRow(int row) {
        return SamePropertyValuesAs.samePropertyValuesAs(new TableModelEvent(model, row, row,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    private Matcher<TableModelEvent> anyInsertionEvent() {
        return hasProperty("type", is(TableModelEvent.INSERT));
    }

}
