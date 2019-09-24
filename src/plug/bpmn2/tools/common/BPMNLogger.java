package plug.bpmn2.tools.common;

import org.eclipse.emf.ecore.EObject;

import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BPMNLogger extends Handler {

    private final Logger logger;
    private final BPMN2PrinterShort shortPrinter;
    private Consumer<String> logOutput;

    public BPMNLogger(Consumer<String> logOutput) {
        this.logOutput = logOutput;
        logger = Logger.getLogger("BPMN");
        while (logger.getHandlers().length > 0) {
            logger.removeHandler(logger.getHandlers()[logger.getHandlers().length - 1]);
        }
        logger.setUseParentHandlers(false);
        logger.addHandler(this);
        shortPrinter = new BPMN2PrinterShort();
    }

    public BPMNLogger() {
        this(System.out::println);
    }

    @Override
    public void publish(LogRecord record) {
        logOutput.accept(record.getMessage());
    }

    @Override
    public void flush() { }

    @Override
    public void close() throws SecurityException {}

    public void setOutput(Consumer<String> logOutput) {
        this.logOutput = logOutput;
    }

    private String getString(Object object) {
        if (object == null) return "";
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof EObject) {
            return shortPrinter.getShortString((EObject) object);
        }
        String className = object.getClass().getTypeName();
        String[] classNames = className.split("\\.");
        return classNames[classNames.length - 1];
    }

    private String getString(Level level, Object author, Object subject, Object text) {
        StringBuilder builder = new StringBuilder()
                .append("[")
                .append(level)
                .append("]");
        String authorString = getString(author);
        if (authorString.length() > 0) {
            builder.append("[")
                    .append(authorString)
                    .append("]");
        }
        String textString = getString(text);
        if (textString.length() > 0) {
            builder.append(" ")
                    .append(textString);
        }
        String subjectString = getString(subject);
        if (subjectString.length() > 0) {
            builder.append(" <")
                    .append(subjectString)
                    .append(">");
        }
        return builder.toString();
    }

    public void log(Level level, Object author, Object subject, Object text) {
        logger.log(level, () -> getString(level, author, subject, text));
    }

    public void log(Object author, Object subject, Object text) {
        log(Level.INFO, author, subject, text);
    }

    public void log(String text) {
        logger.log(Level.INFO, text);
    }

}
