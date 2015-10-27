package org.tdd.auctionsniper.xmpp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuctionMessageTranslatorTest.class, LoggingXMPPFailureReporterTest.class })
public class AllTests {

}
