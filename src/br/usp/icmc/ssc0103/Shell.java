package br.usp.icmc.ssc0103;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

enum Command
{
    NOOP,
    USERADD,
    CATALOGADD,
    CHECKOUT,
    CHECKIN,
    EXIT
};

public class Shell
{
    private String line;
    private Command command;

    private Pattern pattern;
    private Matcher matcher;

    Shell() { command = Command.NOOP; }

    public void runCommand()
    {
        Scanner userInput = new Scanner(System.in);

        while (command != Command.EXIT) {
            System.out.print("> ");

            if (userInput.hasNextLine())
                line = userInput.nextLine();

            if (checkCommand())
                triggerCommand();
        }
    }

    public boolean checkCommand()
    {
        if (line.matches("^(.*[^\\\\];)$")) {

            if (line.matches("^\\s*catalog\\s+add\\s+\\\"(.*[^\\\\])\\\"\\s*;\\s*$"))
                command = Command.CATALOGADD;
            else if (line.matches("^\\s*user\\s+add\\s+\\\"(.*[^\\\\])\\\"\\s*;\\s*$"))
                command = Command.USERADD;
            else if (line.matches("^\\s*exit\\s*;\\s*$"))
                command = Command.EXIT;

            return true;
        } else {
            System.out.println("> Invalid command...");
            command = Command.NOOP;
            return false;
        }
    }

    public void triggerCommand()
    {
        switch (command) {
            //catalog add command
            case CATALOGADD:
                pattern = Pattern.compile(
                        "^\\s*catalog\\s+add\\s+\"\\s*([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\"\\s*;"
                                + "\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    System.out.println("> Command: \"catalog add\" Captures: \"" + matcher.group
                            (1) + "\"");

                    //MANIPULATE DATABASE
                } else {
                    System.out.println("> Invalid syntax...");
                }
                break;

            //user add command
            case USERADD:
                pattern = Pattern.compile(
                        "^\\s*user\\s+add\\s+\"\\s*([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    System.out.println("> Command: \"user add\" Captures: \"" + matcher.group(1)
                                               + "\"");

                    //MANIPULATE DATABASE
                } else {
                    System.out.println("> Invalid syntax...");
                }
                break;
        }
    }
};
