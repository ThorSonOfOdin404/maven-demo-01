package GenericTestingFramework;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class TestScenario1 extends BaseClass{
    private Properties objRepo;
    private WebDriver driver;
    private static final Logger LOGGER = Logger.getLogger(TestScenario1.class);

    static{
        LOGGER.info("Child static init");
    }
    {
        LOGGER.info("Child instance init");
    }
    @Before
    public void setUp(){
        LOGGER.info("Child before");
        driver = getDriver();
        objRepo = getObjectRepo();
    }

    @Test
    @SneakyThrows
    public void TestCase1(){
        LOGGER.info("child 1 test case");
        driver.get(objRepo.getProperty("site_url"));

        WebElement username_field = driver.findElement(By.xpath(objRepo.getProperty("u_name_field")));
        WebElement password_field = driver.findElement(By.xpath(objRepo.getProperty("u_pass_field")));
        WebElement login_btn = driver.findElement(By.xpath(objRepo.getProperty("login_btn")));

        username_field.sendKeys(objRepo.getProperty("u_name_val"));
        password_field.sendKeys(objRepo.getProperty("u_pass_val"));
        login_btn.click();
//        System.out.println(username_field.getClass().getName());

        Thread.sleep(2000);
    }

    @After
    public void terminateSession(){
        LOGGER.info("Child after");
        driver.quit();
    }
}
