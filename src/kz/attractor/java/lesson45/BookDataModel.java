package kz.attractor.java.lesson45;

import kz.attractor.java.Books.Book;

public class BookDataModel {
    private Book book;

    public BookDataModel() {
        this.book = new Book(1, "Букварь", "Жуков", "Учебное пособие", 1995, "free");
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
