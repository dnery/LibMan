package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shell
{
    private enum Command
    {
        NOOP, USERADD, CATALOGADD, CHECKOUT, CHECKIN, LIST, EXIT
    }

    private Command command = Command.NOOP;
    private String line;

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

    // No capture groups when simply validating
    public boolean checkCommand()
    {
        if (line.matches("^(.*[^\\\\];)$")) {

            if (line.matches("^\\s*user\\s+add\\s+(?:[^\\\\,]*);\\s*$")) {
                System.out.println("useraddcmd premise...");
                command = Command.USERADD;
            } else if (line.matches("^\\s*catalog\\s+add\\s+(?:[^\\\\,]*);\\s*$")) {
                command = Command.CATALOGADD;
                System.out.println("catalogaddcmd premise...");
            } else if (line.matches("^(?:[^\\\\,]*)checkout(?:[^\\\\,]*);\\s*$")) {
                System.out.println("checkoutcmd premise...");
                command = Command.CHECKOUT;
            } else if (line.matches("^\\s*checkin\\s+(?:[^\\\\,]*);\\s*$")) {
                System.out.println("checkin premise...");
                command = Command.CHECKIN;
            } else if (line.matches("^\\s*list\\s+(?:[^\\\\,]*);\\s*$")) {
                System.out.println("listcmd premise...");
                command = Command.LIST;
            } else if (line.matches("^\\s*exit\\s*;\\s*$")) {
                System.out.println("exitcmd premise...");
                command = Command.EXIT;
            }
            return true;
        } else {
            System.out.println("> Not a valid command...");
            command = Command.NOOP;
            return false;
        }
    }

    public void triggerCommand()
    {
        Pattern pattern;
        Matcher matcher;

        switch (command) {

            case USERADD:
                //https://regex101.com/r/cZ7lK1/7
                pattern = Pattern.compile("^(?i)\\s*user\\s+add\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"(?:\\s+" +
                                          "(tutor|student|community))?\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    if (matcher.group(2) != null && matcher.group(2).equals("tutor"))
                        Database.getInstance().userAdd(matcher.group(1), UserType.TUTOR);
                    else if (matcher.group(2) != null && matcher.group(2).equals("student"))
                        Database.getInstance().userAdd(matcher.group(1), UserType.STUDENT);
                    else if (matcher.group(2) == null || matcher.group(2).equals("community"))
                        Database.getInstance().userAdd(matcher.group(1), UserType.COMMUNITY);
                } else
                    System.out.println("> Invalid syntax...");
                break;

            case CATALOGADD:
                //https://regex101.com/r/nU9qD4/1
                pattern = Pattern.compile("^(?i)\\s*catalog\\s+add\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"(?:\\s+" +
                                          "(text|general))?\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    if (matcher.group(2) != null && matcher.group(2).equals("text"))
                        Database.getInstance().catalogAdd(matcher.group(1), BookType.TEXT);
                    else if (matcher.group(2) == null || matcher.group(2).equals("general"))
                        Database.getInstance().catalogAdd(matcher.group(1), BookType.GENERAL);
                } else
                    System.out.println("> Invalid syntax...");
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
                    } catch (DatabaseException e) {
                        System.err.println("User or book does not exist!");
                    } catch (AccessException e) {
                        System.err.println("Loan is not authorized!");
                    } catch (AvailException e) {
                        System.err.println("Book already in use!");
                    }
                } else
                    System.out.println("> Invalid syntax...");
                break;

            case CHECKIN:
                //https://regex101.com/r/rT4hC9/2
                pattern = Pattern.compile("^(?i)\\s*checkin\\s+\\\"\\s*" +
                                          "([a-zA-Z][a-zA-Z\\s]+[a-zA-Z])\\s*\\\"\\s*;\\s*$");

                if ((matcher = pattern.matcher(line)).find()) {
                    try {
                        Database.getInstance().checkIn(matcher.group(1), new Date());
                    } catch (DatabaseException e) {
                        System.err.println("Book certainly does not exist!");
                    } catch (AvailException e) {
                        System.err.println("Book is already available!");
                    }
                } else
                    System.out.println("> Invalid syntax...");
                break;

            case LIST:
                //https://regex101.com/r/qI7wF9/10
                if (line.matches("^(?i)\\s*list(?:\\s+(?:users|books|loans))+\\s*;\\s*$")) {
                    pattern = Pattern.compile("(users|books|loans)");
                    matcher = pattern.matcher(line);

                    while (matcher.find()) {
                        if (matcher.group(1).equals("users"))
                            System.out.println("Listing registered users!");
                        else if (matcher.group(1).equals("books"))
                            System.out.println("Listing registered books!");
                        else if (matcher.group(1).equals("loans"))
                            System.out.println("Listing registered loans!");
                    }
                }
                break;

            default:
                break;
        }
    }
};
