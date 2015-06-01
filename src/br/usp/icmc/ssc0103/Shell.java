package br.usp.icmc.ssc0103;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command
{
    NOOP, USERADD, CATALOGADD, CHECKOUT, CHECKIN, LIST, EXIT
}

public class Shell
{
    // Eager start
    private String line;
    private Command command = Command.NOOP;

    // Watches for inputs
    public void runCommand()
    {
        Scanner userInput = new Scanner(System.in);

        while (command != Command.EXIT) {
            System.out.print("> ");

            if (userInput.hasNextLine()) {
                line = userInput.nextLine();

                if (checkCommand())
                    triggerCommand();
            }
        }
    }

    // Pre-validates commands
    public boolean checkCommand()
    {
        if (line.matches("^(.*[^\\\\];)$")) {

            if (line.matches("^\\s*user\\s+add\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.USERADD;
            else if (line.matches("^\\s*catalog\\s+add\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.CATALOGADD;
            else if (line.matches("^(?:[^\\\\,]*)checkout(?:[^\\\\,]*);\\s*$"))
                command = Command.CHECKOUT;
            else if (line.matches("^\\s*checkin\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.CHECKIN;
            else if (line.matches("^\\s*list\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.LIST;
            else if (line.matches("^\\s*exit\\s*;\\s*$"))
                command = Command.EXIT;
            return true;
        } else {
            System.out.println("> Not a valid command...");
            command = Command.NOOP;
            return false;
        }
    }

    // Fully launches commands
    public void triggerCommand()
    {
        // Make it local
        Pattern pattern;
        Matcher matcher;

        // Simple switch
        switch (command) {

            case NOOP:
                break;
            case USERADD:
                //https://regex101.com/r/cZ7lK1/7
                pattern = Pattern.compile("^(?i)\\s*user\\s+add\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"(?:\\s+" +
                                          "(tutor|student|community))?\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        if (matcher.group(2) != null && matcher.group(2).equals("tutor"))
                            Database.getInstance().userAdd(matcher.group(1), UserType.TUTOR);
                        else if (matcher.group(2) != null && matcher.group(2).equals("student"))
                            Database.getInstance().userAdd(matcher.group(1), UserType.STUDENT);
                        else if (matcher.group(2) == null || matcher.group(2).equals("community"))
                            Database.getInstance().userAdd(matcher.group(1), UserType.COMMUNITY);
                    } catch(DatabaseException e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                break;

            case CATALOGADD:
                //https://regex101.com/r/nU9qD4/1
                pattern = Pattern.compile("^(?i)\\s*catalog\\s+add\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"(?:\\s+" +
                                          "(text|general))?\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        if (matcher.group(2) != null && matcher.group(2).equals("text"))
                            Database.getInstance().catalogAdd(matcher.group(1), BookType.TEXT);
                        else if (matcher.group(2) == null || matcher.group(2).equals("general"))
                            Database.getInstance().catalogAdd(matcher.group(1), BookType.GENERAL);
                    } catch (DatabaseException e){
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                break;

            case CHECKOUT:
                //https://regex101.com/r/lV3vI3/1
                pattern = Pattern.compile("^(?i)\\s*user\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])" +
                                          "\\s*\\\"\\s+checkout\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        Database.getInstance().checkOut(matcher.group(1),
                                                        matcher.group(2),
                                                        new Date());
                    } catch (Exception e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                break;

            case CHECKIN:
                //https://regex101.com/r/rT4hC9/2
                pattern = Pattern.compile("^(?i)\\s*checkin\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        Database.getInstance().checkIn(matcher.group(1), new Date());
                    } catch (Exception e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                break;

            case LIST:
                //https://regex101.com/r/qI7wF9/10
                if (line.matches("^(?i)\\s*list(?:\\s+(?:users|books|loans))+\\s*;\\s*$")) {
                    pattern = Pattern.compile("(users|books|loans)");
                    matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        if (matcher.group(1).equals("users"))
                            Formatter.outputUsers(Database.getInstance().getUsers());
                        else if (matcher.group(1).equals("books"))
                            Formatter.outputBooks(Database.getInstance().getBooks());
                        else if (matcher.group(1).equals("loans"))
                            System.out.println("Listing registered loans!");
                    }
                }
                break;

            case EXIT:
                try {
                    Database.getInstance().serializeAndUpdate();
                } catch (IOException e) {
                    Formatter.outputError(e.getMessage());
                }
                break;

            default:
                break;
        }
    }
};
