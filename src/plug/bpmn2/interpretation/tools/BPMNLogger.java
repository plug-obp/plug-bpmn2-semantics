package plug.bpmn2.interpretation.tools;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.tools.analysis.resource.BPMN2PrinterShort;
import plug.logger.SimpleLogger;

import java.util.function.Consumer;

public class BPMNLogger extends SimpleLogger {

    private final BPMN2PrinterShort shortPrinter;

    public BPMNLogger(Consumer<String> logOutput, Consumer<String> warningOutput, Consumer<String> errorOutput) {
        super(logOutput, warningOutput, errorOutput);
        shortPrinter = new BPMN2PrinterShort();
    }

    public BPMNLogger(Consumer<String> commonOutput) {
        this(commonOutput, commonOutput, commonOutput);
    }

    public BPMNLogger() {
        this(System.out::println);
    }

    private String getString(Object object) {
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

    @Override
    public String authorString(Object author) {
        return getString(author);
    }

    @Override
    public String subjectString(Object subject) {
        return getString(subject);
    }

    @Override
    public String textString(Object text) {
        return getString(text);
    }

}
