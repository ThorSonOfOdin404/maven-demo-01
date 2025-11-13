package GenericTestingFramework;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestScenario5 extends BaseClass{
    private WebDriver driver;

    @Before
    public void init(){
        super.init();
        driver = getDriver();
    }

    @SneakyThrows
    @Test
    public void Test1(){
        driver.get("http://localhost");
        String parentWindowHandle = driver.getWindowHandle();

        WebElement fbBtn = driver.findElement(By.xpath("//button[contains(text(),'Facebook')]"));
        WebElement googleBtn = driver.findElement(By.xpath("//button[contains(text(),'Google')]"));
        WebElement samplePageBtn = driver.findElement(By.xpath("//button[contains(text(),'Sample')]"));

        fbBtn.click();
        googleBtn.click();
        samplePageBtn.click();

        Map<String, String> btn_name_to_window_handle = new HashMap<>(3);

        Set<String> handles = driver.getWindowHandles();
        System.out.println(handles.size());

        for(String handle : handles){
            Thread.sleep(1000);
            driver.switchTo().window(handle);
            btn_name_to_window_handle.put(driver.getTitle().split(" ")[0], handle);
        }

        System.out.println(btn_name_to_window_handle.keySet());
        driver.switchTo().window(btn_name_to_window_handle.get("Google"));
        Assert.assertTrue(driver.getTitle().contains("Google"));

        driver.switchTo().window(btn_name_to_window_handle.get("Sample"));
        Assert.assertTrue(driver.getTitle().contains("Sample"));

        driver.switchTo().window(parentWindowHandle);
        Assert.assertTrue(driver.getTitle().contains("Button"));
    }

    @SneakyThrows
    @Test
    public void Test2(){
        driver.get("http://localhost");
        String parentWindowHandle = driver.getWindowHandle();
        WebElement fbBtn = driver.findElement(By.xpath("//button[contains(text(),'Facebook')]"));

        fbBtn.click();
        fbBtn.click();
        fbBtn.click();

        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            if (!handle.equals(parentWindowHandle)) {
                driver.switchTo().window(handle);
                Thread.sleep(500);
                driver.close();
            }
        }

        Assert.assertEquals(1, driver.getWindowHandles().size());
    }

    @SneakyThrows
    @Test
    public void ShadowDomTest(){
        driver.get("https://shop.polymer-project.org/");
        WebElement shadowHost = driver.findElement(By.tagName("shop-app"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> shadowRootMap = (Map<String, Object>) js.executeScript("return arguments[0].shadowRoot", shadowHost);

        String shadowRootId = (String) shadowRootMap.get("shadow-6066-11e4-a52e-4f735466cecf");
        RemoteWebElement shadowDOM1 = new RemoteWebElement();
        shadowDOM1.setParent((RemoteWebDriver) driver);
        shadowDOM1.setId(shadowRootId);

        WebElement shopHome = shadowDOM1.findElement(By.tagName("iron-pages")).findElement(By.tagName("shop-home"));

        shadowRootMap = (Map<String, Object>) js.executeScript("return arguments[0].shadowRoot", shopHome);
        shadowRootId = (String) shadowRootMap.get("shadow-6066-11e4-a52e-4f735466cecf");

        RemoteWebElement shadowDOM2 = new RemoteWebElement();
        shadowDOM2.setParent((RemoteWebDriver) driver);
        shadowDOM2.setId(shadowRootId);

        shadowDOM2.findElement(By.tagName("shop-button")).findElement(By.tagName("a")).click();
        Thread.sleep(1000);
        Assert.assertEquals("Men's Outerwear - SHOP", driver.getTitle());
    }

    @SneakyThrows
    @Test
    public void ShadowDomTest2(){
        driver.get("https://shop.polymer-project.org/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*
        String jsScript =
                "return document.querySelector('shop-app')." +
                "shadowRoot.querySelector('iron-pages')." +
                "querySelector('shop-home')." +
                "shadowRoot.querySelector('shop-button a')";

        WebElement button = (WebElement) js.executeScript(jsScript) ;
        button.click();
        */

        String jsScript =
                "return document.querySelector('shop-app')." +
                "shadowRoot.querySelector('iron-pages')." +
                "querySelector('shop-home')." +
                "shadowRoot.querySelector('shop-button a').click()";
        js.executeScript(jsScript);

        Thread.sleep(1000);
        Assert.assertEquals("Men's Outerwear - SHOP", driver.getTitle());
    }
}

