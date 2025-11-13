package GenericTestingFramework;

import JSExecuterUtil.JsExecutorUtil;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class TestScenario8 extends BaseClass{
    private WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(TestScenario8.class);

    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }

    @Test
    public void TestAuth(){
//        driver.get("https://the-internet.herokuapp.com/basic_auth");
        // Bypassing the auth modal using creds in url
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        Assert.assertEquals("Congratulations! You must have the proper credentials.",
                driver.findElement(By.tagName("p")).getText()
        );
    }

    @Test
    @SneakyThrows
    public void TestFlash(){
        driver.get("https://www.facebook.com");
        WebElement button = driver.findElement(By.xpath("//button[contains(@Id,'u_0_5_')]"));
        JsExecutorUtil.flashElement(button,driver);
        Thread.sleep(300);
        String color = driver.findElement(By.xpath("//button[contains(@Id,'u_0_5_')]")).getCssValue("background-color");
        Assert.assertEquals("rgba(255, 255, 255, 1)", color);
    }

    @Test
    public void TestBorder(){
        driver.get("https://www.facebook.com");
        WebElement button = driver.findElement(By.name("login"));
        JsExecutorUtil.borderElement(button, driver);
        String borderColor = driver.findElement(By.name("login")).getCssValue("border-color");
        Assert.assertEquals("rgb(255, 0, 0)", borderColor);
    }

    @Test
    @SneakyThrows
    public void TestScreenShot(){
        driver.get("https://www.facebook.com");
        WebElement button = driver.findElement(By.name("login"));
        JsExecutorUtil.takeScreenshot(button, driver);
    }

    @Test
    public void TestNavigationAPI(){
        driver.get("https://www.facebook.com");
        Assert.assertTrue(driver.getTitle().contains("Facebook"));

        JsExecutorUtil.navigateToURL("https://www.instagram.com",driver);
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        Assert.assertTrue(driver.getTitle().contains("Instagram"));
    }

    @SneakyThrows
    @Test
    public void TestScrollTo(){
        driver.get("https://www.facebook.com/help/135275340210354");
        WebElement element = driver.findElement(By.xpath("//span[text()='Invite friends']"));

        long scrollPosition = JsExecutorUtil.scrollToElement(element,driver);
        Assert.assertNotEquals(0, scrollPosition);
    }

}
