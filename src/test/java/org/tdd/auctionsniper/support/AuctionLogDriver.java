package org.tdd.auctionsniper.support;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;

public class AuctionLogDriver {
    public static final String LOG_FILE_NAME = "auction-sniper.log";
    private final File logFile = new File(LOG_FILE_NAME);

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
