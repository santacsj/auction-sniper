package org.tdd.auctionsniper.it;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.tdd.auctionsniper.xmpp.it.XMPPAuctionHouseIT;

@RunWith(Suite.class)
@SuiteClasses({ MainWindowIT.class, XMPPAuctionHouseIT.class })
public class AllITTests {

}
