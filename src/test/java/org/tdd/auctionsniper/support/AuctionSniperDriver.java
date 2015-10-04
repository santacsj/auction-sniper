package org.tdd.auctionsniper.support;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.hamcrest.Matchers;
import org.tdd.auctionsniper.Main;
import org.tdd.auctionsniper.ui.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.*;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;
import com.objogate.wl.swing.matcher.JLabelTextMatcher;

@SuppressWarnings("unchecked")
public class AuctionSniperDriver extends JFrameDriver {

    public static AuctionSniperDriver withTimeout(int timeoutMillis) {
        // Needed by WindowLicker on OSX
        System.setProperty("com.objogate.wl.keyboard", "Mac-GB");
        return new AuctionSniperDriver(timeoutMillis);
    }

    private AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME),
                showingOnScreen()), new AWTEventQueueProber(timeoutMillis, 100));
    }

    public void showsSniperStatus(String statusText) {
        new JTableDriver(this)
                .hasCell(JLabelTextMatcher.withLabelText(Matchers.equalTo(statusText)));
    }

    public void showsSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
        JTableDriver table = new JTableDriver(this);
        table.hasRow(IterableComponentsMatcher.matching(JLabelTextMatcher.withLabelText(itemId),
                JLabelTextMatcher.withLabelText(Integer.toString(lastPrice)),
                JLabelTextMatcher.withLabelText(Integer.toString(lastBid)),
                JLabelTextMatcher.withLabelText(statusText)));
    }

    public void hasColumnTitles() {
        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(IterableComponentsMatcher.matching(
                JLabelTextMatcher.withLabelText("Item"),
                JLabelTextMatcher.withLabelText("Last Price"),
                JLabelTextMatcher.withLabelText("Last Bid"),
                JLabelTextMatcher.withLabelText("State")));

    }

    public void startBiddingFor(String itemId) {
        itemIdField().replaceAllText(itemId);
        bidButton().click();
    }

    private JTextFieldDriver itemIdField() {
        JTextFieldDriver newItemId = new JTextFieldDriver(this, JTextField.class,
                named(MainWindow.NEW_ITEM_ID_NAME));
        newItemId.focusWithMouse();
        return newItemId;
    }

    private JButtonDriver bidButton() {
        return new JButtonDriver(this, JButton.class, named(MainWindow.JOIN_BUTTON_NAME));
    }

}
