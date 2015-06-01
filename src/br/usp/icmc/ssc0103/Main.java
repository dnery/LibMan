package br.usp.icmc.ssc0103;

import java.io.IOException;
import java.text.ParseException;

public class Main
{
    static Shell shell;

    public static void main(String[] args)
    {
        try {
            shell = new Shell(args);
            shell.runCommand();
        } catch (ParseException e){
            Formatter.outputError("Date format parse error, halting main routine...");
        } catch (IOException e) {
            Formatter.outputError("Input error detected, halting main routine...");
        }
    }
}
