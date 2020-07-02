package io.smallrye.testing.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LogEmptyTest {
    @RegisterExtension
    static LogCapture logCapture = LogCapture.none();

    private final Logger logger = Logger.getLogger(LogEmptyTest.class);

    @Test
    void testEmptyLogContent() {
        logger.debug("Writing a debug level message.");
        logger.info("Writing an info level message.");
        logger.warn("Writing a warning level message.");
        logger.trace("Writing a trace level message.");

        assertThat(LogEmptyTest.logCapture.records()).hasSize(0);
    }
}
