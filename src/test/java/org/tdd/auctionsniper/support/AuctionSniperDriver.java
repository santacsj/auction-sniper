package org.tdd.auctionsniper.support;

import org.hamcrest.Matchers;
import org.tdd.auctionsniper.Main;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.JLabelTextMatcher;

@SuppressWarnings("unchecked")
public class AuctionSniperDriver extends JFrameDriver {

    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME),
                showingOnScreen()), new AWTEventQueueProber(timeoutMillis, 100));
    }

    public void showsSniperStatus(String statusText) {
        new JTableDriver(this)
                .hasCell(JLabelTextMatcher.withLabelText(Matchers.equalTo(statusText)));
    }

}
