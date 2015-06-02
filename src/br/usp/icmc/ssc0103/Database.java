package br.usp.icmc.ssc0103;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Database level attribute validity
class DatabaseException extends Exception
{
    public DatabaseException() { super(); }

    public DatabaseException(String message) { super(message); }
}

// Interface level client clearance
class AccessException extends Exception
{
    public AccessException() { super(); }

    public AccessException(String message) { super(message); }
}

// Interface level book availability
class AvailException extends Exception
{
    public AvailException() { super(); }

    public AvailException(String message) { super(message); }
}

public class Database
{
    private List<User> users;
    private List<Book> books;
    private List<Loan> loans;
    private String     userFileName;
    private String     bookFileName;
    private String     loanFileName;

    // Fix root directory name and build it atomically
    private static final File databaseDir = new File("database");

    // Static initializer is always run before class is used by any thread
    private static final Database database = new Database("database/users.csv",
                                                          "database/books.csv",
                                                          "database/loans.csv");

    // Private constructor according to Singleton standards
    private Database(String userFileName, String bookFileName, String loanFileName)
    {
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
        loans = new ArrayList<Loan>();

        if (!databaseDir.exists())
            databaseDir.mkdir();

        this.userFileName = userFileName;
        this.bookFileName = bookFileName;
        this.loanFileName = loanFileName;

        loadUsers();
        loadBooks();
        loadLoans();
    }

    // The only existing instance is obtained
    public static Database getInstance() { return database; }

    // "user add (...)" command backend
    public void userAdd(String userName, UserType userType) throws DatabaseException
    {
        // Only add if user doesn't already exist
        if (users.stream().noneMatch(user -> user.getName().equals(userName))) {
            users.add(new User(userName, userType));

            if (userType == UserType.TUTOR)
                System.out.println("Tutor " + userName + " has been added!");
            else if (userType == UserType.STUDENT)
                System.out.println("Student " + userName + " has been added!");
            else
                System.out.println("Community " + userName + " has been added!");
        } else throw new DatabaseException("User has already been registered!");
    }

    // "catalog add (...)" command backend
    public void catalogAdd(String bookName, BookType bookType) throws DatabaseException
    {
        // Only add if book doesn't already exist
        if (books.stream().noneMatch(book -> book.getName().equals(bookName))) {
            books.add(new Book(bookName, bookType));

            if (bookType == BookType.TEXT)
                System.out.println("Textbook " + bookName + " has been catalogued!");
            else
                System.out.println("Literary " + bookName + " has been catalogued!");
        } else throw new DatabaseException("Book has already been catalogued!");
    }

    // "user (...) checkout (...)" command backend
    public void checkOut(String userName, String bookName, Date date) throws
                                                                      DatabaseException,
                                                                      AccessException,
                                                                      AvailException
    {
        // Only process if both user and book exist in database
        if (users.stream().anyMatch(user -> user.getName().equals(userName)) &&
            books.stream().anyMatch(book -> book.getName().equals(bookName))) {
            // User is always unique, so just get it
            User userObject = users.stream()
                                   .filter(user -> user.getName().equals(userName))
                                   .findFirst()
                                   .get();

            // Book is always unique, so just get it
            Book bookObject = books.stream()
                                   .filter(book -> book.getName().equals(bookName))
                                   .findFirst()
                                   .get();

            // Permission check, 1st step
            if (userObject.getSuspendedTill().after(date))
                throw new AccessException("User is still suspended!");

            // Permission check, 1st step (intrusive)
            if (userObject.getCurBooks() > 0 &&
                loans.stream()
                     .filter(loan -> loan.getUserName().equals(userObject.getName()))
                     .anyMatch(loan -> date.after(loan.getCheckInDate())))
                throw new AccessException("Unlogged user suspension!");

            // Permission check, 2nd step
            if (userObject.getType() == UserType.COMMUNITY &&
                bookObject.getType() == BookType.TEXT)
                throw new AccessException("Loan is not authorized!");

            // Permission check, 3rd step
            if (userObject.getCurBooks() >= userObject.getMaxBooks())
                throw new AccessException("Loan limit maxed-out!");

            // Check if book is in use
            if (!bookObject.isAvail())
                throw new AvailException("Book is already in use!");

            // Actual processing
            loans.add(new Loan(userName, bookName, date, userObject.getLoanDuration()));
            userObject.setCurBooks(userObject.getCurBooks() + 1);
            bookObject.setAvail(false);

            System.out.println("> " + bookObject.getName() + " lent to " + userObject.getName());

        } else throw new DatabaseException("User or book not found!");
    }

    // "checkin (...)" command backend
    public void checkIn(String bookName, Date date) throws DatabaseException,
                                                           AvailException
    {
        // Only process book actually exists in database
        if (books.stream().anyMatch(book -> book.getName().equals(bookName))) {
            // Book is always unique, so just get it
            Book bookObject = books.stream()
                                   .filter(book -> book.getName().equals(bookName))
                                   .findFirst()
                                   .get();
            // Evaluate the collection result
            System.out.println("Book found: " + bookObject.toString());

            // Only if unavailable...
            if (!bookObject.isAvail()) {
                // Get the last occurrence of the book loan
                Loan loanObject = loans.stream()
                                       .filter(loan -> loan.getBookName()
                                                           .equals(bookObject.getName()))
                                       .reduce((previous, current) -> current)
                                       .get();
                // And the respective user it was lent to
                User userObject = users.stream()
                                       .filter(user -> user.getName()
                                                           .equals(loanObject.getUserName()))
                                       .findFirst()
                                       .get();

                // Actual processing
                bookObject.setAvail(true);
                loanObject.setRealCID(date);
                userObject.setCurBooks(userObject.getCurBooks() - 1);

                System.out.println("> " + bookObject.getName() + " has been returned");

                // Set suspension date if needed
                if (date.after(loanObject.getCheckInDate())) {
                    long difference = date.getTime() - loanObject.getCheckInDate().getTime();

                    // If not yet suspended, set to present plus difference
                    if (date.after(userObject.getSuspendedTill()))
                        userObject.setSuspendedTill(new Date(difference + date.getTime()));
                        // Otherwise, add difference to current suspension date
                    else userObject.setSuspendedTill(new Date(difference + userObject
                            .getSuspendedTill()
                            .getTime()));
                }
            } else throw new AvailException("Book is not in use!");
        } else throw new DatabaseException("Book not found in database!");
    }

    // users list loader
    private void loadUsers()
    {
        try {
            if (!(new File(userFileName)).createNewFile()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(userFileName));
                String buffer;

                while ((buffer = fileReader.readLine()) != null)
                    users.add(new User(buffer));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // books list loader
    private void loadBooks()
    {
        try {
            if (!(new File(bookFileName)).createNewFile()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(bookFileName));
                String buffer;

                while ((buffer = fileReader.readLine()) != null)
                    books.add(new Book(buffer));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // loans list loader
    private void loadLoans()
    {
        try {
            if (!(new File(loanFileName)).createNewFile()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(loanFileName));
                String buffer;

                while ((buffer = fileReader.readLine()) != null)
                    loans.add(new Loan(buffer));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Serialize everything and update files
    public void serializeAndUpdate() throws IOException
    {
        BufferedWriter fileWriter;

        // Update users file
        fileWriter = new BufferedWriter(new FileWriter(userFileName, false));
        for (User user : users)
            fileWriter.write(user.serialize() + "\n");
        fileWriter.close();

        // Update books file
        fileWriter = new BufferedWriter(new FileWriter(bookFileName, false));
        for (Book book : books)
            fileWriter.write(book.serialize() + "\n");
        fileWriter.close();

        // Update loans file
        fileWriter = new BufferedWriter(new FileWriter(loanFileName, false));
        for (Loan loan : loans)
            fileWriter.write(loan.serialize() + "\n");
        fileWriter.close();
    }

    // Users list to be converted to stream by Formatter (Don't be naughty!)
    public List<User> getUsers() { return users; }

    // Books list to be converted to stream by Formatter (Don't be naughty!)
    public List<Book> getBooks() { return books; }

    // Loans list to be converted to stream by Formatter (Don't be naughty!)
    public List<Loan> getLoans() { return loans; }
}
