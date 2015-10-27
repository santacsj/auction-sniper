package org.tdd.auctionsniper.support;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.tdd.auctionsniper.xmpp.XMPPAuctionHouse;

public class AuctionLogDriver {
    private final File logFile = new File(XMPPAuctionHouse.LOG_FILE_NAME);

    public void hasEntry(Matcher<String> matcher) throws IOException {
        assertThat(readFileToString(logFile), matcher);
    }

    /*
     * Note: The book uses Apache Commons IO for this
     */
    private static String readFileToString(File file) throws IOException {
        return Files.lines(file.toPath()).collect(Collectors.joining("\n"));
    }

    public void clearLog() {
        logFile.delete();
        LogManager.getLogManager().reset();
    }
}
