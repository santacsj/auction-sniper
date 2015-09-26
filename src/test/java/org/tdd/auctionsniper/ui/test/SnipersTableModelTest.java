package org.tdd.auctionsniper.ui.test;

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
import org.tdd.auctionsniper.ui.*;

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

        model.sniperStatusChanged(new SniperSnapshot("item id", 555, 666, SniperState.BIDDING));

        assertColumnEquals(Column.ITEM_IDENTIFIER, "item id");
        assertColumnEquals(Column.LAST_PRICE, 555);
        assertColumnEquals(Column.LAST_BID, 666);
        assertColumnEquals(Column.SNIPER_STATE, MainWindow.STATUS_BIDDING);
    }

    private void assertColumnEquals(Column column, Object expected) {
        int rowIndex = 0;
        int columnIndex = column.ordinal();
        Assert.assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }

    private Matcher<TableModelEvent> aRowChangeEvent() {
        return SamePropertyValuesAs.samePropertyValuesAs(new TableModelEvent(model, 0));
    }

}
