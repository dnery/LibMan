package br.usp.icmc.ssc0103;

import java.util.Date;
import java.util.List;

abstract class User {
	
	String name;
	int maxBooks;
	int currentBooks;
	boolean academic;
	long loanDuration;
	Date SuspendedTill;
	
	public String GetName() {
		return name;
	}
	public int getMaxBooks() {
		return maxBooks;
	}
	public int getCurrentBooks() {
	}
	public long getLoanDuration() {
	}
	public void setSuspendTill(Date date){	
	}
	
}

class Student extends User {
	
	public Student(String name) {
		super.name = name
		super.maxBooks = 4;
		super.currentBooks = 0
		super.academic = true;
		super.loanDuration = 1.296e+9;
		super.SuspendedTill = new Date();
	}
	public Student(String name, int currentBooks, long date) {
		super.name = name
		super.maxBooks = 4;
		super.currentBooks = currentBooks;
		super.academic = true;
		super.loanDuration = 1.296e+9;
		super.SuspendedTill = new(date);
	}
}

class Tutor extends User {
	public Tutor(String name) {
		super.name = name
		super.maxBooks = 6;
		super.currentBooks = 0
		super.academic = true;
		super.loanDuration = 5.184e+9;
		super.SuspendedTill = new Date();
	}
	public Tutor(String name, int currentBooks, long date) {
		super.name = name
		super.maxBooks = 6;
		super.currentBooks = currentBooks;
		super.academic = true;
		super.loanDuration = 5.184e+9;
		super.SuspendedTill = new(date);
	}
}

class Comunity extends User {
	
	public Comunity(String name) {
		super.name = name
		super.maxBooks = 2;
		super.currentBooks = 0
		super.academic = false;
		super.loanDuration = 1.296e+9;
		super.SuspendedTill = new Date();
	}
	public Comunity(String name, int currentBooks, long date) {
		super.name = name
		super.maxBooks = 2;
		super.currentBooks = currentBooks;
		super.academic = false;
		super.loanDuration = 1.296e+9;
		super.SuspendedTill = new(date);
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
