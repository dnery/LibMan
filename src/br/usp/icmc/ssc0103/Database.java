package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

enum UserType
{
    TUTOR, STUDENT, COMMUNITY
}

enum BookType
{
    TEXT, GENERAL
}

class User
{
    private String  name;
    private int     maxBooks;
    private int     curBooks;
    private boolean academic;
    private long    loanDuration;
    private Date    suspendedTill;

    User(String name,
         int maxBooks,
         int curBooks,
         boolean academic,
         long loanDuration,
         Date suspendedTill)
    {
        this.name = name;
        this.maxBooks = maxBooks;
        this.curBooks = curBooks;
        this.academic = academic;
        this.loanDuration = loanDuration;
        this.suspendedTill = suspendedTill;
    }

    public String getName() { return name; }

    public int getMaxBooks() { return maxBooks; }

    public int getCurBooks() { return curBooks; }

    public boolean isAcademic() { return academic; }

    public long getLoanDuration() { return loanDuration; }

    public Date getSuspendedTill() { return suspendedTill; }

    public void setCurBooks(int curBooks) { this.curBooks = curBooks; }

    public void setSuspendedTill(Date suspendedTill) { this.suspendedTill = suspendedTill; }

    public String serialize()
    {
        return this.name + "," +
               this.maxBooks + "," +
               this.curBooks + "," +
               this.academic + "," +
               this.loanDuration + "," +
               this.suspendedTill.getTime();
    }
}

class Tutor extends User
{
    //When creating...
    public Tutor(String name) { super(name, 6, 0, true, (long) 5.184e+9, new Date()); }

    //When recovering...
    public Tutor(String name, int curBooks, long suspendedTill)
    {
        super(name, 6, curBooks, true, (long) 5.184e+9, new Date(suspendedTill));
    }
}

class Student extends User
{
    //When creating...
    public Student(String name) { super(name, 4, 0, true, (long) 1.296e+9, new Date()); }

    //When recovering...
    public Student(String name, int curBooks, long suspendedTill)
    {
        super(name, 4, curBooks, true, (long) 1.296e+9, new Date(suspendedTill));
    }
}

class Comunity extends User
{
    //When creating...
    public Comunity(String name) { super(name, 4, 0, false, (long) 1.296e+9, new Date()); }

    //When recovering...
    public Comunity(String name, int curBooks, long suspendedTill)
    {
        super(name, 4, curBooks, false, (long) 1.296e+9, new Date(suspendedTill));
    }
}

class Book
{
    private String  name;
    private boolean type;
    private boolean avail;

    public Book(String name, boolean type, boolean avail)
    {
        this.name = name;
        this.type = type;
        this.avail = avail;
    }

    public String getName() { return name; }

    public boolean isType() { return type; }

    public boolean isAvail() { return avail; }

    public void setAvail(boolean avail) { this.avail = avail; }

    public String serialize() { return name + "," + type + "," + avail; }
}

class Loan
{
    private int  userID;
    private int  bookID;
    private Date checkOutDate;
    private Date checkInDate;
    private Date realCID;

    public Loan(int userID, int bookID, Date checkOutDate, Date checkInDate, Date realCID)
    {
        this.userID = userID;
        this.bookID = bookID;
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
        this.realCID = realCID;
    }

    public int getUserID() { return userID; }

    public int getBookID() { return bookID; }

    public Date getCheckOutDate() { return checkOutDate; }

    public Date getCheckInDate() { return checkInDate; }

    public Date getRealCID() { return realCID; }

    public void setRealCID(Date realCID) { this.realCID = realCID; }

    public String serialize()
    {
        return userID + "," +
               bookID + "," +
               checkOutDate.getTime() + "," +
               checkInDate.getTime() + "," +
               realCID.getTime();
    }
}

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

    // Static initializer is always run before class is used by any thread
    private static final Database database = new Database("users.csv", "books.csv", "loans.csv");

    // Private constructor in according to Singleton standards
    private Database(String userFileName, String bookFileName, String loanFileName)
    {
        this.userFileName = userFileName;
        this.bookFileName = bookFileName;
        this.loanFileName = loanFileName;
        // Se não existirem os arquivos, crie-os
        // Carrega/cria listas condicionalmente
    }

    // The only existing instance is obtained always
    public static Database getInstance() { return database; }

    public void userAdd(String username, UserType usertype)
    {
        // TODO!!!
        // Adiciona novo elemento usuario na lista users
        // Atualiza o arquivo users.csv com o metodo append()

        //DEBUG
        if (usertype == UserType.TUTOR)
            System.out.println("Tutor " + username + " has been added!");
        else if (usertype == UserType.STUDENT)
            System.out.println("Student " + username + " has been added!");
        else if (usertype == UserType.COMMUNITY)
            System.out.println("Community member " + username + " has been added!");
    }

    public void catalogAdd(String bookname, BookType booktype)
    {
        // TODO!!!
        // Semelhante ao userAdd(), porem com lista books e arquivo books.csv

        //DEBUG
        if (booktype == BookType.TEXT)
            System.out.println("Textbook " + bookname + " has been catalogued!");
        else if (booktype == BookType.GENERAL)
            System.out.println("Literary book " + bookname + " has been catalogued!");
    }

    public void checkOut(String username, String bookname, Date date) throws
                                                                      DatabaseException,
                                                                      AccessException,
                                                                      AvailException
    {
        // TODO!!!
        // Checa pela existencia do usuario na database
        // Checa pela existencia do livro na database
        // Checa se o usuario tem acesso ao livro
        // Checa se o livro ja nao esta alugado
        // Adiciona novo elemento na lista loans
        // Atualiza o arquivo loans.csv com append()

        //DEBUG
        System.out.println(date.toString() + ": loaning \"" + bookname + "\" to " + username + ".");
    }

    public void checkIn(String bookname, Date date) throws DatabaseException,
                                                           AvailException
    {
        // TODO!!!
        // Checa pela existencia do livro na database
        // Checa se o livro ja nao esta disponivel
        // MODIFICA o elemento certo na lista loans
        // MODIFICA a linha certa no arquivo loans.csv com snipe()
        // Ou simplesmente o reescreve inteiro com o metodo dump()

        //DEBUG
        System.out.println(date.toString() + ": \"" + bookname + "\" has been returned!");
    }

    private void append(String filename, String data)
    {
        // TODO!!!
        // Adiciona a string "data" recebida de um
        // serialize() no arquivo <filename>.csv
    }

    // Esse metodo e opcional (vide snipe())
    private void dump(String filename, String[] data)
    {
        // TODO!!!
        // Se implementado, esse metodo deve
        // reescrever o .csv inteiro usando
        // o array de strings "data" recebido
    }

    // Esse metodo e opcional (vide dump())
    private void snipe(String filename, String replaceable, String replacer)
    {
        // TODO!!!
        // Se implementado, esse metodo deve
        // reescrever uma string "replaceable"
        // com uma string "replacer" recebida
        // http://stackoverflow.com/questions/4505818/in-java-how-do-i-edit-1-line-of-a-text-file
    }

    // Recover users stream rather than list itself
    public Stream<User> getUserStream() { return users.stream(); }

    // Recover books stream rather than list itself
    public Stream<Book> getBookStream() { return books.stream(); }

    // Recover loans stream rather than list itself
    public Stream<Loan> getLoanStream() { return loans.stream(); }
}
