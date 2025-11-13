package LibraryManagementTest;

import lombok.Getter;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mytest.LibraryManagement.Library;

public class BaseClass {
    @Getter
    private Library library;

    @BeforeClass
    public static void configure(){
        PropertyConfigurator.configure("log4j.properties");
    }

    @Before
    public void init(){
        library = new Library();
    }
}
