package org.tdd.auctionsniper.it;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.tdd.auctionsniper.support.AuctionSniperDriver;
import org.tdd.auctionsniper.ui.MainWindow;
import org.tdd.auctionsniper.ui.SnipersTableModel;

import com.objogate.wl.swing.probe.ValueMatcherProbe;

public class MainWindowIT {
    private final SnipersTableModel tableModel = new SnipersTableModel();
    private final MainWindow mainWindow = new MainWindow(tableModel, null);
    private final AuctionSniperDriver driver = AuctionSniperDriver.withTimeout(1000);

    @Test
    public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<String> buttonProbe = new ValueMatcherProbe<String>(
                equalTo("an item-id"), "join request");

        mainWindow.addUserRequestListener(itemid -> buttonProbe.setReceivedValue(itemid));

        driver.startBiddingFor("an item-id");
        driver.check(buttonProbe);
    }
}
