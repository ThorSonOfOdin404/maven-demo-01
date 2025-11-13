package LibraryManagementTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mytest.LibraryManagement.Book;
import org.mytest.LibraryManagement.Library;

import java.util.List;

public class SearchBookTest extends BaseClass{
    private Library library;
    @Before
    public void init(){
        super.init();
        library = getLibrary();
    }

    @Test
    public void TestSearchByTitleForAddedBook(){
        Book book = getBook(1);
        List<Book> books = library.searchBookByTitle(book.getTitle());

        Assert.assertFalse(books.isEmpty());
        Assert.assertEquals(book.getTitle(), books.get(0).getTitle());
    }

    @Test
    public void TestSearchByAuthorForAddedBook(){
        Book book = getBook(1);
        List<Book> books = library.searchBookByAuthor(book.getAuthor());

        Assert.assertFalse(books.isEmpty());
        Assert.assertEquals(book.getTitle(), books.get(0).getTitle());
    }

    private Book getBook(int qty) {
        Book book = new Book(
                "The Wings Of Fire",
                "Dr. APJ Abdul Kalam",
                "0012344321678"
        );
        library.addBook(book, qty);
        return book;
    }
}
