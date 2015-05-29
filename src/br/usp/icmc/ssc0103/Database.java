package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.List;

class User
{

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
