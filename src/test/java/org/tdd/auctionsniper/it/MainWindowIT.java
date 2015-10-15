package org.tdd.auctionsniper.it;

import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.tdd.auctionsniper.Item;
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
        final ValueMatcherProbe<Item> itemProbe = new ValueMatcherProbe<Item>(equalTo(new Item(
                "an item-id", 789)), "item request");

        mainWindow.addUserRequestListener(item -> itemProbe.setReceivedValue(item));

        driver.startBiddingFor("an item-id", 789);
        driver.check(itemProbe);
    }
}
