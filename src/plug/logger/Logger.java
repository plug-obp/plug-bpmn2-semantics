package plug.logger;

import java.util.Arrays;

public interface Logger {

    int getLogDepth();
    void setLogDepth(int depth);
    void rawLog(String textString);
    void rawWarning(String textString);
    void rawError(String textString);

    default void increaseLogDepth() {
        setLogDepth(getLogDepth() + 1);
    }

    default void decreaseLogDepth() {
        int logDepth = getLogDepth();
        if (logDepth != 0) {
            setLogDepth(getLogDepth() - 1);
        }
    }

    default int getTabSize() {
        return 2;
    }

    default String getTabs() {
        char[] tabArray = new char[getLogDepth() * getTabSize()];
        Arrays.fill(tabArray, ' ');
        return new String(tabArray);
    }

    default String getTabbedString(String textString) {
        return getTabs() + textString;
    }

    default void log(String logString) {
        rawLog(getTabbedString(logString));
    }

    default void warning(String warningString) {
        rawWarning(getTabbedString(warningString));
    }

    default void error(String errorString) {
        rawError(getTabbedString(errorString));
    }

    default String authorString(Object author) {
        return author.toString();
    }

    default String subjectString(Object subject) {
        return subject.toString();
    }

    default String textString(Object text) {
        return text.toString();
    }

    default String entryString(Object author, Object subject, Object text) {
        String authorString = author == null ? "" : authorString(author);
        String subjectString = subject == null ? "" : subjectString(subject);
        String textString = textString(text);
        StringBuilder builder = new StringBuilder();
        if (authorString.length() > 0) {
            builder.append("[")
                    .append(authorString)
                    .append("] ");
        }
        builder.append(textString);
        if (subjectString.length() > 0) {
            builder.append(" <")
                    .append(subjectString)
                    .append(">");
        }
        return builder.toString();
    }

    default void log(Object author, Object subject, Object text) {
        log(entryString(author, subject, text));
    }

    default void warning(Object author, Object subject, Object text) {
        warning(entryString(author, subject, text));
    }

    default void error(Object author, Object subject, Object text) {
        error(entryString(author, subject, text));
    }

    default void log(int depth, Object author, Object subject, Object text) {
        int oldDepth = getLogDepth();
        setLogDepth(depth);
        log(author, subject, text);
        setLogDepth(oldDepth);
    }

    default void warning(int depth, Object author, Object subject, Object text) {
        int oldDepth = getLogDepth();
        setLogDepth(depth);
        warning(author, subject, text);
        setLogDepth(oldDepth);
    }

    default void error(int depth, Object author, Object subject, Object text) {
        int oldDepth = getLogDepth();
        setLogDepth(depth);
        error(author, subject, text);
        setLogDepth(oldDepth);
    }

    default void openSection(Object author, Object subject, Object text) {
        log(author, subject, text);
        increaseLogDepth();
    }

    default void closeSection() {
        decreaseLogDepth();
    }

}
