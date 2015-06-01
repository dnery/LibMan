package br.usp.icmc.ssc0103;

import java.io.IOException;
import java.text.ParseException;

public class Main
{
    static Shell shell;

    /**
     * Main, calls Shell.run.Commands()
     * @param args arguments from console
     */
    public static void main(String[] args)
    {
        try {
            if (args.length != 1) {
                Formatter.outputError("Missing the date argument, please see UTILIZACAO");
                System.exit(1);
            }
            shell = new Shell(args);
            shell.runCommand();
        } catch (ParseException e){
            Formatter.outputError("Date format parse error, halting main routine...");
        } catch (IOException e) {
            Formatter.outputError("Input error detected, halting main routine...");
        }
    }
}
