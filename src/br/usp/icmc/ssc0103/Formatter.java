package br.usp.icmc.ssc0103;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Formatter: Handles output by formatting streams to user-readable information
 */
public class Formatter
{
    /**
     * Pretty terminal color constants (Yay!)
     */
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    /**
     * Outputs users in database, formatted
     *
     * @param users List<user> to be scanned through
     * @param loans List<loan> to be scanned through
     * @param date  Date for validations
     */
    public static void outputUsers(List<User> users, List<Loan> loans, Date date)
    {
        // Print out tutors
        System.out.println(ANSI_YELLOW + "--University tutors--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.TUTOR)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(date))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(date, user.getSuspendedTill(), TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);
                     // Has any pending suspensions?
                 else if (user.getCurBooks() > 0 &&
                          loans.stream()
                               .filter(loan -> loan.getUserName().equals(user.getName()))
                               .anyMatch(loan -> date.after(loan.getCheckInDate())))
                     System.out.print("# " + ANSI_RED + user.getName() + " (Pending suspension)");
                     // If not just print username
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(ANSI_CYAN + " - Currently loaning " + user.getCurBooks() +
                                      " books");

                 // Append linebreak
                 System.out.print(ANSI_RESET + "\n");
             });

        // Print out students
        System.out.println(ANSI_YELLOW + "--University students--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.STUDENT)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(date))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(date, user.getSuspendedTill(), TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);
                     // Has any pending suspensions?
                 else if (user.getCurBooks() > 0 &&
                          loans.stream()
                               .filter(loan -> loan.getUserName().equals(user.getName()))
                               .anyMatch(loan -> date.after(loan.getCheckInDate())))
                     System.out.print("# " + ANSI_RED + user.getName() + " (Pending suspension)");
                     // If not just print username
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(ANSI_CYAN + " - Currently loaning " + user.getCurBooks() +
                                      " books");

                 // Append linebreak
                 System.out.print(ANSI_RESET + "\n");
             });

        // Print out community users
        System.out.println(ANSI_YELLOW + "--Local community users--" + ANSI_RESET);
        users.stream().filter(user -> user.getType() == UserType.COMMUNITY)
             .sorted(Comparator.comparing(User::getName))
             .forEach(user -> {
                 // Is currently suspended?
                 if (user.getSuspendedTill().after(date))
                     System.out.print("# " + ANSI_RED + user.getName() + " (" +
                                      getDateDiff(date, user.getSuspendedTill(), TimeUnit.DAYS) +
                                      " days suspended)" + ANSI_RESET);

                     // Has any pending suspensions?
                 else if (user.getCurBooks() > 0 &&
                          loans.stream()
                               .filter(loan -> loan.getUserName().equals(user.getName()))
                               .anyMatch(loan -> date.after(loan.getCheckInDate())))
                     System.out.print("# " + ANSI_RED + user.getName() + " (Pending suspension)");

                     // If not just print username
                 else System.out.print("# " + user.getName());

                 // Is loaning any books?
                 if (user.getCurBooks() > 0)
                     System.out.print(ANSI_CYAN + " - Currently loaning " + user.getCurBooks() +
                                      " books");

                 // Append linebreak
                 System.out.print(ANSI_RESET + "\n");
             });
    }

    /**
     * Outputs books in database, formatted
     *
     * @param books List<Book> to be scanned through
     */
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

    /**
     * outputLoans(): show all the loans in the database
     *
     * @param loans List<Loans> to be scanned through
     * @param date  Date for validation
     */
    public static void outputLoans(List<Loan> loans, Date date)
    {
        loans.stream()
             .sorted(Comparator.comparing(loan -> loan.getCheckOutDate().getTime() ==
                                                  loan.getRealCID().getTime()))
             .forEach(loan -> {
                 SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
                 String standard = "\"" + loan.getBookName() + "\" lent to " + loan.getUserName() +
                                   " on " + format.format(loan.getCheckOutDate());
                 if (loan.getCheckOutDate().getTime() == loan.getRealCID().getTime()) {

                     if (date.before(loan.getCheckInDate()))
                         System.out.println(ANSI_CYAN + standard + " - To be returned on " +
                                            format.format(loan.getCheckInDate()));
                     else
                         System.out.println(ANSI_RED + standard + " - Supposed to be returned on " +
                                            format.format(loan.getCheckInDate()) + "(Overdue)");
                 } else
                     System.out.println(ANSI_GREEN + standard + " (Closed)");
             });
    }

    /**
     * Display an error message, formatted
     *
     * @param message error message
     */
    public static void outputError(String message)
    {
        System.out.println("> " + ANSI_RED + message + ANSI_RESET);
    }

    /**
     * Get difference between two date units
     *
     * @param date1    Date to diff from
     * @param date2    Date to diff from
     * @param timeUnit Time unit sets the return format
     * @return difference between date1 and date2 as a specified 'TimeUnit'
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillis = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
};
