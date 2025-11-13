import org.junit.Assert;
import org.junit.Test;
import org.mytest.Greeter;

public class TestGreeter {
    @Test
    public void test(){
        Greeter greeter = new Greeter();
        Assert.assertEquals("Hello Pratham! I hope you are good.", greeter.greet("Pratham"));
    }
}
