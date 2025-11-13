package org.mytest.LibraryManagement;

import lombok.*;

@Getter
public class Book {
    private static int BASE_ID = 10000;

    private final int ID = BASE_ID++;
    private final String title;
    private final String author;
    private final String ISBN;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        if(ISBN.length() == 13){
            this.ISBN = ISBN;
        } else{
            throw new IllegalArgumentException("ISBN might not be correct");
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Book){
            Book book = (Book) o;
            return this.ISBN.equals(book.getISBN()) &&
                    this.title.equals(book.getTitle()) &&
                    this.author.equals(book.getAuthor()) ;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ISBN.hashCode();
    }
}
