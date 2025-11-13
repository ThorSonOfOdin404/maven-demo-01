package GenericTestingFramework;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestScenario10 extends BaseClass {
    private WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(TestScenario10.class);

    @Before
    public void init() {
        super.init();
        driver = getDriver();
    }

    @Test
    public void TestFluentWait() {
        driver.get("http://localhost:8800/delayDIV");
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'CLICK HERE')]"));
        button.click();

        // Explicit Wait for delayed element

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(9L))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class);

        // The element is displayed after 2 seconds but since the polling time is 5 seconds
        // the FluentWait won't find the element till the next polling

        WebElement delayedElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//div[@id='delayedElement']"));
            }
        });

    Assert.assertEquals("This is a delayed element", delayedElement.getText());
    }

    @Test
    public void TestImplicitWait() {
        driver.get("http://localhost:8800/delayDIV");
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'CLICK HERE')]"));
        button.click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        // Here we are implicitly waiting for the delayedElement
        // this is the webdriver level wait
        WebElement delayedElement = driver.findElement(By.id("delayedElement"));
        Assert.assertEquals("This is a delayed element", delayedElement.getText());
    }

    @Test
    public void TestExplicitWait() {
        driver.get("http://localhost:8800/delayDIV");
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'CLICK HERE')]"));
        button.click();

        //Here we are explicitly waiting for the delayedElement
        //this is a specific wait waiting for delayedElement using WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement delayedElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delayedElement")));

        Assert.assertEquals("This is a delayed element", delayedElement.getText());
    }

    @Test
    public void TestThreadSleep() throws InterruptedException {
        driver.get("http://localhost:8800/delayDIV");
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'CLICK HERE')]"));
        button.click();

        Thread.sleep(4000);
        WebElement delayedElement = driver.findElement(By.id("delayedElement"));

        Assert.assertEquals("This is a delayed element", delayedElement.getText());
    }

    @Test
    public void TestNavigateToSite(){
        driver.get("http://localhost:8800/form");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("navigation.navigate(arguments[0])", "https://www.instagram.com");

        Wait<WebDriver> wait = new WebDriverWait(driver, 4);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Log in']")));
        Assert.assertTrue(element.isDisplayed());
    }
}
