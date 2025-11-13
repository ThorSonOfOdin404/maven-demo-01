package GenericTestingFramework;

import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestScenario3 extends BaseClass {
    private Properties objRepo;
    private static final Logger LOGGER = Logger.getLogger(TestScenario3.class);
    private WebDriver driver;

    @Before
    public void init(){
        super.init();
        if(driver == null){
            driver = getDriver();
        }
        objRepo = getObjectRepo();
    }

    @SneakyThrows
    @Test
    public void Test1(){
        driver.get("https://automationexercise.com/");
        driver.manage().window().fullscreen();

        driver.findElement(By.xpath("//a[@href=\"/products\"]")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals( "https://automationexercise.com/products", currentUrl);

        driver.findElement(By.xpath("//a[contains(@href,\"Polo\")]")).click();

        //Attempting the add to cart action on buttons
        List<WebElement> products = driver.findElements(By.linkText("Add to cart"));
        for(int i=0;i<products.size();i++){
            WebElement element = products.get(i);
            element.click();

//            driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1).getSeconds());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("close-modal")));
            if(i == products.size()-1){
                driver.findElement(By.linkText("View Cart")).click();
                break;
            }
            driver.findElement(By.className("close-modal")).click();
        }

        Assert.assertTrue(driver.getCurrentUrl().endsWith("view_cart"));
        // Counting Cart elements
        List<WebElement> cart_elements = driver.findElements(By.xpath("//table[@id=\"cart_info_table\"]/tbody/tr[starts-with(@id,'product-')]"));
        Assert.assertEquals(cart_elements.size(), products.size());
    }

}
