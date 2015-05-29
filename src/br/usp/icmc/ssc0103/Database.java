package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.List;

class User
{

    String name;
    int maxBooks;
    int currentBooks;
    boolean academic;
    long loanDuration;
    Date suspendedTill;

    public User(String name,
                int maxBooks,
                int currentBooks,
                boolean academic,
                long loanDuration,
                Date suspendedTill)
    {
        this.name = name;
        this.maxBooks = maxBooks;
        this.currentBooks = currentBooks;
        this.academic = academic;
        this.loanDuration = loanDuration;
        this.suspendedTill = suspendedTill;
    }

    public String getName()
    {
        return name;
    }

    public int getMaxBooks()
    {
        return maxBooks;
    }

    public int getCurrentBooks()
    {
        return currentBooks;
    }

    public boolean isAcademic()
    {
        return academic;
    }

    public long getLoanDuration()
    {
        return loanDuration;
    }

    public Date getSuspendedTill()
    {
        return suspendedTill;
    }

    public void setCurrentBooks(int currentBooks)
    {
        this.currentBooks = currentBooks;
    }

    public void setSuspendedTill(Date suspendedTill)
    {
        this.suspendedTill = suspendedTill;
    }
}

class Student extends User
{

    public Student(String name)
    {
        super(name, 4, 0, true, (long) 1.296e+9, new Date());
    }

    public Student(String name, int currentBooks, long date)
    {
        super(name, 4, currentBooks, true, (long) 1.296e+9, new Date(date));
    }
}

class Tutor extends User
{
    public Tutor(String name)
    {
        super(name, 6, 0, true, (long) 5.184e+9, new Date());
    }

    public Tutor(String name, int currentBooks, long date)
    {
        super(name, 6, currentBooks, true, (long) 5.184e+9, new Date(date));
    }
}

class Comunity extends User
{

    public Comunity(String name)
    {
        super(name, 4, 0, false, (long) 1.296e+9, new Date());
    }

    public Comunity(String name, int currentBooks, long date)
    {
        super(name, 4, currentBooks, false, (long) 1.296e+9, new Date(date));
    }


}

class Book
{

}

class Loan
{
    //tostring
    //tocsv
}

class DatabaseException extends Exception
{
    public DatabaseException() { super(); }

    public DatabaseException(String message) { super(message); }
}

class AccessException extends Exception
{
    public AccessException() { super(); }

    public AccessException(String message) { super(message); }
}

class AvailException extends Exception
{
    public AvailException() { super(); }

    public AvailException(String message) { super(message); }
}

public class Database
{
    List<User> users;
    List<Book> books;
    List<Loan> loans;

    String userFileName;
    String bookFileName;
    String loanFileName;

    public Database(String userfile, String bookfile, String loanfile)
    {
        // se nao exister cria os arquivos;
        // carregar as listas
        // seta os this.files
        //
    }

    public void UserAdd(String username, int usertype)
    {
        //add na lista
        //atualiaza o csv File, serializa(this.userfile, user.toString)
    }

    public void CatalogAdd(String bookname, int booktype)
    {
        //igual o de cima
    }

    public void CheckIn(String bookname, Date date) throws DatabaseException
    {

        //se nao tiver emprestrado
        //
    }

    public void CheckOut(String username, String bookname, Date date) throws
                                                                      DatabaseException,
                                                                      AccessException,
                                                                      AvailException
    {
    }

    public void List(String... names)
    {
        //local method list
        for (String name : names) {
            if (name.equals("users")) {
                //list user
            }
        }
    }

    private void Serialize(String filename, String data)
    {
        //atualiza os arquivos
    }

}
