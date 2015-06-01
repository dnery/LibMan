package br.usp.icmc.ssc0103;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Date    date;
    private String  line;
    private Command command;

    // Constructor tries to parse arguments
    public Shell(String[] args) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.parse(args[0]);
        command = Command.NOOP;

        System.out.println(date.toString());
    }

    // Watches for command inputs
    public void runCommand() throws IOException
    {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        while (command != Command.EXIT) {
            System.out.print("> ");
            line = userInput.readLine();
            if (checkCommand()) triggerCommand();
        }
    }

    // Pre-validates inputs
    private boolean checkCommand()
    {
        if (line.matches("^(.*[^\\\\];)$")) {

            if (line.matches("^\\s*user\\s+add\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.USERADD;
            else if (line.matches("^\\s*catalog\\s+add\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.CATALOGADD;
            else if (line.matches("^\\s*lend(?:[^\\\\,]*)to(?:[^\\\\,]*);\\s*$"))
                command = Command.CHECKOUT;
            else if (line.matches("^\\s*return\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.CHECKIN;
            else if (line.matches("^\\s*list\\s+(?:[^\\\\,]*);\\s*$"))
                command = Command.LIST;
            else if (line.matches("^\\s*exit\\s*;\\s*$"))
                command = Command.EXIT;
            else {
                command = Command.NOOP;
                Formatter.outputError("Could not be parsed...");
            }
            return true;
        } else {
            Formatter.outputError("Not a valid command...");
            return false;
        }
    }

    // Fully launches the commands
    private boolean triggerCommand()
    {
        // Make it local
        Pattern pattern;
        Matcher matcher;

        // Simple switch
        switch (command) {

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
                    } catch (DatabaseException e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                return true;

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
                    } catch (DatabaseException e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                return true;

            case CHECKOUT:
                //https://regex101.com/r/lV3vI3/2
                pattern = Pattern.compile(
                        "^(?i)\\s*lend\\s+\\\"\\s*([a-zA-Z0-9][a-zA-Z0-9\\s]+[a-zA-Z0-9])" +
                        "\\s*\\\"\\s+to\\s+\\\"\\s*([a-zA-Z0-9][a-zA-Z0-9\\s]+[a-zA-Z0-9])" +
                        "\\s*\\\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        Database.getInstance().checkOut(matcher.group(2), matcher.group(1), date);
                    } catch (Exception e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                return true;

            case CHECKIN:
                //https://regex101.com/r/rT4hC9/3
                pattern = Pattern.compile(
                        "^(?i)\\s*return\\s+\\\"\\s*([a-zA-Z0-9][a-zA-Z0-9\\s]+[a-zA-Z0-9])" +
                        "\\s*\\\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        Database.getInstance().checkIn(matcher.group(1), date);
                    } catch (Exception e) {
                        Formatter.outputError(e.getMessage());
                    }
                } else
                    Formatter.outputError("Invalid syntax...");
                return true;

            case LIST:
                //https://regex101.com/r/qI7wF9/10
                if (line.matches("^(?i)\\s*list(?:\\s+(?:users|books|loans))+\\s*;\\s*$")) {
                    pattern = Pattern.compile("(users|books|loans)");
                    matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        if (matcher.group(1).equals("users"))
                            Formatter.outputUsers(Database.getInstance().getUsers(),
                                                  Database.getInstance().getLoans(),
                                                  date);
                        else if (matcher.group(1).equals("books"))
                            Formatter.outputBooks(Database.getInstance().getBooks());
                        else if (matcher.group(1).equals("loans"))
                            System.out.println("Listing registered loans!");
                    }
                }
                return true;

            case EXIT:
                try {
                    Database.getInstance().serializeAndUpdate();
                    System.out.println();
                } catch (IOException e) {
                    Formatter.outputError(e.getMessage());
                }
                return true;

            default:

                return false;
        }
    }
};
