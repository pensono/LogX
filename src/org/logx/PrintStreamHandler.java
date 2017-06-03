package org.logx;

import java.io.PrintStream;

/**
 * @author Ethan
 */
public class PrintStreamHandler implements LogXHandler {
    private PrintStream output;

    public PrintStreamHandler(PrintStream out){
        output = out;
    }

    @Override
    public void handle(LogXMessage record) {
        // TODO message formatting
        output.println(record.getMessage() + ": " + record.getAllData());
    }
}
