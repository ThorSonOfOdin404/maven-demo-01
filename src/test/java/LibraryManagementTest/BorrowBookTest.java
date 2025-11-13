package LibraryManagementTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mytest.LibraryManagement.Book;
import org.mytest.LibraryManagement.Library;

import java.util.List;

public class BorrowBookTest extends BaseClass{
    private Library library;
    @Before
    public void init(){
        super.init();
        library = getLibrary();
    }
    @Test
    public void TestBorrowBook(){
        Book book = new Book("The Wings Of Fire", "Dr. APJ Abdul Kalam", "0012344321678");
        int bookQty = 3;
        library.addBook(book, bookQty);

        //Find Book First
        List<Book> searchedBooks = library.searchBookByTitle(book.getTitle());
        Assert.assertFalse(searchedBooks.isEmpty());

        for(Book book1 : searchedBooks){
            if(book1.getISBN().equals(book.getISBN())){
                for (int i = 0; i < bookQty ; i++) {
                    boolean borrowStatus = library.borrowBook(book1);
                    Assert.assertTrue(borrowStatus);
                }

                //Testing borrowing more than qty
                boolean borrowStatus = library.borrowBook(book1);
                Assert.assertFalse(borrowStatus);
            }
        }
    }
}
