import io.github.bonigarcia.wdm.WebDriverManager;

import lombok.SneakyThrows;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartTest {
    private static Logger LOGGER = LogManager.getLogger(LoginTestSelenium.class);

    private WebDriver driver;
    static { WebDriverManager.chromedriver().setup(); }
    @Before
    public void initializeWebDriver(){
        driver = new ChromeDriver(new ChromeOptions().addArguments("--incognito"));
    }
    @Test
    public void TestAddAndRemoveCartItem() throws InterruptedException{
        loginToWebSite();

        WebElement shoppingCartQty = null;
        int cartItemsQty=0;

        List<WebElement> addToCartBtnList = driver.findElements(By.className("btn_inventory"));
        for(WebElement addToCardBtn : addToCartBtnList){
            addToCardBtn.click();
            if (++cartItemsQty > 0) {
                shoppingCartQty = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
                Assert.assertEquals(
                        "Cart Item " + cartItemsQty + " was not added",
                        Integer.toString(cartItemsQty), shoppingCartQty.getText());
            }
        }
        List<WebElement> removeFromCartBtnList = driver.findElements(By.className("btn_inventory"));
        for(WebElement addToCardBtn : removeFromCartBtnList){
            addToCardBtn.click();
            if (--cartItemsQty > 0) {
                shoppingCartQty = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
                Assert.assertEquals(
                        "Cart Item " + cartItemsQty + " was not removed",
                        Integer.toString(cartItemsQty), shoppingCartQty.getText());
            }
        }
    }

    @After
    public void cleanup() {
        driver.close();
    }

    //        UTILITY METHODS
    private void loginToWebSite() {
        driver.get("https://www.saucedemo.com/v1/");
        Assert.assertTrue(driver.getTitle().startsWith("Swag"));

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
}
