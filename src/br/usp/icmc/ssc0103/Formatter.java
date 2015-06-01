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
        System.out.println(ANSI_YELLOW + "--University tutors--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.TUTOR)
             .forEach(user -> System.out.println("- " + user.getName()));
        System.out.println(ANSI_YELLOW + "--University students--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.STUDENT)
             .forEach(user -> System.out.println("- " + user.getName()));
        System.out.println(ANSI_YELLOW + "--Local community users--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.COMMUNITY)
             .forEach(user -> System.out.println("- " + user.getName()));
    }

    public static void outputBooks(List<Book> books)
    {
        System.out.println(ANSI_YELLOW + "--Literary open use books--" + ANSI_RESET);
        books.stream().filter(book -> book.getType() == BookType.GENERAL)
             .sorted(Comparator.comparing(Book::isAvail))
             .forEachOrdered(book -> {
                 if (book.isAvail())
                     System.out.print(ANSI_GREEN);
                 else
                     System.out.print(ANSI_RED);
                 System.out.println(book.getName() + ANSI_RESET);
             });
        System.out.println(ANSI_YELLOW + "--Academic use only books--" + ANSI_RESET);
        books.stream().filter(book -> book.getType() == BookType.TEXT)
             .sorted(Comparator.comparing(Book::isAvail))
             .forEachOrdered(book -> {
                 if (book.isAvail())
                     System.out.print(ANSI_GREEN);
                 else
                     System.out.print(ANSI_RED);
                 System.out.println(book.getName() + ANSI_RESET);
             });
    }

    public static void outputLoans(List<Loan> loans) {  }

    public static void outputError(String message){
        System.out.println("> " + ANSI_RED + message + ANSI_RESET);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillis = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
};
