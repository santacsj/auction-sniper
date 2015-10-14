package org.tdd.auctionsniper.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.tdd.auctionsniper.xmpp.test.AuctionMessageTranslatorTest;

@RunWith(Suite.class)
@SuiteClasses({ AuctionSniperTest.class, AuctionMessageTranslatorTest.class, SniperStateTest.class,
        SniperLauncherTest.class, org.tdd.auctionsniper.ui.test.AllTests.class,
        org.tdd.auctionsniper.xmpp.test.AllTests.class })
public class AllTests {

}
