package net.catchpole.console;


import java.io.PrintStream;

public class ConsoleWriter {
    private PrintStream printStream;

    public ConsoleWriter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void eraseScreen() {
        printStream.print("\u001b[2J");
    }

    public void clearLine() {
        printStream.print("\u001b[K");
    }

    public void reverse() {
        printStream.print("\u001b[7m");
    }

    public void regular() {
        printStream.print("\u001b[0m");
    }

    public void print(String string) {
        printStream.print(string);
    }

    public void gotoPosition(int row, int column) {
        printStream.print(String.format("\u001b[%d;%df", row, column));
    }
}