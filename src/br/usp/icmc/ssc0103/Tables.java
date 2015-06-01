package br.usp.icmc.ssc0103;

import java.util.Date;

enum UserType
{
    COMMUNITY, STUDENT, TUTOR
}

enum BookType
{
    TEXT, GENERAL
}

class User
{
    private String   name;
    private UserType type;
    private int      curBooks;
    private Date     suspendedTill;

    // To restore...
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
        this.suspendedTill = new Date();
    }

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

    public void setCurBooks(int curBooks) { this.curBooks = curBooks; }

    public void setSuspendedTill(Date suspendedTill) { this.suspendedTill = suspendedTill; }

    public String serialize()
    {
        return this.name + "," +
               this.type.toString() + "," +
               this.curBooks + "," +
               this.suspendedTill.getTime();
    }
}

class Book
{
    private String   name;
    private BookType type;
    private boolean  avail;

    // To restore...
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

    public String getName() { return name; }

    public BookType getType() { return type; }

    public boolean isAvail() { return avail; }

    public void setAvail(boolean avail) { this.avail = avail; }

    public String serialize() { return name + "," + type.toString() + "," + avail; }
}

class Loan
{
    private String userName;
    private String bookName;
    private Date   checkOutDate;
    private Date   checkInDate;
    private Date   realCID;

    // To restore...
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