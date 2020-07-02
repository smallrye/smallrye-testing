package io.smallrye.testing.logging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LogLevelSetTest {
    @RegisterExtension
    static LogCapture logCapture = LogCapture.with(r -> LogLevelSetTest.class.getName().equals(r.getLoggerName()),
            Level.ALL);

    private final Logger logger = Logger.getLogger(LogLevelSetTest.class);
    private final Logger anotherLogger = Logger.getLogger("io.smallrye.testing.logging.AnotherLogger");

    private static final String INFO = "Writing an info level message.";
    private static final String WARNING = "Writing a warning level message.";
    private static final String DEBUG = "Writing a debug level message.";
    private static final String TRACE = "Writing a trace level message.";

    @Test
    void testLogLevelSetting() {
        logger.debug(DEBUG);
        logger.info(INFO);
        logger.warn(WARNING);
        logger.trace(TRACE);

        anotherLogger.debug("Another logger writing a debug message.");
        anotherLogger.info(INFO);
        anotherLogger.trace(TRACE);

        List<LogRecord> records = LogLevelSetTest.logCapture.records();
        assertThat(records).hasSize(4);
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(INFO))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(INFO));
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(WARNING))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(WARNING));
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(DEBUG))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(DEBUG));
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(TRACE))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(TRACE));
    }
}
