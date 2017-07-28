package net.catchpole.console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ConsolePanel {
    private ConsoleWriter consoleWriter;

    public ConsolePanel(PrintStream printStream) {
        this.consoleWriter = new ConsoleWriter(printStream);
        this.consoleWriter.eraseScreen();
    }

    public ConsoleWriter getConsoleWriter() {
        return consoleWriter;
    }

    public DataUpdate<String> addHeading(int line) {
        return new StringDataUpdate(line, true);
    }

    public DataUpdate<String> addRow(int line) {
        return new StringDataUpdate(line, false);
    }

    public DataUpdate<List<String>> addList(int line, int rows) {
        return new ListDataUpdate(line, rows);
    }

    private class StringDataUpdate implements DataUpdate<String> {
        private int line;
        private boolean reverse;

        public StringDataUpdate(int line, boolean reverse) {
            this.line = line;
            this.reverse = reverse;
        }

        public void update(String string) {
            synchronized (consoleWriter) {
                if (reverse) {
                    consoleWriter.reverse();
                }
                consoleWriter.gotoPosition(line, 0);
                consoleWriter.clearLine();
                consoleWriter.print(string);
                if (reverse) {
                    int spaces = 80 - string.length();
                    if (spaces > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int x=0;x<spaces;x++) {
                            stringBuilder.append(' ');
                        }
                        consoleWriter.print(stringBuilder.toString());
                    }
                    consoleWriter.regular();
                }
            }
        }

        public void clear() {
            synchronized (consoleWriter) {
                consoleWriter.gotoPosition(line, 0);
                consoleWriter.clearLine();
            }
        }
    }

    private class ListDataUpdate implements DataUpdate<List<String>> {
        private int rows;
        private List<StringDataUpdate> stringDataUpdateList = new ArrayList<StringDataUpdate>();

        public ListDataUpdate(int line, int rows) {
            this.rows = rows;
            for (int x=0;x<rows;x++) {
                stringDataUpdateList.add(new StringDataUpdate(line+x, false));
            }
        }

        public void update(List<String> list) {
            int total = list.size() <= rows ? list.size() : rows;
            for (int x=0;x<total;x++) {
                stringDataUpdateList.get(x).update(list.get(x));
            }
            for (int x=total;x<rows;x++) {
                stringDataUpdateList.get(x).clear();
            }
        }

        public void clear() {
            for (int x=0;x<rows;x++) {
                stringDataUpdateList.get(x).clear();
            }
        }
    }
}