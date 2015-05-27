package br.usp.icmc.ssc0103;

import java.util.Date;

public class Main
{
    static Shell shell;

    public static void main(String[] args)
    {
        //shell = new Shell();
        //shell.runCommand();

        Date date1 = new Date();
        Date date2 = new Date((long) (1.296e+9 + date1.getTime()));

        System.out.println(date1.toString() + " - " + date2.toString());
    }
}

