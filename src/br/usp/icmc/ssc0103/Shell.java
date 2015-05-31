package br.usp.icmc.ssc0103;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum UserType
{
    TUTOR, STUDENT, COMMUNITY
}

enum BookType
{
    TEXT, GENERAL
}

public class Shell
{
    enum Command
    {
        USERADD, CATALOGADD,
        CHECKOUT, CHECKIN,
        LIST, EXIT, NOOP
    }

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

            if (line.matches("^\\s*catalog\\s+add\\s+(.*[^\\\\,])\\s*;\\s*$")) {
                command = Command.CATALOGADD;
                System.out.println("catalogaddcmd premise...");
            } else if (line.matches("^\\s*user\\s+add\\s+(.*[^\\\\,])\\s*;\\s*$")) {
                System.out.println("useraddcmd premise...");
                command = Command.USERADD;
            } else if (line.matches("^\\s*list\\s+(.*[^\\\\,])\\s*;\\s*$")) {
                System.out.println("listcmd premise...");
                command = Command.LIST;
            } else if (line.matches("^\\s*exit\\s*;\\s*$")) {
                System.out.println("exitcmd premise...");
                command = Command.EXIT;
            }
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

            case CATALOGADD:
                //https://regex101.com/r/cZ7lK1/5
                pattern = Pattern.compile(
                        "^(?i)\\s*catalog\\s+add\\s+\"\\s*([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])" +
                                "\\s*\"\\s*(text|general|)\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    System.out.print("> Command: 'catalog add' Captures: '" + matcher.group(1) +
                                             "'");
                    if (!matcher.group(2).matches(""))
                        System.out.print(" '" + matcher.group(2) + "'");
                    System.out.print("\n");

                    //MANIPULATE DATABASE
                } else System.out.println("> Invalid syntax...");

                break;

            case USERADD:
                //https://regex101.com/r/cZ7lK1/6
                pattern = Pattern.compile(
                        "^(?i)\\s*user\\s+add\\s+\"\\s*([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\"\\s*" +
                                "(tutor|student|community|)\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    System.out.print("> Command: 'user add' Captures: '" + matcher.group(1) + "'");
                    if (!matcher.group(2).matches(""))
                        System.out.print(" '" + matcher.group(2) + "'");
                    System.out.print("\n");

                    //MANIPULATE DATABASE
                } else System.out.println("> Invalid syntax...");

                break;

            case CHECKOUT:
                break;
            case CHECKIN:
                break;
            case LIST:
                //https://regex101.com/r/qI7wF9/8
                pattern = Pattern.compile("^(?i)\\s*list\\s+(users|books|loans)(?:\\s+(users|books|loans))?(?:\\s+(users|books|loans))?\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    System.out.print("> Command: 'list' Captures: '" + matcher.group(1) + "'");
                    if (matcher.group(2)!=null)
                        System.out.print(" '" + matcher.group(2) + "'");
                    if (matcher.group(3)!=null)
                        System.out.print(" '" + matcher.group(3) + "'");
                    System.out.print("\n");

                    //MANIPULATE DATABASE
                } else System.out.println("> Invalid syntax...");

                break;

            case EXIT:
                break;
            case NOOP:
                break;
        }
    }
};
