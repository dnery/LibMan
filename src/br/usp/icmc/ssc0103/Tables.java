package br.usp.icmc.ssc0103;

import java.util.Date;

/**
 * Tables.java: source of classes which represents the database(files) used in the program.
 */

// types of user
enum UserType
{
    COMMUNITY, STUDENT, TUTOR
}

//types of book
enum BookType
{
    TEXT, GENERAL
}


/**
 * Class user: represent the user (could be tutor, community or student see: enum UserType)
 */
class User
{
    ///
    /// fields
    ///
    private String   name;
    private UserType type;
    private int      curBooks;
    private Date     suspendedTill;

    ///
    /// constructors
    ///

    // To restore from CSV format...
    User(String csv)
    {
        String[] split = csv.split(",");

        this.name = split[0];
        this.type = UserType.valueOf(split[1]);
        this.curBooks = Integer.parseInt(split[2]);
        this.suspendedTill = new Date(Long.parseLong(split[3]));
    }



    // To create a new...
    User(String userName, UserType userType)
    {
        this.name = userName;
        this.type = userType;
        this.curBooks = 0;
        this.suspendedTill = new Date(0);
    }

    ///
    /// getters
    ///
    public String getName() { return name; }

    public UserType getType() { return type; }

    public int getCurBooks() { return curBooks; }

    // Fake getter to reduce attribute count
    public int getMaxBooks()
    {
        if (type == UserType.TUTOR)
            return 6;
        else if (type == UserType.STUDENT)
            return 4;
        else
            return 2;
    }

    // Fake getter to reduce attribute count
    public long getLoanDuration()
    {
        return type == UserType.TUTOR ? (long) 5.184e+9 : (long) 1.296e+9;
    }

    public Date getSuspendedTill() { return suspendedTill; }


    ///
    /// setters
    ///
    public void setCurBooks(int curBooks) { this.curBooks = curBooks; }

    public void setSuspendedTill(Date suspendedTill) { this.suspendedTill = suspendedTill; }

    /**
     * Serialize(): serialize all the field of current object to CSV format
     * @return string serialized
     */
    public String serialize()
    {
        return this.name + "," +
               this.type.toString() + "," +
               this.curBooks + "," +
               this.suspendedTill.getTime();
    }
}

/**
 * Class Book: represent the book in the program (could be text or general)
 */
class Book
{
    //
    //fields
    //
    private String   name;
    private BookType type;
    private boolean  avail;

    ///
    /// constructors
    ///

    // To restore from CSV format
    public Book(String csv)
    {
        String[] split = csv.split(",");

        this.name = split[0];
        this.type = BookType.valueOf(split[1]);
        this.avail = Boolean.parseBoolean(split[2]);
    }

    // To create a new one...
    public Book(String bookName, BookType bookType)
    {
        this.name = bookName;
        this.type = bookType;
        this.avail = true;
    }

    ///
    /// getters
    ///
    public String getName() { return name; }

    public BookType getType() { return type; }

    ///
    /// setter
    ///
    public void setAvail(boolean avail) { this.avail = avail; }

    public boolean isAvail() { return avail; }


    /**
     * Serialize(): serialize all the field of current object to CSV format
     * @return string serialized
     */
    public String serialize() { return name + "," + type.toString() + "," + avail; }
}

/**
 * Loan: represents the Loan done by user and with a book
 */
class Loan
{
    ///
    /// fields
    ///
    private String userName;
    private String bookName;
    private Date   checkOutDate;
    private Date   checkInDate;
    private Date   realCID;

    // To restore from CSV format...
    public Loan(String csv)
    {
        String[] split = csv.split(",");

        this.userName = split[0];
        this.bookName = split[1];
        this.checkOutDate = new Date(Long.parseLong(split[2]));
        this.checkInDate = new Date(Long.parseLong(split[3]));
        this.realCID = new Date(Long.parseLong(split[4]));
    }

    // To create a new...
    public Loan(String userName, String bookName, Date checkOutDate, long loanDuration)
    {
        this.userName = userName;
        this.bookName = bookName;
        this.checkOutDate = checkOutDate;
        this.checkInDate = new Date(checkOutDate.getTime() + loanDuration);
        this.realCID = checkOutDate;
    }

    ///
    /// getters
    ///
    public String getUserName() { return userName; }

    public String getBookName() { return bookName; }

    public Date getCheckOutDate() { return checkOutDate; }

    public Date getCheckInDate() { return checkInDate; }

    public Date getRealCID() { return realCID; }

    public void setRealCID(Date realCID) { this.realCID = realCID; }

    public String serialize()
    {
        return userName + "," +
               bookName + "," +
               checkOutDate.getTime() + "," +
               checkInDate.getTime() + "," +
               realCID.getTime();
    }
}