package io.smallrye.testing.logging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.logging.LogRecord;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LogFilteringTest {
    @RegisterExtension
    static LogCapture logCapture = LogCapture.with(r -> LogFilteringTest.class.getName().equals(r.getLoggerName()));

    private final Logger logger = Logger.getLogger(LogFilteringTest.class);
    private final Logger anotherLogger = Logger.getLogger("io.smallrye.testing.logging.AnotherLogger");

    private static final String INFO = "Writing an info level message.";
    private static final String WARNING = "Writing a warning level message.";

    @Test
    void testFilteredCategory() {
        logger.debug("Writing a debug level message.");
        logger.info(INFO);
        logger.warn(WARNING);
        logger.trace("Writing a trace level message.");

        anotherLogger.debug("Another logger writing a debug message.");
        anotherLogger.info(INFO);

        List<LogRecord> records = LogFilteringTest.logCapture.records();
        assertThat(records).hasSize(2);
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(INFO))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(INFO));
        assertThat(records)
                .filteredOn(r -> r.getMessage().contains(WARNING))
                .hasOnlyOneElementSatisfying(r -> assertThat(r.getMessage()).contains(WARNING));
    }
}
