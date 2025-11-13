package org.mytest.LibraryManagement;

import com.sun.istack.internal.NotNull;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

@NoArgsConstructor
public class Library {
    static {
        PropertyConfigurator.configure("log4j.properties");
    }

    private static final Logger LOGGER = Logger.getLogger(Library.class);
    private final Map<String, Integer> isbnToQtyMap = new HashMap<>();
    private final Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        this.addBook(book,1);
    }
    public boolean addBook(@NotNull Book book, int Qty){
        if(book != null && Qty >= 0) {
            final String isbn = book.getISBN();
            books.add(book);
            if (isbnToQtyMap.containsKey(isbn)) {
                isbnToQtyMap.compute(isbn, (key, storedQTY) -> storedQTY + Qty);
            } else {
                isbnToQtyMap.put(isbn, Qty);
            }
            LOGGER.info(Qty + " Book(s) with ISBN : " + isbn + " was/were added to library");
            return true;
        } else {
            return false;
        }
    }

    public int getBookQty(String isbn){
        return isbnToQtyMap.get(isbn);
    }

    public List<Book> searchBookByAuthor(String author){
        List<Book> matches = new ArrayList<>();
        for(Book book : books){
            if(book.getAuthor().equals(author)){
                matches.add(book);
            }
        }
        return matches;
    }

    public List<Book> searchBookByTitle(String title){
        List<Book> matches = new ArrayList<>();
        for(Book book : books){
            if(book.getTitle().equals(title)){
                matches.add(book);
            }
        }
        return matches;
    }

    public boolean borrowBook(Book book){
        if(isbnToQtyMap.get(book.getISBN()) > 0){
            isbnToQtyMap.compute(book.getISBN(), (key, qty)-> qty-1);
            LOGGER.info("Book ID : " + book.getID() + " was borrowed.");
            return true;
        }
        return false;
    }
}
