package br.usp.icmc.ssc0103;

import java.util.Scanner;

enum UserType
{
    TUTOR,
    STUDENT,
    COMMUNITY
};

enum BookType
{
    TEXT,
    GENERAL
};

public class Shell
{
    private String line;
    private boolean isExit;

    Shell() { isExit = false; }

    public void runCommand()
    {
        Scanner userInput = new Scanner(System.in);

        while (userInput.hasNextLine()) {
            line = userInput.nextLine();
            System.out.println("> " + line);

            //LOOP
            checkCommand();
            triggerCommand();

            if (isExit) break;
        }
    }

    public void checkCommand()
    {
        if (line.matches("^(.*[^\\\\];)$"))
            System.out.println("It's a valid command!");
        else
            System.out.println("It's not a valid command...");
    }

    public void triggerCommand()
    {
        //catalog(...)() command

        //user(...)() command

        //exit() command
        if (line.matches("^\\s*exit\\s*;\\s*$")) {
            isExit = true;
        }
    }
};
