package GenericTestingFramework;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
    This scenario test the interaction with alerts or pop up in selenium
 */
public class TestScenario7 extends BaseClass{
    private WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(TestScenario7.class);

    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }
    @Test
    public void Test1(){
        driver.get("http://localhost:8800");
        // Clicking the set cookie button
        driver.findElement(By.xpath("//button[contains(text(),'Alert')]")).click();
        // Alert comes on page
        // accepting alert after logging its text
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        LOGGER.info(text);
        alert.accept();

        driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
        alert = driver.switchTo().alert();
        text = alert.getText();
        alert.accept();

        //Getting prompt response text span element content
        String promptResponseFromSpan = driver.findElement(By.id("prompt_response")).getText();
        Assert.assertTrue(promptResponseFromSpan.isEmpty());

        // Entering text to prompt and submitting it
        driver.findElement(By.xpath("//button[@id='prompt_user_btn']")).click();
        //Prompt should appear
        alert = driver.switchTo().alert();
        alert.sendKeys("This is the prompt text");
        alert.accept();

        promptResponseFromSpan = driver.findElement(By.id("prompt_response")).getText();
        Assert.assertEquals("This is the prompt text", promptResponseFromSpan);
    }
}
