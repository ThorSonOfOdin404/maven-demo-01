package GenericTestingFramework;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestScenario4 extends BaseClass{
    private WebDriver driver;
    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }

    @Test
    @SneakyThrows
    public void TestXpathAxes(){
        driver.get("https://news.google.com/home");
        String xpath1 = "//h2[text()='Your topics']/parent::div/parent::div/following-sibling::div/descendant::article";
        driver.manage().timeouts().implicitlyWait(1800, TimeUnit.MILLISECONDS);
        List<WebElement> yourTopicsArticles = driver.findElements(By.xpath(xpath1));
        System.out.println(yourTopicsArticles.size());
        for (int i = 0; i < yourTopicsArticles.size(); i++) {
            WebElement article = yourTopicsArticles.get(i);
            System.out.println("\033[3;30;47mArticle "+i +" \033[0m\n" + article.getText());
        }
        Assert.assertNotEquals(0, yourTopicsArticles.size());

    }

    @Test
    public void TestXpathAxes2() throws InterruptedException{
        driver.get("https://www.facebook.com");
        final WebElement signUpBtn = driver.findElement(By.partialLinkText("Create new account"));
        signUpBtn.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("entry_point=login"));
        // Finding the signup button and then getting the placeholder of First Name input box
        // with respect to it

        String xpathString=
                "//button[contains(text(),'Sign Up') and @name='websubmit']/parent::div/" +
                        "preceding-sibling::div[position()=last()]/" +
                        "descendant::div[contains(text(),'First')]/following-sibling::input";

        driver.findElement(By.xpath(xpathString)).sendKeys("Pratham");

        //Checking whether the first name text was updated or not
        WebElement firstName = driver.findElement(By.cssSelector("input[name=firstname]"));
        Assert.assertEquals("Pratham", firstName.getAttribute("value"));
    }
}
