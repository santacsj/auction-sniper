package org.tdd.auctionsniper.xmpp;

import java.util.logging.Logger;

public class LoggingXMPPFailureReporter implements XMPPFailureReporter {
    private final Logger logger;

    public LoggingXMPPFailureReporter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void cannotTranslateMessage(String autionId, String failureMessage, Exception exception) {
        logger.severe(String.format("<%s> Could not translate message \"%s\" because \"%s: %s\"",
                autionId, failureMessage, exception.getClass().getName(),
                exception.getLocalizedMessage()));
    }

}
