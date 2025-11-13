package LibraryManagementTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mytest.LibraryManagement.Book;
import org.mytest.LibraryManagement.Library;

import java.util.List;

public class AddBookTest extends BaseClass{
    private Library library;
    @Before
    public void init(){
        super.init();
        library = getLibrary();
    }
    @Test
    public void TestAddOneBook(){
        Book book = addAndGetBook();
        library.addBook(book);

        List<Book> books = library.searchBookByTitle(book.getTitle());
        Assert.assertEquals("Books was not added", 1, books.size());
    }

    @Test
    public void TestAddMultipleQtyOfBook(){
        Book book = addAndGetBook();
        library.addBook(book,4);

        List<Book> books = library.searchBookByTitle(book.getTitle());
        Assert.assertEquals("Book was not added", 1, books.size());
        int bookQty = library.getBookQty(book.getISBN());

        Assert.assertEquals(4, bookQty);
    }

    @Test
    public void TestAddDifferentQtyOfBooks(){
        Book book1 = addAndGetBook();
        library.addBook(book1,2);
        library.addBook(book1,6);

        //The library should return a single book result
        List<Book> searchResult = library.searchBookByTitle(book1.getTitle());
        Assert.assertEquals(1, searchResult.size());

        int bookQty = library.getBookQty(book1.getISBN());
        Assert.assertEquals(8, bookQty);
    }

    @Test
    public void TestAddNegativeQtyOfBook(){
        Book book = addAndGetBook();
        boolean result = library.addBook(book, -3);
        Assert.assertFalse(result);
    }

    @Test
    public void TestAddNullBookObject(){
            boolean result = library.addBook(null, 4);
            Assert.assertFalse(result);
    }

    private Book addAndGetBook() {
        Book book = new Book("The Wings Of Fire", "Dr. APJ Abdul Kalam", "0012344321678");
        return book;
    }
}
