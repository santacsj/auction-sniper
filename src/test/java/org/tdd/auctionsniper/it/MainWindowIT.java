package org.tdd.auctionsniper.it;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.tdd.auctionsniper.SniperPortfolio;
import org.tdd.auctionsniper.support.AuctionSniperDriver;
import org.tdd.auctionsniper.ui.MainWindow;

import com.objogate.wl.swing.probe.ValueMatcherProbe;

public class MainWindowIT {
    private final SniperPortfolio portfolio = new SniperPortfolio();
    private final MainWindow mainWindow = new MainWindow(portfolio);
    private final AuctionSniperDriver driver = AuctionSniperDriver.withTimeout(1000);

    @Test
    public void makesUserRequestWhenJoinButtonClicked() {
        final ValueMatcherProbe<String> buttonProbe = new ValueMatcherProbe<String>(
                equalTo("an item-id"), "join request");

        mainWindow.addUserRequestListener(itemid -> buttonProbe.setReceivedValue(itemid));

        driver.startBiddingFor("an item-id", Integer.MAX_VALUE);
        driver.check(buttonProbe);
    }
}
