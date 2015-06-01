package br.usp.icmc.ssc0103;

import java.util.Date;

/**
 * Tables.java: source of classes which represents the tables used of database in the program.
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

    /**
     * Fields
     */
    private String   name;
    private UserType type;
    private int      curBooks;
    private Date     suspendedTill;


    /**
     * Constructor which parse the information from CSV string to object
     * @param csv format to parse
     */
    User(String csv)
    {
        String[] split = csv.split(",");

        this.name = split[0];
        this.type = UserType.valueOf(split[1]);
        this.curBooks = Integer.parseInt(split[2]);
        this.suspendedTill = new Date(Long.parseLong(split[3]));
    }


    /**
     * Constructor
     * @param userName name of user
     * @param userType type of user
     */
    User(String userName, UserType userType)
    {
        this.name = userName;
        this.type = userType;
        this.curBooks = 0;
        this.suspendedTill = new Date(0);
    }

    /**
     * Getter
     * @return Name
     */
    public String getName() { return name; }

    /**
     * Getter
     * @return UserType
     */
    public UserType getType() { return type; }

    /**
     * Getter
     * @return curBooks
     */
    public int getCurBooks() { return curBooks; }

    /**
     * Fake getter to reduce attribute count
     * @return int
     */
    public int getMaxBooks()
    {
        if (type == UserType.TUTOR)
            return 6;
        else if (type == UserType.STUDENT)
            return 4;
        else
            return 2;
    }

    /**
     * Fake getter to reduce attribute count
     * @return long
     */
    public long getLoanDuration()
    {
        return type == UserType.TUTOR ? (long) 5.184e+9 : (long) 1.296e+9;
    }

    /**
     * Getter
     * @return suspendedTill
     */
    public Date getSuspendedTill() { return suspendedTill; }


    /**
     * Setter
     * @param curBooks value to set curBooks
     */
    public void setCurBooks(int curBooks) { this.curBooks = curBooks; }

    /**
     * Seter
     * @param suspendedTill value to set suspendedTill
     */
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

    /**
     * Fields
     */
    private String   name;
    private BookType type;
    private boolean  avail;


    /**
     * Constructor which parse the information from CSV string to object
     * @param csv format to parse
     */
    public Book(String csv)
    {
        String[] split = csv.split(",");

        this.name = split[0];
        this.type = BookType.valueOf(split[1]);
        this.avail = Boolean.parseBoolean(split[2]);
    }

    /**
     * Constructor
     * @param bookName name of book
     * @param bookType type of book
     */
    public Book(String bookName, BookType bookType)
    {
        this.name = bookName;
        this.type = bookType;
        this.avail = true;
    }

    /**
     * Getter of field name
     * @return name of object
     */
    public String getName() { return name; }

    /**
     * Getter of field type
     * @return type of object
     */
    public BookType getType() { return type; }


    /**
     * Setter: set the avail field
     * @param avail new value to avail
     */
    public void setAvail(boolean avail) { this.avail = avail; }

    /**
     * Verify if the book is available
     * @return boolean of availability
     */
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
    /**
     * Fields
     */
    private String userName;
    private String bookName;
    private Date   checkOutDate;
    private Date   checkInDate;
    private Date   realCID;

    /**
     * Constructor which parse the information from CSV string to object
     * @param csv format to parse
     */
    public Loan(String csv)
    {
        String[] split = csv.split(",");

        this.userName = split[0];
        this.bookName = split[1];
        this.checkOutDate = new Date(Long.parseLong(split[2]));
        this.checkInDate = new Date(Long.parseLong(split[3]));
        this.realCID = new Date(Long.parseLong(split[4]));
    }

    /**
     * Constructor
     * @param userName used in the loan
     * @param bookName used in the book
     * @param checkOutDate date of loan
     * @param loanDuration time of loan
     */
    public Loan(String userName, String bookName, Date checkOutDate, long loanDuration)
    {
        this.userName = userName;
        this.bookName = bookName;
        this.checkOutDate = checkOutDate;
        this.checkInDate = new Date(checkOutDate.getTime() + loanDuration);
        this.realCID = checkOutDate;
    }

    /**
     * Getter
     * @return userName of Loan
     */
    public String getUserName() { return userName; }

    /**
     * Getter
     * @return bookName of Loan
     */
    public String getBookName() { return bookName; }

    /**
     * Getter
     * @return checkoutDate
     */
    public Date getCheckOutDate() { return checkOutDate; }

    /**
     * Getter
     * @return checkInnDate
     */
    public Date getCheckInDate() { return checkInDate; }

    /**
     * Getter
     * @return  date of RealCID
     */
    public Date getRealCID() { return realCID; }

    /**
     * Setter  of field realCID
     * @param realCID value to set
     */
    public void setRealCID(Date realCID) { this.realCID = realCID; }


    /**
     * Serialize(): serialize all the field of current object to CSV format
     * @return string serialized
     */
    public String serialize()
    {
        return userName + "," +
               bookName + "," +
               checkOutDate.getTime() + "," +
               checkInDate.getTime() + "," +
               realCID.getTime();
    }
}