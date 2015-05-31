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
    //maxBooks: int
    private int      curBooks;
    //loanDuration: long
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
    User(String userName, UserType usertype)
    {
        this.name = userName;
        this.type = usertype;
        this.curBooks = 0;
        this.suspendedTill = new Date();
    }

    public String getName() { return name; }

    public UserType getType() { return type; }

    public int getMaxBooks()
    {
        if (type == UserType.TUTOR)
            return 6;
        else if (type == UserType.STUDENT)
            return 4;
        else
            return 2;
    }

    public int getCurBooks() { return curBooks; }

    public long getLoanDuration()
    {
        return type == UserType.TUTOR ? (long) 5.184e+9 : (long) 1.296e+9;
    }

    public Date getSuspendedTill() { return suspendedTill; }

    public void setCurBooks(int curBooks) { this.curBooks = curBooks; }

    public void setSuspendedTill(Date suspendedTill) { this.suspendedTill = suspendedTill; }

    public boolean isAcademic() { return type != UserType.COMMUNITY; }

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
        this.avail = Boolean.getBoolean(split[2]);
    }

    // To create a new one...
    public Book(String bookname, BookType booktype)
    {
        this.name = bookname;
        this.type = booktype;
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
    private int  userID;
    private int  bookID;
    private Date checkOutDate;
    private Date checkInDate;
    private Date realCID;

    // To restore...
    public Loan(String csv)
    {
        String[] split = csv.split(",");

        this.userID = Integer.parseInt(split[0]);
        this.bookID = Integer.parseInt(split[1]);
        this.checkOutDate = new Date(Long.parseLong(split[2]));
        this.checkInDate = new Date(Long.parseLong(split[3]));
        this.realCID = new Date(Long.parseLong(split[4]));
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