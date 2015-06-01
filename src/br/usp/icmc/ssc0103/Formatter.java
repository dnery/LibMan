package br.usp.icmc.ssc0103;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Formatter
{
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    public static void outputUsers(List<User> users)
    {
        // Get a fixed time
        Date today = new Date();

        // Print out tutors
        System.out.println(ANSI_YELLOW + "--University tutors--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.TUTOR)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(today))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(user.getSuspendedTill(), today, TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(" - Currently loaning " + user.getCurBooks() + " books\n");
                 else System.out.print("\n");
             });

        // Print out students
        System.out.println(ANSI_YELLOW + "--University students--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.STUDENT)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(today))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(user.getSuspendedTill(), today, TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(" - Currently loaning " + user.getCurBooks() + " books\n");
                 else System.out.print("\n");
             });

        // Print out community users
        System.out.println(ANSI_YELLOW + "--Local community users--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.COMMUNITY)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(today))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(user.getSuspendedTill(), today, TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(" - Currently loaning " + user.getCurBooks() + " books\n");
                 else System.out.print("\n");
             });
    }

    public static void outputBooks(List<Book> books)
    {
        // Print out general books
        System.out.println(ANSI_YELLOW + "--Literary open use books--" + ANSI_RESET);
        books.stream().filter(book -> book.getType() == BookType.GENERAL)
             .sorted(Comparator.comparing(Book::isAvail).reversed())
             .forEach(book -> {
                 if (book.isAvail())
                     System.out.print("# " + ANSI_GREEN);
                 else
                     System.out.print("# " + ANSI_RED);
                 System.out.println(book.getName() + ANSI_RESET);
             });

        // Print out academic books
        System.out.println(ANSI_YELLOW + "--Academic use only books--" + ANSI_RESET);
        books.stream().filter(book -> book.getType() == BookType.TEXT)
             .sorted(Comparator.comparing(Book::isAvail).reversed())
             .forEach(book -> {
                 if (book.isAvail())
                     System.out.print("# " + ANSI_GREEN);
                 else
                     System.out.print("# " + ANSI_RED);
                 System.out.println(book.getName() + ANSI_RESET);
             });
    }

    public static void outputLoans(List<Loan> loans) { }

    public static void outputError(String message)
    {
        System.out.println("> " + ANSI_RED + message + ANSI_RESET);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillis = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
};
