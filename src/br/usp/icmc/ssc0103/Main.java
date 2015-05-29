package br.usp.icmc.ssc0103;

import java.util.Date;

public class Main
{
    static Shell shell;

    public static void main(String[] args)
    {
        shell = new Shell();
        shell.runCommand();

        Date date  = new Date(0);
        System.out.println(date.toString());
    }
}

