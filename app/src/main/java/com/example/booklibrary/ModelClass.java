package com.example.booklibrary;

public class ModelClass  {
    String bookID ,bookTitle ,bookAuthodName,bookPages;

    public ModelClass(String bookID , String bookTitle , String bookAuthodName , String bookPages) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.bookAuthodName = bookAuthodName;
        this.bookPages = bookPages;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthodName() {
        return bookAuthodName;
    }

    public void setBookAuthodName(String bookAuthodName) {
        this.bookAuthodName = bookAuthodName;
    }

    public String getBookPages() {
        return bookPages;
    }

    public void setBookPages(String bookPages) {
        this.bookPages = bookPages;
    }
}
