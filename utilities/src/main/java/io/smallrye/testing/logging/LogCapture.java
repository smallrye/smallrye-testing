package io.smallrye.testing.logging;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LogCapture implements BeforeAllCallback {

    private static final Logger rootLogger;

    private InMemoryLogHandler inMemoryLogHandler = new InMemoryLogHandler((r) -> false);

    static {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        rootLogger = LogManager.getLogManager().getLogger("");
    }

    public static LogCapture none() {
        return new LogCapture();
    }

    public static LogCapture with(Predicate<LogRecord> predicate) {
        return LogCapture.with(predicate, Level.INFO);
    }

    public static LogCapture with(Predicate<LogRecord> predicate, Level logLevel) {
        LogCapture capture = new LogCapture(predicate);
        return capture.setLevel(logLevel);
    }

    private LogCapture() {
        // Capture nothing by default
    }

    private LogCapture(Predicate<LogRecord> predicate) {
        inMemoryLogHandler = new InMemoryLogHandler(predicate);
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        rootLogger.addHandler(inMemoryLogHandler);
    }

    private LogCapture setLevel(Level newLevel) {
        rootLogger.setLevel(newLevel);
        inMemoryLogHandler.setLevel(newLevel);
        return this;
    }

    public List<LogRecord> records() {
        return inMemoryLogHandler.records;
    }
}
