package br.usp.icmc.ssc0103;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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
    private DatabaseFileHandler helper;
    private String     userFileName;
    private String     bookFileName;
    private String     loanFileName;

    // Static initializer is always run before class is used by any thread
    private static final Database database = new Database("users.csv", "books.csv", "loans.csv");

    // Private constructor according to Singleton standards
    private Database(String userFileName, String bookFileName, String loanFileName) {

        helper = new DatabaseFileHandler();
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
        loans = new ArrayList<Loan>();


        this.userFileName = userFileName;
        this.bookFileName = bookFileName;
        this.loanFileName = loanFileName;

        helper.loadUsers();
        helper.loadBooks();
        helper.loadLoans();

    }
    // The only existing instance is obtained always
    public static Database getInstance() { return database; }

    // "user add (...)" command backend
    public void userAdd(String username, UserType usertype)
    {

        User user = new User(username, usertype);
        users.add(user);
        helper.append(userFileName, user.serialize());

        //DEBUG
        if (usertype == UserType.TUTOR)
            System.out.println("Tutor " + username + " has been added!");
        else if (usertype == UserType.STUDENT)
            System.out.println("Student " + username + " has been added!");
        else if (usertype == UserType.COMMUNITY)
            System.out.println("Community member " + username + " has been added!");
    }

    // "catalog add (...)" command backend
    public void catalogAdd(String bookname, BookType booktype)
    {
        // TODO!!
        Book book = new Book (bookname, booktype);
        books.add(book);
        helper.append(bookFileName, book.serialize());

        //DEBUG
        if (booktype == BookType.TEXT)
            System.out.println("Textbook " + bookname + " has been catalogued!");
        else if (booktype == BookType.GENERAL)
            System.out.println("Literary book " + bookname + " has been catalogued!");
    }

    // "user (...) checkout (...)" command backend
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
        for (User user : users) {
            if (user.getName().equals(username)) {
                for (Book book : books) {
                    if (book.getName().equals(bookname)) {
                        if (book.isAvail()) {
                            if (book.isAllowed(user.isAcademic())) {
                                Loan loan = new Loan(username, bookname , new Date(), user.getLoanDuration());
                                loans.add(loan);
                                helper.append(loanFileName, loan.serialize());
                                System.out.println(date.toString() + ": loaning \"" + bookname + "\" to " + username + ".");
                            }else throw new AccessException();
                        }else throw new AvailException();
                    }
                }throw new DatabaseException();
            }
        }throw new DatabaseException();

        //DEBUG
        //System.out.println(date.toString() + ": loaning \"" + bookname + "\" to " + username + ".");
    }

    // "checkin (...)" command backend
    public void checkIn(String bookname, Date date) throws DatabaseException,
                                                           AvailException
    {
        // TODO!!!
        for (Book book : books) {
            if (book.getName().equals(bookname)) {
                if (book.isAvail()) {
                    throw new AvailException();
                } else {

                }
            }
        }
        throw new DatabaseException();
        // Checa se o livro ja nao esta disponivel
        // MODIFICA o elemento certo na lista loans
        // MODIFICA a linha certa no arquivo loans.csv com snipe()
        // Ou simplesmente o reescreve inteiro com o metodo dump()

        //DEBUG
        //System.out.println(date.toString() + ": \"" + bookname + "\" has been returned!");
    }

    // File handlers:
    class DatabaseFileHandler {
        public void loadUsers() {
            try {
                BufferedReader in = new BufferedReader(new FileReader(userFileName));
                String csv;
                in.readLine(); // Pula a primeira linha (cabeÁalho)
                while((csv = in.readLine()) != null) {
                    users.add(new User(csv));
                }
            }
            catch(FileNotFoundException e) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(userFileName), "utf-8"))) {
                    writer.write("Registred users:");
                    writer.close();
                }
                catch (IOException er) {
                    System.out.println("Error creating users file!");
                }
            }
            catch(IOException e) {
                System.out.println("Error reading Users file!");
            }
        }

        public void loadBooks(){
            try {
                BufferedReader in = new BufferedReader(new FileReader(bookFileName));
                String csv;
                in.readLine(); // Pula a primeira linha (cabeÁalho)
                while((csv = in.readLine()) != null) {
                    books.add(new Book(csv));
                }
                in.close();
            }
            catch(FileNotFoundException e) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(bookFileName), "utf-8"))) {
                    writer.write("Registred books:");
                    writer.close();
                }
                catch (IOException er) {
                    System.out.println("Error creating books file!");
                }
            }
            catch(IOException e) {
                System.out.println("Error reading books file!");
            }
        }

        public void loadLoans() {
            try {
                BufferedReader in = new BufferedReader(new FileReader(loanFileName));
                String csv;
                in.readLine(); // Pula a primeira linha (cabeÁalho)
                while((csv = in.readLine()) != null) {
                    loans.add(new Loan(csv));
                }
                in.close();
            }
            catch(FileNotFoundException e) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(loanFileName), "utf-8"))) {
                    writer.write("Registred Loans:");
                    writer.close();
                }
                catch (IOException er) {
                    System.out.println("Error creating loans file!");
                }
            }
            catch(IOException e) {
                System.out.println("Error reading loans file!");
            }
        }

        private void append(String filename, String data)
        {
            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
                out.println(data);
            }catch (IOException e) {
                System.err.println(e);
            }
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
            // com uma string "replacer" recebide


        }

        // Recover users stream rather than list itself
        public Stream<User> getUserStream() { return users.stream(); }

        // Recover books stream rather than list itself
        public Stream<Book> getBookStream() { return books.stream(); }

        // Recover loans stream rather than list itself
        public Stream<Loan> getLoanStream() { return loans.stream(); }
    }


}
