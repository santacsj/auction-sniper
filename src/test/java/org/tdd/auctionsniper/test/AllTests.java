package org.tdd.auctionsniper.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuctionSniperTest.class, AuctionMessageTranslatorTest.class, SniperStateTest.class,
        org.tdd.auctionsniper.ui.test.AllTests.class })
public class AllTests {

}
