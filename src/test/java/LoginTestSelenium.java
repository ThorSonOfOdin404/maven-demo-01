import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginTestSelenium {
    private static Logger LOGGER = LogManager.getLogger(LoginTestSelenium.class);

    private WebDriver driver;

    @BeforeClass
    public static void loadSeleniumProps() throws IOException{
        FileInputStream fIS = new FileInputStream("selenium.properties");
        Properties props = new Properties();
        props.load(fIS);
        System.setProperty("webdriver.chrome.driver", props.getProperty("chromeDriver"));
    }

    @Before
    public void initializeWebDriver(){
        driver = new ChromeDriver();
    }

    @Test
    public void TestLoginSuccess(){

        if (!openHomePage()) return;
        WebElement uNameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement uPassField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement formSubmitBtn = driver.findElement(By.id("login-button"));

        uNameField.sendKeys("standard_user");
        uPassField.sendKeys("secret_sauce");
        formSubmitBtn.click();

        Assert.assertEquals(
                "Login Unsuccessful",
                "https://www.saucedemo.com/v1/inventory.html", driver.getCurrentUrl()
        );
    }

    @Test
    public void TestLoginFailure(){
        if(!openHomePage()) return;

        WebElement uNameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement uPassField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement formSubmitBtn = driver.findElement(By.id("login-button"));

        uNameField.sendKeys("standard_user");
        uPassField.sendKeys("random_password");

        formSubmitBtn.click();

        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));

        String expectedErrorMsg = "Epic sadface: Username and password do not match any user in this service";

        Assert.assertEquals(
                "Login Failure Test unsuccessful",
                expectedErrorMsg,
                errorElement.getText()
        );
    }
    
    @Test
    public void TestNoUserNameLogin(){
        if(!openHomePage()) return;

        WebElement formSubmitBtn = driver.findElement(By.id("login-button"));
        formSubmitBtn.click();

        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
        String expectedErrorMsg = "Epic sadface: Username is required";

        Assert.assertEquals("No UserName Login Test unsuccessful",
                expectedErrorMsg,
                errorElement.getText()
        );
    }

    @Test
    public void TestNoPasswordLogin(){
        if(!openHomePage()) return;

        WebElement uNameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        WebElement uPassField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement formSubmitBtn = driver.findElement(By.id("login-button"));

        uNameField.sendKeys("standard_user");

        formSubmitBtn.click();

        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
        Assert.assertTrue("Error message not displayed", errorElement.isDisplayed() );

        String expectedErrorMsg = "Epic sadface: Password is required";

        Assert.assertEquals("No Password Login Test unsuccessful",
                expectedErrorMsg,
                errorElement.getText()
        );
    }

    @After
    public void cleanUp(){
        driver.quit();
    }

//        UTILITY METHODS
    private boolean openHomePage() {
        driver.get("https://www.saucedemo.com/v1/");
        if(!driver.getTitle().startsWith("Swag")){
            LOGGER.fatal("Could Not Open SwagDemo home page");
            return false;
        }
        return true;
    }
}
