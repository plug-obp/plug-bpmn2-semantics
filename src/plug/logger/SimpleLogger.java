package plug.logger;

import java.util.function.Consumer;

public class SimpleLogger implements Logger {

    private Consumer<String> logOutput;
    private Consumer<String> warningOutput;
    private Consumer<String> errorOutput;

    private int depth = 0;

    public SimpleLogger(Consumer<String> logOutput, Consumer<String> warningOutput, Consumer<String> errorOutput) {
        setLogOutput(logOutput);
        setWarningOutput(warningOutput);
        setErrorOutput(errorOutput);
    }

    public SimpleLogger(Consumer<String> commonOutput) {
        setAllOutput(commonOutput);
    }

    public SimpleLogger() {
        this(System.out::println);
    }

    public void setLogOutput(Consumer<String> logOutput) {
        this.logOutput = logOutput;
    }

    public void setWarningOutput(Consumer<String> warningOutput) {
        this.warningOutput = warningOutput;
    }

    public void setErrorOutput(Consumer<String> errorOutput) {
        this.errorOutput = errorOutput;
    }

    public void setAllOutput(Consumer<String> allOutput) {
        setLogOutput(allOutput);
        setWarningOutput(allOutput);
        setErrorOutput(allOutput);
    }

    @Override
    public int getLogDepth() {
        return depth;
    }

    @Override
    public void setLogDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void rawLog(String textString) {
        if (logOutput != null) {
            logOutput.accept(textString);
        }
    }

    @Override
    public void rawWarning(String textString) {
        if (warningOutput != null) {
            warningOutput.accept(textString);
        }
    }

    @Override
    public void rawError(String textString) {
        if (errorOutput != null) {
            errorOutput.accept(textString);
        }
    }

}
